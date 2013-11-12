package com.audaque.lib.core.i18n;

import java.util.ResourceBundle;

/**
 * {@link ResourceBundleProvider}适配器，提供默认实现
 * @author deshu.lin (deshu.lin@audaque.com)
 *
 */
public class ResourceBundleProviderAdaptor implements ResourceBundleProvider {

    @Override
    public ResourceBundle[] getResourceBundles() {
        return null;
    }

    @Override
    public String[] getResourceLocations() {
        return null;
    }
}
