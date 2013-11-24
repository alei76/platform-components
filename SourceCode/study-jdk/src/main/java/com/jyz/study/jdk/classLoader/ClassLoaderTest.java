package com.jyz.study.jdk.classLoader;

import java.net.URL;
import java.security.AccessController;

import sun.misc.Launcher;
import sun.security.action.GetPropertyAction;

public class ClassLoaderTest {

    public static void main(String[] args) {
	System.out.println(AccessController.doPrivileged(
            new GetPropertyAction("sun.boot.class.path")));
	URL[] urls = Launcher.getBootstrapClassPath().getURLs();
	for (int i = 0; i < urls.length; i++) {
	    System.out.println(urls[i]);
	}

	System.out.println(System.getProperty("java.class.path"));
	System.out.println(System.getProperty("java.ext.dirs"));
	System.out.println(ClassLoader.getSystemResource("java/lang/String.class"));

	ClassLoader extensionClassloader = ClassLoader.getSystemClassLoader()
		.getParent();
	System.out.println("the parent of extension classloader : "
		+ extensionClassloader.getParent());

    }
}
