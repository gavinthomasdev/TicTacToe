package dev.gavinthomas.tictactoe.types;

import dev.gavinthomas.tictactoe.Board.PieceType;

public interface Opponent {
  public void getMove();

  public PieceType getPiece();
}