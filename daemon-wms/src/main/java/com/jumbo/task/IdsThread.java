package com.jumbo.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.jumbo.webservice.ids.manager.IdsManagerProxy;
import com.jumbo.wms.model.vmi.ids.IdsServerInformation;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrder;

public class IdsThread extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(IdsThread.class);
    @Autowired
    private IdsManagerProxy idsManagerProxy;
    /*
     * 外包仓-编码
     */
    private String source;
    /*
     * 批次号
     */
    private Long batchNo;
    /*
     * IDS配置实体类
     */
    IdsServerInformation idsServerInformation;


    public IdsThread() {
        WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
        idsManagerProxy = (IdsManagerProxy) context.getBean("idsManagerProxy");
    }

    public IdsThread(String source, Long batchNo, IdsServerInformation idsServerInformation) {
        WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
        idsManagerProxy = (IdsManagerProxy) context.getBean("idsManagerProxy");
        this.source = source;
        this.batchNo = batchNo;
        this.idsServerInformation = idsServerInformation;
    }

    public void run() {
    	if(logger.isDebugEnabled()){
    		logger.debug("IdsThread,Begin;source=" + source);	
    	}
        
        List<MsgOutboundOrder> orderList = idsManagerProxy.getOutBoundListByBatchNo(batchNo);
        if (orderList.size() > 0) {
            idsManagerProxy.sendOrder(source, source, batchNo, orderList, idsServerInformation);
        } else {
            logger.warn("orderList.size()=0,batchNo=" + batchNo);
        }
    	if(logger.isDebugEnabled()){
    		logger.debug("IdsThread,End;source=" + source);	
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
