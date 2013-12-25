package com.jyz.study.jdk.collection;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 测试
 * 1.LinkHashMap构造器里boolean accessOrder的使用
 * 2.LinkHashMap添加一个元素是，调用removeEldestEntry方法
 * @author JoyoungZhang@gmail.com
 *
 */
public class LinkHashMapRemoveFirstTest {

    private static  Map<Object, Object> keyMap;
    
    public static void main(String[] args) {
	keyMap = new LinkedHashMap<Object, Object>(2, .75F, true){
	    @Override
	    protected boolean removeEldestEntry(Map.Entry<Object, Object> eldest) {
	        boolean tooBig = keyMap.size() > 2;
	        if (tooBig) {
	            Object eldestKey = eldest.getKey();
	        }
	        return tooBig;
	      }
	};
	
	keyMap.put(1, 1);
	keyMap.put(2, 2);
	keyMap.get(1);
	keyMap.put(3, 3);
	
	System.out.println(keyMap);
	
	//3.这种特殊的方式创建一个匿名类
	LinkHashMapRemoveFirstTestInner inner = new LinkHashMapRemoveFirstTestInner(){
	    @Override
	    protected boolean test(){
		return true;
	    }
	};
	
    }
	
}


class LinkHashMapRemoveFirstTestInner{
    
    public LinkHashMapRemoveFirstTestInner(){
    }
    
    protected boolean test(){
	return false;
    }
}
