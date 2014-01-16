package com.jyz.study.jdk.thread;

public class SimulateCASTest {

	SimulateCAS cas = new SimulateCAS();
	
	public int get(){
		return cas.get();
	}
	
	public void increment(){
//		int v;
//		do{
//			v = get();
//		}while();
//		return v + 1;
		int v = get();
		while(v != cas.compareAndSwap(v, v + 1)){
			v = get();
		}
	}
	
	public static void main(String[] args) {
		SimulateCASTest test = new SimulateCASTest();
		System.out.println(test.get());
		test.increment();
		System.out.println(test.get());
	}
	
	

}
