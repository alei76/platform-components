package com.jyz.study.jdk.thread;

/**
 * yield hold lock
 * @author JoyoungZhang@gmail.com
 *
 */
public class ThreadYieldLock {
    public static void main(String[] args) throws InterruptedException {
	final MyObject1 obj = new MyObject1();
	//线程1
	Runnable r1 = new Runnable(){
		public void run(){
			obj.getName(1);
		}
	};
	Thread t1 = new Thread(r1);
	t1.start();
//	//线程2
	Runnable r2 = new Runnable(){
		public void run(){
			obj.getAge(2);
		}
	};
	Thread t2 = new Thread(r2);
	t2.start();
    }
}

class MyObject1 {
    private final Object object = new Object();

    public String getName(int i) {
	synchronized (object) {
	    	System.out.println("线程" + i + ":getName yield start");
		while(true){
		    Thread.yield();
		}
	}
    }

    public String getAge(int i) {
	synchronized (object) {
		return "";
	}
    }
}
