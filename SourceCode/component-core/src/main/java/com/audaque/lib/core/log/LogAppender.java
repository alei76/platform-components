/*
 * 
 */
package com.audaque.lib.core.log;

import org.apache.log4j.Appender;

/**
 * 扩展Apache的Appender类
 * @author Wuwei <wei.wu@audaque.com>
 * @create Mar 13, 2013 8:29:46 AM
 *
 */
public interface LogAppender extends Appender {

    LogAppender createLogAppender(AppenderParam... params);

    void close();

    void flush();
}
