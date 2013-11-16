package com.jyz.study.jdk.exception;


/**
 * 异常链
 * @author JoyoungZhang@gmail.com
 *
 */
public class ExceptionCause {
    public static void main(String[] args) throws Exception {
	test1();
    }

    private static void test1() throws Exception{
	try{
	    test2();
	}catch(NullPointerException ex){
//1	    Exception bussinessEx = new Exception("packag exception");
//	    bussinessEx.initCause(ex);
//	    throw bussinessEx;
//2	    throw new Exception("packag exception", ex);
//3	    throw (Exception)ex.fillInStackTrace().initCause(ex);
	}
    }
    
    private static void test2(){
	test3();
    }
    
    private static void test3(){
	throw new  NullPointerException("str is null");
    }

}


