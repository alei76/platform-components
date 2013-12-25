package com.jyz.component.core.cache.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.jyz.component.core.cache.Cache;
import com.jyz.component.core.cache.CacheException;

/**
 * @author JoyoungZhang@gmail.com
 * 
 */
public class PerpetualCache implements Cache {

	private String id;
	private Map<Object, Object> cache = new HashMap<Object, Object>();
	private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

	public PerpetualCache(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public int getSize() {
		return cache.size();
	}

	@Override
	public void putObject(Object key, Object value) {
		cache.put(key, value);
	}

	@Override
	public Object getObject(Object key) {
		return cache.get(key);
	}

	@Override
	public Object removeObject(Object key) {
		return cache.remove(key);
	}

	@Override
	public void clear() {
		cache.clear();
	}

	@Override
	public ReadWriteLock getReadWriteLock() {
		return readWriteLock;
	}

	public boolean equals(Object o) {
		if (getId() == null) {
			throw new CacheException("Cache instances require an ID.");
		}
		if (this == o) {
			return true;
		}
		if (!(o instanceof Cache)){
			return false;
		}
		Cache otherCache = (Cache) o;
		return getId().equals(otherCache.getId());
	}

	public int hashCode() {
		if (getId() == null){
			throw new CacheException("Cache instances require an ID.");
		}
		return getId().hashCode();
	}

}
