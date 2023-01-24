package dev.gavinthomas.tictactoe;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dev.gavinthomas.tictactoe.input.*;
import dev.gavinthomas.tictactoe.opponents.Computer;
import dev.gavinthomas.tictactoe.opponents.Player;
import dev.gavinthomas.tictactoe.types.Opponent;
import dev.gavinthomas.tictactoe.Board.PieceType;
import dev.gavinthomas.tictactoe.Game;
import dev.gavinthomas.tictactoe.types.UIHolder;
import dev.gavinthomas.tictactoe.types.Visuals;
import dev.gavinthomas.tictactoe.ui.Menu;
import dev.gavinthomas.tictactoe.utils.Term;
import dev.gavinthomas.tictactoe.utils.listeners;

public class TicTacToe {
  private List<Keybind> KBS = new ArrayList<>();
  private List<Keybind> KBSDIS = new ArrayList<>();
  private Game game;
  private UIHolder currentUI;
  private Keybind menuKBS;
  public boolean invalidSize = true;
  public final Term TERM = new Term();
  private final Term privTerm = new Term();
  public static TicTacToe CURR;
  public final Point SIZE = new Point(0, 0);
  public static final Point OFFSET = new Point(0, 0);

  public TicTacToe() {
    TicTacToe.CURR = this;
    this.init();
  }
  public void init() {
    new listeners.terminalResizeListener(this::resized);
    input.init();
    input.toggleRead(true);
    TERM.requestSize(this::updateTermSize);
    menuKBS = new Keybind(
        new Keycode[] { Keycode.UP_ARROW, Keycode.DOWN_ARROW, Keycode.LEFT_ARROW, Keycode.RIGHT_ARROW, Keycode.SPACE, Keycode.LOWER_L },
        new Object[] { KeybindArgument.KEYCODE }, this::input);
    input.addBinds(menuKBS);
    currentUI = new Menu();
    currentUI.render();
  }

  public void newGame(Game.Builder gameConfig) {
    menuKBS.set.enabled(false);
    this.game = new Game(gameConfig);
    this.game.start();
    menuKBS.set.enabled(true);
  }

  public void registerKB(Keybind kb) {
    KBS.add(kb);
    input.addBinds(kb);
  }

  public void registerKB(List<Keybind> kb) {
    KBS.addAll(kb);
    input.addBinds(kb);
  }

  public void deregisterKB(List<Keybind> kb) {
    KBS.removeAll(kb);
    input.removeBinds(kb);
  }

  public void resized() {
    TERM.requestSize(this::updateTermSize);
  }

  public void render() {
    if (game != null) {
      game.render();
      return;
    }
    if (currentUI == null) return;
    currentUI.render();
  }


  public void renderSizeUI(int x, int y) {
    TERM.clear(true);
    TERM.hideCursor(true);
    TERM.saveCursor();
    System.out.print("█▀▀▀\033[1B\033[4D█");
    TERM.setCursorPos(x - 3, 1);
    System.out.print("▀▀▀█\033[1B█");
    TERM.setCursorPos(1, y - 1);
    System.out.print("█\033[1B\033[2D█▄▄▄");
    TERM.setCursorPos(x, y - 1);
    System.out.print("█\033[1B\033[3D▄▄▄█");
    TERM.restoreCursor();
    if (x < 30 || y < 10) return;
    TERM.setCursorPos((int) Math.ceil((x / 2.0) - 9.5), (y / 2) - 2);
    System.out.print(Visuals.sizeUI(x, y, 100, 32));
//    System.out.print(Visuals.sizeBorder(x, y));
  }

  public void updateTermSize(int x, int y) {
    SIZE.x = x;
    SIZE.y = y;
    if (x < 100 || y < 32) {
      if (currentUI instanceof Menu) ((Menu) currentUI).endTasks();
      this.invalidSize = true;
      for (Keybind kb : KBS) {
        kb.set.forceDisabled(true);
      }
      this.renderSizeUI(x, y);
      return;
    }

    this.invalidSize = false;
    for (Keybind kb : KBS) {
      kb.set.forceDisabled(false);
    }
    TicTacToe.OFFSET.x = (x - 100) / 2;
    TicTacToe.OFFSET.y = (y - 32) / 2;
    this.render();
  }

  public void updateTermSize(Object[] args) {
//    System.out.println("args1");
    if (!(args[0] instanceof Point)) return;
//    System.out.println("args2");
    updateTermSize(((Point) args[0]).x, ((Point) args[0]).y);
  }

  public void input(Object[] args) {

  }
}