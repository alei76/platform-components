package com.jyz.study.jdk.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *  1.演示CountDownLatch
 *  2.演示内存一致性效果：
 *    线程中调用 countDown() 之前的操作(如注释代码1) happen-before 紧跟在从另一个线程中对应 await() 成功返回的操作。
 *    所以注释1和注释2重排序
 *	@author zhaoyong.zhang
 *	create time 2014-1-15
 */
public class CopyOfCountDownLatch2 {
	
	static final int count = 10000;
	
	public static void main(String[] args) throws InterruptedException {
		final CountDownLatch latch = new CountDownLatch(count);
		final AtomicInteger num = new AtomicInteger(0);
		long begin = System.currentTimeMillis();
		for(int i=0;i<count;i++){
			Thread preThread = new Thread(new PreThread(num, latch));
			preThread.start();
		}
		System.out.println("await 之前 spend " + (System.currentTimeMillis() - begin) + "s");//1
		latch.await();//2
		System.out.println("await 之后 spend " + (System.currentTimeMillis() - begin) + "s");
		System.out.println(num.get());
	}

	static class PreThread implements Runnable{
		private AtomicInteger num;
		private CountDownLatch latch;
		public PreThread(AtomicInteger num, CountDownLatch latch) {
			this.num= num;
			this.latch= latch;
		}
		@Override
		public void run() {
			try{
				num.incrementAndGet();
			}finally{
				latch.countDown();
			}
		}
		
	}
	
}


