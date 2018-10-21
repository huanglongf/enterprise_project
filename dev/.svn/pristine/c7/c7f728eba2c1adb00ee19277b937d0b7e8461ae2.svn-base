package com.bt.lmis.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.bt.EPlatform;
import com.bt.OSinfo;
import com.bt.lmis.balance.dao.ExBillTaskMapper;
import com.bt.lmis.balance.model.ExBillTask;
import com.bt.lmis.controller.form.ExpressBalanceDetialQueryParam;
import com.bt.lmis.controller.form.SummaryQueryParam;
import com.bt.lmis.dao.BalanceSummaryExMapper;
import com.bt.lmis.dao.ExpressBalancedDataMapper;
import com.bt.lmis.dao.ExpressContractConfigMapper;
import com.bt.lmis.dao.ExpressContractMapper;
import com.bt.lmis.dao.ExpressRawDataMapper;
import com.bt.lmis.dao.TotalFreightDiscountMapper;
import com.bt.lmis.dao.TransportProductTypeMapper;
import com.bt.lmis.dao.WaybillDiscountExMapper;
import com.bt.lmis.model.BalanceReportExpressParam;
import com.bt.lmis.model.BalanceSummaryEx;
import com.bt.lmis.model.ExpressContractConfig;
import com.bt.lmis.model.ExpressRawData;
import com.bt.lmis.model.ExpressSummary;
import com.bt.lmis.model.TransportProductType;
import com.bt.lmis.model.tbContractBasicinfo;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.ExpressBalanceService;
import com.bt.lmis.thread.ExpressBalanceRunnable;
import com.bt.lmis.utils.ExpressBalanceUtils;
import com.bt.utils.BigExcelExport;
import com.bt.utils.CommonUtils;
import com.bt.utils.Constant;
import com.bt.utils.DateUtil;
import com.bt.utils.FileUtil;
import com.bt.utils.SessionUtils;

/**
 * @Title:ExpressBalanceServiceImpl
 * @Description: TODO(快递结算ServiceImpl) 
 * @author Ian.Huang 
 * @date 2016年11月9日下午5:25:38
 */
@Service
public class ExpressBalanceServiceImpl implements ExpressBalanceService {

	// 定义日志
	private static final Logger logger = Logger.getLogger(ExpressBalanceServiceImpl.class);
	
	@Autowired
	private TransportProductTypeMapper<T> transportProductTypeMapper;
	@Autowired
	private TotalFreightDiscountMapper<T> totalFreightDiscountMapper;
	@Autowired
	private WaybillDiscountExMapper<T> waybillDiscountMapper;
	@Autowired
	private BalanceSummaryExMapper<T> balanceSummaryExMapper;
	@Autowired
	private ExpressBalancedDataMapper<T> expressBalancedDataMapper;
	@Autowired
	private ExpressRawDataMapper<T> expressRawDataMapper;
	@Autowired
	private ExpressContractConfigMapper<T> expressContractConfigMapper;
	@Autowired
	private ExpressContractMapper<T> expressContractMapper;
	@Autowired
	private ExBillTaskMapper<T> exBillTaskMapper;
	
	@Override
	public JSONObject exportErrorWaybill(ExpressBalanceDetialQueryParam queryParam, HttpServletRequest request, JSONObject result) throws Exception {
		result= new JSONObject();
		// 结算周期
		Map<String, Object> balance_cycle= DateUtil.getBalanceCycle(request.getParameter("balance_month"), Integer.parseInt(expressContractMapper.findById(queryParam.getCon_id()).get("settle_date").toString()));
		// 结算日期开始
		queryParam.setBalance_start_date(balance_cycle.get("balance_start_date").toString());
		// 结算日期结束
		queryParam.setBalance_end_date(balance_cycle.get("balance_end_date").toString());
		// 快递结算异常
		queryParam.setSettle_flag(2);
		if(expressRawDataMapper.countByFlag(queryParam) == 0) {
			result.put("result_code", "FAILURE");
			result.put("result_code", "暂无异常运单！");
			
		} else {
			LinkedHashMap<String, String> title= new LinkedHashMap<String, String>();
			title.put("store_code", "店铺代码");
			title.put("store_name", "店铺名称");
			title.put("warehouse", "仓库");
			title.put("transport_code", "承运商代码");
			title.put("transport_name", "承运商名称");
			title.put("delivery_order", "发货指令");
			title.put("epistatic_order", "上位系统订单号");
			title.put("order_type", "订单类型");
			title.put("express_number", "运单号");
			title.put("transport_direction", "运输方向（正向运输/逆向退货）");
			title.put("itemtype_code", "产品类型代码");
			title.put("itemtype_name", "产品类型名称");
			title.put("transport_time", "订单生成时间");
			title.put("collection_on_delivery", "代收货款金额");
			title.put("order_amount", "订单金额(元)");
			title.put("sku_number", "SKU编码");
			title.put("weight", "实际重量(KG)");
			title.put("length", "长(CM)");
			title.put("width", "宽(CM)");
			title.put("higth", "高(CM)");
			title.put("volumn", "体积(CM³)");
			title.put("delivery_address", "始发地");
			title.put("province", "目的省");
			title.put("city", "市");
			title.put("state", "区");
			title.put("street", "街道");
			title.put("detail_address", "详细地址");
			title.put("insurance_flag", "是否保价");
			title.put("cod_flag", "是否COD");
			result.put("result_code", "SUCCESS");
			result.put("result_content", CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_MAP")+ BigExcelExport.excelDownLoadDatab(expressRawDataMapper.selectByFlag(queryParam), title,
					queryParam.getExpress_name()+ request.getParameter("balance_month")+ "月异常运单明细_"+ SessionUtils.getEMP(request).getEmployee_number()+ "_"+ new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+ ".xls").getName()
					
					);
			
		}
		return result;
		
	}
	
	@Override
	public QueryResult<Map<String, Object>> findAllBalanceDetail(ExpressBalanceDetialQueryParam queryParam, HttpServletRequest request)
			throws Exception {
		// 结算周期
		Map<String, Object> balance_cycle= DateUtil.getBalanceCycle(request.getParameter("balance_month"), Integer.parseInt(expressContractMapper.findById(queryParam.getCon_id()).get("settle_date").toString()));
		// 结算日期开始
		queryParam.setBalance_start_date(balance_cycle.get("balance_start_date").toString());
		// 结算日期结束
		queryParam.setBalance_end_date(balance_cycle.get("balance_end_date").toString());
		QueryResult<Map<String, Object>> qr= new QueryResult<Map<String, Object>>();
		qr.setResultlist(expressBalancedDataMapper.selectAllBalancedData(queryParam));
		qr.setTotalrecord(expressBalancedDataMapper.countAllBalancedData(queryParam));
		return qr;
		
	}
	
	@Override
	public ExpressSummary findAllSummary(HttpServletRequest request) throws Exception {
		// 汇总报表实体
		ExpressSummary expressSummary= new ExpressSummary();
		// 判断是否存在产品类型
		List<TransportProductType> productType= transportProductTypeMapper.findByTransportVendor(true, request.getParameter("express_code"));
		boolean flag= false;
		if(CommonUtils.checkExistOrNot(productType)){
			// 存在产品类型
			flag= true;
			
		}
		// 表头构成
		Map<String, String> title= new LinkedHashMap<String, String>();
		title.put("cost_center", "成本中心");
		title.put("store", "店铺");
		title.put("warehouse", "仓库");
		title.put("order_num", "单量(单)");
		if(flag) {
			for(int i= 0; i< productType.size(); i++){
				title.put("standard_freight_"+ productType.get(i).getProduct_type_code(), productType.get(i).getProduct_type_name()+ "标准运费（元）");
				title.put("discount_"+ productType.get(i).getProduct_type_code(), productType.get(i).getProduct_type_name()+ "单运单优惠金额（元）");
				
			}
			
		} else {
			title.put("standard_freight", "标准运费(元)");
			title.put("discount", "单运单优惠金额(元)");
			title.put("total_discount", "总运费折扣优惠金额(元)");
			
		}
		title.put("insurance", "保价费(元)");
		title.put("specialService", "特殊服务费(元)");
		title.put("total_fee", "总运费(元)");
		expressSummary.setTitle(title);
		// 表内容
		// 封装请求参数
		Map<String, Object> param= CommonUtils.getParamMap(request);
		// 当前最大版本号作为参数
		param.put("ver_id", balanceSummaryExMapper.selectCurrentVerId(param.get("express_code").toString(), param.get("balance_month").toString()));
		// 查询汇总集合
		List<BalanceSummaryEx> resultList= balanceSummaryExMapper.selectAllBallanceSummary(param);
		if(CommonUtils.checkExistOrNot(resultList)){
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
			// 构造显示内容
			List<Map<String, Object>> results= new ArrayList<Map<String, Object>>();
			Map<String, Object> row= null;
			BalanceSummaryEx bSE= null;
			String uk= "";
			// 运单量
			Integer order_num= 0;
			// 保价费
			BigDecimal insurance= new BigDecimal(0);
			// 总费用
			BigDecimal total_fee= new BigDecimal(0);
			for(int i= 0; i< resultList.size(); i++){
				bSE= resultList.get(i);
				if(!uk.equals(bSE.getStore_name() + bSE.getWarehouse_name())){
					if(i != 0){
						row.put("order_num", order_num);
						total.put("order_num", Integer.parseInt(total.get("order_num").toString()) + order_num);
						row.put("insurance", insurance);
						total.put("insurance", new BigDecimal(total.get("insurance").toString()).add(insurance));
						row.put("total_fee", total_fee);
						total.put("total_fee", new BigDecimal(total.get("total_fee").toString()).add(total_fee));
						results.add(row);
						
					} else {
						expressSummary.setCon_id(bSE.getCon_id());
						
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
					results.add(row);
					
				}
				
			}
			results.add(total);
			expressSummary.setResults(results);
			
		}
		return expressSummary;
		
	}
	
	@Override
	public QueryResult<Map<String, Object>> findAllByMonth(SummaryQueryParam queryParam) throws Exception {
		QueryResult<Map<String, Object>> qr = new QueryResult<Map<String, Object>>();
		qr.setResultlist(balanceSummaryExMapper.findAllByMonth(queryParam));
		qr.setTotalrecord(balanceSummaryExMapper.countRecordsByMonth(queryParam));
		return qr;
		
	}
	
	/**
	 * @Title: returnBill
	 * @Description: TODO(账单还原)
	 * @param expressCode
	 * @param expressName
	 * @param ebt
	 * @throws Exception
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年11月25日 下午6:15:45
	 */
	private void returnBill(String expressCode, String expressName, ExBillTask ebt) throws Exception {
		// 手动提交事务
	    WebApplicationContext contextLoader = ContextLoader.getCurrentWebApplicationContext();
	    DataSourceTransactionManager transactionManager = (DataSourceTransactionManager)contextLoader.getBean("transactionManager");
	    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	    // 事物隔离级别，开启新事务，这样会比较安全些。
	    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
	    // 获得事务状态
	    TransactionStatus status = transactionManager.getTransaction(def);
	    try {
	    	// 删除汇总记录
	    	balanceSummaryExMapper.deleteSummary(expressCode, ebt.getBillYm());
			// 如为顺丰快递还原结算明细折扣
	    	if("SF".equals(expressCode)) {
	    		balanceSummaryExMapper.returnDetail(expressName, ebt.getBillCycleStart(), ebt.getBillCycleEnd());
	    	}
	        transactionManager.commit(status);
	    } catch (Exception e) {
	        transactionManager.rollback(status);
	        throw e;
	    }
	}
	
	@Override
	public void expressBalanceSummary() {
		// 检测是否存在有效快递合同
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("contractType", "1");
		params.put("contractOwner", null);
		// 获取有效快递合同
		List<Map<String, Object>> eCs = expressContractMapper.findValidContract(params);
		if(CommonUtils.checkExistOrNot(eCs)) {
			// 存在
			// 生成版本号
			Integer ver_id=null;
			// 合同ID
			Integer con_id=null;
			// 快递编码
			String contract_owner=null;
			// 快递名称
			String transport_name=null;
			// 快递合同
			Map<String, Object> eC=null;
			for(int i=0; i<eCs.size(); i++) {
				try {
					eC=eCs.get(i);
					// 若今天不在合同周期内
					if(!DateUtil.isTime(eC.get("contract_start").toString(), eC.get("contract_end").toString(), DateUtil.getCalendarToString(DateUtil.getYesterDay()))){
						// 不结算并修改有效状态
						expressContractMapper.updateValidity(Integer.parseInt(eC.get("id").toString()), "SYSTEM", 0);
						continue;
					}
					List<ExBillTask> ebts=null;
					ExBillTask ebt=null;
					//　查看该合同昨天是否为结算日期
					if(!DateUtil.judgeSummaryOrNot(Integer.parseInt(eC.get("settle_date").toString()))) {
						// 该合同昨天不是结算日期
						// 查看该合同有效账单任务是否存在
						ebts = exBillTaskMapper.getEnabledTaskByContractId(Integer.parseInt(eC.get("id").toString()));
						if(!CommonUtils.checkExistOrNot(ebts)) {
							// 该合同有效账单任务不存在
							continue;
						}
						// 该合同有效账单任务存在，需要结算
					} else {
						// 该合同昨天是结算日期，今天需要结算
						ebts = new ArrayList<ExBillTask>();
						ebts.add(new ExBillTask(
								DateUtil.getMonth(DateUtil.getYesterDay()),
								DateUtil.getBeforeMToString(DateUtil.getYesterDay()),
								DateUtil.getCalendarToString(DateUtil.getYesterDay())));
					}
					// 已到结算日期或存在合同有效账单任务则汇总
					con_id= Integer.parseInt(eC.get("id").toString());
					contract_owner= eC.get("contract_owner").toString();
					transport_name= eC.get("transport_name").toString();
					for(int j=0; j < ebts.size(); j++) {
						ebt = ebts.get(j);
						// 校验年月格式必须为2017-1而不是2017-01
						if(ebt.getBillYm().indexOf("-0") != -1) {
							ebt.setBillYm(ebt.getBillYm().replace("-0", "-"));
						}
						if(balanceSummaryExMapper.selectRecordsExist(contract_owner, ebt.getBillYm()) != 0) {
							// 当该快递该月份汇总已做过
							// 删除汇总文件
							// 快递账单文件路径
							String fileGenerationPath = CommonUtils.getAllMessage("config", "BALANCE_BILL_EXPRESS_" + OSinfo.getOSname()) + ebt.getBillYm();
							if(OSinfo.getOSname().equals(EPlatform.Linux) || OSinfo.getOSname().equals(EPlatform.Mac_OS_X)) {
								fileGenerationPath += "/";							
							} else if(OSinfo.getOSname().equals(EPlatform.Windows)) {
								fileGenerationPath += "\\";
							}
							FileUtil.isExistFile(fileGenerationPath + transport_name + ebt.getBillYm() + "月结算报表" + Constant.SEPARATE_POINT + Constant.SUF_XLSX);
							// 账单数据还原
							returnBill(contract_owner, transport_name, ebt);
						}
						System.out.println("********************" + transport_name + "[" + ebt.getBillYm() + "]开始汇总********************");
						// 汇总查询
						List<Map<String, Object>> summarys=expressBalancedDataMapper.getSummary(transport_name, ebt.getBillCycleStart(), ebt.getBillCycleEnd());
						Map<String, Object> summary=null;
						// 汇总存在
						if(CommonUtils.checkExistOrNot(summarys)) {
							ver_id = balanceSummaryExMapper.selectCurrentVerId(contract_owner, ebt.getBillYm());
							if(!CommonUtils.checkExistOrNot(ver_id)){
								ver_id = 1;
							} else {
								ver_id += 1;
							}
							// 汇总集合
							List<BalanceSummaryEx> bSEs=new ArrayList<BalanceSummaryEx>();
							// 结算汇总
							BalanceSummaryEx bSE=null;
							for(int m=0; m<summarys.size(); m++){
								summary= summarys.get(m);
								// 汇总记录
								bSE= new BalanceSummaryEx();
								bSE.setVer_id(ver_id);
								bSE.setCon_id(con_id);
								bSE.setExpress(contract_owner);
								bSE.setBalance_month(ebt.getBillYm());
								bSE.setCost_center(summary.get("cost_center") + "");
								bSE.setStore_code(summary.get("store_code") + "");
								bSE.setStore_name(summary.get("store_name") + "");
								bSE.setWarehouse_name(summary.get("warehouse_name") + "");
								bSE.setOrder_num(Integer.parseInt(summary.get("order_num") + ""));
								bSE.setProduct_type_code(summary.get("product_type_code") + "");
								bSE.setProduct_type_name(summary.get("product_type_name") + "");
								bSE.setProduct_type_freight(new BigDecimal(summary.get("product_type_freight") + ""));
								bSE.setProduct_type_discount(new BigDecimal(summary.get("product_type_discount") + ""));
								bSE.setProduct_type_total_discount(new BigDecimal(0));
								bSE.setInsurance(new BigDecimal(summary.get("insurance") + ""));
								bSE.setTotal_fee(new BigDecimal(summary.get("total_fee") + ""));
								bSEs.add(bSE);
							}
							// 批量插入
							balanceSummaryExMapper.insertBatch(bSEs);
							// **********折扣部分**********
							// 合同配置
							ExpressContractConfig eCC = expressContractConfigMapper.findECC(con_id, contract_owner);
							// 是否配置运单折扣启用条件（顺丰才有）
							if(eCC.getWaybill_discount()) {
								// 产品类型
								String product_type=null;
								// 运单折扣启用条件集合
								List<Map<String, Object>> wDs=null;
								// 运单折扣启用条件
								Map<String, Object> wD=null;
								//　折扣率
								BigDecimal discount=null;
								// 查询汇总
								List<Map<String,Object>> summerMaps=balanceSummaryExMapper.getSummarySF(ver_id, contract_owner, ebt.getBillYm());
								if(CommonUtils.checkExistOrNot(summerMaps)) {
									for(Map<String,Object> summerMap : summerMaps) {
										if(CommonUtils.checkExistOrNot(summerMap)) {
											product_type = CommonUtils.checkExistOrNot(summerMap.get("product_type_code")) ? summerMap.get("product_type_code").toString() : "";
											// 获取运单折扣启用条件
											wDs= waybillDiscountMapper.selectAllWD(con_id, contract_owner, product_type);
											// 当该承运商的指定产品类型运单折扣启用条件存在
											if(CommonUtils.checkExistOrNot(wDs)){
												// 总标准运费是否满足折扣条件
												for(int z= 0; z< wDs.size(); z++){
													wD= wDs.get(z);
													// 当满足条件时
													if(CommonUtils.inRegionOrNot(wD, new BigDecimal(summerMap.get("afterDiscount").toString()))){
														// 确定单运单折扣率
														discount= new BigDecimal(wD.get("discount").toString()).divide(new BigDecimal(100));
														// 更新结算明细
														expressBalancedDataMapper.updateBatch(discount, transport_name, product_type, ebt.getBillCycleStart(), ebt.getBillCycleEnd());
														// 更新汇总明细
														balanceSummaryExMapper.updateBatch(discount, ver_id, contract_owner, ebt.getBillYm(), product_type, null);
														break;
													}
												}
											}
										}
									}
								}
							}
							// 是否配置总费用折扣开关（非顺丰才有）
							if(eCC.getTotal_freight_discount()) {
								// 查询汇总
								summary=balanceSummaryExMapper.getSummary(ver_id, contract_owner, ebt.getBillYm(), null);
								if(CommonUtils.checkExistOrNot(summary)) {
									// 获取总费用折扣规则
									// 总费用折扣规则
									List<Map<String, Object>> tFDs=totalFreightDiscountMapper.selectAllTFD(con_id, contract_owner, null);
									// 总运费折扣率
									BigDecimal tFDP=ExpressBalanceUtils.getTotalFreightDiscount(summary, tFDs);
									if(CommonUtils.checkExistOrNot(tFDP)){
										balanceSummaryExMapper.updateBatch(tFDP, ver_id, contract_owner, ebt.getBillYm(), null, Integer.parseInt(tFDs.get(0).get("insurance_contain").toString()));
									}
								}
							}
							System.out.println("********************" + transport_name + "[" + ebt.getBillYm() + "]汇总成功********************");
							System.out.println("********************" + transport_name + "[" + ebt.getBillYm() + "]开始生成账单********************");
							ExpressBalanceUtils.generateBalanceReportExpress(new BalanceReportExpressParam(con_id.toString(), contract_owner, transport_name, ebt.getBillYm()));
							// 当是通过任务机制出的账单，需要使此完成的任务失效
							if(CommonUtils.checkExistOrNot(ebt.getId())) {
								exBillTaskMapper.updateTaskDisabled(ebt.getId());
							}
						}
					}
				} catch(Exception e) {
					e.printStackTrace();
					logger.error(e);	
				}
			}
		}
		// 无有效合同
	}
	
	@Override
	public void expressBalance(ExecutorService pool, Integer maxThreadNum, Integer recommendedSingleThreadProcessingNum) throws Exception {
		Map<String, Object> params= new HashMap<String, Object>();
		params.put("contractType", "1");
		params.put("contractOwner", null);
		// 检测是否存在有效快递合同
		List<Map<String, Object>> eCs= expressContractMapper.findValidContract(params);
		if(CommonUtils.checkExistOrNot(eCs)) {
			//　存在
			for(int i= 0; i< eCs.size(); i++) {
				//　按合同为主体结算原始数据
				Map<String, Object> eC= eCs.get(i);
				// 若今天不在合同周期内
				if(!DateUtil.isTime(eC.get("contract_start").toString(), eC.get("contract_end").toString(), DateUtil.formatDate(new Date()))){
					// 不结算并修改有效状态
					expressContractMapper.updateValidity(Integer.parseInt(eC.get("id").toString()), "SYSTEM", 0);
					continue;
				}
				//　快递合同配置
				ExpressContractConfig eCC= expressContractConfigMapper.findECC(Integer.parseInt(eC.get("id").toString()), eC.get("contract_owner").toString());
				// 初始化批次处理量为最大线程与建议单线程处理量之积
				int batch_num=maxThreadNum*recommendedSingleThreadProcessingNum;
				// 检测是否存在-本次结算主体合同-周期内有效-未结算过-状态正常的原始数据
				// 原始数据集合
				List<ExpressRawData> eRDs=expressRawDataMapper.selectRecords(null, null, null, 
						eC.get("transport_name").toString(), 
						eC.get("contract_start").toString(), 
						eC.get("contract_end").toString(), 
//						"1", 
						0,
						batch_num,
						"'1','3'"
						);
				while(CommonUtils.checkExistOrNot(eRDs)) {
					// 存在数据
					// 初始化并发线程数为最大线程数
					int threadNum=0;
					if(eRDs.size()<batch_num) {
						// 符合条件数据量小于批次处理量时
						threadNum=eRDs.size()/recommendedSingleThreadProcessingNum;
						if(eRDs.size()%recommendedSingleThreadProcessingNum != 0) {
							threadNum++; 
						}
					} else {
						threadNum=maxThreadNum;
					}
					// 线程池队列
					Queue<Future<?>> queue=new ArrayBlockingQueue<Future<?>>(threadNum);
					// 多线程调用
					for(int threadNo=0; threadNo<threadNum; threadNo++) {
						// 创建一个Runnable实现类的对象
						int startPoint=threadNo*recommendedSingleThreadProcessingNum;
						int endPoint=startPoint+recommendedSingleThreadProcessingNum;
						queue.add(pool.submit(new ExpressBalanceRunnable(startPoint, 
								endPoint>=eRDs.size()?eRDs.size():endPoint,
										eRDs, eC, eCC)));
					}
					// 是否所有任务都完成
					while(true) {
						if(queue.size() != 0) {
							if(queue.peek().isDone()) {
								queue.poll();
								
							} else {
								Thread.sleep(5000);
								
							}
							
						} else {
							break;
								
						}
						
					}
					eRDs=expressRawDataMapper.selectRecords(null, null, null, 
							eC.get("transport_name").toString(), 
							eC.get("contract_start").toString(), 
							eC.get("contract_end").toString(), 
//							"1", 
							0, 
							batch_num,
							"'1','3'"
							);
					
				}
				
			}
			
		}
		// 无有效合同
		
	}
	
	@Override
	public tbContractBasicinfo querytbContractBasicinfo(String con_id) {
		return balanceSummaryExMapper.querytbContractBasicinfo(con_id);
		
	}

}