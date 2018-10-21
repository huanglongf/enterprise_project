package com.bt.lmis.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.ClientMapper;
import com.bt.lmis.dao.StoreMapper;
import com.bt.lmis.model.Client;
import com.bt.lmis.page.QueryParameter;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.ClientService;
import com.bt.utils.CommonUtils;
import com.bt.utils.SessionUtils;
@Service
public class ClientServiceImpl<T> extends ServiceSupport<T> implements ClientService<T> {

	@Autowired
    private ClientMapper<T> clientMapper;
	public ClientMapper<T> getClientMapper() {
		return clientMapper;
		
	}
	
	@Autowired
    private StoreMapper<T> storeMapper;
	public StoreMapper<T> getStoreMapper() {
		return storeMapper;
		
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return null;
		
	}

	@Override
	public Client findByContractOwner(String contract_owner) {
		return clientMapper.findByContractOwner(contract_owner);
		
	}
	
	@Override
	public List<Map<String, Object>> findAll() {
		return clientMapper.findAll();
	}

	@Override
	public List<Client> findByClient(Client client) {
		return clientMapper.findByClient(client);
		
	}
	
	@Override
	public JSONObject editClient(HttpServletRequest request, Client client, JSONObject result) throws Exception {
		result= new JSONObject();
		//
		Client temp= clientMapper.findByContractOwner(client.getClient_code());
		if(CommonUtils.checkExistOrNot(client.getId())){
			// 客户ID存在则更新
			if(CommonUtils.checkExistOrNot(temp) && (!temp.getId().equals(client.getId()))){
				// 已存在此客户编码
				result.put("result_code", "FAILURE");
				result.put("result_content", "客户编码已存在！");
				
			} else {
				if(!client.getValidity() && !temp.getValidity().equals(client.getValidity()) && (clientMapper.judgeClientContractExistOrNot(client.getId()) != 0)) {
					// 失效操作且为有效性变更
					// 客户下存在合同
					result.put("result_code", "FAILURE");
					result.put("result_content", "客户下已存在合同，无法失效！");	
					
				} else {
					client.setUpdate_user(SessionUtils.getEMP(request).getEmployee_number());
					clientMapper.updateClient(client);
					result.put("result_code", "SUCCESS");
					result.put("result_content", "更新客户信息成功！");
					
				}
				
			}
			
		} else {
			// 不存在则新增
			if(CommonUtils.checkExistOrNot(temp)){
				// 已存在此客户编码
				result.put("result_code", "FAILURE");
				result.put("result_content", "客户编码已存在！");
				
			} else {
				client.setCreate_user(SessionUtils.getEMP(request).getEmployee_number());
				clientMapper.addClient(client);
				result.put("result_code", "SUCCESS");
				result.put("result_content", "新增客户信息成功！");
				
			}
			
		}
		return result;
		
	}
	
	@Override
	public Client getById(Integer client_id) throws Exception {
		return clientMapper.getById(client_id);
		
	}
	
	@Override
	public JSONObject deleteClient(HttpServletRequest request, JSONObject result) throws Exception {
		result= new JSONObject();
		// 客户ID
		Integer[] ids= CommonUtils.strToIntegerArray(request.getParameter("privIds"));
		result.put("total", ids.length);
		List<Integer> deletable= new ArrayList<Integer>();
		for(int i= 0; i< ids.length; i++){
			if(CommonUtils.checkExistOrNot(storeMapper.selectByClient(ids[i])) || (clientMapper.judgeClientContractExistOrNot(ids[i]) != 0)){
				// 客户下存在店铺或客户存在合同则不能删除
				continue;
				
			}
			deletable.add(ids[i]);
			
		}
		if(CommonUtils.checkExistOrNot(deletable)) {
			clientMapper.delClients(deletable);
			
		}
		result.put("success", deletable.size());
		return result;
		
	}
	
	@Override
	public QueryResult<Map<String,Object>> query(QueryParameter queryParam) {
		QueryResult<Map<String,Object>> qr= new QueryResult<Map<String,Object>>();
		qr.setResultlist(clientMapper.query(queryParam));
		qr.setTotalrecord(clientMapper.count(queryParam));
		return qr;
		
	}

	@Override
	public boolean existClientCode(String client_code, int i) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Map<String, Object>> getStoreByClient(Map map) {
		// TODO Auto-generated method stub
		return clientMapper.getStoreByClient(map);
	}

}
