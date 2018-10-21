package com.bt.lmis.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.BaseService;
import com.bt.lmis.model.CarrierFeeFlag;

/**
 * @Title:CarrierFeeFlagService
 * @Description: TODO(快递费用标志Service) 
 * @author Ian.Huang 
 * @date 2016年8月12日下午4:40:25
 */
@Service
public interface CarrierFeeFlagService<T> extends BaseService<T> {
	
	/**
	 * 
	 * @Description: TODO(按合同ID获取承运商费用标志)
	 * @param con_id
	 * @return
	 * @return: CarrierFeeFlag  
	 * @author Ian.Huang 
	 * @date 2016年8月12日下午5:22:06
	 */
	public CarrierFeeFlag getCarrierFeeFlag(int con_id);
	
	/**
	 * 
	 * @Description: TODO(保存承运商费用规则标记)
	 * @param request
	 * @param result
	 * @return
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2016年8月16日上午10:50:38
	 */
	public JSONObject saveCarrierFeeFlag(HttpServletRequest request, JSONObject result);
	
	/**
	 * 
	 * @Description: TODO(创建承运商费用规则标记记录)
	 * @param carrierFeeFlag
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年8月16日上午11:15:23
	 */
	public Integer createCarrierFeeFlag(CarrierFeeFlag carrierFeeFlag);
	
}
