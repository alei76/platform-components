package com.jyz.study.jdk.interview;


/**
 * 把字符串中的空格换成%20
 * @author JoyoungZhang@gmail.com
 *
 */
public class ReplaceSpace {     
	/**
	 * 从头比较
	 * @param oldchar
	 * @param replacement
	 */
	public void repalceFromEnd(char[] oldchar,char[] replacement){         
		int sumOfSpace = 0;         
		for(int i = 0;i < oldchar.length;i++){             
			if(oldchar[i] == ' ')                 
				sumOfSpace++;         
		}         
		if(sumOfSpace == 0){             
			return ;         
		}         
		char [] newchar = new char[oldchar.length + (replacement.length-1) * sumOfSpace];         //指向被替换字符串的最后一个元素         
		int pOld = oldchar.length - 1;   //10      //指向被替换字符串的最后一个元素        
		int pNew = newchar.length - 1;   //16      
		while(pNew >= 0){             
			if(oldchar[pOld] == ' '){                 
				for(int j = replacement.length - 1;j >= 0;j--){                     
					newchar[pNew--] = replacement[j];                 
				}             
			}else{                 
				newchar[pNew--] = oldchar[pOld];             
			}        
			pOld--;                 
		}         
		for(char c:newchar){             
			System.out.print(c);      
		}     
	}  
	
	/**
	 * 从尾比较
	 * @param oldchar
	 * @param replacement
	 */
	public void repalceFromBegin(char[] oldchar,char[] replacement){         
		int sumOfSpace = 0;         
		for(int i = 0;i < oldchar.length;i++){             
			if(oldchar[i] == ' ')                 
				sumOfSpace++;         
		}         
		if(sumOfSpace == 0){             
			return ;         
		}         
		char [] newchar = new char[oldchar.length + (replacement.length-1) * sumOfSpace];         //指向被替换字符串的最后一个元素         
		int pOld = 0;         //指向被替换字符串的第一个元素        
		int pNew = 0;     
		while(pNew < newchar.length){             
			if(oldchar[pOld] == ' '){                 
				for(int j = 0;j < replacement.length;j++){                     
					newchar[pNew++] = replacement[j];                 
				}             
			}else{                 
				newchar[pNew++] = oldchar[pOld];             
			}        
			pOld++;                 
		}         
		for(char c:newchar){             
			System.out.print(c);      
		}     
	}  
	
	public static void main(String[] args) {        
		char [] ch = "We are happy".toCharArray();    
		ReplaceSpace rs = new ReplaceSpace();     
		rs.repalceFromEnd(ch, "%20".toCharArray());  
		System.out.println();
		rs.repalceFromBegin(ch, "%20".toCharArray());  
		System.out.println();
	}
	
}

