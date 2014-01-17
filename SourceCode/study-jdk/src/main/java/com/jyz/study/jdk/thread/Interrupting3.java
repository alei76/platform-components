package com.jyz.study.jdk.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *  一旦底层资源被关闭，任务将解除阻塞。
 *  sockedInput.close()会导致阻塞的读操作sockedInput.read()抛出java.net.SocketException: socket closed
 *  System.in.close()并不会导致阻塞的读操作System.in.read()抛出任何异常,无法解除阻塞。难道一定要手动输入才能解除则是
 *	@author zhaoyong.zhang
 *	create time 2014-1-17
 */
public class Interrupting3 {
	public static void main(String[] args) throws Exception {
        ExecutorService exec =  Executors. newCachedThreadPool();
//        ServerSocket server = new ServerSocket(8080);
//        InputStream sockedInput = new Socket("localhost" ,8080).getInputStream();
        
//        exec.execute( new IOBlocked(sockedInput));
        
        exec.execute( new IOBlocked(System.in));
        
        TimeUnit.MILLISECONDS.sleep(100);
        System.out.println("shutdown all Threads" );
        exec.shutdownNow();//关闭所有线程
        
//        TimeUnit.SECONDS.sleep(1);
//        System.out.println("closing " + sockedInput.getClass().getName());
//        sockedInput.close();
        
        TimeUnit.SECONDS.sleep(1);
        System.out.println("closing " + System.in.getClass().getName());
        System.in.close();
  }

}
