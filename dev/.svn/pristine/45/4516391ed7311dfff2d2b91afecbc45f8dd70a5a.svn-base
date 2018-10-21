package com.bt.lmis.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.CarrierSospMapper;
import com.bt.lmis.dao.ExpressContractConfigMapper;
import com.bt.lmis.dao.InsuranceECMapper;
import com.bt.lmis.dao.JbpcExMapper;
import com.bt.lmis.dao.PricingFormulaMapper;
import com.bt.lmis.dao.SpecialServiceExMapper;
import com.bt.lmis.dao.TransportProductTypeMapper;
import com.bt.lmis.dao.TransportVendorMapper;
import com.bt.lmis.model.CarrierSosp;
import com.bt.lmis.service.FreightSospService;
import com.bt.utils.SessionUtils;

/**
 * @Title:FreightSospServiceImpl
 * @Description: TODO(sosp运费)
 * @author Ian.Huang 
 * @date 2016年7月6日下午3:51:17
 */
@Service
public class FreightSospServiceImpl<T> extends ServiceSupport<T> implements FreightSospService<T>{
	
	@Autowired
	private SpecialServiceExMapper<T> specialServiceExMapper;
	public SpecialServiceExMapper<T> getSpecialServiceExMapper(){
		return specialServiceExMapper;
	}
	@Autowired
	private InsuranceECMapper<T> insuranceECMapper;
	public InsuranceECMapper<T> getInsuranceECMapper(){
		return insuranceECMapper;
	}
	@Autowired
	private PricingFormulaMapper<T> pFExMapper;
	public PricingFormulaMapper<T> getPFExMapper(){
		return pFExMapper;
	}
	@Autowired
	private JbpcExMapper<T> jbpcExMapper;
	public JbpcExMapper<T> getJbpcExMapper(){
		return jbpcExMapper;
	}
	@Autowired
	private TransportProductTypeMapper<T> transportProductTypeMapper;
	public TransportProductTypeMapper<T> getTransportProductTypeMapper(){
		return transportProductTypeMapper;
	}
	@Autowired
	private ExpressContractConfigMapper<T> expressContractConfigMapper;
	public ExpressContractConfigMapper<T> getExpressContractConfigMapper(){
		return expressContractConfigMapper;
	}
	@Autowired
	private CarrierSospMapper<T> carrierSospMapper;
	public CarrierSospMapper<T> getCarrierSospMapper(){
		return carrierSospMapper;
	}
	@Autowired
	private TransportVendorMapper<T> transportVendorMapper;
	public TransportVendorMapper<T> getTransportVendorMapper(){
		return transportVendorMapper;
	}
	
	@Override
	public JSONObject loadConfigure(HttpServletRequest request, JSONObject result) {
		result = new JSONObject();
		// 配置
		result.put("config", expressContractConfigMapper.findECC(
				Integer.parseInt(request.getParameter("con_id").toString()), 
				request.getParameter("carrier_code").toString()));
		// 产品类型
		result.put("productType", transportProductTypeMapper.findByTransportVendor(true, request.getParameter("carrier_code").toString()));
		return result;
		
	}
	
	@Override
	public List<Map<String, Object>> loadCarrier(int con_id) {
		List<Map<String, Object>> carrierList = carrierSospMapper.findCarriers(con_id);
		Map<String, Object> ca = null;
		for(int i =0;i<carrierList.size();i++){
			ca = carrierList.get(i);
			if(Integer.parseInt(ca.get("carrier_type").toString()) == 0){
				carrierList.get(i).put("carrier_type_name", "物流");
			}
			if(Integer.parseInt(ca.get("carrier_type").toString()) == 1){
				carrierList.get(i).put("carrier_type_name", "快递");
			}
		}
		return carrierList;
	}

	@Override
	public JSONObject delCarrier(HttpServletRequest request, JSONObject result) {
		result = new JSONObject();
		Integer id = Integer.parseInt(request.getParameter("id").toString());
		// 获取需要被删除承运商
		CarrierSosp cS = carrierSospMapper.getById(id);
		Integer con_id = cS.getCon_id();
		String belong_to = cS.getCarrier();
		if(carrierSospMapper.delCarrier(id) != 0){
			// 删除其配置
			expressContractConfigMapper.delConfig(con_id, belong_to);
			// 删除计半抛条件
			jbpcExMapper.delJBPC(con_id, belong_to);
			// 删除计费公式
			pFExMapper.deletePF(con_id, belong_to);
			// 删除保价费规则
			insuranceECMapper.deleteIEC(con_id, belong_to);
			// 删除特殊服务费
			specialServiceExMapper.delSSE(con_id, belong_to);
			// 
			result.put("result_code", "SUCCESS");
			result.put("result_content", "删除成功！");
			List<Map<String, Object>> carrierList = carrierSospMapper.findCarriers(Integer.parseInt(request.getParameter("con_id").toString()));
			if(carrierList.size() != 0) {
				Map<String, Object> ca = null;
				for(int i =0;i<carrierList.size();i++){
					ca = carrierList.get(i);
					if(Integer.parseInt(ca.get("carrier_type").toString()) == 0){
						carrierList.get(i).put("carrier_type_name", "物流");
					}
					if(Integer.parseInt(ca.get("carrier_type").toString()) == 1){
						carrierList.get(i).put("carrier_type_name", "快递");
					}
				}
				result.put("carrierList", carrierList);
				result.put("result_null_ca", "false");
			} else {
				result.put("result_null_ca", "true");
			}
			
		} else {
			result.put("result_code", "FAILURE");
			result.put("result_content", "删除失败,失败原因:该条记录不存在！");	
		}
		return result;
	}
	
	@Override
	public JSONObject addCarrier(HttpServletRequest request, JSONObject result) {
		result = new JSONObject();
		int con_id = Integer.parseInt(request.getParameter("con_id").toString());
		int carrier_type = Integer.parseInt(request.getParameter("carrier_type").toString());
		String carrier = request.getParameter("carrier");
		if(carrierSospMapper.checkExist(con_id, carrier)!=0){
			result.put("result_code", "FAILURE");
			result.put("result_content", "新增承运商失败！失败原因：承运商已存在");
		} else {
			CarrierSosp cS = new CarrierSosp();
			cS.setCon_id(con_id);
			if(carrier_type == 2){
				carrier_type = 0;
			}
			cS.setCarrier_type(carrier_type);
			cS.setCarrier(carrier);
			cS.setCreate_by(SessionUtils.getEMP(request).getUsername());
			carrierSospMapper.addCarrier(cS);
			if(cS.getCarrier_type()==1){
				expressContractConfigMapper.initializeExpressContractConfig(con_id, carrier, SessionUtils.getEMP(request).getUsername());
			}
			List<Map<String, Object>> carrierList = carrierSospMapper.findCarriers(con_id);
			if(carrierList.size()!=0){
				Map<String, Object> ca = null;
				for(int i =0;i<carrierList.size();i++){
					ca = carrierList.get(i);
					if(Integer.parseInt(ca.get("carrier_type").toString()) == 0){
						carrierList.get(i).put("carrier_type_name", "物流");
					}
					if(Integer.parseInt(ca.get("carrier_type").toString()) == 1){
						carrierList.get(i).put("carrier_type_name", "快递");
					}
				}
				result.put("carrierList", carrierList);
				result.put("result_code", "SUCCESS");
				result.put("result_content", "新增承运商成功！");
			} else {
				result.put("result_code", "FAILURE");
				result.put("result_content", "查询承运商失败,未能获取新增承运商及其他！");
			}
		}
		return result;
	}
	
	@Override
	public JSONObject searchCarrier(HttpServletRequest request, JSONObject result) {
		result = new JSONObject();
		if(request.getParameter("carrier_type").equals("1")){
			result.put("carrierList", transportVendorMapper.findExpress());
		} else if(request.getParameter("carrier_type").equals("2")){
			result.put("carrierList", transportVendorMapper.findPhysicaldistribution());
		}
		return result;
	}
	
	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
