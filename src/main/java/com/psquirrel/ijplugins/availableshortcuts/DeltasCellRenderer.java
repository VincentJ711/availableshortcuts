package com.psquirrel.ijplugins.availableshortcuts;

import com.intellij.ui.JBColor;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class DeltasCellRenderer implements ListCellRenderer<String> {
  @Override
  public Component getListCellRendererComponent(JList<? extends String> list,
      String value, int index, boolean isSelected, boolean cellHasFocus) {
    JLabel l = new JLabel(value);

    if (value.startsWith("+")) {
      l.setForeground(JBColor.green);
    } else if (value.startsWith("-")) {
      l.setForeground(JBColor.red);
    }

    return l;
  }
}
