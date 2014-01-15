package com.jyz.study.jdk.thread;

/**
 *  synchronized只有如下一种机制，
 *  B线程中断自己（或者别的线程中断它），Synchronized不去响应，继续让B线程等待，你再怎么中断，我全当耳边风；
 *	@author zhaoyong.zhang
 *	create time 2014-1-15
 */
public class Buffer {    

    private Object lock;    

    public Buffer() {
        lock = this;
    }    

    public void write() {
        synchronized (lock) {
            long startTime = System.currentTimeMillis();
            System.out.println("开始往这个buff写入数据…");
            for (;;)// 模拟要处理很长时间
            {
                if (System.currentTimeMillis()
                        - startTime > Integer.MAX_VALUE)
                    break;
            }
            System.out.println("终于写完了");
        }
    }    

    public void read() {
        synchronized (lock) {
            System.out.println("从这个buff读数据");
        }
    }
}