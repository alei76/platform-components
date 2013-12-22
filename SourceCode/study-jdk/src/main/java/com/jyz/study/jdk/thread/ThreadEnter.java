package com.jyz.study.jdk.thread;

public class ThreadEnter {

    public static void main(String[] args) {
	ThreadEnter enter = new ThreadEnter();
	enter.test(new Runnable() {
	    public void run() {
		try {
		    Thread.sleep(4000L);
		    System.out.println(1);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	});
	new Thread() {
	    public void run() {
		try {
		    Thread.sleep(4000L);
		    System.out.println(9);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	}.start();
	System.out.println(1.5);
	enter.test(new Runnable() {
	    public void run() {
		try {
		    Thread.sleep(2000L);
		    System.out.println(2);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	});
	System.out.println(2.5);
	enter.test(new Runnable() {
	    public void run() {
		try {
		    Thread.sleep(1000L);
		    System.out.println(3);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	});
    }
    
    private void test(Runnable r){
	r.run();
    }
}
