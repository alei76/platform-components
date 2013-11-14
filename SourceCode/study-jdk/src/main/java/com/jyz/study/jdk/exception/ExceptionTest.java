package com.jyz.study.jdk.exception;

import java.io.FileNotFoundException;



/**
 * Throwable学习
 * http://zy19982004.iteye.com/blog/1974796
 * @author JoyoungZhang@gmail.com
 *
 */
public class ExceptionTest{

	public static void main(String[] args) {
		try{
			test3();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	private static void test1(){
		NullPointerException npt = new  NullPointerException("str is null");
		throw npt;
	}
	
	private static void test2() throws FileNotFoundException{
		try{
			test1();
		}catch(Exception ex){
			FileNotFoundException fnx = new FileNotFoundException("file not found ");
			fnx.initCause(ex);
			throw fnx;
		}
	}
	
	private static void test3() throws ClassCastException{
		try{
			test2();
		}catch(Exception ex){
			ClassCastException cce = new ClassCastException("class cast exception");
			cce.initCause(ex);
			throw cce;
		}
	}
    
}
