package dev.gavinthomas.tictactoe.ui;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dev.gavinthomas.tictactoe.TicTacToe;
import dev.gavinthomas.tictactoe.types.UIComponent;
import dev.gavinthomas.tictactoe.types.UIHolder;
import dev.gavinthomas.tictactoe.types.Visuals;
import dev.gavinthomas.tictactoe.ui.GameOverMenu;
import dev.gavinthomas.tictactoe.utils.Term;

// 100x32 min size
public class Grid implements UIComponent {
  private final Term TERM = TicTacToe.CURR.TERM;
  public final PieceType[][] grid = new PieceType[3][3];
  private final UIHolder holder;
  public final Point offset = new Point(21, 1); // 50 - (58 / 2), 16 - (30 / 2)
  private final List<Point> highlights = new ArrayList<Point>();

  public Grid(UIHolder holder) {
    this.holder = holder;
    for (PieceType[] arr : grid) {
      Arrays.fill(arr, PieceType.BLANK);
    }
  }

  public void endTasks() {}

  public void setPiece(int x, int y, PieceType piece) {
    setCursorPos(5 + (x * 20), 2 + (Math.abs(y - (grid.length - 1)) * 10));

    if (piece == PieceType.X) {
      System.out.print(Visuals.XBLOCK);
    } else if (piece == PieceType.O) {
      System.out.print(Visuals.OBLOCK);
    } else {
      System.out.print(Visuals.BLANKBLOCK);
    }
  }

  public void highlightSpot(int x, int y, boolean tog) {
    if (tog && highlights.stream().noneMatch(pt -> pt.equals(new Point(x, y)))) {
      highlights.add(new Point(x, y));
    } else if (!tog) {
      highlights.removeIf(pt -> pt.equals(new Point(x, y)));
    }
    setCursorPos(1 + (x * 20), 1 + (Math.abs(y - (grid.length - 1)) * 10));

    if (tog) {
      System.out.print(Visuals.HIGHLIGHT);
    } else {
      System.out.print(Visuals.UNHIGHLIGHT);
    }
  }

  public void setCursorPos(int x, int y) {
    TERM.setCursorPos(holder.offset().x + offset.x + x, holder.offset().y + offset.y + y);
  }

  public void render() {
    TERM.clear(true);
    TERM.hideCursor(true);
    setCursorPos(0, 0);
    TERM.print(Visuals.GRID);

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