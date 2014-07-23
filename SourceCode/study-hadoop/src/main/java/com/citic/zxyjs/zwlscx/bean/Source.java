package com.citic.zxyjs.zwlscx.bean;

import java.io.Serializable;

/**
 * 资源基类
 * 
 * @author JoyoungZhang@gmail.com
 */
public class Source implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

}
