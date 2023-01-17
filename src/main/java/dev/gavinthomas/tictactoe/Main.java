package dev.gavinthomas.tictactoe;

import dev.gavinthomas.tictactoe.types.Visuals;
import dev.gavinthomas.tictactoe.Board;
import dev.gavinthomas.tictactoe.Board.PieceType;
import dev.gavinthomas.tictactoe.TicTacToe;

import dev.gavinthomas.tictactoe.TTT;
import dev.gavinthomas.tictactoe.utils.Minimax;

import java.awt.Point;
import java.util.Arrays;
import java.util.Scanner;


public class Main {
  public static final PieceType X = PieceType.X;
  public static final PieceType O = PieceType.O;
  public static void main(String[] args) throws Exception {

    // System.out.print("\033[2J\033[H");

//     Board b = new Board(1);
     TicTacToe t = new TicTacToe();
//     b.render();
     t.newGame(new Game.Builder().computerOpponent().firstMove(0));

    PieceType[][] pta = new PieceType[3][3];
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

    pta[0][0] = X;
    pta[1][0] = X;
    pta[2][0] = O;
//    pta[0][1] = O;
    pta[1][1] = O;
    pta[2][1] = O;
    pta[2][2] = X;

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
//    System.out.println(new Minimax(O));
    System.out.println(System.currentTimeMillis() - ts);
    // // String temp = """
    // //   abct
//    // // """;
//    String temp = "\n";
//    System.out.println(((int) temp.charAt(0)));

//    new Scanner(System.in).nextLine(); // no auto exit
  }
}