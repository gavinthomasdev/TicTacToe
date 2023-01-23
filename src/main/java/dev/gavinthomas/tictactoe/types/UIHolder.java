package dev.gavinthomas.tictactoe.types;

import java.awt.Point;

public interface UIHolder {

  Point offset();
  Point size();
  void render();

//  public Point getOffset();
}
