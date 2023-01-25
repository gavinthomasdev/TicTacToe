package dev.gavinthomas.tictactoe;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dev.gavinthomas.tictactoe.types.UIHolder;
import dev.gavinthomas.tictactoe.types.Visuals;
import dev.gavinthomas.tictactoe.ui.GameOverMenu;
import dev.gavinthomas.tictactoe.utils.Term;

// 100x34 min size
public class Board implements UIHolder {
  private final Term TERM = TicTacToe.CURR.TERM;
  public final PieceType[][] grid = new PieceType[3][3];
  private final Point offset = new Point(0, 0);
  private final Point size = new Point(96, 30);
  private final Point brdOffset = new Point(21, 0);
  private final List<Point> highlights = new ArrayList<Point>();
  public final GameOverMenu OVERMENU = new GameOverMenu();

  public Board() {
    for (PieceType[] arr : grid) {
      Arrays.fill(arr, PieceType.BLANK);
    }
  }

  public Point offset() {
    return new Point();
  }

  public Point size() {
    return new Point(100, 32);
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
//    if (TicTacToe.CURR.invalidSize) return;
    TERM.clear(true);
    TERM.hideCursor(true);
    TERM.setCursorPos(TicTacToe.OFFSET.x + brdOffset.x, TicTacToe.OFFSET.y + brdOffset.y);
    TERM.print(Visuals.GRID);
    offset.x = (TicTacToe.CURR.SIZE.x - size.x) / 2;
    offset.y = (TicTacToe.CURR.SIZE.y - size.y) / 2;

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

  public void setOverMode(boolean tog) {
    if (tog) {

    }
  }

  public enum PieceType {
    X, O, BLANK
  }
}