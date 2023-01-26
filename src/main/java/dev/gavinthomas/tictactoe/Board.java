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
  private final Point offset = new Point(0, 1);
  private final Point size = new Point(96, 30);
  public final PieceType[][] grid = new PieceType[3][3];
  public volatile boolean tempGetCompMove = false;
  private final Point brdOffset = new Point(21, 0);
  private final List<Point> highlights = new ArrayList<Point>();

  public Board() {
    for (PieceType[] arr : grid) {
      Arrays.fill(arr, PieceType.BLANK);
    }
  }

  public Point offset() {
    return new Point(offset.x, offset.y);
  }

  public Point size() {
    return new Point(size.x, size.y);
  }

  public void setPiece(int x, int y, PieceType piece) {
    TERM.startGroup();
    TERM.saveCursor();
    TERM.setCursorPos(brdOffset().x + (4 + (x * 20)),
        brdOffset().y + (1 + (Math.abs(y - (grid.length - 1)) * 10)));
//    TERM.setCursorPos(brdOffset().x + (5 + (x * 20)),
//        brdOffset().y + (2 + (Math.abs(y - (grid.length - 1)) * 10)));

    if (piece == PieceType.X) {
      TERM.print(Visuals.XBLOCK);
    } else if (piece == PieceType.O) {
      TERM.print(Visuals.OBLOCK);
    } else {
      TERM.print(Visuals.BLANKBLOCK);
    }
    TERM.restoreCursor();
    TERM.endGroup();
  }

  public void highlightSpot(int x, int y, boolean tog) {
    if (tog && highlights.stream().noneMatch(pt -> pt.equals(new Point(x, y)))) {
      highlights.add(new Point(x, y));
    } else if (!tog) {
      highlights.removeIf(pt -> pt.equals(new Point(x, y)));
    }
    TERM.startGroup();
    TERM.saveCursor();
    TERM.setCursorPos(brdOffset().x + (x * 20),
        brdOffset().y + (Math.abs(y - (grid.length - 1)) * 10));
//    TERM.setCursorPos(brdOffset().x + (1 + (x * 20)),
//        brdOffset().y + (1 + (Math.abs(y - (grid.length - 1)) * 10)));

    if (tog) {
      TERM.print(Visuals.HIGHLIGHT);
    } else {
      TERM.print(Visuals.UNHIGHLIGHT);
    }

    TERM.restoreCursor();
    TERM.endGroup();
  }

  private Point brdOffset() {
    return new Point(offset.x + ((size.x - 58) / 2), offset.y + ((size.y - 29) / 2));
  }

  public void render() {
    offset.x = (TicTacToe.CURR.SIZE.x - size.x) / 2;
    offset.y = (TicTacToe.CURR.SIZE.y - size.y) / 2;

    TERM.startGroup();
    TERM.clear(true);
    TERM.hideCursor(true);
    TERM.setCursorPos(brdOffset().x, brdOffset().y);
    TERM.print(Visuals.GRID);
    TERM.endGroup();

    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        setPiece(i, j, grid[i][j]);
      }
    }
    for (Point pt : highlights) {
      highlights.remove(pt);
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