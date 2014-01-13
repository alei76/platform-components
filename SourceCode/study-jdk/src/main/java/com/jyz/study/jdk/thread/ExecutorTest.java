package com.jyz.study.jdk.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorTest {

	//最好使用ExecutorService, Executor没有关闭接口
	final static Executor service = Executors.newCachedThreadPool();
	final static ExecutorService executorService = Executors.newCachedThreadPool();
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		service.execute(new Thread(){
			@Override
			public void run() {
				System.out.println("run1");
			}
		});
		executorService.execute(new Thread(){
			@Override
			public void run() {
				System.out.println("run1");
			}
		});
		System.out.println(executorService.submit(new Thread(){
			@Override
			public void run() {
				System.out.println("run2");
			}
		}).get());
		
		System.out.println(executorService.submit(new Callable<Bean>() {
			@Override
			public Bean call() throws Exception {
				Thread.sleep(5000L);
				return new Bean();
			}
		}).get());
		executorService.shutdownNow();
	}
}

class Bean{
	@Override
	public String toString() {
		return super.toString();
	}
}
