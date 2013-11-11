/*
 * 
 */
package com.audaque.lib.core.log.impl;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.RollingFileAppender;

import com.audaque.lib.core.log.AppenderParam;
import com.audaque.lib.core.log.LogAppender;

/**
 * 文件日志的实现
 * @author Wuwei <wei.wu@audaque.com>
 * @create Mar 11, 2013 3:20:13 PM
 *
 */
public class SingleFileAppender extends RollingFileAppender implements LogAppender {

    public SingleFileAppender() {
    }

    public LogAppender createLogAppender(AppenderParam... params) {
        if (ArrayUtils.isEmpty(params)) {
            return null;
        }
        for (AppenderParam param : params) {

            if (param == null || !(param instanceof SingleFileParam)) {
                continue;
            }
            SingleFileParam sfp = (SingleFileParam) param;
            if (!sfp.getAppenderName().equals(getName())) {
                continue;
            }
            if (ArrayUtils.isEmpty(sfp.getParams())) {
                continue;
            }
            try {
                File file = sfp.getParams()[0];
                SingleFileAppender newAppender = new SingleFileAppender();
                newAppender.setName(getName());
                //newAppender.setAppend(getAppend());
                //newAppender.setBufferSize(getBufferSize());
                //newAppender.setBufferedIO(getBufferedIO());
                newAppender.setFile(file.getAbsolutePath(), getAppend(), getBufferedIO(), getBufferSize());
                newAppender.setLayout(getLayout());
                newAppender.setThreshold(getThreshold());
                newAppender.setMaxBackupIndex(getMaxBackupIndex());
                newAppender.setMaximumFileSize(getMaximumFileSize());
                return newAppender;
            } catch (IOException ex) {
                System.out.println("初始化SingleFileAppender异常");
            }
        }
        return null;
    }

    @Override
    public void close() {
        super.close();
    }

    public void flush() {
        if (qw != null) {
            qw.flush();
        }
    }
}
