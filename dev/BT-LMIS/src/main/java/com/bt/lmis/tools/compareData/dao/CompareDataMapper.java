package com.bt.lmis.tools.compareData.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bt.common.controller.param.Parameter;
import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.tools.compareData.model.WhsCollectData;
import com.bt.lmis.tools.compareData.model.WhsPackCollectData;
import com.bt.lmis.tools.compareData.model.WhsPackData;
import com.bt.lmis.tools.compareData.model.WhsTask;
@Repository
public interface CompareDataMapper<T> extends BaseMapper<T>{

    public List<Map<String, Object>> queryCompareTask(Parameter parameter);

    public int countCompareTask(Parameter parameter);

    int deleteCompareTaskById(String id);
    
    int addCompareTask(WhsTask whsTask);
    
    int createBatchPackData(List<Map<String,Object>> list); 
    
    int createBatchCollectData(List<Map<String,Object>> list); 
    
    int createBatchPackCollectData(List<WhsPackCollectData> list);

	public List<WhsPackCollectData> comparedNoSize(Map<String, String> paramMap);

	public List<WhsPackCollectData>  comparedSize(Map<String, String> paramMap);

	public List<WhsPackCollectData>  comparedNoQtyNoSize(Map<String, String> paramMap);

	public List<WhsPackCollectData>  comparedNoQtySize(Map<String, String> paramMap);

	public List<WhsCollectData> findCollectByTaskCode(@Param("taskCode") String taskCode);

	public List<WhsPackData> findPackByTaskCode(@Param("taskCode") String taskCode);

	public int updateTaskByTaskCode(WhsTask task);

	public WhsTask findTaskByTaskCode(@Param("taskCode") String taskCode);

	public ArrayList<Map<String, Object>> exportCollectPackData(@Param("taskCode") String taskCode);
    public List<Map<String, Object>> selectFromWms(List<Object> list);

	public List<WhsPackCollectData> findErrorCollectByTaskCode(Map<String, String> paramMap);

	int deletePackDataByTaskCode(String taskCode);
	int deleteCollectDataByTaskCode(String taskCode);
	int deleteTaskByTaskCode(String taskCode);
}
