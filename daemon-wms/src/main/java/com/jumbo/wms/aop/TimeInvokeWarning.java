package com.jumbo.wms.aop;



import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import com.alibaba.dubbo.rpc.RpcException;
import com.jumbo.util.FileUtils;

@Aspect
@Order(0)
public class TimeInvokeWarning implements Ordered {
    protected static final Logger logger = LoggerFactory.getLogger(TimeInvokeWarning.class);


    @Around("execution(* com.jumbo.wms.daemon..*.*(..))")
    public Object doQuery(ProceedingJoinPoint pjp) throws Throwable {
    	if(logger.isDebugEnabled()){
    		logger.debug("start--daemon--method:" + pjp.getSignature().getName() + " class:" + pjp.getSignature().getDeclaringTypeName());	
    	}
        try {
            Object rtn = pjp.proceed(pjp.getArgs());
            return rtn;
        } catch (RpcException e) {
            FileUtils.operateFile(FileUtils.TYPE_WRITE, new Date(), pjp.getSignature().getName(), pjp.getSignature().getDeclaringTypeName());
            throw e;
        } finally {
        	if(logger.isDebugEnabled()){
        		logger.debug("end--daemon--method:" + pjp.getSignature().getName() + " class:" + pjp.getSignature().getDeclaringTypeName());
        	}
        }
    }

    @Around("execution(* com.jumbo.wms.manager..*.*(..))")
    public Object doQuery2(ProceedingJoinPoint pjp) throws Throwable {
    	if(logger.isDebugEnabled()){
    		logger.debug("start--manager--method:" + pjp.getSignature().getName() + " class:" + pjp.getSignature().getDeclaringTypeName());
    	}
    	try {
            Object rtn = pjp.proceed(pjp.getArgs());
            return rtn;
        } catch (RpcException e) {
            FileUtils.operateFile(FileUtils.TYPE_WRITE, new Date(), pjp.getSignature().getName(), pjp.getSignature().getDeclaringTypeName());
            throw e;
        } finally {
        	if(logger.isDebugEnabled()){
        		logger.debug("end--daemon--method:" + pjp.getSignature().getName() + " class:" + pjp.getSignature().getDeclaringTypeName());
        	}
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
