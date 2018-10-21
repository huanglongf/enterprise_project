package com.bt.lmis.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseService;
import com.bt.lmis.model.OperationSettlementRule;

public interface OperationSettlementRuleService<T> extends BaseService<T> {
	public void saveAupdateMain(Map<String,Object> map);
	public List<OperationSettlementRule> findByCBID(@Param("cbid")String cbid);
	
	public void saveAupdateOSF(Map<String, Object> map,Map<String, Object> map2);
	public List<Map<String, Object>>  queryOSFList(@Param("tbid")String tbid);
	public void deleteOSF(@Param("id")String id);
	
	public void saveAupdateTBT(Map<String, Object> map,Map<String, Object> map2);
	public List<Map<String, Object>>  queryTBTList(@Param("tbid")String tbid);
	public void deleteTBT(@Param("id")String id);

	public void saveAupdateTBNT(Map<String, Object> map,Map<String, Object> map2);
	public List<Map<String, Object>>  queryTBNTList(@Param("tbid")String tbid);
	public void deleteTBNT(@Param("id")String id);

	public void saveAupdateTOT(Map<String, Object> map,Map<String, Object> map2);
	public List<Map<String, Object>>  queryTOTList(@Param("tbid")String tbid);
	public void deleteTOT(@Param("id")String id);

	public void saveAupdateTBBT(Map<String, Object> map,Map<String, Object> map2);
	public List<Map<String, Object>>  queryTBBTList(@Param("tbid")String tbid);
	public void deleteTBBT(@Param("id")String id);

	public void saveAupdateBTBD(Map<String, Object> map,Map<String, Object> map2);
	public List<Map<String, Object>>  queryBTBDList(@Param("tbid")String tbid);
	public void deleteBTBD(@Param("id")String id);

	public void saveAupdateBTBD2(Map<String, Object> map,Map<String, Object> map2);
	public List<Map<String, Object>>  queryBTBD2List(@Param("tbid")String tbid);
	public void deleteBTBD2(@Param("id")String id);
	
	public void saveAupdateZZFWF(Map<String,Object> map,Map<String,Object> map2);
	public List<Map<String, Object>> queryZZFWFList(@Param("tbid")String tbid);
	public void deleteZZFWF(@Param("id")String id);

	public void saveAupdateTBBTs(Map<String, Object> map,Map<String, Object> map2);
	public List<Map<String, Object>>  queryTBBTLists(@Param("tbid")String tbid);
	public void deleteTBBTs(@Param("id")String id);
	

	public void saveAupdateTBBTss(Map<String, Object> map,Map<String, Object> map2);
	public List<Map<String, Object>>  queryTBBTListss(@Param("tbid")String tbid);
	public void deleteTBBTss(@Param("id")String id);
}
