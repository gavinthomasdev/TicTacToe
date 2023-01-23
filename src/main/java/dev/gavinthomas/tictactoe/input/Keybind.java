package dev.gavinthomas.tictactoe.input;

import java.util.function.Consumer;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

// import tetris.enums.KeyType;
// import tetris.utils.*;
// import tetris.enums.Command;
// import tetris.enums.KeybindArgument;
// import tetris.enums.Keycode;


public class Keybind {
  private final List<Object> ARGS;
  private final List<Keycode> KEYS;
  private final Consumer<Object[]> RUNNER;

  // new HashMap<String, Object>(Map.ofEntries(Map.entry("abc", 1), ...)); //
  // Unlimited
  // new HashMap<String, Object>(Map.of("abc", 1, ...)); // Up to 10

  public Keybind(Keycode key, Object[] args, Consumer<Object[]> runner) {
    this.KEYS = new ArrayList<Keycode>();
    this.KEYS.add(key);
    this.ARGS = replaceArgs(Arrays.asList(args));
    this.RUNNER = runner;
  }

  public Keybind(Keycode[] keys, Object[] args, Consumer<Object[]> runner) {
    this.KEYS = new ArrayList<Keycode>(Arrays.asList(keys));
    this.ARGS = replaceArgs(Arrays.asList(args));
    this.RUNNER = runner;
  }

  public Keybind(List<Keycode> keys, Object[] args, Consumer<Object[]> runner) {
    this.KEYS = new ArrayList<Keycode>(keys);
    this.ARGS = replaceArgs(Arrays.asList(args));
    this.RUNNER = runner;
  }

  public void addKeys(Keycode key) {
    this.KEYS.add(key);
  }

  public void addKeys(Keycode key, Keycode... keys) {
    this.KEYS.add(key);
    this.KEYS.addAll(Arrays.asList(keys));
  }

  public void removeKeys(Keycode key) {
    this.KEYS.remove(key);
  }

  public void removeKeys(Keycode key, Keycode... keys) {
    this.KEYS.remove(key);
    this.KEYS.removeAll(Arrays.asList(keys));
  }

  // public boolean[] hasKeys(int key) {

  // }

  // public boolean[] hasKeys(int key, int... keys) {

  // }

  public boolean hasKey(Keycode key) {
    return KEYS.contains(key);
  }

  public void run(Keycode key) {
    List<Object> argCopy = new ArrayList<Object>(ARGS);
    for (int i = 0; i < argCopy.size(); i++) {
      if (argCopy.get(i) == KeybindArgument.KEYCODE) {
        argCopy.set(i, key);
      }
    }
    RUNNER.accept(argCopy.toArray());
  }

  public void handle(Keycode key) {
    if (props.enabled && !props.forceDisabled && System.currentTimeMillis() > (props.lastUsed + props.cooldown)) {
      props.lastUsed = System.currentTimeMillis();
      run(key);
    }
  }

  private List<Object> replaceArgs(List<Object> args) {
    for (int i = 0; i < args.size(); i++) {
      if (args.get(i) == KeybindArgument.KEYBIND) {
        args.set(i, this);
      }
    }
    return args;
  }

  public final Getters get = new Getters();
  public final Setters set = new Setters();
  private final Properties props = new Properties();

  private final class Properties {
    private boolean enabled = true;
    private boolean forceDisabled = false;
    private int cooldown = 0;
    private long lastUsed = 0;
  }

  public final class Getters {
    public boolean enabled() {
      return props.enabled;
    }

    public int cooldown() {
      return props.cooldown;
    }

    public long lastUsed() {
      return props.lastUsed;
    }

    public boolean forceDisabled() {
      return props.forceDisabled;
    }
  }

  public final class Setters {
    public void enabled(boolean on) {
      RUNNER.accept(new Object[] { "abc" });
      props.enabled = on;
    }

    public void cooldown(int time) {
      props.cooldown = time;
    }

    public void lastUsed(long time) {
      props.lastUsed = time;
    }

    public void forceDisabled(boolean tog) {
      props.forceDisabled = tog;
    }
  }
}