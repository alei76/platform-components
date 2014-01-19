package com.jyz.study.jdk.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *  isShutdown
 *  	调用shutdown或者shutdownNow就返回true
 *  isTerminated
 *  	调用shutdown或者shutdownNow后，所有任务都已完成(无论正常完成还是被Interrupted)，返回true
 *  awaitTermination(long timeout, TimeUnit unit)
 *   	规定时间类任务完成，返回true，否则返回false
 *  
 *  shutdown：不接受新任务，等待已经接受的任务完成。如果任务永远完不成，如InterruptedThreadWay1 3，则永远无法终止，isTerminated返回false
 *  shutdownNow：调用Thread.interrupt()来中断任务，如果任务无法响应中断，如InterruptedThreadWay1，则永远无法终止,isTerminated返回false
 *	@author zhaoyong.zhang
 *	create time 2014-1-16
 */
public class ExecutorServiceTest {

	public static void main(String[] args) throws InterruptedException {
		
		ExecutorService service = Executors.newCachedThreadPool();
		for(int i=0;i<2;i++){
			service.submit(new InterruptedThreadWay3());
		}
		System.out.println("service.isShutdown() is " + service.isShutdown());
		System.out.println("service.isTerminated() is " + service.isTerminated());
		
		service.shutdown();
		System.out.println("service.isShutdown() is " + service.isShutdown());
		System.out.println("service.isTerminated() is " + service.isTerminated());
		
		service.shutdownNow();
		System.out.println("service.isShutdown() is " + service.isShutdown());
		Thread.sleep(5000);
		System.out.println("service.isTerminated() is " + service.isTerminated());
		System.out.println("service.awaitTermination() is " + service.awaitTermination(1, TimeUnit.SECONDS));
	}

}
