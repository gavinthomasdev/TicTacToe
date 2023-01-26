package dev.gavinthomas.tictactoe.ui;

import dev.gavinthomas.tictactoe.TicTacToe;
import dev.gavinthomas.tictactoe.types.UIComponent;
import dev.gavinthomas.tictactoe.types.UIHolder;
import dev.gavinthomas.tictactoe.types.Visuals;
import dev.gavinthomas.tictactoe.utils.Term;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class OptionUI implements UIComponent {
  private final Term TERM = TicTacToe.CURR.TERM;
  private Option selected;
  private final Point pos;
  private final UIHolder holder;
  public final List<Option> options;

  public OptionUI(UIHolder holder, Option[] options, Point pos) {
    this.holder = holder;
    this.options = Arrays.asList(options);
    this.pos = pos;
  }
  public void render() {
    TERM.startGroup();
    setCursorPos(pos.x, pos.y);
    TERM.print(getRender());
    TERM.endGroup();
    if (selected == null) {
      setSelected(options.get(findNotDisabled(-1, true)));
    }
  }
  public void endTasks() {}

  public String getRender() {
    StringBuilder vals = new StringBuilder();
    for (Option o : options) {
      vals.append(getComp(o, options.indexOf(o) != options.size() - 1));
    }
    return vals.toString();
  }

  public void disable(Option val, boolean tog) {
    if (selected != val) {
      val.mode = (tog ? MODE.DISABLED : MODE.NORMAL);
      return;
    }
    int thisIndex = options.indexOf(val);
    int newIndex = (findNotDisabled(thisIndex, false) != -1 ?
        findNotDisabled(thisIndex, false) :
        findNotDisabled(thisIndex, true));
    val.mode = MODE.DISABLED;
    setSelected(options.get(newIndex));
  }

  public void setCursorPos(int x, int y) {
    TERM.setCursorPos(holder.offset().x + x, holder.offset().y + y);
  }

  public void setSelected(Option newSelect) {
    if (selected != null) {
      TERM.startGroup();
      setCursorPos(pos.x, pos.y + (options.indexOf(selected) * 3));
      selected.mode = (selected.mode == MODE.SELECTED ? MODE.NORMAL : selected.mode);
      TERM.print(getComp(selected, false));
      TERM.endGroup();
    }
    this.selected = newSelect;
    TERM.startGroup();
    setCursorPos(pos.x, pos.y + (options.indexOf(selected) * 3));
    newSelect.mode = MODE.SELECTED;
    TERM.print(getComp(selected, false));
    TERM.endGroup();
  }

  public void moveUp(Object[] args) {
    int sInd = options.indexOf(selected);
    int next = findNotDisabled(options.indexOf(selected), false);
    if (next == -1) return;
    setSelected(options.get(next));
  }

  public void moveDown(Object[] args) {
    int sInd = options.indexOf(selected);
    int next = findNotDisabled(options.indexOf(selected), true);
    if (next == -1) return;
    setSelected(options.get(next));
  }

  public void moveLeft(Object[] args) {
    if (selected == null) return;
    selected.moveLeft();
    setCursorPos(pos.x, pos.y + (options.indexOf(selected) * 3));
    TERM.print(getComp(selected, false));
  }

  public void moveRight(Object[] args) {
    if (selected == null) return;
    selected.moveRight();
    setCursorPos(pos.x, pos.y + (options.indexOf(selected) * 3));
    TERM.print(getComp(selected, false));
  }


  private int findNotDisabled(int start, boolean posIncrement) {
    if (posIncrement) {
      for (int i = start + 1; i < options.size(); i++) {
        if (options.get(i).mode != MODE.DISABLED) return i;
      }
    } else {
      for (int i = start - 1; i >= 0; i--) {
        if (options.get(i).mode != MODE.DISABLED) return i;
      }
    }
    return -1;
  }

  public static class Option {
    public final String NAME;
    public final List<String> CHOICES;
    public String current;
    public boolean disabled = false;
    public MODE mode = MODE.NORMAL;

    public Option(String name, String[] choices) {
      this.NAME = name;
      this.CHOICES = Arrays.asList(choices);
      this.current = choices[0];
    }

    public boolean canMoveLeft() {
      return CHOICES.indexOf(current) != 0;
    }
    public boolean canMoveRight() {
      return CHOICES.indexOf(current) != CHOICES.size() - 1;
    }

    public void moveLeft() {
      if (!canMoveLeft()) return;
      current = CHOICES.get(CHOICES.indexOf(current) - 1);
    }
    public void moveRight() {
      if (!canMoveRight()) return;
      current = CHOICES.get(CHOICES.indexOf(current) + 1);
    }
  }

  public static String getComp(Option opt, boolean cursorMove) {
    return Visuals.optionUI(opt) + (cursorMove ? "\033[1B\033[60D" : "");
  }

  public enum MODE {
    DISABLED, NORMAL, SELECTED
  }
}
