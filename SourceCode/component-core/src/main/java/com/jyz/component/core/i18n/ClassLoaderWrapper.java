package com.jyz.component.core.i18n;

import java.io.InputStream;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 
 * 1.ClassLoader包装器
 * 2.通过
 * getResourceAsURL
 * getResourceAsStream
 * classForName
 * 三种方式加载资源时，
 * 会尝试使用
 * classLoader 
 * defaultClassLoader
 * Thread.currentThread().getContextClassLoader()
 * getClass().getClassLoader()
 * systemClassLoader
 * 去加载
 * 
 * @author JoyoungZhang@gmail.com
 * 
 */
public class ClassLoaderWrapper {
	
	ClassLoader defaultClassLoader;
	ClassLoader systemClassLoader;

	ClassLoaderWrapper() {
		try {
			systemClassLoader = ClassLoader.getSystemClassLoader();
		} catch (SecurityException ignored) {
		}
	}

	/*
	 * Get a resource as a URL using the current class path
	 * @param resource - the resource to locate
	 * @return the resource or null
	 */
	public URL getResourceAsURL(String resource) {
		return getResourceAsURL(resource, getClassLoaders(null));
	}

	/*
	 * Get a resource from the classpath, starting with a specific class loader
	 * @param resource - the resource to find
	 * @param classLoader - the first classloader to try
	 * @return the stream or null
	 */
	public URL getResourceAsURL(String resource, ClassLoader classLoader) {
		return getResourceAsURL(resource, getClassLoaders(classLoader));
	}

	/*
	 * Get a resource from the classpath
	 * @param resource - the resource to find
	 * @return the stream or null
	 */
	public InputStream getResourceAsStream(String resource) {
		return getResourceAsStream(resource, getClassLoaders(null));
	}

	/*
	 * Get a resource from the classpath, starting with a specific class loader
	 * @param resource - the resource to find
	 * @param classLoader - the first class loader to try
	 * @return the stream or null
	 */
	public InputStream getResourceAsStream(String resource, ClassLoader classLoader) {
		return getResourceAsStream(resource, getClassLoaders(classLoader));
	}

	/*
	 * Find a class on the classpath (or die trying)
	 * @param name - the class to look for
	 * @return - the class
	 * @throws ClassNotFoundException Duh.
	 */
	public Class<?> classForName(String name) throws ClassNotFoundException {
		return classForName(name, getClassLoaders(null));
	}

	/*
	 * Find a class on the classpath, starting with a specific classloader (or
	 * die trying)
	 * @param name - the class to look for
	 * @param classLoader - the first classloader to try
	 * @return - the class
	 * @throws ClassNotFoundException Duh.
	 */
	public Class<?> classForName(String name, ClassLoader classLoader) throws ClassNotFoundException {
		return classForName(name, getClassLoaders(classLoader));
	}
	
	/*
	 * Try to get a resource from a group of classloaders
	 * @param resource - the resource to get
	 * @param classLoader - the classloaders to examine
	 * @return the resource or null
	 */
	InputStream getResourceAsStream(String resource, ClassLoader[] classLoader) {
		for (ClassLoader cl : classLoader) {
			if (null != cl) {
				// try to find the resource as passed
				InputStream returnValue = cl.getResourceAsStream(resource);
				// now, some class loaders want this leading "/", so we'll add
				// it and try again if we didn't find the resource
				if (null == returnValue) {
					returnValue = cl.getResourceAsStream("/" + resource);
				}
				if (null != returnValue){
					return returnValue;
				}
			}
		}
		return null;
	}

	/*
	 * Get a resource as a URL using the current class path
	 * @param resource - the resource to locate
	 * @param classLoader - the class loaders to examine
	 * @return the resource or null
	 */
	URL getResourceAsURL(String resource, ClassLoader[] classLoader) {
		URL url;
		for (ClassLoader cl : classLoader) {
			if (null != cl) {
				// look for the resource as passed in...
				url = cl.getResource(resource);
				// ...but some class loaders want this leading "/", so we'll add
				// it
				// and try again if we didn't find the resource
				if (null == url){
					url = cl.getResource("/" + resource);
				}
				// "It's always in the last place I look for it!"
				// ... because only an idiot would keep looking for it after
				// finding it, so stop looking already.
				if (null != url){
					return url;
				}
			}
		}
		// didn't find it anywhere.
		return null;
	}

	/*
	 * Attempt to load a class from a group of classloaders
	 * @param name - the class to load
	 * @param classLoader - the group of classloaders to examine
	 * @return the class
	 * @throws ClassNotFoundException - Remember the wisdom of Judge Smails:
	 * Well, the world needs ditch diggers, too.
	 */
	Class<?> classForName(String name, ClassLoader[] classLoader) throws ClassNotFoundException {
		for (ClassLoader cl : classLoader) {
			if (null != cl) {
				try {
					Class<?> c = Class.forName(name, true, cl);
					if (null != c){
						return c;
					}
				} catch (ClassNotFoundException e) {
					// we'll ignore this until all classloaders fail to locate
					// the class
				}
			}
		}
		throw new ClassNotFoundException("Cannot find class: " + name);
	}
	
	ResourceBundle getResourceBundle(String name, Locale locale){
		return getResourceBundle(name, locale, getClassLoaders(null));
	}
	
	ResourceBundle getResourceBundle(String name, Locale locale, ClassLoader classLoader){
		return getResourceBundle(name, locale, getClassLoaders(classLoader));
	}
	
	ResourceBundle getResourceBundle(String name, Locale locale, ClassLoader[] classLoader){
		for (ClassLoader cl : classLoader) {
			if (null != cl) {
				ResourceBundle resourceBundle = ResourceBundle.getBundle(name, locale, cl);
				if (null != resourceBundle){
					return resourceBundle;
				}
			}
		}
		return null;
	}

	/**
	 * 注意ClassLoader[]里的元素可能为空，为空的情况由使用者去处理
	 * @param classLoader 指定的ClassLoader
	 * @return 所有可能的ClassLoader数组
	 */
	ClassLoader[] getClassLoaders(ClassLoader classLoader) {
		return new ClassLoader[] { 
				classLoader, 
				defaultClassLoader,
				Thread.currentThread().getContextClassLoader(),
				getClass().getClassLoader(), 
				systemClassLoader };
	}

}
