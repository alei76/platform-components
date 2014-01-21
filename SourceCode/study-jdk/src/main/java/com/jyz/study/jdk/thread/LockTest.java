package com.jyz.study.jdk.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *  Lock接口的方法标准用法
 *	@author zhaoyong.zhang
 *	create time 2014-1-21
 */
public class LockTest {

	public static void main(String[] args) {
		final LockTestTT tt = new LockTestTT();
		LockTest test = new LockTest();
		
//		test.test1(tt);
//		test.test2(tt);
//		test.test3(tt);
		test.test4(tt);
	}
	
	void test1(final LockTestTT tt){
		Thread t1 = new Thread(){
			public void run() {tt.t1();};
		};
		t1.start();
		Thread t2 = new Thread(){
			public void run() {tt.t2();};
		};
		t2.start();
	}
	
	void test2(final LockTestTT tt){
		Thread t1 = new Thread(){
			public void run() {tt.t1();};
		};
		t1.start();
		Thread t2 = new Thread(){
			public void run() {tt.t3();};
		};
		t2.start();
	}
	
	void test3(final LockTestTT tt){
		Thread t1 = new Thread(){
			public void run() {tt.t1();};
		};
		t1.start();
		Thread t2 = new Thread(){
			public void run() {
				try {
					tt.t4();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			};
		};
		t2.start();
	}
	
	void test4(final LockTestTT tt){
		Thread t1 = new Thread(){
			public void run() {tt.t1();};
		};
		t1.start();
		Thread t2 = new Thread(){
			public void run() {
				try {
					tt.t5();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			};
		};
		t2.start();
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		t2.interrupt();
	}

}

class LockTestTT{
	Lock lock = new ReentrantLock();
	void t1(){
		lock.lock();
		try{
			TimeUnit.SECONDS.sleep(5);
			System.out.println("t1 happend");
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}finally{
			lock.unlock();
		}
	}
	
	void t2(){
		lock.lock();
		try{
			System.out.println("t2 happend");
		}finally{
			lock.unlock();
		}
	}
	void t5() throws InterruptedException{
		lock.lockInterruptibly();
		try {
			System.out.println("t5 happend");
		}finally{
			lock.unlock();
		}
	}
	
	void t3(){
		if(lock.tryLock()){
			try{
				System.out.println("t3 happend");
			}finally{
				lock.unlock();
			}
		}else{
			System.out.println("can't get lock");
		}
	}
	void t4() throws InterruptedException{
		final int unit = 6;
		if(lock.tryLock(unit, TimeUnit.SECONDS)){
			try{
				System.out.println("t4 happend");
			}finally{
				lock.unlock();
			}
		}else{
			System.out.println(unit + " seconds can't get lock");
		}
	}
}
