package com.jyz.study.jdk.logger;

public class User {
	
	private String name;
	private int age;
	
	public User(String name, int age) {
		this.name = name;
		this.age = age;
	}


	public String generater() {
		return "User[name:"+name+", age:"+age+"] ";
	}
	
}
