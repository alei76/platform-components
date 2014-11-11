package com.jyz.study.jdk.interview;


public class Power {

	/**
	 *Q71-数值的整数次方
	 *实现函数double Power(double base, int exponent)，求base的exponent次方。不需要考虑溢出。
	 */
	private static boolean InvalidInput=false;
	public static void main(String[] args) {
		double result=power(3,15);
		System.out.println(result);
	}

	public static double power(double base,int exponent){
		if(base==0.0){
			if(exponent==0){
				InvalidInput=true;
			}
			return 0.0;
		}
		double result=1;
		int unsignedExponent =exponent;
		if(exponent<0){
			unsignedExponent=-exponent;
		}
		result=powerWithUnsignedExponent(base,unsignedExponent);
		if(exponent<0){
			result=1/result;
		}
		return result;
	}
	/*
	 * in Java,integer is 32 bits
	 * we store 
	 * base^(2^0),base^(2^1),base^(2^2),...base^(2^31) or
	 * base^1,    base^2,    base^4,...    base^(2^31) 
	 * in 
	 * a[0],a[1],a[2],...a[31]
	 * obviously,a[i+1]=a[i]*a[i] (0<=i<=30)
	 * we calculate like this:
	 *  3^7=(3^1)*(3^2)*(3^4)=a[0]*a[1]*a[2]
	 */
	public static double powerWithUnsignedExponent(double base,int exponent){
		double result=1.0;
		int len=Integer.SIZE;//Integer.SIZE=32
		double[] a=new double[len];
		int count=numberOf1(exponent);
		double curPower=0.0;
		for(int i=0,j=0;i<len&&j<count;i++){
			if(i==0){
				curPower=base;
			}else{
				curPower=curPower*curPower;
			}
			if((exponent&(1<<i))!=0){//if exponent has '1' at i-th bit
				a[i]=curPower;
				count++;
			}
		}
		for(int i=0;i<len;i++){
			if(a[i]!=0){
				result*=a[i];
			}
		}
		return result;
	}
	/*
	 * a^n=a^(n/2)*a^(n/2)	(when a is even number)
	 * a^n=a^(n/2)*a^(n/2)*a (when a is odd number)
	 */
	public static double powerWithUnsignedExponent2(double base,int exponent){
		double result=1.0;
		if(exponent==0){
			return 1;
		}
		if(exponent==1){
			return base;
		}
		result=powerWithUnsignedExponent2(base,exponent/2)
				*powerWithUnsignedExponent2(base,exponent/2);
		if((exponent&(0x01))!=0){
			result*=base;
		}
		return result;
	}
	/*
	 * return how many '1' in x (binary)
	 * e.g. (15)D=(1111)B,then we get 4
	 */
	public static int numberOf1(int x){
		int count=0;
		while(x!=0){
			x&=x-1;
			count++;
		}
		return count;
	}
}
