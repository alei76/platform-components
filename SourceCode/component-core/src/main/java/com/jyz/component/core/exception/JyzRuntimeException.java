package com.jyz.component.core.exception;

/**
 * 	Jyz运行时异常
 *	@author JoyoungZhang@gmail.com
 *
 */
public class JyzRuntimeException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public JyzRuntimeException() {
		super();
	}

	public JyzRuntimeException(String message) {
		super(message);
	}

	public JyzRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public JyzRuntimeException(Throwable cause) {
		super(cause);
	}
	
}

