package com.jyz.component.core.cache.decorators;

/**
 * @author JoyoungZhang@gmail.com
 * 
 */
import junit.framework.TestCase;

import com.jyz.component.core.cache.impl.PerpetualCache;

public class FIFOCacheTest extends TestCase {
	
	FIFOCache cache;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		// init size = 2
		cache = new FIFOCache(new PerpetualCache("f1"), 2);
	}
	
	public void testPutObject() {
		cache.putObject("key1", "value1");
		cache.putObject("key2", "value2");
		cache.putObject("key3", "value3");
		
		System.out.println(cache.getObject("key1"));
	}

}
