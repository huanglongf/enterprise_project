package com.jumbo.mongo.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jumbo.pms.model.command.ShippingPointKeepNumber;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:loxia-hibernate-context.xml", "classpath*:loxia-service-context.xml", "classpath*:spring.xml"})
public class MongoDBTest {
    @Resource(name = "mongoTemplate")
    private MongoOperations mongoOperation;

    @Test
    public void testMongo() {
        // ShippingPoint temp = new ShippingPoint();
        // temp.setId(1l);
        // temp.setKeepNumber(0l);
        // mongoOperation.save(temp, "wmsShippingPoints");
        // ShippingPoint temp2 = new ShippingPoint();
        // temp2.setId(2l);
        // temp2.setKeepNumber(0l);
        // mongoOperation.save(temp2, "wmsShippingPoints");
        // ShippingPoint temp2 = mongoOperation.findOne(new Query(Criteria.where("id").is(1l)),
        // ShippingPoint.class, "wmsShippingPoints");
        // System.out.println(temp2);
        // mongoOperation.updateFirst(new Query(Criteria.where("id").is(1l)),
        // Update.update("keepNumber", temp2.getKeepNumber() + 1), ShippingPoint.class,
        // "wmsShippingPoints");
        // ShippingPoint temp3 = mongoOperation.findOne(new Query(Criteria.where("id").is(1l)),
        // ShippingPoint.class, "wmsShippingPoints");
        // System.out.println(temp3);
        // 测试找不到
        // ShippingPoint temp4 = mongoOperation.findOne(new Query(Criteria.where("id").is(8l)),
        // ShippingPoint.class, "wmsShippingPoints");
        // System.out.println(temp4);
        // 测试更新不存在的对象数据 没有报错
        // mongoOperation.updateFirst(new Query(Criteria.where("id").is(161l)),
        // Update.update("keepNumber", 0), ShippingPoint.class, "wmsShippingPoints");
        // ShippingPoint temp2 = mongoOperation.findOne(new Query(Criteria.where("id").is(161l)),
        // ShippingPoint.class, "wmsShippingPoints");
        // System.out.println(temp2);
        // List<ShippingPoint> list = shippingPointDao.getPointByRefShippingPoint("161", new
        // BeanPropertyRowMapper<ShippingPoint>(ShippingPoint.class));
        // System.out.println(list);
        // ShippingPoint p1 = mongoOperation.findAndRemove(new
        // Query(Criteria.where("id").is("161")), ShippingPoint.class, "wmsShippingPoints");
        // System.out.println(p1);
        // ShippingPoint p2 = mongoOperation.findAndRemove(new
        // Query(Criteria.where("id").is("500")), ShippingPoint.class, "wmsShippingPoints");
        // System.out.println(p2);
        // ShippingPoint p3 = mongoOperation.findAndRemove(new
        // Query(Criteria.where("id").is("600")), ShippingPoint.class, "wmsShippingPoints");
        // System.out.println(p3);
        mongoOperation.updateFirst(new Query(Criteria.where("id").is("161")), Update.update("keepNumber", 0), ShippingPointKeepNumber.class, "wmsShippingPoint161");
        mongoOperation.updateFirst(new Query(Criteria.where("id").is("500")), Update.update("keepNumber", 0), ShippingPointKeepNumber.class, "wmsShippingPoint500");
        mongoOperation.updateFirst(new Query(Criteria.where("id").is("500")), Update.update("keepNumber", 0), ShippingPointKeepNumber.class, "wmsShippingPoint600");
    }
}
