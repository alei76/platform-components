package com.jyz.component.core.cache;

import com.jyz.component.core.exception.JyzRuntimeException;

/**
 * @author JoyoungZhang@gmail.com
 * 
 */
public class CacheException extends JyzRuntimeException {

	private static final long serialVersionUID = 1L;
	
	public CacheException() {
		super();
	}

	public CacheException(String message) {
		super(message);
	}

	public CacheException(String message, Throwable cause) {
		super(message, cause);
	}

	public CacheException(Throwable cause) {
		super(cause);
	}
	
}
