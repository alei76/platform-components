package com.jyz.study.jdk.thread;

public class BufferInterruptiblyReader extends Thread {    

    private BufferInterruptibly buff;    

    public BufferInterruptiblyReader(BufferInterruptibly buff) {
        this.buff = buff;
    }    

    @Override
    public void run() {    

        try {
            buff.read();//可以收到中断的异常，从而有效退出  //“读”线程接收到了lock.lockInterruptibly()中断，并且有效处理了这个“异常”。
        } catch (InterruptedException e) {
            System.out.println("我不读了");
        }    

        System.out.println("读结束");    

    }    

}
