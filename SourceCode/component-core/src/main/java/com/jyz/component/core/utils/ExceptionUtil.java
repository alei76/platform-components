package com.jyz.component.core.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;

/**
 * 
 * @author JoyoungZhang@gmail.com
 *
 */
public class ExceptionUtil {

  public static Throwable unwrapThrowable(Throwable wrapped) {
    Throwable unwrapped = wrapped;
    while (true) {
      if (unwrapped instanceof InvocationTargetException) {
        unwrapped = ((InvocationTargetException) unwrapped).getCause();//.getTargetException();
      } else if (unwrapped instanceof UndeclaredThrowableException) {
        unwrapped = ((UndeclaredThrowableException) unwrapped).getCause();//.getUndeclaredThrowable();
      } else {
        return unwrapped;
      }
    }
  }

}
