package com.bt.thread;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
public class ThreadUtil {
	/**
	 * @param param (传递参数)
	 * @param obj (类对象)
	 * @param method (方法名）
	 * @param taskSize (线程总数)
	 * @param poolSize (线程池大小)
	 */
	public static  List<Future<?>> task(Map<Object,Object>param,final Object obj,String method,int startPage,int taskSize,int poolSize){
	    ExecutorService pool = Executors.newFixedThreadPool(5);
		List<Future<?>> list = new ArrayList<Future<?>>();
		for (int i =startPage; i <=taskSize; i++) {
			Callable<?> c =new  MyCallable(String.valueOf(i),param,obj,method);
			Future<?> f =pool.submit(c);
			list.add(f);
		}
		pool.shutdown();
		return list;
	}

	
	
	
}
