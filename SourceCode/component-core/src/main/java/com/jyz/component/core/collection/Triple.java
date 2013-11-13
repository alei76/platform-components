package com.jyz.component.core.collection;

/**
 *  
 *	@author JoyoungZhang@gmail.com
 *
 */
public class Triple<T1, T2, T3> extends Tuple<T1, T2> {
	
	private static final long serialVersionUID = 1L;
	
	private T3 t3;

	public Triple(T1 t1, T2 t2, T3 t3) {
		super(t1, t2);
		this.t3 = t3;
	}
	
	@Override
	public String toString(){
		return "Triple[t1:" + super.getT1() + ", t2:" + super.getT2() + ", t3: " + t3 + "]";
	}
	
	@Override
	public boolean equals(Object object){
		if(!(object instanceof Triple)){
			return false;
		}
		Triple<T1, T2, T3> triple = (Triple<T1, T2, T3>)object;
		return 	triple.getT1() == null ? super.getT1() == null : triple.getT1().equals(super.getT1()) &&
				triple.getT2()== null ? super.getT2()== null : triple.getT2().equals(super.getT2()) &&
				triple.t3 == null ? this.t3 == null : triple.t3.equals(this.t3);
	}
	
	@Override
	public int hashCode(){
		final int prime = 37;
		int result = 17;
		result = result * prime + (super.getT1() == null ? 0 : super.getT1().hashCode());
		result = result * prime + (super.getT2() == null ? 0 : super.getT2().hashCode());
		result = result * prime + (t3 == null ? 0 : t3.hashCode());
		return result;
	}

}
