package com.bt.lmis.service.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.CarrierFeeFlagMapper;
import com.bt.lmis.model.CarrierFeeFlag;
import com.bt.lmis.service.CarrierFeeFlagService;
import com.bt.utils.SessionUtils;
@Service
public class CarrierFeeFlagServiceImpl<T> extends ServiceSupport<T> implements CarrierFeeFlagService<T> {

	@Autowired
	private CarrierFeeFlagMapper<CarrierFeeFlag> carrierFeeFlagMapper;
	
	public CarrierFeeFlagMapper<CarrierFeeFlag> getCarrierFeeFlagMapper(){
		return carrierFeeFlagMapper;
	}
	
	@Override
	public Integer createCarrierFeeFlag(CarrierFeeFlag carrierFeeFlag) {
		return carrierFeeFlagMapper.insertCarrierFeeFlag(carrierFeeFlag);
	}
	
	@Override
	public CarrierFeeFlag getCarrierFeeFlag(int con_id) {
		return carrierFeeFlagMapper.selectByConId(con_id);
	}
	
	@Override
	public JSONObject saveCarrierFeeFlag(HttpServletRequest request, JSONObject result) {
		result = new JSONObject();
		CarrierFeeFlag carrierFeeFlag = new CarrierFeeFlag();
		carrierFeeFlag.setTotalFreightDiscount_flag(Boolean.parseBoolean(request.getParameter("totalFreightDiscount_flag")));
		carrierFeeFlag.setManagementFee_flag(Boolean.parseBoolean(request.getParameter("managementFee_flag")));
		//　更新
		carrierFeeFlag.setId(Integer.parseInt(request.getParameter("id")));
		carrierFeeFlag.setUpdate_by(SessionUtils.getEMP(request).getEmployee_number());
		carrierFeeFlagMapper.updateCarrierFeeFlag(carrierFeeFlag);
		result.put("result_code", "SUCCESS");
		result.put("result_content", "配置成功！");
		return result;
	}
	
	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
