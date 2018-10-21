package com.lmis.pos.whs.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.lmis.pos.common.dao.BaseExcelMapper;
import com.lmis.pos.whs.model.PosWhsSchedule;

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
public interface PosWhsScheduleMapper<T extends PosWhsSchedule> extends BaseExcelMapper<T> {
	List<T> query(T t);
	
	int deleteSchedule(T t);
	
	int saveSchedule(List<Map<String, Object>> list);
	
	int saveScheduleLog(List<Map<String, Object>> list);
	
	int selectByBatchId(String batchId);
	
	int updateRepeatSts(Map<String, Object> map);
	
}
