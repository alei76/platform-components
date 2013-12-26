package com.jyz.component.core.logging;

import junit.framework.TestCase;

public class LogFactoryTest extends TestCase {

	public void testGetLogClassOfQ() {
		Log log = LogFactory.getLog(LogFactoryTest.class);
		System.out.println(log);
		log.error("sasdsdfasdfasdf顶顶顶顶顶sa");
		log.info("sasdsdfasdfasdf顶顶顶顶顶sa");
		log.debug("da是打asdfsdf发斯蒂芬地方sd");
	}

}
