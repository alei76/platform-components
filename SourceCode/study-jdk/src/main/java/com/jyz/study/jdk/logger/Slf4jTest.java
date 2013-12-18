package com.jyz.study.jdk.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  http://www.blogjava.net/DLevin/archive/2012/11/08/390991.html
 *	@author JoyoungZhang@gmail.com
 */
public class Slf4jTest {

	
	public static void main(String[] args) {
		 Logger logger = LoggerFactory.getLogger(Slf4jTest.class);    
		 logger.info("Hello World");
	}

}
