package com.lmis.pos.crdOut.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.lmis.framework.baseDao.BaseMapper;
import com.lmis.pos.crdOut.model.PosCrdOut;

/** 
 * @ClassName: PosCrdOutMapper
 * @Description: TODO(不拆分CRD设置DAO映射接口)
 * @author codeGenerator
 * @date 2018-06-01 17:09:27
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface PosCrdOutMapper<T extends PosCrdOut> extends BaseMapper<T> {
	/**
	 * 根据日期查询
	 * @author xuyisu
	 * @date  2018年6月1日
	 * 
	 * @param date
	 * @return
	 */
    int getPosCrdOutByDate(@Param("date")String date);
    
    List<T> retrieveRepeatDate(T t);
}
