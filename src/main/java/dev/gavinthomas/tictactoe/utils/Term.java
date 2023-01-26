package dev.gavinthomas.tictactoe.utils;

import dev.gavinthomas.tictactoe.input.InputQueue;
import dev.gavinthomas.tictactoe.input.input;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class Term {
  private static boolean watching = false;
  private static final List<List<String>> queue = new ArrayList<>();
  private static final ExecutorService exec = Executors.newSingleThreadExecutor();
  private final Map<String, int[]> cursorSaves = new HashMap<String, int[]>();
  private List<String> currGroup;
  public volatile boolean enabled = true;


  public void setEnabled(boolean tog) {
    if (tog && !enabled) {
      enabled = true;
    } else if (!tog && enabled) {
      enabled = false;
    }
  }

  public static void watchQueue(boolean tog) {
    if (tog && !watching) {
      exec.submit(Term::printQueue);
    } else if (!tog && watching) {
      exec.shutdownNow();
    }
  }

  public static void printQueue() {
    while (true) {
      while (queue.size() == 0) {
        Thread.onSpinWait();
      }
      for (List<String> grp : queue) {
        for (String s : grp) {
          System.out.print(s);
        }
        queue.remove(grp);
      }
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

  public void startGroup() {
    currGroup = new ArrayList<>();
  }

  public void print(String outp) {
    if (!enabled) return;
    if (currGroup != null) {
      currGroup.add(outp);
    } else {
      queue.add(Collections.singletonList(outp));
      exec.submit(Term::printQueue);
    }
//    if (!enabled) {
//      queue.add(outp);
//      return;
//    }

//    System.out.print(outp);
  }

  public void endGroup() {
    if (currGroup == null) return;
    queue.add(currGroup);
    currGroup = null;
    exec.submit(Term::printQueue);
  }

  public void requestSize(Consumer<Object[]> callback) {
    this.startGroup();
    this.saveCursor();
    this.setCursorPos(50000, 50000);
    this.print("\033[6n");
    this.restoreCursor();
    this.endGroup();
    if (!enabled) return;
    input.queue.add(new InputQueue.TermSize.Builder(callback).args(new Object[]{InputQueue.ArgType.TERMSIZE}).build());

  }
}