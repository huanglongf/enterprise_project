package com.jumbo.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.jumbo.webservice.ids.manager.IdsManagerProxy;
import com.jumbo.wms.model.vmi.ids.IdsServerInformation;
import com.jumbo.wms.model.warehouse.AdvanceOrderAddInfo;

public class PreIdsThread extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(PreIdsThread.class);
    @Autowired
    private IdsManagerProxy idsManagerProxy;
    /*
     * 外包仓-编码
     */
    private String source;
    /*
     * 批次号 主键ID
     */
    private Long batchNo;
    /*
     * IDS配置实体类
     */
    IdsServerInformation idsServerInformation;


    public PreIdsThread() {
        WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
        idsManagerProxy = (IdsManagerProxy) context.getBean("idsManagerProxy");
    }

    public PreIdsThread(String source, Long batchNo, IdsServerInformation idsServerInformation) {
        WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
        idsManagerProxy = (IdsManagerProxy) context.getBean("idsManagerProxy");
        this.source = source;
        this.batchNo = batchNo;
        this.idsServerInformation = idsServerInformation;
    }



    public void run() {
    	if(logger.isDebugEnabled()){
            logger.debug("PreIdsThread,Begin;source=" + source);
    	}
        List<AdvanceOrderAddInfo> orderList = idsManagerProxy.getPreOrderListByBatchNo(batchNo);
        if (orderList.size() > 0) {
            try {
                idsManagerProxy.sendPreOrder(source, source, batchNo, orderList, idsServerInformation);
            } catch (Exception e) {
                logger.error("PreIdsThread_error:" + batchNo, e);
            }
        } else {
            logger.warn("PreIdsThread orderList.size()=0,batchNo=" + batchNo);
        }
    	if(logger.isDebugEnabled()){
            logger.debug("PreIdsThread,End;source=" + source);
    	}
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Long getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(Long batchNo) {
        this.batchNo = batchNo;
    }

    public IdsServerInformation getIdsServerInformation() {
        return idsServerInformation;
    }

    public void setIdsServerInformation(IdsServerInformation idsServerInformation) {
        this.idsServerInformation = idsServerInformation;
    }
}
