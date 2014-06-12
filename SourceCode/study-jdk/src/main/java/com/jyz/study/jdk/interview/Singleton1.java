package com.jyz.study.jdk.interview;

/**
 * 饿汉模式
 * @author JoyoungZhang@gmail.com
 *
 */
public class Singleton1 {
	
	private static final Singleton1 INSTANCE = new Singleton1();
	
	private Singleton1(){}
	
	public static Singleton1 getInstance(){
		return INSTANCE;
	}

}
