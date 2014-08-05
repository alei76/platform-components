package com.citic.zxyjs.zwlscx.mapreduce.lib.extension.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.citic.zxyjs.zwlscx.bean.Task;
import com.citic.zxyjs.zwlscx.mapreduce.lib.extension.ExtensionParmeters;
import com.citic.zxyjs.zwlscx.mapreduce.lib.extension.JobExtensionParmeters;
import com.citic.zxyjs.zwlscx.mapreduce.lib.extension.UserExtensionBase;

public class JobExtension extends UserExtensionBase {

    private static final Log LOG = LogFactory.getLog(JobExtension.class);

    @Override
    public void doExtend(ExtensionParmeters parms) {
	if (!(parms instanceof JobExtensionParmeters)) {
	    return;
	}
	JobExtensionParmeters jobParms = (JobExtensionParmeters) parms;
	Task task = jobParms.getTask();
	LOG.info("job运行完后调用此扩展方法，job输出为：" + task.getOutput().getPath());
    }

}
