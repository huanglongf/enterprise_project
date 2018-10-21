package com.bt.lmis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.controller.form.ExpertBillQueryParam;
import com.bt.lmis.dao.ExpertBillMapper;
import com.bt.lmis.model.EmsTemplate;
import com.bt.lmis.model.ExpertBill;
import com.bt.lmis.model.ExpressagingDetail;
import com.bt.lmis.model.ImpModel;
import com.bt.lmis.model.SfTemplate;
import com.bt.lmis.model.StoTemplate;
import com.bt.lmis.model.YtoTemplate;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.ExpertBillService;
import com.bt.radar.model.ReceiveDeadline;
@Service
public class ExpertBillServiceImpl<T> extends ServiceSupport<T> implements ExpertBillService<T> {

	@Autowired
    private ExpertBillMapper<T> mapper;

	@Override
	public QueryResult<ExpertBill> findAll(ExpertBillQueryParam ebQP) {
		QueryResult<ExpertBill> qr = new QueryResult<ExpertBill>();
		qr.setResultlist(mapper.findAlls(ebQP));
		qr.setTotalrecord(mapper.countAlls(ebQP));
		return qr;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return null;
	}

	@Override
	public void save_ems(EmsTemplate ems) {
		mapper.save_ems(ems);
	}

	@Override
	public void save_sf(SfTemplate sf) {
		mapper.save_sf(sf);
	}

	@Override
	public void save_yto(YtoTemplate yto) {
		mapper.save_yto(yto);
	}

	@Override
	public void save_sto(StoTemplate sto) {
		mapper.save_sto(sto);
	}

	@Override
	public void save_eb(ExpertBill eb) {
		mapper.save_eb(eb);
	}

	@Override
	public void del_ems(String bat_id) {
		mapper.del_ems(bat_id);
	}

	@Override
	public void del_sf(String bat_id) {
		mapper.del_sf(bat_id);
	}

	@Override
	public void del_yto(String bat_id) {
		mapper.del_yto(bat_id);
	}

	@Override
	public void del_sto(String bat_id) {
		mapper.del_sto(bat_id);
	}

	@Override
	public List<ExpertBill> finrByID(String id) {
		return mapper.finrByID(id);
	}

	@Override
	public void del_eb(String id) {
		mapper.del_eb(id);
	}

	@Override
	public void save_imp(ImpModel imp) {
		mapper.save_imp(imp);
	}

	@Override
	public void del_imp(String bat_id) {
		mapper.del_imp(bat_id);
	}

	@Override
	public List<Map<String, Object>> findImpByBatId(String id) {
		return mapper.findImpByBatId(id);
	}

	@Override
	public void check_wh(String id) {
		mapper.check_wh(id);
	}

	@Override
	public void check_exp(String id) {
		mapper.check_exp(id);
	}

	@Override
	public void check_area_s(String id) {
		mapper.check_area_s(id);
	}

	@Override
	public void check_area_q(String id) {
		mapper.check_area_q(id);
	}

	@Override
	public void check_type(String id) {
		mapper.check_type(id);
	}

	@Override
	public void check_main(String id) {
		mapper.check_main(id);
	}

	@Override
	public List<Map<String, Object>> query_main(String id) {
		return mapper.query_main(id);
	}

	@Override
	public void ins_imp(ReceiveDeadline receiveDeadline,List<ExpressagingDetail> impList) {
		String uuid = receiveDeadline.getId();
		for (int i = 0; i < impList.size(); i++) {
			mapper.ins_imp_detail(impList.get(i));
		}
		mapper.ins_imp(receiveDeadline);
	}

	@Override
	public List<ImpModel> query_imp_del(ImpModel imp) {
		return mapper.query_imp_del(imp);
	}

	@Override
	public void up_eb(String bat_id) {
		mapper.up_eb(bat_id);
	}

	@Override
	public void batchInsert(List<String[]> sf_list) {
		// TODO Auto-generated method stub
		mapper.batchInsert(sf_list);
	}

	
}
