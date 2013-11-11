/*
 * 
 */
package com.audaque.lib.core.log;

import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.audaque.lib.core.log.impl.ConsoleParam;
import com.audaque.lib.core.log.impl.SingleFileParam;
import com.audaque.lib.core.utils.AdqArrayUtils;
import com.audaque.lib.core.utils.AdqFileUtils;

/**
 * 华傲日志工厂，生成日志实例
 * @author Wuwei <wei.wu@audaque.com>
 * @create Mar 11, 2013 2:05:42 PM
 * 
 */
public class AudaqueLoggerFactory {

    private static final String CATEGORY_DEFAULT = "Audaque";
    private static final String CATEGORY_SINGLEFILEANDCONSOLE = "SingleFileAndConsole";
    private static final String CATEGORY_ONLYSINGLEFILE = "OnlySingleFile";
    private static final String CATEGORY_ONLYCONSOLE = "OnlyConsole";
    private static final String APPENDER_SINGLEFILE1 = "SingleFile1";
    private static final String APPENDER_SINGLEFILE2 = "SingleFile2";
    private static final String APPENDER_CONSOLE1 = "Console1";
    private static final String APPENDER_CONSOLE2 = "Console2";
    private static final Map<String, Logger> loggerCache = new HashMap<String, Logger>();

    static {
        PropertyConfigurator.configure(AdqFileUtils.getSystemFileCanonicalPath("etc", "log4j.properties"));
    }

    /**
     * 将日志输出到配置的message.log文件
     * 
     * @return
     */
    public static Logger getLogger() {
        return Logger.getLogger(CATEGORY_DEFAULT);
    }

    /**
     * 将日志输出到指定的文件 调用该方法打印日志，当不再使用时请调用close方法
     * 
     * @param file
     * @return
     */
    public static Logger getFileLogger(File file) {
        SingleFileParam sfp = new SingleFileParam();
        sfp.setAppenderName(APPENDER_SINGLEFILE2);
        sfp.setParams(file);
        return getLogger(CATEGORY_ONLYSINGLEFILE, sfp);
    }

    /**
     * 将日志输出到指定的控制台 当前客户端的控制台就是NetBeans的控制台，服务端的控制台就是命令行
     * 调用该方法打印日志，当不再使用时请调用close方法
     * 
     * @param labelName
     * @return
     */
    public static Logger getConsoleLogger(String labelName) {
        ConsoleParam cp = new ConsoleParam();
        cp.setAppenderName(APPENDER_CONSOLE2);
        cp.setParams(labelName);
        return getLogger(CATEGORY_ONLYCONSOLE, cp);
    }

    /**
     * 将日志输出到指定的文件和指定的控制台 调用该方法打印日志，当不再使用时请调用close方法
     * 
     * @param file
     * @param labelName
     * @return
     */
    public static Logger getLogger(File file, String labelName) {
        SingleFileParam sfp = new SingleFileParam();
        sfp.setAppenderName(APPENDER_SINGLEFILE1);
        sfp.setParams(file);
        ConsoleParam cp = new ConsoleParam();
        cp.setAppenderName(APPENDER_CONSOLE1);
        cp.setParams(labelName);
        return getLogger(CATEGORY_SINGLEFILEANDCONSOLE, sfp, cp);
    }

    /**
     * 将日志输出到指定的分组
     * 
     * @param category
     * @param param
     *            文件名和窗口名
     * @return
     */
    public static Logger getLogger(String category, AppenderParam... param) {
        String key = getKey(category, param);
        Logger logger = loggerCache.get(key);
        if (logger != null) {
            // 使用缓存的Log
            return logger;
        }
        Logger templateLog = Logger.getLogger(category);
        Enumeration it = templateLog.getAllAppenders();
        if (!it.hasMoreElements()) {
            // 指定的分组不存在，使用默认分组
            return Logger.getLogger(CATEGORY_DEFAULT);
        }
        Logger newLog = Logger.getLogger(key);
        if (newLog.getAllAppenders().hasMoreElements()) {
            // 指定的分组已经被Log4j创建
            loggerCache.put(key, newLog);
            return newLog;
        }
        it = templateLog.getAllAppenders();
        while (it.hasMoreElements()) {
            Object obj = it.nextElement();
            if (obj != null && obj instanceof LogAppender) {
                LogAppender app = (LogAppender) obj;
                if (app != null) {
                    newLog.addAppender(app.createLogAppender(param));
                }
            }
        }

        loggerCache.put(key, newLog);
        return newLog;
    }

    /**
     * 关闭日志
     * @param logger 
     */
    public static void close(Logger logger) {
        if (logger == null) {
            return;
        }
        String name = logger.getName();
        loggerCache.remove(name);
        Enumeration it = logger.getAllAppenders();
        while (it.hasMoreElements()) {
            Object obj = it.nextElement();
            if (obj != null && obj instanceof LogAppender) {
                LogAppender app = (LogAppender) obj;
                app.close();
            }
        }
    }

    /**
     * 刷新日志，将内存中的日志更新到指定位置
     * @param logger 
     */
    public void flush(Logger logger) {
        if (logger == null) {
            return;
        }
        Enumeration it = logger.getAllAppenders();
        while (it.hasMoreElements()) {
            Object obj = it.nextElement();
            if (obj != null && obj instanceof LogAppender) {
                LogAppender app = (LogAppender) obj;
                app.flush();
            }
        }
    }

    private static String getKey(String category, AppenderParam... params) {
        return category + AdqArrayUtils.toString(params);
    }
}
