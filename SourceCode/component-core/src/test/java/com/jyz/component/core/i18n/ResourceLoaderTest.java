package com.jyz.component.core.i18n;

import java.util.Locale;

import junit.framework.TestCase;

public class ResourceLoaderTest extends TestCase {

	public void testGui() throws Throwable {
		System.out.println(ResourcesLoader.getInstance().getString("gui1"));
		System.out.println(ResourcesLoader.getInstance().getString("gui2"));
		
		System.out.println(ResourcesLoader.getInstance().getString(Locale.US, "gui1"));
		System.out.println(ResourcesLoader.getInstance().getString(Locale.US, "gui2"));
		
		System.out.println(ResourcesLoader.getInstance().getString("gui1", "1","2"));
		System.out.println(ResourcesLoader.getInstance().getString("gui2", "1","2","3"));
    }
	
}
