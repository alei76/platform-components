package com.jyz.study.jdk.thread;

/**
 *  http://macrochen.iteye.com/blog/680380
 *  比较并交换(CompareAndSwap CAS)有三个操作数: 内存位置V, 旧的预期值A和新值B.
 *  当且仅当V符合旧值A时, CAS用新值B原子化地更新V的值, 否则它什么都不会做
 *	@author zhaoyong.zhang
 *	create time 2014-1-16
 */
public class SimulateCAS {

	private int value;
	
	public int get(){
		return value;
	}
	
	public int compareAndSwap(int expectedValue, int newValue){
		int oldValue = value;
		if(oldValue == expectedValue){
			value = newValue;
		}
		return oldValue;
	}
	
	public boolean compareAndSet(int expectedValue, int newValue){
		return expectedValue == compareAndSwap(expectedValue, newValue);
	}

}
