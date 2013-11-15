package com.jyz.study.jdk.exception;

import java.io.IOException;

/**
 * 只申明异常 但不抛出异常 等于没用
 * @author JoyoungZhang@gmail.com
 *
 */
public class ThrowsButNotThrowException {
	
	public static void main(String[] args) {
		try {
			System.out.println(1);
			test();
			System.out.println(2);
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}
	
	private static void test() throws IOException{
	}

}
