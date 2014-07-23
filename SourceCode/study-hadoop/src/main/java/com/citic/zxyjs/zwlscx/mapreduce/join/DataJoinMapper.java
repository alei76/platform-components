package com.citic.zxyjs.zwlscx.mapreduce.join;

import java.io.IOException;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.DefaultStringifier;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

import com.citic.zxyjs.zwlscx.bean.Task;
import com.jyz.study.hadoop.mapreduce.datajoin.DataJoinMapperBase;
import com.jyz.study.hadoop.mapreduce.datajoin.TaggedMapOutput;
import com.jyz.study.hadoop.mapreduce.datajoin.DataJoinUseNewApi.TaggedWritable;

/**
 * Join时不同类型的mapper
 * @author JoyoungZhang@gmail.com
 *
 */
public class DataJoinMapper {
    
    /**
     * 数据源为Hdfs文件的mapper
     * @author JoyoungZhang@gmail.com
     *
     */
    public static class DataJoinTextInputFormatMapper extends DataJoinMapperBase<LongWritable, Text, Text, TaggedMapOutput> {
	protected Text generateInputTag(String inputFile) {
	    String datasource = inputFile;
	    return new Text(datasource);
	}

	protected Text generateGroupKey(TaggedMapOutput aRecord, Context context) throws IOException {
	    Task task = DefaultStringifier.load(context.getConfiguration(), "task", Task.class);
	    boolean init = DefaultStringifier.load(context.getConfiguration(), "init", Boolean.class);
	    
	    String line = aRecord.getData().toString();
	    String[] tokens = line.split(",");
	    String groupKey = tokens[0];
	    return new Text(groupKey);
	}

	protected TaggedMapOutput generateTaggedMapOutput(Text value, Context context) throws IOException {
	    Task task = DefaultStringifier.load(context.getConfiguration(), "task", Task.class);
	    boolean init = DefaultStringifier.load(context.getConfiguration(), "init", Boolean.class);
	    
	    TaggedWritable retv = new TaggedWritable(value);
	    retv.setTag(this.inputTag);
	    return retv;
	}
    }
    
    /**
     * 数据源为HBase的mapper
     * @author JoyoungZhang@gmail.com
     *
     */
    public static class DataJoinTableInputFormatMapper extends DataJoinMapperBase<ImmutableBytesWritable, Result, Text, TaggedMapOutput> {
	protected Text generateInputTag(String inputFile) {
	    String datasource = inputFile;
	    return new Text(datasource);
	}
	
	protected Text generateGroupKey(TaggedMapOutput aRecord, Context context) {
	    String line = aRecord.getData().toString();
	    String[] tokens = line.split(",");
	    String groupKey = tokens[0];
	    return new Text(groupKey);
	}
	
	protected TaggedMapOutput generateTaggedMapOutput(Result value, Context context) {
	    TaggedWritable retv = new TaggedWritable(new Text(Bytes.toStringBinary(value.list().get(0).getValue())));
	    retv.setTag(this.inputTag);
	    return retv;
	}

    }
}
