package com.citic.zxyjs.zwlscx.mapreduce.lib.extension;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SystemExtension extends SystemExtensionBase {

    private static final Log LOG = LogFactory.getLog(SystemExtension.class);

    @Override
    public void doExtend(ExtensionParmeters parms) {
	if (!(parms instanceof SystemExtensionParmeters)) {
	    return;
	}
	LOG.info("调用系统扩展类");
    }

}
