package com.jyz.component.core.cache.decorators;

import junit.framework.TestCase;

import com.jyz.component.core.cache.impl.PerpetualCache;

public class SoftCacheTest extends TestCase {

	public void testGetObject() {
		//init size = 1
		SoftCache cache = new SoftCache(new PerpetualCache("l1"), 1);
		Inner s1 = new Inner(1);
		Inner s2 = new Inner(2);
		cache.putObject("key1", s1);
		cache.putObject("key2", s2);
		
		System.out.println(cache.getObject("key1"));
		System.out.println(cache.getObject("key2"));
		s1 = null;
		System.gc();
		System.out.println(cache.getObject("key1"));
		System.out.println(cache.getObject("key2"));
	}

}
