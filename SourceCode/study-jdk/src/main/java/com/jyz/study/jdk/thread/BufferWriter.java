package com.jyz.study.jdk.thread;

public class BufferWriter extends Thread {    

    private Buffer buff;    

    public BufferWriter(Buffer buff) {
        this.buff = buff;
    }    

    @Override
    public void run() {
        buff.write();
    }    

}    
