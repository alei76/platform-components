package com.jyz.study.jdk.classLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * 
 * 这个例子中类加载顺序为BootstrapClassPath----ExtClassPath--SystemClassPath--CClassLoader
 * --DClassLoader
 * <p>
 * 如果不想加载的指定类可以把CClassLoader 的 super.findClass
 * 去掉，但是最好在前面加上findLoadedClass判断类是否已经加载过，防止重复加载
 * <p>
 * 一般而言编写classLoacer只需重载findClass方法即可，loadClass方法会调用findClass方法
 * <p>
 * 如果加载的不是一个class而是一个jar，可以使用URLClassLoader来加载jar包
 * <p>
 * 一般应该服务器会有热加载机制，每个应用服务器对webapp下面应用都会有自己的加载器，当class文件是否有修改，判断文件的lastModify()的修改时间，
 * 如果是修改过则重新加载文件
 * <p>
 * 
 * @author JoyoungZhang@gmail.com
 * 
 */
public class ClassLoaderTest {
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

		
		
		DClassLoader loader = new DClassLoader();

		// Class c = loader.loadClass("java.lang.String"); Object o =
		// c.newInstance();

		Class c = loader.loadClass("LocalClass");
		Object o = c.newInstance();
		Method m = c.getMethod("getClass");
		System.out.println(m.invoke(o));
	}
}


// 寻找C盘class
class CClassLoader extends ClassLoader {
	private final static String DIR_PATHC = "c:/java/";

	protected Class<?> findClass(String s) {
		Class cls = null;
		try {
			cls = super.findClass(s);
		} catch (ClassNotFoundException e1) {
		}
		if (cls != null) {
			System.out.println(super.getClass().getClassLoader() + "  find "
					+ s);
			return cls;
		} else {
			System.out.println(super.getClass().getClassLoader() + " not find "
					+ s);
		}
		byte[] bys = null;
		try {
			bys = getBytes(s);
			cls = defineClass(null, bys, 0, bys.length);
		} catch (IOException e) {
		}
		if (cls != null) {
			System.out.println("CClassLoader find " + s);
		} else {
			System.out.println("CClassLoader not find " + s);
		}
		return cls;
	}

	// 从本地读取文
	private byte[] getBytes(String className) throws IOException {
		String path = DIR_PATHC + className + ".class";
		File file = new File(path);
		long len = file.length();
		byte raw[] = new byte[(int) len];
		FileInputStream fin = new FileInputStream(file);
		int r = fin.read(raw);
		if (r != len) {
			throw new IOException("Can't read all, " + r + " != " + len);
		}
		fin.close();
		return raw;
	}
}

// 寻找D盘class
class DClassLoader extends CClassLoader {
	private final static String DIR_PATHD = "d:/java/";

	protected Class<?> findClass(String s) {
		Class cls = null;
		cls = super.findClass(s);
		if(cls != null ){
			return cls;
		}
		byte[] bys = null;
		try {
			bys = getBytes(s);
			cls = defineClass(null, bys, 0, bys.length);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (cls != null) {
			System.out.println("DClassLoader find " + s);
		} else {
			System.out.println("DClassLoader not find " + s);
		}
		return cls;
	}

	// 从本地读取文件
	private byte[] getBytes(String className) throws IOException {
		String path = DIR_PATHD + className + ".class";
		File file = new File(path);
		long len = file.length();
		byte raw[] = new byte[(int) len];
		FileInputStream fin = new FileInputStream(file);
		int r = fin.read(raw);
		if (r != len) {
			throw new IOException("Can't read all, " + r + " != " + len);
		}
		fin.close();
		return raw;
	}
}






