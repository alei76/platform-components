package com.jyz.study.jdk.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 演示四种元注解的用法
 * @Target
 * @Retention
 * @Document
 * @Inherited
 * 
 * @author JoyoungZhang@gmail.com
 *
 */
@JyzTargetType
@JyzRetentionRuntime
@JyzDocument
@JyzInherited
public class MetaAnnotation {
	@JyzTargetField
	private String info;
	
	@JyzTargetConstructor
	public MetaAnnotation(@JyzTargetParamter String info) {
		this.info = info;
	}
	
	@JyzTargetMethod
	public void test(){
		@JyzTargetLocalVariable
		String infoInner = "sa";
	}
	
	public static void main(String[] args) {
		Package p = Package.getPackage("com.jyz.study.jdk.annotation");
	    if(p != null && p.isAnnotationPresent(JyzTargetPackage.class)){
	    	JyzTargetPackage nav = p.getAnnotation(JyzTargetPackage.class);
	    	if(nav != null){ 
	    		System.out.println(nav.value());
	    	}
	    }
	}
}

@Target(ElementType.TYPE) @interface JyzTargetType{} 						//接口、类、枚举、注解
@Target(ElementType.FIELD) @interface JyzTargetField{}						//字段、枚举的常量
@Target(ElementType.METHOD) @interface JyzTargetMethod{}					//方法
@Target(ElementType.PARAMETER) @interface JyzTargetParamter{}				//方法参数
@Target(ElementType.CONSTRUCTOR) @interface JyzTargetConstructor{}			//构造函数
@Target(ElementType.LOCAL_VARIABLE) @interface JyzTargetLocalVariable{}		//局部变量
@Target(ElementType.ANNOTATION_TYPE) @interface JyzTargetAnnotationType{}	//注解
@Target(ElementType.PACKAGE) @Retention(RetentionPolicy.RUNTIME) @interface JyzTargetPackage{public int value() default 10;}					//包 
@JyzTargetAnnotationType @interface JyzTargetAll{}													

@Retention(RetentionPolicy.SOURCE) @interface JyzRetentionSource{}
@Retention(RetentionPolicy.CLASS)@interface JyzRetentionClass{}
@Retention(RetentionPolicy.RUNTIME)@interface JyzRetentionRuntime{}

@Documented @interface JyzDocument{}

@Inherited @interface JyzInherited{}