package com.bt.wms.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.wms.model.Container;
import com.bt.wms.model.Cut;
import com.bt.wms.model.ErrorLog;
import com.bt.wms.model.LowerRecord;
import com.bt.wms.model.UpperRecord;
import com.bt.wms.utils.QueryParameter;

public interface RecordMapper {
	
	/** 
	* @Title: findByContainerCode 
	* @Description: TODO(根据容器编号查询容器信息) 
	* @param @param code
	* @param @return    设定文件 
	* @return Container    返回类型 
	* @throws 
	*/
	public List<Container> findByContainerCode(Container container);
	
	/** 
	* @Title: insertContainer 
	* @Description: TODO(插入容器信息) 
	* @param @param container    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void insertContainer(Container container);
	
	/** 
	* @Title: updateContainer 
	* @Description: TODO(修改容器信息) 
	* @param @param container    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void updateContainer(Container container);
	
	/** 
	* @Title: insertLower 
	* @Description: TODO(新增下架记录) 
	* @param @param lower    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void insertLower(LowerRecord lower);
	
	/** 
	* @Title: findByLowerRecord 
	* @Description: TODO(查询下架记录) 
	* @param @param lower
	* @param @return    设定文件 
	* @return List<LowerRecord>    返回类型 
	* @throws 
	*/
	public List<LowerRecord> findByLowerRecord(LowerRecord lower);
	
	/** 
	* @Title: insertUpper 
	* @Description: TODO(新增上架记录) 
	* @param @param upper    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void insertUpper(UpperRecord upper);

	/** 
	* @Title: findByUppserRecord 
	* @Description: TODO(查询上架记录) 
	* @param @param lower
	* @param @return    设定文件 
	* @return List<UpperRecord>    返回类型 
	* @throws 
	*/
	public List<UpperRecord> findByUpperRecord(UpperRecord lower);
	
	/** 
	* @Title: findLowerRecordNumber 
	* @Description: TODO(SKU下架数量) 
	* @param @param lower_bat_id
	* @param @param upper_bat_id
	* @param @param sku
	* @param @return    设定文件 
	* @return List<Map<String,String>>    返回类型 
	* @throws 
	*/
	public List<Cut> findLowerRecordNumber(@Param("lower_bat_id")String lower_bat_id,@Param("upper_bat_id")String upper_bat_id,@Param("sku")String sku);
	
	public Integer countContainer(QueryParameter queryParam);
	
	public List<Map<String, Object>> queryContainer(QueryParameter queryParam);
	

	public Integer countRecord(QueryParameter queryParam);
	
	public List<Map<String, Object>> queryRecord(QueryParameter queryParam);

	public Integer countRecordCut(QueryParameter queryParam);

	public List<Map<String, Object>> queryRecordCut(QueryParameter queryParam);

	public Integer countLog(QueryParameter queryParam);

	public List<Map<String, Object>> query_log(QueryParameter queryParam);

	public List<Map<String, Object>> queryCY(@Param("id")int id);
	
	public List<Map<String, Object>> queryLCY(@Param("id")int id);
	
	public List<Map<String, Object>> query_lower_cy(@Param("id")int id);
	
	public List<Map<String, Object>> query_upper_cy(@Param("id")int id);
	
	public void insertlog(ErrorLog error);

	public List<Map<String, Object>> queryC(@Param("code")String code,@Param("type")String type);
	public List<Map<String, Object>> queryB(@Param("container_code")String container_code,@Param("stime")String stime,@Param("etime")String etime,@Param("type")String type,@Param("create_user")String create_user,@Param("location")String location,@Param("sku")String sku);

	public List<Map<String, Object>> queryD(@Param("container_code")String container_code,@Param("stime")String stime,@Param("etime")String etime,@Param("type")String type,@Param("create_user")String create_user,@Param("location")String location,@Param("sku")String sku);
	public List<Map<String, Object>> queryE(@Param("container_code")String container_code,@Param("stime")String stime,@Param("etime")String etime,@Param("create_user")String create_user,@Param("sku")String sku);
	

	public void insertLowers(List<LowerRecord> lower);
	public void insertUppers(List<UpperRecord> upper);
}
