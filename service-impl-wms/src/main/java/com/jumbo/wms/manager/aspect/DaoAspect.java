package com.jumbo.wms.manager.aspect;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import loxia.dao.Pagination;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DAO拦截器，主要用于日志输出，输出list反馈集合大小
 * 
 * @author sjk
 *
 */
@Aspect
public class DaoAspect {

    protected static final Logger log = LoggerFactory.getLogger(DaoAspect.class);

    @Around(" this(loxia.dao.GenericEntityDao)")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        // 判断拦截DAO反馈数据是list或者分页集合时处理,日志输出，否则直接执行方法
        if (pjp.getSignature() instanceof MethodSignature) {
            MethodSignature ms = (MethodSignature) pjp.getSignature();
            Method currentMethod = pjp.getTarget().getClass().getMethod(ms.getName(), ms.getParameterTypes());
            Class<?> cls = currentMethod.getReturnType();
            if (Pagination.class.equals(cls) || List.class.equals(cls)) {
                // 使用时间毫秒数作为方法唯一值,便于日志查询
                Date startTime = new Date();
                log.debug("method :{},key:{}", pjp.getSignature().getName(), startTime.getTime());
                Object obj = pjp.proceed();
                // 日志嵌入输出
                if (obj instanceof List && obj != null) {
                    List<?> list = (List<?>) obj;
                    log.debug("method :{},key:{},size:{}", pjp.getSignature().getName(), startTime.getTime(), list.size());
                    log.debug(pjp.getSignature().getName() + "list size : " + list.size());
                } else if (obj instanceof Pagination && obj != null) {
                    Pagination<?> pg = (Pagination<?>) obj;
                    if (pg.getItems() != null) {
                        log.debug("method :{},key:{},size:{}", pjp.getSignature().getName(), startTime.getTime(), pg.getItems().size());
                    }
                }
                return obj;
            }
        }
        return pjp.proceed();
    }
}
