package com.jumbo.webservice.sfOrder.service;

import javax.jws.WebParam;
import javax.jws.WebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerProxy;
import com.jumbo.wms.model.warehouse.SfOrderFilterLog;
import com.jumbo.mq.MarshallerUtil;
import com.jumbo.webservice.sfOrder.command.OrderFilterResponse;
import com.jumbo.webservice.sfOrder.command.OrderFilterResult;

/**
 * Please modify this class to meet your needs This class is not complete
 */

@WebService(serviceName = "SfService", targetNamespace = "http://www.jumbomart.cn/webservice/")
public class SfWebService {
    protected static final Logger log = LoggerFactory.getLogger(SfWebService.class);

    @Autowired
    private WareHouseManagerProxy wareHouseManagerProxy;
    @Autowired
    private WareHouseManager wareHouseManager;

    public String orderFilter(@WebParam(name = "request") String xml) {
        try {
            OrderFilterResult rs = (OrderFilterResult) MarshallerUtil.buildJaxb(OrderFilterResult.class, xml);
            SfOrderFilterLog lg = wareHouseManagerProxy.saveSfOrderFilter(rs);
            if (!lg.getIsAccept()) {
                try {
                    wareHouseManager.sfOrderFilterAccept(lg);
                } catch (Exception e) {
                    log.error("",e);
                }
            }
            OrderFilterResponse res = new OrderFilterResponse();
            res.setFlag(OrderFilterResponse.FLAG_SUCCESS);
            return MarshallerUtil.buildJaxb(res);
        } catch (Exception e) {
            log.error("",e);
            OrderFilterResponse res = new OrderFilterResponse();
            res.setFlag(OrderFilterResponse.FLAG_SUCCESS);
            return MarshallerUtil.buildJaxb(res);
        }

    }
}
