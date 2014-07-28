package com.jyz.study.hadoop.mapreduce.datajoin;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.ReflectionUtils;

import com.jyz.study.hadoop.common.ConfigurationUtils;
import com.jyz.study.hadoop.common.Utils;

public class DataJoinUseNewApi {
    public static class MapClass extends DataJoinMapperBase<LongWritable, Text, Text, TaggedMapOutput> {
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

	protected TaggedMapOutput generateTaggedMapOutput(Text value, Context context) {
	    TaggedWritable retv = new TaggedWritable(value);
	    retv.setTag(this.inputTag);
	    return retv;
	}
    }

    public static class ReduceClass extends DataJoinReducerBase {
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

    public static class TaggedWritable extends TaggedMapOutput {
	private Text data;

	public TaggedWritable() {
	    this.tag = new Text();
	}

	public TaggedWritable(Text data) {
	    this.tag = new Text("");
	    this.data = data;
	}

	public Text getData() {
	    return data;
	}

	public void setData(Text data) {
	    this.data = data;
	}

	public void write(DataOutput out) throws IOException {
	    this.tag.write(out);
	    out.writeUTF(this.data.getClass().getName());
	    this.data.write(out);
	}

	public void readFields(DataInput in) throws IOException {
	    this.tag.readFields(in);
	    String dataClz = in.readUTF();
	    if (this.data == null || !this.data.getClass().getName().equals(dataClz)) {
		try {
		    this.data = (Text) ReflectionUtils.newInstance(Class.forName(dataClz), null);
		} catch (ClassNotFoundException e) {
		    e.printStackTrace();
		}
	    }
	    this.data.readFields(in);
	}
    }

    public static void main(String[] args) throws Exception {
	Configuration conf = ConfigurationUtils.getHadoopConfiguration();
	Job job = new Job(conf, "DataJoinUseNewApi");
	job.setJarByClass(DataJoinUseNewApi.class);
	job.setMapperClass(MapClass.class);
	job.setReducerClass(ReduceClass.class);
	job.setMapOutputValueClass(TaggedWritable.class);
	job.setOutputKeyClass(Text.class);
	job.setOutputValueClass(Text.class);
	job.setNumReduceTasks(1);
	FileInputFormat.addInputPath(job, new Path("hdfs://200master:9000/user/root/input2"));
	Utils.deleteIfExists(conf, "hdfs://200master:9000/user/root/output20");
	FileOutputFormat.setOutputPath(job, new Path("hdfs://200master:9000/user/root/output20"));
	System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

}