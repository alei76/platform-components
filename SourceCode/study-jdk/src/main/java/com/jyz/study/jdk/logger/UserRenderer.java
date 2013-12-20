package com.jyz.study.jdk.logger;

import org.apache.log4j.or.ObjectRenderer;

public class UserRenderer implements ObjectRenderer{

	@Override
	public String doRender(Object o) {
		if(o instanceof User){
			System.out.println("user is:");
			return ((User)o).generater();
		}
		return o.toString();
	}

}

