package com.jyz.component.core.cache.decorators;

import com.jyz.component.core.cache.impl.PerpetualCache;

import junit.framework.TestCase;

public class ScheduledCacheTest extends TestCase {

	public void testGetObject() throws InterruptedException {
		//init clearInterval = 5s
		ScheduledCache cache = new ScheduledCache(new PerpetualCache("s2"), 5 * 1000L);
		cache.putObject("key", "value");
		System.out.println(cache.getObject("key"));
		Thread.sleep(5 * 1000L);
		System.out.println(cache.getObject("key"));
	}

}
