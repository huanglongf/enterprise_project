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
package com.jumbo.manager;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.jumbo.wms.manager.warehouse.InventoryOccupyManager;
import com.jumbo.wms.model.warehouse.StockTransApplication;

/**
 * @author lichuan
 *
 */
@ContextConfiguration(locations = {"classpath*:loxia-hibernate-context.xml", "classpath*:loxia-service-context.xml", "classpath*:spring.xml", "classpath*:spring-test-*.xml"})
public class InvOccupyTest extends AbstractJUnit4SpringContextTests {
    @Autowired
    private InventoryOccupyManager inventoryOccupyManager;
    
    @Test
    public void occupyTest(){
        StockTransApplication sta = new StockTransApplication();
        sta.setId(5218625l);
        sta.setIsMerge(false);
        inventoryOccupyManager.inventoryOccupyCommon(sta, null, true);
    }

}
