package com.bt.lmis.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.BaseService;
import com.bt.lmis.model.BalanceErrorLog;
import com.bt.lmis.model.ExpressBalancedData;
import com.bt.lmis.model.JibanpaoConditionEx;

/**
 * 
* @ClassName: TransSettleService 
* @Description: TODO(物流结算) 
* @author likun
* @date 2016年7月21日 上午10:14:52 
* 
* @param <T>
 */
@Service
public interface TransSettleService<T> extends BaseService<T> {
	//找到需要结算的合同主信息
	public ArrayList<Object>carriageContractToSettle(HashMap<String,String>param) throws RuntimeException;
	//找到对应合同下的所有需要结算的明细数据并结算每条明细数据
	public HashMap<String,String>carriageSettleByContract(HashMap<String,String>param) throws RuntimeException;
	//承运商费用汇总
	public HashMap<String,String> transPool(HashMap<String,String>param);
	//客户费用汇总
	public HashMap<String,String> customerPool(HashMap<String,String>param);
	//店铺费用汇总
	public HashMap<String,String> storePool(HashMap<String,String>param);
}
