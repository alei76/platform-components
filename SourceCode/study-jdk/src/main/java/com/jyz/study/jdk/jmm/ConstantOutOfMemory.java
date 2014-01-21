package com.jyz.study.jdk.jmm;

import java.util.ArrayList;
import java.util.List;
 
/**
 * @Described：常量池内存溢出探究
 * @VM args : -XX:PermSize=10M -XX:MaxPermSize=10M
 */
public class ConstantOutOfMemory {

    public static void main(String[] args) throws Exception {
       try {
           List<String> strings = new ArrayList<String>();
           int i = 0;
           while(true){
              strings.add(String.valueOf(i++).intern());
           }
       } catch (Exception e) {
           e.printStackTrace();
           throw e;
       }
    }
}
