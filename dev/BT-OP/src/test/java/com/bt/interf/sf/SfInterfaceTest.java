package com.bt.interf.sf;


import com.bt.common.util.CommonUtil;
import com.bt.common.util.HttpHelper;
import com.bt.common.util.MD5Util;
import com.bt.common.util.XmlUtil;
import com.bt.orderPlatform.model.SfexpressServiceRequestBean;
import com.bt.orderPlatform.model.SfexpressServiceRequestBodyBean;
import com.bt.orderPlatform.model.SfexpressServiceResponseBean;
import com.bt.orderPlatform.model.WaybillMaster;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml","classpath:spring-mvc.xml","classpath:spring-mybatis.xml"})  //{"/spring.xml","/spring-mvc.xml","/spring-mybatis.xml"}
public class SfInterfaceTest extends AbstractTransactionalJUnit4SpringContextTests{

	@Autowired
	private SfInterface sfInterface;

	//由controller方法替换
	public void testSfexpressService(){
		Map<String,Object> paramMap = new HashMap<String,Object>();

		SfexpressServiceRequestBean sfexpressServiceRequestBean = getSfexpressServiceRequestBean();

		String xml = XmlUtil.writeObjectToXml(SfexpressServiceRequestBean.class, sfexpressServiceRequestBean);

		String verifyCode = CommonUtil.encodeBase64(
				MD5Util.md5Encrypt(xml
						+CommonUtil.getConfig("checkword_sf")));

		paramMap.put("xml", xml);
		paramMap.put("verifyCode", verifyCode);

		SfexpressServiceResponseBean response = null;
		try {
			response = HttpHelper.fetchObjectByPost(SfexpressServiceResponseBean.class,
					"http://bspoisp.sit.sf-express.com:11080/bsp-oisp/sfexpressService",
					paramMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("========================");
		System.out.println(response);
	}

	public void testObjectToXml(){

		SfexpressServiceRequestBean sfexpressServiceRequestBean = getSfexpressServiceRequestBean();

		String str = XmlUtil.writeObjectToXml(SfexpressServiceRequestBean.class, sfexpressServiceRequestBean);
		System.out.println("========================");
		System.out.println(str);
	}


	/**
	 *
	 */
	@Test
	@Transactional   //标明此方法需使用事务
	@Rollback(false)  //标明使用完此方法后事务不回滚,true时为回滚
	public void testService(){

		//得到测试数据
		WaybillMaster master = getWaybillMaster();

		int r = sfInterface.placeOrder(master);

		System.out.println("=========================");
		System.out.println("============" + r + "============");
		System.out.println("=========================");

	}
	@Test
	@Transactional   //标明此方法需使用事务
	@Rollback(false)  //标明使用完此方法后事务不回滚,true时为回滚
	public void getRouteService() throws Exception{

		//得到测试数据
		WaybillMaster master = getWaybillMaster();

		List<WaybillMaster> list1 = new ArrayList<WaybillMaster>();
		list1.add(master);
		sfInterface.getRoute(list1);
		System.out.println(new Date());
		System.out.println("=========================");
		System.out.println("============" + "============");
		System.out.println("=========================");

	}
	@Test
	@Transactional   //标明此方法需使用事务
	@Rollback(false)  //标明使用完此方法后事务不回滚,true时为回滚
	public void CancelOrder() throws Exception{

		//得到测试数据
		WaybillMaster master = getWaybillMaster();

		List<WaybillMaster> list1 = new ArrayList<WaybillMaster>();
		list1.add(master);
		//sfInterface.CancelOrder(master);

		System.out.println("=========================");
		System.out.println("============" + "============");
		System.out.println("=========================");

	}

	private SfexpressServiceRequestBean getSfexpressServiceRequestBean(){
		SfexpressServiceRequestBean sfexpressServiceRequestBean = new SfexpressServiceRequestBean();

		SfexpressServiceRequestBodyBean body = new SfexpressServiceRequestBodyBean();

		WaybillMaster order = new WaybillMaster();

		body.setRequestOrder(order);
		sfexpressServiceRequestBean.setBody(body);

		return sfexpressServiceRequestBean;
	}


	//WaybillMaster 测试数据
	private WaybillMaster getWaybillMaster(){
		WaybillMaster master = new WaybillMaster();

		master.setId("");
		master.setWaybill("586498273551");
		master.setOrder_id("");
		master.setFrom_orgnization("TNF-7715 浦东八佰伴");
		master.setFrom_orgnization_code("99");
		master.setTo_orgnization("");
		master.setProducttype_code("");
		master.setProducttype_name("");
		master.setTo_phone("");
		master.setTo_contacts("");
		master.setTo_address("");
		master.setTo_province("");
		master.setTo_city("");
		master.setTo_state("");
		master.setFrom_contacts("");
		master.setFrom_phone("");
		master.setFrom_address("");
		master.setFrom_province("");
		master.setFrom_city("");
		master.setFrom_state("");
		//master.setFrom_street("万荣路");
		master.setTotal_qty(1);
		master.setCustid("0210030557");
		master.setIs_docall("1");

		return master;
	}





}
