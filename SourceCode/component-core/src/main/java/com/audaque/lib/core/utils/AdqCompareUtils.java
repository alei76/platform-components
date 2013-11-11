package com.audaque.lib.core.utils;

import org.apache.commons.collections.ComparatorUtils;
import org.apache.commons.lang.ObjectUtils;

/**
 * @author deshu.lin (deshu.lin@audaque.com)
 *
 */
public class AdqCompareUtils extends ComparatorUtils {

    /**
     * compare two object
     * @param source    source
     * @param target    target
     * @return  the compare result. null will be smaller.
     */
    @SuppressWarnings("unchecked")
    public static <T> int compare(Comparable<T> source, Comparable<T> target) {
        if(ObjectUtils.equals(source, target)) {
            return 0;
        }
        if(source == null) {
            if(target == null) {
                return 0;
            }
            return -1;
        }
        if(target == null) {
            return 1;
        }
        return source.compareTo((T) target);
    }
    
}
