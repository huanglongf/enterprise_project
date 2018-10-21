package com.bt.lmis.balance.util;

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
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.bt.EPlatform;
import com.bt.OSinfo;
import com.bt.lmis.balance.common.Constant;
import com.bt.lmis.balance.dao.ConsumerRevenueEstimateMapper;
import com.bt.lmis.balance.dao.ExpressExpenditureEstimateMapper;
import com.bt.lmis.balance.model.EstimateParam;
import com.bt.lmis.balance.model.ExpressExpenditureEstimateProductTypeSummary;
import com.bt.lmis.balance.model.ExpressExpenditureEstimateSummary;
import com.bt.lmis.balance.model.ExpressFreightSummary;
import com.bt.lmis.balance.model.ManagementFeeDomainModel;
import com.bt.lmis.balance.model.TotalFreightDiscountDomainModel;
import com.bt.lmis.base.TABLE_ROLE;
import com.bt.lmis.base.spring.SpringUtils;
import com.bt.lmis.dao.TransportProductTypeMapper;
import com.bt.lmis.model.Ain;
import com.bt.lmis.model.ManagementOtherLadder;
import com.bt.lmis.model.OperationSettlementRule;
import com.bt.lmis.model.PerationfeeData;
import com.bt.lmis.model.SimpleTable;
import com.bt.lmis.model.StorageCharge;
import com.bt.lmis.model.TransportProductType;
import com.bt.lmis.service.AinService;
import com.bt.lmis.service.ManagementOtherLadderService;
import com.bt.lmis.service.OperationSettlementRuleService;
import com.bt.lmis.service.PerationfeeDataService;
import com.bt.lmis.service.StorageChargeService;
import com.bt.utils.BaseConst;
import com.bt.utils.CommonUtils;
import com.bt.utils.FileUtil;
import com.bt.utils.IntervalValidationUtil;
import com.bt.utils.POIUtil;
import com.bt.utils.ReportUtils;
import com.bt.utils.UUIDUtils;

/** 
 * @ClassName: EstimateUtil
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年5月11日 下午2:03:55 
 * 
 */
@SuppressWarnings("unchecked")
public class EstimateUtil {

	private static ExpressExpenditureEstimateMapper<T> expressExpenditureEstimateMapper=(ExpressExpenditureEstimateMapper<T>)SpringUtils.getBean("expressExpenditureEstimateMapper");
	private static ConsumerRevenueEstimateMapper<T> consumerRevenueEstimateMapper=(ConsumerRevenueEstimateMapper<T>)SpringUtils.getBean("consumerRevenueEstimateMapper");
	//耗材费服务类
	private static AinService<Ain> ainService = (AinService<Ain>)SpringUtils.getBean("ainServiceImpl");
	private static StorageChargeService<StorageCharge> storageChargeService = (StorageChargeService<StorageCharge>)SpringUtils.getBean("storageChargeServiceImpl");
	// 操作费结算规则表服务类
	private static OperationSettlementRuleService<OperationSettlementRule> operationSettlementRuleService 
				= SpringUtils.getBean("operationSettlementRuleServiceImpl");
	// 操作费原始数据服务类
	private static PerationfeeDataService<PerationfeeData> perationfeeDataService 
				= SpringUtils.getBean("perationfeeDataServiceImpl");
	//管理费阶梯服务类
	private static ManagementOtherLadderService managementOtherLadderService 
				= SpringUtils.getBean("managementOtherLadderServiceImpl");
	/**
	 * @Title: batchNumberGenerator
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param fromDate
	 * @param toDate
	 * @return: String
	 * @author: Ian.Huang
	 * @date: 2017年5月19日 下午3:04:49
	 */
	public static String batchNumberGenerator(String fromDate, String toDate) {
		return fromDate.replace("-", "").substring(2)+toDate.replace("-", "").substring(2)+UUIDUtils.getUUID8L();
		
	}
	
	/**
	 * @Title: ensureTotalFreightDiscount
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param totalFreightDiscountDomainModel
	 * @param eeepts
	 * @return: BigDecimal
	 * @author: Ian.Huang
	 * @date: 2017年5月11日 下午3:06:05
	 */
	public static BigDecimal ensureTotalFreightDiscount(List<TotalFreightDiscountDomainModel> totalFreightDiscountDomainModel, ExpressExpenditureEstimateProductTypeSummary eeepts) {
		// 总运费或总单量
		BigDecimal sum = null;
		// 阶梯类型
		switch(totalFreightDiscountDomainModel.get(0).getLadderType()){
		case 1:
			// 无阶梯
			return totalFreightDiscountDomainModel.get(0).getDomainValue().divide(new BigDecimal(100));
		case 2:
			// 总运费超出部分阶梯
			sum = totalFreightDiscountDomainModel.get(0).getInsuranceContain() == 0 ? eeepts.getAfterDiscount().add(eeepts.getInsurance()) : eeepts.getAfterDiscount();
			//
			boolean flag = false;
			// 总运费折扣金额
			BigDecimal totalDiscount = new BigDecimal(0);
			for(int i=0; i<totalFreightDiscountDomainModel.size(); i++){
				if(CommonUtil.isInDomain(totalFreightDiscountDomainModel.get(i), sum)){
					totalDiscount = totalDiscount.add(sum.subtract(totalFreightDiscountDomainModel.get(i).getNum1())
							.multiply(totalFreightDiscountDomainModel.get(i).getDomainValue().divide(new BigDecimal(100))));
					flag = true;
					
				} else {
					if(CommonUtils.checkExistOrNot(totalFreightDiscountDomainModel.get(i).getNum2())
							&& sum.compareTo(totalFreightDiscountDomainModel.get(i).getNum2()) >= 0) {
						totalDiscount = totalDiscount.add(
								totalFreightDiscountDomainModel.get(i).getNum2().subtract(totalFreightDiscountDomainModel.get(i).getNum1())
								.multiply(totalFreightDiscountDomainModel.get(i).getDomainValue().divide(new BigDecimal(100))));
						
					} else {
						break;
						
					}
					
				}
				
			}
			if(flag){
				return totalDiscount.divide(sum, 6, BigDecimal.ROUND_HALF_UP);
				
			}break;
		case 3:
			// 总费用总折扣阶梯
			sum = totalFreightDiscountDomainModel.get(0).getInsuranceContain() == 0 ? eeepts.getAfterDiscount().add(eeepts.getInsurance()) : eeepts.getAfterDiscount();
			for(int i=0; i<totalFreightDiscountDomainModel.size(); i++){
				if(CommonUtil.isInDomain(totalFreightDiscountDomainModel.get(i), sum)){
					return totalFreightDiscountDomainModel.get(i).getDomainValue().divide(new BigDecimal(100));
					
				}
				
			}break;
		case 4:
			// 单量折扣阶梯
			sum= new BigDecimal(eeepts.getOrderNum());
			for(int i=0; i<totalFreightDiscountDomainModel.size(); i++){
				if(CommonUtil.isInDomain(totalFreightDiscountDomainModel.get(i), sum)){
					return totalFreightDiscountDomainModel.get(i).getDomainValue().divide(new BigDecimal(100));
					
				}
				
			}break;
			
		}
		return null;
		
	}
	
	/**
	 * @Title: ensureTotalFreightDiscount
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param totalFreightDiscountDomainModel
	 * @param efs
	 * @return: BigDecimal
	 * @author: Ian.Huang
	 * @date: 2017年5月17日 下午5:35:44
	 */
	public static BigDecimal ensureTotalFreightDiscount(List<TotalFreightDiscountDomainModel> totalFreightDiscountDomainModel, ExpressFreightSummary efs) {
		//
		BigDecimal amount=totalFreightDiscountDomainModel.get(0).getInsuranceContain() == 0 ? efs.getTotalFreight().add(efs.getTotalInsurance()) : efs.getTotalFreight();
		// 总运费或总单量
		BigDecimal sum = null;
		// 阶梯类型
		switch(totalFreightDiscountDomainModel.get(0).getLadderType()){
		case 1:
			// 无阶梯
			return amount.multiply(totalFreightDiscountDomainModel.get(0).getDomainValue()).divide(new BigDecimal(100));
		case 2:
			// 总运费超出部分阶梯
			sum=amount;
			//
			boolean flag = false;
			// 总运费折扣金额
			BigDecimal totalDiscount = new BigDecimal(0);
			for(int i=0; i<totalFreightDiscountDomainModel.size(); i++){
				if(CommonUtil.isInDomain(totalFreightDiscountDomainModel.get(i), sum)){
					totalDiscount = totalDiscount.add(sum.subtract(totalFreightDiscountDomainModel.get(i).getNum1())
							.multiply(totalFreightDiscountDomainModel.get(i).getDomainValue().divide(new BigDecimal(100))));
					flag = true;
					
				} else {
					if(CommonUtils.checkExistOrNot(totalFreightDiscountDomainModel.get(i).getNum2())
							&& sum.compareTo(totalFreightDiscountDomainModel.get(i).getNum2()) >= 0) {
						totalDiscount = totalDiscount.add(
								totalFreightDiscountDomainModel.get(i).getNum2().subtract(totalFreightDiscountDomainModel.get(i).getNum1())
								.multiply(totalFreightDiscountDomainModel.get(i).getDomainValue().divide(new BigDecimal(100))));
						
					} else {
						break;
						
					}
					
				}
				
			}
			if(flag){
				return totalDiscount;
				
			}break;
		case 3:
			// 总费用总折扣阶梯
			sum=amount;
			for(int i=0; i<totalFreightDiscountDomainModel.size(); i++){
				if(CommonUtil.isInDomain(totalFreightDiscountDomainModel.get(i), sum)){
					return sum.multiply(totalFreightDiscountDomainModel.get(i).getDomainValue()).divide(new BigDecimal(100));
					
				}
				
			}break;
		case 4:
			// 单量折扣阶梯
			sum=new BigDecimal(efs.getOrderNum());
			for(int i=0; i<totalFreightDiscountDomainModel.size(); i++){
				if(CommonUtil.isInDomain(totalFreightDiscountDomainModel.get(i), sum)){
					return amount.multiply(totalFreightDiscountDomainModel.get(i).getDomainValue()).divide(new BigDecimal(100));
					
				}
				
			}break;
			
		}
		return null;
		
	}
	
	/**
	 * @Title: generateExpressExpenditureEstimate
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param param
	 * @throws Exception
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年5月15日 下午1:45:02
	 */
	@SuppressWarnings("resource")
	public static void generateExpressExpenditureEstimate(EstimateParam param) throws Exception {
		// 文件生成路径
		String fileGenerationPath=CommonUtils.getAllMessage("config", "BALANCE_ESTIMATE_EXPRESS_" + OSinfo.getOSname()) + param.getBatchNumber();
		if(OSinfo.getOSname().equals(EPlatform.Linux) || OSinfo.getOSname().equals(EPlatform.Mac_OS_X)) {
			fileGenerationPath+="/";
			
		} else if(OSinfo.getOSname().equals(EPlatform.Windows)) {
			fileGenerationPath+="\\";
			
		}
		// 路径补全
		FileUtil.isExistPath(fileGenerationPath);
		//
		String fullFileName=fileGenerationPath+param.getContract().getContractOwnerName()+param.getFromDate()+Constant.SYM_SUBTRACT+param.getToDate()+"预估报表"+Constant.SYM_POINT+Constant.FILE_TYPE_SUFFIX_XLSX;
		// 输出文件流
		FileOutputStream output=new FileOutputStream(new File(fullFileName));
		// 内存中保留 10000 条数据，以免内存溢出，其余写入硬盘  
		SXSSFWorkbook wb=new SXSSFWorkbook(10000);
		try {
			// 产品类型区分
			List<TransportProductType> productType= ((TransportProductTypeMapper<T>)SpringUtils.getBean("transportProductTypeMapper")).findByTransportVendor(true, param.getContract().getContractOwner());
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
			// 查询汇总集合
			param.getParam().put("logTitle",param.getContract().getContractOwnerName()+"预估汇总查询");
			List<ExpressExpenditureEstimateSummary> resultList= expressExpenditureEstimateMapper.getExpressExpenditureEstimateSummary(param);
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
				ExpressExpenditureEstimateSummary eees= null;
				String uk= "";
				// 运单量
				Integer order_num= 0;
				// 保价费
				BigDecimal insurance= new BigDecimal(0);
				// 总费用
				BigDecimal total_fee= new BigDecimal(0);
				for(int i= 0; i< resultList.size(); i++) {
					eees= resultList.get(i);
					if(!uk.equals(eees.getStoreName() + eees.getWarehouseName())){
						if(i != 0){
							row.put("order_num", order_num);
							total.put("order_num", Integer.parseInt(total.get("order_num").toString()) + order_num);
							row.put("insurance", insurance);
							total.put("insurance", new BigDecimal(total.get("insurance").toString()).add(insurance));
							row.put("total_fee", total_fee);
							total.put("total_fee", new BigDecimal(total.get("total_fee").toString()).add(total_fee));
							tableContent.add(row);
							
						}
						uk= eees.getStoreName() + eees.getWarehouseName();
						// 初始化一行数据
						row= new LinkedHashMap<String, Object>();
						row.put("cost_center", eees.getCostCenter());
						row.put("store", eees.getStoreName());
						row.put("warehouse", eees.getWarehouseName());
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
					order_num+=eees.getOrderNum();
					insurance=insurance.add(eees.getInsurance());
					total_fee=total_fee.add(eees.getTotalFee());
					if(flag){
						row.put("standard_freight_" + eees.getProductTypeCode(), eees.getProductTypeFreight());
						total.put("standard_freight_" + eees.getProductTypeCode(), new BigDecimal(total.get("standard_freight_" + eees.getProductTypeCode()).toString()).add(eees.getProductTypeFreight()));
						row.put("discount_" + eees.getProductTypeCode(), eees.getProductTypeDiscount());
						total.put("discount_" + eees.getProductTypeCode(), new BigDecimal(total.get("discount_" + eees.getProductTypeCode()).toString()).add(eees.getProductTypeDiscount()));
						
					} else {
						row.put("standard_freight", eees.getProductTypeFreight());
						total.put("standard_freight", new BigDecimal(total.get("standard_freight").toString()).add(eees.getProductTypeFreight()));
						row.put("discount", eees.getProductTypeDiscount());
						total.put("discount", new BigDecimal(total.get("discount").toString()).add(eees.getProductTypeDiscount()));
						row.put("total_discount", eees.getProductTypeTotalDiscount());
						total.put("total_discount", new BigDecimal(total.get("total_discount").toString()).add(eees.getProductTypeTotalDiscount()));
						
					}
					if(i+1 == resultList.size()){
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
				wb=ReportUtils.addSheet(wb, new SimpleTable(TABLE_ROLE.MASTER, "汇总", tableHeader, tableContent));
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
				// 初始化分页参数
				param.getParam().put("start", 0);
				param.getParam().put("length", Constant.SINGLE_SHEET_MAX_NUM);
				// 计算明细总量
				param.getParam().put("logTitle",param.getContract().getContractOwnerName()+"预估明细总量查询");
				Integer cycleNum=CommonUtils.paginationCount(expressExpenditureEstimateMapper.countExpressExpenditureEstimateDetail(param), Constant.SINGLE_SHEET_MAX_NUM);
				for(int i=1; i<=cycleNum; i++) {
					param.getParam().put("logTitle",param.getContract().getContractOwnerName()+"预估明细-"+i+"查询");
					wb=ReportUtils.addSheet(wb, new SimpleTable(TABLE_ROLE.SLAVE, "明细-"+i, tableHeader, expressExpenditureEstimateMapper.getExpressExpenditureEstimateDetail(param)));
					System.out.println("********************明细-"+i+"已生成********************");
					param.getParam().put("start", i*Constant.SINGLE_SHEET_MAX_NUM);
					
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
		
	}
	
	/**
	 * @Title: ensureManagementFee
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param managementFeeDomainModel
	 * @param efs
	 * @param totalFreightDiscount
	 * @return: BigDecimal
	 * @author: Ian.Huang
	 * @date: 2017年5月17日 下午4:40:13
	 */
	public static BigDecimal ensureManagementFee(List<ManagementFeeDomainModel> managementFeeDomainModel, ExpressFreightSummary efs, BigDecimal totalFreightDiscount) {
		// 用于计算管理费的总量
		BigDecimal temp=new BigDecimal(0);
		// 运费包含
		if(managementFeeDomainModel.get(0).isFreight()){
			temp=temp.add(efs.getTotalFreight());
			
		}
		// 保价费包含
		if(managementFeeDomainModel.get(0).isInsurance()){
			temp=temp.add(efs.getTotalInsurance());
			
		}
		// 除去总运费折扣
		temp=temp.subtract(totalFreightDiscount);
		// 阶梯类型
		switch (managementFeeDomainModel.get(0).getLadderType()) {
		//无阶梯
		case 1:
			return temp.multiply(managementFeeDomainModel.get(0).getDomainValue()).divide(new BigDecimal(100));
		case 2:
			for(int i=0; i<managementFeeDomainModel.size(); i++){
				if(CommonUtil.isInDomain(managementFeeDomainModel.get(i), temp)){
					return temp.multiply(managementFeeDomainModel.get(i).getDomainValue()).divide(new BigDecimal(100));
					
				}
				
			}break;
		case 3:
			boolean flag = false;
			// 总运费折扣金额
			BigDecimal managementFee = new BigDecimal(0);
			for(int i=0; i<managementFeeDomainModel.size(); i++){
				if(CommonUtil.isInDomain(managementFeeDomainModel.get(i), temp)){
					managementFee = managementFee.add(temp.subtract(managementFeeDomainModel.get(i).getNum1())
							.multiply(managementFeeDomainModel.get(i).getDomainValue().divide(new BigDecimal(100))));
					flag = true;
					
				} else {
					if(CommonUtils.checkExistOrNot(managementFeeDomainModel.get(i).getNum2())
							&& temp.compareTo(managementFeeDomainModel.get(i).getNum2()) >= 0) {
						managementFee = managementFee.add(managementFeeDomainModel.get(i).getNum2().subtract(managementFeeDomainModel.get(i).getNum1())
								.multiply(managementFeeDomainModel.get(i).getDomainValue().divide(new BigDecimal(100))));
						
					} else {
						break;
						
					}
					
				}
				
			}
			if(flag){
				return managementFee;
				
			}break;
			
		}		
		return null;
		
	}
	
	/**
	 * @Title: generateConsumerRevenueEstimate
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param param
	 * @throws Exception
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年5月18日 下午1:45:46
	 */
	@SuppressWarnings("resource")
	public static void generateConsumerRevenueEstimate(EstimateParam param) throws Exception {
		// 文件生成路径
		String fileGenerationPath=CommonUtils.getAllMessage("config", "BALANCE_ESTIMATE_CUSTOMER_" + OSinfo.getOSname()) + param.getBatchNumber();
		if(OSinfo.getOSname().equals(EPlatform.Linux) || OSinfo.getOSname().equals(EPlatform.Mac_OS_X)) {
			fileGenerationPath+="/";
			
		} else if(OSinfo.getOSname().equals(EPlatform.Windows)) {
			fileGenerationPath+="\\";
			
		}
		// 路径补全
		FileUtil.isExistPath(fileGenerationPath);
		// 输出文件流
		FileOutputStream output=new FileOutputStream(new File(fileGenerationPath+param.getContract().getContractOwnerName()+param.getFromDate()+Constant.SYM_SUBTRACT+param.getToDate()+"预估报表"+Constant.SYM_POINT+Constant.FILE_TYPE_SUFFIX_XLSX));
		// 内存中保留 10000 条数据，以免内存溢出，其余写入硬盘  
		SXSSFWorkbook wb=new SXSSFWorkbook(10000);
		//客户姓名
		String client_name = "";
		try {
			// 根据contractOwner查询客户下所有店铺
			param.getParam().put("sql", new StringBuffer()
					.append("select c.id as cid,c.client_name as client_name,s.id as sid,s.store_name as store_name from tb_client c ")
					.append("left join tb_store s on c.id=s.client_id ")
					.append("where c.client_code='"+param.getContract().getContractOwner()+"' ").toString());
			param.getParam().put("logTitle", param.getContract().getContractOwnerName()+"下所属店铺查询");
			List<Map<String, Object>> store=consumerRevenueEstimateMapper.querySQL(param);
			if(CommonUtils.checkExistOrNot(store)){
				client_name= store.get(0).get("client_name").toString();
				
			}
			// 店铺名称集合
			StringBuffer storeNames=new StringBuffer();
			// 循环客户下所有店铺
			if(store != null && store.size() > 0){
				for (int i= 0; i<store.size(); i++) {
					storeNames.append("'"+store.get(i).get("store_name")+"'");
					if(i+1!=store.size()) {
						storeNames.append(", ");
						
					}
					
				}
				
			} else {
				return;
			}
			
			
			// 报表内容
			List<Map<String, Object>> max_list= new ArrayList<>();
			//仓储费Start---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
			//仓储费汇总记录聚合
//			param.getParam().put("sql", "select '" + param.getFromDate()+ "-" + param.getToDate() +"' 结算周期 ,"+
//					"area_qty as 面积数量," +
//					"area_cost as 按面积结算,piece_qty as 件数,piece_cost as 件数结算 from bal_storage_data_estimate " +
//					"where contract_id="+param.getContract().getId()+" and batch_number = '"+param.getBatchNumber()+"'");
			param.getParam().put("sql", "select '" + param.getFromDate()+ "-" + param.getToDate() +"' 结算周期 ,"+
					"sum(fixed_qty) as 固定数量,sum(fixed_cost) as 按固定费用结算," +
					"sum(area_qty) as 面积数量,sum(area_cost) as 按面积结算," +
					"sum(piece_qty) as 件数,sum(piece_cost) as 件数结算 " 
					+ "from bal_storage_data_estimate " +
					"where contract_id="+param.getContract().getId()+" and batch_number = '"+param.getBatchNumber()+"' "+ 
					"group by fixed_cost,area_cost,piece_cost,tray_cost" );
			param.getParam().put("logTitle", param.getContract().getContractOwnerName()+"仓储费汇总查询");
			List<Map<String, Object>> storageCharge_lists=consumerRevenueEstimateMapper.querySQL(param);
			Map<String, Object> map_1= new LinkedHashMap<>();
			map_1.put("text1", "仓储费");
			if(CollectionUtils.isNotEmpty(storageCharge_lists)){
				List<Map<String,Object>> small_list = new ArrayList<Map<String,Object>>();
				
				BigDecimal storageQty = new BigDecimal("0");
				BigDecimal storageCost = new BigDecimal("0");
				
				Map<String, Object> small_list_row = null;
				for (Map<String, Object> storageCharge_list : storageCharge_lists) {
					small_list_row = new LinkedHashMap<>();
					if(null!= storageCharge_list.get("按面积结算")){
						small_list_row.put("text1", "按面积结算");
						small_list_row.put("text2", storageCharge_list.get("面积数量"));
						small_list_row.put("text4",storageCharge_list.get("按面积结算"));
						storageQty=storageQty.add(new BigDecimal(storageCharge_list.get("面积数量").toString()));
						storageCost=storageCost.add(new BigDecimal(storageCharge_list.get("按面积结算").toString()));
					} else if(null!= storageCharge_list.get("件数结算")){
						small_list_row.put("text1", "件数结算");
						small_list_row.put("text2", storageCharge_list.get("件数"));
						small_list_row.put("text4", storageCharge_list.get("件数结算"));
						storageQty=storageQty.add(new BigDecimal(storageCharge_list.get("件数").toString()));
						storageCost=storageCost.add(new BigDecimal(storageCharge_list.get("件数结算").toString()));
					} else if(null!= storageCharge_list.get("按固定费用结算")){
						small_list_row.put("text1", "按固定费用结算");
						small_list_row.put("text2", storageCharge_list.get("固定数量"));
						small_list_row.put("text4", storageCharge_list.get("按固定费用结算"));
						storageQty=storageQty.add(new BigDecimal(storageCharge_list.get("固定数量").toString()));
						storageCost=storageCost.add(new BigDecimal(storageCharge_list.get("按固定费用结算").toString()));
					}
					if(MapUtils.isNotEmpty(small_list_row)) {
						small_list_row.put("text3", "");
						small_list_row.put("text5", "");
						small_list.add(small_list_row);
					}
				}
				
				map_1.put("text2", "");//storageQty
				map_1.put("text4", storageCost);
				//添加管理费小列表
				BigDecimal ccfFee = (map_1.get("text4") != null && 
						StringUtils.isNotBlank(map_1.get("text4").toString())) ? 
								new BigDecimal(map_1.get("text4").toString()) : new BigDecimal("0");
				Map<String, Object> map_gourp_mangement_fee = new LinkedHashMap<>();
				map_gourp_mangement_fee.put("text1", "管理费");
				map_gourp_mangement_fee.put("text2", "");
				map_gourp_mangement_fee.put("text3", "");
				BigDecimal managementFee = getManagementFee(ccfFee,"ccf",param.getContract().getId());
				map_gourp_mangement_fee.put("text4", managementFee.toString());
				map_gourp_mangement_fee.put("text5", "");
				small_list.add(map_gourp_mangement_fee);
				
				//将明细放入text6 -仓储费
				map_1.put("text4", ccfFee.add(managementFee).toString());
				map_1.put("text6", small_list);
				
			}else{
				map_1.put("text2", "暂无结算数据");
				map_1.put("text3", "");
				map_1.put("text4", "");
				
			}
			map_1.put("text3", "");
			map_1.put("text5", "");
			max_list.add(map_1);
			//仓储费Sheet页
			Map<String, Object> ccfMap= new LinkedHashMap<>();
			if(CommonUtils.checkExistOrNot(storageCharge_lists)){
				ccfMap.put("sheet_name", client_name+ "-仓储费");
				Map<String, String> ccf_title= new LinkedHashMap<>();
				ccf_title.put("结算周期", "结算周期");
				ccf_title.put("面积数量", "面积数量");
				ccf_title.put("按面积结算", "按面积结算");
				ccf_title.put("件数", "件数");
				ccf_title.put("件数结算", "件数结算");
				ccfMap.put("sheet_title", ccf_title);
				ccfMap.put("sheet_list", storageCharge_lists);
//				wb= POIUtil.create_sheet(wb, ccfMap, false);
				
				
				

				//仓储费信息
				List<StorageCharge> scList = storageChargeService
												.findByCBID(String.valueOf(param.getContract().getId()));
				Integer ssc_sc_type = null ;
				StorageCharge storageCharge = null;
				for (int i = 0; i < scList.size(); i++) {
					storageCharge = scList.get(i);
					ssc_sc_type = storageCharge.getSsc_sc_type();

					if (ssc_sc_type != null && ssc_sc_type.equals(4)) {
						break;
					}
					
				}


				if (ssc_sc_type != null && ssc_sc_type.equals(4)) {
					//4按件数结算
					wb= POIUtil.create_sheet(wb, add_ccf_sheet(storeNames.toString(),storageCharge,
							client_name+ "-仓储费-按件数结算明细", param), false);
				} else {
					ccfMap.put("sheet_name", client_name+ "-仓储费结算");
					wb= POIUtil.create_sheet(wb, ccfMap, false);
				}
				
			}
			//仓储费End---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
			//操作费Start---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
			//查询操作费汇总SQL
			StringBuffer sb_get_czf_lists= new StringBuffer();
			sb_get_czf_lists.append("select cb.contract_name as 合同名称,cb.id, hzData.ib_fee as B2B入库,hzData.btb_qty as btb_qty, ");
			sb_get_czf_lists.append("hzData.btc_fee as B2C出库, hzData.ib_qty as ib_qty, ");
			sb_get_czf_lists.append("hzData.btb_fee as B2B出库,hzData.btc_qty as btc_qty, ");
			sb_get_czf_lists.append("hzData.return_fee 销售退货,hzData.return_qty as return_qty, ");
			sb_get_czf_lists.append("(hzData.ib_fee+hzData.btc_fee+hzData.btb_fee+hzData.return_fee) as 按实际使用量结算 ");
			sb_get_czf_lists.append(",xse_fee 按销售额百分比结算 ");
			sb_get_czf_lists.append(",gd_fee 按固定费用结算 ");
			sb_get_czf_lists.append("from bal_operationfee_data_estimate hzData ");
			sb_get_czf_lists.append("left join tb_contract_basicinfo cb on hzData.contract_id=cb.id ");
			sb_get_czf_lists.append("where 1=1 ");
			sb_get_czf_lists.append("and cb.id="+ param.getContract().getId()+ " ");
			sb_get_czf_lists.append("and hzData.batch_number='"+ param.getBatchNumber() + "' ");
			param.getParam().put("sql", sb_get_czf_lists.toString());
			param.getParam().put("logTitle", param.getContract().getContractOwnerName()+"操作费汇总查询");
			List<Map<String, Object>> czf_lists=consumerRevenueEstimateMapper.querySQL(param);
			
			Map<String, Object> map_2= new LinkedHashMap<>();
			if(CommonUtils.checkExistOrNot(czf_lists)){
				List<Map<String, Object>> small_list= new ArrayList<>();
				if(new BigDecimal(czf_lists.get(0).get("按固定费用结算").toString()).compareTo(new BigDecimal(0))!=0){
					map_2.put("text1", "操作费");
					map_2.put("text2", "按固定费用结算");
					map_2.put("text3", "");
					map_2.put("text4", czf_lists.get(0).get("按固定费用结算"));
					map_2.put("text5", "");

					//添加管理费小列表
					BigDecimal czfFee = (map_2.get("text4") != null && 
							StringUtils.isNotBlank(map_2.get("text4").toString())) ? 
									new BigDecimal(map_2.get("text4").toString()) : new BigDecimal("0");
					Map<String, Object> map_gourp_mangement_fee = new LinkedHashMap<>();
					map_gourp_mangement_fee.put("text1", "管理费");
					map_gourp_mangement_fee.put("text2", "");
					map_gourp_mangement_fee.put("text3", "");
					BigDecimal managementFee = getManagementFee(czfFee,"czf",param.getContract().getId());
					map_gourp_mangement_fee.put("text4", managementFee.toString());
					map_gourp_mangement_fee.put("text5", "");
					small_list.add(map_gourp_mangement_fee);

					map_2.put("text4", czfFee.add(managementFee).toString());
					map_2.put("text6", small_list);
					
				}else if(new BigDecimal(czf_lists.get(0).get("按销售额百分比结算").toString()).compareTo(new BigDecimal(0))!=0){
					map_2.put("text1", "操作费");
					map_2.put("text2", "按销售额百分比结算");
					map_2.put("text3", "");
					map_2.put("text4", czf_lists.get(0).get("按销售额百分比结算"));
					map_2.put("text5", "");

					//添加管理费小列表
					BigDecimal czfFee = (map_2.get("text4") != null && 
							StringUtils.isNotBlank(map_2.get("text4").toString())) ? 
									new BigDecimal(map_2.get("text4").toString()) : new BigDecimal("0");
					Map<String, Object> map_gourp_mangement_fee = new LinkedHashMap<>();
					map_gourp_mangement_fee.put("text1", "管理费");
					map_gourp_mangement_fee.put("text2", "");
					map_gourp_mangement_fee.put("text3", "");
					BigDecimal managementFee = getManagementFee(czfFee,"czf",param.getContract().getId());
					map_gourp_mangement_fee.put("text4", managementFee.toString());
					map_gourp_mangement_fee.put("text5", "");
					small_list.add(map_gourp_mangement_fee);

					map_2.put("text4", czfFee.add(managementFee).toString());
					map_2.put("text6", small_list);
				}else if(new BigDecimal(czf_lists.get(0).get("按实际使用量结算").toString()).compareTo(new BigDecimal(0))!=0){
					map_2.put("text1", "操作费");
					map_2.put("text2", "");
					map_2.put("text3", "按实际使用量结算");
					map_2.put("text4", czf_lists.get(0).get("按实际使用量结算"));
					map_2.put("text5", "");
					if(new BigDecimal(czf_lists.get(0).get("B2B出库").toString()).compareTo(new BigDecimal(0))!=0){
						Map<String, Object> map_gourp_4 = new LinkedHashMap<>();
						map_gourp_4.put("text1", "B2B出库");
						map_gourp_4.put("text2", czf_lists.get(0).get("btb_qty"));
						map_gourp_4.put("text3", "");
						map_gourp_4.put("text4", czf_lists.get(0).get("B2B出库"));
						map_gourp_4.put("text5", "");
						small_list.add(map_gourp_4);
					}
					if(new BigDecimal(czf_lists.get(0).get("B2B入库").toString()).compareTo(new BigDecimal(0))!=0){
						Map<String, Object> map_gourp_3 = new LinkedHashMap<>();
						map_gourp_3.put("text1", "B2B入库");
						map_gourp_3.put("text2", czf_lists.get(0).get("ib_qty"));
						map_gourp_3.put("text3", "");
						map_gourp_3.put("text4", czf_lists.get(0).get("B2B入库"));
						map_gourp_3.put("text5", "");
						small_list.add(map_gourp_3);
					}
					if(new BigDecimal(czf_lists.get(0).get("B2C出库").toString()).compareTo(new BigDecimal(0))!=0){
						Map<String, Object> map_gourp_2 = new LinkedHashMap<>();
						map_gourp_2.put("text1", "B2C出库");
						map_gourp_2.put("text2", czf_lists.get(0).get("btc_qty"));
						map_gourp_2.put("text3", "");
						map_gourp_2.put("text4", czf_lists.get(0).get("B2C出库"));
						map_gourp_2.put("text5", "");
						small_list.add(map_gourp_2);
					}
					if(new BigDecimal(czf_lists.get(0).get("销售退货").toString()).compareTo(new BigDecimal(0))!=0){
						Map<String, Object> map_gourp_1 = new LinkedHashMap<>();
						map_gourp_1.put("text1", "销售退货");
						map_gourp_1.put("text2", czf_lists.get(0).get("return_qty"));
						map_gourp_1.put("text3", "");
						map_gourp_1.put("text4", czf_lists.get(0).get("销售退货"));
						map_gourp_1.put("text5", "");
						small_list.add(map_gourp_1);
					}
					//添加管理费小列表
					BigDecimal czfFee = (map_2.get("text4") != null && 
							StringUtils.isNotBlank(map_2.get("text4").toString())) ? 
									new BigDecimal(map_2.get("text4").toString()) : new BigDecimal("0");
					Map<String, Object> map_gourp_mangement_fee = new LinkedHashMap<>();
					map_gourp_mangement_fee.put("text1", "管理费");
					map_gourp_mangement_fee.put("text2", "");
					map_gourp_mangement_fee.put("text3", "");
					BigDecimal managementFee = getManagementFee(czfFee,"czf",param.getContract().getId());
					map_gourp_mangement_fee.put("text4", managementFee.toString());
					map_gourp_mangement_fee.put("text5", "");
					small_list.add(map_gourp_mangement_fee);

					map_2.put("text4", czfFee.add(managementFee).toString());
					map_2.put("text6", small_list);
					
				}
				
			}else{
				map_2.put("text1", "操作费");
				map_2.put("text2", "暂无结算数据");
				map_2.put("text3", "");
				map_2.put("text4", "");
				map_2.put("text5", "");
				
			}
			max_list.add(map_2);
			//操作费Sheet页
			if(CommonUtils.checkExistOrNot(czf_lists)){
				// 获取操作费规则
				List<OperationSettlementRule> oSRList = operationSettlementRuleService.findByCBID(
						String.valueOf(param.getContract().getId()));
				//B2C出库操作费
				int a= 0;
				if(new BigDecimal(czf_lists.get(0).get("B2C出库").toString()).compareTo(new BigDecimal(0))!=0){
					StringBuffer sqlSB = new StringBuffer();

					if(param.getContract().getId() == 236) {
						sqlSB.append("select count(1) from (");
					}
					
					sqlSB.append("select count(1) from tb_operationfee_data where store_name in ("+
							storeNames.toString()+") and job_type in ('退换货出库','销售出库') " +
							"and TO_DAYS(operation_time) >= TO_DAYS('"+ param.getFromDate() +"') and TO_DAYS(operation_time)"+
							" <= TO_DAYS('" + param.getToDate() + "')");
					
					if(param.getContract().getId() == 236) {
						sqlSB.append(" group by job_orderno");
						sqlSB.append(") x");
					}
					
					param.getParam().put("sql", sqlSB.toString());
					param.getParam().put("logTitle", client_name + "B2C出库操作费明细");
					int count1 = consumerRevenueEstimateMapper.countSQL(param);
					a= 0;
					for(int j= 1; j< forCount(count1, BaseConst.excel_pageSize) + 1; j++) {
						wb = POIUtil.create_sheet(wb, add_czf_sheet(storeNames, 
												"'退换货出库','销售出库'", client_name + "-B2C出库操作费明细-"+ j, 
												param, " limit "+ a+ ","+BaseConst.excel_pageSize), false);
						a= j*BaseConst.excel_pageSize;
						
					}
					
					//tb_operationfee_data_detail数据查询
					if (oSRList != null && oSRList.size() >= 1) {
						OperationSettlementRule oSR = oSRList.get(0);
					
						// 2.B2C出库操作费
						Integer osr_btcobop_fee = oSR.getOsr_btcobop_fee();
						if (osr_btcobop_fee == 1) {
							// 按件出库
							Integer osr_btcobop_numfee = oSR.getOsr_btcobop_numfee();
							if (osr_btcobop_numfee == 3) {
								// 总件数阶梯
								String osr_btcobopbill_tobtb_tableid = oSR.getOsr_btcobopbill_tobtb_tableid();
								List<Map<String, Object>> tbtList = operationSettlementRuleService
										.queryTOTList(osr_btcobopbill_tobtb_tableid);
								
								StringBuffer czf_b2cob_sql= new StringBuffer();
								czf_b2cob_sql.append("select sum(out_num) ");
								czf_b2cob_sql.append(" from ");
								czf_b2cob_sql.append("tb_operationfee_data x where store_name in ("+
										storeNames.toString()+") and job_type in ('退换货出库','销售出库')"+
										" and TO_DAYS(operation_time) >= TO_DAYS('" +param.getFromDate()+"') and TO_DAYS(operation_time)"+
										" <= TO_DAYS('"+ param.getToDate() +"') and warehouse_code != 'SHWH215'");
								
								param.getParam().put("sql", czf_b2cob_sql.toString());
								param.getParam().put("logTitle", client_name + "B2C出库操作费");
								BigDecimal B2COutboundNum = consumerRevenueEstimateMapper.sumSQL(param);		//B2C出库操作数量
								
								Map<String, Object> map = getPrice(tbtList, B2COutboundNum, "2", "obt_interval", "obt_price",null, "obt_discount");
								
								param.getParam().put("sql", "select count(1) from (select related_orderno from tb_operationfee_data where store_name in ("+
										storeNames.toString()+") and job_type in ('退换货出库','销售出库') " +
										"and TO_DAYS(operation_time) >= TO_DAYS('"+ param.getFromDate() +"') and TO_DAYS(operation_time)"+
										" <= TO_DAYS('" + param.getToDate() + "') group by related_orderno) x ");
								param.getParam().put("logTitle", client_name + "B2C出库操作费");
								count1 = consumerRevenueEstimateMapper.countSQL(param);
								a= 0;
								for(int j= 1; j< forCount(count1, BaseConst.excel_pageSize) + 1; j++) {
									wb = POIUtil.create_sheet(wb, add_czf_detail_alljt_sheet(storeNames, 
															"'退换货出库','销售出库'", client_name + "-B2C出库操作费-"+ j, 
															param, " limit "+ a+ ","+BaseConst.excel_pageSize, map), 
															false);
									a= j*BaseConst.excel_pageSize;
									
								}
							} else {
								export_BTCout_ex(param,wb,storeNames,client_name);
							}
						} else if (osr_btcobop_fee == 3) {
							// 按单阶梯计费
							Integer osr_btcobop_numfees = null != oSR.getOsr_btcobop_numfees() && 
									!oSR.getOsr_btcobop_numfees().equals("") ? 
											Integer.valueOf(oSR.getOsr_btcobop_numfees()) : 0;
							if (osr_btcobop_numfees == 1) {
								// 无阶梯
								
								BigDecimal osr_btcobop_numprices = oSR.getOsr_btcobop_numprices();
								BigDecimal osr_btcobop_numdcs = oSR.getOsr_btcobop_numdcs();
								
								StringBuffer czf_b2cob_sql= new StringBuffer();
								czf_b2cob_sql.append("select count(1) from (");
								czf_b2cob_sql.append("select related_orderno from ");
								czf_b2cob_sql.append("tb_operationfee_data x where store_name in ("+
										storeNames.toString()+") and job_type in ('退换货出库','销售出库')"+
										" and TO_DAYS(operation_time) >= TO_DAYS('" +param.getFromDate()+"') and TO_DAYS(operation_time)"+
										" <= TO_DAYS('"+ param.getToDate() +"') and warehouse_code != 'SHWH215'");
								czf_b2cob_sql.append(" group by x.related_orderno ");
								czf_b2cob_sql.append(") xt ");
								param.getParam().put("sql", czf_b2cob_sql);
								param.getParam().put("logTitle", client_name + "B2C出库操作费");
								count1 = consumerRevenueEstimateMapper.countSQL(param);
								a= 0;
								for(int j= 1; j< forCount(count1, BaseConst.excel_pageSize) + 1; j++) {
									wb = POIUtil.create_sheet(wb, add_czf_detail_djjt_sheet(storeNames, 
															"'退换货出库','销售出库'", client_name + "-B2C出库操作费-"+ j, 
															param, " limit "+ a+ ","+BaseConst.excel_pageSize,
															osr_btcobop_numprices,osr_btcobop_numdcs), 
															false);
									a= j*BaseConst.excel_pageSize;
									
								}
							} else if (osr_btcobop_numfees == 3) {
								// 总件数阶梯
								String osr_btcobopbill_tobtb_tableidss = oSR.getOsr_btcobopnum_tableidss();
								List<Map<String, Object>> tbtList = operationSettlementRuleService
										.queryTBBTListss(osr_btcobopbill_tobtb_tableidss);

								StringBuffer czf_b2cob_sql= new StringBuffer();
								czf_b2cob_sql.append("select count(1) from (");
								czf_b2cob_sql.append("select related_orderno from ");
								czf_b2cob_sql.append("tb_operationfee_data tt where store_name in ("+
										storeNames.toString()+") and job_type in ('退换货出库','销售出库')"+
										" and TO_DAYS(operation_time) >= TO_DAYS('" +param.getFromDate()+"') and TO_DAYS(operation_time)"+
										" <= TO_DAYS('"+ param.getToDate() +"') and warehouse_code != 'SHWH215'");
								czf_b2cob_sql.append(" group by tt.related_orderno ");
								czf_b2cob_sql.append(") xt ");
								param.getParam().put("sql", czf_b2cob_sql);
								param.getParam().put("logTitle", client_name + "B2C出库操作费");
								count1 = consumerRevenueEstimateMapper.countSQL(param);
								BigDecimal B2COutboundNum = new BigDecimal(count1);
								
								Map<String, Object> map = getPrice(tbtList, B2COutboundNum, "2", "obts_interval", "obts_price", null, "obts_discount");
								a= 0;
								for(int j= 1; j< forCount(count1, BaseConst.excel_pageSize) + 1; j++) {
									wb = POIUtil.create_sheet(wb, add_czf_detail_djjt_sheet(storeNames, 
															"'退换货出库','销售出库'", client_name + "-B2C出库操作费-"+ j, 
															param, " limit "+ a+ ","+BaseConst.excel_pageSize,
															new BigDecimal(map.get("price")==null?"0":map.get("price").toString()),
															new BigDecimal(map.get("zk")==null?"0":map.get("zk").toString())), 
															false);
									a= j*BaseConst.excel_pageSize;
									
								}
								
							} else {
								export_BTCout_ex(param,wb,storeNames,client_name);
							}
						} else {
							export_BTCout_ex(param,wb,storeNames,client_name);
						}
					}
				}
				//B2B入库操作费
				if(new BigDecimal(czf_lists.get(0).get("B2B入库").toString()).compareTo(new BigDecimal(0))!=0){
					StringBuffer sqlSB = new StringBuffer();
					sqlSB.append("select count(1) from tb_operationfee_data where store_name in ("+ 
							storeNames.toString()+ ") and job_type in ('VMI移库入库','代销入库','结算经销入库') " +
							"and TO_DAYS(operation_time) >= TO_DAYS('"+ param.getFromDate() +"') and TO_DAYS(operation_time)"+
							" <= TO_DAYS('" + param.getToDate() + "') and warehouse_code != 'SHWH215'");
					param.getParam().put("sql", sqlSB.toString());
					param.getParam().put("logTitle", client_name + "B2B入库操作费明细");
					int count2= consumerRevenueEstimateMapper.countSQL(param);
					a= 0;
					for (int j= 1; j< forCount(count2, BaseConst.excel_pageSize)+ 1; j++) {
						wb= POIUtil.create_sheet(wb, add_czf_sheet(storeNames, "'VMI移库入库','代销入库','结算经销入库'", 
								client_name+ "-B2B入库操作费明细-"+j, param," limit "+ a+ ","+ BaseConst.excel_pageSize),
								false);
						a= j*BaseConst.excel_pageSize;
						
					}
					
					//tb_operationfee_data_detail数据查询
					
					param.getParam().put("sql", "select count(1) from (select job_orderno from tb_operationfee_data_detail where store_name in ("+ 
							storeNames.toString()+ ") and job_type in ('VMI移库入库','代销入库','结算经销入库') " +
							"and TO_DAYS(operation_time) >= TO_DAYS('"+ param.getFromDate() +"') and TO_DAYS(operation_time)"+
							" <= TO_DAYS('" + param.getToDate() + "') and warehouse_code != 'SHWH215' group by job_orderno ) x");
					param.getParam().put("logTitle", client_name + "B2B入库操作费");
					count2= consumerRevenueEstimateMapper.countSQL(param);

					Map<String, String> czf_title = new LinkedHashMap<>();
					czf_title.put("所属仓库", "所属仓库");
					czf_title.put("店铺", "店铺");
					czf_title.put("园区", "园区");
					czf_title.put("园区成本中心代码", "园区成本中心代码");
					czf_title.put("日期", "日期");
					czf_title.put("作业单号", "作业单号");
					czf_title.put("入库数量", "件数");
					czf_title.put("费用", "费用");
					
					a= 0;
					for (int j= 1; j< forCount(count2, BaseConst.excel_pageSize)+ 1; j++) {
						wb= POIUtil.create_sheet(wb, add_czf_detail_sheet(storeNames, 
								"'VMI移库入库','代销入库','结算经销入库'", client_name+ "-B2B入库操作费-"+j, 
								param," limit "+ a+ ","+ BaseConst.excel_pageSize,czf_title), false);
						a= j*BaseConst.excel_pageSize;
						
					}

				}
				//B2B出库操作费 AD特殊判断
				if(new BigDecimal(czf_lists.get(0).get("B2B出库").toString()).compareTo(new BigDecimal(0))!=0){
					String jobyTypeStr = null;
					if(String.valueOf(param.getContract().getId()).equals("264")){
						jobyTypeStr = "'VMI退仓出库','VMI转店退仓出库','代销出库','结算经销出库','样品领用出库','库间移动-出库'";
					} else {
						jobyTypeStr = "'VMI退仓出库','VMI转店退仓出库','代销出库','结算经销出库','样品领用出库'";
					}

					StringBuffer sqlSB = new StringBuffer();
					sqlSB.append("select count(1) from tb_operationfee_data where store_name in ("+
							storeNames.toString()+") and job_type in (" + jobyTypeStr + ") "+
							"and TO_DAYS(operation_time) >= TO_DAYS('"+ param.getFromDate() +"') and "+
							"TO_DAYS(operation_time) <= TO_DAYS('" + param.getToDate() + "')");
					sqlSB.append(" and warehouse_code != 'SHWH215'");
					param.getParam().put("sql", sqlSB.toString());
					param.getParam().put("logTitle", client_name + "B2B出库操作费明细");
					int count3 = consumerRevenueEstimateMapper.countSQL(param);
					a= 0;
					for (int j = 1; j < forCount(count3, BaseConst.excel_pageSize)+1; j++) {
						wb= POIUtil.create_sheet(wb, add_czf_sheet(storeNames, jobyTypeStr, 
								client_name+"-B2B出库操作费明细-"+j, param,
					" limit "+a+","+BaseConst.excel_pageSize), false);
						a= j*BaseConst.excel_pageSize;
						
					}
					
					//tb_operationfee_data_detail数据查询

					param.getParam().put("sql", "select count(1) from (select job_orderno from tb_operationfee_data_detail where store_name in ("+
							storeNames.toString()+") and job_type in (" + jobyTypeStr + ") "+
							"and TO_DAYS(operation_time) >= TO_DAYS('"+ param.getFromDate() +"') and "+
							"TO_DAYS(operation_time) <= TO_DAYS('" + param.getToDate() + "') and warehouse_code != 'SHWH215' group by job_orderno) x ");
					param.getParam().put("logTitle", client_name + "B2B出库操作费");
					count3 = consumerRevenueEstimateMapper.countSQL(param);

					Map<String, String> czf_title = new LinkedHashMap<>();
					czf_title.put("所属仓库", "所属仓库");
					czf_title.put("店铺", "店铺");
					czf_title.put("园区", "园区");
					czf_title.put("园区成本中心代码", "园区成本中心代码");
					czf_title.put("日期", "日期");
					czf_title.put("作业单号", "作业单号");
					czf_title.put("出库数量", "件数");
					czf_title.put("费用", "费用");
					
					a= 0;
					for (int j = 1; j < forCount(count3, BaseConst.excel_pageSize)+1; j++) {
						wb= POIUtil.create_sheet(wb, add_czf_detail_sheet(storeNames, jobyTypeStr, 
								client_name+"-B2B出库操作费-"+j, param," limit "+a+","+BaseConst.excel_pageSize,czf_title),
								false);
						a= j*BaseConst.excel_pageSize;
						
					}
					
				}
				//B2C退换货入库费
				if(new BigDecimal(czf_lists.get(0).get("销售退货").toString()).compareTo(new BigDecimal(0))!=0){

					StringBuffer sqlSB = new StringBuffer();
					sqlSB.append("select count(1) from tb_operationfee_data where store_name in ("+
							storeNames.toString()+") and job_type in ('退换货入库','消费者退换货入库') "+
							"and TO_DAYS(operation_time) >= TO_DAYS('"+ param.getFromDate() +"') and TO_DAYS(operation_time)"+
							" <= TO_DAYS('" + param.getToDate() + "') and warehouse_code != 'SHWH215'");
					param.getParam().put("sql", sqlSB.toString());
					param.getParam().put("logTitle", client_name + "B2C退换货入库操作费明细");
					int count4= consumerRevenueEstimateMapper.countSQL(param);
					a= 0;
					for (int j= 1; j< forCount(count4, BaseConst.excel_pageSize)+1; j++) {
						wb= POIUtil.create_sheet(wb, add_czf_sheet(storeNames, "'退换货入库','消费者退换货入库'", client_name+ "-B2C退换货入库操作费费明细-"+ j,
								param," limit "+ a+ ","+BaseConst.excel_pageSize), false);
						a= j* BaseConst.excel_pageSize;
						
					}
					
					//tb_operationfee_data_detail数据查询
					if (oSRList != null && oSRList.size() >= 1) {
						
						OperationSettlementRule oSR = oSRList.get(0);
						
						// 5.B2C退换货入库费
						Integer osr_btcrtib_fee = oSR.getOsr_btcrtib_fee();
						
						if (osr_btcrtib_fee == 1) {
							// 按单计算
							StringBuffer czf_b2cob_sql= new StringBuffer();
							czf_b2cob_sql.append("select count(1) from (");
							czf_b2cob_sql.append("select related_orderno from ");
							czf_b2cob_sql.append("tb_operationfee_data tt where store_name in ("+
									storeNames.toString()+") and job_type in ('退换货入库','消费者退换货入库')"+
									" and TO_DAYS(operation_time) >= TO_DAYS('" +param.getFromDate()+"') and TO_DAYS(operation_time)"+
									" <= TO_DAYS('"+ param.getToDate() +"') and warehouse_code != 'SHWH215'");
							czf_b2cob_sql.append(" group by tt.related_orderno ");
							czf_b2cob_sql.append(") xt ");
							param.getParam().put("sql", czf_b2cob_sql);
							param.getParam().put("logTitle", client_name + "B2C退换货入库操作费");
							count4 = consumerRevenueEstimateMapper.countSQL(param);
							BigDecimal osr_btcrtib_bill_billprice = oSR.getOsr_btcrtib_bill_billprice();
							a= 0;
							for(int j= 1; j< forCount(count4, BaseConst.excel_pageSize) + 1; j++) {
								wb = POIUtil.create_sheet(wb, add_czf_detail_B2Cthhin_ad_sheet(storeNames, 
														"'退换货入库','消费者退换货入库'", client_name + "-B2C退换货入库操作费-"+ j, 
														param, " limit "+ a+ ","+BaseConst.excel_pageSize,
														osr_btcrtib_bill_billprice), 
														false);
								a= j*BaseConst.excel_pageSize;
								
							}
							
							
						} else {

							param.getParam().put("sql", "select count(1) from ( select related_orderno from tb_operationfee_data_detail where store_name in ("+
									storeNames.toString()+") and job_type in ('退换货入库','消费者退换货入库') "+
									"and TO_DAYS(operation_time) >= TO_DAYS('"+ param.getFromDate() +"') and TO_DAYS(operation_time)"+
									" <= TO_DAYS('" + param.getToDate() + "') and warehouse_code != 'SHWH215' group by related_orderno ) x");
							param.getParam().put("logTitle", client_name + "B2C退换货入库操作费");
							count4= consumerRevenueEstimateMapper.countSQL(param);
							a= 0;
							for (int j= 1; j< forCount(count4, BaseConst.excel_pageSize)+1; j++) {
								wb= POIUtil.create_sheet(wb, add_czf_detail_B2Cthhin_sheet(storeNames, "'退换货入库','消费者退换货入库'", client_name+ "-B2C退换货入库操作费-"+ j,
										param," limit "+ a+ ","+BaseConst.excel_pageSize), false);
								a= j* BaseConst.excel_pageSize;
								
							}
							
						}
						
					}
				}
			}
			//操作费End---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
			
			//耗材费Start---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
			//查询耗材费汇总SQL
			StringBuffer sb_get_hcf_lists= new StringBuffer();
			sb_get_hcf_lists.append("select b.contract_name,a.contract_id,");
			sb_get_hcf_lists.append("a.qty,a.total_amount,sku_type from bal_invitationdata_estimate a  ");
			sb_get_hcf_lists.append("left join tb_contract_basicinfo b on a.contract_id=b.id ");
			sb_get_hcf_lists.append("where a.contract_id="+ param.getContract().getId()+ " ");
			sb_get_hcf_lists.append("and a.batch_number= '"+ param.getBatchNumber()+ "' ");
			param.getParam().put("sql", sb_get_hcf_lists.toString());
			param.getParam().put("logTitle", param.getContract().getContractOwnerName()+"耗材费汇总查询");
			List<Map<String, Object>> hcf_lists= consumerRevenueEstimateMapper.querySQL(param);
			
			List<Map<String, Object>> small_list_3 = new ArrayList<>();
			BigDecimal sum_qty = new BigDecimal(0.00);
			BigDecimal sum_amount = new BigDecimal(0.00);
			for (int j = 0; j < hcf_lists.size(); j++) {
				if (hcf_lists.get(j).get("sku_type").equals("1")) {
					Map<String, Object> map_gourp_1 = new LinkedHashMap<>();
					map_gourp_1.put("text1", "主材");
					map_gourp_1.put("text2", hcf_lists.get(j).get("qty"));
					sum_qty=sum_qty.add(new BigDecimal(hcf_lists.get(j).get("qty").toString()));
					map_gourp_1.put("text3", "");
					map_gourp_1.put("text4", hcf_lists.get(j).get("total_amount"));
					sum_amount=sum_amount.add(new BigDecimal(hcf_lists.get(j).get("total_amount").toString()));
					map_gourp_1.put("text5", "");
					small_list_3.add(map_gourp_1);
				}else{
					Map<String, Object> map_gourp_2= new LinkedHashMap<>();
					map_gourp_2.put("text1", "辅材");
					map_gourp_2.put("text2", hcf_lists.get(j).get("qty"));
					sum_qty=sum_qty.add(new BigDecimal(hcf_lists.get(j).get("qty").toString()));
					map_gourp_2.put("text3", "");
					map_gourp_2.put("text4", hcf_lists.get(j).get("total_amount"));
					sum_amount=sum_amount.add(new BigDecimal(hcf_lists.get(j).get("total_amount").toString()));
					map_gourp_2.put("text5", "");
					small_list_3.add(map_gourp_2);
					
				}
				
			}
			
			Map<String, Object> map_3 = new LinkedHashMap<>();
			map_3.put("text1", "耗材费");
			
			if (sum_qty.compareTo(new BigDecimal("0"))!=0) {
				
				map_3.put("text2", sum_qty);
				map_3.put("text3", "");

				//添加管理费小列表
				Map<String, Object> map_gourp_mangement_fee = new LinkedHashMap<>();
				map_gourp_mangement_fee.put("text1", "管理费");
				map_gourp_mangement_fee.put("text2", "");
				map_gourp_mangement_fee.put("text3", "");
				BigDecimal managementFee = getManagementFee(sum_amount,"hcf",param.getContract().getId());
				map_gourp_mangement_fee.put("text4", managementFee.toString());
				map_gourp_mangement_fee.put("text5", "");
				small_list_3.add(map_gourp_mangement_fee);
				
				map_3.put("text4", sum_amount.add(managementFee).toString());
				map_3.put("text5", "");
				map_3.put("text6", small_list_3);
				max_list.add(map_3);
				
				List<Ain> ainList = ainService.findByCBID(String.valueOf(param.getContract().getId()));
				
				Integer cat_type = null;
				String cat_type_name = null;
				Ain ain = null;
				Map<Integer,Object> catMap = new LinkedHashMap<Integer,Object>();
				
				if (ainList != null && ainList.size() > 0) {
					//每个规则生成一个sheet页命名：耗材费- + 
					for (int i = 0; i < ainList.size(); i++) {
						ain = ainList.get(i);
	//					String hc_no = ain.getHc_no();
	//					String hc_name = ain.getHc_name();
	//					BigDecimal hc_price = ain.getHc_price();
	//					Integer hc_type = ain.getHc_type();
	//					cat_type = ain.getCat_type();		//耗材类型
	//					BigDecimal bill_num = ain.getBill_num();
	//					if (cat_type != null && cat_type.equals(3)) {
	//						break;
	//					}
						
						catMap.put(ain.getCat_type(), ain.getCat_type_name());
					}
					
				}
				
				for (Entry<Integer,Object> entry : catMap.entrySet()) {
					cat_type = null; 
					cat_type_name = null;
					
					cat_type = entry.getKey();
					cat_type_name = (String)entry.getValue();
					if (cat_type != null) {

						int a= 0;
						
						if(cat_type.equals(1)){
							//合同对应的耗材规则类型：按耗材实际使用量计算
							StringBuffer basesql= new StringBuffer();
							basesql.append("select ");
							basesql.append("count(1)");
							basesql.append(" from tb_invitation_realuseanmount_data tird inner join ");
							basesql.append("(select * from hc_main where contract_id = " 
													+ param.getContract().getId() +" and cat_type=1) hm on tird.sku_code = hm.hc_no ");
							basesql.append("where store_name in ("+storeNames+") ");
							basesql.append("and TO_DAYS(ob_time) >= TO_DAYS('" + param.getFromDate() + "') ");
							basesql.append("and TO_DAYS(ob_time) <= TO_DAYS('" + param.getToDate() + "')");
							param.getParam().put("sql", basesql.toString());
							param.getParam().put("logTitle", param.getContract().getContractOwnerName()+"按耗材实际使用量计算,统计记录数查询");
							int count= consumerRevenueEstimateMapper.countSQL(param);
							for (int j= 1; j< forCount(count, BaseConst.excel_pageSize)+1; j++) {
								wb= POIUtil.create_sheet(wb, add_hcf_use_sheet(storeNames.toString(),//ain,
										client_name+ "-耗材费-结算明细-"+cat_type_name+"-"+j
										, param," limit "+ a+ ","+ BaseConst.excel_pageSize), false);
								a= j* BaseConst.excel_pageSize;
							}
						} else if(cat_type.equals(2)){
							//合同对应的耗材规则类型：按订单量计算
							StringBuffer basesql= new StringBuffer();
							basesql.append("select ");
							basesql.append("count(1)");
							basesql.append(" from tb_warehouse_express_data ");
							basesql.append("where store_name in ("+storeNames+") ");
							basesql.append("and TO_DAYS(transport_time) >= TO_DAYS('" + param.getFromDate() + "') ");
							basesql.append("and TO_DAYS(transport_time) <= TO_DAYS('" + param.getToDate() + "')");
							param.getParam().put("sql", basesql.toString());
							param.getParam().put("logTitle", param.getContract().getContractOwnerName()+"按订单量计算,统计记录数查询");
							int count= consumerRevenueEstimateMapper.countSQL(param);
							for (int j= 1; j< forCount(count, BaseConst.data_pageSize)+1; j++) {
								wb= POIUtil.create_sheet(wb, add_hcf_order_sheet(storeNames.toString(),//ain,
										client_name+ "-耗材费-结算明细-"+cat_type_name+"-"+j
										, param," limit "+ a+ ","+ BaseConst.data_pageSize), false);
								a= j* BaseConst.data_pageSize;
							}
						} else if(cat_type.equals(3)){
							//合同对应的耗材规则类型：按实际采购量计算
							StringBuffer basesql= new StringBuffer();
							basesql.append("select count(1) ");
							basesql.append("from tb_invitation_useanmount_data tiu inner join ");
							basesql.append("(select * from hc_main where contract_id = " 
													+ param.getContract().getId() 
													+" and cat_type=3) hm on tiu.bz_number = hm.hc_no ");
							basesql.append("where store_name in ("+storeNames+") ");
							basesql.append("and TO_DAYS(ib_time) >= TO_DAYS('" + param.getFromDate() + "') ");
							basesql.append("and TO_DAYS(ib_time) <= TO_DAYS('" + param.getToDate() + "')");
							param.getParam().put("sql", basesql.toString());
							param.getParam().put("logTitle", param.getContract().getContractOwnerName()+"按实际采购量计算,统计记录数查询");
							int count= consumerRevenueEstimateMapper.countSQL(param);
							for (int j= 1; j< forCount(count, BaseConst.excel_pageSize)+1; j++) {
								wb= POIUtil.create_sheet(wb, add_hcf_original_sheet(storeNames.toString(),//ain,
										client_name+ "-耗材费-结算明细-"+cat_type_name+"-"+j
										, param," limit "+ a+ ","+ BaseConst.excel_pageSize), false);
								a= j* BaseConst.excel_pageSize;
							}
						} else if(cat_type.equals(4)){
							//合同对应的耗材规则类型：按商品件数计算
							StringBuffer czf_b2cob_sql= new StringBuffer();
							czf_b2cob_sql.append("select ");
							czf_b2cob_sql.append("count(1) ");
							czf_b2cob_sql.append("from  bal_invitation_useanmount_data_groupdetail_estimate ");
							czf_b2cob_sql.append("where contract_id="+param.getContract().getId()
									+" and batch_number='"+param.getBatchNumber()+"' " );
							param.getParam().put("sql", czf_b2cob_sql.toString());
							param.getParam().put("logTitle", param.getContract().getContractOwnerName()+"按商品件数计算,统计记录数查询");
							int count= consumerRevenueEstimateMapper.countSQL(param);
							for (int j= 1; j< forCount(count, BaseConst.excel_pageSize)+1; j++) {
								wb= POIUtil.create_sheet(wb, add_hcf_sheet(String.valueOf(param.getContract().getId()),
										client_name+ "-耗材费-结算明细"+"-"+j,cat_type, param
										," limit "+ a+ ","+ BaseConst.excel_pageSize), false);
								a= j* BaseConst.excel_pageSize;
							}
						}
					}
				}
			} else {
				map_3.put("text2", "暂无耗材费数据");
				map_3.put("text3", "");
				map_3.put("text4", "");
				map_3.put("text5", "");
				max_list.add(map_3);
			}
			
			
			
			
			
			
			
			//耗材费End---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
			
			//增值服务费Start---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
			//查询增值服务费汇总SQL
			StringBuffer sb_get_zzfwf_lists = new StringBuffer();
			sb_get_zzfwf_lists.append("select addservice_name,qty,amount from bal_addservicefee_billdata_estimate ");
			sb_get_zzfwf_lists.append("where contract_id= "+param.getContract().getId()+" ");
			sb_get_zzfwf_lists.append("and batch_number= '"+param.getBatchNumber()+"'");
			String sql = sb_get_zzfwf_lists.toString();
			param.getParam().put("sql", sql);
			List<Map<String, Object>> zzfwf_lists = consumerRevenueEstimateMapper.querySQL(param);
			List<Map<String, Object>> small_list_4 = new ArrayList<>();
			BigDecimal sum_zzfwf_qty = new BigDecimal(0.00);
			BigDecimal sum_zzfwf_amount = new BigDecimal(0.00);
			for (int j = 0; j < zzfwf_lists.size(); j++) {
				Map<String, Object> map_gourp = new LinkedHashMap<>();
				map_gourp.put("text1", zzfwf_lists.get(j).get("addservice_name"));
				map_gourp.put("text2", zzfwf_lists.get(j).get("qty"));
				sum_zzfwf_qty=sum_zzfwf_qty.add(new BigDecimal(zzfwf_lists.get(j).get("qty").toString()));
				map_gourp.put("text3", "");
				map_gourp.put("text4", zzfwf_lists.get(j).get("amount"));
				sum_zzfwf_amount=sum_zzfwf_amount.add(new BigDecimal(zzfwf_lists.get(j).get("amount").toString()));
				map_gourp.put("text5", "单价："+ new BigDecimal(zzfwf_lists.get(j).get("amount").toString()).divide(new BigDecimal(zzfwf_lists.get(j).get("qty").toString())));
				small_list_4.add(map_gourp);
				
			}
			Map<String, Object> map_4 = new LinkedHashMap<>();
			map_4.put("text1", "增值费");
			map_4.put("text2", sum_zzfwf_qty);
			map_4.put("text3", "");

			//添加管理费小列表
			Map<String, Object> map_gourp_mangement_fee = new LinkedHashMap<>();
			map_gourp_mangement_fee.put("text1", "管理费");
			map_gourp_mangement_fee.put("text2", "");
			map_gourp_mangement_fee.put("text3", "");
			BigDecimal managementFee = getManagementFee(sum_zzfwf_amount,"zzfwf",param.getContract().getId());
			map_gourp_mangement_fee.put("text4", managementFee.toString());
			map_gourp_mangement_fee.put("text5", "");
			small_list_4.add(map_gourp_mangement_fee);
			
			map_4.put("text4", sum_zzfwf_amount.add(managementFee).toString());
			map_4.put("text5", "");
			map_4.put("text6", small_list_4);
			max_list.add(map_4);
			
			StringBuffer czf_zzfwf_sql= new StringBuffer();
			czf_zzfwf_sql.append("select store_name as 店铺名,addservice_code as 增值服务费编码,addservice_name as 增值服务费名称,qty as 数量,amount as 总计 from bal_addservicefee_settlement_estimate ");
			czf_zzfwf_sql.append("where store_name in ("+storeNames.toString()+") and batch_number = '"+param.getBatchNumber()+"'");
			param.getParam().put("sql", czf_zzfwf_sql.toString());
			param.getParam().put("logTitle", param.getContract().getContractOwnerName()+"增值服务费明细预估查询");
			List<Map<String, Object>> czf_b2cob_sheet_list = consumerRevenueEstimateMapper.querySQL(param);
			Map<String, Object> czfMap = new LinkedHashMap<>();
			czfMap.put("sheet_name", param.getContract().getContractOwnerName()+ "-增值服务费明细");
			Map<String, String> czf_title = new LinkedHashMap<>();
			czf_title.put("店铺名", "店铺名");
			czf_title.put("增值服务费编码", "增值服务费编码");
			czf_title.put("增值服务费名称", "增值服务费名称");
			czf_title.put("数量", "数量");
			czf_title.put("总计", "总计");
			czfMap.put("sheet_title", czf_title);
			czfMap.put("sheet_list", czf_b2cob_sheet_list);
			
			//创建sheet
			wb= POIUtil.create_sheet(wb, czfMap, false);
			//增值服务费End---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		
			
			//打包费Start---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
			//查询打包费预估SQL
			String dbfSql = "select dbf_qty,total_price from bal_package_charage_estimate where cbid='"+param.getContract().getId()+"' and batch_number= '"+param.getBatchNumber() + "'";
			param.getParam().put("sql", dbfSql);
			List<Map<String, Object>> dbf_lists = consumerRevenueEstimateMapper.querySQL(param);
			int count = 0;
			BigDecimal total_price = new BigDecimal(0);
			if(dbf_lists!=null && dbf_lists.size()>0 && dbf_lists.get(0).get("dbf_qty")!=null && dbf_lists.get(0).get("total_price")!=null){
				count = Integer.valueOf(dbf_lists.get(0).get("dbf_qty").toString());
				total_price = new BigDecimal(dbf_lists.get(0).get("total_price").toString());
			}
			Map<String, Object> map_dbf = new LinkedHashMap<>();
			map_dbf.put("text1", "打包费");
			map_dbf.put("text2", count);
			map_dbf.put("text3", "单");
			
			//添加管理费小列表
			List<Map<String,Object>> small_list_dbf = new ArrayList<Map<String,Object>>();
			map_gourp_mangement_fee = new LinkedHashMap<>();
			map_gourp_mangement_fee.put("text1", "管理费");
			map_gourp_mangement_fee.put("text2", "");
			map_gourp_mangement_fee.put("text3", "");
			managementFee = getManagementFee(total_price,"dbf",param.getContract().getId());
			map_gourp_mangement_fee.put("text4", managementFee.toString());
			map_gourp_mangement_fee.put("text5", "");
			small_list_dbf.add(map_gourp_mangement_fee);
			
			map_dbf.put("text4", total_price.add(managementFee).toString());
			map_dbf.put("text5", "");
			map_dbf.put("text6", small_list_dbf);
			max_list.add(map_dbf);
			//耗材费明细数据
			wb= POIUtil.create_sheet(wb, add_dbf_sheet(String.valueOf(param.getContract().getId()), client_name+ "-打包费明细", param), false);
			//打包费End---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		
			
			
			
			// 运费Start---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
			// 查询运费汇总
			param.getParam().put("sql", "select express, CASE WHEN tb_transport_vendor.transport_name IS NULL THEN '' ELSE tb_transport_vendor.transport_name END AS transport_name, product_type_code, product_type_name, order_num, total_freight, total_insurance, total_cod, total_fee from bal_estimate_express_freight left join tb_transport_vendor on bal_estimate_express_freight.express=tb_transport_vendor.transport_code where batch_number='"+param.getBatchNumber()+"' and contract_id="+param.getContract().getId());
			param.getParam().put("logTitle", param.getContract().getContractOwnerName()+"快递运费汇总查询");
			List<Map<String, Object>> summary=consumerRevenueEstimateMapper.querySQL(param);
			// 总运费折扣
			param.getParam().put("sql", "select carrier, CASE WHEN tb_transport_vendor.transport_name IS NULL THEN '' ELSE tb_transport_vendor.transport_name END AS transport_name, fee_type, fee from bal_estimate_carrier_fee left join tb_transport_vendor on bal_estimate_carrier_fee.carrier=tb_transport_vendor.transport_code where batch_number='"+param.getBatchNumber()+"' and contract_id="+param.getContract().getId()+" and fee_type= 0");
			param.getParam().put("logTitle", param.getContract().getContractOwnerName()+"承运商总运费折扣查询");
			List<Map<String, Object>> tFD=consumerRevenueEstimateMapper.querySQL(param);
			// 管理费
			param.getParam().put("sql", "select carrier, CASE WHEN tb_transport_vendor.transport_name IS NULL THEN '' ELSE tb_transport_vendor.transport_name END AS transport_name, fee_type, fee from bal_estimate_carrier_fee left join tb_transport_vendor on bal_estimate_carrier_fee.carrier=tb_transport_vendor.transport_code where batch_number='"+param.getBatchNumber()+"' and contract_id="+param.getContract().getId()+" and fee_type= 1");
			param.getParam().put("logTitle", param.getContract().getContractOwnerName()+"承运商管理费查询");
			List<Map<String, Object>> man=consumerRevenueEstimateMapper.querySQL(param);
			// 运单量
			BigDecimal order_num=new BigDecimal(0);
			// 运费小计
			BigDecimal freight=new BigDecimal(0);
			// cod小计
			BigDecimal cod=new BigDecimal("0.00");
			// 管理费小计
			BigDecimal manFee=new BigDecimal("0.00");
			// 运费内容
			List<Map<String, Object>> small_list_5= new ArrayList<>();
			Map<String, Object> map_group= null;
			// cod内容
			List<Map<String, Object>> small_list_6= new ArrayList<>();
			Map<String, Object> map_group2= new LinkedHashMap<>();
			map_group2.put("text1", "COD项-合计");
			map_group2.put("text2", "");
			map_group2.put("text3", "");
			map_group2.put("text4", cod);
			map_group2.put("text5", "");
			small_list_6.add(map_group2);
			String name= "";
			// 运单明细查询条件
			StringBuffer condition= new StringBuffer("");
			String temp1= "WHERE dFlag= 0 AND contract_id= "+param.getContract().getId()+ " AND transport_name= '";
			String temp2= " AND TO_DAYS(transport_time) >= TO_DAYS('"+param.getFromDate()+"') AND TO_DAYS(transport_time) <= TO_DAYS('"+param.getToDate()+"')";
			for(int p= 0; p< summary.size(); p++) {
				order_num= order_num.add(new BigDecimal(summary.get(p).get("order_num").toString()));
				freight= freight.add(new BigDecimal(summary.get(p).get("total_fee").toString()));
				cod= cod.add(new BigDecimal(summary.get(p).get("total_cod").toString()));
				map_group= new LinkedHashMap<>();
				map_group2= new LinkedHashMap<>();
				name= CommonUtils.checkExistOrNot(summary.get(p).get("product_type_name"))? summary.get(p).get("product_type_name").toString():summary.get(p).get("transport_name").toString();
				map_group.put("text1", name);
				map_group2.put("text1", name);
				map_group.put("text2", summary.get(p).get("order_num"));
				map_group2.put("text2", "");
				map_group.put("text3", "单");
				map_group2.put("text3", "");
				map_group.put("text4", summary.get(p).get("total_fee"));
				map_group2.put("text4", summary.get(p).get("total_cod"));
				map_group.put("text5", "");
				map_group2.put("text5", "");
				small_list_5.add(map_group);
				small_list_6.add(map_group2);
				//运费明细数据
				condition.append(new StringBuffer(temp1+ summary.get(p).get("transport_name")+ "'"));
				if(CommonUtils.checkExistOrNot(summary.get(p).get("product_type_name"))) {
					condition.append(" AND itemtype_code= '"+ summary.get(p).get("product_type_code")+ "' AND itemtype_name= '"+ summary.get(p).get("product_type_name")+ "'");
					
				}
				condition.append(temp2);
				param.getParam().put("sql", new StringBuffer("select count(1) from tb_warehouse_express_data_store_settlement ").append(condition).toString());
				param.getParam().put("logTitle", param.getContract().getContractOwnerName()+"下"+name+"运单总量查询");
				int cycleNum= forCount(consumerRevenueEstimateMapper.countSQL(param), BaseConst.excel_pageSize)+ 1;
				int head= 0;
				for (int j=1; j<cycleNum; j++) {
					param.getParam().put("sheetName",name+"运费-"+j);
					param.getParam().put("conditionStr",condition);
					param.getParam().put("limitStr"," LIMIT "+head+", "+BaseConst.excel_pageSize);
					wb=POIUtil.create_sheet(wb, addFreightsheet(param), false);
					head=j*BaseConst.excel_pageSize;
					
				}
				condition= new StringBuffer("");
				
			}
			// 若存在总运费折扣
			if(CommonUtils.checkExistOrNot(tFD)) {
				freight.subtract(new BigDecimal(tFD.get(0).get("fee").toString()));
				
			}
			// 表头
			// 运费汇总
			Map<String, Object> map_5 = new LinkedHashMap<String, Object>();
			// 运费
			map_5.put("text1", "运费-合计(折后运费+保价费-总运费折扣)");
			map_5.put("text2", order_num);
			map_5.put("text3", "单");
			map_5.put("text4", freight);
			map_5.put("text5", "");
			// 总运费折扣
			map_group= new LinkedHashMap<>();
			map_group.put("text1", "总运费折扣");
			map_group.put("text2", "");
			map_group.put("text3", "");
			if(CommonUtils.checkExistOrNot(tFD)) {
				map_group.put("text4", tFD.get(0).get("fee").toString());
				
			} else {
				map_group.put("text4", "0.00");
				
			}
			map_group.put("text5", "");
			small_list_5.add(map_group);
			map_5.put("text6", small_list_5);
			max_list.add(map_5);
			// cod费用单独列出
			Map<String, Object> map_6 = new LinkedHashMap<String, Object>();
			// 特殊服务费
			map_6.put("text1", "特殊服务费(COD+委托取件费)-合计");
			map_6.put("text2", "");
			map_6.put("text3", "");
			map_6.put("text4", cod);
			map_6.put("text5", "");
			//
			map_group2= new LinkedHashMap<>();
			map_group2.put("text1", "委托取件费-合计");
			map_group2.put("text2", "");
			map_group2.put("text3", "");
			map_group2.put("text4", "0.00");
			map_group2.put("text5", "");
			small_list_6.add(map_group2);
			map_6.put("text6", small_list_6);
			max_list.add(map_6);
			// 管理费
			List<Map<String, Object>> small_list_7= new ArrayList<>();
			for(int p= 0; p< man.size(); p++) {
				manFee= manFee.add(new BigDecimal(man.get(p).get("fee").toString()));
				if(CommonUtils.checkExistOrNot(man.get(p).get("transport_name").toString())) {
					map_group= new LinkedHashMap<>();
					map_group.put("text1", man.get(p).get("transport_name").toString());
					map_group.put("text2", "");
					map_group.put("text3", "");
					map_group.put("text4", man.get(p).get("fee").toString());
					map_group.put("text5", "");
					small_list_7.add(map_group);
					
				}
				
			}
			Map<String, Object> map_7= new LinkedHashMap<>();
			// 表头
			map_7.put("text1", "管理费-合计");
			map_7.put("text2", "");
			map_7.put("text3", "");
			map_7.put("text4", manFee);
			map_7.put("text5", "");
			map_7.put("text6", small_list_7);
			max_list.add(map_7);
			//运费End---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
			//表头Start---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
			//汇总
			Map<String, Object> map=new LinkedHashMap<String,Object>();
			map.put("company",param.getContract().getDistributionUnit());
			map.put("settlement_period",param.getFromDate()+Constant.SYM_SUBTRACT+param.getToDate());
			map.put("distribution_unit",param.getContract().getDistributionUnit());
			map.put("client_name", param.getContract().getContractOwnerName());
			map.put("store_name", storeNames);
			map.put("unit", "元");
			map.put("max_lists", max_list);
			wb=POIUtil.create_sheet(wb, map, true);
			//表头End---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
			wb.write(output);
			
		} catch (Exception e) {
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
		
	}
	
	/**
	 * Title: getManagementFee
	 * Description: 
	 * @param originalFee	原始费用
	 * @param type			阶梯类型(对应tb_management_other_ladder   type字段)
	 * @param cbId			合同id
	 * @return
	 * @author jindong.lin
	 * @date 2017年9月6日
	 */
	private static BigDecimal getManagementFee(BigDecimal originalFee, String type, Integer cbId) {
		
		ManagementOtherLadder managementOtherLadder = new ManagementOtherLadder();
		managementOtherLadder.setContractId(cbId);
		managementOtherLadder.setType(type);
		
		List<ManagementOtherLadder> managementOtherLadderList = managementOtherLadderService.findList(managementOtherLadder);
		
		if (managementOtherLadderList != null && managementOtherLadderList.size() > 0) {
			
			//阶梯方式
			String ladderMethod = managementOtherLadderList.get(managementOtherLadderList.size()-1).getLadderMethod();
			
			if ("0".equals(ladderMethod)) {
				//无阶梯
				return originalFee.multiply(managementOtherLadderList.get(managementOtherLadderList.size()-1).getRate());
			} else if ("1".equals(ladderMethod)) {
				//总费用阶梯
				for(int i = 0;i < managementOtherLadderList.size();i++){
					
					//判断是否在区间 -1 左趋向 0.无趋向 1.右趋向
					int result = IntervalValidationUtil.judgeInterval(managementOtherLadderList.get(i).getBntInterval(),originalFee);
					
					if (result == 0) {
						return originalFee.multiply(managementOtherLadderList.get(i).getRate());
					}
				}
			} else if ("2".equals(ladderMethod)) {
				//超出部分阶梯
				BigDecimal mangementFee = new BigDecimal(0);
				String minNumberStr = null;
				String maxNumberStr = null;
				BigDecimal minNumber = null;
				BigDecimal maxNumber = null;
				for(int i = 0;i < managementOtherLadderList.size();i++){
					
					minNumberStr = managementOtherLadderList.get(i).getBntInterval().split(",")[0];
					maxNumberStr = managementOtherLadderList.get(i).getBntInterval().split(",")[1];
					minNumber = new BigDecimal(minNumberStr.substring(1, maxNumberStr.length()));
					maxNumber = new BigDecimal(maxNumberStr.substring(0, maxNumberStr.length()-1));
					
					//判断是否在区间 -1 左趋向 0.无趋向 1.右趋向
					int result = IntervalValidationUtil.judgeInterval(managementOtherLadderList.get(i).getBntInterval(),originalFee);

					if (result == 1) {
						mangementFee = mangementFee.add(maxNumber.subtract(minNumber).multiply(managementOtherLadderList.get(i).getRate()));
					} else if (result == 0) {
						mangementFee = mangementFee.add(originalFee.subtract(minNumber).multiply(managementOtherLadderList.get(i).getRate()));
					}
				}
				return mangementFee;
			}
		}
		return new BigDecimal(0);
	}

	private static Map<String, Object> add_czf_detail_B2Cthhin_ad_sheet(StringBuffer str_storename,String job_type,String sheet_name,
			EstimateParam param,String limit, BigDecimal osr_btcrtib_bill_billprice) {

		StringBuffer czf_b2cob_sql= new StringBuffer();

		czf_b2cob_sql.append("select operation_time as 日期,related_orderno as 相关单据号,");
		czf_b2cob_sql.append("warehouse_name as 所属仓库 ,store_name as 店铺,park_name as 园区,");
		czf_b2cob_sql.append("park_cost_center as 园区成本中心代码,sum(in_num) 入库数量,");
		czf_b2cob_sql.append(osr_btcrtib_bill_billprice);
		czf_b2cob_sql.append("*count(1) as 费用 from (");
		czf_b2cob_sql.append("select id,create_time,create_user,update_time,update_user,");
		czf_b2cob_sql.append("order_type,operation_time,store_name,park_name,park_cost_center,");
		czf_b2cob_sql.append("job_orderno,related_orderno,job_type,storaggelocation_code,item_number,");
		czf_b2cob_sql.append("art_no,item_name,item_size,inventory_status,operator,store_id,settle_flag,");
		czf_b2cob_sql.append("sku_number,warehouse_name,sum(in_num) as in_num");
		czf_b2cob_sql.append(" from ");
		czf_b2cob_sql.append("tb_operationfee_data x where store_name in ("+
				str_storename.toString()+") and job_type in (" + job_type +")"+
				" and TO_DAYS(operation_time) >= TO_DAYS('" +param.getFromDate()+"') and TO_DAYS(operation_time)"+
				" <= TO_DAYS('"+ param.getToDate() +"') and warehouse_code != 'SHWH215'");
		czf_b2cob_sql.append("group by x.job_orderno");
		czf_b2cob_sql.append(") tt");
		czf_b2cob_sql.append(" group by tt.related_orderno ");
		czf_b2cob_sql.append(" order by tt.operation_time"+limit);
		param.getParam().put("sql", czf_b2cob_sql.toString());
		param.getParam().put("logTitle", param.getContract().getContractOwnerName()+"操作费明细查询");
		List<Map<String, Object>> czf_b2cob_sheet_list = consumerRevenueEstimateMapper.querySQL(param);
		Map<String, Object> czfMap = new LinkedHashMap<>();
		czfMap.put("sheet_name", sheet_name);
		Map<String, String> czf_title = new LinkedHashMap<>();
		czf_title.put("所属仓库", "所属仓库");
		czf_title.put("店铺", "店铺");
		czf_title.put("日期", "日期");
		czf_title.put("相关单据号", "相关单据号");
		czf_title.put("入库数量", "入库数量");
		czf_title.put("费用", "费用");
		czfMap.put("sheet_title", czf_title);
		czfMap.put("sheet_list", czf_b2cob_sheet_list);
		return czfMap;
	}

	private static Map<String, Object> add_czf_detail_B2Cthhin_sheet(StringBuffer str_storename,String job_type,String sheet_name,
			EstimateParam param,String limit) {
		StringBuffer czf_b2cob_sql= new StringBuffer();
		czf_b2cob_sql.append("select operation_time as 日期,related_orderno as 相关单据号,");
		czf_b2cob_sql.append("warehouse_name as 所属仓库 ,sum(out_num) as 出库数量 ,sum(in_num) as 入库数量,");
		czf_b2cob_sql.append("store_name as 店铺,park_name as 园区,park_cost_center as 园区成本中心代码,");
		czf_b2cob_sql.append("sum(expenses) as 费用 from ");
		czf_b2cob_sql.append("tb_operationfee_data_detail where store_name in ("+
				str_storename.toString()+") and job_type in ("+job_type+")"+
				" and TO_DAYS(operation_time) >= TO_DAYS('" +param.getFromDate()+"') and TO_DAYS(operation_time)"+
				" <= TO_DAYS('"+ param.getToDate() +"') and warehouse_code != 'SHWH215'");
		czf_b2cob_sql.append(" group by related_orderno ");
		czf_b2cob_sql.append(" order by operation_time"+limit);
		param.getParam().put("sql", czf_b2cob_sql.toString());
		param.getParam().put("logTitle", param.getContract().getContractOwnerName()+"操作费明细查询");
		List<Map<String, Object>> czf_b2cob_sheet_list = consumerRevenueEstimateMapper.querySQL(param);
		Map<String, Object> czfMap = new LinkedHashMap<>();
		czfMap.put("sheet_name", sheet_name);
		Map<String, String> czf_title = new LinkedHashMap<>();
		czf_title.put("所属仓库", "所属仓库");
		czf_title.put("店铺", "店铺");
		czf_title.put("日期", "日期");
		czf_title.put("相关单据号", "相关单据号");
		czf_title.put("入库数量", "入库数量");
		czf_title.put("费用", "费用");
		czfMap.put("sheet_title", czf_title);
		czfMap.put("sheet_list", czf_b2cob_sheet_list);
		return czfMap;
	}
	
	/**
	 * 相关单据号分组
	 * @param str_storename
	 * @param job_type
	 * @param sheet_name
	 * @param param
	 * @param limit
	 * @param map
	 * @return
	 */
	private static Map<String, Object> add_czf_detail_alljt_sheet(StringBuffer str_storename,String job_type,String sheet_name,
			EstimateParam param,String limit,Map<String, Object> map) {
		StringBuffer czf_b2cob_sql= new StringBuffer();
		
		czf_b2cob_sql.append("select operation_time as 日期,related_orderno as 相关单据号,");
		czf_b2cob_sql.append("warehouse_name as 所属仓库 ,store_name as 店铺,park_name 园区,");
		czf_b2cob_sql.append("park_cost_center 园区成本中心代码,");
		czf_b2cob_sql.append("sum(out_num) as 出库数量 ");
		
		if (map.get("status") != null) {
			
			if (map.get("status").toString().equals("360") && map.get("zk") != null) {
				czf_b2cob_sql.append(",sum(out_num)*" + map.get("price") + "*" + map.get("zk") + " as 费用");
			} else {
				czf_b2cob_sql.append(",sum(out_num)*" + map.get("price") + " as 费用");
			}
			
		}
		
		czf_b2cob_sql.append(" from ");
		czf_b2cob_sql.append("tb_operationfee_data x where store_name in ("+
				str_storename.toString()+") and job_type in ("+job_type+")"+
				" and TO_DAYS(operation_time) >= TO_DAYS('" +param.getFromDate()+"') and TO_DAYS(operation_time)"+
				" <= TO_DAYS('"+ param.getToDate() +"') and warehouse_code != 'SHWH215'");
		czf_b2cob_sql.append(" group by related_orderno ");
		czf_b2cob_sql.append(" order by operation_time"+limit);
		param.getParam().put("sql", czf_b2cob_sql.toString());
		param.getParam().put("logTitle", param.getContract().getContractOwnerName()+"查询");
		List<Map<String, Object>> czf_b2cob_sheet_list = consumerRevenueEstimateMapper.querySQL(param);
		Map<String, Object> czfMap = new LinkedHashMap<>();
		czfMap.put("sheet_name", sheet_name);
		Map<String, String> czf_title = new LinkedHashMap<>();
		czf_title.put("所属仓库", "所属仓库");
		czf_title.put("店铺", "店铺");
		czf_title.put("园区", "园区");
		czf_title.put("园区成本中心代码", "园区成本中心代码");
		czf_title.put("日期", "日期");
		czf_title.put("相关单据号", "相关单据号");
		czf_title.put("出库数量", "出库数量");
		czf_title.put("费用", "费用");
		czfMap.put("sheet_title", czf_title);
		czfMap.put("sheet_list", czf_b2cob_sheet_list);
		return czfMap;
	}
	
	/**
	 * 相关单据号分组
	 * @param str_storename
	 * @param job_type
	 * @param sheet_name
	 * @param param
	 * @param limit
	 * @return
	 */
	private static Map<String, Object> add_czf_detail_djjt_sheet(StringBuffer str_storename,String job_type,String sheet_name,
			EstimateParam param,String limit,BigDecimal price,BigDecimal zk ) {
		StringBuffer czf_b2cob_sql= new StringBuffer();
		
		czf_b2cob_sql.append("select operation_time as 日期,related_orderno as 相关单据号,");
		czf_b2cob_sql.append("warehouse_name as 所属仓库 ,store_name as 店铺,park_name 园区,");
		czf_b2cob_sql.append("park_cost_center as 园区成本中心代码,sum(out_num) 出库数量,");
		czf_b2cob_sql.append(price.multiply(zk).toString());
		czf_b2cob_sql.append("*count(1) as 费用 from (");
		czf_b2cob_sql.append("select id,create_time,create_user,update_time,update_user,related_orderno,");
		czf_b2cob_sql.append("order_type,operation_time,store_name,park_name,park_cost_center,job_orderno,");
		czf_b2cob_sql.append("job_type,storaggelocation_code,item_number,art_no,item_name,item_size,");
		czf_b2cob_sql.append("inventory_status,operator,store_id,settle_flag,sku_number,warehouse_name,");
		czf_b2cob_sql.append("out_num");
		czf_b2cob_sql.append(" from ");
		czf_b2cob_sql.append("tb_operationfee_data x where store_name in ("+
				str_storename.toString()+") and job_type in (" + job_type +")"+
				" and TO_DAYS(operation_time) >= TO_DAYS('" +param.getFromDate()+"') and TO_DAYS(operation_time)"+
				" <= TO_DAYS('"+ param.getToDate() +"') and warehouse_code != 'SHWH215'");
		czf_b2cob_sql.append(") tt");
		czf_b2cob_sql.append(" group by tt.related_orderno ");
		czf_b2cob_sql.append(" order by tt.operation_time"+limit);
		param.getParam().put("sql", czf_b2cob_sql.toString());
		param.getParam().put("logTitle", param.getContract().getContractOwnerName()+"查询");
		List<Map<String, Object>> czf_b2cob_sheet_list = consumerRevenueEstimateMapper.querySQL(param);
		Map<String, Object> czfMap = new LinkedHashMap<>();
		czfMap.put("sheet_name", sheet_name);
		Map<String, String> czf_title = new LinkedHashMap<>();
		czf_title.put("所属仓库", "所属仓库");
		czf_title.put("店铺", "店铺");
		czf_title.put("园区", "园区");
		czf_title.put("园区成本中心代码", "园区成本中心代码");
		czf_title.put("日期", "日期");
		czf_title.put("相关单据号", "相关单据号");
		czf_title.put("出库数量", "出库数量");
		czf_title.put("费用", "费用");
		czfMap.put("sheet_title", czf_title);
		czfMap.put("sheet_list", czf_b2cob_sheet_list);
		return czfMap;
	}

	/**
	 * 
	 * 相关单据号分组
	 * @param str_storename
	 * @param job_type
	 * @param sheet_name
	 * @param param
	 * @param limit
	 * @return
	 */
	private static Map<String, Object> add_czf_detail_sheet(StringBuffer str_storename,String job_type,String sheet_name,
			EstimateParam param,String limit) {
		StringBuffer czf_b2cob_sql= new StringBuffer();
		czf_b2cob_sql.append("select operation_time as 日期,job_orderno as 作业单号,related_orderno as 相关单据号,");
		czf_b2cob_sql.append("platform_order as 平台订单号,job_type as 操作类型,item_name as 商品名称 ,");
		czf_b2cob_sql.append("warehouse_name as 所属仓库 ,sku_number as SKU编码,sum(out_num) as 出库数量 ,");
		czf_b2cob_sql.append("sum(in_num) as 入库数量,store_name as 店铺,park_name as 园区,");
		czf_b2cob_sql.append("park_cost_center as 园区成本中心代码,sum(expenses) as 费用");
		czf_b2cob_sql.append(" from tb_operationfee_data_detail where store_name in ("+
				str_storename.toString()+") and job_type in ("+job_type+")"+
				" and TO_DAYS(operation_time) >= TO_DAYS('" +param.getFromDate()+"') and TO_DAYS(operation_time)"+
				" <= TO_DAYS('"+ param.getToDate() +"') and warehouse_code != 'SHWH215'");
		czf_b2cob_sql.append(" group by related_orderno ");
		czf_b2cob_sql.append(" order by operation_time"+limit);
		param.getParam().put("sql", czf_b2cob_sql.toString());
		param.getParam().put("logTitle", param.getContract().getContractOwnerName()+"操作费明细查询");
		List<Map<String, Object>> czf_b2cob_sheet_list = consumerRevenueEstimateMapper.querySQL(param);
		Map<String, Object> czfMap = new LinkedHashMap<>();
		czfMap.put("sheet_name", sheet_name);
		Map<String, String> czf_title = new LinkedHashMap<>();
		czf_title.put("所属仓库", "所属仓库");
		czf_title.put("店铺", "店铺");
		czf_title.put("园区", "园区");
		czf_title.put("园区成本中心代码", "园区成本中心代码");
		czf_title.put("日期", "日期");
		czf_title.put("相关单据号", "相关单据号");
		czf_title.put("出库数量", "出库数量");
		czf_title.put("费用", "费用");
		czfMap.put("sheet_title", czf_title);
		czfMap.put("sheet_list", czf_b2cob_sheet_list);
		return czfMap;
	}

	/**
	 * 作业单号分组
	 * @param str_storename
	 * @param job_type
	 * @param sheet_name
	 * @param param
	 * @param limit
	 * @param czf_title
	 * @return
	 */
	private static Map<String, Object> add_czf_detail_sheet(StringBuffer str_storename,String job_type,String sheet_name,
			EstimateParam param,String limit, Map<String, String> czf_title) {
		StringBuffer czf_b2cob_sql= new StringBuffer();
		czf_b2cob_sql.append("select operation_time as 日期,job_orderno as 作业单号,related_orderno as 相关单据号,");
		czf_b2cob_sql.append("platform_order as 平台订单号,job_type as 操作类型,item_name as 商品名称 ,");
		czf_b2cob_sql.append("warehouse_name as 所属仓库 ,sku_number as SKU编码,sum(out_num) as 出库数量 ,");
		czf_b2cob_sql.append("sum(in_num) as 入库数量,store_name as 店铺,park_name as 园区,");
		czf_b2cob_sql.append("park_cost_center 园区成本中心代码,sum(expenses) as 费用");
		czf_b2cob_sql.append(" from tb_operationfee_data_detail x where store_name in ("+
				str_storename.toString()+") and job_type in ("+job_type+")"+
				" and TO_DAYS(operation_time) >= TO_DAYS('" +param.getFromDate()+"') and TO_DAYS(operation_time)"+
				" <= TO_DAYS('"+ param.getToDate() +"') and warehouse_code != 'SHWH215'");
		czf_b2cob_sql.append(" group by job_orderno ");
		czf_b2cob_sql.append(" order by operation_time"+limit);
		param.getParam().put("sql", czf_b2cob_sql.toString());
		param.getParam().put("logTitle", param.getContract().getContractOwnerName()+"操作费明细查询");
		List<Map<String, Object>> czf_b2cob_sheet_list = consumerRevenueEstimateMapper.querySQL(param);
		Map<String, Object> czfMap = new LinkedHashMap<>();
		czfMap.put("sheet_name", sheet_name);
		czfMap.put("sheet_title", czf_title);
		czfMap.put("sheet_list", czf_b2cob_sheet_list);
		return czfMap;
	}

	private static Map<String, Object> add_ccf_sheet(String storeNames,StorageCharge storageCharge, String sheet_name, EstimateParam param) {

		//按件数结算
		String ssc_fixedcost = storageCharge.getSsc_number_price();
		
		StringBuffer czf_b2cob_sql= new StringBuffer();
		czf_b2cob_sql.append("select start_time 日期,warehouse_name 仓库,park_name 园区,");
		czf_b2cob_sql.append("park_cost_center 园区成本中心代码,storage_acreage 数量," + ssc_fixedcost + " 单价 ,");
		czf_b2cob_sql.append("storage_fee 费用  from bal_storage_expenditures_data_settlement_estimate ");
		czf_b2cob_sql.append("where contract_id = " + param.getContract().getId() + " ");
		czf_b2cob_sql.append("and batch_number='" + param.getBatchNumber() + "' ");
		czf_b2cob_sql.append("order by start_time ");
		param.getParam().put("sql", czf_b2cob_sql.toString());
		param.getParam().put("logTitle", param.getContract().getContractOwnerName()+"查询");
		List<Map<String, Object>> czf_b2cob_sheet_list = consumerRevenueEstimateMapper.querySQL(param);
		Map<String, Object> czfMap = new LinkedHashMap<>();
		czfMap.put("sheet_name", sheet_name);
		Map<String, String> czf_title = new LinkedHashMap<>();
		czf_title.put("日期", "日期");
		czf_title.put("仓库", "仓库");
		czf_title.put("园区", "园区");
		czf_title.put("园区成本中心代码", "园区成本中心代码");
		czf_title.put("数量", "数量");
		czf_title.put("费用", "费用");
		czfMap.put("sheet_title", czf_title);
		czfMap.put("sheet_list", czf_b2cob_sheet_list);
		return czfMap;
	}
	
	/**
	 * 耗材实际使用量计算
	 * @param storeNames
	 * @param sheet_name
	 * @param param
	 * @param limit 
	 * @return
	 */
	private static Map<String, Object> add_hcf_use_sheet(String storeNames,//Ain ain,
			String sheet_name, EstimateParam param, String limit) {
		StringBuffer czf_b2cob_sql= new StringBuffer();
		czf_b2cob_sql.append("select ");
		czf_b2cob_sql.append("ob_time 出库时间,store_name 店铺,sku_code 耗材编码,sku_name 耗材名称,epistatic_order 前置单据号, ");
		czf_b2cob_sql.append("convert(express_number using utf8) 运单号,use_amount 使用量,use_amount*hm.hc_price 费用, ");
		czf_b2cob_sql.append("park_name 园区,park_cost_center 园区成本中心代码 ");
		czf_b2cob_sql.append(" from tb_invitation_realuseanmount_data tird inner join ");
		czf_b2cob_sql.append("(select * from hc_main where contract_id = " 
								+ param.getContract().getId() +" and cat_type=1) hm on tird.sku_code = hm.hc_no ");
		czf_b2cob_sql.append("where store_name in ("+storeNames+") ");
		czf_b2cob_sql.append("and TO_DAYS(ob_time) >= TO_DAYS('" + param.getFromDate() + "') ");
		czf_b2cob_sql.append("and TO_DAYS(ob_time) <= TO_DAYS('" + param.getToDate() + "')"+limit);
		param.getParam().put("sql", czf_b2cob_sql.toString());
		param.getParam().put("logTitle", param.getContract().getContractOwnerName()+"查询");
		List<Map<String, Object>> czf_b2cob_sheet_list = consumerRevenueEstimateMapper.querySQL(param);
		Map<String, Object> czfMap = new LinkedHashMap<>();
		czfMap.put("sheet_name", sheet_name);
		Map<String, String> czf_title = new LinkedHashMap<>();
		czf_title.put("出库时间", "出库时间");
		czf_title.put("店铺", "店铺");
		czf_title.put("园区", "园区");
		czf_title.put("园区成本中心代码", "园区成本中心代码");
		czf_title.put("耗材编码", "耗材编码");
		czf_title.put("耗材名称", "耗材名称");
		czf_title.put("前置单据号", "前置单据号");
		czf_title.put("运单号", "运单号");
		czf_title.put("使用量", "使用量");
		czf_title.put("费用", "费用");
		czfMap.put("sheet_title", czf_title);
		czfMap.put("sheet_list", czf_b2cob_sheet_list);
		return czfMap;
	}
	
	
	/**
	 * 按订单量计算
	 * @param storeNames
	 * @param sheet_name
	 * @param param
	 * @param limit 
	 * @return
	 */
	private static Map<String, Object> add_hcf_order_sheet(String storeNames,//Ain ain,
			String sheet_name, EstimateParam param, String limit) {
		StringBuffer czf_b2cob_sql= new StringBuffer();
		czf_b2cob_sql.append("select ");
		czf_b2cob_sql.append("transport_time 订单生成时间,store_name 店铺,warehouse 所属仓库,transport_name 快递名称,");
		czf_b2cob_sql.append("epistatic_order 前置单据号,convert(express_number using utf8) 运单号,itemtype_name 产品类型,");
		czf_b2cob_sql.append("sku_number 耗材编码,park_name 园区,park_cost_center 园区成本中心代码");
		czf_b2cob_sql.append(" from tb_warehouse_express_data ");
		czf_b2cob_sql.append("where store_name in ("+storeNames+") ");
		czf_b2cob_sql.append("and TO_DAYS(transport_time) >= TO_DAYS('" + param.getFromDate() + "') ");
		czf_b2cob_sql.append("and TO_DAYS(transport_time) <= TO_DAYS('" + param.getToDate() + "')"+limit);
		param.getParam().put("sql", czf_b2cob_sql.toString());
		param.getParam().put("logTitle", param.getContract().getContractOwnerName()+"查询");
		List<Map<String, Object>> czf_b2cob_sheet_list = consumerRevenueEstimateMapper.querySQL(param);
		Map<String, Object> czfMap = new LinkedHashMap<>();
		czfMap.put("sheet_name", sheet_name);
		Map<String, String> czf_title = new LinkedHashMap<>();
		czf_title.put("订单生成时间", "订单生成时间");
		czf_title.put("店铺", "店铺");
		czf_title.put("所属仓库", "所属仓库");
		czf_title.put("园区", "园区");
		czf_title.put("园区成本中心代码", "园区成本中心代码");
		czf_title.put("快递名称", "快递名称");
		czf_title.put("前置单据号", "前置单据号");
		czf_title.put("运单号", "运单号");
		czf_title.put("产品类型", "产品类型");
		czf_title.put("耗材编码", "耗材编码");
		//底部插入结算规则
		Map<String, Object> footMap = new LinkedHashMap<String, Object>();
		footMap.put("订单生成时间", "");
		footMap.put("店铺", "");
		footMap.put("所属仓库", "");
		footMap.put("园区", "");
		footMap.put("园区成本中心代码", "");
		footMap.put("快递名称", "");
		footMap.put("前置单据号", "");
		footMap.put("运单号", "");
		footMap.put("产品类型", "");
		footMap.put("耗材编码", "");
		param.getParam().put("sql", "select '结算规则' 订单生成时间,cat_type_name 店铺,'订单量' 所属仓库,"+
						"bill_num 园区,'耗材单价' 园区成本中心代码,hc_price 快递名称,price_unit 前置单据号,'' 运单号 from hc_main where"+
				" contract_id = " + param.getContract().getId() +" and cat_type=2");
		List<Map<String, Object>> list = consumerRevenueEstimateMapper.querySQL(param);
		czf_b2cob_sheet_list.add(footMap);
		czf_b2cob_sheet_list.add(list != null && list.size() > 0 ? list.get(0):new LinkedHashMap<String, Object>());
		czfMap.put("sheet_title", czf_title);
		czfMap.put("sheet_list", czf_b2cob_sheet_list);
		return czfMap;
	}
	
	

	/**
	 * 耗材实际采购量结算
	 * @param storeNames
	 * @param sheet_name
	 * @param param
	 * @param limit 
	 * @return
	 */
	private static Map<String, Object> add_hcf_original_sheet(String storeNames,//Ain ain,
			String sheet_name, EstimateParam param, String limit) {
		StringBuffer czf_b2cob_sql= new StringBuffer();
		czf_b2cob_sql.append("select ib_time 入库时间,vendor 供货商,inbound_no Po单号,barcode 条形码,bz_number 宝尊编码,");
		czf_b2cob_sql.append("item_name 产品名称,item_no 货号,inbound_qty 实际入库数量,purchase_price 单价 ,store_name 店铺 ,");
		czf_b2cob_sql.append("inbound_qty*hm.hc_price 费用,park_name 园区,park_cost_center 园区成本中心代码 ");
		czf_b2cob_sql.append("from tb_invitation_useanmount_data tiu inner join ");
		czf_b2cob_sql.append("(select * from hc_main where contract_id = " 
								+ param.getContract().getId() +" and cat_type=3) hm on tiu.bz_number = hm.hc_no ");
//		czf_b2cob_sql.append("where store_name in ("+storeNames+") and bz_number='"+ain.getHc_no()+"' ");
		czf_b2cob_sql.append("where store_name in ("+storeNames+") ");
		czf_b2cob_sql.append("and TO_DAYS(ib_time) >= TO_DAYS('" + param.getFromDate() + "') ");
		czf_b2cob_sql.append("and TO_DAYS(ib_time) <= TO_DAYS('" + param.getToDate() + "')"+limit);
		param.getParam().put("sql", czf_b2cob_sql.toString());
		param.getParam().put("logTitle", param.getContract().getContractOwnerName()+"查询");
		List<Map<String, Object>> czf_b2cob_sheet_list = consumerRevenueEstimateMapper.querySQL(param);
		Map<String, Object> czfMap = new LinkedHashMap<>();
		czfMap.put("sheet_name", sheet_name);
		Map<String, String> czf_title = new LinkedHashMap<>();
		czf_title.put("入库时间", "入库时间");
		czf_title.put("供货商", "供货商");
		czf_title.put("Po单号", "Po单号");
		czf_title.put("条形码", "条形码");
		czf_title.put("宝尊编码", "宝尊编码");
		czf_title.put("产品名称", "产品名称");
		czf_title.put("货号", "货号");
		czf_title.put("实际入库数量", "实际入库数量");
		czf_title.put("单价", "单价");
		czf_title.put("费用", "费用");
		czf_title.put("园区", "园区");
		czf_title.put("园区成本中心代码", "园区成本中心代码");
		czf_title.put("店铺", "店铺");
		czfMap.put("sheet_title", czf_title);
		czfMap.put("sheet_list", czf_b2cob_sheet_list);
		return czfMap;
	}
	
	

	/**
	 * @Title: addFreightsheet
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param param
	 * @return: Map<String,Object>
	 * @author: Ian.Huang
	 * @date: 2017年5月18日 下午4:42:19
	 */
	private static Map<String, Object> addFreightsheet(EstimateParam param){
		Map<String, Object> yfMap= new LinkedHashMap<>();
		yfMap.put("sheet_name", param.getParam().get("sheetName"));
		Map<String, String> yf_title = new LinkedHashMap<>();
		yf_title.put("所属仓库", "所属仓库");
		yf_title.put("所属店铺", "所属店铺");
		yf_title.put("订单号", "订单号");
		yf_title.put("备注", "备注");
		yf_title.put("商品编码", "商品编码");
		yf_title.put("长(CM)", "长(CM)");
		yf_title.put("宽(CM)", "宽(CM)");
		yf_title.put("高(CM)", "高(CM)");
		yf_title.put("体积(CM³)", "体积(CM³)");
		yf_title.put("发货时间", "发货时间");
//		yf_title.put("平台订单时间", "平台订单时间");
		yf_title.put("平台支付时间", "平台支付时间");
		yf_title.put("计费省", "计费省");
		yf_title.put("计费市", "计费市");
		yf_title.put("计费区", "计费区");
		
		//新增
		yf_title.put("园区编码", "园区编码");
		yf_title.put("园区名称", "园区名称");
		yf_title.put("园区成本中心", "园区成本中心");
		
		yf_title.put("订单销售件数", "订单销售件数");
		yf_title.put("发货重量(KG)", "发货重量(KG)");
		yf_title.put("物流商名称", "物流商名称");
		yf_title.put("产品类型", "产品类型");
		yf_title.put("是否COD", "是否COD");
		yf_title.put("订单金额(元)", "订单金额(元)");
		yf_title.put("运单号", "运单号");
		yf_title.put("类型", "类型");
		yf_title.put("计费重量(KG)", "计费重量(KG)");
		yf_title.put("保价费(元)", "保价费(元)");
		yf_title.put("首重报价(元)", "首重报价(元)");
		yf_title.put("续重报价(元)", "续重报价(元)");
		yf_title.put("折扣", "折扣");
		yf_title.put("COD手续费(元)", "COD手续费(元)");
		yf_title.put("运费(元)", "运费(元)");
		yf_title.put("最终费用(元)（不含COD）", "最终费用(元)（不含COD）");
		yfMap.put("sheet_title", yf_title);
		param.getParam().put("sql", "SELECT "
				+ "warehouse AS '所属仓库',"
				+ "store_name AS '所属店铺',"
				+ "epistatic_order AS '订单号',"
				+ "delivery_order AS '备注',"
				+ "sku_number AS '商品编码',"
				+ "length AS '长(CM)',"
				+ "width AS '宽(CM)',"
				+ "higth AS '高(CM)',"
				+ "volumn AS '体积(CM³)',"
				+ "DATE_FORMAT(transport_time, '%Y-%m-%d %T') AS '发货时间',"
//				+ "DATE_FORMAT(platform_order_time, '%Y-%m-%d %T') AS '平台订单时间',"
//				+ "DATE_FORMAT(platform_pay_time, '%Y-%m-%d %T') AS '平台支付时间',"
				+ "DATE_FORMAT(platform_order_time, '%Y-%m-%d %T') AS '平台支付时间',"
				+ "province AS '计费省',"
				+ "city AS '计费市',"
				+ "state AS '计费区',"
				
				//新增
				+ "park_code AS '园区编码',"
				+ "park_name AS '园区名称',"
				+ "park_cost_center AS '园区成本中心',"
				
				+ "CASE WHEN qty IS NOT NULL THEN qty ELSE '' END AS '订单销售件数',"
				+ "weight AS '发货重量(KG)',"
				+ "transport_name AS '物流商名称',"
				+ "itemtype_name AS '产品类型',"
				+ "CASE cod_flag WHEN true THEN '是' ELSE '否' END as '是否COD',"
				+ "order_amount AS '订单金额(元)',"
				+ "express_number AS '运单号',"
				+ "order_type AS '类型',"
				+ "charge_weight AS '计费重量(KG)',"
				+ "insurance_fee AS '保价费(元)',"
				+ "first_weight_price AS '首重报价(元)',"
				+ "added_weight_price AS '续重报价(元)',"
				+ "(100- discount)/100 AS '折扣',"
				+ "cod AS 'COD手续费(元)',"
				+ "afterdiscount_freight AS '运费(元)',"
				+ "total_fee AS '最终费用(元)（不含COD）' FROM tb_warehouse_express_data_store_settlement "
				+ param.getParam().get("conditionStr")
				+ param.getParam().get("limitStr"));
		param.getParam().put("logTitle", param.getContract().getContractOwnerName()+"下"+param.getParam().get("sheetName")+"运单明细查询");
		yfMap.put("sheet_list", consumerRevenueEstimateMapper.querySQL(param));
		return yfMap;
		
	}
	
	/**
	 * @Title: forCount
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param count
	 * @param max
	 * @return: int
	 * @author: Ian.Huang
	 * @date: 2017年5月18日 下午4:42:27
	 */
	private static int forCount(int count, int max) {
		int i= 1;
		if(count> max) {
			if((count% max)!= 0) {
				i= (count/ max)+ 1;
				
			}else{
				i= (count/ max);
				
			}
			
		}
		return i;
		
	}
	
	public static Map<String, Object> add_czf_sheet(StringBuffer str_storename,String job_type,String sheet_name,EstimateParam param,String limit){
		StringBuffer czf_b2cob_sql= new StringBuffer();
		//耐克匡威合同  B2C出库 额外判断
		if(param.getContract().getId() == 236 && job_type.equals("'退换货出库','销售出库'")) {
			czf_b2cob_sql.append("select warehouse_name 作业仓库 ,operation_time 操作时间,store_name 店铺,");
			czf_b2cob_sql.append("park_name 园区,park_cost_center 园区成本中心代码,job_orderno 作业单号,");
			czf_b2cob_sql.append("related_orderno 前置单据号,platform_order 平台订单号,job_type 作业类型,");
			czf_b2cob_sql.append("if(out_num=0,'入库','出库') 出入库方向,sum(if(out_num=0,in_num,out_num)) 出入库数量,");
			czf_b2cob_sql.append("sum(if(locate('40',barcode)!=1,if(out_num=0,in_num,out_num),0)) 销售件数,");
			czf_b2cob_sql.append("sum(if(locate('40',barcode)=1,if(out_num=0,in_num,out_num),0)) 赠品件数");
		} else {
			//默认
			czf_b2cob_sql.append("select operation_time 操作时间,job_orderno 作业单号,related_orderno 前置单据号,");
			czf_b2cob_sql.append("store_name 店铺,park_name 园区,park_cost_center 园区成本中心代码,");
			czf_b2cob_sql.append("platform_order 平台订单号,job_type 作业类型,item_name 商品名称 ,");
			czf_b2cob_sql.append("warehouse_name 作业仓库 ,operator 操作员,inventory_status 库存状态,item_size 商品大小,");
			czf_b2cob_sql.append("item_name 商品名称,art_no 货号,item_number 商品编码,storaggelocation_code 库位编码,");
			czf_b2cob_sql.append("brand_docking_code 品牌对接编码,barcode 条形码,");
			czf_b2cob_sql.append("sku_number SKU编码,if(out_num=0,'入库','出库') 出入库方向,");
			czf_b2cob_sql.append("if(out_num=0,in_num,out_num) 出入库数量");
		}
		
		czf_b2cob_sql.append(" from tb_operationfee_data x where store_name in ("+
				str_storename.toString()+") and job_type in ("+job_type+")"+
				" and TO_DAYS(operation_time) >= TO_DAYS('" +param.getFromDate()+"') and TO_DAYS(operation_time)"+
				" <= TO_DAYS('"+ param.getToDate() +"') and warehouse_code != 'SHWH215'");
		
		//耐克匡威合同额外判断
		if(param.getContract().getId() == 236) {
			czf_b2cob_sql.append(" group by job_orderno");
		}
		
		czf_b2cob_sql.append(" order by operation_time"+limit);
		param.getParam().put("sql", czf_b2cob_sql.toString());
		param.getParam().put("logTitle", param.getContract().getContractOwnerName()+"查询");
		List<Map<String, Object>> czf_b2cob_sheet_list = consumerRevenueEstimateMapper.querySQL(param);
		Map<String, Object> czfMap = new LinkedHashMap<>();
		czfMap.put("sheet_name", sheet_name);
		Map<String, String> czf_title = new LinkedHashMap<>();
		
		//耐克匡威合同额外判断
		if(param.getContract().getId() == 236 && job_type.equals("'退换货出库','销售出库'")) {

			czf_title.put("作业仓库", "作业仓库");
			czf_title.put("操作时间", "操作时间");
			czf_title.put("店铺", "店铺");
			czf_title.put("园区", "园区");
			czf_title.put("园区成本中心代码", "园区成本中心代码");
			czf_title.put("作业单号", "作业单号");
			czf_title.put("前置单据号", "前置单据号");
			czf_title.put("平台订单号", "平台订单号");
			czf_title.put("作业类型", "作业类型");
			czf_title.put("出入库方向", "出入库方向");
			czf_title.put("出入库数量", "出入库数量");
			czf_title.put("销售件数", "销售件数");
			czf_title.put("赠品件数", "赠品件数");
			
		} else {
			czf_title.put("作业仓库", "作业仓库");
			czf_title.put("操作时间", "操作时间");
			czf_title.put("店铺", "店铺");
			czf_title.put("园区", "园区");
			czf_title.put("园区成本中心代码", "园区成本中心代码");
			czf_title.put("作业单号", "作业单号");
			czf_title.put("前置单据号", "前置单据号");
			czf_title.put("平台订单号", "平台订单号");
			czf_title.put("作业类型", "作业类型");
			czf_title.put("库位编码", "库位编码");
			czf_title.put("出入库方向", "出入库方向");
			czf_title.put("出入库数量", "出入库数量");
			czf_title.put("条形码", "条形码");
			czf_title.put("商品编码", "商品编码");
			czf_title.put("SKU编码", "SKU编码");
			czf_title.put("品牌对接编码", "品牌对接编码");
			czf_title.put("扩展属性", "扩展属性");
			czf_title.put("货号", "货号");
			czf_title.put("商品名称", "商品名称");
			czf_title.put("商品大小", "商品大小");
			czf_title.put("库存状态", "库存状态");
			czf_title.put("操作员", "操作员");
		}
		czfMap.put("sheet_title", czf_title);
		czfMap.put("sheet_list", czf_b2cob_sheet_list);
		return czfMap;
		
	}
	
	public static Map<String, Object> add_hcf_sheet(String cb_id,String sheet_name,Integer catType,EstimateParam param, String limit){
		StringBuffer czf_b2cob_sql= new StringBuffer();
		czf_b2cob_sql.append("select '" + param.getFromDate()+ "-" + param.getToDate() +"' 结算周期 ,");
		czf_b2cob_sql.append("sku_code as 商品编码,sku_name as 商品名称,qty as 数量,price as 单价,");
		czf_b2cob_sql.append("park_name as 园区,park_cost_center as 园区成本中心代码,total_amount as 总价,");
		czf_b2cob_sql.append(" CASE sku_type when '1' then '主材' when '2' then '辅材' end as 类型");
		czf_b2cob_sql.append(" from  bal_invitation_useanmount_data_groupdetail_estimate ");
		czf_b2cob_sql.append("where contract_id="+cb_id+" and batch_number='"+param.getBatchNumber()+"' " +limit);
//		czf_b2cob_sql.append("and cat_type = " +catType);
		param.getParam().put("sql", czf_b2cob_sql.toString());
		param.getParam().put("logTitle", param.getContract().getContractOwnerName()+"查询");
		List<Map<String, Object>> czf_b2cob_sheet_list = consumerRevenueEstimateMapper.querySQL(param);
		Map<String, Object> czfMap = new LinkedHashMap<>();
		czfMap.put("sheet_name", sheet_name);
		Map<String, String> czf_title = new LinkedHashMap<>();
		czf_title.put("结算周期", "结算周期");
		czf_title.put("商品编码", "商品编码");
		czf_title.put("商品名称", "商品名称");
		czf_title.put("园区", "园区");
		czf_title.put("园区成本中心代码", "园区成本中心代码");
		czf_title.put("数量", "数量");
		czf_title.put("单价", "单价");
		czf_title.put("总价", "总价");
		czf_title.put("类型", "类型");
		czfMap.put("sheet_title", czf_title);
		czfMap.put("sheet_list", czf_b2cob_sheet_list);
		return czfMap;
	}
	public static Map<String, Object> add_dbf_sheet(String cb_id,String sheet_name,EstimateParam param){
		String sql = "select warehouse AS '所属仓库',store_name AS '所属店铺',epistatic_order AS '订单号',delivery_order AS '备注',"
				+ "sku_number AS '商品编码',order_type AS '订单类型',length AS '长(CM)',width AS '宽(CM)',higth AS '高(CM)',volumn AS '体积(CM³)',"
				+ "DATE_FORMAT(transport_time, '%Y-%m-%d-%T') AS '发货时间',province AS '计费省',city AS '计费市',state AS '计费区',"
				+ "weight AS '发货重量(KG)',transport_name AS '物流商名称',itemtype_name AS '产品类型',order_amount AS '订单金额(元)',"
				+ "express_number AS '运单号',"
				+ "package_price AS '打包费(元)' "
				+ "from bal_warehouse_express_data_store_settlement_estimate where contract_id="+cb_id+" and batch_number='"+param.getBatchNumber()+"'";
		param.getParam().put("sql", sql);
		param.getParam().put("logTitle", param.getContract().getContractOwnerName()+"查询打包费明细");
		List<Map<String, Object>> dbf_sheet_list = consumerRevenueEstimateMapper.querySQL(param);
		Map<String, Object> czfMap = new LinkedHashMap<>();
		czfMap.put("sheet_name", sheet_name);
		Map<String, String> yf_title = new LinkedHashMap<>();
		czfMap.put("sheet_list", dbf_sheet_list);
		
		yf_title.put("所属仓库", "所属仓库");
		yf_title.put("所属店铺", "所属店铺");
		yf_title.put("订单号", "订单号");
		yf_title.put("备注", "备注");
		yf_title.put("商品编码", "商品编码");
		yf_title.put("长(CM)", "长(CM)");
		yf_title.put("宽(CM)", "宽(CM)");
		yf_title.put("高(CM)", "高(CM)");
		yf_title.put("体积(CM³)", "体积(CM³)");
		yf_title.put("发货时间", "发货时间");
		yf_title.put("计费省", "计费省");
		yf_title.put("计费市", "计费市");
		yf_title.put("计费区", "计费区");
		yf_title.put("发货重量(KG)", "发货重量(KG)");
		yf_title.put("物流商名称", "物流商名称");
		yf_title.put("产品类型", "产品类型");
		yf_title.put("订单类型", "订单类型");
		yf_title.put("订单金额(元)", "订单金额(元)");
		yf_title.put("运单号", "运单号");
		yf_title.put("打包费(元)", "打包费(元)");
		czfMap.put("sheet_title", yf_title);
		return czfMap;
	}

	/** 
	* @Title: getPrice 
	* @Description: TODO(判断如参要取那个阶梯价格) 
	* @param @param list	阶梯表格
	* @param @param inListString 	需要比较的参数 
	* @param @param status	1.超过部分阶梯  2.总占用部分阶梯
	* @param @param section
	* @param @param price
	* @param @param pol
	* @param @param zks
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	public static Map<String, Object> getPrice(List<Map<String, Object>> list, BigDecimal inListString, String status,String section, String price, BigDecimal pol, String zks) {
		Map<String, Object> returnMap = new HashMap<>();
		returnMap.put("status", 500);
		returnMap.put("msg", inListString + "输入参数不在区间内!");
		BigDecimal sum = new BigDecimal(0.00);
		BigDecimal inListStringLS = inListString;
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			String sections = map.get(section).toString();
			String prices = map.get(price).toString();
			// B2C出库按件收费 总件数阶梯折扣
			Map<String, Object> sectionMap = IntervalValidationUtil.strToMapS(sections);
			if (status.equals("1")) {
				if (Integer.valueOf(sectionMap.get("type").toString()) == 1) {
					// 区间
					Integer startSymbol = Integer.valueOf(sectionMap.get("type").toString());
					BigDecimal startNum = new BigDecimal(sectionMap.get("startNum").toString());
					Integer endNum = Integer.valueOf(sectionMap.get("endNum").toString());
					if (startSymbol == 0) {
						// startNum大于 (
						if (inListString.compareTo(startNum) == 1) {
							// (]
							if (inListStringLS.compareTo(new BigDecimal(endNum)) >= 0) {
								sum = sum.add((new BigDecimal(endNum).subtract(startNum)).multiply(new BigDecimal(prices)));
							} else {
								sum = sum.add((inListStringLS.subtract(startNum)).multiply(new BigDecimal(prices)));
							}
						}
					} else {
						// startNum大于等于
						if (inListString.compareTo(startNum) >= 0) {
							// []
							if (inListStringLS.compareTo(new BigDecimal(endNum)) >= 0) {
								sum = sum.add((new BigDecimal(endNum).subtract(startNum)).multiply(new BigDecimal(prices)));
							} else {
								sum = sum.add((inListStringLS.subtract(startNum)).multiply(new BigDecimal(prices)));
							}
						}
					}
				}
			} else if (status.equals("2")) {
				if (Integer.valueOf(sectionMap.get("type").toString()) == 1) {
					// 区间
					Integer startSymbol = Integer.valueOf(sectionMap.get("startSymbol").toString());
					BigDecimal startNum = new BigDecimal(sectionMap.get("startNum").toString());
					Integer endSymbol = Integer.valueOf(sectionMap.get("endSymbol").toString());
					BigDecimal endNum = new BigDecimal(sectionMap.get("endNum").toString());
					if (startSymbol == 0) {
						// startNum大于 (
						if (inListString.compareTo(startNum) == 1) {
							if (endSymbol == 1) {
								// (]
								if (inListString.compareTo(endNum) <= 0) {
									if (null != zks) {
										BigDecimal zk = new BigDecimal(map.get(zks).toString());
										sum = sum.add(new BigDecimal(prices).multiply(inListString).multiply(zk));
										returnMap.put("zk", zk);
									} else {
										if (null != pol) {
											sum = sum.add(new BigDecimal(prices).multiply(pol));
										} else {
											sum = sum.add(new BigDecimal(prices).multiply(inListString));
										}
									}
								}
							} else {
								// ()
								if (inListString.compareTo(endNum) < 0) {
									if (null != zks) {
										BigDecimal zk = new BigDecimal(map.get(zks).toString());
										sum = sum.add(new BigDecimal(prices).multiply(inListString).multiply(zk));
										returnMap.put("zk", zk);
									} else {
										if (null != pol) {
											sum = sum.add(new BigDecimal(prices).multiply(pol));
										} else {
											sum = sum.add(new BigDecimal(prices).multiply(inListString));
										}
									}
								}
							}
						}
					} else {
						// startNum大于等于
						if (inListString.compareTo(startNum) >= 0) {
							if (endSymbol == 1) {
								// []
								if (inListString.compareTo(endNum) >= 0) {
									if (null != zks) {
										BigDecimal zk = new BigDecimal(map.get(zks).toString());
										sum = sum.add(new BigDecimal(prices).multiply(inListString).multiply(zk));
										returnMap.put("zk", zk);
									} else {
										if (null != pol) {
											sum = sum.add(new BigDecimal(prices).multiply(pol));
										} else {
											sum = sum.add(new BigDecimal(prices).multiply(inListString));
										}
									}
								}
							} else {
								// [)
								if (inListString.compareTo(endNum) < 0) {
									if (null != zks) {
										BigDecimal zk = new BigDecimal(map.get(zks).toString());
										sum = sum.add(new BigDecimal(prices).multiply(inListString).multiply(zk));
										returnMap.put("zk", zk);
									} else {
										if (null != pol) {
											sum = sum.add(new BigDecimal(prices).multiply(pol));
										} else {
											sum = sum.add(new BigDecimal(prices).multiply(inListString));
										}
									}
								}
							}
						}
					}
				}
				returnMap.put("price", prices);
			}
		}
		

		returnMap.put("status", 360);
		returnMap.put("msg", sum);
		return returnMap;
	}
	
	/**
	 * Btc出库操作费例外情况
	 * @param param
	 * @param wb
	 * @param storeNames
	 * @param client_name
	 */
	private static void export_BTCout_ex(EstimateParam param,SXSSFWorkbook wb,StringBuffer storeNames,String client_name){
		param.getParam().put("sql", "select count(1) from (select related_orderno from tb_operationfee_data_detail where store_name in ("+
				storeNames.toString()+") and job_type in ('退换货出库','销售出库') " +
				"and TO_DAYS(operation_time) >= TO_DAYS('"+ param.getFromDate() +"') and TO_DAYS(operation_time)"+
				" <= TO_DAYS('" + param.getToDate() + "') group by related_orderno) x ");
		param.getParam().put("logTitle", client_name + "B2C出库操作费");
		int count1 = consumerRevenueEstimateMapper.countSQL(param);
		int a= 0;
		for(int j= 1; j< forCount(count1, BaseConst.excel_pageSize) + 1; j++) {
			try {
				wb = POIUtil.create_sheet(wb, add_czf_detail_sheet(storeNames, 
										"'退换货出库','销售出库'", client_name + "-B2C出库操作费-"+ j, 
										param, " limit "+ a+ ","+BaseConst.excel_pageSize), false);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			a= j*BaseConst.excel_pageSize;
			
		}
	}
	
	
	
	
	
	
	
}