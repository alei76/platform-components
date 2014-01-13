package com.jyz.study.jdk.thread;

public class BufferInterruptiblyWriter extends Thread {

	private BufferInterruptibly buff;

	public BufferInterruptiblyWriter(BufferInterruptibly buff) {
		this.buff = buff;
	}

	@Override
	public void run() {
		buff.write();
	}

}
