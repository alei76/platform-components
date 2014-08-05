package com.citic.zxyjs.zwlscx.bean;

public enum RelationalOperation {
	le(1, "<="),
	lt(2, "<"),
	eq(3, "="),
	gt(4, ">"),
	ge(5, ">="),
	ne(6, "!=");
	
	private int type;
	private String desc;
	
	RelationalOperation(int type, String desc) {
		this.type = type;
		this.desc = desc;
	}
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public static RelationalOperation getDisplayType(int type){
		for(RelationalOperation ro : RelationalOperation.values()){
			if(ro.getType() == type){
				return ro;
			}
		}
		return eq;
	}
	public static RelationalOperation getDisplayType(String desc){
		for(RelationalOperation ro : RelationalOperation.values()){
			if(ro.getDesc().equals(desc)){
				return ro;
			}
		}
		return eq;
	}
}
