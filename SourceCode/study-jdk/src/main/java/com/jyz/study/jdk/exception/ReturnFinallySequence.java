package com.jyz.study.jdk.exception;



/**
 * 1、 finally一般情况总会执行，除了调用System.exit(0)方法，该方法终止java虚拟机进程。 
 * 2、catch里不能return
 * 3、try块里return之前，finally会被执行。
 * 4、return语句会把后面的值复制到一份用来返回，如果return的是基本类型的，finally里对变量的改动将不起效果，如果return
 * 的是引用类型的，改动将可以起效果。 
 * 5、finally里的return语句会把try块里的return语句效果给覆盖掉。
 * 
 * 1、最好把return放到方法尾而不要在try里return 
 * 2、如果非要的话
 * 		2.1、不要在try块和finally块里都包含return
 * 		2.2、如果在try块里return, 则不要在finally块里操作被return的变量 
 * @author JoyoungZhang@gmail.com
 *
 */
public class ReturnFinallySequence {
	
	public static void main(String[] args) {
		System.out.println(test1());
		System.out.println(test2().getAge());
	}
	
	private static int test1(){
		int i=9;
		try{
			return i;
		}catch(Exception ex){
		}finally{
			i=8;
		}
		return 0;
	}
	
	private static Person test2(){
		Person person = new Person(20);
		try{
			return person;
		}catch(Exception ex){
		}finally{
			person.setAge(21);
		}
		return null;
	}
	
	static class Person {
		private int age;

		public Person(int age) {
			this.age = age;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}
		
	}

}
