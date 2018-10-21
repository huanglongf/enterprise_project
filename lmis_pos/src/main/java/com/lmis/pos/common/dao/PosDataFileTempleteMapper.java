package com.lmis.pos.common.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.lmis.framework.baseDao.BaseMapper;
import com.lmis.pos.common.model.PosDataFileTemplete;

/** 
 * @ClassName: PosDataFileTempleteMapper
 * @Description: TODO(下载上传excel模板统一记录表DAO映射接口)
 * @author codeGenerator
 * @date 2018-05-29 15:38:54
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface PosDataFileTempleteMapper<T extends PosDataFileTemplete> extends BaseMapper<T> {
	
}
