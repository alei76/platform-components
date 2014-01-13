package com.jyz.study.jdk.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *  http://zy19982004.iteye.com/blog/1633148
 *  output可能情况
 *  //0-4 10-14任务立马创线程执行
 *  0=====Thread[0,5,main]
	2=====Thread[2,5,main]
	10=====Thread[10,5,main]
	3=====Thread[3,5,main]
	1=====Thread[1,5,main]
	4=====Thread[4,5,main]
	11=====Thread[11,5,main]
	12=====Thread[12,5,main]
	14=====Thread[14,5,main]
	13=====Thread[13,5,main]
	//5-9先放在LinkedBlockingQueue里，等有闲置线程时执行
	5=====Thread[5,5,main]
	6=====Thread[6,5,main]
	9=====Thread[9,5,main]
	8=====Thread[8,5,main]
	7=====Thread[7,5,main]
	//不加service.shutdown()的情况下
	//10s后收缩到 corePoolSize 的大小
 *	@author zhaoyong.zhang
 *	create time 2014-1-13
 */
public class ThreadPoolExecutorParmsTest {

	private static final ExecutorService service;
	
	static{
		service = new ThreadPoolExecutor(
				5, 
				10,
				10L, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>(5)
				);
	}
	
	public static void main(String[] args) {
		try{
			for(int i=0;i<15;i++){
				service.submit(new ElapsedTimeThread(i));
			}
		}finally{
//			service.shutdown();
		}
	}

	static class ElapsedTimeThread extends Thread{
		private int i;
		public ElapsedTimeThread(int i) {
			this.i= i;
			this.setName(String.valueOf(i));
		}
		@Override
		public void run() {
			try {
				Thread.sleep(5 * 1000L);
				System.out.println(String.valueOf(i) + "=====" +  this);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
