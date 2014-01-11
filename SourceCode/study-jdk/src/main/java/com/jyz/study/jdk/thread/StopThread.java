package com.jyz.study.jdk.thread;

public class StopThread {
	
	public static void main(String[] args) throws InterruptedException {
//		Thread t1 = new Thread(new CanInterruptedThread1());
//		t1.start();
//		Thread.sleep(1000);
//		t1.interrupt();
		
		Thread t2 = new Thread(new CanInterruptedThread2());
		t2.start();
		Thread.sleep(1000);
		t2.interrupt();
		
		
	}

}

class CanInterruptedThread1 implements Runnable {
	@Override
	public void run() {
		while(!Thread.currentThread().isInterrupted()){//如果while(true) 无限循环
			dosth();
		}
	}

	private void dosth() {
		System.out.println("运算");
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();//必须传递给外层的run方法
		}
		
	}
}

class CanInterruptedThread2 implements Runnable {
	@Override
	public void run() {
		try {
			while(!Thread.currentThread().isInterrupted()){//可以为while(true)
				System.out.println("运算");
				Thread.sleep(100);
			}
		} catch (InterruptedException e) {
//			Thread.currentThread().interrupt();//已经在最外层的run方法里，可以吞掉。另外一种在main里也可以吞掉
		}
	}

}
	
