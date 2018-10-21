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
package com.jumbo.wms.webservice.ttk;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.jumbo.webservice.ttk.TransBigWordReceiver;
import com.jumbo.webservice.ttk.TransBigWordResponse;
import com.jumbo.webservice.ttk.TtkOrderClient;
import com.jumbo.wms.daemon.TaskSfTw;
import com.jumbo.wms.manager.expressDelivery.logistics.TransOlInterface;
import com.jumbo.wms.manager.mongodb.MongoDBManager;
import com.jumbo.wms.model.warehouse.StaDeliveryInfoCommand;
import com.taobao.api.internal.util.codec.Base64;

/**
 * @author lichuan
 *
 */
@ContextConfiguration(locations = {"classpath*:loxia-hibernate-context.xml", "classpath*:loxia-service-context.xml", "classpath*:spring.xml"})
public class TtkOrderClientTest extends AbstractJUnit4SpringContextTests {
    private static ResourceBundle config = ResourceBundle.getBundle("interface");
    String TTK_SITE = "总部";
    String TTK_CUS = "测试客户";
    String TTK_PASSWORD = "1234";
    String TTK_GET_URL = "http://api.ttk.cn/serverProxy/ProxyActionEx.php"; 
    String TTK_POST_URL = "http://api.ttk.cn:22220/InterfacePlatform/Api";
    String TTK_DIGEST_PARTERNID = "aj7J0pv8Oc";
    @Resource(name = "transOlYto")
    private TransOlInterface yto;
    @Resource(name = "mongoDBManager")
    private MongoDBManager mongoDBManager;
    @Autowired
    private TaskSfTw taskSfTw;
    
    
    public TtkOrderClientTest(){
        TTK_SITE = config.getString("ttk.site");
        TTK_CUS = config.getString("ttk.cus");
        TTK_PASSWORD = config.getString("ttk.password");
        TTK_GET_URL = config.getString("ttk.get.url");
        TTK_POST_URL = config.getString("ttk.post.url");
        TTK_DIGEST_PARTERNID = config.getString("ttk.digest.parternID");
        //生产
        //TTK_SITE = "新篁分部";
        //TTK_CUS = "强生";
        //TTK_PASSWORD = "ttkd00000";
    }

    @Test
    public void getTransNoSegments() throws UnsupportedEncodingException{
        /*TransNoSegmentsResponse res = TtkOrderClient.getTransNoSegments(1,TTK_SITE, TTK_CUS,TTK_PASSWORD,TTK_GET_URL);
        if(null != res){
            System.out.println(res.getResultcode());
            System.out.println(res.getReason());
            System.out.println(res.getData());
        }*/
        //yto.matchingTransNo(21310787L);
        //mongoDBManager.initYtoBigWordIntoMongoDB();
        //defaultTask.createStaForVmiDefault();
        //defaultTask.vmiErrorEmailInform();
        //defaultTask.uploadVmiToHub();
        //VmiRtoDefault vmiRto = vmiRtoDao.getByPrimaryKey(214L);
        //System.out.println(vmiRto.getId());
        //pumaTask.uploadVmiToHubExt();
        //espritTask.generateSta();
        //espritTask.uploadFile();
        //taskSfTw.sendSkuService();
        //taskSfTw.sendInboundOrderService();
        //taskSfTw.sendOutboundOrderService();
        //taskSfTw.inboundOrderRtnService();
        taskSfTw.outboundOrderRtnService();
//        StockTransApplication sta = staDao.getByPrimaryKey(24543098L);//1052696877L,24543097L,24543098L
//        cancelStaManager.cancelStaForSales(sta);
        //espritTask.downloadFile();
        //espritTask.inOutboundEspritDeliveryRtn();
        /*StaDeliveryInfo sd = staDeliveryInfoDao.getByPrimaryKey(17194384L);
        mongoDBManager.matchingPackNo(sd);*/
        /*List<MongoDBYtoBigWord> list =  mongoDBManager.matchingAllPackNo(sd);
        for(MongoDBYtoBigWord bw : list){
            System.out.println(bw.getPackNo() + " " + bw.getDistrict() + " " + bw.getProvince() + " " + bw.getPriority());
        }*/
    }
    
    @Test
    public void getTransBigWordByDeliveryInfo() throws UnsupportedEncodingException{
        List<StaDeliveryInfoCommand> deliveryInfos = new ArrayList<StaDeliveryInfoCommand>();
        StaDeliveryInfoCommand di1 = new StaDeliveryInfoCommand();
        di1.setTrackingNo("K00001");
        di1.setProvince("浙江省");
        di1.setCity("杭州市");
        di1.setDistrict("滨江区");
        StaDeliveryInfoCommand di2 = new StaDeliveryInfoCommand();
        di2.setTrackingNo("K00002");
        di2.setProvince("浙江省");
        di2.setCity("杭州市");
        di2.setDistrict("西湖区");
        StaDeliveryInfoCommand di3 = new StaDeliveryInfoCommand();
        di3.setTrackingNo("K00003");
        di3.setProvince("浙江省");
        di3.setCity("杭州市");
        di3.setDistrict("上城区");
        deliveryInfos.add(di1);
        deliveryInfos.add(di2);
        deliveryInfos.add(di3);
        //PaintMarkerResponse res = TtkOrderClient.getTransBigWordByDeliveryInfo(deliveryInfos,TTK_SITE, TTK_CUS,TTK_PASSWORD,TTK_GET_URL);
        TransBigWordResponse res = TtkOrderClient.postTransBigWordByDeliveryInfo(deliveryInfos,TTK_SITE, TTK_CUS,TTK_PASSWORD,TTK_POST_URL);
        for(TransBigWordReceiver r : res.getData()){
            System.out.println(r.getKey() + " " + r.getResult() + " " + r.getPack());
        }
    }
    
    public static void main(String[] args) {
        //encoding test
        //LghTkEmsD2tbQ3fsIBRcBg==
        String order = "<order></order>123456";
        
        //String digest =new String(Base64.encodeBase64(AppSecretUtil.getMD5Str(order).getBytes()), Charset.forName("GBK"));
        String digest =new String(Base64.encodeBase64(DigestUtils.md5(order.getBytes())), Charset.forName("GBK"));
        System.out.println(digest);
        
    }
}
