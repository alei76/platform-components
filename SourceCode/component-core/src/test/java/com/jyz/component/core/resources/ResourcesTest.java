package com.jyz.component.core.resources;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

import junit.framework.TestCase;

public class ResourcesTest extends TestCase {
	
	public void testGetResourceURL() throws IOException{
		URL url = Resources.getResourceURL("com/jyz/component/core/i18n/手机号码.jar");
		System.out.println(url);
		URLClassLoader cl = new URLClassLoader(new URL[]{url});
		System.out.println(cl.getResource("std.xml"));
	}

}
