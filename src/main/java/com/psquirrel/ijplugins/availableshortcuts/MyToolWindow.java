package com.psquirrel.ijplugins.availableshortcuts;

import com.google.common.collect.Sets;
import com.intellij.openapi.actionSystem.KeyboardShortcut;
import com.intellij.openapi.actionSystem.Shortcut;
import com.intellij.openapi.keymap.Keymap;
import com.intellij.openapi.keymap.KeymapManagerListener;
import com.intellij.openapi.keymap.ex.KeymapManagerEx;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MyToolWindow implements KeymapManagerListener {
  private static final String availableShortcutsPanelTitle = "available";

  Keymap combo1Keymap;

  Keymap combo2Keymap;

  JComboBox<Keymap> comboBox1;

  JComboBox<Keymap> comboBox2;

  private JPanel toolWindowPanel;

  private JTabbedPane availableShortcutsTabbedPane;

  private DefaultListModel<String> freeModel = new DefaultListModel<>();

  private DefaultListModel<String> deltasModel = new DefaultListModel<>();

  private JList<String> freeList;

  private JList<String> deltasList;

  private JLabel deltasLabel;

  private JLabel availableLabel;

  MyToolWindow() {
    combo2Keymap = getActiveMap();
    combo1Keymap = combo2Keymap == null ? null : combo2Keymap.getParent();
    freeList.setModel(freeModel);
    deltasList.setModel(deltasModel);
    deltasList.setCellRenderer(new DeltasCellRenderer());
    initCombos();
    refresh();
  }

  private Keymap getActiveMap() {
    return KeymapManagerEx.getInstanceEx().getActiveKeymap();
  }

  private void initCombos() {
    ComboBoxCellRenderer renderer = new ComboBoxCellRenderer();
    ComboBoxActionListener listener = new ComboBoxActionListener(this);
    comboBox1.setRenderer(renderer);
    comboBox2.setRenderer(renderer);

    for (Keymap km : KeymapManagerEx.getInstanceEx().getAllKeymaps()) {
      comboBox1.addItem(km);
      comboBox2.addItem(km);
    }

    if (combo1Keymap != null) {
      comboBox1.setSelectedItem(combo1Keymap);
      if (combo2Keymap != null) {
        comboBox2.setSelectedItem(combo2Keymap);
      }
    }

    comboBox1.addActionListener(listener);
    comboBox2.addActionListener(listener);
  }

  private void refresh() {
    Keymap activeMap = getActiveMap();

    freeModel.clear();

    if (activeMap == null) {
      availableLabel.setText("");
      return;
    }

    String fmtStr = "<html>The following shortcuts are available after" +
        " taking into<br>consideration your current keymap<br><em>%s</em>" +
        ".</html>";
    availableLabel
        .setText(String.format(fmtStr, activeMap.getPresentableName()));

    HashSet<KeyStroke> takenStrokes = new HashSet<>();
    int freeCnt = 0;

    for (String actionId : activeMap.getActionIdList()) {
      Shortcut[] cuts = activeMap.getShortcuts(actionId);

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

    for (KeyStroke ks : AllStrokes.values) {
      if (!takenStrokes.contains(ks)) {
        freeCnt++;
        freeModel.addElement(Shortcuts.strokeToString(ks));
      }
    }

    availableShortcutsTabbedPane.setTitleAt(0, String.format(
        availableShortcutsPanelTitle + " (%d)", freeCnt));

    refreshDeltas();
  }

  void refreshDeltas() {
    deltasModel.clear();

    if (combo1Keymap == null || combo2Keymap == null) {
      deltasLabel.setText("there are no changes to view");
      return;
    }

    final String fmtStr = "<html><p>The following changes to keymap " +
        "<em>%s</em><br> yield keymap <em>%s</em>.</p></html>";
    deltasLabel.setText(String.format(fmtStr,
        combo1Keymap.getPresentableName(),
        combo2Keymap.getPresentableName()));

    Keymap[] keyMaps = new Keymap[] {combo1Keymap, combo2Keymap};
    List<HashMap<String, Shortcuts>> actionShortcuts = new ArrayList<>();

    actionShortcuts.add(new HashMap<>());
    actionShortcuts.add(new HashMap<>());

    for (int i = 0; i < keyMaps.length; i++) {
      Keymap km = keyMaps[i];
      HashMap<String, Shortcuts> cuts = actionShortcuts.get(i);
      Collection<String> actionIds = km.getActionIdList();

      for (String id : actionIds) {
        cuts.put(id, new Shortcuts(km.getShortcuts(id)));
      }
    }

    for (String actionId : Sets.union(actionShortcuts.get(0).keySet(),
        actionShortcuts.get(1).keySet())) {
      Shortcuts first = actionShortcuts.get(0).get(actionId);
      Shortcuts second = actionShortcuts.get(1).get(actionId);

      if (first != null && first.size > 0 && !first.equals(second)) {
        deltasModel.addElement(subtractionActionLabel(actionId, first));
      }

      if (second != null && second.size > 0 && !second.equals(first)) {
        deltasModel.addElement(additionActionLabel(actionId, second));
      }

      // if (first != null && second != null && !first.equals(second)) {
      //   deltasModel.addElement(subtractionActionLabel(actionId, first));
      //   deltasModel.addElement(additionActionLabel(actionId, second));
      // } else if (second != null && !second.equals(first)) {
      //   deltasModel.addElement(additionActionLabel(actionId, second));
      // } else if (first != null && !first.equals(second)) {
      //   deltasModel.addElement(subtractionActionLabel(actionId, first));
      // }
    }
  }

  private String subtractionActionLabel(String actionId, Shortcuts cuts) {
    return String.format("- %s: %s", actionId, cuts.toString());
  }

  private String additionActionLabel(String actionId, Shortcuts cuts) {
    return String.format("+ %s: %s", actionId, cuts.toString());
  }

  JPanel getContent() {
    return toolWindowPanel;
  }

  @Override
  public void activeKeymapChanged(@Nullable Keymap keymap) {
    if (keymap != null) {
      refresh();
    }
  }

  @Override
  public void shortcutChanged(@NotNull Keymap keymap,
      @NotNull String actionId) {
    refresh();
  }
}
