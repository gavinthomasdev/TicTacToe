package dev.gavinthomas.tictactoe.input;

import java.io.IOException;
import java.util.stream.IntStream;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.RejectedExecutionException;

import org.jline.terminal.*;
import org.jline.utils.*;
import org.jline.keymap.*;

public abstract class input {
  private static boolean initiated = false;
  private static boolean readEnabled = false;
  private static boolean awaitingSizeReport = false;
  private static List<InputQueue.QueueType> queue = new ArrayList<InputQueue.QueueType>();
  private static ExecutorService exec = Executors.newSingleThreadExecutor();

  private static final List<Keybind> KEYS = new ArrayList<Keybind>();
  private static final List<Integer> stream = new ArrayList<Integer>();
  private static Terminal terminal;
  private static NonBlockingReader reader;
  private static BindingReader bindReader;

  @SuppressWarnings("unchecked") // Suppress warning for bind allowing any type as an argument
  public static void init() {
    if (initiated) {
      return;
    }
    initiated = true;
    try {
      terminal = TerminalBuilder.builder().jna(true).system(true).build();

      terminal.enterRawMode();

      reader = terminal.reader();
      bindReader = new BindingReader(reader);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void toggleRead(boolean tog) {
    if (readEnabled == false && tog == true) {
      try {
        exec.submit(input::read);
      } catch (RejectedExecutionException e) {
        exec = Executors.newSingleThreadExecutor();
        exec.submit(input::read);
      }
      readEnabled = true;
    } else if (readEnabled == true && tog == false) {
      readEnabled = false;
      exec.shutdownNow();
    }
  }
  
  public static void tread() {
    while (true) {
      while (readEnabled == true) {
        Keycode key = nextKey();
        if (readEnabled == false) { // probably not needed when using shutdownNow since everything is halted.
          break;
        }
        if (key == null) {
          continue;
        }
        for (Keybind kb : KEYS) {
          if (kb.hasKey(key)) {
            kb.handle(key);
          }
        }
      }
    }
  }

  public static void read() {
    checkerloop: while (true) {
      stream.add(bindReader.readCharacter());
      // Out.append(stream.size());
      List<InputQueue.QueueType> queueCheck = new ArrayList<InputQueue.QueueType>(queue);
      List<InputQueue.QueueType> matched = new ArrayList<InputQueue.QueueType>();
      
      for (int i = 0; i < queueCheck.size(); i++) {
        if (queueCheck.get(i).matches(stream)) {
          matched.add(queueCheck.get(i));
        } else if (queueCheck.get(i).matchStart(stream) == false) {
          queueCheck.remove(i);
        }
      }

      if (matched.size() != 0) {
        Collections.sort(matched, new Comparator<InputQueue.QueueType>() {
          public int compare(InputQueue.QueueType q1, InputQueue.QueueType q2) {
            if (q1.priority() == q2.priority()) {
              return 0;
            }
            return (q1.priority() < q2.priority() ? -1 : 1);
          }
        });
  
        for (int i = 0; i < matched.size(); i++) {
          matched.get(i).run(stream);
          queue.remove(matched.get(i));
          if (matched.get(i).doNext()) {
            continue;
          } else {
            stream.clear();
            continue checkerloop;
          }
        }
      }

      if (queueCheck.size() > 0) continue;

      Keycode matchCode = Keycode.find(stream.stream().mapToInt(Integer::intValue).toArray());

      if (matchCode == null && Keycode.hasNext(stream.stream().mapToInt(Integer::intValue).toArray()) == false) {
        stream.clear();
        continue;
      }

      for (Keybind kb : KEYS) {
        if (kb.hasKey(matchCode)) {
          kb.handle(matchCode);
        }
      }
      if (Keycode.hasNext(stream.stream().mapToInt(Integer::intValue).toArray()) == false) {
        stream.clear();
      }
    }
  }


  private static int checkNull(List<InputQueue.QueueType> qms) {
    int count = 0;
    for (int i = 0; i < qms.size(); i++) {
      count += (qms.get(i) != null ? 1 : 0);
    }
    return count;
  }

  public static Keycode nextKey() {
    List<Integer> codes = new ArrayList<Integer>();
    List<InputQueue.QueueType> qms = new ArrayList<InputQueue.QueueType>(queue);

    // codes.add(bindReader.readCharacter());

    

    checkerloop: while (checkNull(qms) > 0 || Keycode.hasNext(codes.stream().mapToInt(Integer::intValue).toArray())) {
      List<InputQueue.QueueType> matched = new ArrayList<InputQueue.QueueType>();
              // System.out.println("checkLoop"); // ...............
      
      codes.add(bindReader.readCharacter());
      for (int i = 0; i < qms.size(); i++) {
        if (qms.get(i) == null) {
          qms.remove(i);
          continue;
        }

        if (qms.get(i).matches(codes)) {
          matched.add(qms.get(i));
        } else if (qms.get(i).matchStart(codes) == false) {
          qms.remove(i);
        }
      }

      
      if (matched.size() == 0) {
        continue;
      }
      Collections.sort(matched, new Comparator<InputQueue.QueueType>() {
        public int compare(InputQueue.QueueType q1, InputQueue.QueueType q2) {
          if (q1.priority() == q2.priority()) {
            return 0;
          }
          return (q1.priority() < q2.priority() ? -1 : 1);
        }
      });

      for (int i = 0; i < matched.size(); i++) {
        matched.get(i).run(codes);
        queue.remove(matched.get(i));
        if (matched.get(i).doNext()) {
          continue;
        } else {
          // break checkerloop; // Should break this loop as well. Check later.
          return null;
        }
      }
      // codes.add(bindReader.readCharacter());
    }
    while (Keycode.hasNext(codes.stream().mapToInt(Integer::intValue).toArray())) {
      // System.out.println(bindReader.readCharacter());
      codes.add(bindReader.readCharacter());
    }

    return Keycode.find(codes.stream().mapToInt(Integer::intValue).toArray());
  }

  public static void getSizeReport() {
    System.out.print("\033[s\033[50000;50000H\033[6n\033[u");
    awaitingSizeReport = true;
  }

  public static void addBinds(Keybind kb) {
    KEYS.add(kb);
  }

  public static void addBinds(Keybind kb, Keybind... kbs) {
    KEYS.add(kb);
    KEYS.addAll(Arrays.asList(kbs));
  }

  public static void removeBinds(Keybind kb) {
    KEYS.remove(kb);
  }

  public static void removeBinds(Keybind kb, Keybind... kbs) {
    KEYS.remove(kb);
    KEYS.removeAll(Arrays.asList(kbs));
  }

  public static void clearBinds() {
    KEYS.clear();
  }

  public static int nextChar() {
    return bindReader.readCharacter();
  }

  public static boolean arrContainsFinal(int[] arr, int key) {
    final int val = key;
    return IntStream.of(arr).anyMatch(i -> i == val);
  }

  public static int awaitKey(int[] validKeys) {
    int currKey = nextChar();
    while (arrContainsFinal(validKeys, currKey) == false) {
      System.out.println(currKey + " | " + (char) currKey);
      currKey = nextChar();
    }
    return currKey;
  }
}