package com.jyz.study.hadoop.mapreduce.different;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

import com.jyz.study.hadoop.common.TextPair;

/**key比较器 key比较函数类。first升序，second降序。 
 * @author JoyoungZhang@gmail.com
 *
 */
public class Example_Different_01_KeyComparator extends WritableComparator {
    public Example_Different_01_KeyComparator() {
	super(TextPair.class, true);
    }

    @SuppressWarnings("unchecked")
    public int compare(WritableComparable a, WritableComparable b) {
	TextPair t1 = (TextPair) a;  
	TextPair t2 = (TextPair) b;  
        Text l = t1.getFirst();  
        Text r = t2.getFirst();  
        int cmp = l.compareTo(r); 
        if (cmp != 0) {  
            return cmp;  
        }  
        l = t1.getSecond();  
        r = t2.getSecond();  
        return l.compareTo(r);
    }
}