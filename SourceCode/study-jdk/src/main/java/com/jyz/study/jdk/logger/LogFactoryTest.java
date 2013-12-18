package com.jyz.study.jdk.logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *  http://lavasoft.blog.51cto.com/62575/184492
 *	@author JoyoungZhang@gmail.com
 */
public class LogFactoryTest {

	public static void main(String[] args) {

		Log log1 = LogFactory.getLog("LogFactoryTest");
		Log log2 = LogFactory.getLog(LogFactoryTest.class);
		
		Log log3 = LogFactory.getFactory().getInstance(LogFactoryTest.class);
		
		System.out.println(log1==log2);//false
		System.out.println(log2==log3);//true
		
		System.out.println("org.apache.commons.logging.LogFactory is " + System.getProperty("org.apache.commons.logging.LogFactory"));
		
		System.out.println(log1);
		log1.info("sli4j log");
	}

}
