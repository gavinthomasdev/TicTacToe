package dev.gavinthomas.tictactoe;

import java.awt.Point;
import java.util.Arrays;

import dev.gavinthomas.tictactoe.types.Visuals;
import dev.gavinthomas.tictactoe.utils.Term;

public class Board {
  private Point offset;
  private PieceType[] plrs = new PieceType[2];
  public final PieceType[][] grid = new PieceType[3][3];
  public volatile boolean tempGetCompMove = false;

  public Board(int xPlr) {
    this.plrs[xPlr] = PieceType.X;
    this.plrs[(xPlr == 0 ? 1 : 0)] = PieceType.O;

    for (PieceType[] arr : grid) {
      Arrays.fill(arr, PieceType.BLANK);
    }
  }

  public void setPiece(int x, int y, PieceType piece) {

    Term.saveCursor();
    Term.setCursorPos((5 + (x * 20)), (2 + (Math.abs(y - (grid.length - 1)) * 10)));

    if (piece == PieceType.X) {
      System.out.print(Visuals.XBLOCK);
    } else if (piece == PieceType.O) {
      System.out.print(Visuals.OBLOCK);
    } else {
      System.out.print(Visuals.BLANKBLOCK);
    }
    Term.restoreCursor();
  }

  public void highlightSpot(int x, int y, boolean tog) {
    Term.saveCursor();
    Term.setCursorPos((1 + (x * 20)), (1 + (Math.abs(y - (grid.length - 1)) * 10)));

    if (tog) {
      System.out.print(Visuals.HIGHLIGHT);
    } else {
      System.out.print(Visuals.UNHIGHLIGHT);
    }
    

    Term.restoreCursor();
  }

  public void render() {
    Term.clear(true);
    Term.hideCursor(true);
    System.out.println(Visuals.GRID + "\n\n");

    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        setPiece(i, j, grid[i][j]);
      }
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