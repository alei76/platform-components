package com.citic.zxyjs.zwlscx.mapreduce.join;

import org.apache.hadoop.io.Text;

public class DataJoinReducer extends DataJoinReducerBase {
    protected TaggedMapOutput combine(Text[] tags, TaggedMapOutput[] values) {
	String joinedStr = "";
	TaggedWritable tw = (TaggedWritable) values[0];
	String line = tw.getData().toString();
	String[] tokens = line.split(",", 2);
	joinedStr += tokens[1];

	if (tags.length > 1) {
	    for (int i = 1; i < tags.length; i++) {
		joinedStr += ",";
		tw = (TaggedWritable) values[i];
		line = tw.getData().toString();
		tokens = line.split(",", 2);
		joinedStr += tokens[1];
	    }
	}
	TaggedWritable retv = new TaggedWritable(new Text(joinedStr));
	retv.setTag(tags[0]);
	return retv;
    }
}
