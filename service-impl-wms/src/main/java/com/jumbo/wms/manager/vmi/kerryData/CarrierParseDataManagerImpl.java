package com.jumbo.wms.manager.vmi.kerryData;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.baozun.bh.connector.model.ConnectorMessage;
import cn.baozun.bh.connector.ms.model.kerry.CarrierConsignment;
import cn.baozun.bh.connector.ms.model.kerry.CarrierConsignment.CarrierConsignmentInformation.Informations;
import cn.baozun.bh.util.JSONUtil;
import cn.baozun.bh.util.ZipUtil;

import com.jumbo.dao.vmi.kerry.CarrierConsignmentDataDao;
import com.jumbo.wms.model.vmi.kerry.CarrierConsignmentData;

@Service("carrierParseDataManager")
public class CarrierParseDataManagerImpl implements CarrierParseDataManager {

    protected static final Logger log = LoggerFactory.getLogger(CarrierParseDataManagerImpl.class);
    @Autowired
    private CarrierConsignmentDataDao carrierConsignmentDataDao;


    @Override
    @Transactional
    public void receiveCarrierConsignmentByMq(String message) {
        CarrierConsignmentData carrierConsignmentData = null;
        try {
            ConnectorMessage connectorMessage = JSONUtil.jsonToBean(message, ConnectorMessage.class);
            // String confirmId = connectorMessage.getConfirmId();
            String messageContent = connectorMessage.getMessageContent();
            if (connectorMessage.getIsCompress()) {
                messageContent = ZipUtil.decompress(messageContent);
            }
            CarrierConsignment carrierConsignment = JSONUtil.jsonToBean(messageContent, CarrierConsignment.class);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            for (Informations informations : carrierConsignment.getCarrierConsignmentInformation().getInformations()) {
                carrierConsignmentData = new CarrierConsignmentData();
                carrierConsignmentData.setOrderNo(informations.getOrderNo());
                carrierConsignmentData.setCarrierCode(informations.getCarrierNumber());
                carrierConsignmentData.setCarrierNumber(informations.getCarrierCode());
                carrierConsignmentData.setShippingTime(sdf.parse(informations.getShippingTime()));
                carrierConsignmentData.setWeight(informations.getWeight());
                carrierConsignmentData.setCreateDate(new Date());
                carrierConsignmentData.setStatus(0);
                carrierConsignmentDataDao.save(carrierConsignmentData);
            }
        } catch (Exception ex) {
            if (log.isErrorEnabled()) {
                log.error("Carrier receiveCarrierConsignmentByMq Exception:" + message, ex);
            }
        }
    }

}
