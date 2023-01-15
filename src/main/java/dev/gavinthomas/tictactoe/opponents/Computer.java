package dev.gavinthomas.tictactoe.opponents;

import java.awt.Point;
import java.util.List;
import java.util.ArrayList;

import dev.gavinthomas.tictactoe.types.Opponent;
import dev.gavinthomas.tictactoe.Board;
import dev.gavinthomas.tictactoe.Board.PieceType;
import dev.gavinthomas.tictactoe.utils.Minimax;
import out.Out;

public class Computer implements Opponent {
  private int moves;
  public final PieceType PIECE;
  private final Board board;
  public Computer(Board board, PieceType PIECE) {
    this.board = board;
    this.PIECE = PIECE;
  }

  public PieceType getPiece() {
    return PIECE;
  }

  public Point getMove() {
//    if (board.tempGetCompMove == false) return null;
    return new Minimax(PIECE).getBest(board.grid);
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




class Move {
  public List<Computer.MoveOption> moves = new ArrayList<Computer.MoveOption>();
  public int x, y;

  public Move(int x, int y) {
    this.x = x;
    this.y = y;
  }
}


