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
package com.jumbo.wms.rmi;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.card.CardClientFactory;
import com.jumbo.wms.manager.warehouse.card.CardClientManager;
import com.jumbo.wms.manager.warehouse.card.CardResult;

/**
 * @author lichuan
 * 
 */
@ContextConfiguration(locations = {"classpath*:loxia-hibernate-context.xml", "classpath*:loxia-service-context.xml", "classpath*:spring.xml"})
public class StarbucksClientTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private CardClientFactory clientFactory;

    @Test
    public void unlockCardTest() {
        Integer interfaceType = 3;
        String sn = "7310180000316807";
        CardClientManager client = clientFactory.getClient(interfaceType);
        CardResult rt = client.unlockCard(sn);
        if (null == rt) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        } else if (CardResult.STATUS_SUCCESS != rt.getStatus()) {
            System.out.println(rt.getErrorCode()  + "  " + rt.getStatus() + " " + rt.getMsg());
            throw new BusinessException(rt.getMsg());
        }else{
            System.out.println(rt.getErrorCode()  + "  " + rt.getStatus() + " " + rt.getMsg());
        }
    }

}
