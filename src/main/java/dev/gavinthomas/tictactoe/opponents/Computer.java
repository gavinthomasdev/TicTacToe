package dev.gavinthomas.tictactoe.opponents;

import java.awt.Point;
import java.util.List;
import java.util.ArrayList;
import java.util.function.Consumer;

import dev.gavinthomas.tictactoe.types.Opponent;
import dev.gavinthomas.tictactoe.Board;
import dev.gavinthomas.tictactoe.Board.PieceType;
import dev.gavinthomas.tictactoe.utils.Minimax;
import out.Out;

public class Computer implements Opponent {
  private int moves;
  public final PieceType PIECE;
  public final Minimax AI;
  private final Board board;
  private final Consumer<Point> callback;
  public Computer(Board board, final PieceType PIECE, Consumer<Point> callback) {
    this.callback = callback;
    this.board = board;
    this.PIECE = PIECE;
    this.AI = new Minimax(PIECE);
  }

  public PieceType getPiece() {
    return PIECE;
  }

  public void getMove() {
//    if (board.tempGetCompMove == false) return null;
//    try {
//      Thread.sleep((long) (Math.random() * 1500) + 1000);
//    } catch (InterruptedException ignore) {}

    callback.accept(AI.getBest(board.grid));
    // return new Point();
  }

  public boolean canMove(int x, int y, MoveOption moveOp) {
    try {
      for (Point pt : moveOp.openLocs) {
        if (board.grid[x + pt.x][y + pt.y] != PieceType.BLANK) {
          return false;
        }
      }
      for (Point pt : moveOp.ownLocs) {
        if (board.grid[x + pt.x][y + pt.y] != PIECE) {
          return false;
        }
      }
    
      System.out.println(board.grid[0][0]);
      for (Point pt : moveOp.oppLocs) {
        Out.append(board.grid[x + pt.x][y + pt.y]);
        if (board.grid[x + pt.x][y + pt.y] != (PIECE == PieceType.X ? PieceType.O : PieceType.X)) {
          return false;
        }
      }
    } catch (IndexOutOfBoundsException e) {
      // System.out.println("IOBE");
      return false;
    }
    return true;
  }



  public static class MoveOption {
    public int priority;
    public List<Point> openLocs = new ArrayList<Point>();
    public List<Point> ownLocs = new ArrayList<Point>();
    public List<Point> oppLocs = new ArrayList<Point>();

    public MoveOption(int priority) {
      this.priority = priority;
    }

    public MoveOption open(int x, int y) {
      this.openLocs.add(new Point(x, y));
      return this;
    }
    
    public MoveOption own(int x, int y) {
      this.ownLocs.add(new Point(x, y));
      return this;
    }
    
    public MoveOption opp(int x, int y) {
      this.oppLocs.add(new Point(x, y));
      return this;
    }
  }
}


