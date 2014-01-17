package com.jyz.study.jdk.thread;

/**
 *  抛出InterruptedException和重置中断状态死循环
 *	@author zhaoyong.zhang
 *	create time 2014-1-17
 */
public class InterruptedCycle {

	public static void main(String[] args) throws InterruptedException {
		Thread t = new Thread(new InterruptedThread());
		t.start();
		Thread.sleep(2000);//防止t还没有启动就中断了
		t.interrupt();
	}

}


class InterruptedThread implements Runnable{

	@Override
	public void run() {
		while (true) {//不断循环是前提
			try {
				System.out.println("sleep 1s");
				System.out.println(Thread.currentThread().isInterrupted());
				Thread.sleep(1000L);//进入睡眠之前如果线程中断状态为true，立马InterruptedException
			} catch (InterruptedException e) {
				System.out.println(e);
				Thread.currentThread().interrupt();//而此时又重置中断状态为true
			}
		}
	}
	
}