package com.jyz.study.jdk.thread;

/**
 *  某个线程中断对某个锁的请求，如reader线程
 *  http://www.365doit.com/all/news/synchronizeandlock.html
 *	@author zhaoyong.zhang
 *	create time 2014-1-13
 */
public class BufferInterruptiblyTest {
    public static void main(String[] args) {
        BufferInterruptibly buff = new BufferInterruptibly();    

        final BufferInterruptiblyWriter writer = new BufferInterruptiblyWriter(buff);
        writer.setName("BufferInterruptiblyWriter");
        final BufferInterruptiblyReader reader = new BufferInterruptiblyReader(buff);    
        reader.setName("BufferInterruptiblyReader");

        writer.start();
        reader.start();    

        new Thread(new Runnable() {    

            @Override
            public void run() {
                long start = System.currentTimeMillis();
                for (;;) {
                    if (System.currentTimeMillis()
                            - start > 5000) {
                        System.out.println("不等了，尝试中断");
                        reader.interrupt();
                        break;
                    }    

                }    

            }
        }).start();    

    }
}