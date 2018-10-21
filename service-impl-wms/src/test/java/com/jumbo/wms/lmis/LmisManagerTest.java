package com.jumbo.wms.lmis;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jumbo.dao.report.OrderStatusCountCommandDao;
import com.jumbo.wms.manager.lmis.LmisManager;
import com.jumbo.wms.model.command.report.OrderStatusCountCommand;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:loxia-hibernate-context.xml", "classpath*:loxia-service-context.xml", "classpath*:spring.xml"})
public class LmisManagerTest {
    // 参考
    @Autowired
    private OrderStatusCountCommandDao orderStatusCountCommandDao;

    @Autowired
    private LmisManager lmisManager;

    @Resource(name = "mongoTemplate")
    private MongoOperations mongoOperation;

    // @Test
    public void findOrderStatusByOuIdTest() {
        try {
            List<OrderStatusCountCommand> list = orderStatusCountCommandDao.findOrderStatusByOuId(null, "20130428091734", "20160428091734");
            System.out.println(list);
        } catch (Exception e) {
        }
    }

    @Test
    public void getOperatingCostTest() {
        try {
            String string = lmisManager.getOperatingCost("{'start_time':'2015-08-01 00:00:00','end_time':'2016-09-01 00:00:00','order_type':'','store_code':'','job_type':'21'}");
            // String string =
            // lmisManager.getOperatingCost("{'start_time':'2016-08-15 00:00:00','end_time':'2016-09-01 00:00:00','store_code':'','job_type':''}");
            // String string =
            // lmisManager.getOperatingCost("{'page':'1','pageSize':'10','start_time':'2016-08-15 00:00:00','end_time':'2016-09-01 00:00:00','store_code':'','job_type':''}");
            System.out.println(string);

        } catch (Exception e) {
        }
    }

    @Test
    public void getMaterialFeePurchaseDetailsTest() {
        try {
            String string = lmisManager.getMaterialFeePurchaseDetails("{'start_time':'2016-07-01 00:00:00','end_time':'2016-07-30 00:00:00'}");
            System.out.println(string);

        } catch (Exception e) {
        }
    }

    @Test
    public void getMaterialFeeActualUsageTest() {
        try {
            String string = lmisManager.getMaterialFeeActualUsage("{'start_time':'2016-07-01 00:00:00','end_time':'2016-09-01 00:00:00'},'store_code':''");
            System.out.println(string);

        } catch (Exception e) {
        }
    }

    @Test
    public void getExpressWaybillTest() {
        try {
            // String string =
            // lmisManager.getExpressWaybill("{'start_time':'2016-09-12 15:02:28','end_time':'2016-09-12 12:02:28'},'time_type':''");
            String string = lmisManager.getExpressWaybill("{'start_time':'2016-07-10 15:02:28','end_time':'2016-09-12 12:02:28'},'time_type':''");
            // String string =
            // lmisManager.getExpressWaybill("{'start_time':'2016-08-01 00:00:00','end_time':'2016-09-01 00:00:00'},'time_type':''");
            // String string =
            // lmisManager.getExpressWaybill("{'page':'1','pageSize':'10','start_time':'2016-08-01 00:00:00','end_time':'2016-09-01 00:00:00'},'time_type':''");
            System.out.println(string);

        } catch (Exception e) {
        }
    }

    @Test
    public void getWarehouseChargesTest() {
        try {
            // String string =
            // lmisManager.getWarehouseCharges("{'start_time':'2016-08-01 00:00:00','end_time':'2016-09-01 00:00:00'}");
            String string = lmisManager.getWarehouseCharges("{'page':'1','pageSize':'10','start_time':'2016-08-01 00:00:00','end_time':'2016-09-01 00:00:00'}");
            System.out.println(string);

        } catch (Exception e) {
        }
    }
}
