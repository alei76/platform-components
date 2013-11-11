package com.jyz.component.core.i18n;

import com.audaque.lib.core.utils.AdqStringUtils;

public class GuiResourceBundleProvider implements ResourceBundleProvider{


	@Override
	public String[] getResourceLocations() {
		return new String[]{AdqStringUtils.getPath(GuiResourceBundleProvider.class, "gui")};
	}

}
