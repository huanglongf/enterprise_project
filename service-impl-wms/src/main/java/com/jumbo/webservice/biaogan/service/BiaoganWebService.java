package com.jumbo.webservice.biaogan.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.mq.MarshallerUtil;
import com.jumbo.webservice.biaogan.command.InOutBoundResponse;
import com.jumbo.webservice.biaogan.command.RtnOutBoundCommand;
import com.jumbo.webservice.biaogan.manager.InOutBoundManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerProxy;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrder;

/**
 * Please modify this class to meet your needs This class is not complete
 */

@WebService(serviceName = "BiaoganService", targetNamespace = "http://www.jumbomart.cn/webservice/")
public class BiaoganWebService {

    protected static final Logger log = LoggerFactory.getLogger(BiaoganWebService.class);

    @Autowired
    private WareHouseManagerProxy wareHouseManagerProxy;

    @Autowired
    private InOutBoundManager inoutManager;

    public String outboundToBz(@WebParam(name = "request") String xml) {
        log.debug("==========biaogan outboundToBz====================");
        log.debug(xml);
        RtnOutBoundCommand rtnOutBound = (RtnOutBoundCommand) MarshallerUtil.buildJaxb(RtnOutBoundCommand.class, xml);
        String respXml = wareHouseManagerProxy.outboundToBzProxy(rtnOutBound);
        return respXml;

    }

    public String inboundToBz(@WebParam(name = "request") String xml) {
        List<MsgRtnInboundOrder> rntorderList = null;
        try {
            InOutBoundResponse response = new InOutBoundResponse();
            response.setSuccess("true");
            response.setDesc("");
            rntorderList = inoutManager.createMsgRtnInboundLine(xml);
            wareHouseManagerProxy.inboundToBz(rntorderList);
            return MarshallerUtil.buildJaxb(response);
        } catch (Exception e) {
            InOutBoundResponse response = new InOutBoundResponse();
            response.setDesc("error!");
            response.setSuccess("false");
            log.error("",e);
            return MarshallerUtil.buildJaxb(response);
        }
    }
}
