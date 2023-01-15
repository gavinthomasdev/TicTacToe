package dev.gavinthomas.tictactoe.input;

import java.util.function.Consumer;
import java.util.Arrays;
import java.util.List;

import java.util.regex.Pattern;

public abstract class InputQueue {
  public static String codesToString(List<Integer> codes) {
    String str = "";
    for (int i = 0; i < codes.size(); i++) {
      str += Character.toString(codes.get(i));
    }
    return str;
  }

  public interface QueueType {
    public boolean matchStart(List<Integer> codes);

    public boolean matches(List<Integer> codes);

    public void run(List<Integer> codes);

    public int priority();

    public boolean doNext();
  }

  public static class TermSize implements QueueType {
    private final int PRIORITY;
    private final List<Object> ARGS;
    private final Consumer<Object[]> RUNNER;
    private final boolean DONEXT;

    private TermSize(Builder build) {
      this.PRIORITY = build.priority;
      this.ARGS = Arrays.asList(build.args);
      this.RUNNER = build.runner;
      this.DONEXT = build.doNext;
    }

    public boolean matchStart(List<Integer> codes) {
      String cstr = codesToString(codes);
      return pats.termSizePrefix.matcher(cstr).matches();
    }

    public boolean matches(List<Integer> codes) {
      String cstr = codesToString(codes);
      return pats.termSize.matcher(cstr).matches();
    }

    public void run(List<Integer> codes) {
      for (int i = 0; i < ARGS.size(); i++) {
        if (ARGS.get(i) == ArgType.CODES) {
          ARGS.set(i, codes);
        }
      }
      RUNNER.accept(ARGS.toArray());
    }

    public int priority() {
      return PRIORITY;
    }

    public boolean doNext() {
      return DONEXT;
    }

    public static class Builder {
      private int priority = 0;
      private boolean doNext = false;
      private Object[] args = new Object[0];
      private Consumer<Object[]> runner;

      public Builder(Consumer<Object[]> runner) {
        this.runner = runner;
      }

      public Builder args(Object[] args) {
        this.args = args;
        return this;
      }

      public Builder priority(int priority) {
        this.priority = priority;
        return this;
      }

      public Builder doNext(boolean tog) {
        this.doNext = tog;
        return this;
      }

      public TermSize build() {
        return new TermSize(this);
      }
    }
  }

  public static enum ArgType {
    CODES;
  }

  private static abstract class pats {
    public static Pattern termSize = Pattern.compile("\033\\[(\\d+)\\;(\\d+)R");
    public static Pattern termSizePrefix = Pattern.compile(
        "^((?:\033)|(?:\033\\[)|(?:\033\\[(?:\\d+))|(?:\033\\[(?:\\d+)\\;)|(?:\033\\[(?:\\d+)\\;(?:\\d+))|(?:\033\\[(?:\\d+)\\;(?:\\d+)R))$");
  }
}