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

import java.math.BigInteger;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.jumbo.dao.warehouse.WhTransProvideNoDao;
import com.jumbo.wms.daemon.TtkOrderTask;
import com.jumbo.wms.daemon.ZtoOrderTask;
import com.jumbo.wms.manager.task.TaskOmsOutManager;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.warehouse.WhTransProvideNo;

/**
 * @author lichuan
 * 
 */
@ContextConfiguration(locations = {"classpath*:loxia-hibernate-context.xml", "classpath*:loxia-service-context.xml", "classpath*:spring.xml"})
public class TtkOrderTaskTest extends AbstractJUnit4SpringContextTests {
    @Autowired
    TtkOrderTask ttkOrderTask;
    @Autowired
    ZtoOrderTask ztoOrderTask;
    @Autowired
    private WhTransProvideNoDao transProvideNoDao;
    @Autowired
    private TaskOmsOutManager taskOmsOutManager;

    @Test
    public void ttkTransNo() {
        ttkOrderTask.ttkTransNo();
    }
    
    @Test
    public void inertTransNo(){
      String tnSegements = "998006295902-998006296001";
      String[] tns = tnSegements.split("-");
      if (2 == tns.length) {
          BigInteger from = new BigInteger(tns[0]);
          BigInteger to = new BigInteger(tns[1]);
          for (BigInteger i = from; i.compareTo(to) != 1; i = i.add(new BigInteger("1"))) {
              try {// 运单号已存在则跳过
                  WhTransProvideNo whTransProvideNo = new WhTransProvideNo();
                  whTransProvideNo.setLpcode(Transportator.TTKDEX);
                  whTransProvideNo.setTransno(i.toString());
                  transProvideNoDao.save(whTransProvideNo);
              } catch (Exception e) {}
          }
      }
    }
    
    @Test
    public void runThreadOutBound(){
        taskOmsOutManager.threadOutBound();
    }
    
    @Test
    public void reUsedTransNo(){
        System.out.println("");
        ztoOrderTask.reUsedTransNo();
    }
    
    @Test
    public void setTransNoByWarehouse(){
        ttkOrderTask.setTransNoByWarehouse();
    }
    
    @Test
    public void exeTtkConfirmOrderQueue(){
        ttkOrderTask.exeTtkConfirmOrderQueue();
    }
}
