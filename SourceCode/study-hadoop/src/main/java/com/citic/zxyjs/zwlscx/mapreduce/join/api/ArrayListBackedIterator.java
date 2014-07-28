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

package com.citic.zxyjs.zwlscx.mapreduce.join.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class provides an implementation of ResetableIterator. The
 * implementation will be based on ArrayList.
 */
public class ArrayListBackedIterator<E> implements ResetableIterator<E> {

    private Iterator<E> iter;

    private ArrayList<E> data;

    public ArrayListBackedIterator() {
	this(new ArrayList<E>());
    }

    public ArrayListBackedIterator(ArrayList<E> data) {
	this.data = data;
	this.iter = this.data.iterator();
    }

    public void add(E item) {
	this.data.add(item);
    }

    public boolean hasNext() {
	return this.iter.hasNext();
    }

    public E next() {
	return this.iter.next();
    }

    public void remove() {

    }

    public void reset() {
	this.iter = this.data.iterator();
    }

    public void close() throws IOException {
	this.iter = null;
	this.data = null;
    }
}
