package com.jyz.component.core.logging;

import com.jyz.component.core.exception.JyzRuntimeException;

/**
 * 
 * @author JoyoungZhang@gmail.com
 * 
 */
public class LogException extends JyzRuntimeException {

    private static final long serialVersionUID = 1L;

    public LogException() {
	super();
    }

    public LogException(String message) {
	super(message);
    }

    public LogException(String message, Throwable cause) {
	super(message, cause);
    }

    public LogException(Throwable cause) {
	super(cause);
    }

}
