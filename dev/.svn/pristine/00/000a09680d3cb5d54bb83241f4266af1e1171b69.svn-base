package com.bt.lmis.basis.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.bt.lmis.dao.TransportVendorMapper;
import com.bt.lmis.model.TransportVendor;

import com.bt.utils.SessionUtils;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.common.controller.param.Parameter;
import com.bt.common.model.ResultMessage;
import com.bt.lmis.basis.dao.StoreManagerMapper;
import com.bt.lmis.basis.model.Store;
import com.bt.lmis.basis.service.StoreManagerService;
import com.bt.lmis.basis.web.StoreController;
import com.bt.lmis.controller.form.StoreQueryParam;
import com.bt.lmis.page.QueryResult;
import com.bt.utils.CommonUtils;

import javax.servlet.http.HttpServletRequest;

/** 
 * @ClassName: StoreManagerServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年7月31日 下午1:10:15 
 * 
 */
@Service
public class StoreManagerServiceImpl implements StoreManagerService<T> {

	private static final Logger logger = Logger.getLogger(StoreController.class);
	
	@Autowired
	private StoreManagerMapper<T> storeManagerMapper;
	@Autowired
	private TransportVendorMapper<T> transportVendorMapper;

	@Override
	public QueryResult<Map<String, Object>> listStoreView(Parameter paremeter) {
		QueryResult<Map<String,Object>> qr= new QueryResult<Map<String,Object>>();
		qr.setResultlist(storeManagerMapper.listStoreView(paremeter));
		qr.setTotalrecord(storeManagerMapper.countStoreView(paremeter));
		return qr;
	}

	@Override
	public Store getStore(int id) {
		return storeManagerMapper.getStore(id);
	}

	@Override
	public ResultMessage save(Parameter parameter) {
		try {
			String message=checkStore(parameter);
			if(CommonUtils.checkExistOrNot(message)) {
				return new ResultMessage(ResultMessage.OPERATION_ERROR, message);
				
			}
			if(CommonUtils.checkExistOrNot(parameter.getParam().get("woFlag"))) {
				parameter.getParam().put("woFlag", "1");
				
			} else {
				parameter.getParam().put("woFlag", "0");
				
			}
			if(CommonUtils.checkExistOrNot(parameter.getParam().get("validity"))) {
				parameter.getParam().put("validity", "1");
				
			} else {
				parameter.getParam().put("validity", "0");
				
			}
			if(CommonUtils.checkExistOrNot(parameter.getParam().get("storebj"))) {//是否保价
				parameter.getParam().put("storebj", "1");

			} else {
				parameter.getParam().put("storebj", "0");

			}
			if(CommonUtils.checkExistOrNot(parameter.getParam().get("id"))) {
				storeManagerMapper.update(parameter);
				
			} else {
				//
				storeManagerMapper.insert(parameter);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			return new ResultMessage(ResultMessage.SYSTEM_ERR,CommonUtils.getExceptionStack(e));
			
		} finally {
			
			
		}
		return new ResultMessage(ResultMessage.SUCCESS,"操作成功");
	}
	
	/**
	 * @Title: checkStore
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param parameter
	 * @return: String
	 * @author: Ian.Huang
	 * @date: 2017年8月1日 下午4:22:53
	 */
	private String checkStore(Parameter parameter) {
		if(!CommonUtils.checkExistOrNot(parameter.getParam().get("costCenter"))) {
			return "成本中心为空";
			
		}
		if(!CommonUtils.checkExistOrNot(parameter.getParam().get("storeCode"))) {
			return "店铺编码为空";
			
		}
		if(!CommonUtils.checkExistOrNot(parameter.getParam().get("storeName"))) {
			return "店铺名称为空";
			
		}
		if(!CommonUtils.checkExistOrNot(parameter.getParam().get("storeType"))) {
			return "店铺类型为空";
			
		}
		if(!CommonUtils.checkExistOrNot(parameter.getParam().get("settlementMethod"))) {
			return "结算方式为空";
			
		}
		if(!CommonUtils.checkExistOrNot(parameter.getParam().get("contact"))) {
			return "联系人为空";
			
		}
		if(!CommonUtils.checkExistOrNot(parameter.getParam().get("phone"))) {
			return "联系电话为空";
			
		}
		if(!CommonUtils.checkExistOrNot(parameter.getParam().get("address"))) {
			return "联系地址为空";
			
		}
		if(!CommonUtils.checkExistOrNot(parameter.getParam().get("company"))) {
			return "开票公司为空";
			
		}
		if(!CommonUtils.checkExistOrNot(parameter.getParam().get("invoiceType"))) {
			return "发票类型为空";
			
		}
		if(CommonUtils.checkExistOrNot(parameter.getParam().get("id"))) {
			// 更新
			List<Integer> ids=storeManagerMapper.countStoreCode(parameter);
			if(ids.size() != 0 && !ids.get(0).toString().equals(parameter.getParam().get("id"))) {
				return "店铺编码已存在";
				
			}
			//
			ids=storeManagerMapper.countStoreName(parameter);
			if(ids.size() != 0 && !ids.get(0).toString().equals(parameter.getParam().get("id"))) {
				return "店铺名称已存在";
				
			}
			
		} else {
			// 新增
			if(storeManagerMapper.countStoreCode(parameter).size() != 0) {
				return "店铺编码已存在";
				
			}
			if(storeManagerMapper.countStoreName(parameter).size() != 0) {
				return "店铺名称已存在";
				
			}
			
		}
		return null;
		
	}

	@Override
	public ResultMessage del(Parameter parameter) {
		try {
			storeManagerMapper.del((String[]) parameter.getParam().get("ids[]"));
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			return new ResultMessage(ResultMessage.SYSTEM_ERR,CommonUtils.getExceptionStack(e));
			
		} finally {
			
			
		}
		return new ResultMessage(ResultMessage.SUCCESS,"删除成功");
	}

	@Override
	public Store selectByStroeName(String storeName) {
		// TODO Auto-generated method stub
		return storeManagerMapper.selectByStroeName(storeName);
	}

	@Override
	public List<Map<String, Object>> findAll() {
		// TODO Auto-generated method stub
		return storeManagerMapper.findAll();
	}

	@Override
	public Store selectByStoreCode(String storeCode) {
		// TODO Auto-generated method stub
		return storeManagerMapper.selectByStoreCode(storeCode);
	}

	@Override
	public List<Map<String, Object>> download(Parameter paremeter) {
		// TODO Auto-generated method stub
		return storeManagerMapper.listStoreView(paremeter);
	}

	@Override
	public void getSomeParams(HttpServletRequest request) {

			Store store = storeManagerMapper.getStore(Integer.parseInt(request.getParameter("id")+""));
		List<Map<String, Object>> storeTransport = storeManagerMapper.selectTransportByStoreCode(request.getParameter("storeCode"));
		List<Map<String,Object>>mapList =  transportVendorMapper.findAllTransportVendor();
		List<Map<String,Object>> transport = new ArrayList<>();
		for (int j = 0; j < mapList.size(); j++) {
			TransportVendor t = (TransportVendor)mapList.get(j);
			Map<String,Object> temp = new HashMap<>();
			temp.put("id", t.getTransport_code());
			temp.put("name", t.getTransport_name());
			for(Map<String, Object> map:storeTransport){
				if (map.get("transport_code").toString().equals(t.getTransport_code())){
					temp.put("checked", "true");//默认选中
				}
			}
			transport.add(temp);
		}
		    JSONArray objects = JSONArray.fromObject(transport);
			request.setAttribute("store", store);
			request.setAttribute("transport", objects);

	}

	@Override
	public int addStoreTransport(HttpServletRequest request, String transportCodes, String storeCode,String storebj ) {
	
		String [] arrTransportCode = transportCodes.split(",");
		List<Map<String,Object>> list  = new ArrayList<>();
		List<Map<String, Object>> mapList = storeManagerMapper.selectTransportByStoreCode(storeCode);
		List<String> transportCodeList = new ArrayList<>();
		for(int i =0;i<arrTransportCode.length;i++){
			Map<String,Object> map = new HashMap<>();
			map.put("store_code",storeCode);
			map.put("transport_code",arrTransportCode[i]);
			map.put("create_by",SessionUtils.getEMP(request).getId());
			map.put("storebj",storebj);
			list.add(map);
		}
		int a =storeManagerMapper.deleteByStoreCode(storeCode);
		int b  = storeManagerMapper.addStoreTransport(list);
		return a+b;
	}

}
