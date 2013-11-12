package com.audaque.lib.core.i18n;

import java.util.ResourceBundle;

/**
 * 资源集提供者，可以提供ResourceBundle集，也可以提供ResourceLocations
 * @author lindeshu <deshu.lin@audaque.com>
 */
public interface ResourceBundleProvider {

    /**
     * 提供资源集
     */
    ResourceBundle[] getResourceBundles();

    /**
     * 提供资源名称集
     */
    String[] getResourceLocations();
}
