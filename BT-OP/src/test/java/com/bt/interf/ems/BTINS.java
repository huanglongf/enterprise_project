package com.bt.interf.ems;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.bt.interf.btins.BTINSInterface;
import com.bt.orderPlatform.ftpupload.FTPFileUpload;
import com.bt.orderPlatform.model.WaybilDetail;
import com.bt.orderPlatform.model.WaybillMaster;
import com.bt.orderPlatform.service.WaybilDetailService;
import com.bt.orderPlatform.service.WaybillMasterService;



/** 
	* @Description: 
	* @author  Hanery:
 	* @date 2017年7月17日 上午11:28:38  
*/
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = {"classpath:spring.xml","classpath:spring-mvc.xml","classpath:spring-mybatis.xml"})
public class BTINS {

	@Autowired
	private BTINSInterface bTINSInterface;
	@Resource(name = "waybillMasterServiceImpl")
	private WaybillMasterService<WaybillMaster> waybillMasterService;
	@Resource(name = "waybilDetailServiceImpl")
	private WaybilDetailService<WaybilDetail> waybilDetailService;
	
	
	
	
	
	
	
	
	
	@Test
    @Transactional   //标明此方法需使用事务  
    @Rollback(false)  //标明使用完此方法后事务不回滚,true时为回滚
	public void create() throws Exception{
		String status = "7";
		List<WaybillMaster> list1 = waybillMasterService.queryByStatus(status);
		
		for (WaybillMaster waybillMaster : list1) {
			List<WaybilDetail> queryOrderByOrderId = waybilDetailService.queryOrderByOrderId(waybillMaster.getOrder_id());
			bTINSInterface.insertByObj(waybillMaster,queryOrderByOrderId);
		}
		//WaybillMaster waybillMaster = list1.get(0);
		WaybillMaster master = new WaybillMaster();

		/*1:SFCR/顺丰次日
		 2:SFGR/顺丰隔日
		 3:SFGR/顺丰隔日
		 5:SFCC/顺丰次晨
		 6:SFJR/顺丰即日*/
		/*master.setId("008057ba-a15a-4b63-8ba2-5497fc6b16c9");
		master.setIsAutoBill("1");
		master.setPayment("3");
		master.setOrder_id("WO2018011600000207");
		master.setExpress_name("顺丰快递");
		master.setProducttype_code("1");
		master.setProducttype_name("顺丰次日");
		master.setExpressCode("SF");
		master.setFrom_orgnization("快递");
		master.setTo_orgnization("快递");
		master.setTo_phone("12345161678");
		master.setTo_contacts("收货1人A");
		master.setTo_address("XXX1路123号");
		master.setTo_province("上海");
		master.setTo_city("上海");
		master.setTo_state("静安区");
		master.setFrom_province("上海");
		master.setFrom_city("上海");
		master.setFrom_state("闸北区");
		master.setTo_province_code("310000");
		master.setTo_city_code("310100");
		master.setTo_state_code("310101");
		master.setFrom_street("");
		master.setTotal_qty(20);
		master.setTotal_weight(new BigDecimal(500.23));
		master.setTotal_volumn(new BigDecimal(1234.93));
		master.setTotal_amount(new BigDecimal(303));
		master.setCreate_user("test1");
		master.setUpdate_time(new Date());
		master.setUpdate_user("test1");
		master.setStatus("0");
		master.setCost_center("01005063");
		master.setWaybill("12312");
		master.setAmount_flag("0");
		master.setStart_date("2017-11-24 15/16/37");
		
		WaybilDetail wd = new WaybilDetail();
		wd.setId("2000");
		wd.setSku_code("1");
		wd.setSku_name("电视");
		wd.setQty(20);
		
		WaybilDetail wd2 = new WaybilDetail();
		wd2.setId("2001");
		wd2.setSku_code("2");
		wd2.setSku_name("电饭煲");
		wd2.setQty(20);
		
		List<WaybilDetail> list = new ArrayList<>();
		list.add(wd);
		list.add(wd2);*/
		
	}
	
	
	
	
  
}
