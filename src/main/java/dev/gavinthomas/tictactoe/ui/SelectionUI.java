package dev.gavinthomas.tictactoe.ui;

import dev.gavinthomas.tictactoe.TicTacToe;
import dev.gavinthomas.tictactoe.types.UIComponent;
import dev.gavinthomas.tictactoe.types.UIHolder;
import dev.gavinthomas.tictactoe.types.Visuals;
import dev.gavinthomas.tictactoe.utils.Term;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class SelectionUI implements UIComponent {
  private final Term TERM = TicTacToe.CURR.TERM;
  private Selection selected;
  private final Point pos;
  private final UIHolder holder;
  public final List<Selection> selections;

  public SelectionUI(UIHolder holder, Selection[] selections, Point pos) {
    this.selections = Arrays.asList(selections);
    this.pos = pos;
    this.holder = holder;
//    this.setSelected(this.selections.get(findNotDisabled(-1, true)));
  }

  public void render() {
    TERM.startGroup();
    setCursorPos(pos.x, pos.y);
    TERM.print(getRender());
    TERM.endGroup();
    if (selected == null) {
      setSelected(selections.get(findNotDisabled(-1, true)));
    }
  }

  public void endTasks() {}

  public String getRender() {
    StringBuilder vals = new StringBuilder();
    for (Selection s : selections) {
      vals.append(SelectionUI.getComp(s.NAME, s.mode, selections.indexOf(s) != selections.size() - 1));
    }
    return vals.toString();
  }

  public void disable(Selection val, boolean tog) {
    if (selected != val) {
      val.mode = (tog ? MODE.DISABLED : MODE.NORMAL);
      return;
    }
    int thisIndex = selections.indexOf(val);
    int newIndex = (findNotDisabled(thisIndex, false) != -1 ?
        findNotDisabled(thisIndex, false) :
        findNotDisabled(thisIndex, true));
    val.mode = MODE.DISABLED;
    setSelected(selections.get(newIndex));
  }

  public void setCursorPos(int x, int y) {
    TERM.setCursorPos(holder.offset().x + x, holder.offset().y + y);
  }

  public void setSelected(Selection newSelect) {
    if (selected != null) {
      TERM.startGroup();
      setCursorPos(pos.x, pos.y + (selections.indexOf(selected) * 3));
      TERM.print(SelectionUI.getComp(selected.NAME,
          (selected.mode == MODE.SELECTED ? MODE.NORMAL : MODE.DISABLED),
          false));
      selected.mode = (selected.mode == MODE.SELECTED ? MODE.NORMAL : selected.mode);
      TERM.endGroup();
    }
    this.selected = newSelect;
    TERM.startGroup();
    setCursorPos(pos.x, pos.y + (selections.indexOf(selected) * 3));
    TERM.print(SelectionUI.getComp(selected.NAME, MODE.SELECTED, false));
    TERM.endGroup();
    newSelect.mode = MODE.SELECTED;
  }

  public void moveUp(Object[] args) {
    int sInd = selections.indexOf(selected);
    int next = findNotDisabled(selections.indexOf(selected), false);
    if (next == -1) return;
    setSelected(selections.get(next));
  }

  public void moveDown(Object[] args) {
    int sInd = selections.indexOf(selected);
    int next = findNotDisabled(selections.indexOf(selected), true);
    if (next == -1) return;
    setSelected(selections.get(next));
  }

  public void select(Object[] args) {
    if (selected == null) return;
    selected.RUNNER.accept(selected.args);
  }

  private int findNotDisabled(int start, boolean posIncrement) {
    if (posIncrement) {
      for (int i = start + 1; i < selections.size(); i++) {
        if (selections.get(i).mode != MODE.DISABLED) return i;
      }
    } else {
      for (int i = start - 1; i >= 0; i--) {
        if (selections.get(i).mode != MODE.DISABLED) return i;
      }
    }
    return -1;
  }


  public static class Selection {
    public final String NAME;
    public final Consumer<Object[]> RUNNER;
    public boolean disabled = false;
    public MODE mode = MODE.NORMAL;
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

  public static String getComp(String name, MODE mode, boolean cursorMove) {
    return Visuals.menuButton(name, mode) + (cursorMove ? "\033[1B\033[20D" : "");
  }

  public enum MODE {
    DISABLED, NORMAL, SELECTED
  }
}
