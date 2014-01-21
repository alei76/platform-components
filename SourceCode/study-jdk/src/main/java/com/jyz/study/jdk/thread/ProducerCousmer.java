package com.jyz.study.jdk.thread;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *  http://www.iteye.com/topic/1132202
 *  生产者/消费者的Java代码，其中生产者每次生产1个0到1000之间的随机数，消费者则把该随机数打印出来。如果产生的随机数为0，则生产者、消费者均退出运行
 *	@author zhaoyong.zhang
 *	create time 2014-1-21
 */
public class ProducerCousmer {

	private final BlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>();
	private final Random rd = new Random();   
	
	public void startTest() {   
        Producer producer = new Producer();   
        Consumer consumer = new Consumer();   
        producer.start();   
        consumer.start();   
    }   
  
    public static void main(String[] args) {   
        new ProducerCousmer().startTest();   
    } 
	
	class Producer extends Thread{
		@Override
		public void run() {
			while(true){
				try {
					int i = rd.nextInt(10);
					System.out.println("Producer Generate : " + i);   
					queue.put(i);
					if(i == 0){
						System.out.println("Producer stoped.");   
						break;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	class Consumer extends Thread{
		@Override
		public void run() {
			while(true){
				try {
					int i = queue.take();
					System.out.println("Consumer Cousume " + i);
					if (i == 0) {   
                        System.out.println("Consumer stoped.");   
                        return;   
                    } 
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
