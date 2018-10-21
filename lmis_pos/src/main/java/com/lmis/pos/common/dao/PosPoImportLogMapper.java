package com.lmis.pos.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.lmis.pos.common.model.PosPoImportLog;

/** 
 * @ClassName: PosPoImportLogMapper
 * @Description: TODO(Po单导入统计日志表DAO映射接口)
 * @author codeGenerator
 * @date 2018-06-06 17:08:44
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface PosPoImportLogMapper<T extends PosPoImportLog> extends BaseExcelMapper<T> {
	
	List<T> retrieveByIds(@Param("ids") List<String> ids);
	
	List<T> retrieveByGroupFileNo(T t);
	
}
