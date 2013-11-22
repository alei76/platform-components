package com.jyz.study.jdk.annotation;

import java.io.IOException;

/**
 * 测试package-info.java文件的作用
 * 1、为标注在包上Annotation提供便利；<br>  
 * 2、声明包的私有类和常量；<br>  
 * @author JoyoungZhang@gmail.com
 *
 */
public class TestPackageInfo {

	public static void main(String[] args) {
		//1
		Package p = Package.getPackage("com.jyz.study.jdk.annotation");
	    if(p != null && p.isAnnotationPresent(JyzTargetPackage.class)){
	    	JyzTargetPackage nav = p.getAnnotation(JyzTargetPackage.class);
	    	if(nav != null){ 
	    		System.out.println("package version:" + nav.version());
	    	}
	    }
	    
	    //2
		PackageInfo packageInfo = new PackageInfo();
		packageInfo.common();
		
		PackageInfoGeneric<Exception> packageInfoGeneric = new PackageInfoGeneric<Exception>();
		packageInfoGeneric.set(new IOException("device io"));
		packageInfoGeneric.common();
		
		System.out.println(PackageConstants.ERROE_CODE);
	}
}
