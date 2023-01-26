package dev.gavinthomas.tictactoe.utils;

import dev.gavinthomas.tictactoe.Board.PieceType;
import dev.gavinthomas.tictactoe.TTT;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

  public int minimax(PieceType[][] board, int turns, boolean ownTurn, int alpha, int beta) {
    int evalVal = eval(board, turns);
    if (evalVal == 0 && TTT.gameOver(board)) {
      return 0;
    } else if (evalVal != 0) {
      return evalVal;
    }

    int best = (ownTurn ? -1000 : 1000);

    if (turns >= 10) return best;
    for (int x = 0; x < board.length; x++) {
      for (int y = 0; y < board[x].length; y++) {
        if (board[x][y] != PieceType.BLANK) continue;
//        System.out.println(x + ", " + y);
        board[x][y] = (ownTurn ? ownPiece : oppPiece);
        int retVal = minimax(board, turns + 1, !ownTurn, alpha, beta);
        best = (ownTurn ? Math.max(best, retVal) : Math.min(best, retVal));
        board[x][y] = PieceType.BLANK;
        if (ownTurn) {
          alpha = Math.max(alpha, best);
        } else {
          beta = Math.min(beta, best);
        }
        if (alpha >= beta) return best;
      }
    }
    return best;

  }

  public Point getBest(PieceType[][] boardArr) {
    int best = -1000;
    List<Point> bestMoves = new ArrayList<>();

    PieceType[][] board = Arrays.copyOf(boardArr, boardArr.length);

    for (int x = 0; x < board.length; x++) {
      for (int y = 0; y < board[x].length; y++) {
        if (board[x][y] != PieceType.BLANK) continue;
        board[x][y] = ownPiece;
        int move = minimax(board, 0, false, -1000, 1000);
        board[x][y] = PieceType.BLANK;
//        System.out.println(move + " > " + best);
//        System.out.print(x + ", " + y + ": " + move + " | ");
        if (move > best) {
          bestMoves.clear();
          bestMoves.add(new Point(x, y));
          best = move;
        } else if (move == best) {
          bestMoves.add(new Point(x, y));
        }
      }
    }

    return bestMoves.get((int) (Math.random() * bestMoves.size()));
  }
}
