package com.jyz.component.core.logging.stdout;

import com.jyz.component.core.logging.Log;

/**
 * 
 * @author JoyoungZhang@gmail.com
 *
 */
public class StdOutImpl implements Log {

  public StdOutImpl(String clazz) {
  }

  public boolean isDebugEnabled() {
    return true;
  }

  public boolean isTraceEnabled() {
    return true;
  }

  public void error(String s, Throwable e) {
    System.err.println(s);
    e.printStackTrace(System.err);
  }

  public void error(String s) {
    System.err.println(s);
  }

  public void debug(String s) {
    System.out.println(s);
  }

  public void trace(String s) {
    System.out.println(s);
  }

  public void warn(String s) {
    System.out.println(s);
  }
}
