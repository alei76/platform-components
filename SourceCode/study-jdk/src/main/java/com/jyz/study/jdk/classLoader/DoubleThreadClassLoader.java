package com.jyz.study.jdk.classLoader;

/**
 * 测试ClassLoader的缓存机制
 * ClassLoader findLoadedClass
 * 缓存JVM某个ClassLoader已经加载过的class
 * @author JoyoungZhang@gmail.com
 *
 */
public class DoubleThreadClassLoader {

    public static void main(String[] args) throws ClassNotFoundException {
	JyzClassLoader classLoader = new JyzClassLoader("D:\\GoogleCode\\platform-components\\trunk\\SourceCode\\component-core\\target\\classes");
	new CheckThread(classLoader).start();
	new CheckThread(classLoader).start();
    }
}

class CheckThread extends Thread {

    private ClassLoader classLoader;

    public CheckThread(ClassLoader classLoader) {
	this.classLoader = classLoader;
    }
    
    public void run(){
	try {
	    Class<?> clazz = classLoader.loadClass("com.jyz.component.core.collection.Tuple");
	    System.out.println(clazz.newInstance());
	} catch (ClassNotFoundException e) {
	    e.printStackTrace();
	} catch (InstantiationException e) {
	    e.printStackTrace();
	} catch (IllegalAccessException e) {
	    e.printStackTrace();
	}
    }

}
