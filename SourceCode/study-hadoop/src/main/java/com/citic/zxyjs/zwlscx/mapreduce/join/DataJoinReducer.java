package com.citic.zxyjs.zwlscx.mapreduce.join;

import java.io.IOException;

import org.apache.hadoop.io.Text;

import com.citic.zxyjs.zwlscx.xml.Separator;

public class DataJoinReducer extends DataJoinReducerBase {

    protected void setup(Context context) throws IOException, InterruptedException {
	super.setup(context);
    }

    protected TaggedMapOutput combine(Text[] tags, TaggedMapOutput[] values) {
	StringBuffer joinedStr = new StringBuffer();
	for (TaggedMapOutput taggedMapOutput : values) {
	    joinedStr.append(taggedMapOutput.getData()).append(Separator.SEP_COMMA);
	}
	if (values.length > 0) {
	    joinedStr.deleteCharAt(joinedStr.length() - 1);
	}
	TaggedWritable retv = new TaggedWritable(new Text(joinedStr.toString()));
	retv.setTag(tags[0]);
	return retv;
    }
}
