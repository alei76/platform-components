package com.jyz.study.mybatis.po;

public class Blog {

	private int id;

	private String title;

	private String content;

	private String owner;

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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
