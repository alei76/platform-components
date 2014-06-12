package com.jyz.study.jdk.interview;

/**
 * 懒汉模式
 * @author JoyoungZhang@gmail.com
 *
 */
public class Singleton2 {
	
	private static Singleton2 INSTANCE = null;//不能使用final！！！
	
	private Singleton2(){}
	
	public static synchronized  Singleton2 getInstance(){
		if(INSTANCE == null){
			INSTANCE = new Singleton2();
		}
		return INSTANCE;
	}

}
