package com.citic.zxyjs.zwlscx.bean;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

public class Table extends Source implements Writable{

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

    @Override
    public void readFields(DataInput in) throws IOException {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void write(DataOutput out) throws IOException {
	// TODO Auto-generated method stub
	
    }
    
}
