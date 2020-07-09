package com.psquirrel.ijplugins.availableshortcuts;

import com.intellij.openapi.actionSystem.KeyboardShortcut;
import com.intellij.openapi.actionSystem.Shortcut;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.KeyStroke;

public class Shortcuts {
  final int size;

  private final String repr;

  Shortcuts(Shortcut[] shortcuts) {
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
          sb.append("  then  ").append(strokeToString(second));
        }
      } else {
        sb.append(sc.toString());
      }
      strings.add(sb.toString());
    }

    strings.sort(Comparator.naturalOrder());
    repr = String.join("  or  ", strings);
  }

  static String strokeToString(KeyStroke ks) {
    String out = "";

    if (ks != null) {
      String kt = KeyEvent.getKeyText(ks.getKeyCode());
      int modifiers = ks.getModifiers();
      if (modifiers > 0) {
        out = InputEvent.getModifiersExText(modifiers)
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