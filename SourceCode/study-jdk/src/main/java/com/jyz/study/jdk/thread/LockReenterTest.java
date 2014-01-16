package com.jyz.study.jdk.thread;

/**
 *  锁可以重入
 *  线程在试图获得它自己占有的锁时, 请求线程将会成功.
 *  重进入意味着所有的请求都是基于"每线程", 而不是基于"每调用". 
 *	@author zhaoyong.zhang
 *	create time 2014-1-16
 */
public class LockReenterTest {

	public static void main(String[] args) {
		new Thread(){
			public void run() {
				new LockReenterTest().f(10);
			};
		}.start();
	}
	
	private synchronized void f(int count){
		if(--count > 0){
			System.out.println("f count is " + count);
			g(count);
		}
	}
	
	private synchronized void g(int count){
		if(--count > 0){
			System.out.println("g count is " + count);
			f(count);
		}
	}

}
