package dev.gavinthomas.tictactoe;

import dev.gavinthomas.tictactoe.input.input;
import dev.gavinthomas.tictactoe.types.Visuals;
import dev.gavinthomas.tictactoe.Board;
import dev.gavinthomas.tictactoe.Board.PieceType;
import dev.gavinthomas.tictactoe.TicTacToe;

import dev.gavinthomas.tictactoe.TTT;
import dev.gavinthomas.tictactoe.ui.Menu;
import dev.gavinthomas.tictactoe.utils.Minimax;
import dev.gavinthomas.tictactoe.utils.Term;
import io.raffi.drawille.Canvas;
import io.raffi.drawille.Turtle;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class Main {
  public static final PieceType X = PieceType.X;
  public static final PieceType O = PieceType.O;
  public static boolean abc = true;
  public static final Term TERM = new Term();
  public static void titleAnim1(int x, int y, int delay, int duration) {
    long startTime = System.currentTimeMillis();
    while (System.currentTimeMillis() - startTime < duration) {
      boolean[] currOn = {false, false, false, false, false, false, false, false, false};
      int step = -2;
      while (step < 10) {
        for (int i = step; i < step + 3; i++) {
          if (i > 8 || i < 0) continue;
          currOn[i] = true;
        }
        TERM.setCursorPos(x, y);
        TERM.print(Visuals.title(currOn));
        step++;
        currOn = new boolean[]{false, false, false, false, false, false, false, false, false};
        try {
          Thread.sleep(delay);
        } catch (InterruptedException ignore) {
        }
      }
      step = 9;
      while (step > -2) {
        for (int i = step; i > step - 3; i--) {
          if (i > 8 || i < 0) continue;
          currOn[i] = true;
        }
        TERM.setCursorPos(x, y);
        TERM.print(Visuals.title(currOn));
        step--;
        currOn = new boolean[]{false, false, false, false, false, false, false, false, false};
        try {
          Thread.sleep(delay);
        } catch (InterruptedException ignore) {
        }
      }
    }
  }

  public static void titleAnim2(int x, int y, int delay, int duration) {
    long startTime = System.currentTimeMillis();
    int i = 0;
    while(System.currentTimeMillis() - startTime < duration) {
      boolean[] currOn = (i % 2 == 0 ?
          new boolean[]{true, false, true, false, true, false, true, false, true} :
          new boolean[]{false, true, false, true, false, true, false, true, false});
      TERM.setCursorPos(x, y);
      TERM.print(Visuals.title(currOn));
      try {
        Thread.sleep(delay);
      } catch (InterruptedException ignore) {
      }
      i++;
    }
  }

  public static void main(String[] args) throws Exception {

//    System.out.println(Visuals.title1(new boolean[]{true, true, true}));

    int step = 0;
    List<Boolean> currOn = Arrays.asList(false, false, false, false, false, false, false, false, false);
//    titleAnim2(5, 5, 500, 3000);
//    if (abc) return;
//    while (abc) {
////      currOn.set(currOn.indexOf(currOn.get(8)), !currOn.get(8));
//      for (int i = step; i < step + 3; i++) {
//        currOn.set((i > 8 ? (i % 8) - 1 : i), true);
//      }
//      boolean[] bools = new boolean[9];
//      for (int i = 0; i < currOn.size(); i++) {
//        bools[i] = currOn.get(i);
//        currOn.set(i, false);
//      }
//      TERM.setCursorPos(5, 5);
//      System.out.println(Visuals.title(bools));
//      step++;
//      if (step > 8) step = 0;
//      Thread.sleep(150);
//    }
//    while (abc) {
//      Thread.sleep(30);
//      currOn.set(currOn.indexOf(currOn.get(8)), !currOn.get(8));
//      boolean[] bools = new boolean[9];
//      for (int i = 0; i < currOn.size(); i++) {
//        bools[i] = currOn.get(i);
//      }
//      t.setCursorPos(5, 5);
//      System.out.println(Visuals.title(bools));
//    }
//    System.out.println(Visuals.title(new boolean[]{true, true, true, true, true, true, true, true, true}));
//    String t = Visuals.title(new boolean[]{true, true, true, true, true, true, true, true, true});
//    System.out.print("\033[5A");
//    System.out.println(Visuals.title2("38;2;255;0;0"));
//    System.out.print("\033[5A");
//    System.out.println(Visuals.title3("38;2;255;0;0"));

//    input.init();
//System.out.println(Visuals.box(40, 20));
    TicTacToe tttG = new TicTacToe();
//    tttG.init();
    while (abc) {
      Thread.onSpinWait();
    }

    while (abc) {
//      tttG.newGame(new Game.Builder().computerOpponent().firstMove(0));

    }
//    Game g = new Game.Builder().computerOpponent().firstMove(0).build();
//    g.start();


//    Turtle turtle = new Turtle ( 75, 50 );
//    turtle.move ( turtle.getWidth () / 2, turtle.getHeight () / 2 );
//    turtle.down ();
//    for ( int x = 0; x < 72; x++ ) {
//      turtle.right ( 20 );
//      for ( int y = 0; y < 72; y++ ) {
//        turtle.right ( 20 );
//        turtle.forward ( 10 );
//      }
//    }
//    turtle.render ();
//    System.out.println(Visuals.TITLE);
      //    new Menu().render();
      //    input.toggleRead(true);
      //    while (Main.abc) {
      //      Thread.onSpinWait();
      //    }
    // System.out.print("\033[2J\033[H");

//     Board b = new Board(1);
//     TicTacToe t = new TicTacToe();
//     b.render();
//     t.newGame(new Game.Builder().computerOpponent().firstMove(0));

    PieceType[][] pta = new PieceType[4][4];
    for (PieceType[] pieceTypes : pta) {
      Arrays.fill(pieceTypes, PieceType.BLANK);
    }

    // pta[4][0] = X;
    // pta[3][1] = X;
    // pta[2][2] = X;
    // pta[1][3] = X;
    // pta[0][4] = X;

    // pta[0][0] = X;
    // pta[1][1] = X;
    // pta[2][2] = X;
    // pta[3][3] = X;
    // pta[4][4] = X;

//    pta[0][0] = X;
//    pta[1][0] = X;
//    pta[2][0] = O;

    String[] brd = {
        "O - X O",
        "X - O O",
        "X O X X",
        "X - - O",
    };
//    String[] brd = {
//        "OXO",
//        "XOX",
//        "XOX",
//    };

    for (int i = 0; i < brd.length; i++) {
      for (int j = 0; j < brd[i].length(); j += 2) {
        if (brd[i].charAt(j) == '-') continue;
//        pta[Math.abs(i - brd.length + 1)][j] = (brd[i].charAt(j) == 'X' ? X : O);
        pta[j / 2][Math.abs(i - brd.length + 1)] = (brd[i].charAt(j) == 'X' ? X : O);
      }
    }
//    pta[0][1] = O;
//    pta[1][1] = O;
//    pta[2][1] = O;
//    pta[2][2] = X;

    // pta[0][0] = X;
    // pta[1][0] = X;
    // pta[2][0] = X;
    // pta[3][0] = X;
    // pta[4][0] = X;
    long ts = System.currentTimeMillis();
//    System.out.println(TTT.getWinner(pta));
    Point rval = new Minimax(O).getBest(pta);
    System.out.println("------------------------");
    System.out.println(rval.x + ", " + rval.y);
    System.out.println(new Minimax(O));
    System.out.println(System.currentTimeMillis() - ts);
    // // String temp = """
    // //   abct
//    // // """;
//    String temp = "\n";
//    System.out.println(((int) temp.charAt(0)));

//    new Scanner(System.in).nextLine(); // no auto exit
  }
}