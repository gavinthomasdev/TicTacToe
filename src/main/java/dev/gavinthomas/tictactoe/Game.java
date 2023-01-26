package dev.gavinthomas.tictactoe;

import dev.gavinthomas.tictactoe.input.Keybind;
import dev.gavinthomas.tictactoe.input.KeybindArgument;
import dev.gavinthomas.tictactoe.input.Keycode;
import dev.gavinthomas.tictactoe.input.input;
import dev.gavinthomas.tictactoe.opponents.Computer;
import dev.gavinthomas.tictactoe.opponents.Player;
import dev.gavinthomas.tictactoe.types.Opponent;
import dev.gavinthomas.tictactoe.Board;
import dev.gavinthomas.tictactoe.Board.PieceType;
import dev.gavinthomas.tictactoe.TTT;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Game {
  private final Board board;
  private final Opponent[] plrs = new Opponent[2];
  private final List<Keybind> KBS = new ArrayList<Keybind>();
  private int currentTurn = 0;
  private volatile boolean finished = false;
  private volatile boolean quit = false;
  private Keybind inputKB;

  public Game(boolean plr1Comp, boolean plr2Comp) {
    this.board = new Board();
    if (plr1Comp) {
      plrs[0] = new Computer(this.board, PieceType.X, this::handleMove);
    } else {
      plrs[0] = new Player(this.board, PieceType.X, this::handleMove);
    }
    if (plr2Comp) {
      plrs[1] = new Computer(this.board, PieceType.O, this::handleMove);
    } else {
      plrs[1] = new Player(this.board, PieceType.O, this::handleMove);
    }
  }

  public void start() {
    if (plrs[0] instanceof Player) {
      KBS.add(new Keybind(
          new Keycode[]{Keycode.UP_ARROW, Keycode.DOWN_ARROW, Keycode.LEFT_ARROW, Keycode.RIGHT_ARROW, Keycode.SPACE, Keycode.LOWER_L},
          new Object[]{KeybindArgument.KEYCODE}, ((Player) plrs[0])::handleInput));
    }
    if (plrs[1] instanceof Player) {
      KBS.add(new Keybind(
          new Keycode[] { Keycode.UP_ARROW, Keycode.DOWN_ARROW, Keycode.LEFT_ARROW, Keycode.RIGHT_ARROW, Keycode.SPACE, Keycode.LOWER_L },
          new Object[] { KeybindArgument.KEYCODE }, ((Player) plrs[1])::handleInput));
    }
    KBS.add(new Keybind(
        new Keycode[] { Keycode.LOWER_Q },
        new Object[] { KeybindArgument.KEYCODE }, this::quit));
    render();
//    input.toggleRead(true);

//    input.addBinds(KBS);
    TicTacToe.CURR.registerKB(KBS);
    plrs[currentTurn].getMove();
//    while (!TTT.gameOver(board.grid)) {
    while (!quit) {
      Thread.onSpinWait();
    }

//    System.out.println(TTT.getWinner(board.grid));

//    input.removeBinds(KBS);
    TicTacToe.CURR.deregisterKB(KBS);
  }

  public void render() {
    this.board.render();
  }

  public void handleMove(Point pt) {
    if (finished || quit) return;
    board.setPiece(pt.x, pt.y, plrs[currentTurn].getPiece());
    board.grid[pt.x][pt.y] = plrs[currentTurn].getPiece();
//    System.out.println(TTT.gameOver(board.grid));
    if (TTT.gameOver(board.grid)) {
      finished = true;
      return;
    }
    currentTurn = (currentTurn == 0 ? 1 : 0);
    if (plrs[0] instanceof Player && plrs[1] instanceof Player) {
      ((Player) plrs[currentTurn]).getMove(pt.x, pt.y);
    } else {
      plrs[currentTurn].getMove();
    }
  }


  public void quit(Object[] args) {
    if (!finished && (plrs[currentTurn] instanceof Player p)) {
      p.canMove = false;
      board.highlightSpot(p.selectedSpot.x, p.selectedSpot.y, false);
    };
    finished = quit = true;
    TicTacToe.CURR.deregisterKB(KBS);
    TicTacToe.CURR.menu();
  }

  public void input(Object[] args) {
    if (plrs[currentTurn] instanceof Player tempPlr) {
      tempPlr.handleInput(args);
    }
  }
}
