package com.psquirrel.ijplugins.availableshortcuts;

import java.awt.event.KeyEvent;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.swing.KeyStroke;

public final class AllStrokes {
  public static final Set<KeyStroke> values = new LinkedHashSet<>();

  private static final int[][] modsUnmasked = new int[][] {
      {KeyEvent.VK_META},
      {KeyEvent.VK_CONTROL},
      {KeyEvent.VK_CONTROL, KeyEvent.VK_META},
      {KeyEvent.VK_ALT, KeyEvent.VK_META},
      {KeyEvent.VK_SHIFT, KeyEvent.VK_META},
      {KeyEvent.VK_CONTROL, KeyEvent.VK_ALT},
      {KeyEvent.VK_SHIFT, KeyEvent.VK_CONTROL},
      {KeyEvent.VK_CONTROL, KeyEvent.VK_ALT, KeyEvent.VK_META},
      {KeyEvent.VK_SHIFT, KeyEvent.VK_CONTROL, KeyEvent.VK_META},
      {KeyEvent.VK_SHIFT, KeyEvent.VK_ALT, KeyEvent.VK_META},
      {KeyEvent.VK_SHIFT, KeyEvent.VK_CONTROL, KeyEvent.VK_ALT}
  };

  private static final int[] nonModifierKeys = {
      KeyEvent.VK_ESCAPE,
      KeyEvent.VK_F1,
      KeyEvent.VK_F2,
      KeyEvent.VK_F3,
      KeyEvent.VK_F4,
      KeyEvent.VK_F5,
      KeyEvent.VK_F6,
      KeyEvent.VK_F7,
      KeyEvent.VK_F8,
      KeyEvent.VK_F9,
      KeyEvent.VK_F10,
      KeyEvent.VK_F11,
      KeyEvent.VK_F12,
      KeyEvent.VK_BACK_QUOTE,
      KeyEvent.VK_1,
      KeyEvent.VK_2,
      KeyEvent.VK_3,
      KeyEvent.VK_4,
      KeyEvent.VK_5,
      KeyEvent.VK_6,
      KeyEvent.VK_7,
      KeyEvent.VK_8,
      KeyEvent.VK_9,
      KeyEvent.VK_0,
      KeyEvent.VK_MINUS,
      KeyEvent.VK_EQUALS,
      KeyEvent.VK_BACK_SPACE,
      KeyEvent.VK_TAB,
      KeyEvent.VK_Q,
      KeyEvent.VK_W,
      KeyEvent.VK_E,
      KeyEvent.VK_R,
      KeyEvent.VK_T,
      KeyEvent.VK_Y,
      KeyEvent.VK_U,
      KeyEvent.VK_I,
      KeyEvent.VK_O,
      KeyEvent.VK_P,
      KeyEvent.VK_OPEN_BRACKET,
      KeyEvent.VK_CLOSE_BRACKET,
      KeyEvent.VK_BACK_SLASH,
      KeyEvent.VK_A,
      KeyEvent.VK_S,
      KeyEvent.VK_D,
      KeyEvent.VK_F,
      KeyEvent.VK_G,
      KeyEvent.VK_H,
      KeyEvent.VK_J,
      KeyEvent.VK_K,
      KeyEvent.VK_L,
      KeyEvent.VK_SEMICOLON,
      KeyEvent.VK_QUOTE,
      KeyEvent.VK_ENTER,
      KeyEvent.VK_Z,
      KeyEvent.VK_X,
      KeyEvent.VK_C,
      KeyEvent.VK_V,
      KeyEvent.VK_B,
      KeyEvent.VK_N,
      KeyEvent.VK_M,
      KeyEvent.VK_COMMA,
      KeyEvent.VK_PERIOD,
      KeyEvent.VK_SLASH,
      KeyEvent.VK_SPACE,
      KeyEvent.VK_LEFT,
      KeyEvent.VK_UP,
      KeyEvent.VK_RIGHT,
      KeyEvent.VK_DOWN
  };

  private static final int[] mods = new int[modsUnmasked.length];

  static {
    for (int i = 0; i < modsUnmasked.length; i++) {
      int res = 0;

      for (int j = 0; j < modsUnmasked[i].length; j++) {
        int mod = modsUnmasked[i][j];
        int mask = 0;

        switch (mod) {
          case KeyEvent.VK_META:
            mask = KeyEvent.META_MASK;
            break;
          case KeyEvent.VK_CONTROL:
            mask = KeyEvent.CTRL_MASK;
            break;
          case KeyEvent.VK_ALT:
            mask = KeyEvent.ALT_MASK;
            break;
          case KeyEvent.VK_SHIFT:
            mask = KeyEvent.SHIFT_MASK;
            break;
        }

        res |= mask;
      }

      mods[i] = res;
    }

    for (int mod : mods) {
      for (int code : nonModifierKeys) {
        KeyStroke s = KeyStroke.getKeyStroke(code, mod);
        values.add(s);
      }
    }
  }
}
