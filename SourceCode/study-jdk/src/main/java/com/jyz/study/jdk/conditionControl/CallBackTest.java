package com.jyz.study.jdk.conditionControl;

/**
 * @author JoyoungZhang@gmail.com
 *
 */
public class CallBackTest {

    public static void main(String[] args) {
	final String path = "/usr/local/";
	System.out.println("解压文件");
	System.out.println("使用文件");
	test(new CallBack() {
	    public void execute() {
		System.out.println("delete temp file " + path);
	    }
	});
    }
    
    static void test(CallBack callBack){
	callBack.execute();
    }
}

interface CallBack{
    void execute();
}

