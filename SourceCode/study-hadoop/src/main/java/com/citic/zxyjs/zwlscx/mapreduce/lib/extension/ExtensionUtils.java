package com.citic.zxyjs.zwlscx.mapreduce.lib.extension;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.ReflectionUtils;


public class ExtensionUtils {
    
    private static final Log LOG = LogFactory.getLog(ExtensionUtils.class);

    public static Extendable newInstance(String clazz, Configuration conf) {
	if(clazz == null){
	    return null;
	}
	Extendable extendable = null;
	try {
	    extendable = (Extendable) ReflectionUtils.newInstance(Class.forName(clazz), conf);
	    if(extendable instanceof UserExtensionBase){
		((UserExtensionBase) extendable).setParent(ReflectionUtils.newInstance(SystemExtensionBase.class, conf));
	    }
	} catch (ClassNotFoundException e) {
	    LOG.error("newInstance object[" + clazz + "] fail.");
	}
	return extendable;
    }
    
}
