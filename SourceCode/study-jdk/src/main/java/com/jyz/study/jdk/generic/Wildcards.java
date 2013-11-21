package com.jyz.study.jdk.generic;

/**
 * 通配符
 * @author JoyoungZhang@gmail.com
 * http://zy19982004.iteye.com/blog/1978028
 */
public class Wildcards {
	//原生类型
	static void rawArgs(Holder holder, Object arg){
		holder.get();
		holder.set(arg);
		holder.set(new Wildcards());
	}
	
	//无界通配符
	static void unboundedArg(Holder<?> holder, Object arg){
		Object object = holder.get();
		T t = holder.get();//compile error, don't hava any T
		holder.set(arg);//compile error
		holder.set(null);
	}
	
	//子类型通配符
	static <T> T wildSubtype(Holder<? extends T> holder, T arg){
		holder.set(arg);//compile error
		holder.set(null);
		T t = holder.get();
		return t;
	}
	
	//超类型通配符
	static <T> T wildSupertype(Holder<? super T> holder, T arg){
		holder.set(arg);
		holder.set(null);
		T t = holder.get();//compile error
		Object obj = holder.get();
		return t;
	}
	
	
	class Holder<T> {
		private T element;
		
		public Holder() {
		}

		public Holder(T element) {
			this.element = element;
		}
		
		public T get(){
			return element;
		}
		
		public void set(T arg){
			this.element = arg;
		}
		
		public boolean equals(Object obj){
			if(!(obj instanceof Holder)){
				return false;
			}
			Holder<?> holder = (Holder<?>)obj;
			return element == null ? holder.element == null : element.equals(holder.element);
		}
	}
}
