package com.bt.lmis.test;

public class TestThread extends Thread {
	
	public static volatile int n = 0;
	
	public void run() {
		for (int i = 0; i < 10; i++, n++)
        try	{
        	sleep(3);  // 为了使运行结果更随机，延迟3毫秒
        	System.out.println(Thread.currentThread().getName() + ":" + i);
        	
        } catch (Exception e) {}
	    
	}
	    
	public static void main(String[] args) throws Exception {
        Thread threads[]= new Thread[100];
        for (int i= 0; i< threads.length; i++) {
        	// 建立线程
        	Thread thread= new TestThread();
        	thread.setName("thread-"+ i);
        	// 运行
        	thread.start();
        	threads[i]= thread;
        	
        }
        // 100个线程都执行完后继续
        for (int i= 0; i< threads.length; i++)
            threads[i].join();
        System.out.println("n="+ TestThread.n);
	    
	}
	
}
