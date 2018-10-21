package com.bt.utils;

import java.util.concurrent.Executors;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

public class BaseConst {

	/**
	 * session 保存key值
	 */
	public final static String login_session = "session_employee";
	/**
	 * 分页显示条数
	 */
	public static int pageSize=10;
	public static int excel_pageSize=300000;
	public static int data_pageSize=200000;
	
	/**
	 * 系统创建
	 */
	public final static String SYSTEM_CREATE_USER = "1";
	public final static String SYSTEM_JOB_CREATE = "1";
	public final static String PRO_RESULT_FLAG_SUCCESS ="success";
	public final static String PRO_RESULT_FLAG_ERROR ="error";

	//错误
	public final static Integer ERROR_PARAMETER_IS_NULL = 404;
	//成功
	public final static Integer SUCCESS = 200;

	/**
	 * 固定文件路径
	 */
	public final static String file_path = "";
	
	//初始化线程池
	public final static ListeningExecutorService commonTaskExecutorService = MoreExecutors
			.listeningDecorator(Executors.newFixedThreadPool(1));

}
