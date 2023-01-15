package dev.gavinthomas.tictactoe.utils;

import java.util.List;
import java.util.ArrayList;

import dev.gavinthomas.tictactoe.opponents.Computer.MoveOption;

public abstract class MoveList {
  public static List<MoveOption> list = new ArrayList<MoveOption>();

  static {
    // opponent win positions
    {
      // vertical
      {
        list.add(new MoveOption(9).opp(0, -1).opp(0, 1));
        list.add(new MoveOption(9).opp(0, 1).opp(0, 2));
        list.add(new MoveOption(9).opp(0, -1).opp(0, -2));
      }

      // horizontal
      {
        list.add(new MoveOption(9).opp(-1, 0).opp(1, 0));
        list.add(new MoveOption(9).opp(1, 0).opp(2, 0));
        list.add(new MoveOption(9).opp(-1, 0).opp(-2, 0));
      }
      
      // diagonal
      {
        list.add(new MoveOption(9).opp(-1, -1).opp(1, 1));
        list.add(new MoveOption(9).opp(1, 1).opp(2, 2));
        list.add(new MoveOption(9).opp(-1, -1).opp(-2, -2));
        list.add(new MoveOption(9).opp(1, -1).opp(2, -2));
        list.add(new MoveOption(9).opp(1, -1).opp(-1, 1));
        list.add(new MoveOption(9).opp(-1, 1).opp(-2, 2));
      }
    }
  }
}