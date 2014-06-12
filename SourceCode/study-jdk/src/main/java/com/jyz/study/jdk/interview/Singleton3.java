package com.jyz.study.jdk.interview;

/**
 * 
 * @author JoyoungZhang@gmail.com
 *
 */
public class Singleton3 {
	
	private static volatile Singleton3 INSTANCE = null;
	
	private Singleton3(){}
	
	public static Singleton3 getInstance(){
		if(INSTANCE == null){
			synchronized(Singleton3.class){
				if(INSTANCE == null){
					INSTANCE = new Singleton3();
				}
			}
		}
		return INSTANCE;
	}

}
