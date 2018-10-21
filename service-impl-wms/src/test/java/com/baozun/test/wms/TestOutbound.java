package com.baozun.test.wms;

import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.baozun.test.wms.manager.TestOutboundManager;
import com.jumbo.dao.StaTestDao;
import com.jumbo.wms.model.warehouse.StockTransApplication;

/**
 * 测试样例类
 * @author jingkai
 *
 */
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = {"classpath*:loxia-hibernate-context.xml", "classpath*:loxia-service-context.xml", "classpath*:test/spring-test.xml"})
public class TestOutbound {

    @Autowired
    TestOutboundManager testOutboundManager;
    @Autowired
    StaTestDao staTestDao;

    private StockTransApplication staData;

    @Before
    public void befortGetSta() {
        System.out.println("before");
        Map<String, Long> skus = new HashMap<String, Long>();
        skus.put("CVSA46456-650-0085", 1L);
        staData = testOutboundManager.crateSalesOutboundSta(1843L, "1CONVERSE官方商城", skus);
        System.out.println("gen sta ,code :" + staData.getCode());
    }

    /**
     * 
     * @author jingkai
     */
    @Test
    public void getSta() {
        System.out.println("getSta");
        StockTransApplication sta = staTestDao.testFindById(staData.getId());
        System.out.println(sta.getCode() + "sta id : " + sta.getId());
        assertNotNull(sta);
    }
    
    @After
    public void afrerGetSta() {
        System.out.println("after");
//        testOutboundManager.removeTestData(staData.getCode());
    }

}
