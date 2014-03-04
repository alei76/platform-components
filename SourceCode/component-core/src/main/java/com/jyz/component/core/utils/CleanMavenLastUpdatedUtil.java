package com.jyz.component.core.utils;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * 清理maven lastUpdate文件
 * @author JoyoungZhang@gmail.com
 *
 */
public class CleanMavenLastUpdatedUtil {

    private static final String  REPO = "D:\\Maven\\repo";
    private static final String FILE_SUFFIX = "lastUpdated"; 
    
    private static Set<String> getAbandonPath(File file){
	Set<String> abandon = new HashSet<String>();
	if(file.isDirectory()){
	    for(File childFile : file.listFiles()){
		abandon.addAll(getAbandonPath(childFile));
	    }
	}
	if(file.getName().endsWith(FILE_SUFFIX)){
	    abandon.add(file.getParentFile().getAbsolutePath());
	}
	return abandon;
    }
    
    private static Set<String> getAbandonPath(String repo){
	File file = new File(repo);
	if(!file.exists()){
	    System.out.println("file " + repo + " not exists.");
	    System.exit(0);
	}
	return getAbandonPath(file);
    }
    
    private static void clean(File file){
	if(file.isDirectory()){
	    for(File child : file.listFiles()){
		clean(child);
	    }
	}
	file.delete();
    }

    public static void main(String[] args) {
	Set<String> abandon = getAbandonPath(REPO);
	System.out.println("find abandon file:" + abandon.size());
	for(String path : abandon){
	    File file = new File(path);
	    clean(file);
	    System.out.println(path + " delete success.");
	}
    }
}
