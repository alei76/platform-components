/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.citic.zxyjs.zwlscx.mapreduce.join;

import java.io.IOException;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * This abstract class serves as the base class for the reducer class of a data
 * join job. The reduce function will first group the values according to their
 * input tags, and then compute the cross product of over the groups. For each
 * tuple in the cross product, it calls the following method, which is expected
 * to be implemented in a subclass. protected abstract TaggedMapOutput
 * combine(Object[] tags, Object[] values); The above method is expected to
 * produce one output value from an array of records of different sources. The
 * user code can also perform filtering here. It can return null if it decides
 * to the records do not meet certain conditions.
 */
public abstract class DataJoinReducerBase extends Reducer<Text, TaggedMapOutput, Text, Text> {

    private static final Log LOG = LogFactory.getLog(DataJoinReducerBase.class);

    protected Reporter reporter = null;

    private long maxNumOfValuesPerGroup = 100;

    protected long largestNumOfValues = 0;

    protected long numOfValues = 0;

    protected void setup(Context context) throws IOException, InterruptedException {
	this.maxNumOfValuesPerGroup = context.getConfiguration().getLong("datajoin.maxNumOfValuesPerGroup", 100);
    }

    /**
     * The subclass can provide a different implementation on ResetableIterator.
     * This is necessary if the number of values in a reduce call is very high.
     * The default provided here uses ArrayListBackedIterator
     * 
     * @return an Object of ResetableIterator.
     */
    protected <T> ResetableIterator<T> createResetableIterator() {
	return new ArrayListBackedIterator<T>();
    }

    /**
     * This is the function that re-groups values for a key into sub-groups
     * based on a secondary key (input tag).
     * 
     * @param values
     * @return
     */
    private SortedMap<Text, ResetableIterator<TaggedMapOutput>> regroup(Text key, Iterator<TaggedMapOutput> values,
	    Context context) throws IOException {
	this.numOfValues = 0;
	SortedMap<Text, ResetableIterator<TaggedMapOutput>> retv = new TreeMap<Text, ResetableIterator<TaggedMapOutput>>();
	TaggedMapOutput aRecord = null;
	while (values.hasNext()) {
	    this.numOfValues += 1;
	    if (this.numOfValues % 100 == 0) {
		reporter.setStatus("key: " + key.toString() + " numOfValues: " + this.numOfValues);
	    }
	    if (this.numOfValues > this.maxNumOfValuesPerGroup) {
		continue;
	    }
	    aRecord = values.next().clone(context.getConfiguration());
	    Text tag = aRecord.getTag();
	    ResetableIterator<TaggedMapOutput> data = retv.get(tag);
	    if (data == null) {
		data = createResetableIterator();
		retv.put(tag, data);
	    }
	    data.add(aRecord);
	}
	if (this.numOfValues > this.largestNumOfValues) {
	    this.largestNumOfValues = numOfValues;
	    LOG.info("key: " + key.toString() + " this.largestNumOfValues: " + this.largestNumOfValues);
	}
	return retv;
    }

    @SuppressWarnings("unchecked")
    protected void reduce(Text key, Iterable<TaggedMapOutput> values, Context context) throws IOException, InterruptedException {
	SortedMap<Text, ResetableIterator<TaggedMapOutput>> groups = regroup(key, values.iterator(), context);
	Text[] tags = groups.keySet().toArray(new Text[0]);
	ResetableIterator<TaggedMapOutput>[] groupValues = new ResetableIterator[tags.length];
	for (int i = 0; i < tags.length; i++) {
	    groupValues[i] = groups.get(tags[i]);
	}
	joinAndCollect(tags, groupValues, key, context);
	for (int i = 0; i < tags.length; i++) {
	    groupValues[i].close();
	}
    }

    /**
     * join the list of the value lists, and collect the results.
     * 
     * @param tags
     *            a list of input tags
     * @param values
     *            a list of value lists, each corresponding to one input source
     * @param key
     * @param output
     * @throws IOException
     * @throws InterruptedException
     */
    private void joinAndCollect(Text[] tags, ResetableIterator<TaggedMapOutput>[] values, Text key, Context context)
	    throws IOException, InterruptedException {
	if (values.length < 1) {
	    return;
	}
	TaggedMapOutput[] partialList = new TaggedMapOutput[values.length];
	joinAndCollect(tags, values, 0, partialList, key, context);
    }

    /**
     * Perform the actual join recursively.
     * 
     * @param tags
     *            a list of input tags
     * @param values
     *            a list of value lists, each corresponding to one input source
     * @param pos
     *            indicating the next value list to be joined
     * @param partialList
     *            a list of values, each from one value list considered so far.
     * @param key
     * @param output
     * @throws IOException
     * @throws InterruptedException
     */
    private void joinAndCollect(Text[] tags, ResetableIterator<TaggedMapOutput>[] values, int pos, TaggedMapOutput[] partialList,
	    Text key, Context context) throws IOException, InterruptedException {

	if (values.length == pos) {
	    // get a value from each source. Combine them
	    TaggedMapOutput combined = combine(tags, partialList);
	    context.write(key, combined.getData());
	    return;
	}
	ResetableIterator<TaggedMapOutput> nextValues = values[pos];
	nextValues.reset();
	while (nextValues.hasNext()) {
	    TaggedMapOutput v = (TaggedMapOutput) nextValues.next();
	    partialList[pos] = v;
	    joinAndCollect(tags, values, pos + 1, partialList, key, context);
	}
    }

    public static Text SOURCE_TAGS_FIELD = new Text("SOURCE_TAGS");

    public static Text NUM_OF_VALUES_FIELD = new Text("NUM_OF_VALUES");

    /**
     * @param tags
     *            a list of source tags
     * @param values
     *            a value per source
     * @return combined value derived from values of the sources
     */
    protected abstract TaggedMapOutput combine(Text[] tags, TaggedMapOutput[] values);

}
