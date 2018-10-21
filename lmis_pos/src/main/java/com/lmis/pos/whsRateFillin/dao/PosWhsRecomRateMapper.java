package com.lmis.pos.whsRateFillin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.lmis.pos.common.dao.BaseExcelMapper;
import com.lmis.pos.whsRateFillin.model.PosWhsRecomRate;

/** 
 * @ClassName: PosWhsMapper
 * @Description: TODO(仓库主表DAO映射接口)
 * @author codeGenerator
 * @date 2018-05-29 13:23:45
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface PosWhsRecomRateMapper<T extends PosWhsRecomRate> extends BaseExcelMapper<T> {
	
	List<T> query(T t);
	
}
