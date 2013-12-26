package com.jyz.component.core.cache.decorators;

import java.util.LinkedList;
import java.util.concurrent.locks.ReadWriteLock;

import com.jyz.component.core.cache.Cache;

/**
 * FIFO (first in, first out) cache decorator
 * 只用delegate完全可以实现FIFO
 * 但还是加上一个size限定Cache容量
 * @author JoyoungZhang@gmail.com
 * 
 */
public class FIFOCache implements Cache {

	private final Cache delegate;
	private final LinkedList<Object> keyList;
	private int size;

	public FIFOCache(Cache delegate) {
		this(delegate, 1024);
	}

	public FIFOCache(Cache delegate, int size) {
		this.delegate = delegate;
		this.size = size;
		this.keyList = new LinkedList<Object>();
	}

	@Override
	public String getId() {
		return delegate.getId();
	}

	@Override
	public int getSize() {
		return delegate.getSize();
	}

	@Override
	public void putObject(Object key, Object value) {
		cycleKeyList(key);
		delegate.putObject(key, value);
	}

	@Override
	public Object getObject(Object key) {
		return delegate.getObject(key);
	}

	@Override
	public Object removeObject(Object key) {
		return delegate.removeObject(key);
	}

	@Override
	public void clear() {
		delegate.clear();
		keyList.clear();
	}

	@Override
	public ReadWriteLock getReadWriteLock() {
		return delegate.getReadWriteLock();
	}

	private void cycleKeyList(Object key) {
		keyList.addLast(key);
		if (keyList.size() > size) {
			Object oldestKey = keyList.removeFirst();
			delegate.removeObject(oldestKey);
		}
	}

}
