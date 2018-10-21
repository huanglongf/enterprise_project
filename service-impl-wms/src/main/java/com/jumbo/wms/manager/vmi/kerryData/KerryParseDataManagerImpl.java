package com.jumbo.wms.manager.vmi.kerryData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// import cn.baozun.bh.connector.model.kerry.CarrierConsignment;
// import
// cn.baozun.bh.connector.model.kerry.CarrierConsignment.CarrierConsignmentInformation.Informations;

public class KerryParseDataManagerImpl implements KerryParseDataManager {
    protected static final Logger log = LoggerFactory.getLogger(KerryParseDataManagerImpl.class);

    @Override
    public void receiveCarrierConsignmentByMq(String message) {
        // CarrierConsignmentData carrierConsignmentData = null;
        try {
            // CarrierConsignment carrierConsignment = JSONUtil.jsonToBean(message,
            // CarrierConsignment.class);
            // for(Informations informations :
            // carrierConsignment.getCarrierConsignmentInformation().getInformations()){
            // carrierConsignmentData = new CarrierConsignmentData();
            // carrierConsignmentData.setOrderNo(informations.getOrderNo());
            // carrierConsignmentData.setCarrierCode(informations.getCarrierCode());
            // carrierConsignmentData.setCarrierNumber(informations.getCarrierNumber());
            // carrierConsignmentData.setShippingTime(informations.getShippingTime().toGregorianCalendar().getTime());
            // carrierConsignmentData.setWeight(informations.getWeight());
            // carrierConsignmentData.setCreateDate(new Date());
            // carrierConsignmentData.setStatus(0);
            // carrierConsignmentDataDao.save(carrierConsignmentData);
            // }
        } catch (Exception ex) {
            if (log.isErrorEnabled()) {
                log.error("Kerry receiveCarrierConsignmentByMq Exception:" + message, ex);
            }
        }
    }

}
