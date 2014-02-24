package com.jyz.study.jdk.thread;

/**
 *  http://www.blogjava.net/neverend/archive/2011/06/14/352310.html
 *	@author zhaoyong.zhang
 *	create time 2014-1-8
 */
class ThreadTestt implements Runnable {

	@Override
	public void run() {
		System.out.println("before sleep");
		try {
			Thread.sleep(5000);
			//InterruptedException - 如果任何线程中断了当前线程。当抛出该异常时，当前线程的中断状态 被清除。
		} catch (InterruptedException e) {
			System.out.println(Thread.currentThread().getName() + " isInterrupted is " + Thread.currentThread().isInterrupted());
			Thread.currentThread().interrupt();
			System.out.println(Thread.currentThread().getName() + " isInterrupted is " + Thread.currentThread().isInterrupted());
		}
		System.out.println("after sleep");
	}

}

public class ThreadInterruptedNormal {

	public static void main(String[] args) throws InterruptedException {

		Thread t = new Thread(new ThreadTestt(), "thread-1");
		t.start();
		Thread.sleep(2000);
		t.interrupt();
		Thread.sleep(5000);
		System.out.println(t + " is " + t.isInterrupted());//false
	}
}