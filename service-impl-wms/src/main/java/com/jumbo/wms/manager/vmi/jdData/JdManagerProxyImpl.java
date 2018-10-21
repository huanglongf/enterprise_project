package com.jumbo.wms.manager.vmi.jdData;


import java.util.Date;

import loxia.dao.support.BeanPropertyRowMapperExt;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.JdConfirmOrderQueueDao;
import com.jumbo.dao.warehouse.JdConfirmOrderQueueLogDao;
import com.jumbo.dao.warehouse.WhTransProvideNoDao;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.warehouse.JdConfirmOrderQueue;
import com.jumbo.wms.model.warehouse.JdConfirmOrderQueueLog;
import com.jumbo.wms.model.warehouse.WhTransProvideNo;

@Service("jdManagerProxy")
public class JdManagerProxyImpl extends BaseManagerImpl implements JdManagerProxy {

    /**
	 * 
	 */
    private static final long serialVersionUID = -4956242074106420040L;
    @Autowired
    private BiChannelDao biChannelDao;
    @Autowired
    private WhTransProvideNoDao whTransProvideNoDao;
    @Autowired
    private JdConfirmOrderQueueDao jdConfirmOrderQueueDao;
    @Autowired
    private JdConfirmOrderQueueLogDao logDao;

    @Override
    public void receiveBillCodeMq(String message) {
        if(log.isDebugEnabled()){
            log.debug("=========GUESS ProductMaster START===========");    
        }
        try {
            JSONObject jobj = JSONObject.fromObject(message == null ? "{}" : message);
            String result = jobj.getString("result");
            String omsShopId = jobj.getString("omsShopId");
            if (result.equals("1")) {
                String responseBody = jobj.getString("responseBody");
                JSONObject jobjresponseBody = JSONObject.fromObject(responseBody == null ? "{}" : responseBody);
                String jd = jobjresponseBody.getString("jingdong_etms_waybillcode_get_responce");
                JSONObject jobjjd = JSONObject.fromObject(jd == null ? "{}" : jd);
                String resultInfo = jobjjd.getString("resultInfo");
                JSONObject jobresultInfo = JSONObject.fromObject(resultInfo == null ? "{}" : resultInfo);
                String deliveryIdList = null;
                try {
                    deliveryIdList = jobresultInfo.getString("deliveryIdList");
                }catch (Exception e){
                    if(log.isDebugEnabled()){
                        log.debug("data formate error,message : {}",message);    
                    }
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                }
                deliveryIdList = deliveryIdList.replace("[", "");
                deliveryIdList = deliveryIdList.replace("]", "");
                BiChannel biChannel = biChannelDao.getByPrimaryKey(Long.parseLong(omsShopId));
                String transno[] = deliveryIdList.split(",");
                for (int j = 0; j < transno.length; j++) {
                    WhTransProvideNo whTransProvideNo = new WhTransProvideNo();
                    whTransProvideNo.setLpcode("JD");
                    whTransProvideNo.setTransno(transno[j].replace("\"", ""));
                    whTransProvideNo.setOwner(biChannel.getCode());
                    Date createTime = new Date();
                    whTransProvideNo.setCreateTime(createTime);
                    // 75天后过期
                    long cTime = createTime.getTime();
                    long eTime = cTime + 1000 * 60 * 60 * 24 * 75L;
                    Date expTime = new Date(eTime);
                    whTransProvideNo.setExpTime(expTime);
                    whTransProvideNoDao.save(whTransProvideNo);
                }
            }
            if(log.isDebugEnabled()){
                log.debug("=========GUESS ProductMaster ENDs===========");
            }
        } catch (BusinessException e) {
            log.info("receiveBillCodeMq error,error code : {}",e.getErrorCode());
        } catch (Exception e) {
            log.error("", e);
        }

    }

    @Override
    public void receiveBillOrderMq(String message) {
        log.debug("获取同步信息");
        JSONObject jobj = JSONObject.fromObject(message == null ? "{}" : message);
        String transno = jobj.getString("deliveryId");
        JdConfirmOrderQueue jds = jdConfirmOrderQueueDao.findTransNoOrder(transno, new BeanPropertyRowMapperExt<JdConfirmOrderQueue>(JdConfirmOrderQueue.class));
        String result = jobj.getString("result");
        if (result.equals("-1")) {
            String errorMsg = jobj.getString("errorMessage");
            jds.setErrorMsg(errorMsg);
            jdConfirmOrderQueueDao.save(jds);
        } else {
            if (jds != null) {
                JdConfirmOrderQueueLog log = new JdConfirmOrderQueueLog();
                log.setCreateTime(jds.getCreateTime());
                log.setHeight(jds.getHeight());
                log.setLength(jds.getLength());
                log.setStaCode(jds.getStaCode());
                log.setTransno(jds.getTransno());
                log.setWeight(jds.getWeight());
                log.setWhight(jds.getWhight());
                log.setLogCreateTime(new Date());
                logDao.save(log);
                jdConfirmOrderQueueDao.delete(jds);
            }
        }

    }

}
