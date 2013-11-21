package com.jyz.study.jdk.annotation;


/**
 * 演示注解的使用
 * @author JoyoungZhang@gmail.com
 *
 */
public class Member {

	@SQLString(30)
	String firstName;
	String lastName;
	@SQLInteger
	Integer age;
	@SQLString(value=30, constraints = @Constraints(primaryKey = true))
	String handle;
	static int memberCount;
	
	public String getHandle(){
		return handle;
	}
	
	public String getFirstName(){
		return firstName;
	}
	
	public String getLastName(){
		return lastName;
	}
	
	public Integer getAge(){
		return age;
	}

	@Override
	public String toString(){
		return handle;
	}
}
