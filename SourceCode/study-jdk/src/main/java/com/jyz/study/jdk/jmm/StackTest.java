package com.jyz.study.jdk.jmm;

public class StackTest {
	static byte wk = 100;
	public static  void main(String[] args) {
		int i = 40;
		int j = i + wk;
	}

}

