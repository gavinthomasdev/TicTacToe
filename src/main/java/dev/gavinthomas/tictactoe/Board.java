package dev.gavinthomas.tictactoe;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dev.gavinthomas.tictactoe.types.UIHolder;
import dev.gavinthomas.tictactoe.types.Visuals;
import dev.gavinthomas.tictactoe.utils.Term;

// 100x34 min size
public class Board implements UIHolder {
  private final Term TERM = TicTacToe.CURR.TERM;
  private Point offset;
  private PieceType[] plrs = new PieceType[2];
  public final PieceType[][] grid = new PieceType[3][3];
  public volatile boolean tempGetCompMove = false;
  private final Point brdOffset = new Point(21, 0);
  private final List<Point> highlights = new ArrayList<Point>();

  public Board(int xPlr) {
    this.plrs[xPlr] = PieceType.X;
    this.plrs[(xPlr == 0 ? 1 : 0)] = PieceType.O;

    for (PieceType[] arr : grid) {
      Arrays.fill(arr, PieceType.BLANK);
    }
  }

  public Point offset() {
    return new Point();
  }

  public Point size() {
    return new Point();
  }

  public void setPiece(int x, int y, PieceType piece) {
    if (TicTacToe.CURR.invalidSize) return;
    TERM.saveCursor();
    TERM.setCursorPos(TicTacToe.OFFSET.x + brdOffset.x + (5 + (x * 20)),
        TicTacToe.OFFSET.y + (2 + (Math.abs(y - (grid.length - 1)) * 10)));

    if (piece == PieceType.X) {
      System.out.print(Visuals.XBLOCK);
    } else if (piece == PieceType.O) {
      System.out.print(Visuals.OBLOCK);
    } else {
      System.out.print(Visuals.BLANKBLOCK);
    }
    TERM.restoreCursor();
  }

  public void highlightSpot(int x, int y, boolean tog) {
    if (tog && highlights.stream().noneMatch(pt -> pt.equals(new Point(x, y)))) {
      highlights.add(new Point(x, y));
    } else if (!tog) {
      highlights.removeIf(pt -> pt.equals(new Point(x, y)));
    }
    if (TicTacToe.CURR.invalidSize) return;
    TERM.saveCursor();
    TERM.setCursorPos(TicTacToe.OFFSET.x + brdOffset.x + (1 + (x * 20)),
        TicTacToe.OFFSET.y + (1 + (Math.abs(y - (grid.length - 1))
            * 10)));

    if (tog) {
      System.out.print(Visuals.HIGHLIGHT);
    } else {
      System.out.print(Visuals.UNHIGHLIGHT);
    }
    

    TERM.restoreCursor();
  }

  public void render() {
    if (TicTacToe.CURR.invalidSize) return;
    TERM.clear(true);
    TERM.hideCursor(true);
    System.out.println("\n".repeat(TicTacToe.OFFSET.y) +
        " ".repeat(TicTacToe.OFFSET.x) + " ".repeat(brdOffset.x) +
        Visuals.GRID + "\n\n");

    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        setPiece(i, j, grid[i][j]);
      }
    }
    for (int i = 0; i < highlights.size(); i++) {
      Point pt = highlights.get(i);
      highlights.remove(i);
      highlightSpot(pt.x, pt.y, true);
    }

//    highlightSpot(0, 0, true);
  }

  public PieceType getPiece(int x, int y) {
    if (x > 2 || x < 0 || y > 2 || y < 0) return null;
    return grid[x][y];
  }
  
  public enum PieceType {
    X, O, BLANK
  }
}