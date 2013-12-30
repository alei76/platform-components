package com.jyz.component.io;

import junit.framework.TestCase;

import com.jyz.component.core.logging.Log;
import com.jyz.component.core.logging.LogFactory;
import com.jyz.component.core.logging.impl.Log4jImpl;

public class LogFactoryTest extends TestCase {
	
	public void testGetLogClassOfQ() {
		Log log = LogFactory.getLog(LogFactoryTest.class);
		System.out.println(log);
		System.out.println(log instanceof Log4jImpl);
		log.error("IO中cvesdfrror");
		log.info("IO中vcisdfsdnfo");
		log.debug("IOc中vdfsdfasdfasdebug");
	}

}
