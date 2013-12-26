package com.jyz.study.jdk.logger;

import org.apache.log4j.spi.LoggerFactory;


/**
 *  http://www.blogjava.net/DLevin/archive/2012/11/08/390991.html
 *  http://www.blogjava.net/DLevin/archive/2012/11/10/391122.html
 *	@author JoyoungZhang@gmail.com
 *	Detected both jcl-over-slf4j.jar AND slf4j-jcl.jar on the class path, 
 *	preempting StackOverflowError. See also http://www.slf4j.org/codes.html#jclDelegationLoop for more details.
 *
 */
public class Slf4jTest {

	public static void main(String[] args) {
		 Logger logger = LoggerFactory.getLogger(Slf4jTest.class); 
		 System.out.println(logger);
		 logger.info("Hello World");
	}

}
