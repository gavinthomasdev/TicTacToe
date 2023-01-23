package dev.gavinthomas.tictactoe.types;

import dev.gavinthomas.tictactoe.ui.SelectionUI;

import java.text.MessageFormat;

public abstract class Visuals {
//  private static final int[] xBlockVals2 = { 255, 0, 0 };
  private static final String[] gridVals = { "\033[1B\033[58D" };
  private static final String[] xBlockVals = { "\033[38;2;255;0;0m", "\033[0m\033[1B\033[10D" };
  private static final String[] oBlockVals = { "\033[38;2;255;255;255m", "\033[0m\033[1B\033[10D" };
  private static final String[] blankBlockVals = { "\033[0m", "\033[1B\033[10D" };
  private static final String[] highlightVals = { "\033[38;2;0;255;0;1m", "\033[1B\033[18D", "\033[16C" };
  private static final String[] titleVals = {"", "\033[1B\033[70D", "\033[31m", "\033[0m"};
  private static final String[] sizeUIVals = { "", "\033[1B\033[19D" };
  private static final String[] TITLELTRS = {
      """
████████╗
╚══██╔══╝
░░░██║░░░
░░░██║░░░
░░░██║░░░
░░░╚═╝░░░
""", """
██╗
██║
██║
██║
██║
╚═╝
""", """
░█████╗░
██╔══██╗
██║░░╚═╝
██║░░██╗
╚█████╔╝
░╚════╝░
""", """
████████╗
╚══██╔══╝
░░░██║░░░
░░░██║░░░
░░░██║░░░
░░░╚═╝░░░
""", """
░█████╗░
██╔══██╗
███████║
██╔══██║
██║░░██║
╚═╝░░╚═╝
""", """
░█████╗░
██╔══██╗
██║░░╚═╝
██║░░██╗
╚█████╔╝
░╚════╝░
""", """
████████╗
╚══██╔══╝
░░░██║░░░
░░░██║░░░
░░░██║░░░
░░░╚═╝░░░
""", """
░█████╗░
██╔══██╗
██║░░██║
██║░░██║
╚█████╔╝
░╚════╝░
""", """
███████╗
██╔════╝
█████╗░░
██╔══╝░░
███████╗
╚══════╝
"""};

  public static final String GRID = MessageFormat.format("" +
      "                  ██                  ██                  {0}" +
      "                  ██                  ██                  {0}" +
      "                  ██                  ██                  {0}" +
      "                  ██                  ██                  {0}" +
      "                  ██                  ██                  {0}" +
      "                  ██                  ██                  {0}" +
      "                  ██                  ██                  {0}" +
      "                  ██                  ██                  {0}" +
      "                  ██                  ██                  {0}" +
      "██████████████████████████████████████████████████████████{0}" +
      "                  ██                  ██                  {0}" +
      "                  ██                  ██                  {0}" +
      "                  ██                  ██                  {0}" +
      "                  ██                  ██                  {0}" +
      "                  ██                  ██                  {0}" +
      "                  ██                  ██                  {0}" +
      "                  ██                  ██                  {0}" +
      "                  ██                  ██                  {0}" +
      "                  ██                  ██                  {0}" +
      "██████████████████████████████████████████████████████████{0}" +
      "                  ██                  ██                  {0}" +
      "                  ██                  ██                  {0}" +
      "                  ██                  ██                  {0}" +
      "                  ██                  ██                  {0}" +
      "                  ██                  ██                  {0}" +
      "                  ██                  ██                  {0}" +
      "                  ██                  ██                  {0}" +
      "                  ██                  ██                  {0}" +
      "                  ██                  ██                  ",
      (Object[]) gridVals);

  public static final String XBLOCK = MessageFormat.format("" +
      "{0} ▄▄▄  ▄▄▄ {1}" +
      "{0}  ██▄▄██  {1}" +
      "{0}   ████   {1}" +
      "{0}    ██    {1}" +
      "{0}   ████   {1}" +
      "{0}  ██  ██  {1}" +
      "{0} ▀▀▀  ▀▀▀ {1}",
      (Object[]) xBlockVals);

  public static final String OBLOCK = MessageFormat.format("" +
      "{0}   ▄▄▄▄   {1}" +
      "{0}  ██▀▀██  {1}" +
      "{0} ██    ██ {1}" +
      "{0} ██    ██ {1}" +
      "{0} ██    ██ {1}" +
      "{0}  ██▄▄██  {1}" +
      "{0}   ▀▀▀▀   {1}",
      (Object[]) oBlockVals);

  public static final String BLANKBLOCK = MessageFormat.format("" +
      "{0}          {1}" +
      "{0}          {1}" +
      "{0}          {1}" +
      "{0}          {1}" +
      "{0}          {1}" +
      "{0}          {1}" +
      "{0}          {1}",
      (Object[]) blankBlockVals);

  public static final String HIGHLIGHT = MessageFormat.format("" +
      "{0}╭────────────────╮{1}" +
      "{0}│{2}│{1}" +
      "{0}│{2}│{1}" +
      "{0}│{2}│{1}" +
      "{0}│{2}│{1}" +
      "{0}│{2}│{1}" +
      "{0}│{2}│{1}" +
      "{0}│{2}│{1}" +
                                                              
      "{0}╰────────────────╯",
      (Object[]) highlightVals);

  public static final String NOHIGHLIGHT = MessageFormat.format("" +
          "{0}╭────────────────╮{1}" +
          "{0}│{2}│{1}" +
          "{0}│{2}│{1}" +
          "{0}│{2}│{1}" +
          "{0}│{2}│{1}" +
          "{0}│{2}│{1}" +
          "{0}│{2}│{1}" +
          "{0}│{2}│{1}" +

          "{0}╰────────────────╯",
      (Object[]) highlightVals);

  public static final String UNHIGHLIGHT = MessageFormat.format("" +
      "{0}                  {1}" +
      "{0} {2} {1}" +
      "{0} {2} {1}" +
      "{0} {2} {1}" +
      "{0} {2} {1}" +
      "{0} {2} {1}" +
      "{0} {2} {1}" +
      "{0} {2} {1}" +
                                                              
      "{0}                  ",
      (Object[]) highlightVals);


  public static final String TITLE = MessageFormat.format("" +
      "{0}████████╗██╗░█████╗░" + "████████╗░█████╗░░█████╗░".replaceAll("█", "\033[31m█\033[0m") + "████████╗░█████╗░███████╗{1}" +
      "{0}╚══██╔══╝██║██╔══██╗" + "╚══██╔══╝██╔══██╗██╔══██╗".replaceAll("█", "\033[31m█\033[0m") + "╚══██╔══╝██╔══██╗██╔════╝{1}" +
      "{0}░░░██║░░░██║██║░░╚═╝" + "░░░██║░░░███████║██║░░╚═╝".replaceAll("█", "\033[31m█\033[0m") + "░░░██║░░░██║░░██║█████╗░░{1}" +
      "{0}░░░██║░░░██║██║░░██╗" + "░░░██║░░░██╔══██║██║░░██╗".replaceAll("█", "\033[31m█\033[0m") + "░░░██║░░░██║░░██║██╔══╝░░{1}" +
      "{0}░░░██║░░░██║╚█████╔╝" + "░░░██║░░░██║░░██║╚█████╔╝".replaceAll("█", "\033[31m█\033[0m") + "░░░██║░░░╚█████╔╝███████╗{1}" +
      "{0}░░░╚═╝░░░╚═╝░╚════╝░" + "░░░╚═╝░░░╚═╝░░╚═╝░╚════╝░".replaceAll("█", "\033[31m█\033[0m") + "░░░╚═╝░░░░╚════╝░╚══════╝{1}",
      (Object[]) titleVals);



  public static String title1(boolean[] reds) {
    String[] vals = { "\033[0m\033[1B\033[20D",
        (reds[0] ? "\033[38;2;255;0;0m" : "\033[37m"),
        (reds[1] ? "\033[38;2;255;0;0m" : "\033[37m"),
        (reds[2] ? "\033[38;2;255;0;0m" : "\033[37m")
    };

    return MessageFormat.format("" +
        "{1}████████╗{2}██╗{3}░█████╗░{0}" +
        "{1}╚══██╔══╝{2}██║{3}██╔══██╗{0}" +
        "{1}░░░██║░░░{2}██║{3}██║░░╚═╝{0}" +
        "{1}░░░██║░░░{2}██║{3}██║░░██╗{0}" +
        "{1}░░░██║░░░{2}██║{3}╚█████╔╝{0}" +
        "{1}░░░╚═╝░░░{2}╚═╝{3}░╚════╝░\033[0m",
        (Object[]) vals).replaceAll("░", "\033[37m░\033[0m");
  }

  public static String title2(String color) {
    String[] vals = { "\033[" + color + "m", "\033[1B\033[25D" };

    return MessageFormat.format("" +
            "{0}████████╗░█████╗░░█████╗░{1}" +
            "{0}╚══██╔══╝██╔══██╗██╔══██╗{1}" +
            "{0}░░░██║░░░███████║██║░░╚═╝{1}" +
            "{0}░░░██║░░░██╔══██║██║░░██╗{1}" +
            "{0}░░░██║░░░██║░░██║╚█████╔╝{1}" +
            "{0}░░░╚═╝░░░╚═╝░░╚═╝░╚════╝░",
        (Object[]) vals);
  }

  public static String title3(String color) {
    String[] vals = { "\033[" + color + "m", "\033[1B\033[25D" };

    return MessageFormat.format("" +
            "{0}████████╗░█████╗░███████╗{1}" +
            "{0}╚══██╔══╝██╔══██╗██╔════╝{1}" +
            "{0}░░░██║░░░██║░░██║█████╗░░{1}" +
            "{0}░░░██║░░░██║░░██║██╔══╝░░{1}" +
            "{0}░░░██║░░░╚█████╔╝███████╗{1}" +
            "{0}░░░╚═╝░░░░╚════╝░╚══════╝",
        (Object[]) vals);
  }

  public static String title(boolean[] reds) {
    StringBuilder finStr = new StringBuilder();
    for (int i = 0; i < TITLELTRS.length; i++) {
      String[] vals = { "\033[1B\033[" + (TITLELTRS[i].indexOf('\n')) + "D",
          (reds[i] ? "\033[38;2;255;0;0m" : "\033[37m"),
          "\033[0m", "\033[0m", "\033[1C\033[5A"
      };
//      System.out.println(vals[1].replaceAll("\033", ""));
      StringBuilder newLtrBldr = new StringBuilder();
      for (int j = 0; j != -1; j = TITLELTRS[i].indexOf('\n', j + 1)) {
        if (TITLELTRS[i].indexOf('\n', j + 1) == -1) break;
        newLtrBldr.append(TITLELTRS[i], j + (j == 0 ? 0 : 1), TITLELTRS[i].indexOf('\n', j + 1)).append("{3}");
        int nextLine = TITLELTRS[i].indexOf('\n', j + 1);
        if (TITLELTRS[i].indexOf('\n', nextLine + 1) != -1) newLtrBldr.append("{0}");
      }
      newLtrBldr.append("{4}");
      String newStr = newLtrBldr.toString();
//      String newStr = newLtrBldr.toString().replaceAll("((?:╔|═|╝|║|╚|╗)+|░+)", "{2}$0{3}");
      newStr = newStr.replaceAll("(█)+", "{1}$0{3}");
      System.out.println(newStr);
//      System.out.println(MessageFormat.format(newStr, (Object[]) vals));
      finStr.append(MessageFormat.format(newLtrBldr.toString(), (Object[]) vals));
    }
    return finStr.toString();
  }

  public static String sizeUI(int x, int y, int rx, int ry) {
    String xStr = " ".repeat(7 - String.valueOf(x).length()) + x;
    String yStr = y + " ".repeat(7 - String.valueOf(y).length());
    String rxStr = " ".repeat(7 - String.valueOf(rx).length()) + rx;
    String ryStr = ry + " ".repeat(7 - String.valueOf(ry).length());

    return MessageFormat.format("" +
        "{0}╭─────────────────╮{1}" +
        "{0}│     \033[1;4mMinimum\033[0m     │{1}" +
        "{0}│" + rxStr + " x " + ryStr + "│{1}" +
        "{0}│     \033[1;4mCurrent\033[0m     │{1}" +
        "{0}│" + xStr + " x " + yStr + "│{1}" +
        "{0}╰─────────────────╯{1}",

        (Object[]) sizeUIVals);
  }

  public static String menuButton(String text, SelectionUI.MODE mode) {
    String[] vals = { "\033[0m\033[1B\033[20D",
        (mode == SelectionUI.MODE.DISABLED ? "\033[2m" : ""),
        (mode == SelectionUI.MODE.SELECTED ? "\033[1m" : ""),
        "\033[0m"
    };
    text = " ".repeat((text.length() % 2 == 0 ? 9 : 8) - (text.length() / 2)) +
        text + " ".repeat(9 - (text.length() / 2));
    String[] lines = (mode == SelectionUI.MODE.SELECTED ?
        new String[]{"━", "┃", "┏", "┓", "┛", "┗"} :
        new String[]{"─", "│", "┌", "┐", "┘", "└"});

    return MessageFormat.format("" +
        "{1}" + lines[2] + lines[0].repeat(18) + lines[3] + "{0}" +
        "{1}" + lines[1] + "{2}" + text + "{3}" + lines[1] + "{0}" +
        "{1}" + lines[5] + lines[0].repeat(18) + lines[4] + "\033[0m",
        (Object[]) vals);
  }


  public static String box(int w, int h) {
    String[] vals = {"\033[1B\033[" + w + "D", "\033[" + (w - 4) + "C"};
    return MessageFormat.format("" +
        "█".repeat(w) + "{0}" +
        "██{1}██{0}".repeat(h - 2) +
        "█".repeat(w),
        (Object[]) vals);
  }

  public static String doubleLineBox(int w, int h) {
    String[] vals = {"\033[1B\033[" + w + "D", "\033[" + (w - 2) + "C"};
    return MessageFormat.format("" +
            "╔" + "═".repeat(w - 2) + "╗" + "{0}" +
            "║{1}║{0}".repeat(h - 2) +
            "╚" + "═".repeat(w - 2) + "╝",
        (Object[]) vals);
  }

}