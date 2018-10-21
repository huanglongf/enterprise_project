package com.bt.interf.sf.impl;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;

import com.bt.common.util.SfIntraCityCommunicationUtil;
import com.bt.interf.sf.SfIntraCityService;
import com.bt.orderPlatform.model.sfIntraCity.IntraCityCancelorder;
import com.bt.orderPlatform.model.sfIntraCity.IntraCityCommonElement;
import com.bt.orderPlatform.model.sfIntraCity.IntraCityCreateorder;
import com.bt.orderPlatform.model.sfIntraCity.IntraCityListorderfeed;
import com.bt.orderPlatform.model.sfIntraCity.IntraCityResultMsg;
import com.bt.orderPlatform.model.sfIntraCity.ResultListorderfeed;
import com.google.gson.Gson;


@Service
public class SfIntraCityServiceImpl implements SfIntraCityService{
    
    private static  Logger logger = Logger.getLogger(SfIntraCityServiceImpl.class);

    /**创建订单url**/
    public String url_createorder = "/open/api/external/createorder";
    
    /**取消订单url**/
    public String url_cancelorder = "/open/api/external/cancelorder";
    
    /**订单状态查询url**/
    public String url_listorderfeed = "/open/api/external/listorderfeed";
    

    @Override
    public IntraCityResultMsg<T> SfIntraCityRequest(IntraCityCommonElement data,String url){
        
        Long time = System.currentTimeMillis() / 1000;
        data.setDev_id(TC_APP_ID);
        data.setPush_time(time);//推送时间     
        
        Gson gson = new Gson();
        String dataStr = gson.toJson(data);
        
        String sign = SfIntraCityCommunicationUtil.generateSign(dataStr, TC_APP_ID, TC_APP_KEY);        
        IntraCityResultMsg cityResultMsg = new IntraCityResultMsg();
        try{
            String resultMsg = SfIntraCityCommunicationUtil.request(TC_SERVER_HOST + url, dataStr , sign);
            cityResultMsg =  gson.fromJson(resultMsg,new IntraCityResultMsg<T>(){}.getClass());
        }catch (IOException e){
            e.printStackTrace();
            logger.error(e);
        }
        return cityResultMsg;
    }
    
    @Override
    public IntraCityResultMsg Createorder(IntraCityCreateorder intraCityCreateorder){
        
        Long time = System.currentTimeMillis() / 1000;
        intraCityCreateorder.setOrder_time(time);//下单时间
         
        //intraCityCreateorder.setLbs_type(1);//1 百度 2高德 ；不填默认高德
        intraCityCreateorder.setVersion(TC_SERVER_VERSION);
        intraCityCreateorder.setOrder_source(TC_ORDER_SOURCE);
        intraCityCreateorder.setPay_type(1);//1、在线支付 0、货到付款
        intraCityCreateorder.setIs_appoint(0);//0、非预约单；1、预约单

        return SfIntraCityRequest(intraCityCreateorder, url_createorder);
    }

    @Override
    public IntraCityResultMsg Cancelorder(IntraCityCancelorder intraCityCancelorder){
        
        return SfIntraCityRequest(intraCityCancelorder, url_cancelorder);
    }
    

    @Override
    public IntraCityResultMsg Listorderfeed(IntraCityListorderfeed intraCityListorderfeed){

        return SfIntraCityRequest(intraCityListorderfeed, url_listorderfeed);  
    }


}
