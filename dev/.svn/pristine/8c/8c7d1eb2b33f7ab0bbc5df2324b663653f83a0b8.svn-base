package com.bt.orderPlatform.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.orderPlatform.controller.form.WaybilMasterDetailQueryParam;
import com.bt.orderPlatform.dao.WaybilMasterDetailMapper;
import com.bt.orderPlatform.model.BaoZunWaybilMasterDetail;
import com.bt.orderPlatform.model.WaybilMasterDetail;
import com.bt.orderPlatform.model.WaybillMasterDetail;
import com.bt.orderPlatform.page.QueryResult;
import com.bt.orderPlatform.service.WaybilMasterDetailService;
@Service
public class WaybilMasterDetailServiceImpl<T> implements WaybilMasterDetailService<T> {

	@Autowired
    private WaybilMasterDetailMapper<T> mapper;

	public WaybilMasterDetailMapper<T> getMapper() {
		return mapper;
	}


	@Override
	public void insertWayBilMasterDetail(List<WaybilMasterDetail> wbd_list) {
		// TODO Auto-generated method stub
		/*System.out.println(new Date());
			mapper.insert(wbd_list);
		System.out.println(new Date());*/

//	     for (WaybilMasterDetail waybilMasterDetail : wbd_list) {
//	         mapper.insert(waybilMasterDetail); 
//	     
	    /***20180320***/
	    List<BaoZunWaybilMasterDetail> baoZunModelList = new ArrayList<>(wbd_list.size());
	    BaoZunWaybilMasterDetail orig = new BaoZunWaybilMasterDetail() ;
	    for (WaybilMasterDetail waybilMasterDetail : wbd_list) {
	    	 orig= new BaoZunWaybilMasterDetail() ;
	        BeanUtils.copyProperties(waybilMasterDetail, orig);
	        baoZunModelList.add(orig);
	    }
	    insertBaoZunWayBilMasterDetail(baoZunModelList); 
	}


	@Override
	public synchronized void updateByremark(WaybilMasterDetail waybilMasterDetail) {
		// TODO Auto-generated method stub
		mapper.updateByremark(waybilMasterDetail);
	}


	@Override
	public QueryResult<Map<String, Object>> findAllWaybilMasterDetail(WaybilMasterDetailQueryParam queryParam) {
		// TODO Auto-generated method stub
		QueryResult<Map<String, Object>> qr= new QueryResult<Map<String, Object>>();
		qr.setResultlist(mapper.selectAllWaybilMasterDetail(queryParam));
		qr.setTotalrecord(mapper.countAllWaybilMasterDetail(queryParam));
		return qr;
	}


	@Override
	public void deletetByBatId(String id) {
		// TODO Auto-generated method stub
		mapper.deletetByBatId(id);
	}


	@Override
	public List<WaybilMasterDetail> selectByBatId(String id) {
		// TODO Auto-generated method stub
		return mapper.selectByBatId(id);
	}


	@Override
	public List<WaybilMasterDetail> selectWaybilMasterDetail(WaybilMasterDetail waybilMasterDetail2) {
		// TODO Auto-generated method stub
		return mapper.selectWaybilMasterDetail(waybilMasterDetail2);
	}


	@Override
	public void updateByBatId(String id ,Integer flag) {
		// TODO Auto-generated method stub
		mapper.updateByBatId(id,flag);
	}


	@Override
	public List<WaybilMasterDetail> selectByBatIdAndCustomer(String id) {
		// TODO Auto-generated method stub
		return mapper.selectByBatIdAndCustomer(id);
	}


	@Override
	public List<WaybilMasterDetail> selectWaybilMasterDetailByCustomer(String customer_number,String id) {
		// TODO Auto-generated method stub
		return mapper.selectWaybilMasterDetailByCustomer(customer_number,id);
	}


	@Override
	public List<WaybilMasterDetail> queryByCustomerNum(String customer_number,String id) {
		// TODO Auto-generated method stub
		return mapper.queryByCustomerNum(customer_number,id);
	}


	@Override
	public List<WaybilMasterDetail> selectByBatIdAndStuats(String uuid) {
		// TODO Auto-generated method stub
		return mapper.selectByBatIdAndStuats(uuid);
	}


	@Override
	public List<WaybilMasterDetail> selectByIdAndBatIdAndCustomer(String uuid) {
		// TODO Auto-generated method stub
		return mapper.selectByIdAndBatIdAndCustomer(uuid);
	}


	@Override
	public List<WaybilMasterDetail> selectByIdAndBatId(String uuid) {
		// TODO Auto-generated method stub
		return mapper.selectByIdAndBatId(uuid);
	}


	@Override
	public List<WaybilMasterDetail> selectByWaybilMasterDetail(WaybilMasterDetail waybilMasterDetail2) {
		// TODO Auto-generated method stub
		return mapper.selectByWaybilMasterDetail(waybilMasterDetail2);
	}


	@Override
	public void insertBaoZunWayBilMasterDetail(List<BaoZunWaybilMasterDetail> wbd_list1) {
		// TODO Auto-generated method stub
		 for( BaoZunWaybilMasterDetail bb:wbd_list1) {
		    	System.out.println(bb.getId());
		    }
		for (BaoZunWaybilMasterDetail waybilMasterDetail : wbd_list1) {
			  mapper.insertBaoZun(waybilMasterDetail);
		}
	    /***20180320***/
//	    BaoZunWaybilMasterDetail waybilMasterDetail;
//		for(int i = 0 ; i<wbd_list1.size(); i++){
//		    waybilMasterDetail = wbd_list1.get(i);
//		    waybilMasterDetail.setOrdinal(i);
//		    mapper.insertBaoZun(waybilMasterDetail);
//		}
	}


	@Override
	public List<WaybilMasterDetail> baozunselectByBatId(String uuid) {
		// TODO Auto-generated method stub
		return mapper.baozunselectByBatId(uuid);
	}
	
	
	

/*
	@Override
	public List<WaybilMasterDetail> selectBySerial_number(String serial_number) {
		// TODO Auto-generated method stub
		return mapper.selectBySerial_number(serial_number);
	}
*/
		
	
}
