package com.baozun.easytask.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baozun.easytask.service.SkxService;
import com.baozun.easytask.util.BigExcelExport;
import com.baozun.easytask.util.DateUtil;
import com.baozun.easytask.util.MailUtils;

@Controller
@RequestMapping("/task")
public class TaskController {
	
	@Resource(name = "skxServiceImpl")
	private SkxService skxServiceImpl;
	
	@RequestMapping("/task.do")
	public void wmsSearch(HttpServletRequest request,HttpServletResponse res){
		LinkedHashMap<String, String> cMap = new LinkedHashMap<String, String> ();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String filehead = sdf.format(new Date());
		String a = DateUtil.getSpecifiedDayBefore(sdf.format(new Date()))+" 00:00:00";
		String b =sdf.format(new Date())+" 00:00:00";
		List<Map<String, Object>> list = skxServiceImpl.job1(a, b);
		cMap.put("monthly_account", "月结账号");
		cMap.put("store_code", "店铺代码");
		cMap.put("store_name", "店铺名称");
		cMap.put("warehouse_code", "仓库编码");
		cMap.put("warehouse_name", "仓库名称");
		cMap.put("transport_code", "承运商编码");
		cMap.put("delivery_order", "平台订单号");
		cMap.put("job_order", "作业单号");
		cMap.put("epistatic_order", "上位系统订单号");
		cMap.put("order_type", "作业单类型");
		cMap.put("express_number", "运单号");
		cMap.put("itemtype_code", "产品类型名称");
		cMap.put("platform_order_time", "平台订单时间");
		cMap.put("platform_payment_time", "平台付款时间");
		cMap.put("collection_on_delivery", "代收货款金额");
		cMap.put("create_time", "耗材SKU编码");
		cMap.put("weight", "实际重量KG");
		cMap.put("length", "长MM");
		cMap.put("width", "宽MM");
		cMap.put("higth", "高MM");
		cMap.put("volumn", "体积");
		cMap.put("delivery_province", "始发地省");
		cMap.put("province", "目的地省份");
		cMap.put("city", "目的地城市");
		cMap.put("state", "目的地区县");
		cMap.put("insurance_flag", "是否保价（0：否；1：是）");
		cMap.put("cod_flag", "是否COD（0：否；1：是）");
		cMap.put("shiptoname", "收件人");
		cMap.put("phone", "联系电话");
		cMap.put("address", "详细地址");
		cMap.put("express_time", "快递交接时间");
		cMap.put("check_time", "复核时间");
		cMap.put("weight_time", "称重时间");
		cMap.put("total_price", "订单总金额");
		cMap.put("order_source", "订单来源");
		cMap.put("producttype_code", "承运商产品类型编码");
		cMap.put("logistics_createTime", "快递服务创建时间");
		cMap.put("skuQty", "商品数量");
		try {
			BigExcelExport.excelDownLoadDatab(list, cMap, filehead+"斯凯奇数据");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		List<String> filepath = new ArrayList<>();
		filepath.add(MailUtils.path+filehead+"斯凯奇数据.xlsx");
		try {
			MailUtils.sendMail(MailUtils.emailStr, filehead+"斯凯奇数据" + "", "", filepath);
		} catch (UnsupportedEncodingException | MessagingException e) {
			e.printStackTrace();
		}
	}
 }
