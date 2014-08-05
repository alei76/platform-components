package com.citic.zxyjs.zwlscx.mapreduce.lib.extension.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.Text;

import com.citic.zxyjs.zwlscx.mapreduce.lib.extension.ExtensionParmeters;
import com.citic.zxyjs.zwlscx.mapreduce.lib.extension.MapperReducerExtensionParmeters;
import com.citic.zxyjs.zwlscx.mapreduce.lib.extension.UserExtensionBase;

public class ReducerExtension extends UserExtensionBase {

    private static final Log LOG = LogFactory.getLog(ReducerExtension.class);

    @Override
    public void doExtend(ExtensionParmeters parms) {
	if (!(parms instanceof MapperReducerExtensionParmeters)) {
	    return;
	}
	MapperReducerExtensionParmeters mrParms = (MapperReducerExtensionParmeters) parms;
	Text oldValue = (Text) mrParms.getValue();
	Text newValue = new Text(oldValue + "####################test ReduceExtension>");
	mrParms.setValue(newValue);
	LOG.info("Reducer运行完后调用此扩展方法，将：" + oldValue + " 修改为" + newValue);
    }

}
