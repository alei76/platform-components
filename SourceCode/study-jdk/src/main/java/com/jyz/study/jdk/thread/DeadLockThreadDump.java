package com.jyz.study.jdk.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 死锁
 * @author JoyoungZhang@gmail.com
 *
 */
public class DeadLockThreadDump {
   public static void main(String[] args) {
       final LeftRightDeadLock deadLock = new LeftRightDeadLock();
       final Semaphore semp = new Semaphore(2000);
       final ExecutorService service = Executors.newCachedThreadPool();
       for(int i=0;i<1000;i++){
	   service.submit(new Thread(){
	       public void run() {
		   try {
		       semp.acquire();
		       deadLock.leftRight();
		   } catch (InterruptedException e) {
		       e.printStackTrace();
		   }finally{
		       semp.release();
		   }
	       };
	   });
       }
       for(int i=0;i<1000;i++){
	   service.submit(new Thread(){
	       public void run() {
		   try {
		       semp.acquire();
		       deadLock.rightLeft();
		   } catch (InterruptedException e) {
		       e.printStackTrace();
		   }finally{
		       semp.release();
		   }
	       };
	   });
       }
       service.shutdown();
   }
}


class LeftRightDeadLock{
    private final Object left = new String("left");
    private final Object right = new String("right");
    
    public void leftRight() throws InterruptedException{
	synchronized (left) {
	    synchronized (right) {
		Thread.yield();
		System.out.println("leftRight");
	    }
	}
    }
    public void rightLeft() throws InterruptedException{
	synchronized (right) {
	    synchronized (left) {
		Thread.yield();
		System.out.println("rightLeft");
	    }
	}
    }
}
