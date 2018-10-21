package com.lmis.pos.common.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.lmis.framework.baseDao.BaseMapper;
import com.lmis.pos.common.model.PosPurchaseOrderDetail;

/** 
 * @ClassName: PosPurchaseOrderDetailMapper
 * @Description: TODO(NIKE-PO单主表DAO映射接口)
 * @author codeGenerator
 * @date 2018-05-30 16:28:41
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface PosPurchaseOrderDetailMapper<T extends PosPurchaseOrderDetail> extends BaseMapper<T> {
	
	List<Map<String, Object>> getQtySumByPo(@Param("isSize") String isSize, @Param("polist") List<String> po);
	
}
