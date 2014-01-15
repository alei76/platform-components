package com.jyz.study.jdk.thread;

import java.util.concurrent.locks.ReentrantLock;    

/**
 * ReentrantLock就提供了2种机制，
 * 第一，B线程中断自己（或者别的线程中断它），但是ReentrantLock不去响应，继续让B线程等待，你再怎么中断，我全当耳边风（synchronized原语就是如此）；
 * 第二，B线程中断自己（或者别的线程中断它），ReentrantLock处理了这个中断，并且不再等待这个锁的到来，完全放弃。
 *	@author zhaoyong.zhang
 *	create time 2014-1-15
 */
public class BufferInterruptibly {    

    private ReentrantLock lock = new ReentrantLock();    

    public void write() {
        lock.lock();
        try {
            long startTime = System.currentTimeMillis();
            System.out.println("开始往这个buff写入数据…");
            for (;;)// 模拟要处理很长时间
            {
                if (System.currentTimeMillis()
                        - startTime > Integer.MAX_VALUE)
                    break;
            }
            System.out.println("终于写完了");
        } finally {
            lock.unlock();
        }
    }    

    public void read() throws InterruptedException {
//        lock.lock();				// 第一
        lock.lockInterruptibly();	// 第二
        try {
            System.out.println("从这个buff读数据");
        } finally {
            lock.unlock();
        }
    }    

}