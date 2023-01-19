package dev.gavinthomas.tictactoe;

import dev.gavinthomas.tictactoe.Board.PieceType;

import java.util.Arrays;

public abstract class TTT {
  public static PieceType getWinner(PieceType[][] board) {
    return TTT.getWinner(board, board.length);
  }
  public static PieceType getWinner(PieceType[][] board, int size) {
    // horizontals; x = column; y = row
    for (int y = 0; y < size; y++) {
      for (int x = 0; x < size; x++) {
        if (x == 0) {
          continue; // first value, so skip since nothing to compare to
        }
        if (board[x - 1][y] != board[x][y] || board[x][y] == PieceType.BLANK) {
          break; // not all are same, or some are blank
        }
        if (x + 1 == size) {
          return board[x][y]; // return the piece type since they would all match according to check above
        }
      }
    }

    // verticals; x = column; y = row
    for (int x = 0; x < size; x++) {
      for (int y = 0; y < size; y++) {
        if (y == 0) {
          continue;
        }
        if (board[x][y - 1] != board[x][y] || board[x][y] == PieceType.BLANK) {
          break;
        }
        if (y + 1 == size) {
          return board[x][y];
        }
      }
    }
    // diagonals; xy = column & row
//    if (size == 3) return null;
    
    // bottom left to top right
    for (int xy = 0; xy < size; xy++) {
      if (xy == 0) {
        continue;
      }
      if (board[xy - 1][xy - 1] != board[xy][xy] || board[xy][xy] == PieceType.BLANK) {
        break;
      }
      if (xy + 1 == size) {
        return board[xy][xy];
      }
    }
    // top left to bottom right; x = column; y = row
    for (int x = 0; x < size; x++) {
      int y = Math.abs(x - size + 1);
      if (x == 0) {
        continue;
      }
      if (board[x - 1][y + 1] != board[x][y] || board[x][y] == PieceType.BLANK) {
        break;
      }
      if (x + 1 == size) {
        return board[x][y];
      }
    }
    
    return null;
  }

  public static boolean gameOver(PieceType[][] board) {
    if (getWinner(board) != null) return true;

    for (PieceType[] arr : board) {
      for (PieceType piece : arr) {
        if (piece == PieceType.BLANK) {
          return false;
        }
      }
    }
    return true;
  }
}