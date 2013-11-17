package com.jyz.study.jdk.exception;


/**
 * 栈轨迹
 * fillInStackTrace
 * @author JoyoungZhang@gmail.com
 *
 */
public class FillInStackTrace {
    
    public static void main(String[] args) throws Exception {
	test1();
    }

    private static void test1() throws Exception{
	try{
	    test2();
	}catch(NullPointerException ex){
	    throw (Exception)ex.fillInStackTrace();
//	    throw new Exception();
	}
    }
    
    private static void test2(){
	test3();
    }
    
    private static void test3(){
	throw new  NullPointerException("str is null");
    }

}


