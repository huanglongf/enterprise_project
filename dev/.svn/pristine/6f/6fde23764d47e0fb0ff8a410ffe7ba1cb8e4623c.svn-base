package com.bt.lmis.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.dao.TransportProductTypeMapper;
import com.bt.lmis.dao.TransportVendorMapper;
import com.bt.lmis.model.TransportProductType;
import com.bt.lmis.page.QueryParameter;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.TransportProductTypeService;
import com.bt.utils.CommonUtils;
import com.bt.utils.SessionUtils;
@Service
public class TransportProductTypeServiceImpl<T> implements TransportProductTypeService<T> {

	@Autowired
	private TransportVendorMapper<T> transportVendorMapper;
	public TransportVendorMapper<T> getTransportVendorMapper() {
		return transportVendorMapper;
		
	}
	
	@Autowired
    private TransportProductTypeMapper<T> transportProductTypeMapper;
	public TransportProductTypeMapper<T> getTransportProductTypeMapper() {
		return transportProductTypeMapper;
		
	}
	
	@Override
	public List<TransportProductType> getProductTypeByTranportVendor(String vendor_code) {
		return transportProductTypeMapper.findByTransportVendor(true, vendor_code);
		
	}
	
	@Override
	public JSONObject getProductTypeByTranportVendor(HttpServletRequest request, JSONObject result) throws Exception {
		result= new JSONObject();
		result.put("product_type", transportProductTypeMapper.findByTransportVendor(true, request.getParameter("vendor_code")));
		return result;
	}
	
	@Override
	public JSONObject save(HttpServletRequest request, TransportProductType transportProductType, JSONObject result) throws Exception {
		result= new JSONObject();
		//
		TransportProductType temp= transportProductTypeMapper.findByCode(null, transportProductType.getProduct_type_code());
		if(CommonUtils.checkExistOrNot(transportProductType.getId())) {
			// 产品类型ID存在则更新
			if(CommonUtils.checkExistOrNot(temp) && (!temp.getId().equals(transportProductType.getId()))){
				// 已存在此客户编码
				result.put("result_code", "FAILURE");
				result.put("result_content", "产品类型编码已存在！");
				
			} else {
				if(!transportProductType.getStatus() && !temp.getStatus().equals(transportProductType.getStatus()) && (transportVendorMapper.judgeTransportVendorContractExistOrNotByCode(transportProductType.getVendor_code()) != 0)) {
					// 失效操作且为有效性变更
					// 产品类型对应物流商下存在合同
					result.put("result_code", "FAILURE");
					result.put("result_content", "物流商下已存在合同，无法停用产品类型！");
					
				} else {
					transportProductType.setUpdate_by(SessionUtils.getEMP(request).getEmployee_number());
					transportProductTypeMapper.updateTransportProductType(transportProductType);
					result.put("result_code", "SUCCESS");
					result.put("result_content", "更新产品类型信息成功！");
					
				}
				
			}
			
		} else {
			// 产品类型ID不存在则新增
			if(CommonUtils.checkExistOrNot(temp)){
				// 已存在此客户编码
				result.put("result_code", "FAILURE");
				result.put("result_content", "产品类型编码已存在！");
				
			} else {
				transportProductType.setCreate_by(SessionUtils.getEMP(request).getEmployee_number());
				transportProductTypeMapper.addTransportProductType(transportProductType);
				result.put("result_code", "SUCCESS");
				result.put("result_content", "新增产品类型信息成功！");
				
			}
			
		}
		return result;
		
	}
	
	@Override
	public JSONObject getProductType(HttpServletRequest request, JSONObject result) throws Exception {
		result= new JSONObject();
		result.put("product_type", transportProductTypeMapper.selectById(Integer.parseInt(request.getParameter("product_type_id"))));
		return result;
	}
	
	@Override
	public JSONObject deleteProductTypes(HttpServletRequest request, JSONObject result) throws Exception {
		result= new JSONObject();
		transportProductTypeMapper.delTransportProductTypes(CommonUtils.strToIntegerArray(request.getParameter("privIds")));
		result.put("result_code", "SUCCESS");
		result.put("result_content", "删除成功！");
		return result;
		
	}
	
	@Override
	public QueryResult<Map<String,Object>> query(QueryParameter queryParam) {
		QueryResult<Map<String,Object>> qr= new QueryResult<Map<String,Object>>();
		qr.setResultlist(transportProductTypeMapper.query(queryParam));
		qr.setTotalrecord(transportProductTypeMapper.count(queryParam));
		return qr;
		
	}

	@Override
	public TransportProductType selectByNameAndExpressCode(String productTypeName, String transportCode) {
		// TODO Auto-generated method stub
		return transportProductTypeMapper.selectByNameAndExpressCode(productTypeName,transportCode);
	}

	@Override
	public TransportProductType findByCode(String productTypeCode) {
		// TODO Auto-generated method stub
		return transportProductTypeMapper.findByCode(null,productTypeCode);
	}

}
