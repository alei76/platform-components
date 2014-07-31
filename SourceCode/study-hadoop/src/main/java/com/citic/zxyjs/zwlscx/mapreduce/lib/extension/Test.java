package com.citic.zxyjs.zwlscx.mapreduce.lib.extension;


public class Test extends UserExtensionBase {
    
    @Override
    public void doUserExtend() {
	System.out.println(conf.toString());
    }

}
