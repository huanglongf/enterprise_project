package com.jumbo.wms.manager.task.NikeCartonNo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.baozun.scm.customize.nike.manager.NikeCartonNoManager;
import com.baozun.scm.customize.nike.model.CartonNoConfirm;
import com.baozun.scm.customize.nike.model.CartonNoResponse;
import com.baozun.scm.customize.nike.model.TransSkuItem;
import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.nikeLogistics.NikeCartonNoDao;
import com.jumbo.dao.nikeLogistics.NikeCartonNoLineDao;
import com.jumbo.dao.nikeLogistics.NikeCartonNoLineLogDao;
import com.jumbo.dao.nikeLogistics.NikeCartonNoLogDao;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.nikeLogistics.NikeCartonNo;
import com.jumbo.wms.model.nikeLogistics.NikeCartonNoLine;
import com.jumbo.wms.model.nikeLogistics.NikeCartonNoLineLog;
import com.jumbo.wms.model.nikeLogistics.NikeCartonNoLog;


public class NikeCartonNoTaskImpl extends BaseManagerImpl implements NikeCartonNoTask {

    /**
     * 
     */
    private static final long serialVersionUID = -5770425424614347183L;
    @Autowired
    NikeCartonNoDao cartonNoDao;
    @Autowired
    NikeCartonNoLineDao cartonNoLineDao;
    @Autowired
    NikeCartonNoManager nikeCartonNoManager;
    @Autowired
    NikeCartonNoLogDao cartonNoLogDao;
    @Autowired
    NikeCartonNoLineLogDao cartonNoLineLogDao;

    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void sendNikeCartonNo() {
        log.info("<<<<<<<<<<<<<<<<<sendNikeCartonNo");
        List<NikeCartonNo> cartonNos = cartonNoDao.queryNikeCartonNo();
        List<CartonNoConfirm> cartonNoConfirms = new ArrayList<CartonNoConfirm>();
        for (NikeCartonNo nikeCartonNo : cartonNos) {
            CartonNoConfirm cartonNoConfirm = new CartonNoConfirm();
            cartonNoConfirm.setAOID(nikeCartonNo.getAoId());
            cartonNoConfirm.setCartonNo(nikeCartonNo.getCartonNo());
            cartonNoConfirm.setCity(nikeCartonNo.getCity());
            cartonNoConfirm.setCNID(nikeCartonNo.getCNID());
            cartonNoConfirm.setConsumer(nikeCartonNo.getConsumer());
            cartonNoConfirm.setConsumerAddress(nikeCartonNo.getConsumerAddress());
            cartonNoConfirm.setConsumerTel(nikeCartonNo.getConsumerTel());
            cartonNoConfirm.setIsCod(nikeCartonNo.getIsCod());
            cartonNoConfirm.setCPPcode(nikeCartonNo.getCPPcode());
            cartonNoConfirm.setCPPtype(nikeCartonNo.getCPPtype());
            cartonNoConfirm.setDistrict(nikeCartonNo.getDistrict());
            cartonNoConfirm.setIsCPP(nikeCartonNo.getIsCPP());
            cartonNoConfirm.setProvince(nikeCartonNo.getProvince());
            cartonNoConfirm.setServiceLevel(nikeCartonNo.getServiceLevel());
            cartonNoConfirm.setTrackingNo(nikeCartonNo.getTrackingNo());
            cartonNoConfirm.setTrackingType(nikeCartonNo.getTrackingType());
            List<TransSkuItem> skuItems = new ArrayList<TransSkuItem>();
            List<NikeCartonNoLine> cartonNoLines = cartonNoLineDao.findByCartonNoId(nikeCartonNo.getId());
            if (cartonNoLines != null && cartonNoLines.size() > 0) {
                for (NikeCartonNoLine nikeCartonNoLine : cartonNoLines) {
                    TransSkuItem skuItem = new TransSkuItem();
                    skuItem.setColor(nikeCartonNoLine.getColor());
                    skuItem.setQty(nikeCartonNoLine.getQty() == null ? null : nikeCartonNoLine.getQty().intValue());
                    skuItem.setStyle(nikeCartonNoLine.getStyle());
                    skuItem.setUPC(nikeCartonNoLine.getUPC());
                    skuItems.add(skuItem);
                }

            }
            cartonNoConfirm.setSKUList(skuItems);
            cartonNoConfirms.add(cartonNoConfirm);
        }
        List<CartonNoResponse> cartonNoResponses = nikeCartonNoManager.saveNikeOutboundOrder(cartonNoConfirms);
        for (CartonNoResponse cartonNoResponse : cartonNoResponses) {
            if (cartonNoResponse.getStatus().equals(1)) {
                NikeCartonNo cartonNo = cartonNoDao.findByTrackingNo(cartonNoResponse.getTrackingNo());
                NikeCartonNoLog cartonNoLog = new NikeCartonNoLog();
                cartonNoLog.setAoId(cartonNo.getAoId());
                cartonNoLog.setCartonNo(cartonNo.getCartonNo());
                cartonNoLog.setCity(cartonNo.getCity());
                cartonNoLog.setCNID(cartonNo.getCNID());
                cartonNoLog.setConsumer(cartonNo.getConsumer());
                cartonNoLog.setConsumerAddress(cartonNo.getConsumerAddress());
                cartonNoLog.setIsCod(cartonNo.getIsCod());
                cartonNoLog.setConsumerTel(cartonNo.getConsumerTel());
                cartonNoLog.setCPPcode(cartonNo.getCPPcode());
                cartonNoLog.setCPPtype(cartonNo.getCPPtype());
                cartonNoLog.setCreateTime(new Date());
                cartonNoLog.setDistrict(cartonNo.getDistrict());
                cartonNoLog.setIsCPP(cartonNo.getIsCPP());
                cartonNoLog.setProvince(cartonNo.getProvince());
                cartonNoLog.setServiceLevel(cartonNo.getServiceLevel());
                cartonNoLog.setTrackingNo(cartonNo.getTrackingNo());
                cartonNoLog.setTrackingType(cartonNo.getTrackingType());
                cartonNoLogDao.save(cartonNoLog);
                List<NikeCartonNoLine> cartonNoLine = cartonNoLineDao.findByCartonNoId(cartonNo.getId());
                for (NikeCartonNoLine nikeCartonNoLine : cartonNoLine) {
                    NikeCartonNoLineLog cartonNoLineLog = new NikeCartonNoLineLog();
                    cartonNoLineLog.setCartonNo(cartonNo);
                    cartonNoLineLog.setColor(nikeCartonNoLine.getColor());
                    cartonNoLineLog.setQty(nikeCartonNoLine.getQty());
                    cartonNoLineLog.setSize(nikeCartonNoLine.getSize());
                    cartonNoLineLog.setStyle(nikeCartonNoLine.getStyle());
                    cartonNoLineLog.setUPC(nikeCartonNoLine.getUPC());
                    cartonNoLineLogDao.save(cartonNoLineLog);
                    cartonNoLineDao.delete(nikeCartonNoLine);

                }
                cartonNoDao.delete(cartonNo);
            } else {
                NikeCartonNo cartonNo = cartonNoDao.findByTrackingNo(cartonNoResponse.getTrackingNo());
                if (cartonNo != null) {
                    cartonNo.setStatus(0);
                    cartonNoDao.save(cartonNo);
                    cartonNoDao.flush();
                }
            }
        }
        log.info("sendNikeCartonNo>>>>>>>>>>");
    }
}
