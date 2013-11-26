package com.jyz.study.jdk.classLoader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Arrays;

/**
 * 自定义ClassLoader
 * @author JoyoungZhang@gmail.com
 * 
 */
public class JyzClassLoader extends ClassLoader {

	private String classPath;

	public JyzClassLoader(String classPath) {
		this.classPath = classPath;
	}

	@Override
	protected Class<?> findClass(String className)
			throws ClassNotFoundException {
		byte[] bytes = loadClassData(className);
		Class<?> clazz = defineClass(className, bytes, 0, bytes.length);
		fillSigners(clazz);
		return clazz;
	}
	
	//测试ClassLoader setSigners的用法
	private void fillSigners(Class<?> clazz){
		Object[] singers = new Object[1];
		singers[0] = "JoyoungZhang@gmail.com";
		this.setSigners(clazz, singers);
	}

	private byte[] loadClassData(String className)
			throws ClassNotFoundException {
		try {
			String classFile = getClassFile(className);
			FileInputStream fis = new FileInputStream(classFile);
			FileChannel fileC = fis.getChannel();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			WritableByteChannel outC = Channels.newChannel(baos);
			ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
			while (true) {
				int i = fileC.read(buffer);
				if (i == 0 || i == -1) {
					break;
				}
				buffer.flip();
				outC.write(buffer);
				buffer.clear();
			}
			fis.close();
			return baos.toByteArray();
		} catch (IOException fnfe) {
			throw new ClassNotFoundException(className);
		}
	}

	private String getClassFile(String name) {
		StringBuffer sb = new StringBuffer(classPath);
		sb.append(File.separator).append(name.replace('.', File.separatorChar)).append(".class");
		return sb.toString();
	}

	public static void main(String[] args) {
		try {
			JyzClassLoader classLoader = new JyzClassLoader("E:\\GoogleCode\\platform-components\\trunk\\SourceCode\\component-core\\target\\classes");
			Class<?> clazz = classLoader.loadClass("com.jyz.component.core.collection.Tuple");
			
			JyzClassLoader classLoader2 = new JyzClassLoader("E:\\GoogleCode\\platform-components\\trunk\\SourceCode\\component-core\\target\\classes");
			Class<?> clazz2 = classLoader2.loadClass("com.jyz.component.core.collection.Tuple");
			
			System.out.println(clazz == clazz2);//return false
			System.out.println(clazz.equals(clazz2));//return false
			
			System.out.println("c1.getSigners is " + Arrays.toString(clazz.getSigners()));
			System.out.println(clazz.newInstance());
			System.out.println(Arrays.toString(classLoader.getPackages()));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}

	}

}
