package com.bt.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadManageUtil {

	static ExecutorService singleThreadExecutor =null; 
	static ExecutorService singleThreadExecutorTransfer =null;  
	public static  ExecutorService getSingleThreadExecutor(){
		if(singleThreadExecutor==null)
			 singleThreadExecutor = Executors.newSingleThreadExecutor();  
		return singleThreadExecutor;	
	}
	public static  ExecutorService getSingleThreadExecutorTransfer(){
		if(singleThreadExecutorTransfer==null)
			singleThreadExecutorTransfer = Executors.newSingleThreadExecutor();  
		return singleThreadExecutorTransfer;
		
	}
	
	
}
