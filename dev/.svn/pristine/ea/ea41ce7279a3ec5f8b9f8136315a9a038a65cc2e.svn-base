package com.bt.lmis.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.StoreMapper;
import com.bt.lmis.model.Store;
import com.bt.lmis.page.QueryParameter;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.StoreService;
import com.bt.utils.CommonUtils;
import com.bt.utils.SessionUtils;
@Service
public class StoreServiceImpl<T> extends ServiceSupport<T> implements StoreService<T> {

	@Autowired
    private StoreMapper<T> storeMapper;
	public StoreMapper<T> getStoreMapper() {
		return storeMapper;
		
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return storeMapper.selectCount(param);
		
	}

	@Override
	public List<Map<String, Object>> findAll() {
		return storeMapper.findAll();
		
	}

	@Override
	public Store selectById(int id) {
		return storeMapper.selectById(id);
		
	}

	@Override
	public List<Store> selectByClient(Integer client_id) {
		return storeMapper.selectByClient(client_id);
		
	}

	@Override
	public Store findByContractOwner(String contract_owner) {
		return storeMapper.findByContractOwner(contract_owner);
		
	}
	
	@Override
	public JSONObject save(HttpServletRequest request, Store store, JSONObject result) throws Exception {
		result= new JSONObject();
		//
		Store temp= storeMapper.findByContractOwner(store.getStore_code());
		if(CommonUtils.checkExistOrNot(store.getId())) {
			// 店铺ID存在则更新
			if(CommonUtils.checkExistOrNot(temp) && (!temp.getId().equals(store.getId()))){
				// 已存在此客户编码
				result.put("result_code", "FAILURE");
				result.put("result_content", "店铺编码已存在！");
				
			} else {
				if(!store.getValidity() && !temp.getValidity().equals(store.getValidity()) && (storeMapper.judgeStoreContractExistOrNot(store.getId()) != 0)) {
					// 失效操作且为有效性变更
					// 店铺下存在合同
					result.put("result_code", "FAILURE");
					result.put("result_content", "店铺下已存在合同，无法失效！");	
					
				} else {
					store.setUpdate_user(SessionUtils.getEMP(request).getEmployee_number());
					storeMapper.updateStore(store);
					result.put("result_code", "SUCCESS");
					result.put("result_content", "更新店铺信息成功！");
					
				}
				
			}
			
		} else {
			// 店铺ID不存在则新增
			if(CommonUtils.checkExistOrNot(temp)){
				// 已存在此客户编码
				result.put("result_code", "FAILURE");
				result.put("result_content", "店铺编码已存在！");
				
			} else {
				store.setCreate_user(SessionUtils.getEMP(request).getEmployee_number());
				storeMapper.addStore(store);
				result.put("result_code", "SUCCESS");
				result.put("result_content", "新增店鋪信息成功！");
				
			}
			
		}
		return result;
		
	}
	
	@Override
	public JSONObject getStore(HttpServletRequest request, JSONObject result) throws Exception {
		result= new JSONObject();
		result.put("store", storeMapper.selectById(Integer.parseInt(request.getParameter("store_id"))));
		return result;
		
	}
	
	@Override
	public JSONObject deleteStores(HttpServletRequest request, JSONObject result) throws Exception {
		result= new JSONObject();
		// 客户ID
		Integer[] ids= CommonUtils.strToIntegerArray(request.getParameter("privIds"));
		result.put("total", ids.length);
		List<Integer> deletable= new ArrayList<Integer>();
		for(int i= 0; i< ids.length; i++){
			if(storeMapper.judgeStoreContractExistOrNot(ids[i]) != 0){
				// 店铺存在合同则不能删除
				continue;
				
			}
			deletable.add(ids[i]);
			
		}
		if(CommonUtils.checkExistOrNot(deletable)){
			storeMapper.delStores(deletable);
			
		}
		result.put("success", deletable.size());
		return result;
		
	}
	
	@Override
	public QueryResult<Map<String,Object>> query(QueryParameter queryParam) {
		QueryResult<Map<String,Object>> qr= new QueryResult<Map<String,Object>>();
		qr.setResultlist(storeMapper.query(queryParam));
		qr.setTotalrecord(storeMapper.count(queryParam));
		return qr;
		
	}

	@Override
	public Store selectBySelective(Store store) {
		// TODO Auto-generated method stub
		return storeMapper.selectBySelective(store);
	}

}
