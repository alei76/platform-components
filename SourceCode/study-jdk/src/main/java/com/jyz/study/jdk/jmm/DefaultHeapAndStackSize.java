package com.jyz.study.jdk.jmm;

/**
 * http://developer.51cto.com/art/201009/226999.htm
 * 
 * @author zhaoyong.zhang create time 2014-2-13
 */
public class DefaultHeapAndStackSize {
	
	public static void main(String args[]) {
		System.out.println("usage:");
		DefaultHeapAndStackSize m = new DefaultHeapAndStackSize();
		long t = m.showUsage();
		System.out.println("Total:" + t + " Bytes");
		long fr = m.freeMemory();
		// System.gc();
		System.out.println("Free:" + fr + " Bytes");
		long rem = t - fr;
		System.out.println("Occupied Space :" + rem + " Bytes");
	}

	public long showUsage() {
		long l = Runtime.getRuntime().totalMemory();
		return (l);
	}

	public long freeMemory() {
		long f = Runtime.getRuntime().freeMemory();
		return (f);
	}
	
}
