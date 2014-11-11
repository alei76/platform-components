package com.jyz.study.jdk.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 一次最多一个任务运行
 * 
 * @author zhaoyong.zhang create time 2014-1-22
 */
public class SingleRun {

    public static void main(String[] args) {
	ExecutorService service = Executors.newFixedThreadPool(1);
	for (int i = 0; i < 10; i++) {
	    final int ii = i;
	    service.submit(new Runnable() {
		@Override
		public void run() {
		    try {
			Thread.sleep(2000L);
			System.out.println("run" + ii);
		    } catch (InterruptedException e) {
			e.printStackTrace();
		    }
		}
	    });
	}
	service.shutdown();
    }

}
