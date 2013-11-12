/*
 * 
 */
package com.audaque.lib.core.log;

/**
 * 日志参数
 * @author Wuwei <wei.wu@audaque.com>
 * @create 2013-3-14 15:35:18
 */
public abstract class AppenderParam<T> {

    private String appenderName;
    private T[] params;

    public String getAppenderName() {
        return appenderName;
    }

    public void setAppenderName(String appenderName) {
        this.appenderName = appenderName;
    }

    public T[] getParams() {
        return params;
    }

    public void setParams(T... params) {
        this.params = params;
    }

    @Override
    public String toString() {
        if (params == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(appenderName);
        for (T t : params) {
            sb.append(t.toString());
        }
        return sb.toString();
    }
}
