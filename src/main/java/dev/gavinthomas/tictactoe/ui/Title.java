package dev.gavinthomas.tictactoe.ui;

import dev.gavinthomas.tictactoe.TicTacToe;
import dev.gavinthomas.tictactoe.types.UIComponent;
import dev.gavinthomas.tictactoe.types.UIHolder;
import dev.gavinthomas.tictactoe.types.Visuals;
import dev.gavinthomas.tictactoe.utils.Term;

import java.awt.Point;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.function.BiConsumer;

public class Title implements UIComponent {
  private final Term TERM = TicTacToe.CURR.TERM;
  private final Point pos;
  private final UIHolder holder;
  private volatile long animStart = 0;
  private volatile boolean animRunning = false;
  private ExecutorService animExec;
//  private final AnimProps[] anims = {
//      new AnimProps(50, 1000, this::titleAnim1),
//      new AnimProps(250, 2000, this::titleAnim2),
//  };
  private final AnimProps[] anims = {
      new AnimProps(50, 1, this::titleAnim1),
      new AnimProps(250, 6, this::titleAnim2),
  };

  public Title(UIHolder holder, Point pos) {
    this.holder = holder;
    this.pos = pos;
    this.animExec = Executors.newSingleThreadExecutor();
  }
  
  public void render() {
    toggleAnimations(false);
    TERM.startGroup();
    setCursorPos(pos.x, pos.y);
    TERM.print(Visuals.title(new boolean[]{false, false, false, false, false, false, false, false, false}));
    TERM.endGroup();
    toggleAnimations(true);
  }
  
  public void toggleAnimations(boolean tog) {
    if (tog) {
      if (animStart != 0) animExec.shutdownNow();
      try {
        animExec.submit(this::startAnimations);
      } catch (RejectedExecutionException e) {
        animExec = Executors.newSingleThreadExecutor();
        animExec.submit(this::startAnimations);
      }
    } else if (animStart != 0) {
      animStart = 0;
      animExec.shutdownNow();
    }
  }

  public void endTasks() {
    toggleAnimations(false);
  }

  private void startAnimations() {
    long selfStartTime = System.currentTimeMillis();
    Point lastSize = TicTacToe.CURR.SIZE;
    animStart = selfStartTime;

    try {
      Thread.sleep(1000);
    } catch (InterruptedException ignored) {}
    if (TicTacToe.CURR.SIZE != lastSize) return;

    AnimProps current = null;

    while (animStart == selfStartTime) {
      AnimProps rand;
      do {
        rand = anims[(int) (Math.random() * anims.length)];
      } while (rand == current);
      current = rand;
      rand.RUNNER.accept(rand.DELAY, rand.DURATION);
    }
  }

  public void setCursorPos(int x, int y) {
    TERM.setCursorPos(holder.offset().x + x, holder.offset().y + y);
  }
  
  private void titleAnim1(Integer delay, Integer duration) {
    long startTime = System.currentTimeMillis();
//    while (System.currentTimeMillis() - startTime < duration) {
    for (int rep = 1; rep <= duration; rep++) {
      boolean[] currOn = {false, false, false, false, false, false, false, false, false};
      int step = -2;
      while (step < 10) {
//        if (System.currentTimeMillis() - startTime < duration) break;
        for (int i = step; i < step + 3; i++) {
          if (i > 8 || i < 0) continue;
          currOn[i] = true;
        }
        TERM.startGroup();
        setCursorPos(pos.x, pos.y);
        TERM.print(Visuals.title(currOn));
        TERM.endGroup();
        step++;
        currOn = new boolean[]{false, false, false, false, false, false, false, false, false};
        try {
          Thread.sleep(delay);
        } catch (InterruptedException ignore) {
        }
      }
      step = 9;
      while (step > -2) {
//        if (System.currentTimeMillis() - startTime < duration) break;
        for (int i = step; i > step - 3; i--) {
          if (i > 8 || i < 0) continue;
          currOn[i] = true;
        }
        TERM.startGroup();
        setCursorPos(pos.x, pos.y);
        TERM.print(Visuals.title(currOn));
        TERM.endGroup();
        step--;
        currOn = new boolean[]{false, false, false, false, false, false, false, false, false};
        try {
          Thread.sleep(delay);
        } catch (InterruptedException ignore) {
        }
      }
    }
    TERM.startGroup();
    setCursorPos(pos.x, pos.y);
    TERM.print(Visuals.title(new boolean[]{false, false, false, false, false, false, false, false, false}));
    TERM.endGroup();
  }

  private void titleAnim2(Integer delay, Integer duration) {
    long startTime = System.currentTimeMillis();
    int i = 0;
//    while(System.currentTimeMillis() - startTime < duration) {
    for (int rep = 1; rep <= duration; rep++) {
      boolean[] currOn = (i % 2 == 0 ?
          new boolean[]{true, false, true, false, true, false, true, false, true} :
          new boolean[]{false, true, false, true, false, true, false, true, false});
      TERM.startGroup();
      setCursorPos(pos.x, pos.y);
      TERM.print(Visuals.title(currOn));
      TERM.endGroup();
      try {
        Thread.sleep(delay);
      } catch (InterruptedException ignore) {
      }
      i++;
    }
    TERM.startGroup();
    setCursorPos(pos.x, pos.y);
    TERM.print(Visuals.title(new boolean[]{false, false, false, false, false, false, false, false, false}));
    TERM.endGroup();
  }
}

class AnimProps {
  public final Integer DELAY, DURATION;
  public final BiConsumer<Integer, Integer> RUNNER;
  public AnimProps(int DELAY, int DURATION, BiConsumer<Integer, Integer> RUNNER) {
    this.DELAY = DELAY;
    this.DURATION = DURATION;
    this.RUNNER = RUNNER;
  }
}
