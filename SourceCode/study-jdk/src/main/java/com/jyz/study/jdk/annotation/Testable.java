package com.jyz.study.jdk.annotation;

/**
 * @author JoyoungZhang@gmail.com
 *
 */
public class Testable {

	@Test
	public void testExecute(){
		exeucte();
	}
	
	public void exeucte(){
		System.out.println(123);
	}
	
}


