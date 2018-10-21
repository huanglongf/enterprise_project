package com.bt.orderPlatform.service.impl;


import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.orderPlatform.dao.WaybillMasterBackupsMapper;
import com.bt.orderPlatform.model.WaybilDetail;
import com.bt.orderPlatform.model.WaybilMasterDetail;
import com.bt.orderPlatform.model.WaybillMaster;
import com.bt.orderPlatform.model.WaybillMasterBackups;
import com.bt.orderPlatform.service.WaybilDetailBackupsService;
import com.bt.orderPlatform.service.WaybilDetailService;
import com.bt.orderPlatform.service.WaybilMasterDetailService;
import com.bt.orderPlatform.service.WaybillMasterBackupsService;
import com.bt.orderPlatform.service.WaybillMasterService;
@Service
public class WaybillMasterBackupsServiceImpl<T>  implements WaybillMasterBackupsService<T> {

	@Resource(name = "waybillMasterBackupsServiceImpl")
	private WaybillMasterBackupsService<WaybillMasterBackups> waybillMasterBackupsService;
	@Resource(name = "waybilDetailBackupsServiceImpl")
	private WaybilDetailBackupsService<WaybilDetail> waybilDetailBackupsService;
	@Resource(name = "waybilMasterDetailServiceImpl")
	private WaybilMasterDetailService<WaybilMasterDetail> waybilMasterDetailService;
	@Resource(name = "waybillMasterServiceImpl")
	private  WaybillMasterService<WaybillMaster> waybillMasterServiceImpl;
	@Autowired
    private WaybillMasterBackupsMapper<T> mapper;

	public WaybillMasterBackupsMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public void insert(WaybillMaster waybillMaster) {
		// TODO Auto-generated method stub
		mapper.insert(waybillMaster);
	}

	@Override
	public void insetWaybilMasterDetailByCustomer(WaybilMasterDetail waybilMasterDetail1) {
		// TODO Auto-generated method stub
		List<WaybilMasterDetail> list = waybilMasterDetailService.selectWaybilMasterDetailByCustomer(waybilMasterDetail1.getCustomer_number(),waybilMasterDetail1.getBat_id());
		WaybilMasterDetail waybilMasterDetail = list.get(0);
		String result=waybillMasterServiceImpl.get_business_code();
		waybilMasterDetail.setId(result);
		waybilMasterDetail.setOrder_id(result);
		waybilMasterDetail.setTotal_amount(waybilMasterDetail.getAmount());
		waybilMasterDetail.setTotal_qty(waybilMasterDetail.getQty());
		waybilMasterDetail.setTotal_volumn(waybilMasterDetail.getVolumn());
		waybilMasterDetail.setTotal_weight(waybilMasterDetail.getWeight());
		waybilMasterDetail.setStatus("1");
		waybilMasterDetail.setPrint_code("0");
		mapper.insertWaybilMaster(waybilMasterDetail);
		String id1 = UUID.randomUUID().toString();
		waybilMasterDetail.setId(id1);
		waybilDetailBackupsService.insertwmd(waybilMasterDetail);
		for (int i =1; i<list.size();i++) {
			WaybilMasterDetail waybilMasterDetail3 = list.get(i);
			WaybillMaster waybillMaster = mapper.selectById(result);
			waybilMasterDetail3.setOrder_id(result);
			waybilMasterDetail3.setStatus("1");
			waybillMaster.setTotal_qty(waybilMasterDetail3.getQty()+waybillMaster.getTotal_qty());
			if(waybillMaster.getTotal_amount()==null){
				waybillMaster.setTotal_amount(waybilMasterDetail3.getAmount());
			}else{
				waybillMaster.setTotal_amount(waybilMasterDetail3.getAmount().add(waybillMaster.getTotal_amount()));
			}
			if(waybillMaster.getTotal_volumn()==null){
				waybillMaster.setTotal_volumn(waybilMasterDetail3.getVolumn());
			}else{
				waybillMaster.setTotal_volumn(waybilMasterDetail3.getVolumn().add(waybillMaster.getTotal_volumn()));
			}
			if(waybillMaster.getTotal_weight()==null){
				waybillMaster.setTotal_weight(waybilMasterDetail3.getWeight());
			}else{
				waybillMaster.setTotal_weight(waybilMasterDetail3.getWeight().add(waybillMaster.getTotal_weight()));
			}
			mapper.updatewmd(waybillMaster);
			String id3 = UUID.randomUUID().toString();
			waybilMasterDetail3.setId(id3);
			waybilDetailBackupsService.insertwmd(waybilMasterDetail3);
		}
	}
	
}
