package com.jyz.study.jdk.classLoader;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 *  演示ContextClassLoader的使用
 *  @author JoyoungZhang@gmail.com
 *  
 */
public class MainClass {
	
	public static void main(String[] args) throws MalformedURLException, ClassNotFoundException {
		System.out.println("MainClass getClassLoader: " + MainClass.class.getClassLoader());
		System.out.println("MainClass getContextClassLoader: " + Thread.currentThread().getContextClassLoader());
		Thread innerThread1 = new InnerThread1();
		innerThread1.start();
	}
	
}

class InnerThread1 extends Thread{
	@Override
	public void run() {
		try {
			URL[] urls = new URL[1];
			urls[0] = new URL("jar:file:/E:/GoogleCode/platform-components/trunk/SourceCode/component-core/target/component-core-1.0.jar!/");
			URLClassLoader urlClassLoader = new URLClassLoader(urls);
			Class clazz = urlClassLoader.loadClass("com.jyz.component.core.collection.Tuple");
			Object tuple = clazz.newInstance();
			System.out.println(tuple);
			
			
			System.out.println("InnerThread1 getClassLoader: " + clazz.getClassLoader());
			System.out.println("InnerThread1 getContextClassLoader: " + Thread.currentThread().getContextClassLoader());
			
			this.setContextClassLoader(urlClassLoader);
			
			Thread innerThread2 = new InnerThread2();
			innerThread2.start();
		}catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}


class InnerThread2 extends Thread{
	@Override
	public void run() {
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			classLoader.loadClass("com.jyz.component.core.collection.Triple");
			System.out.println("InnerThread2 getContextClassLoader: " + Thread.currentThread().getContextClassLoader());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}

