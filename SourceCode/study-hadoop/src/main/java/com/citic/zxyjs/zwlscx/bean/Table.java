package com.citic.zxyjs.zwlscx.bean;

public class Table extends Source {

    private static final long serialVersionUID = 1L;

    private boolean hasZipperTable;
    private String zipperTableName;

    public boolean isHasZipperTable() {
	return hasZipperTable;
    }

    public void setHasZipperTable(boolean hasZipperTable) {
	this.hasZipperTable = hasZipperTable;
    }

    public String getZipperTableName() {
	return zipperTableName;
    }

    public void setZipperTableName(String zipperTableName) {
	this.zipperTableName = zipperTableName;
    }

}
