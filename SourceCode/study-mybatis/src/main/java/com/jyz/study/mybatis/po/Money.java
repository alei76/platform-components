package com.jyz.study.mybatis.po;

import java.io.Serializable;

public class Money implements Serializable{

    private static final long serialVersionUID = 1L;
    
    double sum;
    
    public Money(double sum) {
	this.sum = sum;
    }
    
    public Money() {
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }
    
    
}