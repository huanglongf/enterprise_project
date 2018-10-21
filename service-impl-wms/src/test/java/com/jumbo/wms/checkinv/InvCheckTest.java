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
 * 
 */
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
 * 
 */
package com.jumbo.wms.checkinv;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jumbo.wms.manager.hub2wms.HubWmsService;
import com.jumbo.wms.model.warehouse.StockTransApplication;

/**
 * 
 * @author hui.li
 * 
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:loxia-hibernate-context.xml", "classpath*:loxia-service-context.xml", "classpath*:spring.xml"})
public class InvCheckTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private HubWmsService hubWmsService;


    @Test
    public void testExecute() {

        try {
            hubWmsService.insertOccupiedAndReleaseByCheck(6451008L);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * 退大仓通知ADTB
     * 
     * @param sta
     */
    public void insertTakebackSta(StockTransApplication sta) {
        try {
            // TB店铺code
            // Map<String, String> ownerCode =
            // chooseOptionManager.getOptionByCategoryCode("adidasTBOwner");
            // if (ownerCode != null && ownerCode.get(sta.getOwner()) != null) {
            // AdidasTakebackSta ats = new AdidasTakebackSta();
            // ats.setCreate_time(new Date());
            // ats.setError_count(0);
            // ats.setSlip_code(sta.getRefSlipCode());
            // ats.setSta_code(sta.getCode());
            // ats.setStatus(DefaultStatus.CREATED.getValue());
            // adidasTakebackStaDao.save(ats);
            // }
        } catch (Exception e) {
            // log.error("insertTakebackSta error ", e);
        }
    }

}
