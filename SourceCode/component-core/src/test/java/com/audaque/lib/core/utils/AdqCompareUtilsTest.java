package com.audaque.lib.core.utils;

import junit.framework.TestCase;

/**
 * @author deshu.lin (deshu.lin@audaque.com)
 *
 */
public class AdqCompareUtilsTest extends TestCase {

    public void testNull() {
        assertEquals(0, AdqCompareUtils.compare((String)null, (String)null));
    }
    
    public void testNullSmaller() {
        assertEquals(-1, AdqCompareUtils.compare((String)null, "1"));
    }
    
    public void testEqauls() {
        assertEquals(0, AdqCompareUtils.compare("1", "1"));
    }
    
    public void testNotEmpty() {
        assertEquals(-1, AdqCompareUtils.compare("1", "2"));
    }
    
}
