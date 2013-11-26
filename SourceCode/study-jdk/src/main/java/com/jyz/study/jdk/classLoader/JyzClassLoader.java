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
 * 
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
		Class clazz = defineClass(className, bytes, 0, bytes.length);
		Object[] singers = new Object[1];
		singers[0] = "gogogo";
		this.setSigners(clazz, singers);
		return clazz;
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
		name = name.replace('.', File.separatorChar) + ".class";
		sb.append(File.separator + name);
		return sb.toString();
	}

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {
		try {
			JyzClassLoader classLoader = new JyzClassLoader("E:\\GoogleCode\\platform-components\\trunk\\SourceCode\\component-core\\target\\classes");
			Class c1 = classLoader.loadClass("com.jyz.component.core.collection.Tuple");
			Class c2 = classLoader.loadClass("com.jyz.component.core.collection.Triple");
			System.out.println("c1.getSigners is " + Arrays.toString(c1.getSigners()));
			Object tuple = c1.newInstance();
			System.out.println(tuple);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}

	}

}
