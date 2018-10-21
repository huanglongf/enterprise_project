package com.jumbo.util;

import org.apache.poi.hssf.record.formula.functions.T;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringBeanFactory implements ApplicationContextAware {

    private static ApplicationContext ctx;

    @SuppressWarnings("hiding")
    public static synchronized <T> T getxBean(String beanName, Class<T> clazz) {
        return ctx.getBean(beanName, clazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
    }
}
