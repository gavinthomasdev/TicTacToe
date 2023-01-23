package dev.gavinthomas.tictactoe.utils;

import dev.gavinthomas.tictactoe.input.InputQueue;
import dev.gavinthomas.tictactoe.input.input;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.function.Consumer;

public class Term {
  private final Map<String, int[]> cursorSaves = new HashMap<String, int[]>();
  private final List<String> queue = new ArrayList<>();
  public boolean enabled = true;

  public void printQueue() {
    for (String s : queue) {
      System.out.print(s);
    }
  }

  public void saveCursor() {
    this.print("\033[s");
  }

  public void restoreCursor() {
    this.print("\033[u");
  }

  public void setCursorPos(int x, int y) {
    this.print("\033[" + y + ";" + x + "H");
  }

  public void clear(boolean goHome) {
    this.print("\033[2J" + (goHome ? "\033[H" : ""));
  }

  public void hideCursor(boolean tog) {
    this.print("\033[?25" + (tog ? "l" : "h"));
  }

  public void print(String outp) {
    if (!enabled) {
      queue.add(outp);
      return;
    }
    System.out.print(outp);
  }

  public void requestSize(Consumer<Object[]> callback) {
    this.saveCursor();
    this.setCursorPos(50000, 50000);
    this.print("\033[6n");
    this.restoreCursor();
    input.queue.add(new InputQueue.TermSize.Builder(callback).args(new Object[]{InputQueue.ArgType.TERMSIZE}).build());
  }
}