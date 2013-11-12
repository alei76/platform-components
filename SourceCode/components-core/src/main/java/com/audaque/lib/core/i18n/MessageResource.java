package com.audaque.lib.core.i18n;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.ServiceLoader;
import java.util.Set;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.audaque.lib.core.utils.AdqStringUtils;

/**
 * menager all the message resources.
 * 
 * @author lindeshu <deshu.lin@audaque.com>
 */
public class MessageResource {

    private final static Logger logger = Logger.getLogger(MessageResource.class);
    private final static ThreadLocal<Locale> customeLocale = new ThreadLocal<Locale>();
    // 默认语言版本，可以为空（将使用Locale.getDefault()）。
    private Locale defaultLocale = null;
    //
    private List<LoadedBundleCachKey> loadedBundleKeys = new ArrayList<LoadedBundleCachKey>();
    private List<String> bundleNames = new ArrayList<String>();
    private Set<ResourceBundle> bundles = new HashSet<ResourceBundle>();

    public static MessageResource getInstance() {
        return Singleton.INSTANCE;
    }

    private static class Singleton {

        private final static MessageResource INSTANCE = new MessageResource();
    }

    private static class LoadedBundleCachKey {

        private final String bundleName;
        private final Locale locale;

        public LoadedBundleCachKey(String bundleName, Locale locale) {
            this.bundleName = bundleName;
            this.locale = locale;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            LoadedBundleCachKey other = (LoadedBundleCachKey) obj;
            if (bundleName == null) {
                if (other.bundleName != null) {
                    return false;
                }
            } else if (!bundleName.equals(other.bundleName)) {
                return false;
            }
            if (locale == null) {
                if (other.locale != null) {
                    return false;
                }
            } else if (!locale.equals(other.locale)) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((bundleName == null) ? 0 : bundleName.hashCode());
            result = prime * result + ((locale == null) ? 0 : locale.hashCode());
            return result;
        }
    }

    /**
     * 私有构造
     */
    private MessageResource() {
        loadResourceBundles();
    }

    /**
     * 设置默认语言
     * 
     * @param defaultLocale
     *            默认语言，可为空。
     */
    public synchronized void setDefaultLocale(Locale defaultLocale) {
        this.defaultLocale = defaultLocale;
    }

    /**
     * 获取目标语言。
     * 
     * @param source
     *            目标语言
     */
    private Locale getLocale(Locale source) {
        if (source != null) {
            return source;
        }
        if (this.defaultLocale != null) {
            return defaultLocale;
        }
        return Locale.getDefault();
    }

    public Locale getDefaultLocale() {
        if(customeLocale.get() != null) {
            return customeLocale.get();
        }
        if (defaultLocale != null) {
            return defaultLocale;
        }
        return Locale.getDefault();
    }

    /**
     * 根据键及默认语言来获取消息
     * 
     * @param key
     *            消息键
     * @return 如果源文件没有找到相应的键，则返回空消息。
     */
    public synchronized String getMessage(String key) {
        return getMessage(key, defaultLocale);
    }

    /**
     * 加载ResourceBundle
     */
    private void loadResourceBundles() {
        ServiceLoader<ResourceBundleProvider> providers = ServiceLoader.load(ResourceBundleProvider.class);
        for (ResourceBundleProvider provider : providers) {
            loadResourceBundles(provider);
        }
    }

    /**
     * 加载ResourceBundle
     * 
     * @param provider
     *            ResourceBundle提供者
     */
    private void loadResourceBundles(ResourceBundleProvider provider) {
        ResourceBundle[] resourceBundles = provider.getResourceBundles();
        if (resourceBundles != null) {
            for (ResourceBundle bundle : resourceBundles) {
                if (bundle != null) {
                    this.bundles.add(bundle);
                }
            }
        }
        String[] bundleNames = provider.getResourceLocations();
        if (bundleNames != null) {
            for (String bundleName : bundleNames) {
                if (StringUtils.isEmpty(bundleName)) {
                    continue;
                }
                this.bundleNames.add(bundleName);
            }
        }
    }

    private ResourceBundle getBundle(String bundleName, ClassLoader classLoader, Locale targetLocale, String key) {
        ResourceBundle bundle = null;
        LoadedBundleCachKey cachKey = null;
        try {
            if (targetLocale == null) {
                bundle = ResourceBundle.getBundle(bundleName);
            } else {
                bundle = ResourceBundle.getBundle(bundleName, targetLocale, classLoader);
            }
            cachKey = new LoadedBundleCachKey(bundleName, targetLocale);
        } catch (MissingResourceException e) {
            logger.debug("no i18n resource for locale[" + targetLocale + "].", e);
            return null;
        }
        if (bundle != null) {
            this.bundles.add(bundle);
            // 当加载了新的bundle以后，将该资源信息缓存起来
            this.loadedBundleKeys.add(cachKey);
        }
        return bundle;
    }

    /**
     * 根据消息键、语言、消息参数来获取消息
     * 
     * @param key
     *            消息键
     * @param locale
     *            目标语言
     * @param params
     *            消息参数
     * @return 如果源文件没有找到相应的键，则返回空消息。
     */
    public synchronized String getMessage(String key, Locale locale, Object... params) {
        Locale defaultLocale = getDefaultLocale();
        Locale targetLocale = getLocale(locale);
        for (String bundleName : this.bundleNames) {
            // 资源是否已经加载
            if (this.loadedBundleKeys.contains(new LoadedBundleCachKey(bundleName, targetLocale))) {
                continue;
            }
            for (ClassLoader classLoader : getClassLoaders()) {
                ResourceBundle bundle = getBundle(bundleName, classLoader, targetLocale, key);
                if (bundle == null) {
                    bundle = getBundle(bundleName, classLoader, defaultLocale, key);
                }
                if (bundle == null) {
                    bundle = getBundle(bundleName, classLoader, null, key);
                }
                if (bundle != null && bundle.containsKey(key)) {
                    return getMessage(bundle, key, params);
                }
            }
        }
        ResourceBundle targetBundle = getTargetBundle(key, targetLocale);
        if(targetBundle == null) {
            if (!ObjectUtils.equals(targetLocale, defaultLocale)) {
                targetBundle = getTargetBundle(key, defaultLocale);
            }
        }
        if(targetBundle == null) {
            for (ResourceBundle cachedBundle : this.bundles) {
                if (cachedBundle.containsKey(key)) {
                    targetBundle = cachedBundle;
                }
            }
        }
        if(targetBundle != null) {
            return getMessage(targetBundle, key, params);
        }
        logger.debug("no i18n resource for [" + key + "]--" + targetLocale + ".");
        return key;
    }
    
    private ResourceBundle getTargetBundle(String key, Locale targetLocale) {
        for (ResourceBundle cachedBundle : this.bundles) {
            if (cachedBundle.containsKey(key) && ObjectUtils.equals(targetLocale, cachedBundle.getLocale())) {
                return cachedBundle;
            }
        }
        return null;
    }
    
    /**
     * 在指定的国际化文件，获取消息键、语言、消息参数来获取消息
     * 
     * @param bundleName
     *            指定的国际化资源，如<code>com/audaque/lib/i18n/message</code>
     * @param key
     *            消息键
     * @param params
     *            消息参数
     * @return 如果源文件没有找到相应的键，则返回空消息。
     */
    public synchronized String getMessage(String bundleName, String key, Object... params) {
        return getMessage(bundleName, key, null, params);
    }

    /**
     * 在指定的国际化文件，获取消息键、语言、消息参数来获取消息
     * 
     * @param bundleName
     *            指定的国际化资源，如<code>com/audaque/lib/i18n/message</code>
     * @param key
     *            消息键
     * @param locale
     *            the locale
     * @param params
     *            消息参数
     * @return 如果源文件没有找到相应的键，则返回空消息。
     */
    public synchronized String getMessage(String bundleName, String key, Locale locale, Object... params) {
        locale = getLocale(locale);
        for (ClassLoader classLoader : getClassLoaders()) {
            ResourceBundle bundle = getBundle(bundleName, classLoader, locale, key);
            if(bundle == null) {
                continue;
            }
            if (bundle.containsKey(key)) {
                return getMessage(bundle, key, params);
            }
        }
        return null;
    }

    /**
     * 查找clazz对应包下的<code>bundle</code>文件
     * 
     * @param key
     *            消息键
     * @param params
     *            消息参数
     * @return 如果源文件没有找到相应的键，则返回空消息。
     */
    public synchronized String getMessage(Class<?> clazz, String key, Object... params) {
        return getMessage(AdqStringUtils.getPath(clazz, "Bundle"), key, params);
    }

    /**
     * 查找clazz对应包下的<code>bundle</code>文件
     * 
     * @param key
     *            消息键
     * @param locale
     *            the locale
     * @param params
     *            消息参数
     * @return 如果源文件没有找到相应的键，则返回空消息。
     */
    public synchronized String getMessage(Class<?> clazz, String key, Locale locale, Object... params) {
        return getMessage(AdqStringUtils.getPath(clazz, "Bundle"), locale, key, params);
    }

    /**
     * 从bundle中获取消息并格式化
     * 
     * @param bundle
     *            ResourceBundle
     * @param key
     *            Key
     * @param params
     *            参数集
     * @return 格式化的消息
     */
    private String getMessage(ResourceBundle bundle, String key, Object... params) {
        if (params == null || params.length == 0) {
            return bundle.getString(key);
        }
        MessageFormat format = new MessageFormat(bundle.getString(key), bundle.getLocale());
        return format.format(params);
    }

    /**
     * 获取类加载器，优先使用ThreadLocal.currentThread.contextClassLoader
     */
    private ClassLoader[] getClassLoaders() {
        ClassLoader[] classLoaders = new ClassLoader[2];
        classLoaders[0] = Thread.currentThread().getContextClassLoader();
        classLoaders[1] = MessageResource.class.getClassLoader();
        // Clean up possible null values. Note that #getClassLoader may return a
        // null value.
        List<ClassLoader> loaders = new ArrayList<ClassLoader>();
        for (ClassLoader classLoader : classLoaders) {
            if (classLoader != null && !loaders.contains(classLoader)) {
                loaders.add(classLoader);
            }
        }
        return loaders.toArray(new ClassLoader[loaders.size()]);
    }
    
    public static void customeLocale(Locale locale) {
        customeLocale.set(locale);
    }
    
    public static void unbindLocale() {
        customeLocale.remove();
    }
    
}
