package com.audaque.lib.core.exception;

/**
 * Audaque exception，所有checked exception的基类，用来标识是自定义的底层异常。
 * 
 * @author deshu.lin (deshu.lin@audaque.com)
 *
 */
public abstract class AdqBaseException extends Exception {

    private static final long serialVersionUID = 1L;

    public AdqBaseException() {
        super();
    }

    public AdqBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public AdqBaseException(String message) {
        super(message);
    }

    public AdqBaseException(Throwable cause) {
        super(cause);
    }
}
