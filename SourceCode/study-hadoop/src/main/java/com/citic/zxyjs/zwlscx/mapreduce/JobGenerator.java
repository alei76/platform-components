package com.citic.zxyjs.zwlscx.mapreduce;

import java.io.IOException;

import org.apache.hadoop.mapreduce.Job;


public interface JobGenerator {
    
    public Job generateJob() throws IOException;
    
}
