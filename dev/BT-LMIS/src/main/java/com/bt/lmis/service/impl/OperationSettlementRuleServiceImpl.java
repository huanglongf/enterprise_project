package com.bt.lmis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.OperationSettlementRuleMapper;
import com.bt.lmis.model.OperationSettlementRule;
import com.bt.lmis.service.OperationSettlementRuleService;
@Service
public class OperationSettlementRuleServiceImpl<T> extends ServiceSupport<T> implements OperationSettlementRuleService<T> {

	@Autowired
    private OperationSettlementRuleMapper<T> mapper;

	public OperationSettlementRuleMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}

	@Override
	public List<OperationSettlementRule> findByCBID(String cbid) {
		return  mapper.findByCBID(cbid);
	}

	@Override
	public void saveAupdateMain(Map<String, Object> map) {
		try {
			List<OperationSettlementRule> list = mapper.findByCBID(map.get("contract_id").toString());
			if(list.size()==0){
				mapper.saveMain(map);
			}else{
				mapper.updateMain(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveAupdateOSF(Map<String, Object> map, Map<String, Object> map2) {
		try {
			List<OperationSettlementRule> list = mapper.findByCBID(map.get("contract_id").toString());
			if(list.size()==0){
				mapper.saveMain(map);
			}else{
				mapper.updateMain(map);
			}
			mapper.saveAupdateOSF(map2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Map<String, Object>>  queryOSFList(String tbid) {
		return mapper.queryOSFList(tbid);
	}

	@Override
	public void deleteOSF(String id) {
		mapper.deleteOSF(id);
	}

	@Override
	public void saveAupdateTBT(Map<String, Object> map, Map<String, Object> map2) {
		try {
			List<OperationSettlementRule> list = mapper.findByCBID(map.get("contract_id").toString());
			if(list.size()==0){
				mapper.saveMain(map);
			}else{
				mapper.updateMain(map);
			}
			mapper.saveAupdateTBT(map2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Map<String, Object>> queryTBTList(String tbid) {
		return mapper.queryTBTList(tbid);
	}

	@Override
	public void deleteTBT(String id) {
		mapper.deleteTBT(id);
	}

	@Override
	public void saveAupdateTBNT(Map<String, Object> map,Map<String, Object> map2) {
		List<OperationSettlementRule> list = mapper.findByCBID(map.get("contract_id").toString());
		if(list.size()==0){
			mapper.saveMain(map);
		}else{
			mapper.updateMain(map);
		}
		mapper.saveAupdateTBNT(map2);
	}

	@Override
	public List<Map<String, Object>> queryTBNTList(String tbid) {
		return mapper.queryTBNTList(tbid);
	}

	@Override
	public void deleteTBNT(String id) {
		mapper.deleteTBNT(id);
	}

	@Override
	public void saveAupdateTOT(Map<String, Object> map, Map<String, Object> map2) {
		List<OperationSettlementRule> list = mapper.findByCBID(map.get("contract_id").toString());
		if(list.size()==0){
			mapper.saveMain(map);
		}else{
			mapper.updateMain(map);
		}
		mapper.saveAupdateTOT(map2);
	}

	@Override
	public List<Map<String, Object>> queryTOTList(String tbid) {
		return mapper.queryTOTList(tbid);
	}

	@Override
	public void deleteTOT(String id) {
		mapper.deleteTOT(id);
	}

	@Override
	public void saveAupdateTBBT(Map<String, Object> map, Map<String, Object> map2) {
		List<OperationSettlementRule> list = mapper.findByCBID(map.get("contract_id").toString());
		if(list.size()==0){
			mapper.saveMain(map);
		}else{
			mapper.updateMain(map);
		}
		mapper.saveAupdateTBBT(map2);
	}

	@Override
	public List<Map<String, Object>> queryTBBTList(String tbid) {
		return mapper.queryTBBTList(tbid);
	}

	@Override
	public void deleteTBBT(String id) {
		mapper.deleteTBBT(id);
	}

	@Override
	public void saveAupdateBTBD(Map<String, Object> map, Map<String, Object> map2) {
		List<OperationSettlementRule> list = mapper.findByCBID(map.get("contract_id").toString());
		if(list.size()==0){
			mapper.saveMain(map);
		}else{
			mapper.updateMain(map);
		}
		mapper.saveAupdateBTBD(map2);
	}

	@Override
	public List<Map<String, Object>> queryBTBDList(String tbid) {
		return mapper.queryBTBDList(tbid);
	}

	@Override
	public void deleteBTBD(String id) {
		mapper.deleteBTBD(id);
	}

	@Override
	public void saveAupdateBTBD2(Map<String, Object> map, Map<String, Object> map2) {
		List<OperationSettlementRule> list = mapper.findByCBID(map.get("contract_id").toString());
		if(list.size()==0){
			mapper.saveMain(map);
		}else{
			mapper.updateMain(map);
		}
		mapper.saveAupdateBTBD2(map2);
	}

	@Override
	public List<Map<String, Object>> queryBTBD2List(String tbid) {
		return mapper.queryBTBD2List(tbid);
	}

	@Override
	public void deleteBTBD2(String id) {
		mapper.deleteBTBD2(id);
	}

	@Override
	public void saveAupdateZZFWF(Map<String, Object> map, Map<String, Object> map2) {
		List<OperationSettlementRule> list = mapper.findByCBID(map.get("contract_id").toString());
		if(list.size()==0){
			mapper.saveMain(map);
		}else{
			mapper.updateMain(map);
		}
		mapper.saveAupdateZZFWF(map2);
	}

	@Override
	public List<Map<String, Object>> queryZZFWFList(String tbid) {
		return mapper.queryZZFWFList(tbid);
	}

	@Override
	public void deleteZZFWF(String id) {
		mapper.deleteZZFWF(id);
	}

	@Override
	public void saveAupdateTBBTs(Map<String, Object> map, Map<String, Object> map2) {
		List<OperationSettlementRule> list = mapper.findByCBID(map.get("contract_id").toString());
		if(list.size()==0){
			mapper.saveMain(map);
		}else{
			mapper.updateMain(map);
		}
		mapper.saveAupdateTBBTs(map2);
	}

	@Override
	public List<Map<String, Object>> queryTBBTLists(String tbid) {
		return mapper.queryTBBTLists(tbid);
	}

	@Override
	public void deleteTBBTs(String id) {
		mapper.deleteTBBTs(id);
	}

	@Override
	public void saveAupdateTBBTss(Map<String, Object> map, Map<String, Object> map2) {
		List<OperationSettlementRule> list = mapper.findByCBID(map.get("contract_id").toString());
		if(list.size()==0){
			mapper.saveMain(map);
		}else{
			mapper.updateMain(map);
		}
		mapper.saveAupdateTBBTss(map2);
	}

	@Override
	public List<Map<String, Object>> queryTBBTListss(String tbid) {
		return mapper.queryTBBTListss(tbid);
	}

	@Override
	public void deleteTBBTss(String id) {
		mapper.deleteTBBTss(id);
	}

}
