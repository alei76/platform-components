package com.jyz.study.mybatis.po;

import java.io.Serializable;
import java.util.Map;

public class Content implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String cn;
	private String us;
	private int size;
	
	private Map<String, String> blogs;
	
	public Content(String cn, String us, int size) {
		this.cn = cn;
		this.us = us;
		this.size = size;
	}
	
	public Content() {
	}
	
	public String getCn() {
		return cn;
	}
	public void setCn(String cn) {
		this.cn = cn;
	}
	public String getUs() {
		return us;
	}
	public void setUs(String us) {
		this.us = us;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}

	public Map<String, String> getBlogs() {
		return blogs;
	}

	public void setBlogs(Map<String, String> blogs) {
		this.blogs = blogs;
	}

	@Override
	public String toString() {
		return "cn: " + cn + ", us: " + us + ", size: " + size;
	}
	
}
