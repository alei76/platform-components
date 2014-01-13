package com.jyz.study.jdk.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class CookEgg {

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		//1.煮鸡蛋
		System.out.println("开始煮鸡蛋");
		FutureTask<Egg> task = new FutureTask<Egg>(new EggCallable());
		ExecutorService service = Executors.newSingleThreadExecutor();
		service.execute(task);
//		service.submit(task);
		//2.洗漱8分钟
		Thread.sleep(1000 * 8L);
		System.out.println("洗漱完毕");
		
		System.out.println("鸡蛋煮熟没: " + task.isDone());
		
		System.out.println(task.get().isHasCooked());
	}
}

class EggCallable implements Callable<Egg> {

	@Override
	public Egg call() throws Exception {
		Thread.sleep(1000 * 10L);  //鸡蛋煮10分钟
		return new Egg(true);
	}
	
}

class Egg {
	private boolean hasCooked;
	public Egg() {
	}
	public Egg(boolean hasCooked) {
		this.hasCooked = hasCooked;
	}

	public boolean isHasCooked() {
		return hasCooked;
	}
}