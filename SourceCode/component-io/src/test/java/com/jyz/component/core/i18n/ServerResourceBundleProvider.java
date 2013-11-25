package com.jyz.component.core.i18n;

import com.jyz.component.core.utils.JyzStringUtils;

public class ServerResourceBundleProvider implements ResourceBundleProvider{
	
	@Override
	public String[] getResourceLocations() {
		return new String[]{JyzStringUtils.getPath(ServerResourceBundleProvider.class, "server")};
	}
	
}
