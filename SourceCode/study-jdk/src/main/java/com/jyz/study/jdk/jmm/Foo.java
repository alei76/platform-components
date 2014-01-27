package com.jyz.study.jdk.jmm;

import java.io.IOException;
import java.util.Arrays;

/**
 *  http://www.iteye.com/topic/1112358
 *	@author zhaoyong.zhang
 *	create time 2014-1-27
 */
public class Foo {

	private static void foo() {
		try {
			System.out.println("try");
			foo();
//		} catch (Error e) {
		} catch (Throwable  e) {
			System.out.println("catch");   
			System.out.println(e);
			foo();
		} finally {
			System.out.println("finally");
			foo();
		}
	}

	public static void main(String[] args) throws IOException {
//		foo();
		int[] x = new int[6];   
	    Arrays.fill(x, 1);   
	    for (int i = 0; i < x.length; i++) {   
	        System.in.read();   
	        System.out.println(x[i]);   
	    }   

	}

}
