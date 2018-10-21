package com.jumbo.webservice.zto;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.baozun.bh.util.JSONUtil;

import com.jumbo.wms.Constants;

import com.jumbo.wms.model.warehouse.WhTransProvideNoCommand;
import com.jumbo.wms.model.warehouse.ZtoConfirmOrderQueue;
import com.jumbo.util.HttpClientUtil;
import com.jumbo.util.JsonUtil;
import com.jumbo.util.zip.AppSecretUtil;
import com.jumbo.webservice.sfNew.SfOrderWebserviceClient;

public final class ZtoOrderClient {

    protected static final Logger logger = LoggerFactory.getLogger(SfOrderWebserviceClient.class);

    public static boolean comfirmOrder(ZtoConfirmOrderQueue order, ZtoCommand zc,String ztoUserName,String ztoPassword) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowdate = sdf.format(new Date());
        boolean flag;
        try {
            String ztocmd = JsonUtil.obj2jsonStrIncludeAll(zc);
            String content = new String(Base64.encodeBase64(ztocmd.getBytes("UTF-8")), "UTF-8");
            String verify = AppSecretUtil.getMD5Str(ztoUserName + nowdate + content + ztoPassword);
            Map<String, String> params = new HashMap<String, String>();
            params.put("style", "json");
            params.put("func", "order.submit");
            params.put("partner", ztoUserName);
            params.put("datetime", nowdate);
            params.put("content", content);
            params.put("verify", verify);
            String rtn = HttpClientUtil.httpPost(Constants.ZTO_INTERFACE_URL, params);
            logger.error("ZTO interface return Message:" + rtn + "----------------------");
            WhTransProvideNoCommand wtp1 = JSONUtil.jsonToBean(rtn, WhTransProvideNoCommand.class);
            flag = wtp1.isResult();
            return flag;
        } catch (Exception e) {
            flag = false;
            logger.error("ZTO comfirmOrder interface error----------------------");
            logger.error("", e);
            return flag;
        }
    }

    public static WhTransProvideNoCommand getZtoTransNo(String lastNo,String ztoUserName,String ztoPassword) throws UnsupportedEncodingException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowdate = sdf.format(new Date());
        logger.error("lastno:" + lastNo + "-----------------");
        String content = new String(Base64.encodeBase64(("{\"number\":\"1000\",\"lastno\":\"" + lastNo + "\"}").getBytes("UTF-8")), "UTF-8");
        String verify = AppSecretUtil.getMD5Str(ztoUserName + nowdate + content + ztoPassword);
        Map<String, String> params = new HashMap<String, String>();
        params.put("style", "json");
        params.put("func", "mail.apply");
        params.put("partner", ztoUserName);
        params.put("datetime", nowdate);
        params.put("content", content);
        params.put("verify", verify);
        logger.error("Get trans no from Zto,interface begin------------------------------");
        String rtn = HttpClientUtil.httpPost(Constants.ZTO_INTERFACE_URL, params);
        logger.error("Get trans no from Zto,result is:" + rtn);
        return JSONUtil.jsonToBean(rtn, WhTransProvideNoCommand.class);
    }

}
