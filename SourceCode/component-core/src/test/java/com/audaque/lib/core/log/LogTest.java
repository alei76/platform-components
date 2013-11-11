/*
 * 
 */
package com.audaque.lib.core.log;

import java.io.File;
import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author Wuwei <wei.wu@audaque.com>
 * @create 2013-3-14 16:36:18
 */
public class LogTest extends TestCase {

    public void testa(){
        
    }
    public void testLog() {
        Logger l = Logger.getLogger(LogTest.class);
        l.info("123123213");
        
        System.out.println("aaaa");
        Logger logger1 = AudaqueLoggerFactory.getLogger();
        logger1.info("全局日志打印");
        
        Logger logger2 = AudaqueLoggerFactory.getFileLogger(new File("./log/aaa.log"));
        logger2.info("单文件日志打印");
        
        Logger logger3 = AudaqueLoggerFactory.getConsoleLogger("窗口名1");
        logger3.info("窗口日志打印");
        
        Logger logger4 = AudaqueLoggerFactory.getLogger(new File("./log/bbbbb.log"), "窗口名2");
        logger4.info("单文件|窗口日志打印");
    }
}
