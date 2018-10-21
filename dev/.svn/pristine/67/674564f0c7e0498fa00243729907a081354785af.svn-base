package com.bt.lmis.utils;

import java.beans.IntrospectionException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.alibaba.fastjson.JSON;
import com.bt.EPlatform;
import com.bt.OSinfo;
import com.bt.base.redis.RedisUtils;
import com.bt.lmis.base.TABLE_ROLE;
import com.bt.lmis.base.spring.SpringUtils;
import com.bt.lmis.controller.form.ExpressBalanceDetialQueryParam;
import com.bt.lmis.dao.BalanceSummaryExMapper;
import com.bt.lmis.dao.ExpressBalancedDataMapper;
import com.bt.lmis.dao.ExpressContractMapper;
import com.bt.lmis.dao.InsuranceECMapper;
import com.bt.lmis.dao.JbpcExMapper;
import com.bt.lmis.dao.NextPriceInternalMapper;
import com.bt.lmis.dao.OriginDestParamMapper;
import com.bt.lmis.dao.PricingFormulaMapper;
import com.bt.lmis.dao.SpecialServiceExMapper;
import com.bt.lmis.dao.TransportProductTypeMapper;
import com.bt.lmis.model.BalanceReportExpressParam;
import com.bt.lmis.model.BalanceSummaryEx;
import com.bt.lmis.model.DiffBilldeatils;
import com.bt.lmis.model.ExpressContractConfig;
import com.bt.lmis.model.ExpressRawData;
import com.bt.lmis.model.InsuranceEC;
import com.bt.lmis.model.JibanpaoConditionEx;
import com.bt.lmis.model.JipaoConditionEx;
import com.bt.lmis.model.OriginationDestinationParam;
import com.bt.lmis.model.SimpleTable;
import com.bt.lmis.model.SpecialServiceEx;
import com.bt.lmis.model.TransportProductType;
import com.bt.utils.CommonUtils;
import com.bt.utils.Constant;
import com.bt.utils.DateUtil;
import com.bt.utils.FileUtil;
import com.bt.utils.JSONUtils;
import com.bt.utils.ReportUtils;

/**
 * @Title:ExpressBalanceUtils
 * @Description: TODO(快递结算工具类)
 * @author Ian.Huang 
 * @date 2016年8月5日下午5:27:38
 */
public class ExpressBalanceUtils {
	
	/**
	 * 
	 * @Description: TODO
	 * @param brep
	 * @return: void  
	 * @author Ian.Huang 
	 * @throws Exception 
	 * @date 2017年3月23日上午10:59:20
	 */
	@SuppressWarnings({ "unchecked", "resource" })
	public static void generateBalanceReportExpress(BalanceReportExpressParam brep) throws Exception {
		// 文件生成路径
		String fileGenerationPath= CommonUtils.getAllMessage("config", "BALANCE_BILL_EXPRESS_" + OSinfo.getOSname()) + brep.getBalanceMonth();
		if(OSinfo.getOSname().equals(EPlatform.Linux) || OSinfo.getOSname().equals(EPlatform.Mac_OS_X)) {
			fileGenerationPath+= "/";
			
		} else if(OSinfo.getOSname().equals(EPlatform.Windows)) {
			fileGenerationPath+= "\\";
			
		}
		// 路径补全
		FileUtil.isExistPath(fileGenerationPath);
		//
		String fullFileName=fileGenerationPath+brep.getExpressName()+brep.getBalanceMonth()+"月结算报表"+Constant.SEPARATE_POINT + Constant.SUF_XLSX;
		// 输出文件流
		FileOutputStream output=new FileOutputStream(new File(fullFileName));
		// 内存中保留 10000 条数据，以免内存溢出，其余写入硬盘  
		SXSSFWorkbook wb=new SXSSFWorkbook(10000);
		try {
			// 产品类型区分
			List<TransportProductType> productType= ((TransportProductTypeMapper<T>)SpringUtils.getBean("transportProductTypeMapper")).findByTransportVendor(true, brep.getExpressCode());
			boolean flag= false;
			if(CommonUtils.checkExistOrNot(productType)){
				// 存在产品类型
				flag= true;
				
			}
			// 生成表
			// 汇总
			// 表头构成
			LinkedHashMap<String, String> tableHeader= new LinkedHashMap<String, String>();
			tableHeader.put("cost_center", "成本中心");
			tableHeader.put("store", "店铺");
			tableHeader.put("warehouse", "仓库");
			tableHeader.put("order_num", "单量(单)");
			if(flag) {
				for(int i= 0; i< productType.size(); i++){
					tableHeader.put("standard_freight_"+ productType.get(i).getProduct_type_code(), productType.get(i).getProduct_type_name()+ "标准运费（元）");
					tableHeader.put("discount_"+ productType.get(i).getProduct_type_code(), productType.get(i).getProduct_type_name()+ "单运单优惠金额（元）");
					
				}
				
			} else {
				tableHeader.put("standard_freight", "标准运费(元)");
				tableHeader.put("discount", "单运单优惠金额(元)");
				tableHeader.put("total_discount", "总运费折扣优惠金额(元)");
				
			}
			tableHeader.put("insurance", "保价费(元)");
			tableHeader.put("specialService", "特殊服务费(元)");
			tableHeader.put("total_fee", "总运费(元)");
			// 表内容构成
			// 构造显示内容
			List<Map<String, Object>> tableContent= new ArrayList<Map<String, Object>>();
			BalanceSummaryExMapper<T> balanceSummaryExMapper= (BalanceSummaryExMapper<T>)SpringUtils.getBean("balanceSummaryExMapper");
			// 封装请求参数
			Map<String, Object> param= new HashMap<String, Object>();
			// 当前最大版本号作为参数
			param.put("ver_id", balanceSummaryExMapper.selectCurrentVerId(brep.getExpressCode(), brep.getBalanceMonth()));
			param.put("express_code", brep.getExpressCode());
			param.put("balance_month", brep.getBalanceMonth());
			// 查询汇总集合
			List<BalanceSummaryEx> resultList= balanceSummaryExMapper.selectAllBallanceSummary(param);
			if(CommonUtils.checkExistOrNot(resultList)) {
				// 存在汇总集合
				// 汇总数值初始化
				Map<String, Object> total= new LinkedHashMap<String, Object>();
				total.put("cost_center", "");
				total.put("store", "总计");
				total.put("warehouse", "");
				total.put("order_num", 0);
				if(flag){
					for(int j= 0; j< productType.size(); j++){
						total.put("standard_freight_"+ productType.get(j).getProduct_type_code(), 0);
						total.put("discount_"+ productType.get(j).getProduct_type_code(), 0);
						
					}
					
				} else {
					total.put("standard_freight", 0);
					total.put("discount", 0);
					total.put("total_discount", 0);
					
				}
				total.put("insurance", 0);
				total.put("specialService", 0);
				total.put("total_fee", 0);
				//
				Map<String, Object> row= null;
				BalanceSummaryEx bSE= null;
				String uk= "";
				// 运单量
				Integer order_num= 0;
				// 保价费
				BigDecimal insurance= new BigDecimal(0);
				// 总费用
				BigDecimal total_fee= new BigDecimal(0);
				for(int i= 0; i< resultList.size(); i++) {
					bSE= resultList.get(i);
					if(!uk.equals(bSE.getStore_name() + bSE.getWarehouse_name())){
						if(i != 0){
							row.put("order_num", order_num);
							total.put("order_num", Integer.parseInt(total.get("order_num").toString()) + order_num);
							row.put("insurance", insurance);
							total.put("insurance", new BigDecimal(total.get("insurance").toString()).add(insurance));
							row.put("total_fee", total_fee);
							total.put("total_fee", new BigDecimal(total.get("total_fee").toString()).add(total_fee));
							tableContent.add(row);
							
						}
						uk= bSE.getStore_name()+ bSE.getWarehouse_name();
						// 初始化一行数据
						row= new LinkedHashMap<String, Object>();
						row.put("cost_center", bSE.getCost_center());
						row.put("store", bSE.getStore_name());
						row.put("warehouse", bSE.getWarehouse_name());
						order_num= 0;
						row.put("order_num", order_num);
						if(flag){
							for(int j= 0; j< productType.size(); j++){
								row.put("standard_freight_"+ productType.get(j).getProduct_type_code(), 0);
								row.put("discount_"+ productType.get(j).getProduct_type_code(), 0);
								
							}
							
						} else {
							row.put("standard_freight", 0);
							row.put("discount", 0);
							row.put("total_discount", 0);
							
						}
						insurance= new BigDecimal(0);
						row.put("insurance", insurance);
						row.put("specialService", 0);
						total_fee= new BigDecimal(0);
						row.put("total_fee", total_fee);
						
					}
					order_num+= bSE.getOrder_num();
					insurance= insurance.add(bSE.getInsurance());
					total_fee= total_fee.add(bSE.getTotal_fee());
					if(flag){
						row.put("standard_freight_" + bSE.getProduct_type_code(), bSE.getProduct_type_freight());
						total.put("standard_freight_" + bSE.getProduct_type_code(), new BigDecimal(total.get("standard_freight_" + bSE.getProduct_type_code()).toString()).add(bSE.getProduct_type_freight()));
						row.put("discount_" + bSE.getProduct_type_code(), bSE.getProduct_type_discount());
						total.put("discount_" + bSE.getProduct_type_code(), new BigDecimal(total.get("discount_" + bSE.getProduct_type_code()).toString()).add(bSE.getProduct_type_discount()));
						
					} else {
						row.put("standard_freight", bSE.getProduct_type_freight());
						total.put("standard_freight", new BigDecimal(total.get("standard_freight").toString()).add(bSE.getProduct_type_freight()));
						row.put("discount", bSE.getProduct_type_discount());
						total.put("discount", new BigDecimal(total.get("discount").toString()).add(bSE.getProduct_type_discount()));
						row.put("total_discount", bSE.getProduct_type_total_discount());
						total.put("total_discount", new BigDecimal(total.get("total_discount").toString()).add(bSE.getProduct_type_total_discount()));
						
					}
					if(i+ 1 == resultList.size()){
						row.put("order_num", order_num);
						total.put("order_num", Integer.parseInt(total.get("order_num").toString()) + order_num);
						row.put("insurance", insurance);
						total.put("insurance", new BigDecimal(total.get("insurance").toString()).add(insurance));
						row.put("total_fee", total_fee);
						total.put("total_fee", new BigDecimal(total.get("total_fee").toString()).add(total_fee));
						tableContent.add(row);
						
					}
					
				}
				tableContent.add(total);
				wb= ReportUtils.addSheet(wb, new SimpleTable(TABLE_ROLE.MASTER, "汇总", tableHeader, tableContent));
				System.out.println("********************汇总已生成********************");
				// 明细
				// 表头
				tableHeader= new LinkedHashMap<String, String>();
				tableHeader.put("store_name", "店铺");
				tableHeader.put("warehouse", "仓库");
				tableHeader.put("delivery_order", "订单号/指令号");
				tableHeader.put("epistatic_order", "上位系统订单号");
				tableHeader.put("order_type", "订单类型");
				tableHeader.put("express_number", "运单号");
				tableHeader.put("transport_direction", "运输方向（正向运输/逆向退货）");
				tableHeader.put("itemtype_name", "运输产品类型");
				tableHeader.put("transport_time", "订单生成时间");
				tableHeader.put("collection_on_delivery", "代收货款金额");
				tableHeader.put("order_amount", "订单金额(元)");
				tableHeader.put("sku_number", "SKU编码");
				tableHeader.put("weight", "实际重量(KG)");
				tableHeader.put("length", "长(CM)");
				tableHeader.put("width", "宽(CM)");
				tableHeader.put("higth", "高(CM)");
				tableHeader.put("volumn", "体积(CM³)");
				tableHeader.put("delivery_address", "始发地");
				tableHeader.put("province", "目的省");
				tableHeader.put("city", "市");
				tableHeader.put("state", "区");
				tableHeader.put("insurance_flag", "是否保价");
				tableHeader.put("cod_flag", "是否COD");
				tableHeader.put("first_weight_price", "首重报价(元)");
				tableHeader.put("added_weight_price", "续重报价(元)");
				tableHeader.put("standard_freight", "标准运费(元)");
				tableHeader.put("discount", "运单折扣(%)");
				tableHeader.put("afterdiscount_freight", "折后运费(元)");
				tableHeader.put("insurance_fee", "保价费(元)");
				tableHeader.put("total_fee", "合计费用(元)");
				tableHeader.put("park_code", "园区成本中心");
				tableHeader.put("park_name", "园区名称");
				tableHeader.put("park_cost_center", "园区成本中心");
				//
				ExpressBalanceDetialQueryParam queryParam= new ExpressBalanceDetialQueryParam();
				queryParam.setCon_id(Integer.parseInt(brep.getConId()));
				queryParam.setExpress_name(brep.getExpressName());
				// 结算周期
				Map<String, Object> balance_cycle=DateUtil.getBalanceCycle(brep.getBalanceMonth(), Integer.parseInt(((ExpressContractMapper<T>)SpringUtils.getBean("expressContractMapper")).findById(Integer.parseInt(brep.getConId())).get("settle_date").toString()));
				// 结算日期开始
				queryParam.setBalance_start_date(balance_cycle.get("balance_start_date").toString());
				// 结算日期结束
				queryParam.setBalance_end_date(balance_cycle.get("balance_end_date").toString());
				//
				queryParam.setFirstResult(0);
				queryParam.setMaxResult(Constant.SINGLE_SHEET_MAX_NUM);
				//
				ExpressBalancedDataMapper<T> expressBalancedDataMapper= (ExpressBalancedDataMapper<T>)SpringUtils.getBean("expressBalancedDataMapper");
				Integer cycleNum=CommonUtils.paginationCount(expressBalancedDataMapper.countAllBalancedData(queryParam), Constant.SINGLE_SHEET_MAX_NUM);
				for(int i=1; i<=cycleNum; i++) {
					wb=ReportUtils.addSheet(wb, new SimpleTable(TABLE_ROLE.SLAVE, "明细-"+i, tableHeader, expressBalancedDataMapper.getExpressSettlementDetail(queryParam)));
					System.out.println("********************明细-" + i + "已生成********************");
					queryParam.setFirstResult(i * Constant.SINGLE_SHEET_MAX_NUM);
					
				}
				wb.write(output);
				
			}
			
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | IntrospectionException | IOException e) {
			e.printStackTrace();
			throw e;
			
		} finally {
			try {
				wb.close();
				output.close();
				
			} catch (IOException e) {
				e.printStackTrace();
				throw e;
				
			}
			
		}
		System.out.println("*************" + fullFileName + "已生成*************");
		// 将生成报表文件复制到下载路径
		FileUtil.copyFile(fullFileName, CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_" + OSinfo.getOSname())+brep.getExpressName()+brep.getBalanceMonth()+"月结算报表"+Constant.SEPARATE_POINT+Constant.SUF_XLSX, true);
		
	}
	
	/**
	 * 
	 * @Description: TODO(通过报价中计费重量将所给重量进位)
	 * @param weight
	 * @param jfWeight
	 * @return: BigDecimal  
	 * @author Ian.Huang 
	 * @date 2017年2月20日下午1:09:30
	 */
	private static BigDecimal convertWeight(BigDecimal weight, BigDecimal jfWeight){
		if(jfWeight.compareTo(new BigDecimal("0.1")) == 0){
			return weight.setScale(1, BigDecimal.ROUND_UP);
			
		} else if(jfWeight.compareTo(new BigDecimal("0.5")) == 0){
			BigDecimal pricingWeight_c = weight.setScale(0, BigDecimal.ROUND_HALF_UP);
			// 如数为1.4，小于1.5，向上4舍5入取整，得1，1.4大于1，可知小数位小于0.5
			if(weight.compareTo(pricingWeight_c) == 1){
				return pricingWeight_c.add(jfWeight);
				
			}
			// 如数为1.5，向上四舍五入取整得2，减1.5得0.5，可知小数位等于0.5
			if(pricingWeight_c.compareTo(weight) == 0 || pricingWeight_c.subtract(weight).compareTo(new BigDecimal("0.5")) == 0){
				return weight;
				
			}
			// 如数为1.6，大于1.5，向上4舍5入取整，得2，1.6小于2，可知小数位大于0.5
			if(weight.compareTo(pricingWeight_c) == -1){
				return pricingWeight_c;
				
			}
			
		} else if(jfWeight.compareTo(new BigDecimal(1)) == 0){
			return weight.setScale(0, BigDecimal.ROUND_UP);
			
		}
		// 计费重量（报价不存在）
		return null;
		
	}
	
	/**
	 * 
	 * @Description: TODO(获取核算重量)
	 * @param weight
	 * @param jfWeight
	 * @param firstWeight
	 * @return: BigDecimal  
	 * @author Ian.Huang 
	 * @date 2017年2月20日下午1:15:57
	 */
	private static BigDecimal getAccountWeight(BigDecimal weight, BigDecimal jfWeight, BigDecimal firstWeight) {
		// 转换入参重量
		BigDecimal anotherWeight= convertWeight(weight, jfWeight);
		// 与首重作比较，取大值
		if(anotherWeight.compareTo(firstWeight) >= 0) {
			return anotherWeight;
			
		} else {
			return firstWeight;
			
		}
		
	}
	
	/**
	 * 
	 * @Description: TODO(获取全部所需重量)
	 * @param param
	 * @return: Map<String,Object>  
	 * @author Ian.Huang 
	 * @date 2017年2月20日上午11:53:50
	 */
	public static Map<String, Object> getAllWeight(Map<String, Object> param) {
		// 返回值
		Map<String, Object> allWeight= new HashMap<String, Object>();
		// 原始运单
		ExpressRawData eRD= (ExpressRawData)param.get("eRD");
		// 合同配置
		ExpressContractConfig eCC= (ExpressContractConfig)param.get("eCC");
		// 报价
		OriginationDestinationParam oDP= (OriginationDestinationParam)param.get("oDP");
		/* 
			全部所需重量有：
			1、物理重量-已存在
			2、物理核算重量-（物理重量-计费重量转换-与首重比较取大值）
			3、体积重量（全抛/半抛）可选
			4、体积核算重量-（体积重量-计费重量转换-与首重比较取大值）可选
			5、计费重量-物理核算重量与体积核算重量比较取大值
			
		*/
		// 物理核算重量
		BigDecimal account_weight= getAccountWeight(eRD.getWeight(), oDP.getJf_weight(), oDP.getSzxz_sz());
		//
		allWeight.put("account_weight", account_weight);
		// 体积重量
		BigDecimal volumn_weight= new BigDecimal("0");
		// 体积核算重量
		BigDecimal volumn_account_weight=  new BigDecimal("0");
		// 判断是否需要计抛
		if(eCC.getWeight_method() != 1) {
			// 当计重方式不为实际重量时，有体积重量（抛重）
			// 判断计全抛还是计半抛
			switch(eCC.getWeight_method()) {
			case 2:
				boolean flag_qp = false;
				BigDecimal attr_qp = null;
				List<JipaoConditionEx> jqpcList= (List<JipaoConditionEx>)param.get("jqpcList");
				if(CommonUtils.checkExistOrNot(jqpcList)) {
					for1: for(JipaoConditionEx jipaoConditionEx:jqpcList){
						// 是否满足计半抛条件
						switch(jipaoConditionEx.getAttr()){
						case 0:
							attr_qp= eRD.getLength();
							break;
						case 1:
							attr_qp= eRD.getWidth();
							break;
						case 2:
							attr_qp= eRD.getHigth();
							break;
						}
						switch(jipaoConditionEx.getCompareMark()){
						case 0:
							if(attr_qp.compareTo(jipaoConditionEx.getCon()) == 1){
								flag_qp = true;
								break for1;
							}
							break;
						case 1:
							if(attr_qp.compareTo(jipaoConditionEx.getCon()) >= 0){
								flag_qp = true;
								break for1;
							}
							break;
						}
					}
				} else {
					// 无限制直接计算计半抛重量
					flag_qp= true;
				}
				//　计全抛
				//　计抛计重取大值（不计半抛）
				//　计抛重量 = (长 * 宽 * 高) / 基数
				if(flag_qp){
					volumn_weight= eRD.getLength().multiply(eRD.getWidth()).multiply(eRD.getHigth()).divide(oDP.getSzxz_jpnum(), 2, BigDecimal.ROUND_HALF_UP);
				}
				break;
			case 3:
				// 计半抛
				// 计抛计重取大值（计半抛）
				// 计抛重量 = (长 * 宽 * 高 / 基数 - 实际重量) * 百分比 + 实际重量
				boolean flag= false;
				//　计全抛重量大于实际重量且满足计半抛条件则计半抛
				//　计全抛重量是否大于实际重量
				// 是否存在计半抛条件
				@SuppressWarnings("unchecked")
				List<JibanpaoConditionEx> jCExs= (List<JibanpaoConditionEx>)param.get("jCExs");
				BigDecimal attr = null;
				if(CommonUtils.checkExistOrNot(jCExs)) {
					for1: for(int i= 0; i< jCExs.size(); i++){
						// 是否满足计半抛条件
						switch(jCExs.get(i).getAttr()){
						case 0:
							attr= eRD.getLength();
							break;
						case 1:
							attr= eRD.getWidth();
							break;
						case 2:
							attr= eRD.getHigth();
							break;
						}
						switch(jCExs.get(i).getCompare_mark()){
						case 0:
							if(attr.compareTo(jCExs.get(i).getCon()) == 1){
								flag = true;
								break for1;
								
							}
							break;
						case 1:
							if(attr.compareTo(jCExs.get(i).getCon()) >= 0){
								flag = true;
								break for1;
								
							}
							break;
							
						}
						
					}
				
				} else {
					// 无限制直接计算计半抛重量
					flag= true;
					
				}
				// 是否满足计半抛条件
				if(flag){
					// 满足计半抛条件
					// 计算计半抛重量
					volumn_weight=
							eRD.getLength().multiply(eRD.getWidth()).multiply(eRD.getHigth()).divide(oDP.getSzxz_jpnum(), 2, BigDecimal.ROUND_HALF_UP)
							.subtract(eRD.getWeight())
							.multiply(eCC.getPercent().divide(new BigDecimal(100)))
							.add(eRD.getWeight());
					
				}break;
			default:break;
			}
			if(!(volumn_weight.compareTo(new BigDecimal("0")) == 0)) {
				volumn_account_weight= getAccountWeight(volumn_weight, oDP.getJf_weight(), oDP.getSzxz_sz());
				
			}
			
		}
		//
		allWeight.put("volumn_weight", volumn_weight);
		allWeight.put("volumn_account_weight", volumn_account_weight);
		// 计费重量
		if(account_weight.compareTo(volumn_account_weight) >= 0) {
			// 物理核算重量大或相等，都可取前者
			allWeight.put("charged_weight", account_weight);
			
		} else {
			// 体积核算重量大
			allWeight.put("charged_weight", volumn_account_weight);
			
		}
		return allWeight;
		
	}
	/**
	 * 
	 * @Description: TODO(获取全部所需重量)
	 * @param param
	 * @return: Map<String,Object>  
	 * @author Ian.Huang 
	 * @date 2017年2月20日上午11:53:50
	 */
	public static Map<String, Object> getAllWeight2(Map<String, Object> param) {
		// 返回值
		Map<String, Object> allWeight= new HashMap<String, Object>();
		// 原始运单
		DiffBilldeatils eRD= (DiffBilldeatils)param.get("eRD");
		// 合同配置
		ExpressContractConfig eCC= (ExpressContractConfig)param.get("eCC");
		// 报价
		OriginationDestinationParam oDP= (OriginationDestinationParam)param.get("oDP");
		/* 
			全部所需重量有：
			1、物理重量-已存在
			2、物理核算重量-（物理重量-计费重量转换-与首重比较取大值）
			3、体积重量（全抛/半抛）可选
			4、体积核算重量-（体积重量-计费重量转换-与首重比较取大值）可选
			5、计费重量-物理核算重量与体积核算重量比较取大值
			
		 */
		// 物理核算重量
		BigDecimal account_weight= getAccountWeight(eRD.getTransport_weight(), oDP.getJf_weight(), oDP.getSzxz_sz());
		//
		allWeight.put("account_weight", account_weight);
		// 体积重量
		BigDecimal volumn_weight= new BigDecimal("0");
		// 体积核算重量
		BigDecimal volumn_account_weight=  new BigDecimal("0");
		// 判断是否需要计抛
		if(eCC.getWeight_method() != 1) {
			// 当计重方式不为实际重量时，有体积重量（抛重）
			// 判断计全抛还是计半抛
			switch(eCC.getWeight_method()) {
			case 2:
				boolean flag_qp = false;
				BigDecimal attr_qp = null;
				List<JipaoConditionEx> jqpcList= (List<JipaoConditionEx>)param.get("jqpcList");
				if(CommonUtils.checkExistOrNot(jqpcList)) {
					for1: for(JipaoConditionEx jipaoConditionEx:jqpcList){
						// 是否满足计半抛条件
						switch(jipaoConditionEx.getAttr()){
						case 0:
							attr_qp= eRD.getLength();
							break;
						case 1:
							attr_qp= eRD.getWidth();
							break;
						case 2:
							attr_qp= eRD.getHeight();
							break;
						}
						switch(jipaoConditionEx.getCompareMark()){
						case 0:
							if(attr_qp.compareTo(jipaoConditionEx.getCon()) == 1){
								flag_qp = true;
								break for1;
							}
							break;
						case 1:
							if(attr_qp.compareTo(jipaoConditionEx.getCon()) >= 0){
								flag_qp = true;
								break for1;
							}
							break;
						}
					}
				} else {
					// 无限制直接计算计半抛重量
					flag_qp= true;
				}
				//　计全抛
				//　计抛计重取大值（不计半抛）
				//　计抛重量 = (长 * 宽 * 高) / 基数
				if(flag_qp){
					volumn_weight= eRD.getLength().multiply(eRD.getWidth()).multiply(eRD.getHeight()).divide(oDP.getSzxz_jpnum(), 2, BigDecimal.ROUND_HALF_UP);
				}
				break;
			case 3:
				// 计半抛
				// 计抛计重取大值（计半抛）
				// 计抛重量 = (长 * 宽 * 高 / 基数 - 实际重量) * 百分比 + 实际重量
				boolean flag= false;
				//　计全抛重量大于实际重量且满足计半抛条件则计半抛
				//　计全抛重量是否大于实际重量
				// 是否存在计半抛条件
				@SuppressWarnings("unchecked")
				List<JibanpaoConditionEx> jCExs= (List<JibanpaoConditionEx>)param.get("jCExs");
				BigDecimal attr = null;
				if(CommonUtils.checkExistOrNot(jCExs)) {
					for1: for(int i= 0; i< jCExs.size(); i++){
						// 是否满足计半抛条件
						switch(jCExs.get(i).getAttr()){
						case 0:
							attr= eRD.getLength();
							break;
						case 1:
							attr= eRD.getWidth();
							break;
						case 2:
							attr= eRD.getHeight();
							break;
						}
						switch(jCExs.get(i).getCompare_mark()){
						case 0:
							if(attr.compareTo(jCExs.get(i).getCon()) == 1){
								flag = true;
								break for1;
								
							}
							break;
						case 1:
							if(attr.compareTo(jCExs.get(i).getCon()) >= 0){
								flag = true;
								break for1;
								
							}
							break;
							
						}
						
					}
				
				} else {
					// 无限制直接计算计半抛重量
					flag= true;
					
				}
				// 是否满足计半抛条件
				if(flag){
					// 满足计半抛条件
					// 计算计半抛重量
					volumn_weight=
							eRD.getLength().multiply(eRD.getWidth()).multiply(eRD.getHeight()).divide(oDP.getSzxz_jpnum(), 2, BigDecimal.ROUND_HALF_UP)
							.subtract(eRD.getTransport_weight())
							.multiply(eCC.getPercent().divide(new BigDecimal(100)))
							.add(eRD.getTransport_weight());
					
				}break;
			default:break;
			}
			if(!(volumn_weight.compareTo(new BigDecimal("0")) == 0)) {
				volumn_account_weight= getAccountWeight(volumn_weight, oDP.getJf_weight(), oDP.getSzxz_sz());
				
			}
			
		}
		//
		allWeight.put("volumn_weight", volumn_weight);
		allWeight.put("volumn_account_weight", volumn_account_weight);
		// 计费重量
//		if(account_weight.compareTo(volumn_account_weight) >= 0) {
			// 物理核算重量大或相等，都可取前者
			allWeight.put("charged_weight", account_weight);
			
//		} else {
//			// 体积核算重量大
//			allWeight.put("charged_weight", volumn_account_weight);
//			
//		}
		return allWeight;
		
	}
	
	/**
	 * 
	 * @Description: TODO(根据redis连接情况切换查询方式)
	 * @param key
	 * @param clazz
	 * @param prefix
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @return: Object  
	 * @author Ian.Huang 
	 * @date 2016年11月30日下午3:11:06
	 */
	public static Object getDataRecourse(String key, Class<?> clazz, String prefix) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		if(RedisUtils.check_con()){
			// redis连接正常
			String str= RedisUtils.get(key);
			if(CommonUtils.checkExistOrNot(str)){
				// redis命中
				switch(JSONUtils.getJSONType(str)){
					case JSON_TYPE_OBJECT: return JSON.parseObject(str, clazz);
					case JSON_TYPE_ARRAY: return JSON.parseArray(str, clazz);
					case JSON_TYPE_ERROR:;
					default: break;
					
				}
				
			} 
			
		}
		// redis连接失败或未命中结果，转接mysql继续查询
		return getByMySql(prefix, key);
			
	}
	
	private static Object getByMySql(String prefix, String key) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		// 获取Dao
		Object mapper= SpringUtils.getBean(prefix+ "Mapper");
		return mapper.getClass().getMethod("get", String.class).invoke(mapper, key);
		
	}
	
	/**
	 * 
	 * @Description: TODO(将快递/客户店铺使用快递结算必须的配置资源加载到redis缓存)
	 * @param type(现有业务-0快递/1客户店铺使用快递)
	 * @return: void  
	 * @author Ian.Huang 
	 * @throws SecurityException  
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws ClassNotFoundException 
	 * @date 2016年11月29日下午1:50:41
	 */
	@SuppressWarnings("unchecked")
	public static void g2RedisCache() throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		// 快递报价
		CommonUtils.redisBeanList2Redis("com.bt.lmis.model.OfferRedisBean", ((OriginDestParamMapper<T>)SpringUtils.getBean(OriginDestParamMapper.class)).loadOffer(1));
		// 计半抛条件
		CommonUtils.redisBeanList2Redis("com.bt.lmis.model.JbpcRedisBean", ((JbpcExMapper<T>)SpringUtils.getBean(JbpcExMapper.class)).loadJBPC());
		// 计费公式
		CommonUtils.redisBeanList2Redis("com.bt.lmis.model.PricingFormulaRedisBean", ((PricingFormulaMapper<T>)SpringUtils.getBean(PricingFormulaMapper.class)).loadPF());
		// 续重区间
		CommonUtils.redisBeanList2Redis("com.bt.lmis.model.NextPriceInternalRedisBean", ((NextPriceInternalMapper<T>)SpringUtils.getBean(NextPriceInternalMapper.class)).loadNextPriceInternal());
		// 保价费规则
		CommonUtils.redisBeanList2Redis("com.bt.lmis.model.InsuranceRedisBean", ((InsuranceECMapper<T>)SpringUtils.getBean(InsuranceECMapper.class)).loadInsurance());
		// 特殊服务费规则
		CommonUtils.redisBeanList2Redis("com.bt.lmis.model.SpecialServiceRedisBean", ((SpecialServiceExMapper<T>)SpringUtils.getBean(SpecialServiceExMapper.class)).loadSSE());
	
	}
	
	/**
	 * @Title: g2RedisCacheSE
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年7月5日 下午5:14:45
	 */
	@SuppressWarnings("unchecked")
	public static void g2RedisCacheSE() throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		// 计半抛条件
		CommonUtils.redisBeanList2Redis("com.bt.lmis.model.JbpcRedisBean", ((JbpcExMapper<T>)SpringUtils.getBean(JbpcExMapper.class)).loadJBPC());
		// 续重区间
		CommonUtils.redisBeanList2Redis("com.bt.lmis.model.NextPriceInternalRedisBean", ((NextPriceInternalMapper<T>)SpringUtils.getBean(NextPriceInternalMapper.class)).loadNextPriceInternal());
		// 保价费规则
		CommonUtils.redisBeanList2Redis("com.bt.lmis.model.InsuranceRedisBean", ((InsuranceECMapper<T>)SpringUtils.getBean(InsuranceECMapper.class)).loadInsurance());
		// 特殊服务费规则
		CommonUtils.redisBeanList2Redis("com.bt.lmis.model.SpecialServiceRedisBean", ((SpecialServiceExMapper<T>)SpringUtils.getBean(SpecialServiceExMapper.class)).loadSSE());
	
	}
	
	/**
	 * 
	 * @Description: TODO(获取管理费)
	 * @param total_freight
	 * @param total_insurance
	 * @param total_freight_discount
	 * @param mFs
	 * @return
	 * @return: BigDecimal  
	 * @author Ian.Huang 
	 * @date 2016年10月25日下午5:13:37
	 */
	public static BigDecimal getManagerCost(BigDecimal total_freight, BigDecimal total_insurance, BigDecimal total_freight_discount, List<Map<String, Object>> mFs){
		Map<String,Object> mfs = mFs.get(0);
		BigDecimal transCost = new BigDecimal(0);
		BigDecimal managerCost = new BigDecimal(0);
		Map<String, Object> region = null;
		if((boolean) mfs.get("freight")){
			transCost= transCost.add(total_freight);
		}
		if((boolean)mfs.get("insurance")){
			transCost= transCost.add(total_insurance);
		}
		transCost= transCost.subtract(total_freight_discount);
		switch (Integer.valueOf(mfs.get("ladder_type").toString())) {
		//无阶梯
		case 1:
			 managerCost=transCost.multiply((new BigDecimal(String.valueOf(mfs.get("charge_percent")))).divide(new BigDecimal(100)));
			 break;
		case 2:
			for(int k= 0; k< mFs.size(); k++){
				region= new HashMap<String, Object>();
				Map<String,Object> res= mFs.get(k);
				region.put("compare_1", res.get("compare_1"));
				region.put("num_1",res.get("num_1"));
				region.put("compare_2", res.get("compare_2"));
				BigDecimal num_2 = null;
				if(CommonUtils.checkExistOrNot(new BigDecimal(res.get("num_2").toString()))){
					num_2 =new BigDecimal(res.get("num_2").toString());
				}else {
					num_2 = CommonUtils.getMax(
							new BigDecimal[]{new BigDecimal(res.get("num_1").toString()),transCost}
							).add(new BigDecimal(1));
				}
				region.put("num_2", num_2);
				if(CommonUtils.inRegionOrNot(region, transCost)){
					managerCost=managerCost.add(managerCost.multiply(new BigDecimal(res.get("charge_percent").toString()).divide(new BigDecimal(100))));
				}
			}
			break;
		case 3:
			int flag = 0;
			for(int i = 0; i < mFs.size(); i++){
				region = new HashMap<String, Object>();
				Map<String,Object> res_2 = mFs.get(i);
				region.put("compare_1", res_2.get("compare_1"));
				region.put("num_1", res_2.get("num_1"));
				region.put("compare_2",res_2.get("compare_2"));
				BigDecimal num_2 = null;
				if(CommonUtils.checkExistOrNot(res_2.get("num_2"))){
					num_2 =new BigDecimal(res_2.get("num_2").toString());
				} else {
					num_2 = CommonUtils.getMax(
							new BigDecimal[]{new BigDecimal(res_2.get("num_1").toString()),transCost}
							).add(new BigDecimal(1));
				}
				region.put("num_2", num_2);
				if(CommonUtils.inRegionOrNot(region, transCost)){
					if(CommonUtils.checkExistOrNot(new BigDecimal(res_2.get("charge_percent").toString()))){
						managerCost = managerCost.add(
							transCost.subtract(new BigDecimal(res_2.get("num_1").toString()))
							.multiply(
									new BigDecimal(res_2.get("charge_percent").toString())
									.divide(new BigDecimal(100)))
							);
					}
					flag++;
				} else {
					if(transCost.compareTo(num_2) == 1 || transCost.compareTo(num_2) == 0){
						if(CommonUtils.checkExistOrNot(new BigDecimal(res_2.get("charge_percent").toString()))){
							managerCost = managerCost.add(
								num_2.subtract(new BigDecimal(res_2.get("num_1").toString()))
								.multiply(
									new BigDecimal(res_2.get("charge_percent").toString())
									.divide(new BigDecimal(100))
									)
								);
						}
					} else {
						break;
					}
					continue;
				}
			}
			if(flag == 0){
				managerCost = new BigDecimal(0);
			}
			break;
		}		
		return managerCost;
	}
	
	/**
	 * 
	 * @Description: TODO(获取货到付款手续费)
	 * @param eRD
	 * @param sSE
	 * @return
	 * @return: BigDecimal  
	 * @author Ian.Huang 
	 * @date 2016年7月29日上午9:49:42
	 */
	public static BigDecimal getCod(ExpressRawData eRD, SpecialServiceEx sSE){
		BigDecimal cod = null;
		switch(sSE.getCod_charge_method()){
		case 1:
			BigDecimal param = eRD.getCollection_on_delivery()
			.multiply(
					sSE.getPercent()
					.divide(new BigDecimal(100))
					).setScale(sSE.getAccurate_decimal_place(), BigDecimal.ROUND_UP);
			if(param.compareTo(sSE.getParam_1()) == -1){
				cod = sSE.getCharge_1();
			} else {
				cod = eRD.getOrder_amount().multiply(
						sSE.getPercent()
						.divide(new BigDecimal(100))
						).setScale(sSE.getAccurate_decimal_place(), BigDecimal.ROUND_UP);
			}
		break;
		case 2:
			cod = eRD.getOrder_amount().multiply(sSE.getPercent().divide(new BigDecimal(100)));
		break;
		case 3:
			cod = eRD.getCollection_on_delivery().multiply(sSE.getPercent().divide(new BigDecimal(100)));
		break;
		}
		if(sSE.getCharge_min_flag() == 0 && cod.compareTo(sSE.getCharge_min()) == -1){
			cod = sSE.getCharge_min();
		}
		return cod;
	}
	
	public static BigDecimal getTotalFreightDiscount(
			BigDecimal total_freight, BigDecimal total_insurance, BigDecimal total_order_num, List<Map<String, Object>> tFDs){
		// 总运费折扣规则
		Map<String, Object> tFD = tFDs.get(0);
		// 总运费折扣金额
		BigDecimal total_discount = new BigDecimal(0);
		BigDecimal count=total_freight;
		if(Byte.parseByte(tFD.get("insurance_contain").toString()) == 0){
			count = count.add(total_insurance);
		}
		BigDecimal num = null;
		// 阶梯类型
		Integer ladder_type = Integer.parseInt(tFD.get("ladder_type").toString());
		if(ladder_type == 4){
			num=total_order_num;
		} else {
			num=count;
			
		}
		Map<String, Object> region = null;
		switch(ladder_type){
		case 1:
			// 无阶梯
			return num.multiply(
					new BigDecimal(tFD.get("discount").toString()).divide(new BigDecimal(100)));
		case 2:
			// 总运费超出部分阶梯
			int flag = 0;
			for(int i = 0; i < tFDs.size(); i++){
				region = new HashMap<String, Object>();
				tFD = tFDs.get(i);
				region.put("compare_1", tFD.get("compare_1"));
				region.put("num_1", tFD.get("num_1"));
				region.put("compare_2", tFD.get("compare_2"));
				BigDecimal num_2 = null;
				if(CommonUtils.checkExistOrNot(tFD.get("num_2"))){
					num_2 = new BigDecimal(tFD.get("num_2").toString());
				} else {
					num_2 = CommonUtils.getMax(
							new BigDecimal[]{new BigDecimal(tFD.get("num_1").toString()), num}
							).add(new BigDecimal(1));
				}
				region.put("num_2", num_2);
				if(CommonUtils.inRegionOrNot(region, num)){
					total_discount = total_discount.add(
							num.subtract(new BigDecimal(region.get("num_1").toString()))
							.multiply(
									new BigDecimal(tFD.get("discount").toString())
									.divide(new BigDecimal(100))
									)
							);
					flag++;
				} else {
					if(num.compareTo(num_2) == 1){
						total_discount = total_discount.add(
								num_2.subtract(
										new BigDecimal(region.get("num_1").toString()))
								.multiply(
										new BigDecimal(tFD.get("discount").toString())
										.divide(new BigDecimal(100))
										)
								);
					} else {
						break;
					}
					continue;
				}
			}
			if(flag == 0){
				return new BigDecimal("0");
			} else {
				return total_discount;
			}
		case 3:
			// 总费用总折扣阶梯
			for(int i = 0; i < tFDs.size(); i++){
				region = new HashMap<String, Object>();
				tFD = tFDs.get(i);
				region.put("compare_1", tFD.get("compare_1"));
				region.put("num_1", tFD.get("num_1"));
				region.put("compare_2", tFD.get("compare_2"));
				BigDecimal num_2 = null;
				if(CommonUtils.checkExistOrNot(tFD.get("num_2"))){
					num_2 = new BigDecimal(tFD.get("num_2").toString());
				} else {
					num_2 = CommonUtils.getMax(
							new BigDecimal[]{new BigDecimal(tFD.get("num_1").toString()), num}
							).add(new BigDecimal(1));
				}
				region.put("num_2", num_2);
				if(CommonUtils.inRegionOrNot(region, num)){
					return num.multiply(
							new BigDecimal(tFD.get("discount").toString()).divide(new BigDecimal(100)));
				} else {
					continue;
				}
			}
			break;
		case 4:
			// 单量折扣阶梯
			for(int i = 0; i < tFDs.size(); i++){
				region = new HashMap<String, Object>();
				tFD = tFDs.get(i);
				region.put("compare_1", tFD.get("compare_1"));
				region.put("num_1", tFD.get("num_1"));
				region.put("compare_2", tFD.get("compare_2"));
				BigDecimal num_2 = null;
				if(CommonUtils.checkExistOrNot(tFD.get("num_2"))){
					num_2 = new BigDecimal(tFD.get("num_2").toString());
				} else {
					num_2 = CommonUtils.getMax(
							new BigDecimal[]{new BigDecimal(tFD.get("num_1").toString()), num}
							).add(new BigDecimal(1));
				}
				region.put("num_2", num_2);
				if(CommonUtils.inRegionOrNot(region, num)){
					return count.multiply(
							new BigDecimal(tFD.get("discount").toString()).divide(new BigDecimal(100)));
				} else {
					continue;
				}
			}
			break;
		}
		return null;
	}
	
	/**
	 * 
	 * @Description: TODO(获取产品类型对应总费用折扣)
	 * @param sum
	 * @param tFDs
	 * @return
	 * @return: BigDecimal  
	 * @author Ian.Huang 
	 * @date 2016年7月22日上午11:24:33
	 */
	public static BigDecimal getTotalFreightDiscount(Map<String, Object> summary, List<Map<String, Object>> tFDs){
		// 总运费折扣金额
		BigDecimal total_discount = new BigDecimal(0);
		// 总运费或总单量
		BigDecimal sum = null;
		// 当总费用折扣存在
		if(CommonUtils.checkExistOrNot(tFDs)) {
			// 总运费折扣规则
			Map<String, Object> tFD = tFDs.get(0);
			// 阶梯类型
			Integer ladder_type = Integer.parseInt(tFD.get("ladder_type").toString());
			if(ladder_type == 4){
				// 单量折扣阶梯
				sum = new BigDecimal(summary.get("order_num").toString());
			} else {
				if(ladder_type != 1){
					// 总费用折扣阶梯
					sum = new BigDecimal(summary.get("afterDiscount").toString());
					if(Byte.parseByte(tFD.get("insurance_contain").toString()) == 0){
						sum = sum.add(new BigDecimal(summary.get("insurance").toString()));
					}
				}
			}
			Map<String, Object> region = null;
			switch(ladder_type){
			case 1:
				// 无阶梯
				return new BigDecimal(
						tFD.get("discount").toString()
						).divide(new BigDecimal(100));
			case 2:
				// 总运费超出部分阶梯
				int flag = 0;
				for(int i = 0; i < tFDs.size(); i++){
					region = new HashMap<String, Object>();
					tFD = tFDs.get(i);
					region.put("compare_1", tFD.get("compare_1"));
					region.put("num_1", tFD.get("num_1"));
					region.put("compare_2", tFD.get("compare_2"));
					BigDecimal num_2 = null;
					if(CommonUtils.checkExistOrNot(tFD.get("num_2"))){
						num_2 = new BigDecimal(tFD.get("num_2").toString());
					} else {
						num_2 = CommonUtils.getMax(
								new BigDecimal[]{new BigDecimal(tFD.get("num_1").toString()), sum}
								).add(new BigDecimal(1));
					}
					region.put("num_2", num_2);
					if(CommonUtils.inRegionOrNot(region, sum)){
						total_discount = total_discount.add(
								sum.subtract(new BigDecimal(region.get("num_1").toString()))
								.multiply(
										new BigDecimal(tFD.get("discount").toString())
										.divide(new BigDecimal(100))
										)
								);
						flag++;
					} else {
						if(sum.compareTo(num_2) == 1){
							total_discount = total_discount.add(
									num_2.subtract(
											new BigDecimal(region.get("num_1").toString()))
									.multiply(
											new BigDecimal(tFD.get("discount").toString())
											.divide(new BigDecimal(100))
											)
									);
						} else {
							break;
						}
						continue;
					}
				}
				if(flag == 0){
					return null;
				} else {
					return total_discount.divide(sum, 6, BigDecimal.ROUND_HALF_UP);
				}
			case 3:
				// 总费用总折扣阶梯
				for(int i = 0; i < tFDs.size(); i++){
					region = new HashMap<String, Object>();
					tFD = tFDs.get(i);
					region.put("compare_1", tFD.get("compare_1"));
					region.put("num_1", tFD.get("num_1"));
					region.put("compare_2", tFD.get("compare_2"));
					BigDecimal num_2 = null;
					if(CommonUtils.checkExistOrNot(tFD.get("num_2"))){
						num_2 = new BigDecimal(tFD.get("num_2").toString());
					} else {
						num_2 = CommonUtils.getMax(
								new BigDecimal[]{new BigDecimal(tFD.get("num_1").toString()), sum}
								).add(new BigDecimal(1));
					}
					region.put("num_2", num_2);
					if(CommonUtils.inRegionOrNot(region, sum)){
						return new BigDecimal(
								tFD.get("discount").toString()
								).divide(new BigDecimal(100));
					} else {
						continue;
					}
				}
				break;
			case 4:
				// 单量折扣阶梯
				for(int i = 0; i < tFDs.size(); i++){
					region = new HashMap<String, Object>();
					tFD = tFDs.get(i);
					region.put("compare_1", tFD.get("compare_1"));
					region.put("num_1", tFD.get("num_1"));
					region.put("compare_2", tFD.get("compare_2"));
					BigDecimal num_2 = null;
					if(CommonUtils.checkExistOrNot(tFD.get("num_2"))){
						num_2 = new BigDecimal(tFD.get("num_2").toString());
					} else {
						num_2 = CommonUtils.getMax(
								new BigDecimal[]{new BigDecimal(tFD.get("num_1").toString()), sum}
								).add(new BigDecimal(1));
					}
					region.put("num_2", num_2);
					if(CommonUtils.inRegionOrNot(region, sum)){
						return new BigDecimal(
								tFD.get("discount").toString()
								).divide(new BigDecimal(100));
					} else {
						continue;
					}
				}
				break;
			}
		}
		return null;
	}
	
	/**
	* @Title: getInsurance
	* @Description: TODO(获得保价费)
	* @param iECs
	* @param order_amount
	* @param contractType
	* @return BigDecimal
	* @throws
	*/ 
	public static BigDecimal getInsurance(List<InsuranceEC> iECs, BigDecimal order_amount, int contractType){
		BigDecimal insurance= null;
		Map<String, Object> region= null;
		// 检验保价费规则是否存在
		if(CommonUtils.checkExistOrNot(iECs)) {
			// 规则存在
			InsuranceEC insuranceRule= iECs.get(0);
			switch(insuranceRule.getLadder_type()){
			case 1:
				// 无阶梯
				// 收费率计算保价费
				insurance= order_amount.multiply(insuranceRule.getCharge_percent().divide(new BigDecimal(100)));
				// 保价费小于最低收费则取最低收费
				if(insuranceRule.getCharge_min_flag() == 0){
					if(insurance.compareTo(insuranceRule.getCharge_min()) == -1){
						insurance= insuranceRule.getCharge_min();
					}
				}
				break;
			case 2:
				// 总费用阶梯价
				for(int i= 0; i< iECs.size(); i++){
					region= new HashMap<String, Object>();
					insuranceRule= iECs.get(i);
					region.put("compare_1", insuranceRule.getCompare_1());
					region.put("num_1", insuranceRule.getNum_1());
					region.put("compare_2", insuranceRule.getCompare_2());
					BigDecimal num_2= null;
					if(CommonUtils.checkExistOrNot(insuranceRule.getNum_2())){
						num_2= insuranceRule.getNum_2();
					} else {
						num_2= CommonUtils.getMax(new BigDecimal[]{insuranceRule.getNum_1(), order_amount}).add(new BigDecimal(1));
					}
					region.put("num_2", num_2);
					if(CommonUtils.inRegionOrNot(region, order_amount)){
						if(CommonUtils.checkExistOrNot(insuranceRule.getCharge_percent())){
							insurance= order_amount.multiply(insuranceRule.getCharge_percent().divide(new BigDecimal(100)));
						}
						if(CommonUtils.checkExistOrNot(insuranceRule.getCharge())){
							insurance= insuranceRule.getCharge();
						}
						break;
					} else {
						continue;
					}
				}
				break;
			case 3:
				// 超出部分阶梯价
				int flag= 0;
				insurance= new BigDecimal(0);
				for(int i= 0; i < iECs.size(); i++){
					region= new HashMap<String, Object>();
					insuranceRule= iECs.get(i);
					region.put("compare_1", insuranceRule.getCompare_1());
					region.put("num_1", insuranceRule.getNum_1());
					region.put("compare_2", insuranceRule.getCompare_2());
					BigDecimal num_2= null;
					if(CommonUtils.checkExistOrNot(insuranceRule.getNum_2())){
						num_2= insuranceRule.getNum_2();
					} else {
						num_2= CommonUtils.getMax(new BigDecimal[]{insuranceRule.getNum_1(), order_amount}).add(new BigDecimal(1));
					}
					region.put("num_2", num_2);
					if(CommonUtils.inRegionOrNot(region, order_amount)){
						if(CommonUtils.checkExistOrNot(insuranceRule.getCharge_percent())){
							insurance= insurance.add(order_amount.subtract(insuranceRule.getNum_1()).multiply(insuranceRule.getCharge_percent().divide(new BigDecimal(100))));
						}
						if(CommonUtils.checkExistOrNot(insuranceRule.getCharge())){
							insurance= insurance.add(insuranceRule.getCharge());
						}
						flag++;
					} else {
						if(order_amount.compareTo(num_2) >= 0){
							if(CommonUtils.checkExistOrNot(insuranceRule.getCharge_percent())){
								insurance = insurance.add(num_2.subtract(insuranceRule.getNum_1()).multiply(insuranceRule.getCharge_percent().divide(new BigDecimal(100))));
							}
							if(CommonUtils.checkExistOrNot(insuranceRule.getCharge())){
								insurance = insurance.add(insuranceRule.getCharge());
							}
						} else {
							break;
						}
						continue;
					}
				}
				if(flag == 0){
					insurance= null;
				}
				break;
			}
			if(CommonUtils.checkExistOrNot(insurance)){
				// 小数位处理
				if(contractType == 1){
					// 快递结算
					if(insuranceRule.getBelong_to().equals("SF")){
						// 顺丰-4舍5入到整数
						insurance= insurance.setScale(0, BigDecimal.ROUND_HALF_UP);
					} else {
						// 非顺丰-4舍5入到小数后两位
						insurance= insurance.setScale(2, BigDecimal.ROUND_HALF_UP);
					}
				} else {
					// 使用快递结算-向上进位到两位小数
					insurance= insurance.setScale(2, BigDecimal.ROUND_UP);
				}
			}
		} else {
			// 规则不存在
			insurance= new BigDecimal(0);
		}
		return insurance;
	}
	
	/**
	 * 
	 * @Description: TODO(运费公式计算)
	 * @param param
	 * @return: Map<String,Object>  
	 * @author Ian.Huang 
	 * @date 2016年7月18日上午11:39:15
	 */
	public static Map<String, Object> formula(Map<String, Object> param){
		Map<String, Object> freight= new HashMap<String, Object>();
		// 初始化标准运费-如计费重量小于等于首重，首重价格即为标准运费
		BigDecimal standard_freight= new BigDecimal(param.get("firstWeightPrice").toString());
		BigDecimal pricingWeight= new BigDecimal(param.get("pricingWeight").toString());
		BigDecimal firstWeight= new BigDecimal(param.get("firstWeight").toString());
		// 计费重量大于首重，按各自公式计算
		if(pricingWeight.compareTo(firstWeight) == 1){
			switch(Integer.parseInt(param.get("formula").toString())){
			case 1:
				// 运费公式：总费用向上取整
				standard_freight= pricingWeight.subtract(firstWeight)
				.multiply(new BigDecimal(param.get("addedWeightPrice").toString()))
				.add(standard_freight)
				.setScale(Integer.parseInt(param.get("accurate_decimal_place").toString()), BigDecimal.ROUND_UP);
				break;
			case 2:
				// 计费重量向上取整
				standard_freight= pricingWeight
				.setScale(Integer.parseInt(param.get("accurate_decimal_place").toString()), BigDecimal.ROUND_UP)
				.subtract(firstWeight)
				.multiply(new BigDecimal(param.get("addedWeightPrice").toString()))
				.add(standard_freight);
				break;
			case 3:
				// 首重价格+续重价格（不向上取整）
				standard_freight= pricingWeight
				.subtract(firstWeight)
				.multiply(new BigDecimal(param.get("addedWeightPrice").toString()))
				.add(standard_freight);
				break;
			case 4:
				// 运费公式：EMS-首重0.5kg续重0.5kg报价
				// 公式 : 费用 = ((ROUNDUP(计费重量 * 2, 0) * 0.5 - 首重) * 续重单价 * 2 + 首重价格) * 折扣
				// 公式 : 费用 = ((计费重量  - 首重) * 续重单价 * 2 + 首重价格) * 折扣
				standard_freight= pricingWeight
//				.multiply(new BigDecimal(2))
//				.setScale(0, BigDecimal.ROUND_UP)
//				.multiply(new BigDecimal(0.5))
				.subtract(firstWeight)
				.multiply(new BigDecimal(param.get("addedWeightPrice").toString()).multiply(new BigDecimal(2)))
				.add(standard_freight);
				break;
				
			}
			
		}
		// 单运单折扣后运费
		freight.put("standard_freight", standard_freight);
		freight.put("balanced_freight", standard_freight.multiply(new BigDecimal(1).subtract(new BigDecimal(param.get("discount").toString()).divide(new BigDecimal(100)))));
		return freight;
		
	}
	
	/**
	* @Title: convertPricingWeight
	* @Description: TODO(转换计费重量)
	* @param param
	* @return BigDecimal    返回类型
	* @throws
	*/ 
	public static BigDecimal convertPricingWeight(BigDecimal pricingWeight, BigDecimal jfWeight){
		if(jfWeight.compareTo(new BigDecimal("0.1")) == 0){
			return pricingWeight.setScale(1, BigDecimal.ROUND_UP);
		} else if(jfWeight.compareTo(new BigDecimal("0.5")) == 0){
			BigDecimal pricingWeight_c = pricingWeight.setScale(0, BigDecimal.ROUND_HALF_UP);
			// 如数为1.4，小于1.5，向上4舍5入取整，得1，1.4大于1，可知小数位小于0.5
			if(pricingWeight.compareTo(pricingWeight_c) == 1){
				return pricingWeight_c.add(jfWeight);
			}
			// 如数为1.5，向上四舍五入取整得2，减1.5得0.5，可知小数位等于0.5
			if(pricingWeight_c.compareTo(pricingWeight) == 0
					|| pricingWeight_c.subtract(pricingWeight).compareTo(new BigDecimal("0.5")) == 0){
				return pricingWeight;
			}
			// 如数为1.6，大于1.5，向上4舍5入取整，得2，1.6小于2，可知小数位大于0.5
			if(pricingWeight.compareTo(pricingWeight_c) == -1){
				return pricingWeight_c;
			}
		} else if(jfWeight.compareTo(new BigDecimal(1)) == 0){
			return pricingWeight.setScale(0, BigDecimal.ROUND_UP);
		}
		return null;
	}
	
	/**
	 * 
	 * @Description: TODO(获取计费重量)
	 * @param param
	 * @return: BigDecimal  
	 * @author Ian.Huang 
	 * @date 2016年7月14日上午10:30:43
	 */
	@SuppressWarnings("unchecked")
	public static BigDecimal getPricingWeight(Map<String, Object> param){
		ExpressContractConfig eCC= (ExpressContractConfig)param.get("eCC");
		ExpressRawData eRD= (ExpressRawData)param.get("eRD");
		BigDecimal actualWeight= eRD.getWeight();
		BigDecimal volumn_weight= null;
		switch(eCC.getWeight_method()){
		case 1:
			// 实际重量
			return actualWeight;
		case 2:
			//　计全抛
			//　计抛计重取大值（不计半抛）
			//　计抛重量 = (长 * 宽 * 高) / 基数
			boolean flag_qp = false;
			//　计全抛重量大于实际重量且满足计半抛条件则计半抛
			//　计全抛重量是否大于实际重量
			// 是否存在计半抛条件
			List<JipaoConditionEx> jqpcList= (List<JipaoConditionEx>)param.get("jqpcList");
			BigDecimal attr_qp = null;
			if(CommonUtils.checkExistOrNot(jqpcList)) {
				for1: for(int i= 0; i< jqpcList.size(); i++){
					// 是否满足计半抛条件
					switch(jqpcList.get(i).getAttr()){
					case 0:
						attr_qp= eRD.getLength();
						break;
					case 1:
						attr_qp= eRD.getWidth();
						break;
					case 2:
						attr_qp= eRD.getHigth();
						break;
					}
					switch(jqpcList.get(i).getCompareMark()){
					case 0:
						if(attr_qp.compareTo(jqpcList.get(i).getCon()) == 1){
							flag_qp = true;
							break for1;
						}
						break;
					case 1:
						if(attr_qp.compareTo(jqpcList.get(i).getCon()) >= 0){
							flag_qp = true;
							break for1;
						}
						break;
					}
				}
			} else {
				// 无限制直接计算计半抛重量
				flag_qp= true;
			}
			if(flag_qp){
				volumn_weight= new BigDecimal(param.get("volumn_weight").toString());
				if(actualWeight.compareTo(volumn_weight) == 1){
					return actualWeight;
				} else {
					return volumn_weight;
				}
			}else{
				return actualWeight;
			}
		case 3:
			// 计半抛
			// 计抛计重取大值（计半抛）
			// 计抛重量 = (长 * 宽 * 高 / 基数 - 实际重量) * 百分比 + 实际重量
			boolean flag= false;
			//　计全抛重量大于实际重量且满足计半抛条件则计半抛
			//　计全抛重量是否大于实际重量
			// 是否存在计半抛条件
			List<JibanpaoConditionEx> jCExs= (List<JibanpaoConditionEx>)param.get("jCExs");
			BigDecimal attr = null;
			if(CommonUtils.checkExistOrNot(jCExs)) {
				for1: for(int i= 0; i< jCExs.size(); i++){
					// 是否满足计半抛条件
					switch(jCExs.get(i).getAttr()){
					case 0:
						attr= eRD.getLength();
						break;
					case 1:
						attr= eRD.getWidth();
						break;
					case 2:
						attr= eRD.getHigth();
						break;
					}
					switch(jCExs.get(i).getCompare_mark()){
					case 0:
						if(attr.compareTo(jCExs.get(i).getCon()) == 1){
							flag = true;
							break for1;
						}
						break;
					case 1:
						if(attr.compareTo(jCExs.get(i).getCon()) >= 0){
							flag = true;
							break for1;
						}
						break;
					}
				}
			} else {
				// 无限制直接计算计半抛重量
				flag= true;
			}
			// 是否满足计半抛条件
			if(flag){
				// 满足计半抛条件
				// 计算计半抛重量
				BigDecimal jbpWeight = new BigDecimal(param.get("volumn_weight").toString()).subtract(actualWeight).multiply(eCC.getPercent().divide(new BigDecimal(100))).add(actualWeight);
				if(jbpWeight.compareTo(actualWeight) == -1){
					// 计半抛重量小于实际重量，返回实际重量
					return actualWeight;
				} else {
					// 计半抛重量大等于实际重量，返回计半抛重量
					return jbpWeight;
				}
			} else {
				//　不满足
				// 返回实际重量
				return actualWeight;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @Description: TODO(检验合同配置有效性)
	 * @param eCC
	 * @return: Boolean  
	 * @author Ian.Huang 
	 * @date 2016年7月13日下午6:19:32
	 */
	public static Boolean checkValidity(ExpressContractConfig eCC){
		if(eCC.getWeight_method() == 0){
			return true;
			
		}
		return false;
		
	}
	
}