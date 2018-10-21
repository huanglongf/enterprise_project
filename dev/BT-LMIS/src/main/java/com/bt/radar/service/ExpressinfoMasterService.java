package com.bt.radar.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.bt.lmis.code.BaseService;
import com.bt.lmis.page.QueryParameter;
import com.bt.lmis.page.QueryResult;
import com.bt.radar.controller.form.ExpressinfoMasterQueryParam;
import com.bt.radar.model.ExpressinfoMaster;

public interface ExpressinfoMasterService<T> extends BaseService<T> {
	
	List<T> getProduct_type(Map param);

	
	List<T> getWarehouse(Map param);
	
	
	List<T> getStore(Map param);
	
	
   QueryResult<T> findAllData(QueryParameter qr);
  
   
   QueryResult<T> findAllWarninData(QueryParameter qr);
           
 
   List<T> findDetailsByOrderNO(Map param);
 
   
   List<T> findAlarmDetailsByOrderNO(Map param);
 
   
   QueryResult<T>  findExpressByCondition(QueryParameter qr);
   
   
   QueryResult<T> findAllDataBy_warnCdn(QueryParameter qr);


   List<T>  getPhysical_Warehouse(Map param);


   List<T> findAlarmDetailsByOrderNO_ADV(Map em);


  QueryResult<T> findAllData_adv(ExpressinfoMasterQueryParam queryParam);

  
  String downLoad(ExpressinfoMasterQueryParam queryParam);

  
  QueryResult<T> findExpressByConditionUnion(QueryParameter qr);

  
  QueryResult<T> findExpressByWarningUnion(QueryParameter qr);


QueryResult<T> findExpressByConditionUnionNotCount(QueryParameter qr);


String uploadWaybill(Map param)throws IOException;


String getTrbFilePath(String uUID);


void uploadTransferWaybill(Map param);


void updateStatus(Map param);
  
}
