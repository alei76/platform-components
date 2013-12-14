package com.jyz.study.jdk.classLoader;

import junit.framework.TestCase;

/**
 * 
 * @author JoyoungZhang@gmail.com
 * 
 */
public class LoadConfig extends TestCase{

//	public static void main(String[] args) {
//		ClassLoader cl = LoadConfig.class.getClassLoader();
//		System.out.println(cl);
//		System.out.println(cl.getSystemResourceAsStream("com/jyz/study/jdk/mybatis-config.xml") == null);
//	}
	
	public void test() {
		ClassLoader cl = LoadConfig.class.getClassLoader();
		System.out.println(cl);
		System.out.println(cl.getSystemResourceAsStream("com/jyz/study/jdk/mybatis-config.xml") == null);
	}
	
}
