package com.citic.zxyjs.zwlscx.bean.future;

import java.util.ArrayList;
import java.util.List;

import com.citic.zxyjs.zwlscx.bean.File;
import com.citic.zxyjs.zwlscx.bean.Source;

public class MetaDataHelper {

    public static List<Field> getFieldByFileName(String fileName) {
	List<Field> fields = new ArrayList<Field>();
	if(fileName.equals("HDP_TX_O_F_DSDSA")){
	    fields.add(new Field("DSACNO"));
	    fields.add(new Field("DSNFFG"));
	    fields.add(new Field("DSTRDT"));
	    fields.add(new Field("DSTRTM"));
	    fields.add(new Field("DSTRCD"));
	    fields.add(new Field("DSTRNO"));
	}
	return fields;
    }

    public static List<Field> getFieldByTableName(String tableName) {
	List<Field> fields = new ArrayList<Field>();
	if(tableName.equals("HDP_CM_O_F_PMTPA")){
	    fields.add(new Field("TPTRCD"));
	    fields.add(new Field("TPTACD"));
	    fields.add(new Field("TPPMTX"));
	}
	return fields;
    }

    public static List<Field> getFieldByName(Source source) {
	List<Field> fields = new ArrayList<Field>();
	List<Source> sources =  source.getParentSource();
	for(Source s : sources){
	    if(s instanceof File){
		fields.addAll(getFieldByFileName(s.getName()));
	    }else{
		fields.addAll(getFieldByTableName(s.getName()));
	    }
	}
	return fields;
    }

    public static byte[] generateRowKey() {
	return null;
    }
}
