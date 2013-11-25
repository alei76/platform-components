package com.jyz.study.jdk.classLoader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

/**
 * 自定义ClassLoader
 * @author JoyoungZhang@gmail.com
 * 
 */
public class JyzClassLoader extends ClassLoader {

    private String fileName;

    public JyzClassLoader(String fileName) {
	this.fileName = fileName;
    }

    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException {
	byte[] bytes = loadClassData(className);
	return defineClass(className, bytes, 0, bytes.length);
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
	StringBuffer sb = new StringBuffer(fileName);
	name = name.replace('.', File.separatorChar) + ".class";
	sb.append(File.separator + name);
	return sb.toString();
    }

    public static void main(String[] args) {
	try {
	    JyzClassLoader classLoader = new JyzClassLoader(
		    "D:\\GoogleCode\\platform-components\\trunk\\SourceCode\\component-core\\target\\classes");
	    Class c = classLoader
		    .loadClass("com.jyz.component.core.collection.Tuple");
	    c = classLoader
		    .loadClass("com.jyz.component.core.collection.Tuple");
	    Object tuple = c.newInstance();
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
