package com.bt.interf.zto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.bt.interf.yl.YlInterface;
import com.bt.orderPlatform.model.WaybilDetail;
import com.bt.orderPlatform.model.WaybillMaster;

/** 
	* @Description: 
	* @author  Hanery:
 	* @date 2017年10月16日 下午2:25:44  
*/
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = {"classpath:spring.xml","classpath:spring-mvc.xml","classpath:spring-mybatis.xml"})
public class ZTOTest {

	@Autowired
	private ZTOInterface zTOInterface;
	
	@Test
    @Transactional   //标明此方法需使用事务  
    @Rollback(false)  //标明使用完此方法后事务不回滚,true时为回滚
	public void create(){
		WaybillMaster master = new WaybillMaster();

		master.setFrom_orgnization("TT");
		master.setId("9874102255");
		master.setIsAutoBill("122121");
		master.setExpressCode("ZTO");
		master.setPayment("1");
		master.setOrder_id("7878845545445");
		master.setFrom_orgnization("快递");
		master.setTo_orgnization("快递");
		master.setProducttype_code("1221");
		master.setProducttype_name("的方法大概快递");
		master.setTo_phone("454752352");
		master.setTo_contacts("啊撒飞洒地方");
		master.setTo_address("电饭锅路123号");
		master.setTo_province("上海");
		master.setTo_city("上海");
		master.setTo_state("静安区");
		master.setFrom_contacts("发货人2s");
		master.setFrom_phone("2122112121212");
		master.setFrom_address("XXX路789号");
		master.setFrom_province("上海");
		master.setFrom_city("上海");
		master.setFrom_state("静安区");
		master.setFrom_street("");
		master.setTotal_qty(20);
		master.setTotal_weight(new BigDecimal(500.23));
		master.setTotal_volumn(new BigDecimal(1234.93));
		master.setTotal_amount(new BigDecimal(303));
		master.setCreate_user("test2");
		master.setUpdate_time(new Date());
		master.setUpdate_user("test2");
		master.setStatus("0");
		master.setWaybill("");
		//master.setWaybill("538829378192");
		
		WaybilDetail wd = new WaybilDetail();
		wd.setId("2000");
		wd.setSku_code("1");
		wd.setSku_name("电视");
		
		WaybilDetail wd2 = new WaybilDetail();
		wd2.setId("2001");
		wd2.setSku_code("2");
		wd2.setSku_name("电饭煲");
		
		List<WaybilDetail> list = new ArrayList<>();
		list.add(wd);
		list.add(wd2);
		//订单在WMS系统的状态,0:下单, 1:发货, 2:取消
		int flag =0;
		int r = zTOInterface.createOrder(master,list,flag);
		
		System.out.println("=========================");
		System.out.println("============" + r + "============");
		System.out.println("=========================");
		
	}
	@Test
    @Transactional   //标明此方法需使用事务  
    @Rollback(false)  //标明使用完此方法后事务不回滚,true时为回滚
	public void createtest(){
		WaybillMaster master = new WaybillMaster();
		/*master.setWaybill("761914097630");
		zTOInterface.queryOrder(master);*/
		zTOInterface.queryOrdertest(master);
	}
}
