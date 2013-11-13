package com.jyz.component.core.i18n;


public class GuiResourceBundleProvider implements ResourceBundleProvider{


	@Override
	public String[] getResourceLocations() {
		return new String[]{JyzStringUtils.getPath(GuiResourceBundleProvider.class, "gui")};
	}

}
