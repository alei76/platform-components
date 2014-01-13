package com.jyz.study.jdk.thread;

/**
 *  http://www.365doit.com/all/news/synchronizeandlock.html/2
 *	@author zhaoyong.zhang
 *	create time 2014-1-13
 */
public class BufferTest {
    public static void main(String[] args) {
        Buffer buff = new Buffer();    

        final BufferWriter writer = new BufferWriter(buff);
        writer.setName("BufferWriter");
        final BufferReader reader = new BufferReader(buff);    
        reader.setName("BufferReader");

        writer.start();
        reader.start();    

        new Thread(new Runnable() {    

            @Override
            public void run() {
                long start = System.currentTimeMillis();
                for (;;) {
                    //等5秒钟去中断读
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