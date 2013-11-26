package com.jyz.study.jdk.classLoader;

import java.util.HashMap;

import sun.security.util.SecurityConstants;

/**
 * ClassLoader源码
 * 
 * @author JoyoungZhang@gmail.com
 */
public abstract class ClassLoaderSrc {

	// 父ClassLoader
	private ClassLoader parent;

	// 被此classLoader加载过的Class对象
	private Vector classes = new Vector();
	
	// The packages defined in this class loader.  Each package name is mapped
    // to its corresponding Package object.
    private HashMap packages = new HashMap();

	// 由虚拟机调用
	void addClass(Class c) {
		classes.addElement(c);
	}

	// The packages defined in this class loader. Each package name is mapped
	// to its corresponding Package object.
	private final HashMap<String, Package> packages = new HashMap<String, Package>();

	// 指明parent
	protected ClassLoader(ClassLoader parent) {
		this(checkCreateClassLoader(), parent);
	}

	// 不指名parent时使用SystemClassLoader
	protected ClassLoader() {
		this(checkCreateClassLoader(), getSystemClassLoader());
	}

	// 默认resolve=false
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		return loadClass(name, false);
	}

	protected synchronized Class<?> loadClass(String name, boolean resolve)
			throws ClassNotFoundException {
		// 1 检查此class是否被此classloader加载过，
		// 最终是有native方法返回，native方法会使用到classes集合
		Class c = findLoadedClass(name);
		// 1.1 未被加载
		if (c == null) {
			try {
				// 1.1.1 此classloader有parent，委托parent去load
				if (parent != null) {
					c = parent.loadClass(name, false);
				} else {// 1.1.2 此classloader无parent = 启动类装载器去加载
					c = findBootstrapClassOrNull(name);
				}
			} catch (ClassNotFoundException e) {
			}
			// 如果没有找到class，自定义findCalss去加载
			if (c == null) {
				c = findClass(name);
			}
		}
		// 找到的Class对象是否需要连接操作
		if (resolve) {
			resolveClass(c);
		}
		// 1.2 被加载过，直接返回
		return c;
	}

	protected final Class<?> findLoadedClass(String name) {
		if (!checkName(name))
			return null;
		return findLoadedClass0(name);
	}

	// 如果name里包含/，或者虚拟机不支持class数组你使用了数组的时候返回false，其它情况返回true，包括空的name
	// eg com.jyz.component.core.collection.Tuple return true
	private boolean checkName(String name) {
		if ((name == null) || (name.length() == 0))
			return true;
		if ((name.indexOf('/') != -1)
				|| (!VM.allowArraySyntax() && (name.charAt(0) == '[')))
			return false;
		return true;
	}

	// 检查package是否可访问
	private void checkPackageAccess(Class cls, ProtectionDomain pd) {
		final SecurityManager sm = System.getSecurityManager();
		if (sm != null) {
			// ...
		}
		domains.add(pd);
	}

	// 自定义classloader时重写此方法
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		throw new ClassNotFoundException(name);
	}

	// 将byte数组转换成一个Class对象，最终有native方法实现
	protected final Class<?> defineClass(String name, byte[] b, int off, int len)
			throws ClassFormatError {
		return defineClass(name, b, off, len, null);
	}

	/*
	 * Determine protection domain, and check that: - not define java.* class, -
	 * signer of this class matches signers for the rest of the classes in
	 * package.
	 */
	//native的defineClass时会调用此方法检查name是否合法
	//首先checkName，然后还需要!name.startsWith("java.")
	//所以我们定义了java.mypackage包，都将异常
	//java.lang.SecurityException: Prohibited package name: java.mypackage
	private ProtectionDomain preDefineClass(String name,
			ProtectionDomain protectionDomain) {
		if (!checkName(name))
			throw new NoClassDefFoundError("IllegalName: " + name);
		if ((name != null) && name.startsWith("java.")) {
			throw new SecurityException("Prohibited package name: "
					+ name.substring(0, name.lastIndexOf('.')));
		}
		//...
	}

	// protected的resolveClass方法，可以在自定义的classloader调用
	protected final void resolveClass(Class<?> c) {
		resolveClass0(c);
	}
	
	//获得appClassLoader，实际调用Launcher完成
	public static ClassLoader getSystemClassLoader() {
		sun.misc.Launcher l = sun.misc.Launcher.getLauncher();
		return l.getClassLoader();
    }

}
