package com.jyz.component.io.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import com.jyz.component.core.exception.JyzRuntimeException;

/**
 *	@author JoyoungZhang@gmail.com
 *
 */
public class IOUtils {

	public static void closeInputStream(InputStream in){
		if(in == null){
			return;
		}
		try {
			in.close();
		} catch (IOException e) {
			throw new JyzRuntimeException(e);
		}
	}
	
	public static void closeOutputStream(OutputStream out){
		if(out == null){
			return;
		}
		try {
			out.close();
		} catch (IOException e) {
			throw new JyzRuntimeException(e);
		}
	}
	
	public static void closePrinter(Writer writer){
		if(writer == null){
			return;
		}
		try {
			writer.close();
		} catch (IOException e) {
			throw new JyzRuntimeException(e);
		}
	}
	
	public static void closeOutputStream(Reader reader){
		if(reader == null){
			return;
		}
		try {
			reader.close();
		} catch (IOException e) {
			throw new JyzRuntimeException(e);
		}
	}
	
}
