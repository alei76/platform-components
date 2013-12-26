package com.jyz.component.core.cache.decorators;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.concurrent.locks.ReadWriteLock;

import com.jyz.component.core.cache.Cache;

/** 
 *  Weak Reference cache decorator.
 *  对象外部无引用 + hardLinksToAvoidGarbageCollection不引用(通过numberOfHardLinks控制)时
 *  垃圾回收回会收此对象，因为使用WeakReference引用对象
 *  @author JoyoungZhang@gmail.com
 */
public class WeakCache implements Cache {
	private final LinkedList<Object> hardLinksToAvoidGarbageCollection;
	private final ReferenceQueue<Object> queueOfGarbageCollectedEntries;
	private final Cache delegate;
	private int numberOfHardLinks;

	public WeakCache(Cache delegate) {
		this(delegate, 256);
	}
	
	public WeakCache(Cache delegate, int numberOfHardLinks) {
		this.delegate = delegate;
		this.numberOfHardLinks = numberOfHardLinks;
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
		delegate.putObject(key, new WeakEntry(key, value, queueOfGarbageCollectedEntries));
	}

	public Object getObject(Object key) {
		Object result = null;
		@SuppressWarnings("unchecked")
		// assumed delegate cache is totally managed by this cache
		WeakReference<Object> weakReference = (WeakReference<Object>) delegate.getObject(key);
		if (weakReference != null) {
			result = weakReference.get();
			if (result == null) {
				delegate.removeObject(key);
			} else {
				hardLinksToAvoidGarbageCollection.addFirst(result);
				if (hardLinksToAvoidGarbageCollection.size() > numberOfHardLinks) {
					hardLinksToAvoidGarbageCollection.removeLast();
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
		hardLinksToAvoidGarbageCollection.clear();
		removeGarbageCollectedItems();
		delegate.clear();
	}

	public ReadWriteLock getReadWriteLock() {
		return delegate.getReadWriteLock();
	}

	private void removeGarbageCollectedItems() {
		WeakEntry sv;
		while ((sv = (WeakEntry) queueOfGarbageCollectedEntries.poll()) != null) {
			delegate.removeObject(sv.key);
		}
	}

	private static class WeakEntry extends WeakReference<Object> {
		private final Object key;

		private WeakEntry(Object key, Object value, ReferenceQueue<Object> garbageCollectionQueue) {
			super(value, garbageCollectionQueue);
			this.key = key;
		}
	}

}
