package com.jyz.study.mybatis.po;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable{

    private static final long serialVersionUID = 1L;

    List<Money> moneys;
    
    public List<Money> getMoneys() {
	return moneys;
    }
    public void setMoneys(List<Money> moneys) {
	this.moneys = moneys;
    }
    
}


