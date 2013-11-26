package com.jyz.study.jdk.classLoader;

import java.net.URL;

import sun.misc.Launcher;

/**
 * 演示
 * appClassLoader
 * extClassloader
 * bootstrapLoader
 * 所加载的文件
 * @author JoyoungZhang@gmail.com
 * 
 */
public class ClassLoaderTest {
	public static void main(String[] args) throws Exception {
		
		ClassLoader appClassLoader = ClassLoader.getSystemClassLoader();
		ClassLoader extClassloader = appClassLoader.getParent();
		ClassLoader bootstrapLoader  = extClassloader.getParent();
		System.out.println("the bootstrapLoader : " + bootstrapLoader);
		System.out.println("the extClassloader : " + extClassloader);
		System.out.println("the appClassLoader : " + appClassLoader);
		
		System.out.println();
		System.out.println("bootstrapLoader加载以下文件：");
		URL[] urls = Launcher.getBootstrapClassPath().getURLs();
		for (int i = 0; i < urls.length; i++) {
		    System.out.println(urls[i]);
		}
		
		System.out.println();
		System.out.println("extClassloader加载以下文件：");
		System.out.println(System.getProperty("java.ext.dirs"));
		
		System.out.println();
		System.out.println("appClassLoader加载以下文件：");
		System.out.println(System.getProperty("java.class.path"));
		
	}
}






