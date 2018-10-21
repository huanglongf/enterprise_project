package com.bt.interf.yl;


import com.bt.orderPlatform.model.WaybilDetail;
import com.bt.orderPlatform.model.WaybillMaster;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml","classpath:spring-mvc.xml","classpath:spring-mybatis.xml"})  //{"/spring.xml","/spring-mvc.xml","/spring-mybatis.xml"}
public class YlInterfaceTest extends AbstractTransactionalJUnit4SpringContextTests{

	@Autowired
	private YlInterface ylInterface;

	@Test
    @Transactional   //标明此方法需使用事务
    @Rollback(false)  //标明使用完此方法后事务不回滚,true时为回滚
	public void create(){
		WaybillMaster master = new WaybillMaster();

		master.setFrom_orgnization("VIFI");
		master.setId("1234567");
		master.setIsAutoBill("1");
		master.setExpressCode("YUNDA");
		master.setPayment("3");
		master.setOrder_id("2705019115020");
		master.setFrom_orgnization("yunda快递");
		master.setTo_orgnization("from快递");
		master.setProducttype_code("4");
		master.setProducttype_name("标准快递");
		master.setTo_phone("12345161678");
		master.setTo_contacts("收货1人A");
		master.setTo_address("XXX1路123号");
		master.setTo_province("上海");
		master.setTo_city("上海");
		master.setTo_state("静安区");
		master.setFrom_contacts("发货人1A");
		master.setFrom_phone("12345116678");
		master.setFrom_address("XXX路789号");
		master.setFrom_province("上海");
		master.setFrom_city("上海");
		master.setFrom_state("静安区");
		master.setFrom_street("");
		master.setTotal_qty(20);
		master.setTotal_weight(new BigDecimal(500.23));
		master.setTotal_volumn(new BigDecimal(1234.93));
		master.setTotal_amount(new BigDecimal(303));
		master.setCreate_user("test1");
		master.setUpdate_time(new Date());
		master.setUpdate_user("test1");
		master.setStatus("0");
		master.setWaybill("");

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
		int r = ylInterface.createOrder(master,list);

		System.out.println("=========================");
		System.out.println("============" + r + "============");
		System.out.println("=========================");

	}

	@Test
	@Transactional   //标明此方法需使用事务
	@Rollback(false)  //标明使用完此方法后事务不回滚,true时为回滚
	public void cancelOrder(){
		String shipperCode = "YUNDA";
		//订单号
		String orderId = "2705019115020";
		//运单号
		String wayBill = "71171875573577";
		//物流公司单号
		String umsbillNo = "";
		int isAutoBill = 1;
		int i = ylInterface.cancelOrder(shipperCode,wayBill,umsbillNo,isAutoBill,orderId,"1");
		System.out.println(i);
	}



	@Test
	@Transactional   //标明此方法需使用事务
	@Rollback(false)  //标明使用完此方法后事务不回滚,true时为回滚
	public void queryOrder(){
		String shipperCode = "YUNDA";
		String orderId = "71171875573577";
		int i = ylInterface.queryOrder(shipperCode,orderId);
		System.out.println(i);
	}


	@Test
	@Transactional   //标明此方法需使用事务
	@Rollback(false)  //标明使用完此方法后事务不回滚,true时为回滚
	public void insertByObj(){
		WaybillMaster master = new WaybillMaster();

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

		String time = sdf.format(date);
		String date1 = sdf1.format(date);

		master.setOrder_id("12323221");
		master.setCreate_user("test");
		master.setExpressCode("YUNDA");
		master.setFrom_contacts("发货人f");
		master.setFrom_address("江苏省南京市鼓楼区雨花大道1w93号");
		master.setFrom_phone("13900221");
		master.setFrom_num("11013019282");
		master.setFrom_province("江苏省");
		master.setFrom_city("南京市");
		master.setFrom_state("鼓楼区");
		master.setTo_contacts("收货人r");
		master.setTo_address("江苏省苏州市工业园区金鸡湖路7381w号");
		master.setTo_phone("1380013232801");
		master.setTo_province("江苏省");
		master.setTo_city("苏州市");
		master.setTo_state("工业园区");
		master.setTo_num("13413253125783");
		master.setCargo_type("衣服");
		master.setMemo("当日送达");
		master.setSubmit_date(date1);
		master.setSubmit_time(time);
		master.setTotal_amount(new BigDecimal(0));
		master.setTo_phone2("");
		master.setTotal_weight(new BigDecimal(0));
		master.setCustomer_source("");
		master.setCustomer_phone("");

		String obj = ylInterface.insertByObj(master);
		System.out.println(obj);
	}


	@Test
	@Transactional   //标明此方法需使用事务
	@Rollback(false)  //标明使用完此方法后事务不回滚,true时为回滚
	public void cancelcOrder(){
		String logisticsId = "";
		String orderId = "DH20170523101835881463";
		int i = ylInterface.cancelOrder(logisticsId,orderId,"1");
		System.out.println(i);
	}



	@Test
	@Transactional   //标明此方法需使用事务
	@Rollback(false)  //标明使用完此方法后事务不回滚,true时为回滚
	public void queryCOrder(){
		String waybillNo = "";//运单号
		String orderId = "DH20170512173024879382";//订单号
		int i = ylInterface.queryCOrder(waybillNo,orderId);
		System.out.println(i);
	}

}
