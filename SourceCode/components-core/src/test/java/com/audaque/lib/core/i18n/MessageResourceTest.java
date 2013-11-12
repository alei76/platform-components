package com.audaque.lib.core.i18n;

import java.util.Locale;

import junit.framework.TestCase;

import com.audaque.lib.core.utils.AdqStringUtils;

/**
 * @author deshu.lin (deshu.lin@audaque.com)
 * 
 */
public class MessageResourceTest extends TestCase {

    public void testGUI() throws Throwable {
        assertEquals("gui_zh_CN", MessageResource.getInstance().getMessage("gui", Locale.CHINA));
        Locale.setDefault(Locale.CHINA);
        assertEquals("gui_en", MessageResource.getInstance().getMessage("gui", Locale.CANADA));
        Locale.setDefault(Locale.CHINA);
        assertEquals("gui_zh_CN", MessageResource.getInstance().getMessage("gui"));
        assertEquals("gui_en", MessageResource.getInstance().getMessage(AdqStringUtils.getPath(MessageResource.class, "gui")
                , "gui", Locale.CANADA));
        MessageResource.customeLocale(Locale.ENGLISH);
        assertEquals("gui_en", MessageResource.getInstance().getMessage(AdqStringUtils.getPath(MessageResource.class, "gui")
                , "gui", Locale.CANADA));
    }
    
    public void testServer() throws Throwable {
        assertEquals("server_en_US", MessageResource.getInstance().getMessage("server", Locale.US));
        assertEquals("server_zh_CN", MessageResource.getInstance().getMessage("server", Locale.CHINA));
    }

    public void testOnlyCN() {
        Locale.setDefault(Locale.CHINA);
        assertEquals("zh_CN", MessageResource.getInstance().getMessage("only"));
        assertEquals("zh_CN", MessageResource.getInstance().getMessage("only", Locale.CHINA));
    }

}
