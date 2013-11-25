package com.jyz.component.core.i18n;

import com.jyz.component.core.utils.JyzStringUtils;

public class GuiResourceBundleProvider implements ResourceBundleProvider{

	@Override
	public String[] getResourceLocations() {
		return new String[]{JyzStringUtils.getPath(GuiResourceBundleProvider.class, "gui")};
	}

}
