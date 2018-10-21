package com.bt.lmis.balance.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.balance.service.BalanceService;
import com.bt.lmis.dao.BalanceSummaryExMapper;
import com.bt.lmis.dao.CollectionMasterMapper;
import com.bt.lmis.dao.ContractBasicinfoMapper;
import com.bt.lmis.dao.ExpressContractConfigMapper;
import com.bt.lmis.dao.TotalFreightDiscountMapper;
import com.bt.lmis.dao.WaybillDiscountExMapper;
import com.bt.lmis.model.CollectionDetail;
import com.bt.lmis.model.CollectionMaster;
import com.bt.lmis.model.ContractBasicinfo;
import com.bt.lmis.model.DiffBilldeatils;
import com.bt.lmis.model.ExpressContractConfig;
import com.bt.lmis.thread.ExpressBalanceRunnable2;
import com.bt.lmis.utils.ExpressBalanceUtils;
import com.bt.utils.CommonUtils;
@Service
public class BalanceServiceImpl implements BalanceService {
	@Autowired
	private ExpressContractConfigMapper<T> expressContractConfigMapper;
	@Autowired
	private ContractBasicinfoMapper<T> contractBasicinfoMapper;
	@Autowired
	private BalanceSummaryExMapper<T> balanceSummaryExMapper;
	@Autowired
	private WaybillDiscountExMapper<T> waybillDiscountMapper;
	@Autowired
	private TotalFreightDiscountMapper<T> totalFreightDiscountMapper;
	@Autowired
	private CollectionMasterMapper<T> collectionMasterMapper;
	
	
	@Override
	public List<DiffBilldeatils> rebalance(List<DiffBilldeatils> eRDs, Integer conId) {
		//查询快递合同
		List<Map<String, Object>> eCs = contractBasicinfoMapper.findValidContract(conId);
		Map<String, Object> eC = eCs.get(0);
		ExpressContractConfig eCC= expressContractConfigMapper.findECC(conId, eC.get("contract_owner").toString());
		int recommendedSingleThreadProcessingNum = 3000;
		int threadNum = eRDs.size()/recommendedSingleThreadProcessingNum;
		if(eRDs.size()%recommendedSingleThreadProcessingNum != 0) {
			threadNum++; 
		}
		// 定义线程池
		ExecutorService pool= Executors.newCachedThreadPool();
		// 线程池队列
		Queue<Future<?>> queue=new ArrayBlockingQueue<Future<?>>(threadNum);
		// 多线程调用
		for(int threadNo=0; threadNo<threadNum; threadNo++) {
			// 创建一个Runnable实现类的对象
			int startPoint=threadNo*recommendedSingleThreadProcessingNum;
			int endPoint=startPoint+recommendedSingleThreadProcessingNum;
			queue.add(pool.submit(new ExpressBalanceRunnable2(startPoint, endPoint>=eRDs.size()?eRDs.size():endPoint, eRDs, eC, eCC)));
		}
		// 是否所有任务都完成
		while(true) {
			if(queue.size() != 0) {
				if(queue.peek().isDone()) {
					queue.poll();
				} else {
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} else {
				break;
			}
		}
		// 销毁线程池
		pool.shutdown();
		return eRDs;
	}

	@Override
	public Map<String, Object> getDiscount(Map<String, String> map) {
		int contract_id = map.get("contract_id")!=null?Integer.parseInt(map.get("contract_id")):0;
		ContractBasicinfo contractBasicinfo = contractBasicinfoMapper.findById(contract_id);
		String master_id = map.get("master_id");
		String table_name = map.get("table_name");
		//结果
		Map<String, Object> result = new HashMap<String, Object>();
		if(table_name==null){
			return result;
		}
		// 产品类型集合
		List<String> product_types = null;
		String contract_owner = contractBasicinfo.getContract_owner();
		// 产品类型
		String product_type = null;
		// 
		Map<String, Object> summary = null;
		// 运单折扣启用条件集合
		List<Map<String, Object>> wDs = null;
		// 运单折扣启用条件
		Map<String, Object> wD = null;
		//　折扣率
		BigDecimal discount = null;
		// 总运费折扣率
		BigDecimal tFDP = null;
		// 总费用折扣规则
		List<Map<String, Object>> tFDs = null;
		// 合同配置
		ExpressContractConfig eCC= expressContractConfigMapper.findECC(contract_id, contract_owner);
		// 是否配置运单折扣启用条件（顺丰才有）
		if(eCC.getWaybill_discount()){
			// 查询产品类型
			product_types= balanceSummaryExMapper.selectProductTypeByExpress(table_name,contract_owner,master_id);
			for(int x= 0; x< product_types.size(); x++){
				product_type= product_types.get(x);
				// 查询汇总
				summary= balanceSummaryExMapper.getSummaryGroupbyType(contract_owner, product_type, master_id,table_name);
				if(CommonUtils.checkExistOrNot(summary)&&CommonUtils.checkExistOrNot(summary.get("afterDiscount"))){
					// 获取运单折扣启用条件
					wDs= waybillDiscountMapper.selectAllWD(contract_id, contract_owner, product_type);
					// 当该承运商的指定产品类型运单折扣启用条件存在
					if(CommonUtils.checkExistOrNot(wDs)){
						// 总标准运费是否满足折扣条件
						for(int z= 0; z< wDs.size(); z++){
							wD= wDs.get(z);
							// 当满足条件时
							if(CommonUtils.inRegionOrNot(wD, new BigDecimal(summary.get("afterDiscount").toString()))){
								// 确定单运单折扣率
								discount= new BigDecimal(wD.get("discount").toString()).divide(new BigDecimal(100));
								//
								result.put(product_type, new BigDecimal("1").subtract(discount));
								break;
							}
						}
					}else{
						result.put(product_type, new BigDecimal("1"));
					}
				}
			}
		}
		// 是否配置总费用折扣开关（非顺丰才有）
		if(eCC.getTotal_freight_discount()){
			// 查询汇总
			summary= balanceSummaryExMapper.getSummaryGroupbyType(contract_owner,null,master_id,table_name);
			if(CommonUtils.checkExistOrNot(summary)){
				// 获取总费用折扣规则
				tFDs= totalFreightDiscountMapper.selectAllTFD(contract_id, contract_owner, null);
				tFDP= ExpressBalanceUtils.getTotalFreightDiscount(summary, tFDs);
				if(CommonUtils.checkExistOrNot(tFDP)){
					result.put("ALL", new BigDecimal("1").subtract(tFDP));
				}
			}
		}
		return result;
	}

	@Override
	public List<CollectionMaster> getSummary(Map<String, String> param) {
		String table_name = param.get("table_name");
		if(table_name==null){
			return null;
		}
		int contract_id = param.get("contract_id")!=null?Integer.parseInt(param.get("contract_id")):0;
		ContractBasicinfo contractBasicinfo = contractBasicinfoMapper.findById(contract_id);
		String contract_owner = contractBasicinfo.getContract_owner();
		String account_id = param.get("account_id");
		// 查询汇总
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("contract_owner", contract_owner);
		map.put("account_id", account_id);
		map.put("table_name", table_name);
		List<Map<String,Object>> masterMap = collectionMasterMapper.getMaster(map);
		List<CollectionMaster> masterList = new ArrayList<>();
		for(Map<String,Object> map1:masterMap){
			CollectionMaster collectionMaster = new CollectionMaster();
			String cost_center = map1.get("cost_center")!=null?map1.get("cost_center").toString():null;
			collectionMaster.setCost_center(cost_center);
			String store_code = map1.get("store_code")!=null?map1.get("store_code").toString():null;
			collectionMaster.setStore_code(store_code);
			String warehouse_code = map1.get("warehouse_code")!=null?map1.get("warehouse_code").toString():null;
			collectionMaster.setWarehouse_code(warehouse_code);
			BigDecimal insurance_fee = map1.get("insurance_fee")!=null?(BigDecimal)map1.get("insurance_fee"):null;
			collectionMaster.setInsurance_fee(insurance_fee);
			BigDecimal service_fee = map1.get("service_fee")!=null?(BigDecimal)map1.get("service_fee"):null;
			collectionMaster.setService_fee(service_fee);
			BigDecimal total = map1.get("total")!=null?(BigDecimal)map1.get("total"):null;
			collectionMaster.setTotal(total);
			
			map.put("cost_center", cost_center);
			map.put("store_code", store_code);
			map.put("warehouse_code", warehouse_code);
			List<Map<String,Object>> detailMap = new ArrayList<>();
			if("SF".equals(contract_owner)){
				detailMap = collectionMasterMapper.getDetailSF(map);
			}else{
				detailMap = collectionMasterMapper.getDetail(map);
			}
			List<CollectionDetail> detailList = new ArrayList<>();
			for(Map<String,Object> map2:detailMap){
				CollectionDetail collectionDetail = new CollectionDetail();
				collectionDetail.setProducttype_code(map2.get("producttype_code")!=null?map2.get("producttype_code").toString():null);
				collectionDetail.setSum(Integer.parseInt(map2.get("sum")!=null?map2.get("sum").toString():"0"));
				collectionDetail.setFavourable_price(map2.get("favourable_price")!=null?(BigDecimal)map2.get("favourable_price"):null);
				collectionDetail.setFavourable_price_total(map2.get("favourable_price_total")!=null?(BigDecimal)map2.get("favourable_price_total"):null);
				collectionDetail.setFreight(map2.get("freight")!=null?(BigDecimal)map2.get("freight"):null);
				detailList.add(collectionDetail);
			}
			collectionMaster.setDetails(detailList);
			masterList.add(collectionMaster);
		}
		return masterList;
	} 

}
