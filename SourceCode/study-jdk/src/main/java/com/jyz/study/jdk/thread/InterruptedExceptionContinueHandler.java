package com.jyz.study.jdk.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *  http://coolxing.iteye.com/blog/1474375
 *	@author zhaoyong.zhang
 *	create time 2014-1-9
 */
public class InterruptedExceptionContinueHandler implements Runnable {
	private BlockingQueue<Integer> queue;

	public InterruptedExceptionContinueHandler(BlockingQueue<Integer> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			dosomething();
		}
		System.out.println(queue.size());
	}

	private void dosomething() {
		System.out.println("enter dosomething");
		// cancelled变量用于表明线程是否发生过中断
		boolean cancelled = false;
		for (int i = 0; i < 10000; i++) {
			try {
				queue.put(i);
			} catch (InterruptedException e) {
				// 就算发生了InterruptedException, 循环也希望继续运行下去, 此时将cancelled设置为true, 以表明遍历过程中发生了中断
				System.out.println("InterruptedException happened when i = " + i);
				cancelled = true;
			}
		}
		// 如果当前线程曾经发生过中断, 就将其中断标记设置为true, 以通知dosomething方法的上层调用栈
		if (cancelled) {
			Thread.currentThread().interrupt();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		Thread t = new Thread(new InterruptedExceptionContinueHandler(new LinkedBlockingQueue<Integer>()));
		t.start();
		
		// 启动线程2ms后设置其中断标记为true
		Thread.sleep(2);
		t.interrupt();
	}
}
