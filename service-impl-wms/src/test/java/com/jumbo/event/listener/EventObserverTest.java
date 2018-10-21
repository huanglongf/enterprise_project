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
package com.jumbo.event.listener;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.warehouse.StaInvoiceDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StockTransVoucherDao;
import com.jumbo.dao.wmsInterface.IntfcCfmDao;
import com.jumbo.dao.wmsInterface.IntfcInvoiceCfmDao;
import com.jumbo.dao.wmsInterface.IntfcInvoiceLineCfmDao;
import com.jumbo.dao.wmsInterface.IntfcLineCfmDao;
import com.jumbo.event.TransactionalEvent;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.warehouse.StaInvoice;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.wmsInterface.cfm.IntfcCfm;
import com.jumbo.wms.model.wmsInterface.cfm.IntfcInvoiceCfm;
import com.jumbo.wms.model.wmsInterface.cfm.IntfcInvoiceLineCfm;
import com.jumbo.wms.model.wmsInterface.cfm.IntfcLineCfm;

/**
 * 
 * @author hui.li
 * 
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:loxia-hibernate-context.xml", "classpath*:loxia-service-context.xml", "classpath*:spring.xml"})
public class EventObserverTest extends AbstractJUnit4SpringContextTests {
    @Autowired
    private StockTransApplicationDao staDao;

    @Autowired
    private IntfcCfmDao intfcCfmDao;

    private EventObserver eventObserver;

    @Autowired
    private StockTransVoucherDao stvDao;
    @Autowired
    private OperationUnitDao opDao;
    @Autowired
    private IntfcLineCfmDao intfcLineCfmDao;
    @Autowired
    private IntfcInvoiceCfmDao intfcInvoiceCfmDao;
    @Autowired
    private IntfcInvoiceLineCfmDao intfcInvoiceLineCfmDao;

    @Autowired
    private StaInvoiceDao staInvoiceDao;

    @Before
    public void init() {
        try {
            eventObserver = applicationContext.getBean(EventObserver.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * sta 状态监听
     */
    @Test
    public void onEventNoticeOmsToHub4StaTest() {
        StockTransApplication sta = staDao.getByPrimaryKey(90303571L);
        OperationUnit ou = opDao.getByPrimaryKey(sta.getMainWarehouse().getId());

        eventObserver.onEvent(new TransactionalEvent(sta));

        List<IntfcCfm> icList = intfcCfmDao.getIntfcCfmByStatus(1);

        assertNotNull(icList);

        IntfcCfm ic = null;
        for (IntfcCfm intfcCfm : icList) {
            if (sta.getCode().equals(intfcCfm.getWmsCode())) {
                ic = intfcCfm;
                break;
            }
        }
        assertNotNull(ic);

        List<IntfcLineCfm> ilcList = intfcLineCfmDao.getIntfcLineCfmByIcId(ic.getId());

        List<StaInvoice> invoices = staInvoiceDao.getBySta(sta.getId());
        if (invoices != null && invoices.size() != 0) {
            List<IntfcInvoiceCfm> iicList = intfcInvoiceCfmDao.getIntfcInvoiceCfmByIcId(ic.getId());

            assertNotNull(iicList);
            assertNotNull(iicList.get(0));
            List<IntfcInvoiceLineCfm> iilList = intfcInvoiceLineCfmDao.getIntfcInvoiceLineCfmByIicId(iicList.get(0).getId());

            assertNotNull(iilList);
            assertNotNull(iilList.get(0));
        }


        assertNotNull(ilcList);
        assertNotNull(ilcList.get(0));
        
        assertEquals(sta.getOwner(), ic.getStoreCode());
        assertEquals(ou.getCode(), ic.getWhCode());
        assertEquals(1, ic.getTransactionType().intValue());
        assertEquals(sta.getSkuQty(), ic.getPlanQty());


    }

    /**
     * stv 状态监听
     */
    @Test
    public void onEventNoticeOmsToHub4Test() {
        StockTransVoucher stv = stvDao.getByPrimaryKey(9715449L);
        StockTransApplication sta = staDao.getByPrimaryKey(stv.getSta().getId());
        OperationUnit ou = opDao.getByPrimaryKey(sta.getMainWarehouse().getId());
        eventObserver.onEvent(new TransactionalEvent(stv));

        List<IntfcCfm> icList = intfcCfmDao.getIntfcCfmByStatus(1);

        assertNotNull(icList);

        IntfcCfm ic = null;
        for (IntfcCfm intfcCfm : icList) {
            if (sta.getCode().equals(intfcCfm.getWmsCode())) {
                ic = intfcCfm;
                break;
            }
        }
        assertNotNull(ic);

        List<IntfcLineCfm> ilcList = intfcLineCfmDao.getIntfcLineCfmByIcId(ic.getId());


        assertNotNull(ilcList);
        assertNotNull(ilcList.get(0));

        assertEquals(sta.getOwner(), ic.getStoreCode());
        assertEquals(ou.getCode(), ic.getWhCode());
        assertEquals(0, ic.getTransactionType().intValue());
        assertEquals(sta.getSkuQty(), ic.getPlanQty());

    }
}
