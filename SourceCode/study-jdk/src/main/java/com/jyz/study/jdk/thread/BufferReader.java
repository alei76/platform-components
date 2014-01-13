package com.jyz.study.jdk.thread;


public class BufferReader extends Thread {    

    private Buffer buff;    

    public BufferReader(Buffer buff) {
        this.buff = buff;
    }    

    @Override
    public void run() {    

        buff.read();//这里估计会一直阻塞    

        System.out.println("读结束");    

    }    

}