package com.jyz.study.jdk.annotation;

import org.junit.Test;


/**
 * @author JoyoungZhang@gmail.com
 *
 */
public class Testable {

	@Test
	void testExecute(){
		exeucte();
	}
	
	public void exeucte(){
		System.out.println(123);
	}
	
}
