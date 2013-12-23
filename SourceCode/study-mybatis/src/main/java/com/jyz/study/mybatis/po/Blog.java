package com.jyz.study.mybatis.po;

import java.io.Serializable;

public class Blog implements Serializable{

	private static final long serialVersionUID = 1L;

	private int id;

	private String title;
	
	private Content content;

	private String owner;

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return "id: " + id + ", title: " + title + ", content: " + content
				+ ", owner: " + owner;
	}

}
