package dev.gavinthomas.tictactoe.utils;

import dev.gavinthomas.tictactoe.Board.PieceType;
import dev.gavinthomas.tictactoe.TTT;

import java.awt.Point;

public class Minimax {
  private final PieceType ownPiece, oppPiece;

  public Minimax(PieceType ownPiece) {
    this.ownPiece = ownPiece;
    this.oppPiece = (ownPiece == PieceType.X ? PieceType.O : PieceType.X);
  }

  public int eval(PieceType[][] board, int turns) {
    PieceType winner = TTT.getWinner(board);
    if (winner == ownPiece) {
      return 10 - turns;
    } else if (winner == oppPiece) {
      return -10 - turns;
    } else {
      return 0;
    }
  }

  public int minimax(PieceType[][] board, int turns, boolean ownTurn) {
    int evalVal = eval(board, turns);
    if (evalVal == 0 && TTT.gameOver(board)) {
      return 0;
    } else if (evalVal != 0) {
      return evalVal;
    }

    int best = (ownTurn ? -100 : 100);

    for (int x = 0; x < board.length; x++) {
      for (int y = 0; y < board[x].length; y++) {
        if (board[x][y] != PieceType.BLANK) continue;
//        System.out.println(x + ", " + y);
        board[x][y] = (ownTurn ? ownPiece : oppPiece);
        int retVal = minimax(board, turns + 1, !ownTurn);
        best = (ownTurn ? Math.max(best, retVal) : Math.min(best, retVal));
        board[x][y] = PieceType.BLANK;
      }
    }
    return best;

  }

  public Point getBest(PieceType[][] board) {
    int best = -100;
    Point bestMove = null;

    for (int x = 0; x < board.length; x++) {
      for (int y = 0; y < board[x].length; y++) {
        if (board[x][y] != PieceType.BLANK) continue;
        board[x][y] = ownPiece;
        int move = minimax(board, 0, false);
        board[x][y] = PieceType.BLANK;
//        System.out.println(move + " > " + best);
//        System.out.print(x + ", " + y + ": " + move + " | ");
        if (move >= best) {
          bestMove = new Point(x, y);
          best = move;
        }
      }
    }

    return bestMove;
  }
}
