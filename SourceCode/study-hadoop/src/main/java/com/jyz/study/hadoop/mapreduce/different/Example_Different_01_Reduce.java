package com.jyz.study.hadoop.mapreduce.different;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import com.jyz.study.hadoop.common.TextPair;

/**
 * Reduce函数
 * 
 * @author JoyoungZhang@gmail.com
 */
public class Example_Different_01_Reduce extends Reducer<TextPair, Text, Text, Text> {
    private static Log LOG = LogFactory.getLog(Example_Different_01_Reduce.class);

    protected void reduce(TextPair key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
	Text rowkey = new Text(key.getFirst().toString().substring(0, key.getFirst().toString().indexOf("99991231") - 1));
	String pre = values.iterator().next().toString();
	String second = key.getSecond().toString();

	LOG.info(second);
	if (second.equals("0")) {
	    if (values.iterator().hasNext()) {
		String after = values.iterator().next().toString();
		if (!pre.equals(after)) {
		    context.write(rowkey, new Text(after));
		}
	    } else {
		context.write(rowkey, new Text(pre + "\t" + "deleted"));
	    }
	} else if (second.equals("1")) {
	    if (values.iterator().hasNext()) {
		String after = values.iterator().next().toString();
		if (!pre.equals(after)) {
		    context.write(rowkey, new Text(pre));
		}
	    } else {
		context.write(rowkey, new Text(pre));
	    }
	}
    }
}