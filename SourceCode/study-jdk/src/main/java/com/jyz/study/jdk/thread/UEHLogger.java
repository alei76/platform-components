package com.jyz.study.jdk.thread;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import com.jyz.component.core.logging.Log;
import com.jyz.component.core.logging.LogFactory;

/**
 * UncaughtExceptionHandler的用法
 * @author JoyoungZhang@gmail.com
 *
 */
public class UEHLogger implements UncaughtExceptionHandler{
    
    private static final Log LOG = LogFactory.getLog(UEHLogger.class);

    @Override
    public void uncaughtException(Thread t, Throwable e) {
	LOG.error("thread terminated with exception: " + t.getName(), e);
    }
    
    public static void main(String[] args) {
	System.out.println(Runtime.getRuntime().availableProcessors());
	ExecutorService service = Executors.newCachedThreadPool(new ThreadFactory() {
	    @Override
	    public Thread newThread(Runnable r) {
		Thread thread = new Thread(r);
		thread.setUncaughtExceptionHandler(new UEHLogger());
		return thread;
	    }
	}); 
	service.execute(new Runnable(){//submit无效！！！
	    @Override
	    public void run() {
	        System.out.println(1/0);
	    }
	});
    }

}
