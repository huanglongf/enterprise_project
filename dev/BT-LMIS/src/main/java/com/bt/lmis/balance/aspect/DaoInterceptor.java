package com.bt.lmis.balance.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.bt.lmis.balance.util.CommonUtil;
import com.bt.lmis.balance.util.DateUtil;

/** 
 * @ClassName: DaoInterceptor
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年7月6日 下午2:32:42 
 * 
 */
@Aspect
@Component
public class DaoInterceptor {
	
	/**
	 * @Title: method
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年7月6日 下午2:33:52
	 */
	@Pointcut("execution(public * com.bt.lmis.balance.dao.*FreightSettlementMapper.*(..))")
	private void method() {};
	
	/**
	 * @Title: timeConsumingLog
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param pjp
	 * @throws Throwable
	 * @return: Object
	 * @author: Ian.Huang
	 * @date: 2017年7月6日 下午2:39:43
	 */
	@Around(value="method()")
	public Object timeConsumingLog(ProceedingJoinPoint pjp) throws Throwable {
		// 开始
		String from=DateUtil.presentTimestrapStrYMDHMSS();
		System.out.println(">>>>>>>>>>>>>>>>>>>>"+pjp.getArgs()[0]+"开始");
		//
		Object retrunValue=pjp.proceed();
		// 退出
		String to=DateUtil.presentTimestrapStrYMDHMSS();
		System.out.println(">>>>>>>>>>>>>>>>>>>>"+pjp.getArgs()[0]+"退出");
		// 计算耗时
		long timeConsuming=DateUtil.milliSecondsInDomainYMDHMSS(from, to);
		System.out.println(">>>>>>>>>>>>>>>>>>>>"+pjp.getArgs()[0]+"耗时["+timeConsuming+"毫秒]>>["+timeConsuming/1000+"秒]>>["+timeConsuming/1000/60+"分钟]>>["+timeConsuming/1000/60/60+"小时]");
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
	 * @date: 2017年7月6日 下午2:41:36
	 */
	@AfterThrowing(value="method()", throwing="ex")
	public void exceptionLog(JoinPoint jp, Throwable ex) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>"+jp.getArgs()[0]+"异常");
		System.out.println(CommonUtil.formatExceptionStack(ex));
		
	}
	
}