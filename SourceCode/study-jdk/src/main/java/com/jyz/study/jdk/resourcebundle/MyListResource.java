package com.jyz.study.jdk.resourcebundle;

import java.util.ListResourceBundle;
import java.util.ResourceBundle;

/**
 * ListResourceBundle的使用
 * @author JoyoungZhang@gmail.com
 */
public class MyListResource extends ListResourceBundle {
	
	protected Object[][] getContents() {
		         return new Object[][]{
		        	 {"sa", "sav"},
		        	 {"sb", "sbv"},
		         };
	}
	
	public static void main(String[] args) {
		MyListResource resources = new MyListResource();
		
//		ResourceBundle resources = ResourceBundle.getBundle("com.jyz.study.jdk.resourcebundle.MyListResource");
		System.out.println(resources.getObject("sa"));
		System.out.println(resources.getKeys());
//		System.out.println(resources.handleKeySet());
		
	}
	
}
