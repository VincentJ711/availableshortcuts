package com.psquirrel.ijplugins.availableshortcuts;

import com.intellij.openapi.keymap.Keymap;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ComboBoxActionListener implements ActionListener {
  private MyToolWindow myToolWindow;

  ComboBoxActionListener(MyToolWindow myToolWindow) {
    this.myToolWindow = myToolWindow;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Object src = e.getSource();

    if (myToolWindow.comboBox1.equals(src)) {
      myToolWindow.combo1Keymap =
          (Keymap) myToolWindow.comboBox1.getSelectedItem();
      myToolWindow.refreshDeltas();
    } else if (myToolWindow.comboBox2.equals(src)) {
      myToolWindow.combo2Keymap =
          (Keymap) myToolWindow.comboBox2.getSelectedItem();
      myToolWindow.refreshDeltas();
    }
  }
}
