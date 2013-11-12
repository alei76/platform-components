/*
 * 
 */
package com.audaque.lib.core.log.impl;

import com.audaque.lib.core.log.AppenderParam;
import com.audaque.lib.core.log.LogAppender;
import org.apache.log4j.ConsoleAppender;

/**
 * 默认的控制台实现，使用Apache的控制台
 * @author Wuwei <wei.wu@audaque.com>
 * @create 2013-3-14 16:56:05
 */
public class DefaultConsoleAppender extends ConsoleAppender implements LogAppender {

    @Override
    public LogAppender createLogAppender(AppenderParam... params) {
        DefaultConsoleAppender nc = new DefaultConsoleAppender();
        nc.setEncoding(getEncoding());
        nc.setFollow(getFollow());
        nc.setImmediateFlush(getImmediateFlush());
        nc.setLayout(getLayout());
        nc.setName(getName());
        nc.setTarget(getTarget());
        nc.setThreshold(getThreshold());
        nc.setErrorHandler(getErrorHandler());
        nc.activateOptions();
        return nc;
    }

    @Override
    public void flush() {
    }
}
