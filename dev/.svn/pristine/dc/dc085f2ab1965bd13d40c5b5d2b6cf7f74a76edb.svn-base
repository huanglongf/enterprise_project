package com.bt.radar.dao;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.page.QueryParameter;
import com.bt.radar.controller.form.ExpressinfoMasterQueryParam;
import com.bt.radar.model.ExpressinfoDetail;
import com.bt.radar.model.ExpressinfoMaster;
import com.bt.radar.model.ExpressinfoMasterInput;

/**
* @ClassName: ExpressinfoMasterMapper
* @Description: TODO(ExpressinfoMasterMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface ExpressinfoMasterMapper<T> extends BaseMapper<T> {

	List<T> getProduct_type(Map param);

	List<T> getWarehouse(Map param);
	
	List<T> getPhysical_Warehouse(Map param);
	
	List<T> getStore(Map param);

	List<T> findAllData(QueryParameter qr);
	
	List<T> findDetailsByOrderNo(Map param);
	
	List<T> findAlarmDetailsByOrderNo(Map param);
	
	List<T> findAlarmDetailsByOrderNo_ADV(Map param);
	
	int selectCount(QueryParameter qr);
	
	int findExpressByConditionCount(QueryParameter qr);
	
	List<T> findExpressByCondition(QueryParameter qr);

	List<T> findAllDataBy_warnCdn(QueryParameter qr);

	int  selectWarnTB(QueryParameter qr);
	
	int findAllDataBy_warnCdnCount(QueryParameter qr);

	List<T> findAllData_adv(ExpressinfoMasterQueryParam queryParam);

	List<Map<String, Object>> downLoad(ExpressinfoMasterQueryParam queryParam);

	List<Map<String, Object>> downLoadSe(ExpressinfoMasterQueryParam queryParam); 
  
	List<T> findExpressByConditionUnion(QueryParameter qr);

	int findExpressByConditionUnionCnt(QueryParameter qr);

	List<T> findExpressByWarningUnion(QueryParameter qr);

	int findExpressByWarningUnionCnt(QueryParameter qr);
	
    int findExpressByWarningUnionCnt_se(QueryParameter qr);
    
    List<T> findExpress(QueryParameter qr);
    
    List<Map<String,Object>> findDataAll(Map<String,Object>param);

	void downloadCsv(ExpressinfoMasterQueryParam queryParam);
	
	List<Map<String,Object>> findBaseInfo(QueryParameter qr);

	void addExpressMasterInput(List<ExpressinfoMasterInput> listMaster);

	void addExpressDetailsInput(List<ExpressinfoDetail> listDetail);

	void checkIsNUll(@Param("bat_id")String bat_id);

	void insertMaster(@Param("bat_id")String bat_id);

	List<Map<String,Object>> getTrbFilePath(@Param("bat_id")String bat_id);

	void insertMasterList(Map<String, String> insertParam);

	void insertDetails(@Param("bat_id")String string);

	void updateStatus(Map param);

	int insertImportMatchDetail(Map<String,Object> param);

	List<Map<String, Object>> getExpressinfoImportMatchResult(Map<String,Object> param);

	int clearMatchedData(String bat_id);
}
