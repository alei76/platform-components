package com.jyz.component.core.cache.decorators;

import java.util.concurrent.locks.ReadWriteLock;

import com.jyz.component.core.cache.Cache;

/**
 * FIFO (first in, first out) cache decorator
 * @author JoyoungZhang@gmail.com
 * 
 */
public class FIFOCache implements Cache {
	
	private final Cache delegate;
	
	public FIFOCache(Cache delegate) {
		this.delegate = delegate;
	}

	@Override
	public String getId() {
		return delegate.getId();
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void putObject(Object key, Object value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getObject(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object removeObject(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ReadWriteLock getReadWriteLock() {
		// TODO Auto-generated method stub
		return null;
	}

}
