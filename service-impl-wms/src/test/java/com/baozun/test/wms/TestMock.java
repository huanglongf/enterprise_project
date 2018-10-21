package com.baozun.test.wms;

import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import com.baozun.test.wms.manager.TestMockManager;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.warehouse.StockTransApplication;

import tool.AopTargetUtils;

/**
 * 测试样例类,提供MOCK使用
 * 
 * @author jingkai
 *
 */
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:loxia-hibernate-context.xml", "classpath*:loxia-service-context.xml", "classpath*:test/spring-test.xml"})
public class TestMock {


    @Resource
    @InjectMocks
    TestMockManager testMockManager;
    @Mock
    WareHouseManager wareHouseManagerImpl;

    @Mock
    private StockTransApplicationDao staDao;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        when(wareHouseManagerImpl.isCodByStaId(1L)).thenReturn(true);

    }

    @Test
    public void testMock() throws Exception {
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(testMockManager), "wareHouseManager", wareHouseManagerImpl);
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(testMockManager), "staDao", staDao);

        System.out.println("场景1：模拟mock类反馈");
        System.out.println(wareHouseManagerImpl.isCodByStaId(1L));
        System.out.println(testMockManager.mockManager(1L));
        System.out.println(testMockManager.mockManager(2L));

        System.out.println("场景2：替换testOutboundManager 类中 staDao,使用mock类模拟反馈");
        StockTransApplication x = new StockTransApplication();
        x.setId(1L);
        x.setCode("X00001");
        when(staDao.getByPrimaryKey(1L)).thenReturn(x);


        System.out.println(testMockManager.testMockStaDao(1L).getCode());
        System.out.println(testMockManager.testMockStaDao(98158508L).getCode());
    }

}
