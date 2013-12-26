package com.jyz.component.core.cache.decorators;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.LinkedList;
import java.util.concurrent.locks.ReadWriteLock;

import com.jyz.component.core.cache.Cache;

/**
 * 
 * Soft Reference cache decorator.+
 * 对象外部无引用 + hardLinksToAvoidGarbageCollection不引用(通过numberOfHardLinks控制)时
 * 垃圾回收回可能收此对象，因为使用SoftReference引用对象
 * @author JoyoungZhang@gmail.com
 */
public class SoftCache implements Cache {
	private final LinkedList<Object> hardLinksToAvoidGarbageCollection;
	private final ReferenceQueue<Object> queueOfGarbageCollectedEntries;
	private final Cache delegate;
	private int numberOfHardLinks;

	public SoftCache(Cache delegate) {
		this(delegate, 256);
	}
	
	public SoftCache(Cache delegate, int numberOfHardLinks) {
		this.delegate = delegate;
		this.numberOfHardLinks = 256;
		this.hardLinksToAvoidGarbageCollection = new LinkedList<Object>();
		this.queueOfGarbageCollectedEntries = new ReferenceQueue<Object>();
	}

	public String getId() {
		return delegate.getId();
	}

	public int getSize() {
		removeGarbageCollectedItems();
		return delegate.getSize();
	}

	public void setSize(int size) {
		this.numberOfHardLinks = size;
	}

	public void putObject(Object key, Object value) {
		removeGarbageCollectedItems();
		delegate.putObject(key, new SoftEntry(key, value, queueOfGarbageCollectedEntries));
	}

	public Object getObject(Object key) {
		Object result = null;
		@SuppressWarnings("unchecked")
		// assumed delegate cache is totally managed by this cache
		SoftReference<Object> softReference = (SoftReference<Object>) delegate.getObject(key);
		if (softReference != null) {
			result = softReference.get();
			if (result == null) {
				delegate.removeObject(key);
			} else {
				// See #586 (and #335) modifications need more than a read lock
				synchronized (hardLinksToAvoidGarbageCollection) {
					hardLinksToAvoidGarbageCollection.addFirst(result);
					if (hardLinksToAvoidGarbageCollection.size() > numberOfHardLinks) {
						hardLinksToAvoidGarbageCollection.removeLast();
					}
				}
			}
		}
		return result;
	}

	public Object removeObject(Object key) {
		removeGarbageCollectedItems();
		return delegate.removeObject(key);
	}

	public void clear() {
		synchronized (hardLinksToAvoidGarbageCollection) {
			hardLinksToAvoidGarbageCollection.clear();
		}
		removeGarbageCollectedItems();
		delegate.clear();
	}

	public ReadWriteLock getReadWriteLock() {
		return delegate.getReadWriteLock();
	}

	private void removeGarbageCollectedItems() {
		SoftEntry sv;
		while ((sv = (SoftEntry) queueOfGarbageCollectedEntries.poll()) != null) {
			delegate.removeObject(sv.key);
		}
	}

	private static class SoftEntry extends SoftReference<Object> {
		private final Object key;

		private SoftEntry(Object key, Object value,
				ReferenceQueue<Object> garbageCollectionQueue) {
			super(value, garbageCollectionQueue);
			this.key = key;
		}
	}

}