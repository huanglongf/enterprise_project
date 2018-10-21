package com.bt.lmis.test;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TestRunnable implements Runnable {

	public static volatile int n = 0;
	
	@Override
	public void run() {
		for (int i = 0; i < 10; i++, n++)
	        try	{
	        	Thread.sleep(3);  // 为了使运行结果更随机，延迟3毫秒
	        	System.out.println(Thread.currentThread().getName() + ":" + i);
	        	
	        } catch (Exception e) {}
		   	
	}
	
	public static void main(String[] args) throws InterruptedException {
		ExecutorService pool= Executors.newCachedThreadPool();
		Queue<Future<?>> queue= new ArrayBlockingQueue<Future<?>>(10);;
		for(int i= 0; i< 10; i++) {
			queue.add(pool.submit(new TestRunnable()));
			
		}
		while(true) {
			if(queue.size() != 0) {
				if(queue.peek().isDone()) {
					queue.poll();
					
				}
				
			} else {
				break;
					
			}
			
		}
		System.out.println(n);
		
	}

}
