package com.bt.lmis.balance.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.bt.lmis.balance.model.EstimateParam;
import com.bt.lmis.balance.util.CommonUtil;
import com.bt.lmis.balance.util.DateUtil;

/** 
 * @ClassName: LogInterceptor
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年5月10日 下午2:43:42 
 * 
 */
@Aspect
@Component
public class LogInterceptor {
	
	/**
	 * @Title: method
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年5月10日 下午4:01:28
	 */
	@Pointcut("execution(public * com.bt.lmis.balance..*(com.bt.lmis.balance.model.EstimateParam,..))")
	private void method() {};
	
	/**
	 * @Title: timeConsumingLog
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param pjp
	 * @throws Throwable
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年5月10日 下午2:53:25
	 */
	@Around(value="method()")
	public Object timeConsumingLog(ProceedingJoinPoint pjp) throws Throwable {
		EstimateParam param=(EstimateParam) pjp.getArgs()[0];
		// 批次号
		String batchNumber=">>>>>>>>>>>>>>>>>>>>{"+param.getBatchNumber()+"}";
		// 操作日志项
		String logTitle=param.getParam().get("logTitle").toString();
		// 开始
		String from=DateUtil.presentTimestrapStrYMDHMSS();
		System.out.println(batchNumber+"<"+from+">"+logTitle+"开始");
		//
		Object retrunValue=pjp.proceed();
		// 退出
		String to=DateUtil.presentTimestrapStrYMDHMSS();
		System.out.println(batchNumber+"<"+to+">"+logTitle+"退出");
		// 计算耗时
		long timeConsuming=DateUtil.milliSecondsInDomainYMDHMSS(from, to);
		System.out.println(batchNumber+logTitle+"耗时["+timeConsuming+"毫秒]>>["+timeConsuming/1000+"秒]>>["+timeConsuming/1000/60+"分钟]>>["+timeConsuming/1000/60/60+"小时]");
		// 被切函数返回值
		return retrunValue;
		
	}
	
	/**
	 * @Title: exceptionLog
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param jp
	 * @param ex
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年5月10日 下午4:55:25
	 */
	@AfterThrowing(value="method()", throwing="ex")
	public void exceptionLog(JoinPoint jp, Throwable ex) {
		EstimateParam param=(EstimateParam) jp.getArgs()[0];
		System.out.println(">>>>>>>>>>>>>>>>>>>>{"+param.getBatchNumber()+"}"+param.getParam().get("logTitle")+"异常");
		System.out.println(CommonUtil.formatExceptionStack(ex));
		
	}
	
}