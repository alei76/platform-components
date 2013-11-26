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


/**
 * 
the bootstrapLoader : null
the extClassloader : sun.misc.Launcher$ExtClassLoader@1c78e57
the appClassLoader : sun.misc.Launcher$AppClassLoader@6b97fd

bootstrapLoader加载以下文件：
file:/C:/Program%20Files/Java/jdk1.6.0_27/jre/lib/resources.jar
file:/C:/Program%20Files/Java/jdk1.6.0_27/jre/lib/rt.jar
file:/C:/Program%20Files/Java/jdk1.6.0_27/jre/lib/sunrsasign.jar
file:/C:/Program%20Files/Java/jdk1.6.0_27/jre/lib/jsse.jar
file:/C:/Program%20Files/Java/jdk1.6.0_27/jre/lib/jce.jar
file:/C:/Program%20Files/Java/jdk1.6.0_27/jre/lib/charsets.jar
file:/C:/Program%20Files/Java/jdk1.6.0_27/jre/lib/modules/jdk.boot.jar
file:/C:/Program%20Files/Java/jdk1.6.0_27/jre/classes

extClassloader加载以下文件：
C:\Program Files\Java\jdk1.6.0_27\jre\lib\ext;C:\Windows\Sun\Java\lib\ext

appClassLoader加载以下文件：
E:\GoogleCode\platform-components\trunk\SourceCode\study-jdk\target\classes;E:\GoogleCode\platform-components\trunk\SourceCode\component-core\target\classes;C:\Users\audaque\.m2\repository\junit\junit\3.8.1\junit-3.8.1.jar

 */






