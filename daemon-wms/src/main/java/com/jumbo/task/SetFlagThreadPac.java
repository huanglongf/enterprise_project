package com.jumbo.task;

import java.util.List;

import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.jumbo.util.StringUtil;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.hub2wms.CreateStaTaskManager;
import com.jumbo.wms.manager.warehouse.QueueStaManagerExecute;
import com.jumbo.wms.model.warehouse.QueueSta;


public class SetFlagThreadPac implements Runnable {
    @Autowired
    private CreateStaTaskManager createStaTaskManager;
    @Autowired
    private QueueStaManagerExecute queueStaManagerExecute;
    private static final Logger logger = LoggerFactory.getLogger(SetFlagThreadPac.class);
    /**
     * 标识执行id(需要分仓的id 或者需要创建作业单的中间表ID)
     */
    private Long id;
    /**
     * 需要执行的方法区分标识<br/>
     * name = "ou" 执行分仓标识逻辑 <br/>
     * name = "sta" 执行创单逻辑
     */
    private String name;
    private String warehouseCode;
    private String threadName;

    public SetFlagThreadPac(String name) {
        threadName = name;
        WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
        createStaTaskManager = (CreateStaTaskManager) context.getBean("createStaTaskManager");
        queueStaManagerExecute = (QueueStaManagerExecute) context.getBean("queueStaManagerExecute");
    }

    public void run() {
        if (logger.isDebugEnabled()) {
            logger.debug(threadName + " Begin");
        }
        if (("canFlag").equals(name)) {
            setFlagByWarehousePac(id);
        } else if (("beginFlag").equals(name)) {
            createStaTaskManager.setBeginFlagForOrderPac(id);
        } else if (("InitInv_by warehouseCode").equals(name)) {
            createStaTaskManager.initInventoryByOuId(warehouseCode);
        } else {
            try {
                String mpMsg = createStaTaskManager.createStaByIdPac(id);
                if (!StringUtil.isEmpty(mpMsg)) {
                    try {
                        createStaTaskManager.sendCreateOrderMQToPac(mpMsg);
                        // createStaTaskManager.sendCreateOrderMQToPac(mpMsg + "_qstaId_" + id);
                    } catch (Exception e) {
                        createStaTaskManager.updateCreateOrderMQToPacStatus(mpMsg);
                        Log.info("sendCreateOrderMQToPac  " + mpMsg, e);
                    }
                }
            } catch (Exception e) {
                logger.error("createStaByIdPac----", e);
                // 增加错误次数
                if (logger.isDebugEnabled()) {
                    logger.info("qstaId:" + id, e);
                }
                if (e instanceof BusinessException) {
                    BusinessException be = (BusinessException) e;
                    if (be.getErrorCode() == ErrorCode.OMS_ORDER_CANACEL) {
                        // 删除数据，记录日志
                        if (logger.isDebugEnabled()) {
                            logger.info("qstaId:{}, pac order cancel, delete queue and log queue", id);
                        }
                        queueStaManagerExecute.removeQstaAddLog(id);
                    } else {
                        // e.printStackTrace();
                        if (logger.isErrorEnabled()) {
                            logger.error("qstaId:{}, pac order cancel, delete queue and log queue not ErrorCode.OMS_ORDER_CANACEL" + id, e);
                        }
                        // 增加错误次数
                        if (logger.isDebugEnabled()) {
                            logger.info("qstaId:{},create sta throw exception, add error count 1 for qsta,qsta order code is:{}", id);
                        }
                        queueStaManagerExecute.addErrorCountForQsta(id, 1);
                    }
                } else {
                    // e.printStackTrace();
                    if (logger.isErrorEnabled()) {
                        logger.error("qstaId:{}, pac order cancel, delete queue and log queue not other BusinessException" + id, e);
                    }
                    // 增加错误次数
                    if (logger.isDebugEnabled()) {
                        logger.info("qstaId:{},create sta throw exception, add error count 1 for qsta,qsta order code is:{}", id);
                    }
                    queueStaManagerExecute.addErrorCountForQsta(id, 1);
                }

            }
        }
        if (logger.isInfoEnabled()) {
            logger.info(threadName + " End");
        }

    }

    private void setFlagByWarehousePac(Long id2) {
        if (logger.isInfoEnabled()) {
            logger.info("SetFlagThread.setFlagByWarehouse Enter id2:" + id2);
        }
        List<QueueSta> queueStas = createStaTaskManager.getToSetBatchOrderByWarehouse(id2);
        for (QueueSta wq : queueStas) {
            try {
                createStaTaskManager.setFlagToOrderPac(wq.getId(), id2);
                createStaTaskManager.createOrdersendToMq(wq.getId());
            } catch (Exception e) {
                logger.error("setFlagByWarehousePac3", e);
            }
        }
        if (logger.isInfoEnabled()) {
            logger.info("SetFlagThread.setFlagByWarehouse End id2:" + id2);
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

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

}
