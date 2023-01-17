package dev.gavinthomas.tictactoe.ui;

import dev.gavinthomas.tictactoe.input.*;
import dev.gavinthomas.tictactoe.types.UIComponent;
import dev.gavinthomas.tictactoe.types.UIHolder;
import dev.gavinthomas.tictactoe.utils.Term;

import dev.gavinthomas.tictactoe.ui.SelectionUI.Selection;
import dev.gavinthomas.tictactoe.utils.listeners;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Menu implements UIHolder {
  private final String tempFill = "".repeat(100) + "\n";
  private final List<UIComponent> comps = new ArrayList<UIComponent>();
  private long lastResizeCheck = 0;

  public Menu() {
    new listeners.terminalResizeListener(this::resized);
    input.init();
    Selection[] sArr = {
        new Selection("S1", this::temp, new Object[]{}),
        new Selection("S1", this::temp, new Object[]{}),
        new Selection("S1", this::temp, new Object[]{})
    };
    comps.add(new SelectionUI(this, sArr, new Point(3, 1)));
//    comps.get(0).render();
    SelectionUI sui = (SelectionUI) comps.get(0);
    Keybind upKB = new Keybind(
        new Keycode[] { Keycode.UP_ARROW },
        new Object[] { KeybindArgument.KEYCODE }, sui::moveUp);
    Keybind downKB = new Keybind(
        new Keycode[] { Keycode.DOWN_ARROW },
        new Object[] { KeybindArgument.KEYCODE }, sui::moveDown);
    input.addBinds(upKB, downKB);
  }

  public void resized() {
//    System.out.println(input.exec.isTerminated());

//    if (System.currentTimeMillis() - lastResizeCheck < 1000) return;
    System.out.print("\033[s\033[50000;50000H\033[6n\033[u");
    input.queue.add(new InputQueue.TermSize.Builder(this::logNew).args(new Object[]{InputQueue.ArgType.CODES, InputQueue.ArgType.TERMSIZE}).build());
    System.out.println("resize");
    lastResizeCheck = System.currentTimeMillis();
  }

  public void logNew(Object[] args) {
    System.out.println(args[1]);
  }

  public void render() {
    Term.clear(true);
    Term.hideCursor(true);

    System.out.print(tempFill.repeat(15));

    comps.get(0).render();
  }

  public void temp(Object[] args) {

  }

  public void setLocation(Point pos, String str) {
    Term.saveCursor();
    Term.setCursorPos(pos.x, pos.y);
    System.out.print(str);
    Term.restoreCursor();
  }
}
