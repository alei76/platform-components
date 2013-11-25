package com.jyz.component.io;

import com.jyz.component.core.i18n.ResourceBundleProvider;
import com.jyz.component.core.utils.JyzStringUtils;

/**
 *	@author JoyoungZhang@gmail.com
 *
 */
public class IOResourceBundleProvider implements ResourceBundleProvider{

	@Override
	public String[] getResourceLocations() {
		return new String[]{JyzStringUtils.getPath(IOResourceBundleProvider.class, "io")};
	}

}
