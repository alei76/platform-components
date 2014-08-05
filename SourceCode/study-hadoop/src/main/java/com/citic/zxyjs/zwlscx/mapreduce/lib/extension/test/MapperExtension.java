package com.citic.zxyjs.zwlscx.mapreduce.lib.extension.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.citic.zxyjs.zwlscx.mapreduce.lib.extension.ExtensionParmeters;
import com.citic.zxyjs.zwlscx.mapreduce.lib.extension.MapperReducerExtensionParmeters;
import com.citic.zxyjs.zwlscx.mapreduce.lib.extension.UserExtensionBase;

public class MapperExtension extends UserExtensionBase {

    private static final Log LOG = LogFactory.getLog(MapperExtension.class);

    @Override
    public void doExtend(ExtensionParmeters parms) {
	if (!(parms instanceof MapperReducerExtensionParmeters)) {
	    return;
	}
	MapperReducerExtensionParmeters mrParms = (MapperReducerExtensionParmeters) parms;
	LOG.info("Mapper运行完后调用此扩展方法");
    }

}
