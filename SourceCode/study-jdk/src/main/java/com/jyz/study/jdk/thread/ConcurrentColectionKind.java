package com.jyz.study.jdk.thread;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *  同步容器
 *  1.Concurrent*					:					对大多数读不加锁，对写操作采用锁分段来实现线程安全
 *  2.Collections.synchronized*		:					使用this对象本身作为信号量实现线程安全
 *  3.CopyOnWrite*					:					使用ReentrantLock实现线程安全
 *  4.*BlockingQueue				:					使用ReentrantLock实现线程安全
 *	@author zhaoyong.zhang
 *	create time 2014-1-20
 */
public class ConcurrentColectionKind {

	public static void main(String[] args) {
		//同步map
		ConcurrentMap map1 = new ConcurrentHashMap();
		//支持comparator的同步map
		ConcurrentMap map2 = new ConcurrentSkipListMap();
		//以this作为信号量的同步map
		Map map3 = Collections.synchronizedMap(new HashMap());
		//hashtable同步map
		Map map4 = new Hashtable();
		
		//同步list
		CopyOnWriteArrayList list1 = new CopyOnWriteArrayList();
		
		//同步set 使用CopyOnWriteArrayList实现
		CopyOnWriteArraySet set2 = new CopyOnWriteArraySet();
		//支持comparator的同步set
		ConcurrentSkipListSet set1 = new ConcurrentSkipListSet();
				
		
		//由数组支持的有界阻塞队列 10是固定容量 不是初始容量
		BlockingQueue queue1 = new ArrayBlockingQueue(10);
		//由链表支持的无界阻塞队列 因为Integer.MAX_VALUE 所以我们说成无界的
		BlockingQueue queue2 = new LinkedBlockingQueue();//默认new LinkedBlockingQueue(Integer.MAX_VALUE);
		//由链表支持的无界线程安全队列
		Queue queue3 = new ConcurrentLinkedQueue();
		
	}

}
