package com.jyz.component.core.collection;

import java.io.Serializable;


/**
 *  
 *	@author JoyoungZhang@gmail.com
 *	从效果上看，tuple 冻结一个 list，而 list 解冻一个 tuple
 *	http://www.blogjava.net/lsbwahaha/archive/2011/10/28/362254.html
 *
 */
public class Tuple<T1, T2>  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private T1 t1;
	private T2 t2;
	
	public Tuple(T1 t1, T2 t2){
		this.t1 = t1;
		this.t2 = t2;
	}

	public T1 getT1() {
		return t1;
	}

	public void setT1(T1 t1) {
		this.t1 = t1;
	}

	public T2 getT2() {
		return t2;
	}

	public void setT2(T2 t2) {
		this.t2 = t2;
	}
	
	@Override
	public String toString(){
		return "Tuple[t1:" + t1 + ", t2:" + t2 + "]";
	}
	
	@Override
	public boolean equals(Object object){
		if(!(object instanceof Tuple)){
			return false;
		}
		Tuple<T1, T2> tuple = (Tuple<T1, T2>)object;
		return 	tuple.t1 == null ? this.t1 == null : tuple.t1.equals(this.t1) &&
				tuple.t2 == null ? this.t2 == null : tuple.t2.equals(this.t2);
	}
	
	@Override
	public int hashCode(){
		final int prime = 37;
		int result = 17;
		result = result * prime + (t1 == null ? 0 : t1.hashCode());
		result = result * prime + (t2 == null ? 0 : t2.hashCode());
		return result;
	}
	
}
