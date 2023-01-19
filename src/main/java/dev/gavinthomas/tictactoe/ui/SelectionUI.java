package dev.gavinthomas.tictactoe.ui;

import dev.gavinthomas.tictactoe.types.UIComponent;
import dev.gavinthomas.tictactoe.types.UIHolder;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class SelectionUI implements UIComponent {
  public static final String[] uiFormatting = {"\033[1m> ", " <\033[0m"};
  private Selection selected;
  private Point pos;
  private final UIHolder holder;
  public final List<Selection> selections;
  java.util.concurrent.Semaphore s = new java.util.concurrent.Semaphore(0);

  public SelectionUI(UIHolder holder, Selection[] selections, Point pos) {
    this.selections = Arrays.asList(selections);
    this.selected = selections[0];
    this.pos = pos;
    this.holder = holder;
  }

  public void render() {
    holder.setLocation(pos, getRender());
  }



  public String getRender() {
    StringBuilder vals = new StringBuilder();
    for (Selection s : selections) {
      vals.append(SelectionUI.getComp(s.NAME, s == selected, selections.indexOf(s) != selections.size() - 1));
    }
    return vals.toString();
  }

  public void setSelected(Selection newSelect) {
    holder.setLocation(new Point(pos.x, pos.y + selections.indexOf(selected)),
        SelectionUI.getComp(selected.NAME, false, false));
    this.selected = newSelect;
    holder.setLocation(new Point(pos.x, pos.y + selections.indexOf(selected)),
        SelectionUI.getComp(selected.NAME, true, false));
  }

  public void moveUp(Object[] args) {
    int sInd = selections.indexOf(selected);
    if (sInd == 0) return;
    setSelected(selections.get(sInd - 1));
  }

  public void moveDown(Object[] args) {
    int sInd = selections.indexOf(selected);
    if (sInd == selections.size() - 1) return;
    setSelected(selections.get(sInd + 1));
  }


  public static class Selection {
    public final String NAME;
    public final Consumer<Object[]> RUNNER;
    public Object[] args;
    public Selection(String name, Consumer<Object[]> runner, Object[] args) {
      this.NAME = name;
      this.RUNNER = runner;
      this.args = args;
    }

    public void run() {
      this.RUNNER.accept(args);
    }
  }

  public static String getComp(String name, boolean selected, boolean cursorMove) {
    String rawStr = "> " + name + " <";
    return (selected ? "\033[1m" : "") + rawStr + (selected ? "\033[0m" : "") +
        (cursorMove ? "\033[1B\033[" + rawStr.length() + "D" : "");
  }

  public enum OPTION {
    SELECTED((rawStr) -> {
      return new String[]{"\033[1m", "\033[22m"};
    }),

    DISABLED((rawStr) -> {
      return new String[]{"\033[2m", "\033[22m"};
    });

    private Function<String, String[]> formatter;

    private OPTION(Function<String, String[]> formatter) {
      this.formatter = formatter;
    }
  }

  public static String[] formatArr(String rawStr, OPTION[] opts) {
    String[] vals = new String[]{"", ""};
    for (OPTION o : opts) {
      String[] codes = o.formatter.apply(rawStr);
      vals[0] += codes[0];
      vals[1] += codes[1];
    }

    return vals;
  }
  public static String format(String rawStr, OPTION[] opts) {
    String[] formArr = formatArr(rawStr, opts);
    return formArr[0] + rawStr + formArr[1];
  }
}
