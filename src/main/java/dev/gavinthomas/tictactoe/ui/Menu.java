package dev.gavinthomas.tictactoe.ui;

import dev.gavinthomas.tictactoe.TicTacToe;
import dev.gavinthomas.tictactoe.input.*;
import dev.gavinthomas.tictactoe.types.UIComponent;
import dev.gavinthomas.tictactoe.types.UIHolder;
import dev.gavinthomas.tictactoe.types.Visuals;
import dev.gavinthomas.tictactoe.utils.Term;

import dev.gavinthomas.tictactoe.ui.SelectionUI.Selection;
import dev.gavinthomas.tictactoe.utils.listeners;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Menu implements UIHolder {
  private final Term TERM = TicTacToe.CURR.TERM;
  private final List<UIComponent> comps = new ArrayList<UIComponent>();
  private final List<Keybind> KBS = new ArrayList<Keybind>();
  private final Point offset = new Point(0, 0);
  private final Point size = new Point(90, 30);

  public Menu() {
    Selection[] sArr = {
        new Selection("New Game", this::newGameUI, new Object[]{}),
        new Selection("Load Game", this::temp, new Object[]{}),
        new Selection("Settings", this::temp, new Object[]{})
    };

//    comps.get(0).render();
    SelectionUI sui = new SelectionUI(this, sArr, new Point((size().x / 2) - 10, 10));
    comps.add(sui);
    comps.add(new Title(this, new Point((size().x / 2) - 39, 1)));
    KBS.add(new Keybind(
        new Keycode[] { Keycode.UP_ARROW },
        new Object[] { KeybindArgument.KEYCODE }, sui::moveUp));
    KBS.add(new Keybind(
        new Keycode[] { Keycode.DOWN_ARROW },
        new Object[] { KeybindArgument.KEYCODE }, sui::moveDown));
    KBS.add(new Keybind(
        new Keycode[] { Keycode.SPACE },
        new Object[] { KeybindArgument.KEYCODE }, sui::select));
    TicTacToe.CURR.registerKB(KBS);
    sui.disable(sArr[1], true);
    sui.disable(sArr[2], true);
  }

  public void endTasks() {
    for (UIComponent comp : comps) {
      comp.endTasks();
    }
  }

  public void render() {
    for (UIComponent comp : comps) {
      comp.endTasks();
    }
    TERM.startGroup();
    TERM.clear(true);
    TERM.hideCursor(true);
    Point tSize = TicTacToe.CURR.SIZE;
//    offset = new Point((tSize.x / 2) - (size().x / 2), (tSize.y / 2) - (size().y / 2));
    offset.x = (tSize.x / 2) - (size().x / 2);
    offset.y = 1;
    TERM.setCursorPos(offset.x, offset.y);
    TERM.print(Visuals.doubleLineBox(size.x, size.y));
    TERM.endGroup();

    for (UIComponent comp : comps) {
      comp.render();
    }

//    System.out.print(tempFill.repeat(15));

//    comps.get(0).render();
  }

  private void newGameUI(Object[] args) {
    endTasks();
    TicTacToe.CURR.deregisterKB(KBS);
    TicTacToe.CURR.newGameUI();
  }

  public void temp(Object[] args) {

  }

  public Point offset() {
//    return new Point(offset.x + 2, offset.y + 1);
    return new Point(offset.x + 1, offset.y + 1);
  }

  public Point size() {
//    return new Point(size.x - 4, size.y - 2);
    return new Point(size.x - 2, size.y - 2);
  }

  public void setLocation(Point pos, String str) {
    TERM.saveCursor();
    TERM.setCursorPos(pos.x, pos.y);
    System.out.print(str);
    TERM.restoreCursor();
  }
}
