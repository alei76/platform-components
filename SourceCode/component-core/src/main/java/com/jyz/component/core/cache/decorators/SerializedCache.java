package com.jyz.component.core.cache.decorators;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.locks.ReadWriteLock;

import com.jyz.component.core.cache.Cache;
import com.jyz.component.core.cache.CacheException;
import com.jyz.component.core.utils.SerializeUtils;

/**
 *  序列化 反序列化cache
 *  @author JoyoungZhang@gmail.com
 */
public class SerializedCache implements Cache {

	private Cache delegate;

	public SerializedCache(Cache delegate) {
		this.delegate = delegate;
	}

	public String getId() {
		return delegate.getId();
	}

	public int getSize() {
		return delegate.getSize();
	}

	public void putObject(Object key, Object object) {
		if (object == null || object instanceof Serializable) {
			try {
				delegate.putObject(key, SerializeUtils.serialize((Serializable) object));
			} catch (IOException e) {
				throw new CacheException("Serialize object fail.", e);
			}
		} else {
			throw new CacheException("SharedCache failed to make a copy of a non-serializable object: " + object);
		}
	}

	public Object getObject(Object key) {
		Object object = delegate.getObject(key);
		if(object == null){
			return null;
		}
		try {
			object = SerializeUtils.deserialize((byte[]) object);
		} catch (Exception e) {
			throw new CacheException("Deserialize object fail.", e);
		} 
		return object;
	}

	public Object removeObject(Object key) {
		return delegate.removeObject(key);
	}

	public void clear() {
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

}
