package com.audaque.lib.core.utils;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author deshu.lin (deshu.lin@audaque.com)
 * 
 */
public class AdqFileUtils extends FileUtils {

    /**
     * 获取应用系统根目录
     */
    public static String getUserDir() {
        return System.getProperty("user.dir");
    }
    
    /**
     * 设置应用系统根目录
     * @param dir   应用系统根目录
     */
    public static void setUserDir(String dir) {
        System.setProperty("user.dir", dir);
    }

    /**
     * 获取系统资源文件完整路径
     * 
     * @param folder
     *            目录名称，e.g etc
     * @param file
     *            文件名称，e.g log4j.properties
     */
    public static String getSystemFileCanonicalPath(String folder, String file) {
        String resolvedLocationPattern = getUserDir() + File.separator + folder + File.separator + file;
        return resolvedLocationPattern;
    }

    /**
     * make directories. 
     * @param fileName  the directory name
     */
    public static String mkdirs(String fileName) {
        String holdedFileName = handleFileName(fileName);
        String dir = holdedFileName.substring(0, holdedFileName.lastIndexOf(File.separator) + 1);
        if (StringUtils.isNotEmpty(dir)) {
            File dirFile = new File(dir);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
        }

        return dir;
    }

    public static String handleFileName(String fileName) {
        return fileName.replaceAll("/", "\\" + File.separator).replaceAll("\\\\", "\\" + File.separator);
    }
}
