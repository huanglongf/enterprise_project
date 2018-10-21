package com.bt.lmis.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.TransportProductTypeMapper;
import com.bt.lmis.dao.TransportVendorMapper;
import com.bt.lmis.model.TransportVendor;
import com.bt.lmis.page.QueryParameter;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.TransportVendorService;
import com.bt.utils.CommonUtils;
import com.bt.utils.SessionUtils;

/**
 * @Title:TransportVendorServiceImpl
 * @Description: TODO  
 * @author Ian.Huang 
 * @date 2016年12月12日下午5:32:55
 */
@Service
//@Transactional
public class TransportVendorServiceImpl<T> extends ServiceSupport<T> implements TransportVendorService<T> {

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
	public List<Map<String, Object>> getAllExpress() {
		return transportVendorMapper.findExpress();
	}
	
	@Override
	public JSONObject save(HttpServletRequest request, TransportVendor transportVendor, JSONObject result) throws Exception {
		result= new JSONObject();
		//
		TransportVendor temp= transportVendorMapper.findByCode(null, transportVendor.getTransport_code());
		if(CommonUtils.checkExistOrNot(transportVendor.getId())){
			// 物流商ID存在则更新
			if(CommonUtils.checkExistOrNot(temp) && (!temp.getId().equals(transportVendor.getId()))){
				// 已存在此物流商编码
				result.put("result_code", "FAILURE");
				result.put("result_content", "物流商编码已存在！");
				
			} else {
				if(!transportVendor.getValidity() && !temp.getValidity().equals(transportVendor.getValidity()) && (transportVendorMapper.judgeTransportVendorContractExistOrNot(transportVendor.getId()) != 0)) {
					// 失效操作且为有效性变更
					// 物流商下存在合同
					result.put("result_code", "FAILURE");
					result.put("result_content", "物流商下已存在合同，无法失效！");
					
				} else {
					transportVendor.setUpdate_user(SessionUtils.getEMP(request).getEmployee_number());
					transportVendorMapper.updateTransportVendor(transportVendor);
					result.put("result_code", "SUCCESS");
					result.put("result_content", "更新物流商信息成功！");
				
				}
				
			}
			
		} else {
			// 不存在则新增
			if(CommonUtils.checkExistOrNot(temp)){
				// 已存在此客户编码
				result.put("result_code", "FAILURE");
				result.put("result_content", "物流商编码已存在！");
				
			} else {
				transportVendor.setCreate_user(SessionUtils.getEMP(request).getEmployee_number());
				transportVendorMapper.addTransportVendor(transportVendor);
				result.put("result_code", "SUCCESS");
				result.put("result_content", "新增物流商信息成功！");
				
			}
			
		}
		return result;
		
	}
	
	@Override
	public TransportVendor getById(int id) throws Exception {
		return transportVendorMapper.getById(id);
		
	}
	
	@Override
	public JSONObject deleteTransportVendors(HttpServletRequest req, JSONObject result) throws Exception {
		result= new JSONObject();
		// 物流商ID
		Integer[] ids= CommonUtils.strToIntegerArray(req.getParameter("privIds"));
		result.put("total", ids.length);
		List<Integer> deletable= new ArrayList<Integer>();
		for(int i= 0; i< ids.length; i++){
			if(transportVendorMapper.judgeTransportVendorContractExistOrNot(ids[i]) != 0){
				// 物流商存在合同则不能删除
				continue;
				
			}
			deletable.add(ids[i]);
			
		}
		if(CommonUtils.checkExistOrNot(deletable)){
			// 批量删除对应产品类型
			transportProductTypeMapper.delByTransportVendor(deletable);
			// 批量删除物流商
			transportVendorMapper.delTransportVendors(deletable);
			
		}
		result.put("success", deletable.size());
		return result;
		
	}
	
	@Override
	public QueryResult<T> query(QueryParameter qr) {
		QueryResult<T> queryResult= new QueryResult<T>();
		queryResult.setResultlist(transportVendorMapper.query(qr));
		queryResult.setTotalrecord(transportVendorMapper.count(qr));
		return queryResult;
		
	}
	
	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return null;
		
	}

	@Override
	public TransportVendor selectByName(String expressName) {
		// TODO Auto-generated method stub
		return transportVendorMapper.selectByName(expressName);
	}

	@Override
	public TransportVendor findByCode(String productTypeCode) {
		// TODO Auto-generated method stub
		return transportVendorMapper.findByCode(null,productTypeCode);
	}

}
