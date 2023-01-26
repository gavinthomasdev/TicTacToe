package dev.gavinthomas.tictactoe;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dev.gavinthomas.tictactoe.input.Keybind;
import dev.gavinthomas.tictactoe.input.KeybindArgument;
import dev.gavinthomas.tictactoe.input.Keycode;
import dev.gavinthomas.tictactoe.input.input;
import dev.gavinthomas.tictactoe.opponents.Computer;
import dev.gavinthomas.tictactoe.opponents.Player;
import dev.gavinthomas.tictactoe.types.Opponent;
import dev.gavinthomas.tictactoe.Board.PieceType;
import dev.gavinthomas.tictactoe.Game;
import dev.gavinthomas.tictactoe.types.UIHolder;
import dev.gavinthomas.tictactoe.types.Visuals;
import dev.gavinthomas.tictactoe.ui.Menu;
import dev.gavinthomas.tictactoe.ui.NewGameUI;
import dev.gavinthomas.tictactoe.utils.Term;
import dev.gavinthomas.tictactoe.utils.listeners;

public class TicTacToe {
  private List<Keybind> KBS = new ArrayList<>();
  private List<Keybind> KBSDIS = new ArrayList<>();
  private Game game;
  private final List<UIHolder> UIS = new ArrayList<>();
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
    Term.watchQueue(true);
    new listeners.terminalResizeListener(this::resized);
    input.init();
    input.toggleRead(true);
    TERM.requestSize(this::updateTermSize);
//    UIS.add(new Menu());
    UIS.add(new NewGameUI());
    currentUI = UIS.get(0);
    currentUI.render();
  }

  public void newGame(boolean comp1, boolean comp2) {
    this.game = new Game(comp1, comp2);
    this.game.start();
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
    privTerm.requestSize(this::updateTermSize);
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
    privTerm.startGroup();
    privTerm.clear(true);
    privTerm.hideCursor(true);
    privTerm.saveCursor();
    privTerm.print("█▀▀▀\033[1B\033[4D█");
    privTerm.setCursorPos(x - 3, 1);
    privTerm.print("▀▀▀█\033[1B█");
    privTerm.setCursorPos(1, y - 1);
    privTerm.print("█\033[1B\033[2D█▄▄▄");
    privTerm.setCursorPos(x, y - 1);
    privTerm.print("█\033[1B\033[3D▄▄▄█");
    privTerm.restoreCursor();
    if (x < 30 || y < 10) {
      privTerm.endGroup();
      return;
    };
    privTerm.setCursorPos((int) Math.ceil((x / 2.0) - 9.5), (y / 2) - 2);
    privTerm.print(Visuals.sizeUI(x, y, 100, 32));
    privTerm.endGroup();
//    System.out.print(Visuals.sizeBorder(x, y));
  }

  public void updateTermSize(int x, int y) {
    SIZE.x = x;
    SIZE.y = y;
    if (x < 100 || y < 32) {
      if (currentUI instanceof Menu) ((Menu) currentUI).endTasks();
      this.invalidSize = true;
      TERM.enabled = false;
      for (Keybind kb : KBS) {
        kb.set.forceDisabled(true);
      }
      this.renderSizeUI(x, y);
      return;
    }

    this.invalidSize = false;
    TERM.enabled = true;
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