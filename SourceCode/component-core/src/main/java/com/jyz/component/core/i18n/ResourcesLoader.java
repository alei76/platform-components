package com.jyz.component.core.i18n;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.ServiceLoader;

import org.apache.commons.lang.StringUtils;


/**
 * 
 *	@author JoyoungZhang@gmail.com
 *
 */
public class ResourcesLoader {
	
	private List<String> bundleNames = new ArrayList<String>();
	private List<CacheKey> loadedKeys = new ArrayList<CacheKey>();
	private static final Locale defaultLocale = Locale.getDefault();
	
	private static class Singleton {
		private static final ResourcesLoader INSTANCE = new ResourcesLoader();
	}
	
	private ResourcesLoader(){
		loadResourceBundles();
	}
	
	public static ResourcesLoader getInstance(){
		return Singleton.INSTANCE;
	}
	
	private void loadResourceBundles() {
		ServiceLoader<ResourceBundleProvider> providers = ServiceLoader.load(ResourceBundleProvider.class);
		if(providers == null){
			return;
		}
		for(ResourceBundleProvider provider : providers){
			loadResourceBundle(provider);
		}
	}

	private void loadResourceBundle(ResourceBundleProvider provider) {
		String[] bundleNames = provider.getResourceLocations();
		if(bundleNames == null || bundleNames.length == 0){
			return;
		}
		for(String bundleName : bundleNames){
			if(!StringUtils.isEmpty(bundleName))
				this.bundleNames.add(bundleName);
		}
	}
	
	public synchronized String getValue(String key, Locale locale){
		for (String bundleName : this.bundleNames) {
			if(loadedKeys.contains(new CacheKey(bundleName, locale))){
				continue;
			}
			ResourceBundle bundle = ResourceBundle.getBundle(bundleName);
			if(bundle != null){
				this.loadedKeys.add(new CacheKey(bundleName, locale));
			}
			return bundle.getString(key);
		}
		return null;
	}
	
	private static final class CacheKey{
		private final String bundleName;
		private final Locale locale;
		
		public CacheKey(String bundleName, Locale locale) {
            this.bundleName = bundleName;
            this.locale = locale;
        }
		
		@Override
		public String toString(){
			return "CacheKey[bundleName="+bundleName+", locale="+locale+"]";
		}
		
		@Override
		public boolean equals(Object object){
			if(!(object instanceof CacheKey)){
				return false;
			}
			CacheKey key = (CacheKey)object;
			return key.bundleName == null ? this.bundleName == null : key.bundleName.equals(this.bundleName) &&
					key.locale == null ? this.locale == null : key.locale.equals(this.locale);
		}
		
		@Override
		public int hashCode(){
			final int prime = 37;
			int result = 17;
			result = result * prime + (bundleName == null ? 0 : bundleName.hashCode());
			result = result * prime + (locale == null ? 0 : locale.hashCode());
			return result;
		}
	}
	
	
}
