package com.jyz.study.jdk.interview;

/**
 * 主动使用一个类时，会导致类的初始化
 * 避免过早创建
 * @author JoyoungZhang@gmail.com
 *
 */
public class Singleton4 {

	private Singleton4(){}
	
	public static Singleton4 getInstance(){
		return Inner.INSTANCE;
	}
	
	private static class Inner{
		public static final Singleton4 INSTANCE = new Singleton4();
	}
}
