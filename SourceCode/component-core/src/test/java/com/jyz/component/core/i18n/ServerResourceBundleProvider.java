package com.jyz.component.core.i18n;

import com.audaque.lib.core.utils.AdqStringUtils;

public class ServerResourceBundleProvider implements ResourceBundleProvider{


	@Override
	public String[] getResourceLocations() {
		return new String[]{AdqStringUtils.getPath(ServerResourceBundleProvider.class, "server")};
	}

}
