package com.jyz.component.core.exception;

import junit.framework.TestCase;

public class JyzExceptionTest extends TestCase{

	public void exception() throws JyzException{
		throw new JyzException("1001", "jyz", "jDD");
	}
	
	public void test(){
		try {
			exception();
		} catch (JyzException e) {
			System.out.println(e.getMessage());
			assertEquals(e.getMessage(), "jyz不存在jDD");
		}
	}
}
