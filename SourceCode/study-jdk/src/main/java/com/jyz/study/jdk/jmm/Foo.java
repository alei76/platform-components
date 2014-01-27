package com.jyz.study.jdk.jmm;

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

	public static void main(String[] args) {
		foo();
	}

}
