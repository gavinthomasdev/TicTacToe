package dev.gavinthomas.tictactoe.types;

import java.text.MessageFormat;

public abstract class Visuals {
  private static final int[] xBlockVals2 = { 255, 0, 0 };
  private static final String[] xBlockVals = { "\033[38;2;255;0;0m", "\033[0m\033[1B\033[10D" };
  private static final String[] oBlockVals = { "\033[38;2;255;255;255m", "\033[0m\033[1B\033[10D" };
  private static final String[] blankBlockVals = { "\033[0m", "\033[1B\033[10D" };
  private static final String[] highlightVals = { "\033[38;2;0;255;0m", "\033[1B\033[18D", "\033[16C" };

  public static final String GRID = "" +
      "                  ██                  ██                  \n" +
      "                  ██                  ██                  \n" +
      "                  ██                  ██                  \n" +
      "                  ██                  ██                  \n" +
      "                  ██                  ██                  \n" +
      "                  ██                  ██                  \n" +
      "                  ██                  ██                  \n" +
      "                  ██                  ██                  \n" +
      "                  ██                  ██                  \n" +
      "██████████████████████████████████████████████████████████\n" +
      "                  ██                  ██                  \n" +
      "                  ██                  ██                  \n" +
      "                  ██                  ██                  \n" +
      "                  ██                  ██                  \n" +
      "                  ██                  ██                  \n" +
      "                  ██                  ██                  \n" +
      "                  ██                  ██                  \n" +
      "                  ██                  ██                  \n" +
      "                  ██                  ██                  \n" +
      "██████████████████████████████████████████████████████████\n" +
      "                  ██                  ██                  \n" +
      "                  ██                  ██                  \n" +
      "                  ██                  ██                  \n" +
      "                  ██                  ██                  \n" +
      "                  ██                  ██                  \n" +
      "                  ██                  ██                  \n" +
      "                  ██                  ██                  \n" +
      "                  ██                  ██                  \n" +
      "                  ██                  ██                  ";

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

}