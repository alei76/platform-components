package com.jyz.study.jdk.thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *  http://blog.chinaunix.net/uid-122937-id-215995.html 
 *   	可中断阻塞的结束方法
 *   	不可中断阻塞的结束方法
 *   
 *  http://www.linuxidc.com/Linux/2011-09/43560.htm
 *  	可中断的阻塞和不可中断的阻塞
 *  
 *  http://www.ibm.com/developerworks/cn/java/j-jtp05236.html
 *  如果那个线程在执行一个低级可中断阻塞方法，
 *  	例如 Thread.sleep()、 Thread.join() 或 Object.wait()，那么它将取消阻塞并抛出 InterruptedException。并还原中断状态为false
 *  否则， interrupt() 只是设置线程的中断状态为true
 *	@author zhaoyong.zhang
 *	create time 2014-1-15
 */
public class InterruptedThreadWay {
	public static void main(String[] args) throws InterruptedException, IOException {
		Thread thread = new Thread(new InterruptedThreadWay3());
		thread.start();
		Thread.sleep(100);
        System.out.println("****************************");
        System.out.println("Interrupted Thread!");
        System.out.println("****************************");
        thread.interrupt();
        
//		InterruptedThreadWay5 runnable5 = new InterruptedThreadWay5();
//        Thread thread5 = new Thread(runnable5);
//        thread5.start();
//        Thread.sleep(100);
//        System.out.println("****************************");
//        System.out.println("Interrupted Thread!");
//        System.out.println("****************************");
//        runnable5.stop=true;
		
//        InterruptedThreadWay6 runnable6 = new InterruptedThreadWay6();
//        Thread thread6 = new Thread(runnable6);
//        thread6.start();
//        Thread.sleep(100);
//        System.out.println("****************************");
//        System.out.println("Interrupted Thread!");
//        System.out.println("****************************");
////        thread6.interrupt();//由于socket.accept()阻塞不可中断，interrupt不产生任何作用
//        runnable6.stop = true;//首先停止标记
//        runnable6.socket.close();//其次通过关闭socket连接来中断socket.accept()的阻塞
	}

}

//中断一个正在执行的线程，但是并未结束线程
//中断一个正在执行的线程是没有效果的，只有中断一个阻塞的线程才有效果
class InterruptedThreadWay1 implements Runnable{
	private double d = 0.0;
	@Override
	public void run() {
		while(true){
//			System.out.println("i am running");
			for (int i = 0; i < 900000; i++) {
	            d = d + (Math.PI + Math.E) / d;
	        }
			Thread.yield();
		}
	}
}

//通过线程sleep时调用Interrupt引发异常，来结束线程.
//但不怎么保险，因为不能保证中断时刻一定在sleep时
class InterruptedThreadWay2 implements Runnable{
	private double d = 0.0;
	@Override
	public void run() {
		try{
			while(true){
				System.out.println("i am running");
				for (int i = 0; i < 900000; i++) {
					d = d + (Math.PI + Math.E) / d;
				}
				Thread.sleep(50L);
			}
		}catch(InterruptedException ex){
			System.out.println("catch InterruptedException");
		}
		System.out.println("end run");
	}
}

//通过检查线程中断状态，来结束线程
//此时中断线程，要等下一次判断中断Thread.interrupted()状态时生效
class InterruptedThreadWay3 implements Runnable{
	private double d = 0.0;
	@Override
	public void run() {
		while(!Thread.interrupted()){
//			System.out.println("i am running");
			for (int i = 0; i < 900000; i++) {
				d = d + (Math.PI + Math.E) / d;
			}
		}
		System.out.println("end run");
	}
}


//通过检查线程中断状态3和sleep期间抛出异常2两个措施，来结束线程
//如果sleep期间被中断，立马抛出InterruptedException结束线程
//如果其它期间被打断，则等到下一次对线程状态的判断时结束线程
class InterruptedThreadWay4 implements Runnable{
	private double d = 0.0;
	@Override
	public void run() {
		try {
            while (!Thread.interrupted()) {
                System.out.println("I am running!");
                Thread.sleep(10);
                System.out.println("Calculating");
                for (int i = 0; i < 900000; i++) {
                    d = d + (Math.PI + Math.E) / d;
                }
            }
        } catch (InterruptedException e) {
            System.out.println("catch InterruptedException");
        }
		System.out.println("end run");
	}
}

//通过共享变量中断线程，结束线程的执行
class InterruptedThreadWay5 implements Runnable{
	private double d = 0.0;
	volatile boolean stop = false;
	@Override
	public void run() {
        while (!stop) {
            System.out.println("I am running!");
            System.out.println("Calculating");
            for (int i = 0; i < 900000; i++) {
                d = d + (Math.PI + Math.E) / d;
            }
        }
		System.out.println("end run");
	}
}

//通过关闭socket连接
//同时配合共享变量的使用
//来结束不可中断的阻塞
class InterruptedThreadWay6 implements Runnable{
	volatile ServerSocket socket;
	volatile boolean stop = false;
	@Override
	public void run() {
		try {
            socket = new ServerSocket(7856);
        } catch (IOException e) {
            System.out.println("Could not create the socket...");
            return;
        }
        while (!stop) {
            System.out.println("Waiting for connection...");
            try {
                Socket sock = socket.accept();
            } catch (IOException e) {
                System.out.println("accept() failed or interrupted...");
            }
        }
        System.out.println("end run");
	}
}

