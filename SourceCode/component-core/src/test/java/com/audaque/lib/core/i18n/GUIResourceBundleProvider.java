package com.audaque.lib.core.i18n;

import com.audaque.lib.core.utils.AdqStringUtils;

/**
 * @author deshu.lin (deshu.lin@audaque.com)
 *
 */
public class GUIResourceBundleProvider extends ResourceBundleProviderAdaptor {

	@Override
	public String[] getResourceLocations() {
	    return new String[]{AdqStringUtils.getPath(GUIResourceBundleProvider.class, "gui")};
	}
	
}
