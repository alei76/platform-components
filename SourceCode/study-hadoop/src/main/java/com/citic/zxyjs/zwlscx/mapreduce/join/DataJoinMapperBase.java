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

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

/**
 * This abstract class serves as the base class for the mapper class of a data
 * join job. This class expects its subclasses to implement methods for the
 * following functionalities: 1. Compute the source tag of input values 2.
 * Compute the map output value object 3. Compute the map output key object The
 * source tag will be used by the reducer to determine from which source (which
 * table in SQL terminology) a value comes. Computing the map output value
 * object amounts to performing projecting/filtering work in a SQL statement
 * (through the select/where clauses). Computing the map output key amounts to
 * choosing the join key. This class provides the appropriate plugin points for
 * the user defined subclasses to implement the appropriate logic.
 */
public abstract class DataJoinMapperBase<KEYIN, VALUEIN, KEYOUT, VALUEOUT> extends Mapper<KEYIN, VALUEIN, KEYOUT, VALUEOUT> {

    protected String datasource = null;

    protected Text inputTag = null;

    protected void setup(Context context) throws IOException, InterruptedException {
	this.datasource = generateDatasource(context);;
	this.inputTag = generateInputTag(context);
    }

    protected void map(KEYIN key, VALUEIN value, Context context) throws IOException, InterruptedException {
	VALUEOUT aRecord = generateTaggedMapOutput(value, context);
	if (aRecord == null) {
	    return;
	}
	KEYOUT groupKey = generateGroupKey(aRecord, context);
	if (groupKey == null) {
	    return;
	}
	context.write(groupKey, aRecord);
    }
    
    /**
     * Determine the source input based on the input file name.
     * 
     * @param inputFile
     * @return the source tag computed from the given file name.
     */
    protected abstract String generateDatasource(Context context) throws IOException;
	
    
    /**
     * Determine the source tag based on the input file name.
     * 
     * @param inputFile
     * @return the source tag computed from the given file name.
     */
    protected Text generateInputTag(Context context) throws IOException{
	return new Text(datasource);
    }

    /**
     * Generate a tagged map output value. The user code can also perform
     * projection/filtering. If it decides to discard the input record when
     * certain conditions are met,it can simply return a null.
     * 
     * @param value
     * @return an object of TaggedMapOutput computed from the given value.
     */
    protected abstract VALUEOUT generateTaggedMapOutput(VALUEIN value, Context context) throws IOException;

    /**
     * Generate a map output key. The user code can compute the key
     * programmatically, not just selecting the values of some fields. In this
     * sense, it is more general than the joining capabilities of SQL.
     * 
     * @param aRecord
     * @return the group key for the given record
     */
    protected abstract KEYOUT generateGroupKey(VALUEOUT aRecord, Context context) throws IOException;

}
