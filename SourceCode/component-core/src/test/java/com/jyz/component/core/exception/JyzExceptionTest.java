package com.jyz.component.core.exception;

import java.util.Locale;

import junit.framework.TestCase;

public class JyzExceptionTest extends TestCase{

	public void exception() throws JyzException{
		throw new JyzException("1001", "jyz", "jDD");
	}
	
	public void test(){
		try {
			exception();
		} catch (JyzException e) {
			System.out.println(e.getErrorMessage(Locale.CHINA));
			assertEquals(e.getMessage(), "jyz不存在jDD");
		}
	}
}
