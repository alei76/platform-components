package com.jyz.study.jdk.exception;

import java.io.IOException;

/**
 * 丢失异常
 * @author JoyoungZhang@gmail.com
 *
 */
public class LostException {
	
	public static void test1() {
		try{
			throw new IOException();
		}finally{
			return;
		}
	}
	
	public static void test2() throws IOException {
		try{
			throw new IOException();
		}finally{
			System.out.println(1);
		}
	}
	
	public static void main(String[] args) {
		test1();
		try {
			test2();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
