package com.jumbo.wms.bean;

import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.impl.JobDetailImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.util.MethodInvoker;

public class AppsMethodInvokingJobDetailFactoryBean extends MethodInvokingJobDetailFactoryBean {

    protected static final Logger logger = LoggerFactory.getLogger(AppsMethodInvokingJobDetailFactoryBean.class);



    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws ClassNotFoundException, NoSuchMethodException {
        super.afterPropertiesSet();

        logger.info("origin jobClass : " + ((JobDetail) super.getObject()).getJobClass().getName());
        // Consider the concurrent flag to choose between stateful and stateless job.
        if (MethodInvokingJob.class.getName().equals(((JobDetail) super.getObject()).getJobClass().getName())) {
            ((JobDetailImpl) super.getObject()).setJobClass(AppsMethodInvokingJob.class);
        } else {
            ((JobDetailImpl) super.getObject()).setJobClass(AppsStatefulMethodInvokingJob.class);
        }
        logger.info("new jobClass : " + ((JobDetailImpl) super.getObject()).getName());
    }

    public static class AppsMethodInvokingJob extends MethodInvokingJob {


        protected static final Logger logger = LoggerFactory.getLogger(AppsMethodInvokingJobDetailFactoryBean.class);
        private MethodInvoker methodInvoker;
        private String errorMessage;

        /**
         * Set the MethodInvoker to use.
         */
        @Override
        public void setMethodInvoker(MethodInvoker methodInvoker) {
            this.methodInvoker = methodInvoker;
            this.errorMessage = "Could not invoke method '" + this.methodInvoker.getTargetMethod() + "' on target object [" + this.methodInvoker.getTargetObject() + "]";
        }

        /**
         * Invoke the method via the MethodInvoker.
         */
        @Override
        protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

            try {

                // 只有当任务开启时，才会调用任务 // 调用具体task执行method代码
                this.methodInvoker.invoke();

                logger.info("task invoke:" + methodInvoker.getTargetMethod() + " time:" + new Date());

            } catch (Exception ex) {
                throw new JobExecutionException(this.errorMessage, ex, false);
            }
        }
    }

    public static class AppsStatefulMethodInvokingJob extends AppsMethodInvokingJob {}
}
