package com.audaque.lib.core.utils;

/**
 * IP地址转换
 * @author 郭国兴 <Guoxing.GUO> <guoxing.guo@audaque.com>
 */
public class AdqIPUtil {
    
    /**
     * 32位IP值 转换为 点分十进制字符串
     * 例如： 1L -> 0.0.0.1
     * @param ipValue 
     * @return return null if ip address value is illegal.
     */
    public static String toStringIPv4(long ipValue){
        if(ipValue > 4294967295L || ipValue < 0){
            //throw new IllegalArgumentException("Illegal IPv4 address["+ipValue+"].");
            return null;
        }
        int val1 = (int)(ipValue>>>24);
        int val2 = (int)(ipValue<<8)>>>24;
        int val3 = (int)(ipValue<<16)>>>24;
        int val4 = (int)(ipValue<<24)>>>24;
        return  val1 + "." + val2 + "." + val3 +"." + val4;
    }
    
    /**
     * 点分十进制字符串 转换为32位IP值
     * 例如： 0.0.0.1 -> 1L
     * @param ipString
     * @return return -1 if ip address value is illegal.
     */
    public static long toValueIPv4(String ipString){
        String[] values = ipString.split("\\.");
        if(values.length!=4){
            //throw new IllegalArgumentException("Illegal IPv4 address["+ipString+"].");
            return -1;
        }
        try{
            long val1 = Long.parseLong(values[0]);
            if(val1<0 || val1>255) return -1;
            long val2 = Long.parseLong(values[1]);
            if(val2<0 || val2>255) return -1;
            long val3 = Long.parseLong(values[2]);
            if(val3<0 || val3>255) return -1;
            long val4 = Long.parseLong(values[3]);
            if(val4<0 || val4>255) return -1;
            return (val1<<24) + (val2<<16) + (val3<<8) + val4;
        }catch(NumberFormatException e){
            //throw new IllegalArgumentException("Illegal IPv4 address["+ipString+"].");
            return -1;
        }
    }
    
    public static String toStringIPv6(long ipValue){
        throw new UnsupportedOperationException("未实现");
    }
    
    public static long toValueIPv6(String ipString){
        throw new UnsupportedOperationException("未实现");
    }
    
    public static void main(String[] args){
        System.out.println(AdqIPUtil.toStringIPv4(1));
        System.out.println(AdqIPUtil.toStringIPv4(4294967295L));
        System.out.println(AdqIPUtil.toStringIPv4(4294967294L));
        System.out.println(AdqIPUtil.toStringIPv4(Integer.MAX_VALUE));
        
        System.out.println(AdqIPUtil.toValueIPv4(AdqIPUtil.toStringIPv4(1)));
        System.out.println(AdqIPUtil.toValueIPv4(AdqIPUtil.toStringIPv4(4294967295L)));
        System.out.println(AdqIPUtil.toValueIPv4(AdqIPUtil.toStringIPv4(4294967294L)));
        System.out.println(AdqIPUtil.toValueIPv4(AdqIPUtil.toStringIPv4(Integer.MAX_VALUE)));
    }
}
