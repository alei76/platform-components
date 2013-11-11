package com.jyz.component.core.i18n;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.ServiceLoader;


/**
 * 
 *	@author JoyoungZhang@gmail.com
 *
 */
public class ResourcesLoader {
	
	private List<ResourceBundle> bundles = new ArrayList<ResourceBundle>();
	
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
		String[] resourceBundleLocations = provider.getResourceLocations();
		if(resourceBundleLocations == null || resourceBundleLocations.length == 0){
			return;
		}
		for(String location : resourceBundleLocations){
			ResourceBundle bundle = ResourceBundle.getBundle(location);
			bundles.add(bundle);
		}
	}
	
	public synchronized String getValue(String key){
		for(ResourceBundle bundle : bundles){
			if (bundle.containsKey(key)) {
                return bundle.getString(key);
            }
		}
		return null;
	}
	
	

	
	
	
	
}
