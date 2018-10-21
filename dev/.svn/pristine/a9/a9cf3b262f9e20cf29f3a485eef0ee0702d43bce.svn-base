package com.bt.lmis.service.impl;

import java.io.File;
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

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.controller.form.ExpressBalanceDetialQueryParam;
import com.bt.lmis.dao.BalanceErrorLogMapper;
import com.bt.lmis.dao.BalanceSummaryUsedExMapper;
import com.bt.lmis.dao.CarrierSospMapper;
import com.bt.lmis.dao.ClientMapper;
import com.bt.lmis.dao.ContractBasicinfoMapper;
import com.bt.lmis.dao.ExpressContractConfigMapper;
import com.bt.lmis.dao.ExpressRawDataMapper;
import com.bt.lmis.dao.ExpressUsedBalancedDataMapper;
import com.bt.lmis.dao.PackageChargeMapper;
import com.bt.lmis.dao.StoreMapper;
import com.bt.lmis.model.BalanceErrorLog;
import com.bt.lmis.model.BalanceSummaryUsedEx;
import com.bt.lmis.model.Client;
import com.bt.lmis.model.ContractBasicinfo;
import com.bt.lmis.model.ExpressContractConfig;
import com.bt.lmis.model.ExpressRawData;
import com.bt.lmis.model.Store;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.ExpressUsedBalanceService;
import com.bt.lmis.thread.ExpressUsedBalanceRunnable;
import com.bt.utils.BaseConst;
import com.bt.utils.BigExcelExport;
import com.bt.utils.CommonUtils;
import com.bt.utils.DateUtil;
import com.bt.utils.SessionUtils;

/**
 * @Title:ExpressUsedBalanceServiceImpl
 * @Description: TODO  
 * @author Ian.Huang 
 * @date 2016年11月27日下午1:10:40
 */
@Service
public class ExpressUsedBalanceServiceImpl implements ExpressUsedBalanceService {
	
	@Autowired
	private BalanceSummaryUsedExMapper<T> balanceSummaryUsedExMapper;
	@Autowired
	private ExpressContractConfigMapper<T> expressContractConfigMapper;
	@Autowired
	private ExpressUsedBalancedDataMapper<T> expressUsedBalancedDataMapper;
	@Autowired
	private PackageChargeMapper<T> packageChargeMapper;
	@Autowired
	private ExpressRawDataMapper<T> expressRawDataMapper;
	@Autowired
	private CarrierSospMapper<T> carrierSospMapper;
	@Autowired
    private ClientMapper<T> clientMapper;
	@Autowired
	private BalanceErrorLogMapper<T> balanceErrorLogMapper;
	@Autowired
    private StoreMapper<T> storeMapper;
	@Autowired
	private ContractBasicinfoMapper<T> contractBasicinfoMapper;

	@Override
	public JSONObject exportErrorWaybill(ExpressBalanceDetialQueryParam queryParam, 
			HttpServletRequest request, JSONObject result) throws Exception {
		result= new JSONObject();
		List<ContractBasicinfo> cbList= contractBasicinfoMapper.find_by_cb();
		ContractBasicinfo cb= null;
		List<Map<String, Object>> list= new ArrayList<Map<String, Object>>();
		for(int i= 0; i< cbList.size(); i++) {
			cb= cbList.get(i);
			// 结算周期
			Map<String, Object> balance_cycle= DateUtil.getBalanceCycle(
					request.getParameter("balance_month"), Integer.parseInt(cb.getSettle_date().toString()));
			// 结算日期开始
			queryParam.setBalance_start_date(balance_cycle.get("balance_start_date").toString());
			// 结算日期结束
			queryParam.setBalance_end_date(balance_cycle.get("balance_end_date").toString());
			// 快递结算异常
			queryParam.setSettle_client_flag(2);
			Integer contract_type= Integer.parseInt(cb.getContract_type());
			List<Store> storeList= null;
			if(contract_type == 3){
				storeList= new ArrayList<Store>();
				storeList.add(storeMapper.findByContractOwner(cb.getContract_owner()));
				
			} else if(contract_type == 4) {
				storeList= storeMapper.selectByClient(clientMapper.findByContractOwner(
						cb.getContract_owner()).getId());
				
			}
			if(CommonUtils.checkExistOrNot(storeList)) {
				Store store= null;
				for(int j= 0; j< storeList.size(); j++) {
					store= storeList.get(j);
					queryParam.setCost_center(store.getCost_center());
					queryParam.setStore_name(store.getStore_name());
					list.addAll(expressRawDataMapper.selectByFlag(queryParam));
					
				}
				
			}
			
		}
		if(CommonUtils.checkExistOrNot(list)) {
			LinkedHashMap<String, String> title= new LinkedHashMap<String, String>();
			title.put("warehouse", "所属仓库");
			title.put("store_name", "所属店铺");
			title.put("epistatic_order", "订单号");
			title.put("delivery_order", "备注");
			title.put("sku_number", "商品编码");
			title.put("length", "长(CM)");
			title.put("width", "宽(CM)");
			title.put("higth", "高(CM)");
			title.put("volumn", "体积(CM³)");
			title.put("transport_time", "发货时间");
			title.put("province", "计费省");
			title.put("city", "计费市");
			title.put("state", "计费区");
			title.put("weight", "发货重量(KG)");
			title.put("transport_name", "物流商名称");
			title.put("itemtype_name", "产品类型");
			title.put("cod_flag", "是否COD");
			title.put("order_amount", "订单金额(元)");
			title.put("express_number", "运单号");
			title.put("order_type", "类型");
			result.put("result_code", "SUCCESS");
			result.put("result_content", CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_MAP")+ 
					BigExcelExport.excelDownLoadDatab(list, title, "客户使用快递"+ 
			request.getParameter("balance_month")+ "月异常运单明细_"+ 
							SessionUtils.getEMP(request).getEmployee_number()+ "_"+ 
			new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+ ".xls").getName());
			
		} else {
			result.put("result_code", "FAILURE");
			result.put("result_code", "暂无异常运单！");
			
		}	
		return result;
		
	}
	
	@Override
	public File exportUsedDetailExcel(ExpressBalanceDetialQueryParam queryParam, HttpServletRequest request)
			throws Exception {
		// 结算周期
		Map<String, Object> balance_cycle= DateUtil.getBalanceCycle(request.getParameter("balance_month"), 
				Integer.parseInt(contractBasicinfoMapper.findById(
						queryParam.getCon_id()).getSettle_date().toString()));
		// 结算日期开始
		queryParam.setBalance_start_date(balance_cycle.get("balance_start_date").toString());
		// 结算日期结束
		queryParam.setBalance_end_date(balance_cycle.get("balance_end_date").toString());
		LinkedHashMap<String, String> title = new LinkedHashMap<String, String>();
		title.put("warehouse", "所属仓库");
		title.put("store_name", "所属店铺");
		title.put("epistatic_order", "订单号");
		title.put("delivery_order", "备注");
		title.put("sku_number", "商品编码");
		title.put("length", "长(CM)");
		title.put("width", "宽(CM)");
		title.put("higth", "高(CM)");
		title.put("volumn", "体积(CM³)");
		title.put("transport_time", "发货时间");
		title.put("province", "计费省");
		title.put("city", "计费市");
		title.put("state", "计费区");
		title.put("weight", "发货重量(KG)");
		title.put("transport_name", "物流商名称");
		title.put("itemtype_name", "产品类型");
		title.put("cod_flag", "是否COD");
		title.put("order_amount", "订单金额(元)");
		title.put("express_number", "运单号");
		title.put("order_type", "类型");
		title.put("charged_weight", "计费重量(KG)");
		title.put("insurance_fee", "保价费(元)");
		title.put("first_weight_price", "首重报价(元)");
		title.put("added_weight_price", "续重报价(元)");
		title.put("discount", "折扣");
		title.put("cod", "COD手续费(元)");
		title.put("afterdiscount_freight", "运费(元)");
		title.put("total_fee", "最终费用(元)（不含COD）");
		String name = null;
		if(request.getParameter("express_code").equals("SF")){
			name = request.getParameter("product_type_name");
			
		} else {
			name = request.getParameter("express_name");
			
		}
		return BigExcelExport.excelDownLoadDatab(expressUsedBalancedDataMapper.findDetail(queryParam), title, 
				request.getParameter("client_name")
				+ "使用"
				+ name
				+ request.getParameter("balance_month") 
				+ "月结算明细报表" 
				+ "_"
				+ SessionUtils.getEMP(request).getEmployee_number()
				+ "_"
				+ new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) 
				+ ".xls");
		
	}
	
	@Override
	public List<Map<String, Object>> selectRecordsBySubject(String con_id, String balance_month) {
		return balanceSummaryUsedExMapper.selectRecordsByConId(Integer.parseInt(con_id), null, balance_month);
		
	}
	
	@Override
	public QueryResult<Map<String, Object>> findAllBalanceDetail(ExpressBalanceDetialQueryParam queryParam, 
			HttpServletRequest request)
			throws Exception {
		// 结算周期
		Map<String, Object> balance_cycle= DateUtil.getBalanceCycle(request.getParameter("balance_month"), 
				Integer.parseInt(contractBasicinfoMapper.findById(queryParam.getCon_id()).getSettle_date().toString()));
		// 结算日期开始
		queryParam.setBalance_start_date(balance_cycle.get("balance_start_date").toString());
		// 结算日期结束
		queryParam.setBalance_end_date(balance_cycle.get("balance_end_date").toString());
		QueryResult<Map<String, Object>> qr = new QueryResult<Map<String, Object>>();
		qr.setResultlist(expressUsedBalancedDataMapper.selectAllBalancedData(queryParam));
		qr.setTotalrecord(expressUsedBalancedDataMapper.countAllBalancedData(queryParam));
		return qr;
		
	}
	
	@Override
	public Boolean expressUsedBalanceSummary(ContractBasicinfo cb, String ym) throws Exception {
		// 合同id
		Integer con_id = Integer.parseInt(cb.getId());
		// 快递集合
		List<Map<String, Object>> expresses= carrierSospMapper.findExpress(con_id);
		if(!CommonUtils.checkExistOrNot(expresses)){
			// 合同下不存在使用快递，汇总下一合同
			return false;
			
		}
		// 定义变量
		// 快递
		Map<String, Object> express= null;
		// 汇总集合
		List<Map<String, Object>> summarys= null;
		// 汇总
		Map<String, Object> summary= null;
		// 结算汇总集合
		List<BalanceSummaryUsedEx> bSUEs= null;
		// 结算汇总
		BalanceSummaryUsedEx bSUE= null;
		// 结算开始日期
		String balance_start_date= DateUtil.getBeforeMToString(DateUtil.getYesterDay());
		// 结算结束日期
		String balance_end_date= DateUtil.getCalendarToString(DateUtil.getYesterDay());
		for(int j= 0; j< expresses.size(); j++){
			express= expresses.get(j);
			summarys= expressUsedBalancedDataMapper.getSummary(con_id, express.get("carrier").toString(), 
					balance_start_date, balance_end_date);
			if(CommonUtils.checkExistOrNot(summarys)){
				bSUEs = new ArrayList<BalanceSummaryUsedEx>();
				// 结算明细汇总记录存在
				for(int k= 0; k < summarys.size(); k++){
					summary= summarys.get(k);
					bSUE= new BalanceSummaryUsedEx();
					bSUE.setCon_id(con_id);
					bSUE.setBalance_subject(cb.getContract_owner());
					bSUE.setSubject_type(Integer.parseInt(cb.getContract_type()));
					bSUE.setBalance_month(ym);
					bSUE.setExpress(express.get("carrier_code") + "");
					bSUE.setProduct_type_code(summary.get("product_type_code") + "");
					bSUE.setProduct_type_name(summary.get("product_type_name") + "");
					bSUE.setOrder_num(Integer.parseInt(summary.get("order_num") + ""));
					bSUE.setTotal_freight(new BigDecimal(summary.get("total_freight") + ""));
					bSUE.setTotal_insurance(new BigDecimal(summary.get("total_insurance") + ""));
					bSUE.setTotal_cod(new BigDecimal(summary.get("total_cod") + ""));
					bSUE.setTotal_fee(new BigDecimal(summary.get("total_fee") + ""));
					bSUEs.add(bSUE);
					
				}
				// 批量插入
				balanceSummaryUsedExMapper.insertBatch(bSUEs);
				
			}
			// 无结算明细汇总记录
			
		}
		return true;
		
	}
	
	@Override
	public void expressUsedBalance(ExecutorService pool, Integer maxThreadNum, 
			Integer recommendedSingleThreadProcessingNum) throws Exception {
		// 检测是否存在有效快递合同
		// 合同信息
		List<ContractBasicinfo> cbList= contractBasicinfoMapper.find_by_cb();
		if(CommonUtils.checkExistOrNot(cbList)){
			// 存在
			for(int i= 0; i< cbList.size(); i++) {
				//　按合同为主体结算原始数据
				ContractBasicinfo cb= cbList.get(i);
				// 合同ID
				Integer con_id= Integer.parseInt(cb.getId());
				// 若今天不在合同周期内
				if(DateUtil.isTime(cb.getContract_start(), cb.getContract_end(), 
						DateUtil.formatDate(new Date()))){
					// 判断该合同是否启用打包费
					if(CommonUtils.checkExistOrNot(packageChargeMapper.findByConId(con_id)) && 
							packageChargeMapper.findByConId(con_id).getCarrier_charge() == 0) {
						continue;
						
					}
					// 店铺集合
					List<Store> storeList= null;
					// 使用快递集合
					List<Map<String, Object>> expresses= null;
					// 合同类型
					Integer contract_type= Integer.parseInt(cb.getContract_type());
					if(contract_type == 3){
						// 店铺合同
						// 店铺
						Store store= storeMapper.findByContractOwner(cb.getContract_owner());
						if(CommonUtils.checkExistOrNot(store)) {
							expresses= carrierSospMapper.findExpress(con_id);
							if(CommonUtils.checkExistOrNot(expresses)){
								storeList= new ArrayList<Store>();
								storeList.add(store);
							} else {
								continue;
								
							}
							
						} else {
							BalanceErrorLog bEL= new BalanceErrorLog();
							bEL.setContract_id(con_id);
							bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
							bEL.setPro_result_info("该店铺不存在！");
							balanceErrorLogMapper.addBalanceErrorLog(bEL);
							continue;
							
						}
						
					} else if(contract_type == 4) {
						// 客户合同
						// 客户
						Client client= clientMapper.findByContractOwner(cb.getContract_owner());
						// 临时缓存
						List<Store> tempList= storeMapper.selectByClient(client.getId());
						if(CommonUtils.checkExistOrNot(tempList)){
							expresses= carrierSospMapper.findExpress(con_id);
							if(CommonUtils.checkExistOrNot(expresses)){
								storeList= tempList;
								
							} else {
								continue;
								
							}
							
						} else {
							BalanceErrorLog bEL= new BalanceErrorLog();
							bEL.setContract_id(con_id);
							bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
							bEL.setPro_result_info("合同ID[" + con_id + "]," + client.getClient_name() + 
									"客户没有绑定店铺！");
							balanceErrorLogMapper.addBalanceErrorLog(bEL);
							continue ;
							
						}
						
					}
					// 循环一家店铺或
					// 循环一家客户下的所有店铺
					for(int j= 0; j< storeList.size(); j++) {
						Store store= storeList.get(j);
						for(int k= 0; k< expresses.size(); k++){
							// 使用快递
							Map<String, Object> express= expresses.get(k);
							//　客户使用快递配置
							ExpressContractConfig eCC= expressContractConfigMapper.findECC(con_id, 
									express.get("carrier_code").toString());
							if(eCC.getWeight_method() == 0){
								// 合同失效错误日志新增
								BalanceErrorLog bEL= new BalanceErrorLog();
								bEL.setContract_id(con_id);
								bEL.setPro_result_flag(BaseConst.PRO_RESULT_FLAG_ERROR);
								String str= "";
								if(contract_type == 3){
									str= "店铺";
									
								} else if(contract_type == 4){
									str= "客户";
									
								}
								bEL.setPro_result_info("此" + str + "合同下" + express.get("carrier") + 
										"结算规则配置不完整，计重方式为空！");
								balanceErrorLogMapper.addBalanceErrorLog(bEL);
								
							} else {
								// 初始化批次处理量为最大线程与建议单线程处理量之积
								int batch_num= maxThreadNum* recommendedSingleThreadProcessingNum;
								// 检测是否存在-本次结算主体合同-周期内有效-未结算过-状态正常的原始数据
								// 原始数据集合
								List<ExpressRawData> eRDs= expressRawDataMapper.selectRecords(
										contract_type, store.getCost_center(), 
										store.getStore_name(), express.get("carrier").toString(), 
										cb.getContract_start(), cb.getContract_end(), 
//										"1", 
										0, 
										batch_num,
										"'2','3'"
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
										queue.add(pool.submit(new ExpressUsedBalanceRunnable(
												startPoint, endPoint>=eRDs.size()?eRDs.size():endPoint, 
														eRDs, cb, express, eCC)));
										
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
									eRDs= expressRawDataMapper.selectRecords(
											contract_type, store.getCost_center(), 
											store.getStore_name(), express.get("carrier").toString(), 
											cb.getContract_start(), cb.getContract_end(), 
//											"1", 
											0, 
											batch_num,
											"'2','3'"
											);
								}
							}
						}
					}
				} else {
					// 判断时间不在该生效合同周期内，合同修改为失效
					Map<String, Object> map= new HashMap<String, Object>();
					map.put("validity", 0);
					map.put("update_user", BaseConst.SYSTEM_JOB_CREATE);
					map.put("id", cb.getId());
					contractBasicinfoMapper.update_cb_validity(map);
				}
			}
		}
		// 无有效合同
	}
	
}