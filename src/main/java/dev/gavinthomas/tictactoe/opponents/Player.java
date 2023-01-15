package dev.gavinthomas.tictactoe.opponents;

import java.awt.Point;

import dev.gavinthomas.tictactoe.types.Opponent;
import dev.gavinthomas.tictactoe.input.input;
import dev.gavinthomas.tictactoe.input.Keycode;
import dev.gavinthomas.tictactoe.Board;
import dev.gavinthomas.tictactoe.Board.PieceType;

public class Player implements Opponent {
  private final Board board;
  private final Point selectedSpot = new Point(0, 0);
  private volatile boolean canMove = false;
  private volatile boolean tempCompTest = false;
  public final PieceType PIECE;
  
  public Player(Board board, PieceType PIECE) {
    this.board = board;
    this.PIECE = PIECE;
  }

  public PieceType getPiece() {
    return PIECE;
  }

  public void handleInput(Object[] args) {
    if (!canMove) return;
    Keycode key = (Keycode) args[0];
    if (key == Keycode.UP_ARROW && selectedSpot.y != 0) {
      board.highlightSpot(selectedSpot.x, selectedSpot.y, false);
      board.highlightSpot(selectedSpot.x, selectedSpot.y - 1, true);
      selectedSpot.translate(0, -1);
    } else if (key == Keycode.DOWN_ARROW && selectedSpot.y != 2) {
      board.highlightSpot(selectedSpot.x, selectedSpot.y, false);
      board.highlightSpot(selectedSpot.x, selectedSpot.y + 1, true);
      selectedSpot.translate(0, 1);
    } else if (key == Keycode.LEFT_ARROW && selectedSpot.x != 0) {
      board.highlightSpot(selectedSpot.x, selectedSpot.y, false);
      board.highlightSpot(selectedSpot.x - 1, selectedSpot.y, true);
      selectedSpot.translate(-1, 0);
    } else if (key == Keycode.RIGHT_ARROW && selectedSpot.x != 2) {
      board.highlightSpot(selectedSpot.x, selectedSpot.y, false);
      board.highlightSpot(selectedSpot.x + 1, selectedSpot.y, true);
      selectedSpot.translate(1, 0);
    } else if (key == Keycode.SPACE && board.grid[selectedSpot.x][selectedSpot.y] == PieceType.BLANK) {
    // } else if (key == Keycode.SPACE) {
      // System.out.println(selectedSpot.x + ", " + selectedSpot.y);
      this.canMove = false;
    } else if (key == Keycode.LOWER_L) {
      board.tempGetCompMove = true;
      this.tempCompTest = true;
      this.canMove = false;
    }
  }

  public Point getMove() {
    board.highlightSpot(selectedSpot.x, selectedSpot.y, true);
    this.canMove = true;
    input.toggleRead(true);
    while (canMove) {
      Thread.onSpinWait();
    }
    
    if (this.tempCompTest) {
      this.tempCompTest = false;
      return null;
    }
    input.toggleRead(false);
    board.highlightSpot(selectedSpot.x, selectedSpot.y, false);
    return this.selectedSpot;
    // return new Point();
  }
}