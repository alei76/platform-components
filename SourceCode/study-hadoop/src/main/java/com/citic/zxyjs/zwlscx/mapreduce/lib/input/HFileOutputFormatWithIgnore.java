/**
 *
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
package com.citic.zxyjs.zwlscx.mapreduce.lib.input;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.mapreduce.KeyValueSortReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.mapreduce.Job;

/**
 * Writes HFiles. Passed KeyValues must arrive in order. Writes current time as
 * the sequence id for the file. Sets the major compacted attribute on created
 * hfiles. Calling write(null,null) will forceably roll all HFiles being
 * written.
 * <p>
 * Using this class as part of a MapReduce job is best done using
 * {@link #configureIncrementalLoad(Job, HTable)}.
 * 
 * @see KeyValueSortReducer
 */
@InterfaceAudience.Public
@InterfaceStability.Stable
public class HFileOutputFormatWithIgnore extends HFileOutputFormatBase {
    private static Log LOG = LogFactory.getLog(HFileOutputFormatWithIgnore.class);

    @Override
    public boolean ignore(KeyValue kv) {
	boolean ignore = Bytes.toString(kv.getValue()).indexOf("Del") >= 0;
	return ignore;
    }
}
