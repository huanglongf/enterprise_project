package com.bt.lmis.thread;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.transaction.annotation.Transactional;

import com.bt.lmis.base.spring.SpringUtils;
import com.bt.lmis.dao.TransportProductTypeMapper;
import com.bt.lmis.model.DiffBilldeatils;
import com.bt.lmis.model.ExpressContractConfig;
import com.bt.lmis.model.JibanpaoConditionEx;
import com.bt.lmis.model.NextPriceInternal;
import com.bt.lmis.model.OriginationDestinationParam;
import com.bt.lmis.model.PricingFormula;
import com.bt.lmis.utils.ExpressBalanceUtils;
import com.bt.utils.CommonUtils;
import com.bt.utils.Constant;
import com.bt.utils.IntervalValidationUtil;

/**
 * @Title:ExpressBalanceRunnable
 * @Description: TODO  
 * @author Ian.Huang 
 * @date 2016年11月27日下午1:12:01
 */
@SuppressWarnings("unchecked")
@Transactional
public class ExpressBalanceRunnable2 implements Runnable {

	// 定义日志
	private static final Logger logger= Logger.getLogger(ExpressBalanceRunnable2.class);
//	@Autowired
//	private TransportProductTypeMapper<T> transportProductTypeMapper;
	private TransportProductTypeMapper<T> transportProductTypeMapper= (TransportProductTypeMapper<T>)SpringUtils.getBean(TransportProductTypeMapper.class);
	// 起始节点
	private int startPoint;
	// 结束节点
	private int endPoint;
	// 原始数据
	private List<DiffBilldeatils> eRDs;
	// 快递合同
	private Map<String, Object> eC;
	// 合同配置
	private ExpressContractConfig eCC;
	
	// 无参构造
	public ExpressBalanceRunnable2(){
		
	}
	// 有参构造
	public ExpressBalanceRunnable2(int startPoint, int endPoint, List<DiffBilldeatils> eRDs, Map<String, Object> eC, ExpressContractConfig eCC) {
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.eRDs = eRDs;
		this.eC = eC;
		this.eCC = eCC;
	}
	
	@Override
	public void run() {
		// 合同ID
		int con_id= Integer.parseInt(eC.get("id").toString());
		// 快递编码
		String transport_code= eC.get("contract_owner").toString();
		// 快递名称
		String transport_name= eC.get("transport_name").toString();
		// 开始结算
		try {
			// 存在
			for(int i=startPoint; i<endPoint; i++){
				// 定义变量
				// 始发地目的地参数
				OriginationDestinationParam oDP= null;
				// 计费重量
				BigDecimal pricingWeight= null;
				// 首重
				BigDecimal firstWeight= null;
				// 续重
				BigDecimal addedWeight= new BigDecimal(0);
				// 续重价格
				BigDecimal addedWeightPrice= null;
				// 标准运费和折后运费
				Map<String, Object> freight= null;
				// 参数对象
				Map<String, Object> param= null;
				// 结算材料1：原始数据
				DiffBilldeatils eRD=eRDs.get(i);
				if(!CommonUtils.checkExistOrNot(eRD.getDest_province())){
					// 目的地省不存在
					eRD.setSettleErrorReason("此运单中目的地省不存在！");
					eRD.setSettleFlag(2);
					continue;
				}
				String vendor_code = eRD.getExpress_code();
				String itemtype_code = eRD.getProducttype_code();
				Map<String,String> par = new HashMap<>();
				par.put("vendor_code", vendor_code);
				par.put("itemtype_code", itemtype_code);
				Map<String,Object> map = transportProductTypeMapper.getItemName(par);
				String itemtype_name = map.get("product_type_name")!=null?map.get("product_type_name").toString():null;
				// 结算材料2：始发地目的地参数
				// key: mysql-key/redis-key
				String key= con_id + transport_name;
				if(CommonUtils.checkExistOrNot(itemtype_name)){
					key+= itemtype_name;
				}
				// 报价必到省
				key+=eRD.getOrigin_province()+"中国"+eRD.getDest_province();
				for(int j=0; j<3; j++){
					String temp=key;
					if((j != 2) && CommonUtils.checkExistOrNot(eRD.getDest_city())){
						// 没有区则省市
						temp+=eRD.getDest_city();
						if((j == 0) && CommonUtils.checkExistOrNot(eRD.getDest_state())){
							// 先查省市区
							temp+=eRD.getDest_state();
						}
					}
					//报价
					oDP= (OriginationDestinationParam)ExpressBalanceUtils.getDataRecourse(temp, OriginationDestinationParam.class, Constant.PERFIX_MAPPER_ODP);
					if(CommonUtils.checkExistOrNot(oDP)){
						break;
					} else {
						// 无记录
						continue;
					}
				}
				// 始发地目的地参数存在
				if(!CommonUtils.checkExistOrNot(oDP)) {
					// 始发地目的地参数不存在
					eRD.setSettleErrorReason("原始运单记录对应始发地目的地参数不存在！");
					eRD.setSettleFlag(2);
					continue;
				}
				if(oDP.getSzxz_switch() == 0) {
					// 首重续重开关为关闭状态，参数未维护
					eRD.setSettleErrorReason("首重续重开关为关闭状态！");
					eRD.setSettleFlag(2);
					continue;
				}
				if((eCC.getWeight_method() != 1) && !CommonUtils.checkExistOrNot(oDP.getSzxz_jpnum())) {
					// 计重方式不为实际重量且计抛基数不存在
					eRD.setSettleErrorReason("需计算抛重，计抛基数不存在！");
					eRD.setSettleFlag(2);
					continue;
				}
				// 获取计费公式
				PricingFormula pF= (PricingFormula)ExpressBalanceUtils.getDataRecourse("PF" + con_id + transport_code + oDP.getFormula(), PricingFormula.class, Constant.PERFIX_MAPPER_PF);
				if(!CommonUtils.checkExistOrNot(pF)) {
					// 未维护对应公式
					eRD.setSettleErrorReason("始发地目的地参数中使用的公式未在合同中维护！");
					eRD.setSettleFlag(2);
					continue;
				}
				// 参数
				param= new HashMap<String, Object>();
				param.put("eRD", eRD);
				param.put("oDP", oDP);
				param.put("eCC", eCC);
				if(eCC.getWeight_method() == 3){
					param.put("jCExs", ExpressBalanceUtils.getDataRecourse("JBPC" + con_id + transport_code, JibanpaoConditionEx.class, Constant.PERFIX_MAPPER_JBPC));
				}
				// 获取所有所需重量
				Map<String, Object> allWeight= ExpressBalanceUtils.getAllWeight2(param);
				// 计费重量
				pricingWeight= new BigDecimal(allWeight.get("charged_weight").toString());
				// 运费结算
				// 运费结算入参
				param= new HashMap<String, Object>();
				// 始发地目的地使用公式
				param.put("formula", oDP.getFormula());
				// 已维护对应公式
				if(CommonUtils.checkExistOrNot(pF.getAccurate_decimal_place())){
					// 使用公式参数
					param.put("accurate_decimal_place", pF.getAccurate_decimal_place());
				}
				// 首重
				firstWeight= oDP.getSzxz_sz();
				// 首重
				param.put("firstWeight", firstWeight);
				// 首重价格
				param.put("firstWeightPrice", oDP.getSzxz_price());
				// 计费重量
				param.put("pricingWeight", pricingWeight);
				// 续重区间价格集合
				List<NextPriceInternal> nextWeightInternals= (List<NextPriceInternal>)ExpressBalanceUtils.getDataRecourse("NPI" + oDP.getSzxz_id(), NextPriceInternal.class, Constant.PERFIX_MAPPER_NPI);
				// 取续重报价，由于暂时业务中只存在一条续重报价情况，故直接初始化为第一条记录
				addedWeightPrice= nextWeightInternals.get(0).getPrice();
				// 控制标志位
				boolean flag= false;
				if(pricingWeight.compareTo(firstWeight) == 1) {
					// 续重 = 计费重量 - 首重
					addedWeight= pricingWeight.subtract(firstWeight);
					for(int m= 0; m< nextWeightInternals.size(); m++){
						if(IntervalValidationUtil.isExist(Float.parseFloat(addedWeight + ""), nextWeightInternals.get(m).getInternal())){
							// 找到续重存在区间
							if(m != 0){
								// 第一条数据已赋值，故除第一条记录外继续赋值
								addedWeightPrice= nextWeightInternals.get(m).getPrice();
							}
							// 获取续重报价，打标记
							flag= true;
							break;
						}
					}
					if(flag){
						param.put("addedWeightPrice", addedWeightPrice);
						// 重置标志位
						flag= false;
					} else {
						// 未找到相应区间内的续重价格
						eRD.setSettleErrorReason("该运单及其对应合同规则所得续重不在任何维护的续重区间中！");
						eRD.setSettleFlag(2);
						continue;
					}
				}
				// 单运费折扣
				param.put("discount", oDP.getSzxz_discount());
				// 得出标准运费/折后运费
				freight= ExpressBalanceUtils.formula(param);
				eRD.setProducttype_code1(eRD.getProducttype_code());
				eRD.setInsurance_fee1(eRD.getInsurance_fee());
				eRD.setJpNum(oDP.getSzxz_jpnum());
				eRD.setVolumnWeight(new BigDecimal(allWeight.get("volumn_weight").toString()));
				eRD.setVolumnAccountWeight(new BigDecimal(allWeight.get("volumn_account_weight").toString()));
				eRD.setJfWeight(oDP.getJf_weight());
				eRD.setCharged_weight1(pricingWeight);
				eRD.setFirstWeight(firstWeight);
				eRD.setFirstWeightPrice(oDP.getSzxz_price());
				eRD.setAddedWeight(addedWeight);
				eRD.setAddedWeightPrice(addedWeightPrice);
				eRD.setStandard_freight(new BigDecimal(freight.get("standard_freight").toString()));
				if(!transport_code.equals("SF")){
					eRD.setDiscount(oDP.getSzxz_discount());
					eRD.setAfterdiscount_freight(new BigDecimal(freight.get("balanced_freight").toString()));
				} else {
					eRD.setDiscount(new BigDecimal(0));
					eRD.setAfterdiscount_freight(new BigDecimal(freight.get("standard_freight").toString()));
				}
				eRD.setSettleFlag(1);
				eRD.setLast_fee((eRD.getOther_value_added_service_charges()==null?new BigDecimal("0.00"):eRD.getOther_value_added_service_charges()).add(eRD.getInsurance()==null?new BigDecimal("0.00"):eRD.getInsurance()).add(eRD.getStandard_freight()));
			}
		} catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
	}
	
}