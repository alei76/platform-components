package com.jyz.study.jdk.thread;

/**
 *  http://yeziwang.iteye.com/blog/844730
 *	@author zhaoyong.zhang
 *	create time 2014-1-8
 */
public class ThreadInterruptedReInterrupt extends Thread {
	
	public static void main(String[] args) throws InterruptedException {
		ThreadInterruptedReInterrupt t = new ThreadInterruptedReInterrupt();
		t.start();
		Thread.sleep(1000);
		t.interrupt();
	}

	@Override
	public void run() {
		doSth();
		doSthElse();
		doSthElse2();
		System.out.println("end run");
	}

	public synchronized void doSthElse()  {
		System.out.println("start doSthElse");
		// simulate doing sth else here
		// and then wait
		try {
			wait();
		} catch (InterruptedException ex) {
			System.out.println(ex);
			Thread.currentThread().interrupt();//必须！！！！！！！！！！！！！！！！
		}
		System.out.println("end doSthElse");
	}
	
	public synchronized void doSthElse2()  {
		System.out.println("start doSthElse2");
		try {
			// simulate doing sth here
			// and sleep a while
			Thread.sleep(2000);
		} catch (InterruptedException ex) {
			System.out.println(ex);
			Thread.currentThread().interrupt();//必须！！！！！！！！！！！！！！！！
		}
		System.out.println("end doSthElse2");
	}

	public void doSth()  {
		System.out.println("start doSth");
		try {
			// simulate doing sth here
			// and sleep a while
			Thread.sleep(2000);
		} catch (InterruptedException ex) {
			System.out.println(ex);
			Thread.currentThread().interrupt();//必须！！！！！！！！！！！！！！！！
		}
		System.out.println("end doSth");
	}
}