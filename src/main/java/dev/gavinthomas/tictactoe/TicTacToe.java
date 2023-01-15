package dev.gavinthomas.tictactoe;

import java.awt.Point;

import dev.gavinthomas.tictactoe.opponents.Computer;
import dev.gavinthomas.tictactoe.opponents.Player;
import dev.gavinthomas.tictactoe.types.Opponent;
import dev.gavinthomas.tictactoe.Board.PieceType;
import dev.gavinthomas.tictactoe.input.input;
import dev.gavinthomas.tictactoe.input.Keycode;
import dev.gavinthomas.tictactoe.input.Keybind;
import dev.gavinthomas.tictactoe.input.KeybindArgument;
import dev.gavinthomas.tictactoe.Game;

public class TicTacToe {
  private Game game;
  private Keybind menuKBS;

  public TicTacToe() {
    this.init();
  }
  public void init() {
    input.init();
    menuKBS = new Keybind(
        new Keycode[] { Keycode.UP_ARROW, Keycode.DOWN_ARROW, Keycode.LEFT_ARROW, Keycode.RIGHT_ARROW, Keycode.SPACE, Keycode.LOWER_L },
        new Object[] { KeybindArgument.KEYCODE }, this::input);
    input.addBinds(menuKBS);
  }

  public void newGame(Game.Builder gameConfig) {
    menuKBS.set.enabled(false);
    this.game = new Game(gameConfig);
    this.game.start();
    menuKBS.set.enabled(true);
  }

  public void input(Object[] args) {

  }
}