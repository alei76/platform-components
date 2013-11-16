package com.jyz.study.jdk.exception;


/**
 * 栈轨迹
 * @author JoyoungZhang@gmail.com
 *
 */
public class StackTrace {
    
    public static void main(String[] args) {
	test1();
    }

    private static void test1(){
	test2();
    }
    
    private static void test2(){
	test3();
    }
    
    private static void test3(){
	throw new  NullPointerException("str is null");
    }

}


