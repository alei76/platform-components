package com.citic.zxyjs.zwlscx.mapreduce.lib.extension;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.ReflectionUtils;

public class ExtensionUtils {

    private static final Log LOG = LogFactory.getLog(ExtensionUtils.class);

    public static Extendable newInstance(String clazz, Configuration conf) {
	if (StringUtils.isBlank(clazz)) {
	    return null;
	}
	Extendable extendable = null;
	try {
	    extendable = (Extendable) ReflectionUtils.newInstance(Class.forName(clazz), conf);
	} catch (ClassNotFoundException e) {
	    LOG.error("newInstance object[" + clazz + "] fail.");
	}
	return extendable;
    }

    public static Extendable getSystenExtension(Configuration conf) {
	return (Extendable) ReflectionUtils.newInstance(SystemExtension.class, conf);
    }

}
