package com.jyz.study.jdk.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *  1.volatile不保证可见性
 *  2.AtomicInteger保证原子性和可见性
 *	@author zhaoyong.zhang
 *	create time 2014-1-15
 */
public class AtomicIntegerAndVolatile {

	private static volatile Integer num1 = 0;
	//没必要使用volatile，因为此时原子性可以保证可见性，某个线程使用完num2后，会刷新主存
	private static AtomicInteger num2 = new AtomicInteger(0);
	
	public static void main(String[] args) throws InterruptedException {
//		for(int i=0;i<10000;i++){
//			new Thread(){
//				public void run() {
//					num1 ++;
//				};
//			}.start();
//		}
//		Thread.sleep(10000); 
//		System.out.println(num1);//may 9838
		
		for(int i=0;i<10000;i++){
			new Thread(){
				public void run() {
					num2.incrementAndGet();
				};
			}.start();
		}
		Thread.sleep(10000); 
		System.out.println(num2);//must 10000
	}

}
