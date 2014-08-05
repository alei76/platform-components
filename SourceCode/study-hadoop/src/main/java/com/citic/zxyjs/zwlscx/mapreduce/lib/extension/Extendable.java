package com.citic.zxyjs.zwlscx.mapreduce.lib.extension;

import org.apache.hadoop.conf.Configurable;

public interface Extendable extends Configurable {

    void doExtend(ExtensionParmeters parms);

}
