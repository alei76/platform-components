package com.jyz.component.core.i18n;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.ServiceLoader;

import org.apache.commons.lang.StringUtils;

import com.jyz.component.core.logging.Log;
import com.jyz.component.core.logging.LogFactory;
import com.jyz.component.core.resources.ResourceNotFoundException;
import com.jyz.component.core.resources.Resources;

/**
 * 
 *	@author JoyoungZhang@gmail.com
 *
 */
public class ResourcesLoader {
	
	private static final Log log = LogFactory.getLog(ResourcesLoader.class);
    
    private static Locale defaultLocale = Locale.getDefault();
	
	private List<String> bundleNames = new ArrayList<String>();
	
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
				log.debug("Loaded resourceBundle[" + bundleName + "].");
		}
	}
	
	public synchronized String getString(String key){
	    return getString(key, defaultLocale);
	}
	
	public synchronized String getString(String key, Locale locale, Object...arguments){
		for (String bundleName : this.bundleNames) {
			ResourceBundle resource = null;
			try {
			    resource = Resources.getResourceBundle(bundleName, locale);
			} catch (ResourceNotFoundException e) {
			    //ignore
			}
			if(resource == null){
			    return null;
			}
			if(resource.containsKey(key)){
				log.debug("Find resourceBundle[" + bundleName + "] contains key[" + key + "].");
				if(arguments == null){
					return resource.getString(key);
				}
				return MessageFormat.format(resource.getString(key), arguments);
			}
		}
		return null;
	}
	
	/**
	 * 从指定bundle文件里获取属性值
	 * @param bundle
	 * @param key
	 * @param arguments
	 * @return
	 */
	public synchronized String getString(String bundle, String key){
	    return getString(bundle, key, defaultLocale);
	}
	
	/**
	 * 
	 * @param bundle
	 * @param key
	 * @param locale
	 * @param arguments
	 * @return
	 */
	public synchronized String getString(String bundleName, String key, Locale locale, Object...arguments){
	    	ResourceBundle resource = null;
		try {
		    resource = Resources.getResourceBundle(bundleName, locale);
		} catch (ResourceNotFoundException e) {
		    //ignore
		}
		if(resource == null){
		    return null;
		}
		if(resource.containsKey(key)){
			log.debug("Find resourceBundle[" + bundleName + "] contains key[" + key + "].");
			if(arguments == null){
				return resource.getString(key);
			}
			return MessageFormat.format(resource.getString(key), arguments);
		}
		return null;
	}
	
}
