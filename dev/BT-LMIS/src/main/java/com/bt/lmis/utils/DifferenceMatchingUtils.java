package com.bt.lmis.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;

import com.bt.OSinfo;
import com.bt.lmis.base.TABLE_ROLE;
import com.bt.lmis.base.spring.SpringUtils;
import com.bt.lmis.dao.DifferenceMatchingMapper;
import com.bt.lmis.dao.WarehouseExpressDataMapper;
import com.bt.lmis.model.DifferenceObj;
import com.bt.lmis.model.ExpertBill;
import com.bt.lmis.model.SimpleMasterSlaveReport;
import com.bt.lmis.model.SimpleTable;
import com.bt.utils.CommonUtils;
import com.bt.utils.Constant;
import com.bt.utils.DateUtil;
import com.bt.utils.FileUtil;
import com.bt.utils.ReportUtils;
import com.bt.utils.ZipUtils;

/**
 * @Title:DifferenceMatchingUtils
 * @Description: TODO  
 * @author Ian.Huang 
 * @date 2017年3月23日下午4:32:30
 */
@SuppressWarnings("unchecked")
public class DifferenceMatchingUtils {
	
	private static WarehouseExpressDataMapper<T> warehouseExpressDataMapper= (WarehouseExpressDataMapper<T>)SpringUtils.getBean("warehouseExpressDataMapper");

	private static DifferenceMatchingMapper<T> differenceMatchingMapper= (DifferenceMatchingMapper<T>)SpringUtils.getBean("differenceMatchingMapper");
	
	/**
	 * 
	 * @Description: TODO
	 * @param eb
	 * @throws Exception
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2017年3月24日上午10:08:20
	 */
	public static String generateDifferenceMatching(ExpertBill eb) throws Exception {
		//
		String fileName= eb.getFile_name();
		String path= CommonUtils.getAllMessage("config", "BALANCE_DIFFERENCE_EXPRESS_" + OSinfo.getOSname());
		String file= fileName.substring(0, fileName.length() - 5)+ "差异报表";
		if(!FileUtil.judgeFileExistOrNot(path + file + Constant.SUFFIX_XLSX)&&!FileUtil.judgeFileExistOrNot( CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_" + OSinfo.getOSname()) + file + Constant.SUFFIX_ZIP)) {
			//
			List<SimpleTable> sts= new ArrayList<SimpleTable>();
			String code= "";
			// 表头构成
			LinkedHashMap<String, String> tableHeader= new LinkedHashMap<String, String>();
			LinkedHashMap<String, String> tableHeader0= new LinkedHashMap<String, String>();
			switch(eb.getTemplate()) {
			case "1":
				code= "SF";
				tableHeader.put("date", "日期");
				tableHeader.put("express_number", "运单号码");
				tableHeader.put("other_area", "对方地区");
				tableHeader.put("other_company_name", "对方公司名称");
				tableHeader.put("charge_weight", "计费重量");
				tableHeader.put("product_type", "产品类型");
				tableHeader.put("pay_method", "付款方式");
				tableHeader.put("fee", "费用(元)");
				tableHeader.put("insurance", "保费");
				tableHeader.put("delegated_picked", "委托取件/逆向物流");
				tableHeader.put("sumsung_project", "春节期间服务费+同城派件地址变更 ：10元/票");
				tableHeader.put("return_goods", "退货：5元/票");
				tableHeader.put("fragile_pieces", "易碎件");
				tableHeader.put("ts", "1月24日起—2月5日期间春节当天件：特殊服务费100元/票");
				tableHeader.put("amount_payable", "应付金额");
				tableHeader.put("operator", "经手人");
				tableHeader.put("source", "原寄地");
				tableHeader.put("deliver_company_name", "寄件公司名称");
				tableHeader.put("deliver_company_phone", "寄件公司电话");
				tableHeader.put("reach", "到件地区");
				tableHeader.put("reach_client_name", "到方客户名称");
				tableHeader.put("reach_client_phone", "到方客户电话");
				tableHeader.put("sender", "寄件人");
				tableHeader.put("send_time", "寄件时间");
				tableHeader.put("origin_province", "始发地(省名)");
				tableHeader.put("send_company_address", "寄件公司地址");
				tableHeader.put("recipient_no", "收件人工号");
				tableHeader.put("deliver_content", "托寄物内容)");
				tableHeader.put("deliver_num", "托寄物数量");
				tableHeader.put("value", "声明价值");
				tableHeader.put("num", "件数");
				tableHeader.put("volumn", "体积");
				tableHeader.put("dest_province", "目的地(省名)");
				tableHeader.put("recipient", "收件人");
				tableHeader.put("recipient_address", "收件地址");
				tableHeader.put("courier_no", "派件员工号");
				tableHeader.put("weight", "实际重量");
				tableHeader.put("return_order_no", "回单单号");
				tableHeader.put("receiver", "签收人");
				tableHeader.put("recipient_time", "签收时间");
				tableHeader.put("return_related_express_number", "退回件关联运单号");
				tableHeader.put("default1", "附加字段1");
				tableHeader.put("default2", "附加字段2");
				tableHeader.put("default3", "附加字段3");
				tableHeader.put("balance_date", "结算日期");
				tableHeader.put("pay_net_node", "付款网点");
				tableHeader.put("subcompany_card_number", "子公司卡号");
				tableHeader.put("remark", "备注");
				tableHeader.put("express_content", "快件内容");
				tableHeader.put("reserve_column3", "预留字段3");
				tableHeader.put("reserve_column4", "预留字段4");
				tableHeader.put("reserve_column5", "预留字段5");
				tableHeader.put("delegated_picked_remark", "委托费备注说明");
				tableHeader.put("firstWeight", "首重");
				tableHeader.put("addedWeight", "续重");
				tableHeader.put("remark2", "备注");
				tableHeader.put("length", "长");
				tableHeader.put("width", "宽");
				tableHeader.put("height", "高");
				tableHeader.put("warehouse", "发货仓");
				tableHeader.put("cost_center", "成本中心代码");
				tableHeader.put("store_name", "所属店铺");
				tableHeader.put("epistatic_order", "前置单据号");
				tableHeader.put("lmisWeight", "重量");
				tableHeader.put("province", "省份");
				tableHeader.put("order_amount", "订单金额");
				tableHeader.put("account_weight", "物理核算重量");
				tableHeader.put("volumn_account_weight", "体积核算重量");
				tableHeader.put("charged_weight", "计费重量");
				tableHeader.put("first_weight_price", "首重");
				tableHeader.put("added_weight_price", "续重");
				tableHeader.put("discount", "折扣");
				tableHeader.put("standard_freight", "标准运费");
				tableHeader.put("afterdiscount_freight", "折扣运费");
				tableHeader.put("insurance_fee", "保价费");
				tableHeader.put("DataSouring", "DataSouring");
				tableHeader.put("lmisVolumn", "尺寸");
				tableHeader.put("difference", "差异");
				tableHeader.put("order_type", "订单类型");
				tableHeader.put("difference_reason", "差异原因");
				tableHeader.put("park_name", "园区名称");
				tableHeader.put("park_cost_center", "园区成本中心");
				tableHeader0.put("日期", "日期");
				tableHeader0.put("运单号码", "运单号码");
				tableHeader0.put("对方地区", "对方地区");
				tableHeader0.put("对方公司名称", "对方公司名称");
				tableHeader0.put("计费重量", "计费重量");
				tableHeader0.put("产品类型", "产品类型");
				tableHeader0.put("付款方式", "付款方式");
				tableHeader0.put("费用(元)", "费用(元)");
				tableHeader0.put("保费", "保费");
				tableHeader0.put("委托取件/逆向物流", "委托取件/逆向物流");
				tableHeader0.put("春节期间服务费+同城派件地址变更 ：10元/票", "春节期间服务费+同城派件地址变更 ：10元/票");
				tableHeader0.put("退货：5元/票", "退货：5元/票");
				tableHeader0.put("易碎件", "易碎件");
				tableHeader0.put("1月24日起—2月5日期间春节当天件：特殊服务费100元/票", "1月24日起—2月5日期间春节当天件：特殊服务费100元/票");
				tableHeader0.put("应付金额", "应付金额");
				tableHeader0.put("经手人", "经手人");
				tableHeader0.put("原寄地", "原寄地");
				tableHeader0.put("寄件公司名称", "寄件公司名称");
				tableHeader0.put("寄件公司电话", "寄件公司电话");
				tableHeader0.put("到件地区", "到件地区");
				tableHeader0.put("到方客户名称", "到方客户名称");
				tableHeader0.put("到方客户电话", "到方客户电话");
				tableHeader0.put("寄件人", "寄件人");
				tableHeader0.put("寄件时间", "寄件时间");
				tableHeader0.put("始发地(省名)", "始发地(省名)");
				tableHeader0.put("寄件公司地址", "寄件公司地址");
				tableHeader0.put("收件人工号", "收件人工号");
				tableHeader0.put("托寄物内容)", "托寄物内容)");
				tableHeader0.put("托寄物数量", "托寄物数量");
				tableHeader0.put("声明价值", "声明价值");
				tableHeader0.put("件数", "件数");
				tableHeader0.put("体积", "体积");
				tableHeader0.put("目的地(省名)", "目的地(省名)");
				tableHeader0.put("收件人", "收件人");
				tableHeader0.put("收件地址", "收件地址");
				tableHeader0.put("派件员工号", "派件员工号");
				tableHeader0.put("实际重量", "实际重量");
				tableHeader0.put("回单单号", "回单单号");
				tableHeader0.put("签收人", "签收人");
				tableHeader0.put("签收时间", "签收时间");
				tableHeader0.put("退回件关联运单号", "退回件关联运单号");
				tableHeader0.put("附加字段1", "附加字段1");
				tableHeader0.put("附加字段2", "附加字段2");
				tableHeader0.put("附加字段3", "附加字段3");
				tableHeader0.put("结算日期", "结算日期");
				tableHeader0.put("付款网点", "付款网点");
				tableHeader0.put("子公司卡号", "子公司卡号");
				tableHeader0.put("备注", "备注");
				tableHeader0.put("快件内容", "快件内容");
				tableHeader0.put("预留字段3", "预留字段3");
				tableHeader0.put("预留字段4", "预留字段4");
				tableHeader0.put("预留字段5", "预留字段5");
				tableHeader0.put("委托费备注说明", "委托费备注说明");
				tableHeader0.put("首重", "首重");
				tableHeader0.put("续重", "续重");
				tableHeader0.put("备注2", "备注2");
				tableHeader0.put("长2", "长");
				tableHeader0.put("宽2", "宽");
				tableHeader0.put("高2", "高");
				tableHeader0.put("发货仓", "发货仓");
				tableHeader0.put("成本中心代码", "成本中心代码");
				tableHeader0.put("所属店铺", "所属店铺");
				tableHeader0.put("前置单据号", "前置单据号");
				tableHeader0.put("重量", "重量");
				tableHeader0.put("省份", "省份");
				tableHeader0.put("订单金额", "订单金额");
				tableHeader0.put("物理核算重量", "物理核算重量");
				tableHeader0.put("体积核算重量", "体积核算重量");
				tableHeader0.put("计费重量2", "计费重量2");
				tableHeader0.put("首重2", "首重2");
				tableHeader0.put("续重2", "续重2");
				tableHeader0.put("折扣2", "折扣");
				tableHeader0.put("标准运费", "标准运费");
				tableHeader0.put("折扣运费", "折扣运费");
				tableHeader0.put("保价费", "保价费");
				tableHeader0.put("DataSouring", "DataSouring");
				tableHeader0.put("尺寸", "尺寸");
				tableHeader0.put("difference", "差异");
				tableHeader0.put("订单类型", "订单类型");
				tableHeader0.put("差异原因", "差异原因");
				tableHeader0.put("park_name", "园区名称");
				tableHeader0.put("park_cost_center", "园区成本中心");
				break;
			case "2":
				code= "EMS";
				tableHeader.put("delivery_time", "收寄日期");
				tableHeader.put("express_number", "邮件号");
				tableHeader.put("reach", "寄达地");
				tableHeader.put("type", "类");
				tableHeader.put("weight", "重量");
				tableHeader.put("postage", "邮资");
				tableHeader.put("total_fee", "合计费用");
				tableHeader.put("balance_postage", "结算邮资");
				tableHeader.put("standard_postage", "标准邮资");
				tableHeader.put("other_fee", "其他费");
				tableHeader.put("product", "产品");
				tableHeader.put("num", "件数");
				tableHeader.put("num_in", "内件数");
				tableHeader.put("payment_amount", "货款金额");
				tableHeader.put("big_client", "大客户");
				tableHeader.put("receiver", "揽收员");
				tableHeader.put("staff_name", "员工姓名");
				tableHeader.put("pay_status", "收款情况");
				tableHeader.put("package_fee", "包装费");
				tableHeader.put("premium", "保险费");
				tableHeader.put("insurance", "保价费");
				tableHeader.put("declare_fee", "报关费");
				tableHeader.put("additional_fee", "附加费");
				tableHeader.put("single_fee", "单式费");
				tableHeader.put("airport_fee", "机场费");
				tableHeader.put("return_fee", "回执费");
				tableHeader.put("information_fee", "信息费");
				tableHeader.put("receive_fee", "揽收费");
				tableHeader.put("other_postage", "其他资费");
				tableHeader.put("insurance_procedures", "保价手续");
				tableHeader.put("length", "长");
				tableHeader.put("width", "宽");
				tableHeader.put("higth", "高");
				tableHeader.put("volumn_weight", "体积重");
				tableHeader.put("organization_number", "机构编号");
				tableHeader.put("system_discount", "系统折扣");
				tableHeader.put("adjustment_discount", "调整折扣");
				tableHeader.put("real_weight", "实重");
				tableHeader.put("real_standard_postage", "实重标准资费");
				tableHeader.put("balance_freight", "结算运费");
				tableHeader.put("weight_different", "重量差额");
				tableHeader.put("freight_different", "运费差额");
				tableHeader.put("warehouse", "发货仓");
				tableHeader.put("cost_center", "成本中心代码");
				tableHeader.put("store_name", "所属店铺");
				tableHeader.put("delivery_order", "前置单据号");
				tableHeader.put("lmisWeight", "重量");
				tableHeader.put("volumn", "体积");
				tableHeader.put("province", "省份");
				tableHeader.put("order_amount", "订单金额");
				tableHeader.put("account_weight", "物理核算重量");
				tableHeader.put("volumn_account_weight", "体积核算重量");
				tableHeader.put("charged_weight", "计费重量");
				tableHeader.put("first_weight_price", "首重");
				tableHeader.put("added_weight_price", "续重");
				tableHeader.put("discount", "折扣");
				tableHeader.put("standard_freight", "标准运费");
				tableHeader.put("afterdiscount_freight", "折扣运费");
				tableHeader.put("insurance_fee", "保价费");
				tableHeader.put("difference", "运费差异");
				tableHeader.put("park_name", "园区名称");
				tableHeader.put("park_cost_center", "园区成本中心");
				break;
			case "3":
				code= "YTO";
				tableHeader.put("transport_time", "发货日期");
				tableHeader.put("express_number", "运单号");
				tableHeader.put("origin", "始发地");
				tableHeader.put("province_dest", "目的省");
				tableHeader.put("city_dist", "市区");
				tableHeader.put("weight", "实际重量");
				tableHeader.put("charge_weight", "圆通计费重量");
				tableHeader.put("firstWeightPrice", "首重报价");
				tableHeader.put("addedWeightPrice", "续重报价");
				tableHeader.put("discount", "折扣");
				tableHeader.put("standard_freight", "标准运费");
				tableHeader.put("warehouse", "发货仓");
				tableHeader.put("cost_center", "成本中心代码");
				tableHeader.put("store_name", "所属店铺");
				tableHeader.put("epistatic_order", "前置单据号");
				tableHeader.put("lmisWeight", "重量");
				tableHeader.put("volumn", "体积");
				tableHeader.put("province", "省份");
				tableHeader.put("order_amount", "订单金额");
				tableHeader.put("account_weight", "物理核算重量");
				tableHeader.put("charged_weight", "计费重量");
				tableHeader.put("first_weight_price", "首重");
				tableHeader.put("added_weight_price", "续重");
				tableHeader.put("lmisStandardFreight", "标准运费");
				tableHeader.put("insurance_fee", "保价费");
				tableHeader.put("freightDifference", "运费差异");
				tableHeader.put("chargedWeightDifference", "计费重量差异");
				tableHeader.put("remark", "备注");
				tableHeader.put("park_name", "园区名称");
				tableHeader.put("park_cost_center", "园区成本中心");
				break;
			case "4":
				code= "STO";
				tableHeader.put("transport_product_type", "运输产品类别");
				tableHeader.put("transport_direction", "运输方向（正向运输/逆向退货）");
				tableHeader.put("deliver_date", "发货日期'");
				tableHeader.put("transport_time", "运输时间");
				tableHeader.put("express_number", "运单号");
				tableHeader.put("store", "店铺/品牌");
				tableHeader.put("order_number", "订单号/指令号");
				tableHeader.put("orign", "始发地");
				tableHeader.put("province_dest", "目的省");
				tableHeader.put("city_dest", "市区");
				tableHeader.put("weight", "实际重量");
				tableHeader.put("sku_number", "SKU编码");
				tableHeader.put("length", "长");
				tableHeader.put("width", "宽");
				tableHeader.put("higth", "高");
				tableHeader.put("volumn", "体积");
				tableHeader.put("order_amount", "订单金额");
				tableHeader.put("firstWeigthPrice", "首重报价");
				tableHeader.put("addedWeightPrice", "续重报价");
				tableHeader.put("discount", "折扣");
				tableHeader.put("standard_freight", "标准运费");
				tableHeader.put("afterDiscount_freight", "折后运费");
				tableHeader.put("insurance", "保价费");
				tableHeader.put("other", "其他");
				tableHeader.put("total_fee", "合计费用");
				tableHeader.put("remark", "备注");
				tableHeader.put("warehouse", "发货仓");
				tableHeader.put("cost_center", "成本中心代码");
				tableHeader.put("store_name", "所属店铺");
				tableHeader.put("epistatic_order", "前置单据号");
				tableHeader.put("lmisWeight", "重量");
				tableHeader.put("lmisVolumn", "体积");
				tableHeader.put("province", "省份");
				tableHeader.put("lmisOrderAmount", "订单金额");
				tableHeader.put("account_weight", "物理核算重量");
				tableHeader.put("charged_weight", "计费重量");
				tableHeader.put("first_weight_price", "首重");
				tableHeader.put("added_weight_price", "续重");
				tableHeader.put("insurance_fee", "保价费");
				tableHeader.put("lmisStandardFreight", "标准运费");
				tableHeader.put("lmisOtherRemark", "其他宝尊确认");
				tableHeader.put("lmisTotalFee", "合计");
				tableHeader.put("difference", "差异");
				tableHeader.put("lmisRemark", "备注");
				tableHeader.put("park_name", "园区名称");
				tableHeader.put("park_cost_center", "园区成本中心");
				break;
			default: break;
			}
			if("1".equals(eb.getTemplate())){
				processSf(tableHeader,eb,tableHeader0);
				
			}else{
				List<Map<String, Object>> tableContent= (List<Map<String, Object>>)differenceMatchingMapper.getClass().getMethod("matching" + code + "Difference", String.class).invoke(differenceMatchingMapper, eb.getBatch_id());
				sts.add(new SimpleTable(TABLE_ROLE.COMMON, file, tableHeader, tableContent));
				SimpleMasterSlaveReport smsr= new SimpleMasterSlaveReport(path, file, Constant.SUF_XLSX, sts);
				ReportUtils.generateSimpleMasterSlaveReport(smsr);
				FileUtil.copyFile(smsr.getFilePath(), CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_" + OSinfo.getOSname()) + file + Constant.SUFFIX_XLSX, true);

			}
			
		}
		if("1".equals(eb.getTemplate()))return file + Constant.SUFFIX_ZIP; 
		return file + Constant.SUFFIX_XLSX;
		
	}
	
	
	
	public static String generateDifferenceMatching_test(ExpertBill eb) throws Exception {
		//
		String fileName= eb.getFile_name();
		String path= CommonUtils.getAllMessage("config", "BALANCE_DIFFERENCE_EXPRESS_" + OSinfo.getOSname());
		String file= fileName.substring(0, fileName.length() - 5)+ "差异报表";
		if(!FileUtil.judgeFileExistOrNot(path + file + Constant.SUFFIX_XLSX)&&!FileUtil.judgeFileExistOrNot( CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_" + OSinfo.getOSname()) + file + Constant.SUFFIX_ZIP)) {
			//
			List<SimpleTable> sts= new ArrayList<SimpleTable>();
			String code= "";
			// 表头构成
			LinkedHashMap<String, String> tableHeader= new LinkedHashMap<String, String>();
			LinkedHashMap<String, String> tableHeader0= new LinkedHashMap<String, String>();
			switch(eb.getTemplate()) {
			case "1":
				code= "SF";
				tableHeader.put("date", "日期");
				tableHeader.put("express_number", "运单号码");
				tableHeader.put("other_area", "对方地区");
				tableHeader.put("other_company_name", "对方公司名称");
				tableHeader.put("charge_weight", "计费重量");
				tableHeader.put("product_type", "产品类型");
				tableHeader.put("pay_method", "付款方式");
				tableHeader.put("fee", "费用(元)");
				tableHeader.put("insurance", "保费");
				tableHeader.put("delegated_picked", "委托取件/逆向物流");
				tableHeader.put("sumsung_project", "春节期间服务费+同城派件地址变更 ：10元/票");
				tableHeader.put("return_goods", "退货：5元/票");
				tableHeader.put("fragile_pieces", "易碎件");
				tableHeader.put("ts", "1月24日起—2月5日期间春节当天件：特殊服务费100元/票");
				tableHeader.put("amount_payable", "应付金额");
				tableHeader.put("operator", "经手人");
				tableHeader.put("source", "原寄地");
				tableHeader.put("deliver_company_name", "寄件公司名称");
				tableHeader.put("deliver_company_phone", "寄件公司电话");
				tableHeader.put("reach", "到件地区");
				tableHeader.put("reach_client_name", "到方客户名称");
				tableHeader.put("reach_client_phone", "到方客户电话");
				tableHeader.put("sender", "寄件人");
				tableHeader.put("send_time", "寄件时间");
				tableHeader.put("origin_province", "始发地(省名)");
				tableHeader.put("send_company_address", "寄件公司地址");
				tableHeader.put("recipient_no", "收件人工号");
				tableHeader.put("deliver_content", "托寄物内容)");
				tableHeader.put("deliver_num", "托寄物数量");
				tableHeader.put("value", "声明价值");
				tableHeader.put("num", "件数");
				tableHeader.put("volumn", "体积");
				tableHeader.put("dest_province", "目的地(省名)");
				tableHeader.put("recipient", "收件人");
				tableHeader.put("recipient_address", "收件地址");
				tableHeader.put("courier_no", "派件员工号");
				tableHeader.put("weight", "实际重量");
				tableHeader.put("return_order_no", "回单单号");
				tableHeader.put("receiver", "签收人");
				tableHeader.put("recipient_time", "签收时间");
				tableHeader.put("return_related_express_number", "退回件关联运单号");
				tableHeader.put("default1", "附加字段1");
				tableHeader.put("default2", "附加字段2");
				tableHeader.put("default3", "附加字段3");
				tableHeader.put("balance_date", "结算日期");
				tableHeader.put("pay_net_node", "付款网点");
				tableHeader.put("subcompany_card_number", "子公司卡号");
				tableHeader.put("remark", "备注");
				tableHeader.put("express_content", "快件内容");
				tableHeader.put("reserve_column3", "预留字段3");
				tableHeader.put("reserve_column4", "预留字段4");
				tableHeader.put("reserve_column5", "预留字段5");
				tableHeader.put("delegated_picked_remark", "委托费备注说明");
				tableHeader.put("firstWeight", "首重");
				tableHeader.put("addedWeight", "续重");
				tableHeader.put("remark2", "备注");
				tableHeader.put("length", "长");
				tableHeader.put("width", "宽");
				tableHeader.put("height", "高");
				tableHeader.put("warehouse", "发货仓");
				tableHeader.put("cost_center", "成本中心代码");
				tableHeader.put("store_name", "所属店铺");
				tableHeader.put("epistatic_order", "前置单据号");
				tableHeader.put("lmisWeight", "重量");
				tableHeader.put("province", "省份");
				tableHeader.put("order_amount", "订单金额");
				tableHeader.put("account_weight", "物理核算重量");
				tableHeader.put("volumn_account_weight", "体积核算重量");
				tableHeader.put("charged_weight", "计费重量");
				tableHeader.put("first_weight_price", "首重");
				tableHeader.put("added_weight_price", "续重");
				tableHeader.put("discount", "折扣");
				tableHeader.put("standard_freight", "标准运费");
				tableHeader.put("afterdiscount_freight", "折扣运费");
				tableHeader.put("insurance_fee", "保价费");
				tableHeader.put("DataSouring", "DataSouring");
				tableHeader.put("lmisVolumn", "尺寸");
				tableHeader.put("difference", "差异");
				tableHeader.put("order_type", "订单类型");
				tableHeader.put("difference_reason", "差异原因");
				break;
			case "2":
				code= "EMS";
				tableHeader.put("delivery_time", "收寄日期");
				tableHeader.put("express_number", "邮件号");
				tableHeader.put("reach", "寄达地");
				tableHeader.put("type", "类");
				tableHeader.put("weight", "重量");
				tableHeader.put("postage", "邮资");
				tableHeader.put("total_fee", "合计费用");
				tableHeader.put("balance_postage", "结算邮资");
				tableHeader.put("standard_postage", "标准邮资");
				tableHeader.put("other_fee", "其他费");
				tableHeader.put("product", "产品");
				tableHeader.put("num", "件数");
				tableHeader.put("num_in", "内件数");
				tableHeader.put("payment_amount", "货款金额");
				tableHeader.put("big_client", "大客户");
				tableHeader.put("receiver", "揽收员");
				tableHeader.put("staff_name", "员工姓名");
				tableHeader.put("pay_status", "收款情况");
				tableHeader.put("package_fee", "包装费");
				tableHeader.put("premium", "保险费");
				tableHeader.put("insurance", "保价费");
				tableHeader.put("declare_fee", "报关费");
				tableHeader.put("additional_fee", "附加费");
				tableHeader.put("single_fee", "单式费");
				tableHeader.put("airport_fee", "机场费");
				tableHeader.put("return_fee", "回执费");
				tableHeader.put("information_fee", "信息费");
				tableHeader.put("receive_fee", "揽收费");
				tableHeader.put("other_postage", "其他资费");
				tableHeader.put("insurance_procedures", "保价手续");
				tableHeader.put("length", "长");
				tableHeader.put("width", "宽");
				tableHeader.put("higth", "高");
				tableHeader.put("volumn_weight", "体积重");
				tableHeader.put("organization_number", "机构编号");
				tableHeader.put("system_discount", "系统折扣");
				tableHeader.put("adjustment_discount", "调整折扣");
				tableHeader.put("real_weight", "实重");
				tableHeader.put("real_standard_postage", "实重标准资费");
				tableHeader.put("balance_freight", "结算运费");
				tableHeader.put("weight_different", "重量差额");
				tableHeader.put("freight_different", "运费差额");
				tableHeader.put("warehouse", "发货仓");
				tableHeader.put("cost_center", "成本中心代码");
				tableHeader.put("store_name", "所属店铺");
				tableHeader.put("delivery_order", "前置单据号");
				tableHeader.put("lmisWeight", "重量");
				tableHeader.put("volumn", "体积");
				tableHeader.put("province", "省份");
				tableHeader.put("order_amount", "订单金额");
				tableHeader.put("account_weight", "物理核算重量");
				tableHeader.put("volumn_account_weight", "体积核算重量");
				tableHeader.put("charged_weight", "计费重量");
				tableHeader.put("first_weight_price", "首重");
				tableHeader.put("added_weight_price", "续重");
				tableHeader.put("discount", "折扣");
				tableHeader.put("standard_freight", "标准运费");
				tableHeader.put("afterdiscount_freight", "折扣运费");
				tableHeader.put("insurance_fee", "保价费");
				tableHeader.put("difference", "运费差异");
				break;
			case "3":
				code= "YTO";
				tableHeader.put("transport_time", "发货日期");
				tableHeader.put("express_number", "运单号");
				tableHeader.put("origin", "始发地");
				tableHeader.put("province_dest", "目的省");
				tableHeader.put("city_dist", "市区");
				tableHeader.put("weight", "实际重量");
				tableHeader.put("charge_weight", "圆通计费重量");
				tableHeader.put("firstWeightPrice", "首重报价");
				tableHeader.put("addedWeightPrice", "续重报价");
				tableHeader.put("discount", "折扣");
				tableHeader.put("standard_freight", "标准运费");
				tableHeader.put("warehouse", "发货仓");
				tableHeader.put("cost_center", "成本中心代码");
				tableHeader.put("store_name", "所属店铺");
				tableHeader.put("epistatic_order", "前置单据号");
				tableHeader.put("lmisWeight", "重量");
				tableHeader.put("volumn", "体积");
				tableHeader.put("province", "省份");
				tableHeader.put("order_amount", "订单金额");
				tableHeader.put("account_weight", "物理核算重量");
				tableHeader.put("charged_weight", "计费重量");
				tableHeader.put("first_weight_price", "首重");
				tableHeader.put("added_weight_price", "续重");
				tableHeader.put("lmisStandardFreight", "标准运费");
				tableHeader.put("insurance_fee", "保价费");
				tableHeader.put("freightDifference", "运费差异");
				tableHeader.put("chargedWeightDifference", "计费重量差异");
				tableHeader.put("remark", "备注");
				break;
			case "4":
				code= "STO";
				tableHeader.put("transport_product_type", "运输产品类别");
				tableHeader.put("transport_direction", "运输方向（正向运输/逆向退货）");
				tableHeader.put("deliver_date", "发货日期'");
				tableHeader.put("transport_time", "运输时间");
				tableHeader.put("express_number", "运单号");
				tableHeader.put("store", "店铺/品牌");
				tableHeader.put("order_number", "订单号/指令号");
				tableHeader.put("orign", "始发地");
				tableHeader.put("province_dest", "目的省");
				tableHeader.put("city_dest", "市区");
				tableHeader.put("weight", "实际重量");
				tableHeader.put("sku_number", "SKU编码");
				tableHeader.put("length", "长");
				tableHeader.put("width", "宽");
				tableHeader.put("higth", "高");
				tableHeader.put("volumn", "体积");
				tableHeader.put("order_amount", "订单金额");
				tableHeader.put("firstWeigthPrice", "首重报价");
				tableHeader.put("addedWeightPrice", "续重报价");
				tableHeader.put("discount", "折扣");
				tableHeader.put("standard_freight", "标准运费");
				tableHeader.put("afterDiscount_freight", "折后运费");
				tableHeader.put("insurance", "保价费");
				tableHeader.put("other", "其他");
				tableHeader.put("total_fee", "合计费用");
				tableHeader.put("remark", "备注");
				tableHeader.put("warehouse", "发货仓");
				tableHeader.put("cost_center", "成本中心代码");
				tableHeader.put("store_name", "所属店铺");
				tableHeader.put("epistatic_order", "前置单据号");
				tableHeader.put("lmisWeight", "重量");
				tableHeader.put("lmisVolumn", "体积");
				tableHeader.put("province", "省份");
				tableHeader.put("lmisOrderAmount", "订单金额");
				tableHeader.put("account_weight", "物理核算重量");
				tableHeader.put("charged_weight", "计费重量");
				tableHeader.put("first_weight_price", "首重");
				tableHeader.put("added_weight_price", "续重");
				tableHeader.put("insurance_fee", "保价费");
				tableHeader.put("lmisStandardFreight", "标准运费");
				tableHeader.put("lmisOtherRemark", "其他宝尊确认");
				tableHeader.put("lmisTotalFee", "合计");
				tableHeader.put("difference", "差异");
				tableHeader.put("lmisRemark", "备注");
				break;
			default: break;
			}
			if("1".equals(eb.getTemplate())){
				processSf(tableHeader,eb,tableHeader0);
				
			}else{
				List<Map<String, Object>> tableContent= (List<Map<String, Object>>)differenceMatchingMapper.getClass().getMethod("matching" + code + "Difference", String.class).invoke(differenceMatchingMapper, eb.getBatch_id());
				sts.add(new SimpleTable(TABLE_ROLE.COMMON, file, tableHeader, tableContent));
				SimpleMasterSlaveReport smsr= new SimpleMasterSlaveReport(path, file, Constant.SUF_XLSX, sts);
				ReportUtils.generateSimpleMasterSlaveReport(smsr);
				FileUtil.copyFile(smsr.getFilePath(), CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_" + OSinfo.getOSname()) + file + Constant.SUFFIX_XLSX, true);

			}
			
		}
		if("1".equals(eb.getTemplate()))return file + Constant.SUFFIX_ZIP; 
		return file + Constant.SUFFIX_XLSX;
		
	}
	
	
	public static String processSf(LinkedHashMap<String, String> head,ExpertBill eb,LinkedHashMap<String, String> head0) throws Exception{
		//建结算表
		String fileName= eb.getFile_name();
		DifferenceObj obj=new DifferenceObj();
		obj.setBat_id(eb.getBatch_id());
		obj.setTableHeader(head);
		DifferenceObj obj0=new DifferenceObj();
		obj0.setBat_id(eb.getBatch_id());
		obj0.setTableHeader(head0);
		String file= fileName.substring(0, fileName.length() - 5)+ "差异报表";
		obj.setFileName(file);
		File f=new File("config", "COMMON_DOWNLOAD_" + OSinfo.getOSname()+file);
		if(!f.exists())f.mkdir();
		obj.setUrl(CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_" + OSinfo.getOSname())+file);
	   List <Map<String,Object>> idsMap=	differenceMatchingMapper.getIdByBatIdCYPP(eb.getBatch_id());
		//计算需要的线程数量  最多10个线程
	  int begin_index=Integer.parseInt(idsMap.get(0).get("min_id").toString());
		Thread[] thread=new Thread[5];
	   for(int i=0;i<5;i++){
			thread[i] =new RobortA(obj,begin_index+(i*200000),i);	
			thread[i].start();
		}
		
		RobortB b=new RobortB(obj);
		b.start();
		b.join();
		 for(int i=0;i<5;i++){				
			thread[i].join();
		}
	    //汇总  zyz1234
		differenceMatchingMapper.collect(eb.getBatch_id());
		
		obj.setTableHeader(head0);
		Thread t1=new RobortD(obj);
		Thread t2=new RobortD1(obj);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		
		
		//写文件
		/* Shell shell=new Shell("10.7.46.48",22,"root","WJ@baozun123");
		  String cmd[]=new String[2];
		  Thread.sleep(5000);
		  Date d=new Date();
		  String dStr=DateUtil.formatSS(d);
    	  cmd[0]="mysql -uroot -pbaozun2017";
    	  cmd[1]="select   * from lmis_pe.df_sf_result1  where id=1 or difference=0 and bat_id=\""+eb.getBatch_id()+"\" INTO OUTFILE '/home/"+dStr+"nodiff.csv' FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '' LINES TERMINATED BY '\n';"
    				+"select   * from lmis_pe.df_sf_result1  where id=1 or difference!=0 and bat_id=\""+eb.getBatch_id()+"\" INTO OUTFILE '/home/"+dStr+"diff.csv' FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '' LINES TERMINATED BY '\n'"
    						+"select   * from lmis_pe.df_sf_result3  where id=1 or difference=0 and bat_id=\""+eb.getBatch_id()+"\" INTO OUTFILE '/home/"+dStr+"nodiff-1.csv' FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '' LINES TERMINATED BY '\n'"
    								+"select   * from lmis_pe.df_sf_result3  where id=1 or difference!=0 and bat_id=\""+eb.getBatch_id()+"\" INTO OUTFILE '/home/"+dStr+"diff.-1csv' FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '' LINES TERMINATED BY '\n'";			    	
    	  System.out.println(cmd[1]);
    	  System.out.println(
    	  shell.executeCommands(cmd));
    	  Thread.sleep(25000);
    	  shell.close();
    	  String []cmd1=new String[5];
    	  shell=new Shell("10.7.46.48",22,"root","WJ@baozun123");
    	  cmd1[0]="cd /home";
    	  cmd1[1]="scp "+dStr+"nodiff.csv root@10.7.46.46:"+CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_" + OSinfo.getOSname())+file;
    	  cmd1[2]="scp "+dStr+"diff.csv root@10.7.46.46:"+CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_" + OSinfo.getOSname())+file;
    	  cmd1[3]="scp "+dStr+"nodiff-1.csv root@10.7.46.46:"+CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_" + OSinfo.getOSname())+file;
    	  cmd1[4]="scp "+dStr+"diff-1.csv root@10.7.46.46:"+CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_" + OSinfo.getOSname())+file;
    	  System.out.println(
    	    	  shell.executeCommands(cmd1));
    	  Thread.sleep(15000);
    	    	  shell.close();*/
		
		System.out.println("-----------文件已经生成完毕。准备打包");
		ZipUtils.zip(CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_" + OSinfo.getOSname())+file,CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_" + OSinfo.getOSname()));
		
		
		return CommonUtils.getAllMessage("config", "BALANCE_DIFFERENCE_EXPRESS_" + OSinfo.getOSname())+file+Constant.SUFFIX_ZIP;
	
	}
	
	public static void processSf_retrun(Map<String, Object> tableContent){
		Map<String,Object> mmap=new HashMap<String,Object> ();
		mmap.put("waybill", tableContent.get("express_number"));
		Map<String,Object> map = differenceMatchingMapper.getReturnData(mmap);
		if(map==null){processSf_base(tableContent); return;}
		tableContent.put("warehouse", map.get("warehouse_name"));
		tableContent.put("cost_center", map.get("cost_center"));
		tableContent.put("store_name", map.get("store_name"));
		tableContent.put("epistatic_order",map.get( "related_no"));
		tableContent.put("lmisWeight", map.get("re_weight"));
		tableContent.put("order_amount",map.get( "out_order"));
		//tableContent.put("province", map.get("re_province"));
		tableContent.put("order_type",map.get( "job_type"));
		tableContent.put("product_type", map.get("itemtype_name"));
		tableContent.put("lmisVolumn", map.get("re_volumn"));
		/*tableHeader.put("length", "长");
		tableHeader.put("width", "宽");
		tableHeader.put("height", "高");
		*/
		tableContent.put("length", map.get("re_length"));
		tableContent.put("width", map.get("re_width"));
		tableContent.put("height",  map.get("re_higth"));
	}
	
	public static void processSf_base(Map<String, Object> tableContent){
		//1.没参加结算2.而且不是退货入库的单子。 最后匹配基础信息
		/*WarehouseExpressData qr=new WarehouseExpressData();
		qr.setExpress_number(tableContent.get("express_number").toString());
		List<WarehouseExpressData> list =warehouseExpressDataMapper.findAll(qr);
		WarehouseExpressData data=null;
		if(list!=null&&list.size()!=0){
			data=list.get(0);
			tableContent.put("warehouse", data.getWarehouse());
			tableContent.put("cost_center", data.getCost_center());
			tableContent.put("store_name", data.getStore_name());
			tableContent.put("epistatic_order", data.getEpistatic_order());
			tableContent.put("lmisWeight", data.getWeight());
			tableContent.put("order_amount", data.getOrder_amount());
			tableContent.put("province", data.getProvince());
			tableContent.put("lmisVolumn", data.getLength().multiply(data.getWidth()).multiply(data.getHigth()));
			tableContent.put("order_type", data.getOrder_type());
			tableContent.put("DataSouring", data.getItemtype_name());
		}*/
		
	}
	
}