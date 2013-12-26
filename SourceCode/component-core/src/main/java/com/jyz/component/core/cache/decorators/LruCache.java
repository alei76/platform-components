package com.jyz.component.core.cache.decorators;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;

import com.jyz.component.core.cache.Cache;

/**
 * 	Lru (least recently used) cache decorator
 *  借助LinkedHashMap实现
 *  同时加上一个size限定Cache容量
 *  @author JoyoungZhang@gmail.com
 */
public class LruCache implements Cache {

	private final Cache delegate;
	private Map<Object, Object> keyMap;
	private Object eldestKey;

	public LruCache(Cache delegate) {
		this(delegate, 1024);
	}
	
	public LruCache(Cache delegate, int size) {
		this.delegate = delegate;
		setSize(size);
	}

	public String getId() {
		return delegate.getId();
	}

	public int getSize() {
		return delegate.getSize();
	}

	public void setSize(final int size) {
		keyMap = Collections.synchronizedMap(new LinkedHashMap<Object, Object>(
				size, .75F, true) {
			private static final long serialVersionUID = 1L;

			protected boolean removeEldestEntry(Map.Entry<Object, Object> eldest) {
				boolean tooBig = size() > size;
				if (tooBig) {
					eldestKey = eldest.getKey();
				}
				return tooBig;
			}
		});
	}

	public void putObject(Object key, Object value) {
		delegate.putObject(key, value);
		cycleKeyList(key);
	}

	public Object getObject(Object key) {
		keyMap.get(key); // touch
		return delegate.getObject(key);

	}

	public Object removeObject(Object key) {
		return delegate.removeObject(key);
	}

	public void clear() {
		delegate.clear();
		keyMap.clear();
	}

	public ReadWriteLock getReadWriteLock() {
		return delegate.getReadWriteLock();
	}

	private void cycleKeyList(Object key) {
		keyMap.put(key, key);
		if (eldestKey != null) {
			delegate.removeObject(eldestKey);
			eldestKey = null;
		}
	}

}
