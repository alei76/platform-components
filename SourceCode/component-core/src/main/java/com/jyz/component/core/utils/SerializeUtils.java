package com.jyz.component.core.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 
 *	@author JoyoungZhang@gmail.com
 *
 */
public final class SerializeUtils {

	/**
	 * 
	 * @param object is want to serialize
	 * @return
	 * @throws IOException
	 */
	public static byte[] serializeObject(Serializable object) throws IOException {
		byte[] buffer = null;
		ByteArrayOutputStream bos = null;
		ObjectOutputStream oos = null;
		try{
			bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);
			oos.writeObject(object);
			oos.flush();
			buffer =  bos.toByteArray();
		}catch(IOException ex){
			throw ex;
		}finally{
			if(oos != null){
				oos.close();
			}
			if(bos != null){
				bos.close();
			}
		}
		return buffer;
	}

	/**
	 * 
	 * @param buf is want to deserialize
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings({ "unchecked" })
	public static Serializable deserializeObject(byte[] buf) throws IOException, ClassNotFoundException {
	    	Serializable object = null;
		ByteArrayInputStream bis = null; 
		ObjectInputStream ois = null;
		try{
			bis = new ByteArrayInputStream(buf);
			ois = new ObjectInputStream(bis);
			object = (Serializable) ois.readObject();
		}catch(IOException ex){
			throw ex;
		}finally{
			if(ois != null){
				ois.close();
			}
			if(bis != null){
				bis.close();
			}
		}
		return object;
	}
	

}
