package com.jyz.study.jdk.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *	getTask返回一个对象，没返回前如果中断，忽略此中断继续获取对象，直到获取到为止
 * 	最后finally里向上层重新恢复中断状态
 *	@author zhaoyong.zhang
 *	create time 2014-1-9
 */
public class InterruptedExceptionContinueHandler2 implements Runnable {
	private BlockingQueue<Integer> queue;

	public InterruptedExceptionContinueHandler2(BlockingQueue<Integer> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
	    while(!Thread.interrupted()){
		System.out.println(getTask());
	    }
	}
	
	public Integer getTask(){
	    boolean cancelled = false;
	    try{
		while(true){
		    try{
			return queue.take();
		    }catch(Exception ex){
			System.out.println(ex);
			cancelled = true;
		    }
		}
	    }finally{
		if(cancelled){
		    Thread.currentThread().interrupt();
		}
	    }
	}

	public static void main(String[] args) throws InterruptedException {
	      	LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>();
		Thread t = new Thread(new InterruptedExceptionContinueHandler2(queue));
		t.start();
		
		// 启动线程2ms后设置其中断标记为true
		Thread.sleep(1000);
		t.interrupt();
		
		queue.put(2);
	}
}
