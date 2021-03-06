package com.jyz.component.core.exception;

import java.util.Locale;

import com.jyz.component.core.i18n.ResourcesLoader;

/**
 *  封装errorCode
 *	@author JoyoungZhang@gmail.com
 *
 */
public class JyzException extends JyzBaseException {
	
	private static final long serialVersionUID = 1L;
	
	private String errorCode;
	private Object[] arguments;
	
	public JyzException(String errorCode) {
        this.errorCode = errorCode;
    }
	
	public JyzException(String errorCode, Object... arguments) {
		this.errorCode = errorCode;
		this.arguments = arguments;
	}
	
	public JyzException(Throwable throwable, String errorCode, Object... arguments) {
        super(throwable);
        this.errorCode = errorCode;
        this.arguments = arguments;
    }
	
	@Override
	public String getMessage(){
		return getLocalizedMessage();
	}
	
	@Override
	public String getLocalizedMessage() {
        return getLocalizedMessage(null);
    }
	
	public String getLocalizedMessage(Locale locale) {
        return ResourcesLoader.getInstance().getString(errorCode, locale, arguments);
    }
	
}
