package dev.gavinthomas.tictactoe.ui;

import dev.gavinthomas.tictactoe.TicTacToe;
import dev.gavinthomas.tictactoe.input.Keybind;
import dev.gavinthomas.tictactoe.input.KeybindArgument;
import dev.gavinthomas.tictactoe.input.Keycode;
import dev.gavinthomas.tictactoe.types.UIComponent;
import dev.gavinthomas.tictactoe.types.UIHolder;
import dev.gavinthomas.tictactoe.types.Visuals;
import dev.gavinthomas.tictactoe.utils.Term;

import dev.gavinthomas.tictactoe.ui.OptionUI;
import dev.gavinthomas.tictactoe.ui.OptionUI.Option;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class NewGameUI implements UIHolder {
  private final Term TERM = TicTacToe.CURR.TERM;
  private final List<UIComponent> comps = new ArrayList<UIComponent>();
  private final List<Keybind> KBS = new ArrayList<Keybind>();
  private final Point offset = new Point(0, 0);
  private final Point size = new Point(90, 30);

  public NewGameUI() {
    Option[] opts = {
        new Option("Player 1", new String[]{"Player", "Computer"}),
        new Option("Player 2", new String[]{"Player", "Computer"})
    };

    OptionUI oui = new OptionUI(this, opts, new Point(14, 0));
    comps.add(oui);
    KBS.add(new Keybind(
        new Keycode[] { Keycode.UP_ARROW },
        new Object[] { KeybindArgument.KEYCODE }, oui::moveUp));
    KBS.add(new Keybind(
        new Keycode[] { Keycode.DOWN_ARROW },
        new Object[] { KeybindArgument.KEYCODE }, oui::moveDown));
    KBS.add(new Keybind(
        new Keycode[] { Keycode.LEFT_ARROW },
        new Object[] { KeybindArgument.KEYCODE }, oui::moveLeft));
    KBS.add(new Keybind(
        new Keycode[] { Keycode.RIGHT_ARROW },
        new Object[] { KeybindArgument.KEYCODE }, oui::moveRight));
    KBS.add(new Keybind(
        new Keycode[] { Keycode.ENTER },
        new Object[] { KeybindArgument.KEYCODE }, this::continueToGame));
    TicTacToe.CURR.registerKB(KBS);
//    oui.disable(opts[1], true);
    render();
  }

  public Point offset() {
    return new Point(offset.x + 1, offset.y + 1);
  }

  public Point size() {
    return new Point(size.x, size.y);
  }

  public void render() {
    TERM.startGroup();
    TERM.clear(true);
    TERM.hideCursor(true);
    Point tSize = TicTacToe.CURR.SIZE;
    offset.x = (tSize.x / 2) - (size().x / 2);
    offset.y = 1;
    TERM.setCursorPos(offset.x, offset.y);
    TERM.print(Visuals.doubleLineBox(size.x, size.y));
    TERM.setCursorPos(offset.x + ((size.x - 18) / 2), offset.y + size.y - 2);
    TERM.print("\033[1mContinue\033[0m: \033[3m<Enter>\033[0m");

    TERM.endGroup();

    for (UIComponent comp : comps) {
      comp.render();
    }
  }


  public void continueToGame(Object[] args) {
    OptionUI oui = (OptionUI) comps.get(0);
    TicTacToe.CURR.deregisterKB(KBS);
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
//      throw new RuntimeException(e);
    }
    TicTacToe.CURR.EXEC.submit(() -> {
      TicTacToe.CURR.newGame(oui.options.get(0).current.equals("Computer"), oui.options.get(1).current.equals("Computer"));
    });
  }
}
