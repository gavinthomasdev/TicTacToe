package dev.gavinthomas.tictactoe.utils;

import java.util.Map;
import java.util.HashMap;

public abstract class Term {
  private static Map<String, int[]> cursorSaves = new HashMap<String, int[]>();

  public static void saveCursor() {
    System.out.print("\033[s");
  }

  public static void restoreCursor() {
    System.out.print("\033[u");
  }

  public static void setCursorPos(int y, int x) {
    System.out.print("\033[" + y + ";" + x + "H");
  }

  public static void clear(boolean goHome) {
    System.out.print("\033[2J" + (goHome ? "\033[H" : ""));
  }

  public static void hideCursor(boolean tog) {
    System.out.print("\033[?25" + (tog ? "l" : "h"));
  }
}