package com.jyz.study.jdk.resourcebundle;

import java.util.ResourceBundle;

/**
 *  测试从classpath下的jar里获取资源文件
 *	@author zhaoyong.zhang
 *	create time 2013-12-12
 */
public class TestJarResourceBundle {

	public static void main(String[] args) {
		//form component-io.jar
		ResourceBundle bundle = ResourceBundle.getBundle("com/jyz/component/io/io");
		System.out.println(bundle.getString("1001"));
		//form component-core.jar 传递依赖is ok
		ResourceBundle bundle2 = ResourceBundle.getBundle("com/jyz/component/core/i18n/gui");
		System.out.println(bundle2.getString("gui1"));
		//form 外部.jar, Can't find bundle for base name com/audaque/datadiscovery/bundle, locale zh_CN
		ResourceBundle bundle3 = ResourceBundle.getBundle("com/audaque/datadiscovery/bundle");
		System.out.println(bundle3.getString("003002"));
	}
}
