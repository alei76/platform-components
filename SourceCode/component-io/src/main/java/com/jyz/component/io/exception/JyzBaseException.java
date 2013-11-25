package com.jyz.component.core.exception;

/**
 * Jyz exception，所有受检异常的基类，标识所有受检异常
 * @author JoyoungZhang@gmail.com
 * 
 */
public class JyzBaseException extends Exception {

	private static final long serialVersionUID = 1L;

	public JyzBaseException() {
		super();
	}

	public JyzBaseException(String message) {
		super(message);
	}

	public JyzBaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public JyzBaseException(Throwable cause) {
		super(cause);
	}
	
}
