package com.lmis.pos.whsCover.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.lmis.framework.baseDao.BaseMapper;
import com.lmis.pos.whsCover.model.PosWhsCover;

/** 
 * @ClassName: PosWhsCoverMapper
 * @Description: TODO(仓库覆盖区域设置DAO映射接口)
 * @author codeGenerator
 * @date 2018-05-29 16:30:53
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface PosWhsCoverMapper<T extends PosWhsCover> extends BaseMapper<T> {

    /**
     * @param province
     * @return
     */
    int checkProvince(@Param("province")String province);

    /**
     * @param province
     * @param city
     * @return
     */
    int checkProvinceCity(Map<String,Object> map);
    
    /**
     * @param province
     * @return
     */
    String getaddrNameByCode(@Param("code")String province);
	
}
