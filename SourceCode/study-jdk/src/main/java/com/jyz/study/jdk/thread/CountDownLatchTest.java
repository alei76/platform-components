package com.jyz.study.jdk.thread;

import java.util.concurrent.CountDownLatch;

/**
 *  1.preThread线程执行3秒后, 主线程打印end main
 *  2.打开注释的代码，可以验证latch.await() throws InterruptedException
 *	@author zhaoyong.zhang
 *	create time 2014-1-15
 */
public class CountDownLatchTest {

	static final CountDownLatch latch = new CountDownLatch(1);
	
	public static void main(String[] args) throws InterruptedException {
		final Thread preThread = new Thread(new PreThread());
		preThread.start();
		
//		new Thread(){
//			public void run() {
//				try {
//					Thread.sleep(1000L);
//				} catch (InterruptedException e) {
//				}
//				preThread.interrupt();
//			}
//		}.start();
		
		latch.await();
		System.out.println("end main");
	}

	static class PreThread implements Runnable{

		@Override
		public void run() {
			try {
				Thread.sleep(3000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("end run");
			latch.countDown();
		}
		
	}
	
}


