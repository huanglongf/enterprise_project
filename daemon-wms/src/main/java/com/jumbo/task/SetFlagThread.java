package com.jumbo.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.jumbo.wms.manager.hub2wms.CreateStaTaskManager;
import com.jumbo.wms.manager.outbound.AdCheckManager;
import com.jumbo.wms.model.hub2wms.WmsSalesOrderQueue;

public class SetFlagThread implements Runnable {
    @Autowired
    private CreateStaTaskManager createStaTaskManager;
    @Autowired
    private AdCheckManager adCheckManager;

    private static final Logger logger = LoggerFactory.getLogger(SetFlagThread.class);
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
    /**
     * 分店铺初始化库存
     */
    private String owner;
    /**
     * 线程名称
     */
    private String threadName = "";

    public SetFlagThread(String threadName) {
        WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
        createStaTaskManager = (CreateStaTaskManager) context.getBean("createStaTaskManager");
        adCheckManager = (AdCheckManager) context.getBean("adCheckManager");
        // createStaTaskManager = SpringUtil.getBean("createStaTaskManager");
        // adCheckManager = SpringUtil.getBean("adCheckManager");
        this.threadName = threadName;
    }

    public void run() {
        if (logger.isDebugEnabled()) {
            logger.debug(threadName + " Begin");
        }
        if (("ou").equals(name)) {
            setFlagByWarehouse(id);
        } else if (("setWarehouse").equals(name)) {
            createStaTaskManager.setDefaultWarehouseById(id);
            createStaTaskManager.setDefaultCanFlagIsFalse(id);
        } else if (("initInv").equals(name)) {
            createStaTaskManager.initInventoryForOnceUseByOwner(owner);
        } else if (("IsNotCheckInv").equals(name)) {
            createStaTaskManager.createTomsOrdersendToMq2(id);// 先打标
            createStaTaskManager.createTomsOrdersendToMq(id);
        } else if (("adCheck").equals(name)) {
            adCheckManager.confirmCheckOrderById(id);
        } else {
            createStaTaskManager.createStaById(id);
        }
        if (logger.isDebugEnabled()) {
            logger.debug(threadName + " End");
        }
    }



    private void setFlagByWarehouse(Long id2) {
        if (logger.isDebugEnabled()) {
            logger.debug(threadName + " SetFlagThread.setFlagByWarehouse Enter id2:" + id2);
        }
        List<WmsSalesOrderQueue> wql = createStaTaskManager.getToSetFlagOrderByWarehouse(id2);
        for (WmsSalesOrderQueue wq : wql) {
            try {
                createStaTaskManager.setFlagToOrder(wq.getId(), id2);
                createStaTaskManager.createTomsOrdersendToMq(wq.getId());
            } catch (Exception e) {
                logger.error("", e);
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug(threadName + " SetFlagThread.setFlagByWarehouse End id2:" + id2);
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

}
