package com.jyz.study.jdk.thread;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

class PrimeProducer extends Thread {
    private final BlockingQueue<BigInteger> queue;
    PrimeProducer(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }
    public void run() {
        try {
            BigInteger p = BigInteger.ONE;
            while (!Thread.currentThread().isInterrupted())
                queue.put(p = p.nextProbablePrime());//@throws InterruptedException if interrupted while waiting
        } catch (InterruptedException consumed) {
            System.out.println(consumed);
        }
        System.out.println("end run");
    }
    public void cancel() { interrupt(); }
}

class PrimeProducer2 extends Thread {
    private final BlockingQueue<BigInteger> queue;
    private volatile boolean cancelled = false;
    PrimeProducer2(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }
    public void run() {
        try {
            BigInteger p = BigInteger.ONE;
            while (!cancelled)
                queue.put(p = p.nextProbablePrime());//@throws InterruptedException if interrupted while waiting
        } catch (InterruptedException consumed) {
            System.out.println(consumed);
        }
        System.out.println("end run");
    }
    public void cancel() { cancelled = true; }
}

/**
 *  1.BlockingQueue take get 时@throws InterruptedException if interrupted while waiting
 *  //@throws InterruptedException if interrupted while waiting
 *  2.PrimeProducer能被中断，interrupt就抛出异常
 *    PrimeProducer2的put一直阻塞，即使cancelled发生了改变，也检测不到
 *	@author zhaoyong.zhang
 *	create time 2014-1-17
 */
public class Interrupting {
	public static void main(String[] args) throws InterruptedException {
		BlockingQueue<BigInteger> queue = new LinkedBlockingQueue<BigInteger>(1);
		queue.put(BigInteger.ONE);
		
		PrimeProducer t = new PrimeProducer(queue);
		t.start();
		TimeUnit.MILLISECONDS.sleep(5000);
		t.cancel();
		
//		PrimeProducer2 t2 = new PrimeProducer2(queue);
//		t2.start();
//		TimeUnit.MILLISECONDS.sleep(5000);
//		t2.cancel();
	}
}
