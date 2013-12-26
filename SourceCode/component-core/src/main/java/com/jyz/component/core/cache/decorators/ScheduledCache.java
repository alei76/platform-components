package com.jyz.component.core.cache.decorators;

import java.util.concurrent.locks.ReadWriteLock;

import com.jyz.component.core.cache.Cache;

/**
 *  ScheduledCache
 *  1.在clearInterval后清理cache
 *  2.并不是真正在clearInterval这个点清理，而是在cache将要变化是清理
 *  @author JoyoungZhang@gmail.com
 */
public class ScheduledCache implements Cache {

	private Cache delegate;
	protected long clearInterval;
	protected long lastClear;

	public ScheduledCache(Cache delegate) {
		this(delegate, 60 * 60 * 1000);
	}
	
	public ScheduledCache(Cache delegate, long clearInterval) {
		this.delegate = delegate;
		this.clearInterval = clearInterval;
		this.lastClear = System.currentTimeMillis();
	}


	public String getId() {
		return delegate.getId();
	}

	public int getSize() {
		clearWhenStale();
		return delegate.getSize();
	}

	public void putObject(Object key, Object object) {
		clearWhenStale();
		delegate.putObject(key, object);
	}

	public Object getObject(Object key) {
		if (clearWhenStale()) {
			return null;
		} else {
			return delegate.getObject(key);
		}
	}

	public Object removeObject(Object key) {
		clearWhenStale();
		return delegate.removeObject(key);
	}

	public void clear() {
		lastClear = System.currentTimeMillis();
		delegate.clear();
	}

	public ReadWriteLock getReadWriteLock() {
		return delegate.getReadWriteLock();
	}

	public int hashCode() {
		return delegate.hashCode();
	}

	public boolean equals(Object obj) {
		return delegate.equals(obj);
	}

	private boolean clearWhenStale() {
		if (System.currentTimeMillis() - lastClear > clearInterval) {
			clear();
			return true;
		}
		return false;
	}

}
