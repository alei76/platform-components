package com.jyz.component.io;

import com.jyz.component.core.exception.JyzException;

/**
 *	@author JoyoungZhang@gmail.com
 *
 */
public class CryptoException extends JyzException{

	private static final long serialVersionUID = 1L;
	
	public CryptoException(String errorCode) {
		 super(errorCode);
    }
	
	public CryptoException(String errorCode, Object... arguments) {
		 super(errorCode, arguments);
	}
	
	public CryptoException(Throwable throwable, String errorCode, Object... arguments) {
        super(throwable, errorCode, arguments);
    }
	
	
}
