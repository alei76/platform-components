package com.jyz.component.core.cache.decorators;

import com.jyz.component.core.cache.impl.PerpetualCache;

import junit.framework.TestCase;

/**
 * @author JoyoungZhang@gmail.com
 * 
 */
public class LruCacheTest extends TestCase {

	public void testGetObject() {
		//init size = 2
		LruCache cache = new LruCache(new PerpetualCache("l1"), 2);
		cache.putObject("key1", "value1");
		cache.putObject("key2", "value2");
		//cache.getObject("key1");
		cache.putObject("key3", "value3");
		System.out.println(cache.getObject("key1"));
	}

}
