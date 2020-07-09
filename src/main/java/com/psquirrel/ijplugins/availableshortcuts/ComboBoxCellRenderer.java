package com.psquirrel.ijplugins.availableshortcuts;

import com.intellij.openapi.keymap.Keymap;
import com.intellij.openapi.keymap.ex.KeymapManagerEx;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ComboBoxCellRenderer implements ListCellRenderer<Keymap> {
  @Override
  public Component getListCellRendererComponent(
      JList<? extends Keymap> list,
      Keymap kmap, int index, boolean isSelected, boolean cellHasFocus) {
    if (kmap == null) {
      return null;
    }

    Keymap active = KeymapManagerEx.getInstanceEx().getActiveKeymap();
    String prefix = active != null && active.equals(kmap) ? "* " : "- ";
    StringBuilder sb = new StringBuilder(prefix + kmap.getPresentableName());

    while (kmap.getParent() != null) {
      kmap = kmap.getParent();
      sb.append(" < ").append(kmap.getPresentableName());
    }

    return new JLabel(sb.toString());
  }
}
