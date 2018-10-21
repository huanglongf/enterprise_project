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
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.vmi.espData.ESPDeliveryDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StvLineDao;
import com.jumbo.manager.TestStaManager;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.daemon.EspritTask;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.vmi.espData.ESPOrderManager;
import com.jumbo.wms.manager.vmi.espData.ESPReceiveDeliveryManager;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.command.vmi.esprit.xml.espDispatch.EspDispatch;
import com.jumbo.wms.model.command.vmi.esprit.xml.order.EspritOrderRootXml;
import com.jumbo.wms.model.warehouse.StvLineCommand;

@ContextConfiguration(locations = {"classpath*:loxia-hibernate-context.xml", "classpath*:loxia-service-context.xml", "classpath*:test/spring-test.xml"})
public class StaTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    TestStaManager testStaManager;

    @Test
    public void getSta() {
        testStaManager.getSta();
    }

}
