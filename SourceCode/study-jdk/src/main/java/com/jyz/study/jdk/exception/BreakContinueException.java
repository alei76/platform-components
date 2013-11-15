package com.jyz.study.jdk.exception;


/**
 * break continue时， finally子句也会得到执行
 * @author JoyoungZhang@gmail.com
 *
 */
public class BreakContinueException {
	
	public static void main(String[] args) {
		try{
			for(int i=1;i<=2;i++){
				if(i==1){
					break;
				}
				throw new RuntimeException();
			}
		}catch(RuntimeException rx){
			rx.printStackTrace();
		}finally{
			System.out.println("enter finally");
		}
	}

}
