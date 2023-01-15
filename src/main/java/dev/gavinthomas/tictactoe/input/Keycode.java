package dev.gavinthomas.tictactoe.input;

import java.util.Arrays;


public enum Keycode {
  
  // lowercase letters
  LOWER_A(97, 'a', "a"), LOWER_B(98, 'b', "b"), LOWER_C(99, 'c', "c"), LOWER_D(100, 'd', "d"), LOWER_E(101, 'e', "e"),
  LOWER_F(102, 'f', "f"), LOWER_G(103, 'g', "g"), LOWER_H(104, 'h', "h"), LOWER_I(105, 'i', "i"), LOWER_J(106, 'j', "j"),
  LOWER_K(107, 'k', "k"), LOWER_L(108, 'l', "l"), LOWER_M(109,'m', "m"), LOWER_N(110, 'n', "n"), LOWER_O(111, 'o', "o"),
  LOWER_P(112, 'p', "p"), LOWER_Q(113, 'q', "q"), LOWER_R(114, 'r', "r"), LOWER_S(115,'s', "s"), LOWER_T(116, 't', "t"),
  LOWER_U(117, 'u', "u"), LOWER_V(118, 'v', "v"), LOWER_W(119, 'w', "w"), LOWER_X(120, 'x', "x"), LOWER_Y(121, 'y', "y"),
  LOWER_Z(122, 'z', "z"),

  // uppercase letters
  UPPER_A(65, 'A', "A"), UPPER_B(66, 'B', "B"), UPPER_C(67, 'C', "C"), UPPER_D(68, 'D', "D"), UPPER_E(69, 'E', "E"),
  UPPER_F(70, 'F', "F"), UPPER_G(71, 'G', "G"), UPPER_H(72, 'H', "H"), UPPER_I(73, 'I', "I"), UPPER_J(74, 'J', "J"),
  UPPER_K(75, 'K', "K"), UPPER_L(76, 'L', "L"), UPPER_M(77, 'M', "M"), UPPER_N(78, 'N', "N"), UPPER_O(79, 'O', "O"),
  UPPER_P(80, 'P', "P"), UPPER_Q(81, 'Q', "Q"), UPPER_R(82, 'R', "R"), UPPER_S(83, 'S', "S"), UPPER_T(84, 'T', "T"),
  UPPER_U(85, 'U', "U"), UPPER_V(86, 'V', "V"), UPPER_W(87, 'W', "W"), UPPER_X(88, 'X', "X"), UPPER_Y(89, 'Y', "Y"),
  UPPER_Z(90, 'Z', "Z"),


  // numbers
  ZERO(48, '0', "0"), ONE(49, '1', "1"), TWO(50, '2', "2"), THREE(51, '3', "3"), FOUR(52, '4', "4"), FIVE(53, '5', "5"),
  SIX(54, '6', "6"), SEVEN(55, '7', "7"), EIGHT(56, '8', "8"), NINE(57, '9', "9"),

  // special characters
  PERIOD(46, '.', "."), COMMA(44, ',', ","), COLON(58, ':', ":"), SEMICOLON(59, ';', ";"), QUESTION_MARK(63, '?', "?"),
  EXCLAMATION_POINT(33, '!', "!"), TILDE(126, '~', "~"), UNDERSCORE(95, '_', "_"), MINUS(45, '-', "-"), PLUS(43, '+', "+"),
  BACKSLASH(92, '\\', "\\"), FORWARD_SLASH(47, '/', "/"), GRAVE(96, '`', "`"), LEFT_BRACKET(91, '[', "["),
  RIGHT_BRACKET(93, ']', "]"), QUOTE(34, '"', "\""), APOSTROPHE(39, '\'', "\'"), LEFT_CURLY_BRACKET(123, '{', "{"),
  RIGHT_CURLY_BRACKET(125, '}', "}"), LESS_THAN(60, '<', "<"), GREATER_THAN(62, '>', ">"), EQUAL(61, '=', "="),

  
  BACK_QUOTE(96, '`', "`"), VERTICAL_BAR(124, '|', "|"), AT(64, '@', "@"), POUND(35, '#', "#"), CARET(94, '^', "^"),
  AMPERSAND(38, '&', "&"), ASTERISK(42, '*', "*"), LEFT_PARENTHESIS(40, '(', "("), RIGHT_PARENTHESIS(41, ')', ")"),
  PERCENT(37, '%', "%"),

  // mod keys
  BACKSPACE(127, '\b', "Backspace"), SPACE(32, ' ', "Space"), TAB(9, '\t', "Tab"), ENTER(13, '\n', "Enter"),

  UP_ARROW(new int[]{27, 91, 65}, "Up Arrow"), DOWN_ARROW(new int[]{27, 91, 66}, "Down Arrow"),
  RIGHT_ARROW(new int[]{27, 91, 67}, "Right Arrow"), LEFT_ARROW(new int[]{27, 91, 68}, "Left Arrow");

  
  private int[] codes;
  private Character key;
  private String name;

  Keycode(int code, Character key, String name) {
    this.codes = new int[]{code};
    this.key = key;
    this.name = name;
  }

  Keycode(int[] codes, String name) {
    this.codes = codes;
    this.name = name;
    this.key = null;
  }

  public int[] getCodes() {
    return codes;
  }
  public Character getKey() {
    return key;
  }
  public String getName() {
    return name;
  }

  
  public static Keycode find(int code) {
    for (Keycode key : Keycode.values()) {
      if (key.getCodes()[0] == code) {
        return key;
      }
    }
    return null;
  }

  public static Keycode find(int[] codes) {
    for (Keycode key : Keycode.values()) {
      if (Arrays.equals(key.getCodes(), codes)) {
        // System.out.println(key.getName());
        return key;
      }
    }
    return null;
  }

  public static Keycode find(Character keychar) {
    for (Keycode key : Keycode.values()) {
      if (key.getKey().equals(keychar)) {
        return key;
      }
    }
    return null;
  }

  public static Keycode find(String name) {
  	for (Keycode key : Keycode.values()) {
  		if (key.getName().equals(name)) {
  			return key;
  		}
  	}
  	return null;
  }

  public static boolean hasNext(int[] codes) {
    for (Keycode key : Keycode.values()) {
      int[] next = key.getCodes();
      if (codes.length < next.length && Arrays.equals(Arrays.copyOfRange(next, 0, codes.length), codes)) {
        // System.out.println(key);
        return true;
      }
    }
    return false;
  }

  public static Keycode matchStart(int[] codes) {
    for (Keycode key : Keycode.values()) {
      if (Arrays.equals(key.getCodes(), Arrays.copyOfRange(codes, 0, key.getCodes().length))) {
        // System.out.println(key.getName());
        return key;
      }
    }
    return null;
  }
}