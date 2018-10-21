package com.bt.lmis.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseService;
import com.bt.lmis.model.AllArea;
import com.bt.lmis.model.AllSingltTray;
import com.bt.lmis.model.AllTray;
import com.bt.lmis.model.AllVolume;
import com.bt.lmis.model.AllVolumeCalculation;
import com.bt.lmis.model.StorageCharge;
import com.bt.lmis.model.TotalArea;
import com.bt.lmis.model.TotalSingltTray;
import com.bt.lmis.model.TotalTray;
import com.bt.lmis.model.TotalVolume;
import com.bt.lmis.model.TotalVolumeCalculation;

public interface StorageChargeService<T> extends BaseService<T> {

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
	* @throws Exception  
	* @Title: saveAreaCGBF 
	* @Description: TODO(超过部分占用面积) 
	* @param @param ta
	* @param @param scØ    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void saveAreaCGBF(TotalArea ta,T sc) throws Exception;
	public void updateAreaCGBF(TotalArea ta,T sc) throws Exception;

	/**
	* @throws Exception  
	* @Title: saveAreaCGBF 
	* @Description: TODO(总占用面积) 
	* @param @param ta
	* @param @param scØ    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void saveAreaALL(AllArea aa,T sc) throws Exception;
	public void updateAreaALL(AllArea aa, T sc) throws Exception;

	/**
	* @throws Exception  
	* @Title: saveAreaCGBF 
	* @Description: TODO(超过部分占用体积阶梯) 
	* @param @param ta
	* @param @param scØ    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void saveVolumeCGBF(TotalVolume tv,T sc) throws Exception;
	public void updateVolumeCGBF(TotalVolume tv,T sc) throws Exception;
	/**
	* @throws Exception  
	* @Title: saveAreaCGBF 
	* @Description: TODO(总占用体积阶梯) 
	* @param @param ta
	* @param @param scØ    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void saveVolumeALL(AllVolume av,T sc) throws Exception;
	public void updateVolumeALL(AllVolume av,T sc) throws Exception;

	/** 
	* @Title: saveVolumeALL 
	* @Description: TODO(超过部分按商品体积推算) 
	* @param @param tvc
	* @param @param sc
	* @param @throws Exception    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void saveTVC(TotalVolumeCalculation tvc,T sc) throws Exception;
	public void updateTVC(TotalVolumeCalculation tvc,T sc) throws Exception;
	/** 
	* @Title: saveVolumeALL 
	* @Description: TODO(按总商品体积推算) 
	* @param @param tvc
	* @param @param sc
	* @param @throws Exception    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void saveAVC(AllVolumeCalculation tvc,T sc) throws Exception;
	public void updateAVC(AllVolumeCalculation tvc,T sc) throws Exception;
	/** 
	* @Title: saveTT 
	* @Description: TODO(超过托盘数阶梯) 
	* @param @param tt
	* @param @param sc
	* @param @throws Exception    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void saveTT(TotalTray tt,T sc) throws Exception;
	public void updateTT(TotalTray tt,T sc) throws Exception;
	/** 
	* @Title: saveAT 
	* @Description: TODO(总占用托盘数阶梯) 
	* @param @param tt
	* @param @param sc
	* @param @throws Exception    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void saveAT(AllTray at,T sc) throws Exception;
	public void updateAT(AllTray at,T sc) throws Exception;
	/** 
	* @Title: saveTST 
	* @Description: TODO(托盘反推超过部分) 
	* @param @param tst
	* @param @param sc
	* @param @throws Exception    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void saveTST(TotalSingltTray tst,T sc) throws Exception;
	public void updateTST(TotalSingltTray tst,T sc) throws Exception;
	/** 
	* @Title: saveAST 
	* @Description: TODO(托盘反推总占用) 
	* @param @param tst
	* @param @param sc
	* @param @throws Exception    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void saveAST(AllSingltTray ast,T sc) throws Exception;
	public void updateAST(AllSingltTray ast,T sc) throws Exception;
	
	/** 
	* @Title: deleteByCBID 
	* @Description: TODO(根据合同删除仓储费主数据) 
	* @param @param cbid    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void deleteByCBID(@Param("id")String id);
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
