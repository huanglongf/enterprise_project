package com.lmis.pos.whsSkutypeOutrate.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.lmis.framework.baseDao.BaseMapper;
import com.lmis.pos.whsSkutypeOutrate.model.PosWhsSkutypeOutrate;

/** 
 * @ClassName: PosWhsSkutypeOutrateMapper
 * @Description: TODO(仓库-鞋服配出库占比DAO映射接口)
 * @author codeGenerator
 * @date 2018-06-05 16:49:38
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface PosWhsSkutypeOutrateMapper<T extends PosWhsSkutypeOutrate> extends BaseMapper<T> {

	List<PosWhsSkutypeOutrate> selectPosWhsSkutypeOutrate(T t);
	
}
