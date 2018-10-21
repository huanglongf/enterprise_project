package com.lmis.pos.whsOutPlan.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.lmis.pos.common.dao.BaseExcelMapper;
import com.lmis.pos.whsOutPlan.model.PosWhsOutPlanLog;

/** 
 * @ClassName: PosWhsOutPlanLogMapper
 * @Description: TODO(仓库出库计划导入日志DAO映射接口)
 * @author codeGenerator
 * @date 2018-05-29 21:41:43
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface PosWhsOutPlanLogMapper<T extends PosWhsOutPlanLog> extends BaseExcelMapper<T> {
	
}
