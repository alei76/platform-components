package com.jyz.component.core.utils;

import org.apache.commons.lang.StringUtils;

/**
 * 
 *	@author JoyoungZhang@gmail.com
 *
 */
public class JyzStringUtils extends StringUtils{
	
	private static final String FOLDER_SEPARATOR = "/";

	public static String getPath(String packageName) {
        return packageName.replace(".", FOLDER_SEPARATOR);
    }

    public static String getPath(Class<?> clazz, String fileName) {
        String directory = getPath(clazz.getPackage().getName());
        return directory + FOLDER_SEPARATOR + fileName;
    }


}
