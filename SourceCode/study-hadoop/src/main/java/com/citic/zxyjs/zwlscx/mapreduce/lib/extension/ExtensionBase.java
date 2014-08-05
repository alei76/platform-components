package com.citic.zxyjs.zwlscx.mapreduce.lib.extension;

import org.apache.hadoop.conf.Configuration;

public abstract class ExtensionBase implements Extendable {

    protected Configuration conf;

    @Override
    public Configuration getConf() {
	return conf;
    }

    @Override
    public void setConf(Configuration conf) {
	this.conf = conf;
    }
}
