package com.jumbo.wms.manager.lmis;

import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jumbo.wms.manager.util.SpringUtil;
import com.jumbo.wms.model.lmis.ExpressWaybill;

public class LmisThread implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(LmisThread.class);
    private LmisThreadTaskManager lmisThreadTaskManager;
    /**
     * 标识执行id
     */
    private Long id;
    /**
     * 需要执行的方法区分标识
     */
    private String name;
    /**
     * 运单数据
     */
    private ExpressWaybill expressWaybill;
    /**
     * 线程名称
     */
    private String threadName = "";

    public LmisThread(String threadName) {
        lmisThreadTaskManager = (LmisThreadTaskManager) SpringUtil.getBean("lmisThreadTaskManager");
        if (lmisThreadTaskManager == null) {
            Log.info("SpringUtil.getBean失败！");
            lmisThreadTaskManager = (LmisThreadTaskManager) SpringUtil.getBean("lmisThreadTaskManager", LmisThreadTaskManagerImpl.class);
        }
        this.threadName = threadName;
    }

    @Override
    public void run() {
        if (("getExpressWaybillData").equals(name)) {
            try {
                lmisThreadTaskManager.getExpressWaybillDetailData(expressWaybill);
            } catch (Exception e) {
                logger.error("多线程获取运单明细数据异常！tracking_no=" + expressWaybill.getExpress_number(), e);
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public ExpressWaybill getExpressWaybill() {
        return expressWaybill;
    }

    public void setExpressWaybill(ExpressWaybill expressWaybill) {
        this.expressWaybill = expressWaybill;
    }
}
