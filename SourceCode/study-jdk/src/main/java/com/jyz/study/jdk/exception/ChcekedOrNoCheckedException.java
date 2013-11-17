package com.jyz.study.jdk.exception;

/**
 * 
 * @author JoyoungZhang@gmail.com
 *
 */
public class ChcekedOrNoCheckedException {

    public static void main(String[] args) {
	try {
	    pop1(0);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	
	try {
	    pop2(0);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
    
    private static void pop1(int position) throws Exception{
	if(position == 0){
	    throw new Exception("checked exception");
	}
    }
    
    private static void pop2(int position){
	if(position == 0){
	    throw new RuntimeException("uncheked exception");
	}
    }
    
}
