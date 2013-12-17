package com.jyz.study.mybatis.test.log;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;

public class LogFactoryTest {

	private static final Log LOG = LogFactory.getLog(LogFactoryTest.class);
	
	public static void main(String[] args) {
		LOG.error("error");
		System.out.println(LOG.isDebugEnabled());
		System.out.println(LOG.isTraceEnabled());
	}
	
}
