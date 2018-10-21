package com.bt.lmis.service;

import com.bt.lmis.code.BaseService;
import com.bt.lmis.controller.form.DiffBilldeatilsQueryParam;
import com.bt.lmis.model.CollectionMaster;
import com.bt.lmis.model.DiffBilldeatils;
import com.bt.lmis.page.QueryResult;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.xml.sax.SAXException;

public interface DiffBilldeatilsService<T> extends BaseService<T> {

	QueryResult<DiffBilldeatils> selectMasterId(DiffBilldeatilsQueryParam queryParam);

	void insertDiff(Map<String, String> param0);
	
	void insertDiff(DiffBilldeatilsQueryParam queryParam);

	void verification(DiffBilldeatilsQueryParam param);
	
	void verification(List<String> ids);
	
    void Cancelverification(DiffBilldeatilsQueryParam param);
	
	void Cancelverification(List<String> ids);
	
	int  saveAccount(Map<String, Object> param) throws Exception;
	
	int  saveAccount(DiffBilldeatilsQueryParam queryParam) throws Exception;
	
	int  reDiff(DiffBilldeatilsQueryParam param);
	
	int  reDiff(List<String> ids);
	
	
	List<CollectionMaster> uploadByQueryParam(DiffBilldeatilsQueryParam param,String contract_id,String uuid);
	
	List<CollectionMaster> uploadByIds(String[] ids,String contract_id,String uuid);

	void deleteDiffBilldeatilsTempByAccountId(String uuid);

	void insertDiffResult(Map<String, String> param0) throws IOException, OpenXML4JException, ParserConfigurationException, SAXException;

	void getUploadeExcel(String account_id,String contract_id,String file_name);

	String upload(DiffBilldeatilsQueryParam queryParam) throws IOException;

	String uploadIds(String ids) throws IOException;

	void deleteByQuery(DiffBilldeatilsQueryParam dq);
	
	void closeBfData(String master_id);

	List<Map<String, Object>> getMonthAccount();
	
}
