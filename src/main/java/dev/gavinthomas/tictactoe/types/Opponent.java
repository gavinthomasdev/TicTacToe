package dev.gavinthomas.tictactoe.types;

import dev.gavinthomas.tictactoe.Board.PieceType;

import java.awt.Point;

public interface Opponent {
  public Point getMove();

  public PieceType getPiece();
}