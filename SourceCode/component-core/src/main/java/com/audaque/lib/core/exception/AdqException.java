package com.audaque.lib.core.exception;

import java.util.Locale;

import com.audaque.lib.core.i18n.MessageResource;

/**
 * 业务异常
 * @author deshu.lin (deshu.lin@audaque.com)
 *
 */
public class AdqException extends AdqBaseException {

    private static final long serialVersionUID = 1L;
    private final static String SUBFIX_DETAIL = ".detail";
    private String errorCode;//错误码
    private Object[] params;

    /**
     * 构造器
     * @param errorCode    错误码
     * @param params   参数
     */
    public AdqException(String errorCode, Object... params) {
        this.errorCode = errorCode;
        this.params = params;
    }

    /**
     * 构造器
     * @param errorCode    错误码
     */
    public AdqException(String errorCode) {
        this.errorCode = errorCode;
    }
    
    public AdqException(long errorCode, Object... params) {
        this(Long.toString(errorCode), params);
    }
    
    public AdqException(int errorCode, Object[] params) {
        this(Integer.toString(errorCode), params);
    }

    /**
     * 构造器
     * @param throwable    嵌套异常
     * @param errorCode    错误码
     * @param params   参数
     */
    public AdqException(Throwable throwable, String errorCode, Object... params) {
        super(throwable);
        this.errorCode = errorCode;
        this.params = params;
    }
    
    public AdqException(Throwable throwable, long errorCode, Object... params) {
        super(throwable);
        this.errorCode = Long.toString(errorCode);
        this.params = params;
    }
    
    public AdqException(Throwable throwable, int errorCode, Object... params) {
        super(throwable);
        this.errorCode = Integer.toString(errorCode);
        this.params = params;
    }

    @Override
    public String getMessage() {
        return getErrorMessage();
    }

    public String getErrorMessage() {
        return getErrorMessage(null);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public Object[] getParams() {
        return params;
    }

    /**
     * Get the error message for the error code
     * @param locale      the locale
     * @return     the message
     */
    public String getErrorMessage(Locale locale) {
        return MessageResource.getInstance().getMessage(errorCode, locale, params);
    }

    public String getDetailMessage() {
        return getDetailMessage(null);
    }

    /**
     * Get the detail message for the error code
     * @param locale    the locale
     * @return  the message
     */
    public String getDetailMessage(Locale locale) {
        return MessageResource.getInstance().getMessage(errorCode + SUBFIX_DETAIL, locale, params);
    }
}
