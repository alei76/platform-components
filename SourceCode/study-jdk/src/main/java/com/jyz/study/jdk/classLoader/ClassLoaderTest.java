package com.jyz.study.jdk.classLoader;

import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;

import sun.misc.Launcher;
import sun.security.action.GetPropertyAction;

/**
 * 
 * @author JoyoungZhang@gmail.com
 * 
 */
public class ClassLoaderTest {
	@SuppressWarnings({ "rawtypes", "unused" })
	public static void main(String[] args) throws Exception {
		
//		System.out.println(AccessController.doPrivileged(
//	            new GetPropertyAction("sun.boot.class.path")));
//		URL[] urls = Launcher.getBootstrapClassPath().getURLs();
//		for (int i = 0; i < urls.length; i++) {
//		    System.out.println(urls[i]);
//		}
//
//		System.out.println("java.class.path: " + System.getProperty("java.class.path"));
//		System.out.println(System.getProperty("java.ext.dirs"));
//		System.out.println(ClassLoader.getSystemResource("java/lang/String.class"));
//
//		ClassLoader extensionClassloader = ClassLoader.getSystemClassLoader()
//			.getParent();
//		System.out.println("the parent of extension classloader : "
//			+ extensionClassloader.getParent());
		
		URL[] urls = new URL[1];
		urls[0] = new URL("jar:file:/E:/GoogleCode/platform-components/trunk/SourceCode/component-core/target/component-core-1.0.jar!/");
		URLClassLoader urlClassLoader = new URLClassLoader(urls);
		Class clazz = urlClassLoader.loadClass("com.jyz.component.core.collection.Tuple");

		
	}
}






