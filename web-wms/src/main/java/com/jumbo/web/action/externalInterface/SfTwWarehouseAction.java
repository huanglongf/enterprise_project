/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 */
package com.jumbo.web.action.externalInterface;

import java.io.IOException;
import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.web.action.BaseJQGridAction;
import com.jumbo.wms.manager.task.sf.tw.TaskSfTwManager;

/**
 * 台湾顺丰服务接口
 * 
 * @author lichuan
 * 
 */
public class SfTwWarehouseAction extends BaseJQGridAction {
    private static final long serialVersionUID = 8307263972566458267L;
    @Autowired
    private TaskSfTwManager taskSfTwManger;
    private String logistics_interface;
    private String data_digest;

    /**
     * 台湾顺丰推送入库单(入库反馈) void
     * 
     * @throws
     */
    public void purchaseOrderInboundPushService() {
        if (log.isInfoEnabled()) {
            log.info("SFTW====> purchase order inbound push service start...");
        }
        // System.out.println("request is occur!");
        // System.out.println(logistics_interface);
        log.error("SFTW====> purchase order inbound push service invoke start,logistics_interface is:[{}], data_digest is:[{}]", logistics_interface, data_digest);
        String responseXml = taskSfTwManger.receiveInboundOrderPushService(logistics_interface, data_digest);
        log.error("SFTW====> purchase order inbound push service invoke end,logistics_interface is:[{}], response is:[{}]", logistics_interface, responseXml);
        // System.out.println(responseXml);
        responseMsg(responseXml);
        // System.out.println("response is send");
        if (log.isInfoEnabled()) {
            log.info("SFTW====> purchase order inbound push service end");
        }
    }

    /**
     * 下发供应商到台湾顺丰 void
     * 
     * @throws
     */
    public void sendVendorService() {
        if (log.isInfoEnabled()) {
            log.info("SFTW====> send vendor service start...");
        }
        // System.out.println("send vendor start");
        log.error("SFTW====> send vendior service invoke start...");
        String responseXml = taskSfTwManger.sendVendorService();
        log.error("SFTW====> send vendor service invoke end, response is:[{}]", responseXml);
        responseMsg(responseXml);
        // System.out.println("send vendor end");
        if (log.isInfoEnabled()) {
            log.info("SFTW====> send vendor service end");
        }
    }

    protected void responseMsg(String s) {
        try {
            // response.setCharacterEncoding("UTF-8");
            // response.setContentType("text/xml;charset=utf-8");//application/xml;charset=UTF-8
            response.setContentType("application/xml;charset=UTF-8");
            response.getWriter().print(new String(s.getBytes(), Charset.forName("UTF-8")));
        } catch (IOException e) {
            log.error("", e);
        }
    }

    /**
     * 台湾顺丰推送出库单(出库反馈) void
     * 
     * @throws
     */
    public void saleOrderOutboundPushService() {
        if (log.isInfoEnabled()) {
            log.info("SFTW====> sale order outbound push service start...");
        }
        // System.out.println("sale request is occur!");
        // System.out.println(logistics_interface);
        log.error("SFTW====> sale order outbound push service invoke start,logistics_interface is:[{}], data_digest is:[{}]", logistics_interface, data_digest);
        String responseXml = taskSfTwManger.receiveOutboundOrderPushService(logistics_interface, data_digest);
        log.error("SFTW====> sale order outbound push service invoke end,logistics_interface is:[{}], response is:[{}]", logistics_interface, responseXml);
        // System.out.println(responseXml);
        responseMsg(responseXml);
        // System.out.println("sale response is send");
        if (log.isInfoEnabled()) {
            log.info("SFTW====> sale order outbound push service end");
        }
    }

    /**
     * 台湾顺丰库存变化接口（出入库，库存调整） void
     * 
     * @throws
     */
    public void inventoryChangePushService() {
        if (log.isInfoEnabled()) {
            log.info("SFTW====> inventory change push service start...");
        }
        System.out.println("inv change request is occur!");
        System.out.println(logistics_interface);
        /*
         * String responseXml =
         * taskSfTwManger.receiveInventoryChangePushService(logistics_interface, data_digest);
         * System.out.println(responseXml); responseMsg(responseXml);
         */
        System.out.println("inv change response is send");
        if (log.isInfoEnabled()) {
            log.info("SFTW====> inventory change push service end");
        }
    }

    public String getLogistics_interface() {
        return logistics_interface;
    }

    public void setLogistics_interface(String logisticsInterface) {
        logistics_interface = logisticsInterface;
    }

    public String getData_digest() {
        return data_digest;
    }

    public void setData_digest(String dataDigest) {
        data_digest = dataDigest;
    }

}
