package com.psquirrel.ijplugins.availableshortcuts;

import com.google.common.collect.Sets;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.KeyboardShortcut;
import com.intellij.openapi.actionSystem.Shortcut;
import com.intellij.openapi.keymap.Keymap;
import com.intellij.openapi.keymap.KeymapManager;
import com.intellij.openapi.keymap.KeymapManagerListener;
import com.intellij.openapi.keymap.KeymapUtil;
import com.intellij.ui.JBColor;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.ListCellRenderer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SomeToolWindow implements KeymapManagerListener,
    ListCellRenderer<String> {
  private static final String availableShortcutsPanelTitle =
      "availableShortcuts";

  private DefaultListModel<String> availableModel = new DefaultListModel<>();

  private DefaultListModel<String> firstDeltas = new DefaultListModel<>();

  private DefaultListModel<String> secondDeltas = new DefaultListModel<>();

  private JList<String> availableShortcutsList;

  private JPanel toolWindowPanel;

  private JList<String> deltasList;

  private JLabel keyMapDeltasDescription;

  private JScrollPane scrollPane;

  private JPanel availableShortcutsPanel;

  private JTabbedPane availableShortcutsTabbedPane;

  private JList<String> deltasList2;

  private JPanel keyMapDeltas2Panel;

  private JPanel keyMapDeltasPanel;

  private JLabel keyMapDeltas2Description;

  public SomeToolWindow() {
    availableShortcutsList.setVisible(true);
    availableShortcutsList.setModel(availableModel);
    deltasList.setModel(firstDeltas);
    deltasList2.setModel(secondDeltas);
    deltasList.setCellRenderer(this);
    deltasList2.setCellRenderer(this);
    refresh();

    // XTODO here we are
    // KeymapManagerEx.getInstanceEx().getAllKeymaps()
  }

  public void refresh() {
    Keymap curr = KeymapManager.getInstance().getActiveKeymap();
    Iterator<String> itr = curr.getActionIdList().iterator();
    HashSet<KeyStroke> takenStrokes = new HashSet<>();
    HashSet<KeyStroke> availableStrokes = new HashSet<>();

    log("refreshing available shortcuts for keymap " +
        curr.getPresentableName());

    while (itr.hasNext()) {
      String actionId = itr.next();
      Shortcut[] cuts =
          KeymapUtil.getActiveKeymapShortcuts(actionId).getShortcuts();

      for (Shortcut cut : cuts) {
        if (cut instanceof KeyboardShortcut) {
          KeyboardShortcut sc = (KeyboardShortcut) cut;
          takenStrokes.add(sc.getFirstKeyStroke());

          if (sc.getSecondKeyStroke() != null) {
            takenStrokes.add(sc.getSecondKeyStroke());
          }
        }
      }
    }

    availableModel.clear();

    for (KeyStroke ks : AllStrokes.values) {
      if (!takenStrokes.contains(ks)) {
        availableStrokes.add(ks);
        availableModel.addElement(Shortcuts.strokeToString(ks));
      }
    }

    availableShortcutsTabbedPane.setTitleAt(0, String.format(
        availableShortcutsPanelTitle + " (%d)", availableStrokes.size()));

    refreshDeltas();
  }

  public void log(String msg) {
    Notifications.Bus.notify(new Notification(
        "okWhatever",
        "SomeToolWindow log",
        msg,
        NotificationType.INFORMATION
    ));
  }

  private void refreshDeltas() {
    Keymap curr = KeymapManager.getInstance().getActiveKeymap();
    Keymap par;

    if (curr == null || (par = curr.getParent()) == null) {
      // nothing to compare
      keyMapDeltasPanel.setVisible(false);
      keyMapDeltas2Panel.setVisible(false);
      return;
    }

    Keymap rootPar = par;

    while (rootPar.getParent() != null) {
      rootPar = rootPar.getParent();
    }

    keyMapDeltasPanel.setVisible(true);
    keyMapDeltasDescription.setText(String.format("Comparing current keymap " +
            "%s with parent keymap %s", curr.getPresentableName(),
        par.getPresentableName()));
    compare(curr, par, firstDeltas);

    if (rootPar != par) {
      keyMapDeltas2Panel.setVisible(true);
      keyMapDeltas2Description.setText(String.format("Comparing current " +
              "keymap %s with root keymap %s", curr.getPresentableName(),
          rootPar.getPresentableName()));
      compare(curr, rootPar, secondDeltas);
    } else {
      keyMapDeltas2Panel.setVisible(false);
    }
  }

  public void compare(Keymap curr, Keymap par,
      DefaultListModel<String> model) {
    Keymap[] kmaps = new Keymap[] {curr, par};
    List<HashMap<String, Shortcuts>> actionShortcuts = new ArrayList<>();
    actionShortcuts.add(new HashMap<>());
    actionShortcuts.add(new HashMap<>());

    for (int i = 0; i < kmaps.length; i++) {
      Keymap km = kmaps[i];
      HashMap<String, Shortcuts> cuts = actionShortcuts.get(i);
      Collection<String> actionIds = km.getActionIdList();

      for (String id : actionIds) {
        cuts.put(id, new Shortcuts(km.getShortcuts(id)));
      }
    }

    model.clear();

    for (String actionId : Sets.union(actionShortcuts.get(0).keySet(),
        actionShortcuts.get(1).keySet())) {
      // atleast 1 of the following must not be null, since we merged
      // their keysets
      Shortcuts currShorts = actionShortcuts.get(0).get(actionId);
      Shortcuts parShorts = actionShortcuts.get(1).get(actionId);

      if (currShorts != null && parShorts != null &&
          !currShorts.equals(parShorts)) {
        model.addElement(subtractionActionLabel(actionId, parShorts));
        model.addElement(additionActionLabel(actionId, currShorts));
      } else if (parShorts != null && !parShorts.equals(currShorts)) {
        model.addElement(subtractionActionLabel(actionId, parShorts));
      } else if (currShorts != null && !currShorts.equals(parShorts)) {
        model.addElement(additionActionLabel(actionId, currShorts));
      }
    }

    if (model.size() == 0) {
      model.addElement("There were no differences between keymaps " +
          curr.getPresentableName() + " and " + par.getPresentableName());
    }
  }

  public String subtractionActionLabel(String actionId, Shortcuts cuts) {
    return String.format("- %s: %s", actionId, cuts.toString());
  }

  public String additionActionLabel(String actionId, Shortcuts cuts) {
    return String.format("+ %s: %s", actionId, cuts.toString());
  }

  @Override
  public Component getListCellRendererComponent(JList list, String value,
      int index, boolean isSelected, boolean cellHasFocus) {
    JLabel l = new JLabel(value);

    if (value.startsWith("+")) {
      l.setForeground(JBColor.green);
    } else if (value.startsWith("-")) {
      l.setForeground(JBColor.red);
    }

    return l;
  }

  public JPanel getContent() {
    return toolWindowPanel;
  }

  @Override
  public void activeKeymapChanged(@Nullable Keymap keymap) {
    if (keymap != null) {
      log("active keymap changed to " + keymap.getPresentableName());
      refresh();
    }
  }

  @Override
  public void shortcutChanged(@NotNull Keymap keymap,
      @NotNull String actionId) {
    log("shortcut changed for action " + actionId + ". this is for keymap" +
        keymap.getPresentableName() + ".");
    refresh();
  }

  private static class Shortcuts {
    public final int size;

    private final String repr;

    public Shortcuts(Shortcut[] shortcuts) {
      size = shortcuts.length;

      List<String> strings = new ArrayList<>();

      for (Shortcut sc : shortcuts) {
        StringBuilder sb = new StringBuilder();

        if (sc instanceof KeyboardShortcut) {
          KeyboardShortcut ksc = (KeyboardShortcut) sc;
          KeyStroke first = ksc.getFirstKeyStroke();
          KeyStroke second = ksc.getSecondKeyStroke();
          sb.append(strokeToString(first));
          if (second != null) {
            sb.append(" -> ").append(strokeToString(second));
          }
        } else {
          sb.append(sc.toString());
        }
        strings.add(sb.toString());
      }

      strings.sort(Comparator.naturalOrder());
      repr = strings.toString();
    }

    private static String strokeToString(KeyStroke ks) {
      String out = "";

      if (ks != null) {
        String kt = KeyEvent.getKeyText(ks.getKeyCode());
        int modifiers = ks.getModifiers();
        if (modifiers > 0) {
          out = KeyEvent.getKeyModifiersText(modifiers)
              .replaceAll("\\+", " ");
        }

        if (!out.contains(kt)) {
          out += " " + kt;
        }
      }

      return out;
    }

    @Override
    public int hashCode() {
      return repr.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
      if (!(obj instanceof Shortcuts)) {
        return false;
      }
      return repr.equals(((Shortcuts) obj).repr);
    }

    @Override
    public String toString() {
      return repr;
    }
  }
}
