package com.lmis.pos.common.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.lmis.pos.common.model.PosWhsAllocate;
import com.lmis.pos.skuMaster.model.PosSkuMaster;
import com.lmis.pos.whsAllocate.model.PosWhsAllocateDate;
import com.lmis.pos.soldtoRule.model.PosSoldtoRule;


/** 
 * @ClassName: PosWhsAllocateMapper
 * @Description: TODO(PO分仓结果DAO映射接口)
 * @author codeGenerator
 * @date 2018-05-30 13:26:28
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface PosWhsAllocateMapper<T extends PosWhsAllocate> extends BaseExcelMapper<T> {
	
	/**
	 * @Title: getSkuMaster
	 * @Description: TODO(根据prod_code获取主档信息)
	 * @param prodCode
	 * @return: PosSkuMaster
	 * @author: Ian.Huang
	 * @date: 2018年6月21日 下午3:57:45
	 */
	PosSkuMaster getSkuMaster(@Param("prodCode") String prodCode);
	
    /**
     * @Title: savePosWhsAllocate
     * @Description: TODO(保存拆分结果)
     * @param t
     * @return: void
     * @author: Ian.Huang
     * @date: 2018年6月5日 上午2:49:39
     */
    void savePosWhsAllocate(T t);
    
	/**
	 * @Title: deleteBatch
	 * @Description: TODO(物理批量删除)
	 * @param ids
	 * @return: int
	 * @author: Ian.Huang
	 * @date: 2018年5月31日 下午5:21:28
	 */
	int deleteBatch(@Param("ids") List<String> ids);
	
	/**
	 * @Title: getQtySum
	 * @Description: TODO(获取指定数据的商品数量汇总)
	 * @param ids
	 * @return: Integer
	 * @author: Ian.Huang
	 * @date: 2018年6月7日 上午11:24:01
	 */
	Integer getQtySum(@Param("ids") List<String> ids);
	
	/**
	 * @Title: getQtySumByPo
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param size
	 * @param po
	 * @return: Integer
	 * @author: Ian.Huang
	 * @date: 2018年6月7日 下午2:54:03
	 */
	Integer getQtySumByPo(@Param("size") String size, @Param("polist") List<String> po);
	
	/**
	 * @Title: selectInvalidPo
	 * @Description: TODO(查询无效PO单号)
	 * @param isScOccupy
	 * @param soldTo
	 * @param fileNo
	 * @return: List<String>
	 * @author: Ian.Huang
	 * @date: 2018年6月19日 下午3:15:11
	 */
	List<String> selectInvalidPo(@Param("isScOccupy") String isScOccupy, @Param("soldTolist") List<PosSoldtoRule> soldTo, @Param("fileNo") String fileNo);
	
	/**
	 * @Title: listPosWhsAllocateIds
	 * @Description: TODO(根据源PO单号查询当前有效的拆分明细ID)
	 * @param poSource
	 * @return: List<String>
	 * @author: Ian.Huang
	 * @date: 2018年6月21日 下午11:40:01
	 */
	List<String> listPosWhsAllocateIds(@Param("poSource") String poSource);
	
	/**
	 * @Title: logicalDeleteRollback
	 * @Description: TODO(逻辑删除回滚操作)
	 * @param updateBy
	 * @param ids
	 * @return: int
	 * @author: Ian.Huang
	 * @date: 2018年6月21日 下午11:39:02
	 */
	int logicalDeleteRollback(@Param("updateBy") String updateBy, @Param("ids") List<String> ids);
	
	/**
	 * 获取仓库分配比例
	 * @param t
	 * @return
	 */
    List<Map<String,String>> selectWhsOutrate(PosWhsAllocateDate t);
    
    /**
     * 获取仓库分配比例和
     * @param t
     * @return
     */
    String selectWhsPlanInPoSum(PosWhsAllocateDate t);
	
}
