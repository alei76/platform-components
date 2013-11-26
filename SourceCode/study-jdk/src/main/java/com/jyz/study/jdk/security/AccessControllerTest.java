package com.jyz.study.jdk.security;

import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * 
 * @author JoyoungZhang@gmail.com
 *
 */
public class AccessControllerTest {

    public static void main(String[] args) {
	String name = AccessController.doPrivileged(new PrivilegedAction<String>() {
	    @Override
	    public String run() {
		return "1";
	    }
	});
	System.out.println(name);
    }
}
