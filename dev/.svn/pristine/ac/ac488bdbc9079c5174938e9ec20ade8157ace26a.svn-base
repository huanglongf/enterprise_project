package com.bt.lmis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.model.ExpertBill;

/**
 * @Title:DifferenceMatchingMapper
 * @Description: TODO  
 * @author Ian.Huang 
 * @date 2017年2月22日下午3:49:27
 */
public interface DifferenceMatchingMapper<T> {
	
	/**
	 * 
	 * @Description: TODO
	 * @param fileName
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2017年3月24日下午9:23:53
	 */
	Integer judgeFileNameDuplicate(@Param("fileName")String fileName);
	
	/**
	 * 
	 * @Description: TODO
	 * @param id
	 * @return: ExpertBill  
	 * @author Ian.Huang 
	 * @date 2017年2月22日下午4:38:02
	 */
	ExpertBill selectById(@Param("id")Integer id);
	/**
	 * 
	 * @Description: TODO
	 * @param bat_id
	 * @return: List<Map<String, Object>>
	 * @author Ian.Huang 
	 * @date 2017年2月22日下午3:51:33
	 */
	List<Map<String, Object>> matchingEMSDifference(@Param("bat_id")String bat_id);
	/**
	 * 
	 * @Description: TODO
	 * @param bat_id
	 * @return: List<Map<String, Object>>
	 * @author Ian.Huang 
	 * @date 2017年2月22日下午3:51:36
	 */
	List<Map<String, Object>> matchingSTODifference(@Param("bat_id")String bat_id);
	/**
	 * 
	 * @Description: TODO
	 * @param bat_id
	 * @return: List<Map<String, Object>>
	 * @author Ian.Huang 
	 * @date 2017年2月22日下午3:51:38
	 */
	List<Map<String, Object>> matchingYTODifference(@Param("bat_id")String bat_id);
	/**
	 * 
	 * @Description: TODO
	 * @param bat_id
	 * @return: List<Map<String, Object>>
	 * @author Ian.Huang 
	 * @date 2017年2月22日下午3:51:41
	 */
	List<Map<String, Object>> matchingSFDifference(@Param("bat_id")String bat_id);

	List<Map<String, Object>> matchingSFDifferenceSettle(Map param);
	
	Map<String, Object> getReturnData(Map tableContent);

	void createReturnTab(@Param("bat_id")String bat_id);

	List<Map<String, Object>> returnTableContent(@Param("bat_id")String bat_id);

	List<Map<String, Object>> returnTableContentCYPP(@Param("bat_id")String bat_id);
	
	void createWarehouseTab(@Param("bat_id")String bat_id);
	
	void createWarehouseTabCYPP(@Param("bat_id")String bat_id);

	List<Map<String, Object>> warehouseTableContent(String bat_id);

	void deleteReturnTab(@Param("bat_id")String bat_id);

	void deleteReturnTabCYPP(@Param("bat_id")String bat_id);
	
	void deleteWarehouseTab(@Param("bat_id")String bat_id);

	void deleteWarehouseTabCYPP(@Param("bat_id")String bat_id);
	
	List<Map<String, Object>> getIdByBatId(@Param("bat_id")String batch_id);
	/*
	 * SF差异匹配
	 */
	List<Map<String, Object>> getIdByBatIdCYPP(@Param("bat_id")String batch_id);
	
	List<String> test(Map param);

	void matchingSFDifferenceSettle_insert4(Map param);

	void matchingSFDifferenceSettle_insert3(Map param);

	void matchingSFDifferenceSettle_insert2(Map param);

	void matchingSFDifferenceSettle_insert0(Map param);

	void matchingSFDifferenceSettle_insert1(Map param);

	void collect(@Param("bat_id")String batch_id);

	void deleteResult1(@Param("bat_id")String bat_id);

	void deleteResult0(@Param("bat_id")String bat_id);

	void deleteResult00(@Param("bat_id")String bat_id);
	
	void deleteResult2(@Param("bat_id")String bat_id);

	void deleteResult3(@Param("bat_id")String bat_id);

	void deleteResult4(String string);

	List<Map<String, Object>> matchingSF(Map<String, String> param);

	List<Map<String, Object>> matchingSFun(Map<String, String> param);

	void InsertWarehouseTab(Map<String, Object> pp);
	
	void InsertWarehouseTabCYPP(Map<String, Object> pp);

	void createReturn(@Param("bat_id")String bat_id);

	void insertTable(@Param("bat_id")String bat_id);

	void updateWarning(Map param);

	void  createRow(@Param("bat_id")String bat_id);

	void updateReason(@Param("bat_id")String bat_id);

	
}