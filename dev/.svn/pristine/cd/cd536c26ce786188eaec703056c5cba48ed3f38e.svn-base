package com.bt.orderPlatform.service.impl;

import java.math.BigDecimal;
import java.nio.file.Watchable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bt.common.util.CommonUtil;
import com.bt.common.util.UUIDUtils;
import com.bt.orderPlatform.controller.form.SFExpressPrint;
import com.bt.orderPlatform.controller.form.WaybillMasterQueryParam;
import com.bt.orderPlatform.dao.WaybillMasterMapper;
import com.bt.orderPlatform.model.BaoZunWaybilMasterDetail;
import com.bt.orderPlatform.model.ExpressinfoMasterInputlist;
import com.bt.orderPlatform.model.InterfaceRouteinfo;
import com.bt.orderPlatform.model.WaybilDetail;
import com.bt.orderPlatform.model.WaybilMasterDetail;
import com.bt.orderPlatform.model.WaybillMaster;
import com.bt.orderPlatform.model.WaybillMasterDetail;
import com.bt.orderPlatform.page.QueryResult;
import com.bt.orderPlatform.service.InterfaceRouteinfoService;
import com.bt.orderPlatform.service.WaybilDetailService;
import com.bt.orderPlatform.service.WaybilMasterDetailService;
import com.bt.orderPlatform.service.WaybillMasterService;

@Service
public class WaybillMasterServiceImpl<T> implements WaybillMasterService<T> {

	@Resource(name = "waybilMasterDetailServiceImpl")
	private WaybilMasterDetailService<WaybilMasterDetail> waybilMasterDetailService;
	@Resource(name = "interfaceRouteinfoServiceImpl")
	private InterfaceRouteinfoService<InterfaceRouteinfo> interfaceRouteinfoService;
	@Resource(name = "waybilDetailServiceImpl")
	private WaybilDetailService<WaybilDetail> waybilDetailService;
	@Autowired
	private WaybillMasterMapper<T> mapper;

	public WaybillMasterMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public void CancelOrder(WaybillMaster master) {
		// TODO Auto-generated method stub
		mapper.CancelOrder(master);
	}

	@Override
	public int insertByObj(WaybillMaster master) {
		// TODO Auto-generated method stub
		return mapper.insertByObj(master);
	}

	@Override
	public void updateByObj(WaybillMaster master) {
		// TODO Auto-generated method stub
		mapper.updateByObj(master);
	}

	@Override
	public QueryResult<Map<String, Object>> findAllWaybillMaster(WaybillMasterQueryParam queryParam) {
		QueryResult<Map<String, Object>> qr = new QueryResult<Map<String, Object>>();
		qr.setResultlist(mapper.selectAllWaybillMaster(queryParam));
		qr.setTotalrecord(mapper.countAllWaybillMaster(queryParam));
		return qr;
	}

	@Override
	public Integer findMaxOrderId() {
		// TODO Auto-generated method stub
		return mapper.findMaxOrderId();
	}

	@Override
	public void insertOrder(WaybillMaster queryParam, JSONArray arr, Date date) {
		// TODO Auto-generated method stub
		String result=get_business_code();
		queryParam.setOrder_id(result);
		for (int i = 0; i < arr.size(); i++) {
			JSONArray js1 = (JSONArray) arr.get(i);
			JSONObject js = (JSONObject) js1.get(0);
			WaybilDetail waybillDetail = new WaybilDetail();
			waybillDetail.setId(UUID.randomUUID().toString());
			waybillDetail.setOrder_id(queryParam.getOrder_id());
			waybillDetail.setCreate_time(date);
			waybillDetail.setUpdate_time(date);
			waybillDetail.setCreate_user(queryParam.getCreate_user());
			waybillDetail.setUpdate_user(queryParam.getCreate_user());
			waybillDetail.setSku_code(js.get("sku_code").toString());
			waybillDetail.setSku_name(js.get("sku_name").toString());
			waybillDetail.setSerial_number(js.get("serial_number").toString());
			Integer qty = Integer.parseInt(js.get("qty").toString());
			waybillDetail.setQty(qty);
			if (i != 0) {
				queryParam.setTotal_qty(queryParam.getTotal_qty() + qty);
			} else {
				queryParam.setTotal_qty(qty);
			}
			queryParam.setAmount_flag(js.get("amount_flag").toString());
			waybillDetail.setStatus(queryParam.getStatus());
			if (!js.get("weight").toString().equals("")) {
				waybillDetail.setWeight(new BigDecimal(js.get("weight").toString()));
			}
			if (!js.get("volumn").toString().equals("")) {
				waybillDetail.setVolumn(new BigDecimal(js.get("volumn").toString()));
			}
			if (!js.get("amount").toString().equals("")) {
				waybillDetail.setAmount(new BigDecimal(js.get("amount").toString()));
			}
			if (queryParam.getTotal_amount() == null) {
				queryParam.setTotal_amount(waybillDetail.getAmount());
			} else if (waybillDetail.getAmount() == null) {
				queryParam.setTotal_amount(queryParam.getTotal_amount());
			} else {
				queryParam.setTotal_amount(waybillDetail.getAmount().add(queryParam.getTotal_amount()));
			}
			if (queryParam.getTotal_volumn() == null) {
				queryParam.setTotal_volumn(waybillDetail.getVolumn());
			} else if (waybillDetail.getVolumn() == null) {
				queryParam.setTotal_volumn(queryParam.getTotal_volumn());
			} else {
				queryParam.setTotal_volumn(waybillDetail.getVolumn().add(queryParam.getTotal_volumn()));
			}
			if (queryParam.getTotal_weight() == null) {
				queryParam.setTotal_weight(waybillDetail.getWeight());
			} else if (waybillDetail.getWeight() == null) {
				queryParam.setTotal_weight(queryParam.getTotal_weight());
			} else {
				queryParam.setTotal_weight(waybillDetail.getWeight().add(queryParam.getTotal_weight()));
			}
			waybilDetailService.insert(waybillDetail);
		}
		mapper.insertOrder(queryParam);
	}

	@Override
	public List<WaybillMaster> queryOrder(WaybillMasterQueryParam queryParam) {
		// TODO Auto-generated method stub
		return mapper.queryOrder(queryParam);
	}

	@Override
	public WaybillMaster selectById(String id) {
		// TODO Auto-generated method stub
		return mapper.selectById(id);
	}

	@Override
	public void updatestatus(List<String> list, WaybillMaster waybillmaster) {
		// TODO Auto-generated method stub
		for (String order_id : list) {
			waybillmaster.setOrder_id(order_id);
			List<WaybilDetail> waybilDetail = waybilDetailService.queryOrderByOrderId(order_id);
			if (waybilDetail != null) {
				WaybilDetail quaram = new WaybilDetail();
				quaram.setOrder_id(order_id);
				quaram.setStatus(waybillmaster.getStatus());
				waybilDetailService.updatestatus(quaram);
			}
			mapper.updatestatus(waybillmaster);

		}
	}

	/*
	 * @Override public void insertAllOrder(List<WaybillMasterDetail> wbd_list)
	 * { // TODO Auto-generated method stubD List<WaybillMasterDetail> list =
	 * new ArrayList<WaybillMasterDetail>(); for (WaybillMasterDetail
	 * waybillMasterDetail : wbd_list) { waybillMasterDetail.setStatus("1"); for
	 * (int i=0; i<list.size();i++) { WaybillMasterDetail waybillMasterDetail2 =
	 * list.get(i);
	 * if(waybillMasterDetail2.getCustomer_number()==waybillMasterDetail.
	 * getCustomer_number()||
	 * waybillMasterDetail.getCustomer_number().equals(waybillMasterDetail2.
	 * getCustomer_number())){
	 * waybillMasterDetail.setOrder_id(waybillMasterDetail2.getOrder_id());
	 * waybillMasterDetail.setId(waybillMasterDetail2.getId());
	 * waybillMasterDetail.setTotal_qty(waybillMasterDetail.getQty()+
	 * waybillMasterDetail2.getTotal_qty());
	 * if(waybillMasterDetail2.getTotal_amount()==null){
	 * waybillMasterDetail.setTotal_amount(waybillMasterDetail.getAmount());
	 * }else{
	 * waybillMasterDetail.setTotal_amount(waybillMasterDetail.getAmount().add(
	 * waybillMasterDetail2.getTotal_amount())); }
	 * if(waybillMasterDetail2.getTotal_volumn()==null){
	 * waybillMasterDetail.setTotal_volumn(waybillMasterDetail.getVolumn());
	 * }else{
	 * waybillMasterDetail.setTotal_volumn(waybillMasterDetail.getVolumn().add(
	 * waybillMasterDetail2.getTotal_volumn())); }
	 * if(waybillMasterDetail2.getTotal_weight()==null){
	 * waybillMasterDetail.setTotal_weight(waybillMasterDetail.getWeight());
	 * }else{
	 * waybillMasterDetail.setTotal_weight(waybillMasterDetail.getWeight().add(
	 * waybillMasterDetail2.getTotal_weight())); } list.set(i,
	 * waybillMasterDetail); }else{ String uuid = UUIDUtils.getUUID8L();
	 * waybillMasterDetail.setOrder_id(uuid); String uuid1 =
	 * UUIDUtils.getUUID8L(); waybillMasterDetail.setId(uuid1);
	 * waybillMasterDetail.setTotal_qty(waybillMasterDetail.getQty());
	 * waybillMasterDetail.setTotal_amount(waybillMasterDetail.getAmount());
	 * waybillMasterDetail.setTotal_volumn(waybillMasterDetail.getVolumn());
	 * waybillMasterDetail.setTotal_weight(waybillMasterDetail.getWeight());
	 * list.add(waybillMasterDetail); } }
	 * if(list.size()==0||list.isEmpty()||list.equals(null)||list.equals("")){
	 * waybillMasterDetail.setOrder_id(UUIDUtils.getUUID8L());
	 * waybillMasterDetail.setId(UUIDUtils.getUUID8L());
	 * waybillMasterDetail.setTotal_qty(waybillMasterDetail.getQty());
	 * waybillMasterDetail.setTotal_amount(waybillMasterDetail.getAmount());
	 * waybillMasterDetail.setTotal_volumn(waybillMasterDetail.getVolumn());
	 * waybillMasterDetail.setTotal_weight(waybillMasterDetail.getWeight());
	 * list.add(waybillMasterDetail); }
	 * if(waybillMaster==null||waybillMaster.equals("")||waybillMaster.equals(
	 * null)){ String uuid = UUIDUtils.getUUID8L();
	 * waybillMasterDetail.setOrder_id(uuid); String uuid1 =
	 * UUIDUtils.getUUID8L(); waybillMasterDetail.setId(uuid1);
	 * waybillMasterDetail.setTotal_qty(waybillMasterDetail.getQty());
	 * waybillMasterDetail.setTotal_amount(waybillMasterDetail.getAmount());
	 * waybillMasterDetail.setTotal_volumn(waybillMasterDetail.getTotal_volumn()
	 * ); waybillMasterDetail.setTotal_weight(waybillMasterDetail.getWeight());
	 * list.add(waybillMasterDetail); mapper.insertwmd(waybillMasterDetail);
	 * }else{
	 * 
	 * 
	 * String uuid = UUIDUtils.getUUID8L(); waybillMasterDetail.setId(uuid);
	 * waybilDetailService.insertwmd(waybillMasterDetail); } for
	 * (WaybillMasterDetail waybillMasterDetail3 : list) {
	 * mapper.insertwmd(waybillMasterDetail3); } }
	 */

	@Override
	public void updateOrder(WaybillMaster queryParam, JSONArray arr, Date date) {
		// TODO Auto-generated method stub
		List<WaybilDetail> waybilDetail = waybilDetailService.queryOrderByOrderId(queryParam.getOrder_id());
		queryParam.setTotal_amount(new BigDecimal(0));
		queryParam.setTotal_volumn(new BigDecimal(0));
		queryParam.setTotal_weight(new BigDecimal(0));
		for (WaybilDetail waybilDetail2 : waybilDetail) {
			waybilDetailService.deletById(waybilDetail2.getId());
		}
		for (int i = 0; i < arr.size(); i++) {
			JSONArray js1 = (JSONArray) arr.get(i);
			JSONObject js = (JSONObject) js1.get(0);
			WaybilDetail waybillDetail = new WaybilDetail();
			waybillDetail.setId(UUID.randomUUID().toString());
			waybillDetail.setOrder_id(queryParam.getOrder_id());
			waybillDetail.setCreate_time(date);
			waybillDetail.setUpdate_time(date);
			waybillDetail.setCreate_user(queryParam.getUpdate_user());
			waybillDetail.setUpdate_user(queryParam.getUpdate_user());
			waybillDetail.setSku_code(js.get("sku_code").toString());
			waybillDetail.setSku_name(js.get("sku_name").toString());
			waybillDetail.setSerial_number(js.get("serial_number").toString());

			if (!js.get("weight").toString().equals("")) {
				waybillDetail.setWeight(new BigDecimal(js.get("weight").toString()));
			}
			if (!js.get("volumn").toString().equals("")) {
				waybillDetail.setVolumn(new BigDecimal(js.get("volumn").toString()));
			}
			if (!js.get("amount").toString().equals("")) {
				waybillDetail.setAmount(new BigDecimal(js.get("amount").toString()));
			}
			if (queryParam.getTotal_amount() == null) {
				queryParam.setTotal_amount(waybillDetail.getAmount());
			} else {
				if (waybillDetail.getAmount() != null) {
					queryParam.setTotal_amount(waybillDetail.getAmount().add(queryParam.getTotal_amount()));
				} else {
					queryParam.setTotal_amount(queryParam.getTotal_amount());
				}
			}
			if (queryParam.getTotal_volumn() == null) {
				queryParam.setTotal_volumn(waybillDetail.getVolumn());
			} else {
				if (waybillDetail.getVolumn() != null) {
					queryParam.setTotal_volumn(waybillDetail.getVolumn().add(queryParam.getTotal_volumn()));
				} else {
					queryParam.setTotal_volumn(queryParam.getTotal_volumn());
				}
			}
			if (queryParam.getTotal_weight() == null) {
				queryParam.setTotal_weight(waybillDetail.getWeight());
			} else {
				if (waybillDetail.getWeight() != null) {
					queryParam.setTotal_weight(waybillDetail.getWeight().add(queryParam.getTotal_weight()));
				} else {
					queryParam.setTotal_weight(queryParam.getTotal_weight());
				}
			}
			queryParam.setAmount_flag(js.get("amount_flag").toString());
			/*
			 * waybillDetail.setWeight(new
			 * BigDecimal(js.get("weight").toString()));
			 * waybillDetail.setVolumn(new
			 * BigDecimal(js.get("volumn").toString()));
			 * waybillDetail.setAmount(new
			 * BigDecimal(js.get("amount").toString()));
			 */
			Integer qty = Integer.parseInt(js.get("qty").toString());
			waybillDetail.setQty(qty);
			waybillDetail.setStatus("1");
			waybilDetailService.insert(waybillDetail);
		}
		mapper.updateOrder(queryParam);
	}

	@Override
	public void updateByMaster(WaybillMaster master) {
		// TODO Auto-generated method stub
		mapper.updateByMaster(master);
	}

	@Override
	public List<WaybillMaster> queryByStatus(String status) {
		// TODO Auto-generated method stub
		return mapper.queryByStatus(status);
	}

	@Override
	public void setstatus(String orderId, String status) {
		// TODO Auto-generated method stub
		mapper.setstatus(orderId, status);
	}

	@Override
	public List<Map<String, Object>> selectByQuery(WaybillMasterQueryParam queryParam) {
		// TODO Auto-generated method stub
		return mapper.selectByQuery(queryParam);
	}

	@Override
	public WaybillMasterDetail queryByCustomerNum(String customer_number) {
		// TODO Auto-generated method stub
		return mapper.queryByCustomerNum(customer_number);
	}

	@Override
	public void setPrint_code(WaybillMaster waybillMaster) {
		// TODO Auto-generated method stub
		mapper.setPrint_code(waybillMaster);
	}

	@Override
	public void insertWayBilMasterDetail(List<WaybilMasterDetail> wbd_list) {
		// TODO Auto-generated method stub
		waybilMasterDetailService.insertWayBilMasterDetail(wbd_list);

	}

	@Override
	public void insetByWaybilMasterDetail(WaybilMasterDetail waybilMasterDetail2) {
		// TODO Auto-generated method stub
		waybilMasterDetail2.setTotal_amount(null);
		waybilMasterDetail2.setTotal_qty(null);
		waybilMasterDetail2.setTotal_volumn(null);
		waybilMasterDetail2.setTotal_weight(null);
		List<WaybilMasterDetail> list = waybilMasterDetailService.selectWaybilMasterDetail(waybilMasterDetail2);
		WaybilMasterDetail waybilMasterDetail = list.get(0);
		String result=get_business_code();
		waybilMasterDetail.setId(result);
		waybilMasterDetail.setOrder_id(result);
		waybilMasterDetail.setTotal_amount(waybilMasterDetail.getAmount());
		waybilMasterDetail.setTotal_qty(waybilMasterDetail.getQty());
		waybilMasterDetail.setTotal_volumn(waybilMasterDetail.getVolumn());
		waybilMasterDetail.setTotal_weight(waybilMasterDetail.getWeight());
		waybilMasterDetail.setStatus("1");
		waybilMasterDetail.setPrint_code("0");
		
		waybilMasterDetail.setOrdinal(waybilMasterDetail2.getOrdinal());
		
		mapper.insertWaybilMaster(waybilMasterDetail);
		String id1 = UUID.randomUUID().toString();
		waybilMasterDetail.setId(id1);
		waybilDetailService.insertwmd(waybilMasterDetail);
//		for (int i = 1; i < list.size(); i++) {
//			WaybilMasterDetail waybilMasterDetail3 = list.get(i);
//			WaybillMaster waybillMaster = mapper.selectById(result);
//			waybilMasterDetail3.setOrder_id(result);
//			waybilMasterDetail3.setStatus("1");
//			waybillMaster.setTotal_qty(waybilMasterDetail3.getQty() + waybillMaster.getTotal_qty());
//			if (waybillMaster.getTotal_amount() == null) {
//				waybillMaster.setTotal_amount(waybilMasterDetail3.getAmount());
//			} else {
//				waybillMaster.setTotal_amount(waybilMasterDetail3.getAmount().add(waybillMaster.getTotal_amount()));
//			}
//			if (waybillMaster.getTotal_volumn() == null) {
//				waybillMaster.setTotal_volumn(waybilMasterDetail3.getVolumn());
//			} else {
//				waybillMaster.setTotal_volumn(waybilMasterDetail3.getVolumn().add(waybillMaster.getTotal_volumn()));
//			}
//			if (waybillMaster.getTotal_weight() == null) {
//				waybillMaster.setTotal_weight(waybilMasterDetail3.getWeight());
//			} else {
//				waybillMaster.setTotal_weight(waybilMasterDetail3.getWeight().add(waybillMaster.getTotal_weight()));
//			}
//			mapper.updatewmd(waybillMaster);
//			String id3 = UUID.randomUUID().toString();
//			waybilMasterDetail3.setId(id3);
//			waybilDetailService.insertwmd(waybilMasterDetail3);
//		}
	}

	@Override
	public void insetWaybilMasterDetailByCustomer(WaybilMasterDetail waybilMasterDetail1) {
		// TODO Auto-generated method stub
		List<WaybilMasterDetail> list = waybilMasterDetailService.selectWaybilMasterDetailByCustomer(
				waybilMasterDetail1.getCustomer_number(), waybilMasterDetail1.getBat_id());
		WaybilMasterDetail waybilMasterDetail = list.get(0);
		String result=get_business_code();
		waybilMasterDetail.setId(result);
		waybilMasterDetail.setOrder_id(result);
		waybilMasterDetail.setTotal_amount(waybilMasterDetail.getAmount());
		waybilMasterDetail.setTotal_qty(waybilMasterDetail.getQty());
		waybilMasterDetail.setTotal_volumn(waybilMasterDetail.getVolumn());
		waybilMasterDetail.setTotal_weight(waybilMasterDetail.getWeight());
		waybilMasterDetail.setStatus("1");
		waybilMasterDetail.setPrint_code("0");
		
	    waybilMasterDetail.setOrdinal(waybilMasterDetail.getOrdinal());
		
		mapper.insertWaybilMaster(waybilMasterDetail);
		String id1 = UUID.randomUUID().toString();
		waybilMasterDetail.setId(id1);
		waybilDetailService.insertwmd(waybilMasterDetail);
		for (int i = 1; i < list.size(); i++) {
			WaybilMasterDetail waybilMasterDetail3 = list.get(i);
			WaybillMaster waybillMaster = mapper.selectById(result);
			waybilMasterDetail3.setOrder_id(result);
			waybilMasterDetail3.setStatus("1");
			waybillMaster.setTotal_qty(waybilMasterDetail3.getQty() + waybillMaster.getTotal_qty());
			if (waybillMaster.getTotal_amount() == null) {
				waybillMaster.setTotal_amount(waybilMasterDetail3.getAmount());
			} else {
				waybillMaster.setTotal_amount(waybilMasterDetail3.getAmount().add(waybillMaster.getTotal_amount()));
			}
			if (waybillMaster.getTotal_volumn() == null) {
				waybillMaster.setTotal_volumn(waybilMasterDetail3.getVolumn());
			} else {
				waybillMaster.setTotal_volumn(waybilMasterDetail3.getVolumn().add(waybillMaster.getTotal_volumn()));
			}
			if (waybillMaster.getTotal_weight() == null) {
				waybillMaster.setTotal_weight(waybilMasterDetail3.getWeight());
			} else {
				waybillMaster.setTotal_weight(waybilMasterDetail3.getWeight().add(waybillMaster.getTotal_weight()));
			}
			mapper.updatewmd(waybillMaster);
			String id3 = UUID.randomUUID().toString();
			waybilMasterDetail3.setId(id3);
			waybilDetailService.insertwmd(waybilMasterDetail3);
		}
	}

	@Override
	public void confirmOrdersById(String id, String stauts) {
		// TODO Auto-generated method stub
		mapper.confirmOrdersById(id, stauts);
	}

	@Override
	public void updateByremark(WaybilMasterDetail waybilMasterDetail) {
		// TODO Auto-generated method stub
		waybilMasterDetailService.updateByremark(waybilMasterDetail);
	}

	@Override
	public void insertWaybilMasterDetailByCustomer(String uuid) {
		// TODO Auto-generated method stub
		List<WaybilMasterDetail> waybilMasterDetailById = waybilMasterDetailService.selectByIdAndBatIdAndCustomer(uuid);
		for (WaybilMasterDetail waybilMasterDetail : waybilMasterDetailById) {
			WaybillMaster selectById = mapper.selectById(waybilMasterDetail.getId());
			if ((!selectById.getOrder_id().equals(null)) || (!selectById.getOrder_id().equals(""))
					|| selectById.getOrder_id() != null) {
				List<WaybilMasterDetail> selectWaybilMasterDetailByCustomer = waybilMasterDetailService
						.selectWaybilMasterDetailByCustomer(waybilMasterDetail.getCustomer_number(), uuid);
				waybilDetailService.deletByOrder_Id(selectById.getOrder_id());
				for (WaybilMasterDetail waybilMasterDetail2 : selectWaybilMasterDetailByCustomer) {
					waybilMasterDetail2.setOrder_id(selectById.getOrder_id());
					waybilDetailService.insertwmd(waybilMasterDetail2);
				}
			}
		}
		List<WaybilMasterDetail> waybilMasterDetail = waybilMasterDetailService.selectByBatId(uuid);
		for (WaybilMasterDetail waybilMasterDetail2 : waybilMasterDetail) {
			// waybillMasterServiceImpl.insetByWaybilMasterDetail(waybilMasterDetail2);
			String result=get_business_code();
			waybilMasterDetail2.setOrder_id(result);
			waybilMasterDetail2.setStatus("1");
			waybilMasterDetail2.setPrint_code("0");
			mapper.insertWaybilMaster(waybilMasterDetail2);
			waybilDetailService.insertwmd(waybilMasterDetail2);
		}
		List<WaybilMasterDetail> waybilMasterDetailByOther = waybilMasterDetailService.selectByIdAndBatId(uuid);
		for (WaybilMasterDetail waybilMasterDetail2 : waybilMasterDetailByOther) {
			WaybillMaster selectById = mapper.selectById(waybilMasterDetail2.getId());
			if (!selectById.getOrder_id().equals(null) || !selectById.getOrder_id().equals("")
					|| selectById.getOrder_id() != null) {
				List<WaybilMasterDetail> selectWaybilMasterDetailByCustomer = waybilMasterDetailService
						.selectByWaybilMasterDetail(waybilMasterDetail2);
				waybilDetailService.deletByOrder_Id(selectById.getOrder_id());
				for (WaybilMasterDetail waybilMasterDetail3 : selectWaybilMasterDetailByCustomer) {
					waybilMasterDetail3.setOrder_id(selectById.getOrder_id());
					waybilDetailService.insertwmd(waybilMasterDetail3);
				}
			}
		}
	}

	@Override
	public void insertWaybilMasterDetailByCustomerList(String uuid) {
		// TODO Auto-generated method stub
		List<WaybilMasterDetail> waybilMasterDetail1 = waybilMasterDetailService.selectByBatIdAndCustomer(uuid);
		for (WaybilMasterDetail waybilMasterDetail2 : waybilMasterDetail1) {
			String result=get_business_code();
			waybilMasterDetail2.setOrder_id(result);
			waybilMasterDetail2.setStatus("1");
			waybilMasterDetail2.setPrint_code("0");
			mapper.insertWaybilMaster(waybilMasterDetail2);
			waybilDetailService.insertwmd(waybilMasterDetail2);
		}
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		mapper.deleteById(id);
	}

	@Override
	public List<WaybillMaster> selectByQuery_test(String query_test, String org_cde, String status) {
		// TODO Auto-generated method stub
		return mapper.selectByQuery_test(query_test, org_cde, status);
	}

	@Override
	public List<WaybillMaster> selectByWaybillMaster(WaybillMaster queryParam) {
		// TODO Auto-generated method stub
		return mapper.selectByWaybillMaster(queryParam);
	}

	@Override
	public List<Map<String, Object>> selectByQueryCSV(WaybillMasterQueryParam queryParam) {
		// TODO Auto-generated method stub
		return mapper.selectByQueryCSV(queryParam);
	}

	@Override
	public List<WaybillMaster> queryByStatusAndStartDate(String status) {
		// TODO Auto-generated method stub
		return mapper.queryByStatusAndStartDate(status);
	}

	@Override
	public void setStartDate(String waybill, Date route_time) {
		// TODO Auto-generated method stub
		mapper.setStartDate(waybill, route_time);
	}

	@Override
	public void updateOrdera(WaybillMaster queryParam) {
		// TODO Auto-generated method stub
		mapper.updateOrder(queryParam);
	}

	@Override
	public SFExpressPrint selectSFPrint(String id) {
		// TODO Auto-generated method stub
		return mapper.selectSFPrint(id);
	}

	@Override
	public List<WaybillMaster> selectByBatIdAndStatus(String bat_id) {
		// TODO Auto-generated method stub
		return mapper.selectByBatIdAndStatus(bat_id);
	}

	@Override
	public List<WaybillMaster> selectIdByBatIdAndStatus(String bat_id) {
		// TODO Auto-generated method stub
		return mapper.selectIdByBatIdAndStatus(bat_id);
	}

	@Override
	public void insertBaoZunWayBilMasterDetail(List<BaoZunWaybilMasterDetail> wbd_list1) {
		// TODO Auto-generated method stub
		waybilMasterDetailService.insertBaoZunWayBilMasterDetail(wbd_list1);
	}

	@Override
	public void updateplaceError(String order_id, String status, String placeError) {
		// TODO Auto-generated method stub
		mapper.updateplaceError(order_id, status, placeError);
	}

	@Override
	public String get_business_code() {
		Map<String,String>param=new HashMap<String,String>();
		param.put("business_type", "1");
		param.put("serial_code", "");
		mapper.get_business_code(param);
		System.out.println(param.get("serial_code"));
		return param.get("serial_code");
	}

	@Override
	public List<WaybillMaster> queryByStatusSF() {
		// TODO Auto-generated method stub
		return mapper.queryByStatusSF();
	}

	@Override
	public int updExpTime(Map<String, Object> map) {
		return mapper.updExpTime(map);
	}

    @Override
    public int updateSendTypeById(Integer sendType,String id){
        return mapper.updateSendTypeById(sendType,id);
    }

    @Override
    public List<WaybillMaster> queryBySendTypeAndCount(Integer sendType,Integer count){
        // TODO Auto-generated method stub
        return mapper.queryBySendTypeAndCount(sendType, count);
    }

	@Override
	public List<WaybillMaster> query2MonthAgoMaster() {
		// TODO Auto-generated method stub
		return mapper.query2MonthAgoMaster();
	}

	@Override
	public int updateRunTime(String waybill) {
		// TODO Auto-generated method stub
		return mapper.updateRunTime(waybill);
	}

}
