package com.jyz.component.core.logging;

import junit.framework.TestCase;

import com.jyz.component.core.logging.impl.Log4jImpl;

public class LogFactoryTest extends TestCase {

	public void testGetLogClassOfQ() {
		Log log = LogFactory.getLog(LogFactoryTest.class);
		System.out.println(log instanceof Log4jImpl);
		log.error("error中v");
		log.info("info中v");
		log.debug("debug中v");
	}

}
