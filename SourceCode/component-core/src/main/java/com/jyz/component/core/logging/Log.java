package com.jyz.component.core.logging;

/**
 * 日志接口
 * @author JoyoungZhang@gmail.com
 *
 */
public interface Log {

  boolean isDebugEnabled();

  boolean isTraceEnabled();

  void error(String s, Throwable e);

  void error(String s);
  
  void info(String s);

  void debug(String s);

  void trace(String s);

  void warn(String s);

}
