package com.jyz.study.jdk.logger;

import com.jyz.component.core.logging.Log;
import com.jyz.component.core.logging.LogFactory;

/**
 * 
 * @author JoyoungZhang@gmail.com
 *
 */
public class JyzLogTest {
 
    public static void main(String[] args) {
	Log log = LogFactory.getLog(JyzLogTest.class);
	System.out.println(log);
    }
}
