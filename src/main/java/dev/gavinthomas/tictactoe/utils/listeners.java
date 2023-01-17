package dev.gavinthomas.tictactoe.utils;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.InvocationHandler;

public abstract class listeners {
  public static class terminalResizeListener {
    final Runnable runner;

    private static class Handler implements InvocationHandler {
      @Override
      public Object invoke(Object proxy, Method method, Object[] args) {
        return null;
      }
    }

    private void register() throws Throwable {
      
    }
    
    public terminalResizeListener(final Runnable runner) {
      this.runner = runner;
      try {
        Class<?> signalClass = Class.forName("sun.misc.Signal");
        for (Method m : signalClass.getDeclaredMethods()) {
          if ("handle".equals(m.getName())) {
            Object windowResizeHandler = Proxy.newProxyInstance(getClass().getClassLoader(),
                new Class[] { Class.forName("sun.misc.SignalHandler") }, (proxy, method, args) -> {
                  if ("handle".equals(method.getName())) {
                    runner.run();
                  }
                  return null;
                });
            m.invoke(null, signalClass.getConstructor(String.class).newInstance("WINCH"), windowResizeHandler);
          }
        }
      } catch (Throwable ignore) {}
    }
  }

  public static class onExitListener {
    public onExitListener(Runnable runner) {
      Runtime.getRuntime().addShutdownHook(new Thread(runner));
    }
  }
}
