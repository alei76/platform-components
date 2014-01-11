package com.jyz.study.jdk.thread;

/**
 *  http://coolxing.iteye.com/blog/1474375
 *  1.线程间断只是一个状态，不代表run不能继续执行了，
 *   Thread.interrupt()方法不会中断一个正在运行的线程。它的作用是，在线程受到阻塞时抛出一个中断信号，这样线程就得以退出阻塞的状态
 *  2.我们应该根据这个状态来处理如下操作
 *   2.1 作为while条件(主动操作)
 *   2.2 中断如wait等可中断方法(被动操作)
 *   
 *   
 *  如果打断发生时线程阻塞在sleep，wait之类的方法中，那它们会重置interrupted状态并抛出InterruptedException。这意味着在抓InterruptedException的时候调用isInterrupted会返回false。
	如果打断发生在线程正常运行时，那打断只会将interrupted标志改成true，线程需要自己检查并清除这个标志。
	如果在进入阻塞方法之前线程就已经被打断并且interrupted标志未被重置，那这些阻塞方法会立刻抛出InterruptedException
 *	@author zhaoyong.zhang
 *	create time 2014-1-9
 */
public class InterruptedExceptionHandler implements Runnable {
	
	private Object lock = new Object();

	@Override
	public void run() {
//		while (true) {
//			dosomething();
//		}
		while (!Thread.currentThread().isInterrupted()) {
			dosomething();
		}
//		while (!Thread.interrupted()) {
//			dosomething();
//		}
		System.out.println(Thread.currentThread().isInterrupted());
		System.out.println("can run !!");
	}

	private void dosomething() {
		System.out.println("enter dosomething");
		try {
			// Object.wait是一个可中断的阻塞方法, 如果在其阻塞期间检查到当前线程的中断标记为true,
			// 会重置中断标记后从阻塞状态返回, 并抛出InterruptedException异常
//			synchronized (lock) {
//				lock.wait();
//			}
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			System.out.println("InterruptedException happened");
			// catch住InterruptedException后设置当前线程的中断标记为true, 以供调用栈上层进行相应的处理
			// 在此例中, dosomething方法的调用栈上层是run方法.状态传递！！！！！！！！！！！！！！
			System.out.println("1 " + Thread.currentThread()+ " is " + Thread.currentThread().isInterrupted());
			System.out.println("1 " + Thread.currentThread()+ " is " + Thread.interrupted());
			System.out.println("1 " + Thread.currentThread()+ " is " + Thread.currentThread().isInterrupted());
			Thread.currentThread().interrupt();//必须有！！！！！！！！！！！！！！！!!!!!!!!!!!!!!!!!!!!!!!!!!!!!没有run()方法不会终止
			System.out.println("2 " + Thread.currentThread()+ " is " + Thread.currentThread().isInterrupted());
			System.out.println("2 " + Thread.currentThread()+ " is " + Thread.interrupted());
			System.out.println("2 " + Thread.currentThread()+ " is " + Thread.currentThread().isInterrupted());
			Thread.currentThread().interrupt();//必须有！！！！！！！！！！！！！！！!!!!!!!!!!!!!!!!!!!!!!!!!!!!!没有run()方法不会终止
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Thread t = new Thread(new InterruptedExceptionHandler());
		t.start();
		// 启动线程1s后设置其中断标记为true
		Thread.sleep(2000);
		t.interrupt();
		System.out.println("3 " + t + " is " + t.isInterrupted());
		System.out.println("3 " + Thread.currentThread()+ " is " + Thread.currentThread().isInterrupted());
	}
	
}