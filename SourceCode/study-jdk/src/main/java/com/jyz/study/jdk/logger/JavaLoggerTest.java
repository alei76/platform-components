package com.jyz.study.jdk.logger;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class JavaLoggerTest {

	public static void main(String[] args) throws IOException {
		
		Logger logger = Logger.getLogger(JavaLoggerTest.class.getName());
		logger.info("java logger");
		
		System.out.println("java.util.logging.config.class:" + System.getProperty("java.util.logging.config.class"));
		System.out.println("java.util.logging.config.file:" + System.getProperty("java.util.logging.config.file"));
		
		String java_home = System.getProperty("java.home");
		System.out.println("java.home:" + java_home);
		File f = new File(java_home, "lib");
		System.out.println(f.getAbsolutePath());
		f = new File(f, "logging.properties");
		System.out.println(f.getAbsolutePath());
		System.out.println(f.getCanonicalPath());
		
	}
}
