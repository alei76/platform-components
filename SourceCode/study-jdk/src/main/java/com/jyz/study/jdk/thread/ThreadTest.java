package com.jyz.study.jdk.thread;

public class ThreadTest {
	public static void main(String[] args) {
		final MyObject obj = new MyObject();
		final MyObject obj2 = new MyObject();
		//线程1
		Runnable r1 = new Runnable(){
			public void run(){
				obj.getName(1);
			}
		};
		Thread t1 = new Thread(r1);
		t1.start();
//		//线程2
		Runnable r2 = new Runnable(){
			public void run(){
				obj.getAge(2);
			}
		};
		Thread t2 = new Thread(r2);
		t2.start();
		//线程3
//		Runnable r3 = new Runnable(){
//			public void run(){
//				MyObject.getNameStatic(3);
//			}
//		};
//		Thread t3 = new Thread(r3);
//		t3.start();
//		
//		//线程4
		Runnable r4 = new Runnable(){
			public void run(){
				obj2.getName(4);
			}
		};
		Thread t4 = new Thread(r4);
		t4.start();
	}
}
class MyObject {
    	private static final Object object = new Object();//static or not different
	public String getName(int i) {
		synchronized (object) {
			try {
				System.out.println("线程" + i + ":getName sleep 10 secs start");
				Thread.sleep(1000 * 10);
				System.out.println("线程" + i + ":getName sleep 10 secs end");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "";
		}
	}
	public String getAge(int i) {
		synchronized (object) {
			try {
				System.out.println("线程" + i + ":getAge sleep 10 secs start");
				Thread.sleep(1000 * 10);
				System.out.println("线程" + i + ":getAge sleep 10 secs end");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "";
		}
	}
	
	public static String getNameStatic(int i) {
		synchronized (MyObject.class) {
			try {
				System.out.println("线程" + i + ":getNameStatic sleep 10 secs start");
				Thread.sleep(1000 * 10);
				System.out.println("线程" + i + ":getNameStatic sleep 10 secs end");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "";
		}
	}
}