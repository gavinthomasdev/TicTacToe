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
  private int currentTurn;
  private volatile boolean finished = false;
  private Keybind inputKB;

  public Game(Builder config) {
    this.board = new Board(config.firstMove);
    currentTurn = config.firstMove;
    plrs[0] = new Player(this.board, (config.firstMove == 0 ? PieceType.X : PieceType.O), this::handleMove);
    if (config.computer) {
      plrs[1] = new Computer(this.board, (config.firstMove == 1 ? PieceType.X : PieceType.O), this::handleMove);
    } else {
      plrs[1] = new Player(this.board, (config.firstMove == 1 ? PieceType.X : PieceType.O), this::handleMove);
    }
  }

  public void start() {
    KBS.add(new Keybind(
        new Keycode[] { Keycode.UP_ARROW, Keycode.DOWN_ARROW, Keycode.LEFT_ARROW, Keycode.RIGHT_ARROW, Keycode.SPACE, Keycode.LOWER_L },
        new Object[] { KeybindArgument.KEYCODE }, ((Player) plrs[0])::handleInput));
    if (plrs[1] instanceof Player) {
      KBS.add(new Keybind(
          new Keycode[] { Keycode.UP_ARROW, Keycode.DOWN_ARROW, Keycode.LEFT_ARROW, Keycode.RIGHT_ARROW, Keycode.SPACE, Keycode.LOWER_L },
          new Object[] { KeybindArgument.KEYCODE }, ((Player) plrs[1])::handleInput));
    }
    this.board.render();
    input.toggleRead(true);

    input.addBinds(KBS);

    plrs[currentTurn].getMove();
//    while (!TTT.gameOver(board.grid)) {
    while (!finished) {
      Thread.onSpinWait();
    }

    System.out.println(TTT.getWinner(board.grid));

//    input.removeBinds(inputKB);
  }

  public void handleMove(Point pt) {
    board.setPiece(pt.x, pt.y, plrs[currentTurn].getPiece());
    board.grid[pt.x][pt.y] = plrs[currentTurn].getPiece();
//    System.out.println(TTT.gameOver(board.grid));
    if (TTT.gameOver(board.grid)) {
      finished = true;
      return;
    }
    currentTurn = (currentTurn == 0 ? 1 : 0);
    plrs[currentTurn].getMove();
  }


  public void input(Object[] args) {
    if (plrs[currentTurn] instanceof Player tempPlr) {
      tempPlr.handleInput(args);
    }
  }

  public static class Builder {
    private boolean computer = true; // playing against computer
    private int firstMove = (int) (2 * Math.random());

    public Builder computerOpponent() {
      this.computer = true;
      return this;
    }

    public Builder playerOpponent() {
      this.computer = false;
      return this;
    }

    public Builder firstMove(int plr) {
      if (plr != 0 && plr != 1)
        return this;
      this.firstMove = plr;
      return this;
    }

    public Game build() {
      return new Game(this);
    }
  }
}
