package com.bt.lmis.test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ThreadTest {
	public static void main(String[] args) {
		 Callable<Integer> myCallable1 = new MyCallable();    // 创建MyCallable对象
		 FutureTask<Integer> ft1 = new FutureTask<Integer>(myCallable1); //使用FutureTask来包装MyCallable对象
		 Callable<Integer> myCallable2 = new MyCallable();    // 创建MyCallable对象
		 FutureTask<Integer> ft2 = new FutureTask<Integer>(myCallable2); //使用FutureTask来包装MyCallable对象
		 for (int i = 0; i < 100; i++) {
			 System.out.println(Thread.currentThread().getName() + " " + i);
			 if (i == 30) {
				 Thread thread1 = new Thread(ft1);   //FutureTask对象作为Thread对象的target创建新的线程
				 thread1.start();                      //线程进入到就绪状态
			 }
			 if (i == 31) {
				 Thread thread2 = new Thread(ft2);   //FutureTask对象作为Thread对象的target创建新的线程
				 thread2.start();                      //线程进入到就绪状态
			 }
		 }
		 System.out.println("主线程for循环执行完毕..");
		 try {
			 int sum = ft1.get();            //取得新创建的新线程中的call()方法返回的结果
			 System.out.println("sum = " + sum);
		 } catch (InterruptedException e) {
			 e.printStackTrace();
		 } catch (ExecutionException e) {
			 e.printStackTrace();
		 }
	 }
}
