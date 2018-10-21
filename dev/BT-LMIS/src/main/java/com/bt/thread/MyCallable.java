package com.bt.thread;

import java.util.Map;
import java.util.concurrent.Callable;


public class MyCallable implements Callable<Object> {
	private String taskNum;
	  final Object obj;
	  final String methodName;
	  Map<Object,Object>param;
	public MyCallable(String taskNum, Map<Object,Object>para,final Object objs,String methodNames) {
		this.taskNum = taskNum;
		this.obj=objs;
		this.methodName=methodNames;
		this.param=para;
	}

	@Override
	public  Object call(){
		Object object = null;
		try {
//			System.out.println("线程:"+taskNum+"启动:"+DateUtil.getData());
			object=Invoke.invoke(obj, methodName,new Class[] {Map.class,String.class},new Object[]{param,taskNum});
//			System.out.println("线程:"+taskNum+"结束:"+DateUtil.getData()+"返回结果为:"+object);		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return object;
	}
	
}
