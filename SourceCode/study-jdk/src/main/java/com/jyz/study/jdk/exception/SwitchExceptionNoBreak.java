package com.jyz.study.jdk.exception;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

/**
 * case子句里面 throw exception的话，就不需要break了
 * @author JoyoungZhang@gmail.com
 *
 */
public class SwitchExceptionNoBreak {
	
	public static void main(String[] args) throws IOException, SQLException, NoSuchMethodException {
		printNormal(2);
		System.out.println("=============");
		printException(2);
	}
	
	private static void printNormal(int p){
		switch(p){
			case 1 : System.out.println(1);
			case 2 : System.out.println(2);
			default:System.out.println("other");
		}
	}
	
	private static void printException(int p) throws IOException, SQLException, NoSuchMethodException{
		switch(p){
			case 1 : throw new FileNotFoundException();
			case 2 : throw new SQLException();
			default: throw new NoSuchMethodException();
		}
	}

}
