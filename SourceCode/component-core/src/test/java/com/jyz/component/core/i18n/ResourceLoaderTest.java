package com.jyz.component.core.i18n;

import java.util.Locale;

import junit.framework.TestCase;

public class ResourceLoaderTest extends TestCase {
	
	public void testGui() throws Throwable {
		
		System.out.println(ResourcesLoader.getInstance().getString("server"));
		
		System.out.println(ResourcesLoader.getInstance().getString("gui1"));
		System.out.println(ResourcesLoader.getInstance().getString("gui2"));
		
		System.out.println(ResourcesLoader.getInstance().getString("gui1", Locale.US));
		System.out.println(ResourcesLoader.getInstance().getString("gui2", Locale.US));
		
		System.out.println(ResourcesLoader.getInstance().getString("gui1", Locale.CHINA, "1","2"));
		System.out.println(ResourcesLoader.getInstance().getString("gui2", Locale.CHINA, "1","2","3"));
		
		System.out.println(ResourcesLoader.getInstance().getString("com.jyz.component.core.i18n.gui", "gui1"));
		System.out.println(ResourcesLoader.getInstance().getString("com.jyz.component.core.i18n.gui", "gui1", Locale.US));
		
		System.out.println(ResourcesLoader.getInstance().getString("com.jyz.component.core.i18n.gui", "gui1", Locale.CHINA, "1","2","3"));
    
	}
	
}
