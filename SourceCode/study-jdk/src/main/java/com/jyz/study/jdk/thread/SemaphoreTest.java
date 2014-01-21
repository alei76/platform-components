package com.jyz.study.jdk.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 *  http://www.cnblogs.com/whgw/archive/2011/09/29/2195555.html
 *	@author zhaoyong.zhang
 *	create time 2014-1-21
 */
public class SemaphoreTest {

	public static void main(String[] args) {
		// 线程池
		ExecutorService exec = Executors.newCachedThreadPool();
		// 只能5个线程同时访问
		final Semaphore semp = new Semaphore(5);
		// 模拟20个客户端访问
		for (int index = 0; index < 20; index++) {
			final int NO = index;
			Runnable run = new Runnable() {
				public void run() {
					try {
						// 获取许可
						semp.acquire();
						System.out.println("Accessing: " + NO);
						Thread.sleep((long) (Math.random() * 10000));
						// 访问完后，释放
						semp.release();
						System.out.println("-----------------" + semp.availablePermits());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			exec.execute(run);
		}
		// 退出线程池
		exec.shutdown();
	}

}
