package dev.gavinthomas.tictactoe.ui;

import dev.gavinthomas.tictactoe.types.UIComponent;
import dev.gavinthomas.tictactoe.types.UIHolder;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class SelectionUI implements UIComponent {
  public static final String[] uiFormatting = {"\033[1m> ", " <\033[0m"};
  private Selection selected;
  private Point pos;
  private final UIHolder holder;
  public final List<Selection> selections;

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
}
