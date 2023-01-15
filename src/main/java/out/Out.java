package out;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public abstract class Out {
  public static void append(String str) {
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt", true));
      writer.append(str);
      writer.append('\n');

      writer.close();
    } catch (IOException e) {
    }
  }

  public static void append(Object val) {
    append(String.valueOf(val));
  }
  public static void append(int val) {
    append(String.valueOf(val));
  }
  public static void append(byte val) {
    append(String.valueOf(val));
  }
  public static void append(char val) {
    append(String.valueOf(val));
  }
  public static void append(double val) {
    append(String.valueOf(val));
  }
  public static void append(long val) {
    append(String.valueOf(val));
  }
  public static void append(float val) {
    append(String.valueOf(val));
  }
  public static void append(boolean val) {
    append(String.valueOf(val));
  }
}