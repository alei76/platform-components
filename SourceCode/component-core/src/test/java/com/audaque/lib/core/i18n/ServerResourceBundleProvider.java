package com.audaque.lib.core.i18n;

import com.audaque.lib.core.i18n.ResourceBundleProviderAdaptor;
import com.audaque.lib.core.utils.AdqStringUtils;

/**
 * @author deshu.lin (deshu.lin@audaque.com)
 *
 */
public class ServerResourceBundleProvider extends ResourceBundleProviderAdaptor {

	@Override
	public String[] getResourceLocations() {
		return new String[]{
			AdqStringUtils.getPath(ServerResourceBundleProvider.class, "server")
		};
	}
	
}
