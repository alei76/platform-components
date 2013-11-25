package com.jyz.study.jdk.conditionControl;


/**
 * return 程序返回
 * break 跳出(本层+1)循环 
 * continue 忽略本次循环
 * @author JoyoungZhang@gmail.com
 *
 */
public class ReturnBreakContinue {
	
	public static void main(String[] args) {
		for(int k=1;k<=3;k++){
			for(int i=1;i<=3;i++){
				for(int j=1;j<=3;j++){
					if(j==2){
//						continue;
//						break;
						return;
					}
					System.out.println("k=" + k + ", i=" + i + ", j=" + j);
				}
			}
		}
	}
	
}
