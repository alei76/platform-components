package com.jyz.study.jdk.logger;

import org.apache.log4j.Logger;

/**
 *  http://lavasoft.blog.51cto.com/62575/184492
 *	@author JoyoungZhang@gmail.com
 */
public class Log4jTest {
	
	static Logger logger = Logger.getLogger(Log4jTest.class);
			
	public static void main(String[] args) {
		logger.info(new User("z", 10));
	}
	
}
