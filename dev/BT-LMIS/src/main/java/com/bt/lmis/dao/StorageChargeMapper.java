package com.bt.lmis.dao;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.model.StorageCharge;

/**
* @ClassName: StorageChargeMapper
* @Description: TODO(StorageChargeMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface StorageChargeMapper<T> extends BaseMapper<T> {

	/** 
	* @Title: findBySSCID 
	* @Description: TODO(根据合同ID查询仓储费) 
	* @param @param id
	* @param @return    设定文件 
	* @return StorageCharge    返回类型 
	* @throws 
	*/
	public List<StorageCharge> findByCBID(@Param("cbid")String cbid);
	public List<StorageCharge> findByID(@Param("cbid")String cbid);

	public List<StorageCharge> findBySSCID(@Param("cbid")String cbid);
	/** 
	* @Title: deleteByCBID 
	* @Description: TODO(根据合同删除仓储费主数据) 
	* @param @param cbid    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void deleteByCBID(@Param("id")String cbid);
	
	/** 
	* @Title: selectSSCById 
	* @Description: TODO(根据主键查询仓储费) 
	* @param @param id
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws 
	*/
	public List<Map<String, Object>> selectSSCById(@Param("id")int id);
	
	/** 
	* @Title: queryWAS 
	* @Description: TODO(根据仓库，区域，商品类型,仓储费规则 查询仓储费规则是否存在) 
	* @param @param ssc_whs_id
	* @param @param ssc_area_id
	* @param @param ssc_item_type
	* @param @param ssc_sc_type
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws 
	*/
	public List<StorageCharge> queryWAS(StorageCharge sc);
	
	public void deleteSAAByCBID(@Param("cbid")String cbid);
	public void deleteSASTByCBID(@Param("cbid")String cbid);
	public void deleteSATByCBID(@Param("cbid")String cbid);
	public void deleteSAVByCBID(@Param("cbid")String cbid);
	public void deleteSAVCByCBID(@Param("cbid")String cbid);
	public void deleteSTAByCBID(@Param("cbid")String cbid);
	public void deleteSTSTByCBID(@Param("cbid")String cbid);
	public void deleteSTTByCBID(@Param("cbid")String cbid);
	public void deleteSTVByCBID(@Param("cbid")String cbid);
	public void deleteSTVCByCBID(@Param("cbid")String cbid);
	
	public void deleteSAAByID(@Param("id")String id);
	public void deleteSASTByID(@Param("id")String id);
	public void deleteSATByID(@Param("id")String id);
	public void deleteSAVByID(@Param("id")String id);
	public void deleteSAVyID(@Param("id")String id);
	public void deleteSTAByID(@Param("id")String id);
	public void deleteSTSTByID(@Param("id")String id);
	public void deleteSTTByID(@Param("id")String id);
	public void deleteSTVByID(@Param("id")String id);
	public void deleteSTVyID(@Param("id")String id);
}
