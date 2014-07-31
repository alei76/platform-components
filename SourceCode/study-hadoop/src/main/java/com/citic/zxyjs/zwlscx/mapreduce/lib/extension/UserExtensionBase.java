package com.citic.zxyjs.zwlscx.mapreduce.lib.extension;

public abstract class UserExtensionBase  extends ExtensionBase {
    
    private Extendable parent;
    
    public Extendable getParent() {
        return parent;
    }

    public void setParent(Extendable parent) {
        this.parent = parent;
    }

    @Override
    public void doExtend() {
	parent.doExtend();
	doUserExtend();
    }

    public abstract void doUserExtend();
    
}
