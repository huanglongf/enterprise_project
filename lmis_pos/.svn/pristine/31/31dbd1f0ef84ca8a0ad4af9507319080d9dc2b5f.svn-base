package com.lmis.pos.whsUser.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.lmis.framework.baseDao.BaseMapper;
import com.lmis.pos.whsUser.model.PosWhsUser;

/** 
 * @ClassName: PosWhsUserMapper
 * @Description: TODO(仓库用户DAO映射接口)
 * @author codeGenerator
 * @date 2018-05-29 16:31:04
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface PosWhsUserMapper<T extends PosWhsUser> extends BaseMapper<T> {

    /**
     * @param t
     * @return
     */
    List<Map<String,Object>> whsUserSearch(T t);

    /**
     * @return
     */
    List<Map<String,Object>> getAllUser();
	
}
