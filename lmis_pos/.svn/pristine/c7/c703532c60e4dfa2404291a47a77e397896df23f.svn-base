package com.lmis.pos.whsOutPlan.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.lmis.framework.baseDao.BaseMapper;
import com.lmis.pos.common.dao.BaseExcelMapper;
import com.lmis.pos.whsOutPlan.model.PosWhsOutPlan;
import com.lmis.pos.whsOutPlan.model.PosWhsOutPlanLog;

/** 
 * @ClassName: PosWhsOutPlanMapper
 * @Description: TODO(仓库出库计划DAO映射接口)
 * @author codeGenerator
 * @date 2018-05-29 15:13:58
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface PosWhsOutPlanMapper<T extends PosWhsOutPlan> extends BaseExcelMapper<T> {

    String uuid();

    void createBatchLog(@Param("list")List<PosWhsOutPlanLog> list,@Param("isdisabled") boolean b);

    void executeSelect(T t,String startDate,String endDate);

    List selectAllocate(T t);

    List retrieveLog(PosWhsOutPlanLog posWhsOutPlanLog);

    int logicalDeleteLog(PosWhsOutPlanLog posWhsOutPlanLog);

    List checkPosWhsList();

    List<Map<String,Object>> findSkutypeOutrate(String whsCode);

    List<Map<String, Object>> findWhsOutrate();

    List selectPlanBatch(String formateDate);

    void deletePlanBatch(@Param("datelist")List<String> subList);
	
}
