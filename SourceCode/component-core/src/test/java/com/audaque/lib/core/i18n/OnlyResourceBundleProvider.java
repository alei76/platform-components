package com.audaque.lib.core.i18n;

import java.util.ResourceBundle;

import com.audaque.lib.core.i18n.ResourceBundleProviderAdaptor;
import com.audaque.lib.core.utils.AdqStringUtils;

/**
 * @author deshu.lin (deshu.lin@audaque.com)
 *
 */
public class OnlyResourceBundleProvider extends ResourceBundleProviderAdaptor {

	@Override
	public ResourceBundle[] getResourceBundles() {
		return new ResourceBundle[]{
			ResourceBundle.getBundle(AdqStringUtils.getPath(OnlyResourceBundleProvider.class, "only"))
		};
	}
	
}
