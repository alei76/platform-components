package com.jyz.component.core.i18n;

import junit.framework.TestCase;

public class ResourceLoaderTest extends TestCase {

	public void testGui() throws Throwable {
        assertEquals("gui_enss", ResourcesLoader.getInstance().getValue("gui"));
        assertEquals("xsserver", ResourcesLoader.getInstance().getValue("xs"));
    }
	
}
