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
package com.jumbo.wms.vmi;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.jumbo.dao.vmi.espData.ESPDeliveryDao;
import com.jumbo.dao.warehouse.StvLineDao;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.daemon.EspritTask;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.vmi.espData.ESPOrderManager;
import com.jumbo.wms.manager.vmi.espData.ESPReceiveDeliveryManager;
import com.jumbo.wms.model.command.vmi.esprit.xml.espDispatch.EspDispatch;
import com.jumbo.wms.model.command.vmi.esprit.xml.order.EspritOrderRootXml;
import com.jumbo.wms.model.warehouse.StvLineCommand;

/**
 * @author lichuan
 *
 */
@ContextConfiguration(locations = {"classpath*:loxia-hibernate-context.xml", "classpath*:loxia-service-context.xml", "classpath*:spring.xml", "classpath*:spring-test-*.xml"})
public class EspritTest extends AbstractJUnit4SpringContextTests {
    @Autowired
    EspritTask espTask;
    @SuppressWarnings("unused")
    @Autowired
    private ESPDeliveryDao espDeliveryDao;
    @Autowired
    private StvLineDao stvLineDao;
    @Autowired
    private ESPReceiveDeliveryManager espRDeliveryManager;
    @Autowired
    private ESPOrderManager espOrderManager;
    
    @Test
    public void saveOrderFileToTableTest() throws IOException{
        String filePath = "D:\\baozun\\work\\esprit\\esp\\4046655000480_4046655000664_espOrder_1052.xml";
        String backPath = "D:\\baozun\\work\\esprit\\esp\\backup";
        File file = new File(filePath);
        EspritOrderRootXml root = unmarshallerEspOrder(file);
        espOrderManager.saveEspritOrderData(root, file, backPath);
    }
    
    @Test
    public void downloadEspritDispatchInfo(){
        //espTask.downloadEspritDispatchInfo();
        try {
            String filePath = "D:\\baozun\\work\\esprit\\esprit\\4046655000480_4046655000664_espDispatch_1213.xml";
            String backPath = "D:\\baozun\\work\\esprit\\esprit\\backup";
            File file = new File(filePath);
            EspDispatch root = unmarshallerEspritDispatchDate(file);
            espRDeliveryManager.saveEspritDispatchDate(root, file, backPath);
        } catch (Exception e) {
        } 
    }
    
    @Test
    public void generateEspritDispatchSta(){
        espTask.generateEspritDispatchSta();
    }
    
    @Test
    public void execute(){
//        String staCode = "R600000933761";
//        espDeliveryDao.updateStatusByStaCode(ESPDelivery.STATUS_END, staCode);
        //espRDeliveryManager.updateReceiveOrdersStatus(null, "1", "I201501081627", null);
        //espRDeliveryManager.inOutBoundRtn();
        //espRDeliveryManager.inOutBoundRtn();
        espRDeliveryManager.inBoundCloseRtn();
        //String staCode = "H600000952012";
        //espRDeliveryManager.inOutBoundDeliveryRtn(staCode);
    }
    
    @Test
    public void inBoundClose(){
        //espRDeliveryManager.inBoundCloseRtn();
        String staCode = "H600006954824";
        espRDeliveryManager.inBoundDeliveryCloseRtn(staCode);
    }
    
    @Test
    public void dealDuplicate() {
        Long staId = 13894999L;
        List<StvLineCommand> stvLines = stvLineDao.findOutboundStvLinesByStaId(staId, new BeanPropertyRowMapper<StvLineCommand>(StvLineCommand.class));
        //合并重复明细
        List<StvLineCommand> sls = new ArrayList<StvLineCommand>();
        for (StvLineCommand sc : stvLines) {
            StvLineCommand cmd = sc;
            if (sls.size() > 0) {
                boolean isExist = false;
                String extCode2 = cmd.getExtCode2();
                if (StringUtil.isEmpty(extCode2)) {
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                }
                ListIterator<StvLineCommand> iter = sls.listIterator();
                while (iter.hasNext()) {
                    StvLineCommand c = iter.next();
                    if (!StringUtils.isEmpty(extCode2)) {
                        if (extCode2.equals(c.getExtCode2())) {
                            isExist = true;
                            c.setQuantity(c.getQuantity() + cmd.getQuantity());
                            iter.set(c);
                            break;
                        }
                    }
                }
                if (false == isExist) {
                    sls.add(cmd);
                }
            } else {
                sls.add(cmd);
            }
        }
        System.out.println(sls.size());
        for (StvLineCommand c : sls) {
            System.out.println(c.getExtCode2() + ":" + c.getQuantity());
        }
    }
    
    private EspritOrderRootXml unmarshallerEspOrder(File file) {
        try {
            JAXBContext cxt = JAXBContext.newInstance(EspritOrderRootXml.class);
            Unmarshaller unmarshaller = cxt.createUnmarshaller();
            return (EspritOrderRootXml) unmarshaller.unmarshal(file);
        } catch (Exception e) {
            return null;
        }
    }
    
    private EspDispatch unmarshallerEspritDispatchDate(File file) {
        try {
            JAXBContext cxt = JAXBContext.newInstance(EspDispatch.class);
            Unmarshaller unmarshaller = cxt.createUnmarshaller();
            return (EspDispatch) unmarshaller.unmarshal(file);
        } catch (Exception e) {
//            String fileName = (null != file ? file.getName() : "");
            //log.error("====>Esprit dispatch xml source file [" + fileName + "] transform to java bean error:", e);
            return null;
        }
    }

}
