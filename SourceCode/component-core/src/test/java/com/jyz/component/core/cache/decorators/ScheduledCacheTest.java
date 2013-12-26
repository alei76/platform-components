package com.jyz.component.core.cache.decorators;

import junit.framework.TestCase;

import com.jyz.component.core.cache.impl.PerpetualCache;

/**
 * @author JoyoungZhang@gmail.com
 * 
 */
public class ScheduledCacheTest extends TestCase {

	public void testGetObject() throws InterruptedException {
		//init clearInterval = 5s
		ScheduledCache cache = new ScheduledCache(new PerpetualCache("s2"), 2 * 1000L);
		cache.putObject("key", "value");
		System.out.println(cache.getObject("key"));
		Thread.sleep(2100L);
		System.out.println(cache.getObject("key"));
	}

}
