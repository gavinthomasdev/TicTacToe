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

public class Game {
  private final Board board;
  private final Opponent[] plrs = new Opponent[2];
  private int currentTurn;
  private Keybind inputKB;

  public Game(Builder config) {
    this.board = new Board(config.firstMove);
    currentTurn = config.firstMove;
    plrs[0] = new Player(this.board, (config.firstMove == 0 ? PieceType.X : PieceType.O));
    plrs[1] = (config.computer ? new Computer(this.board, (config.firstMove == 1 ? PieceType.X : PieceType.O)) : new Player(this.board, (config.firstMove == 1 ? PieceType.X : PieceType.O)));
  }

  public void start() {
    inputKB = new Keybind(
        new Keycode[] { Keycode.UP_ARROW, Keycode.DOWN_ARROW, Keycode.LEFT_ARROW, Keycode.RIGHT_ARROW, Keycode.SPACE, Keycode.LOWER_L },
        new Object[] { KeybindArgument.KEYCODE }, this::input);
    input.addBinds(inputKB);
    this.board.render();
    while (!TTT.gameOver(board.grid)) {
      Point pt = plrs[currentTurn].getMove();

//      if (pt == null) {
//        currentTurn = (currentTurn == 0 ? 1 : 0);
//        continue;
//      }
      // System.out.println(pt.x + ", " + pt.y);
      board.setPiece(pt.x, pt.y, plrs[currentTurn].getPiece());
      board.grid[pt.x][pt.y] = plrs[currentTurn].getPiece();
      currentTurn = (currentTurn == 0 ? 1 : 0);
    }

    input.removeBinds(inputKB);
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
