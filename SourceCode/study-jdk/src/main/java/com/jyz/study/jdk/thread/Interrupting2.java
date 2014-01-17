package com.jyz.study.jdk.thread;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

//可以通过Thread.interrupt()方法中断阻塞
class SleepBlocked implements Runnable {
	public void run() {
		try {
			TimeUnit.SECONDS.sleep(100);
		} catch (InterruptedException e) {
			System.out.println("InterruptedException");
		}
		System.out.println("Exiting SleepBlocked.run()");
	}
}
//通过Thread.interrupt()方法不能中断阻塞
class IOBlocked implements Runnable {
	private InputStream in;

	public IOBlocked(InputStream is) {
		in = is;
	}

	public void run() {
		try {
			System.out.println("Waiting for read():");
			in.read();
		} catch (IOException e) {
			if (Thread.currentThread().isInterrupted()) {
				System.out.println("isInterrupted true," + e);
				System.out.println("Interrupted from blocked I/O");
			} else {
				System.out.println("isInterrupted false," + e);
				throw new RuntimeException(e);
			}
		}
		System.out.println("Exiting IOBlocked.run()");
	}
}
//通过Thread.interrupt()方法不能中断阻塞
//可以使用Lock代替Synchronized，使用lock.lockInterruptibly()来中断
class SynchronizedBlocked implements Runnable {
	public synchronized void f() {
		while (true)
			// Never releases lock
			Thread.yield();
	}
	public synchronized void g() {
		System.out.println("g happend");
	}

	public SynchronizedBlocked() {
		new Thread() {
			public void run() {
				f(); // Lock acquired by this thread
			}
		}.start();
	}

	public void run() {
		System.out.println("Trying to call f()");
		g();
		System.out.println("Exiting SynchronizedBlocked.run()");
	}
}

/**
 *	@author zhaoyong.zhang
 *	create time 2014-1-17
 */
public class Interrupting2 {
	private static ExecutorService exec = Executors.newCachedThreadPool();

	static void test(Runnable r) throws InterruptedException {
		//关于Future/Executor/ExecutorService/FutureTask/shutdown()/shutdownNow()及 java.util.concurrent 包里面的类等找到工作后研究
        Future<?> f = exec.submit(r);
		TimeUnit.MILLISECONDS.sleep(100);
		System.out.println("Interrupting " + r.getClass().getName());
		f.cancel(true); // Interrupts if running
		System.out.println("Interrupt sent to " + r.getClass().getName());
	}

	public static void main(String[] args) throws Exception {
		//可以中断
//		test(new SleepBlocked());
		//不可以中断IO型的阻塞
		InputStream in = System.in;
		test(new IOBlocked(in));//System.in.read如果不输入会一直阻塞，无法取消
		//不可以中断 synchronized 型的阻塞
//		test(new SynchronizedBlocked());
		TimeUnit.SECONDS.sleep(300);
		System.out.println("Aborting with System.exit(0)");
		System.exit(0); // ... since last 2 interrupts failed
	}
}
