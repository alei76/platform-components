package com.jyz.study.jdk.thread;

import java.util.concurrent.TimeUnit;

/**
 *  TimeUnit
 *	@author zhaoyong.zhang
 *	create time 2014-1-21
 */
public class TimeUnitTest {

	public static void main(String[] args)  {
		try {
			TimeUnit.SECONDS.sleep(TimeUnit.SECONDS.toSeconds(1));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(TimeUnit.SECONDS.toSeconds(2));
		
		System.out.println(System.nanoTime());
		System.out.println(System.currentTimeMillis());
		System.out.println(TimeUnit.MILLISECONDS.toNanos(System.currentTimeMillis()));
	}

}
