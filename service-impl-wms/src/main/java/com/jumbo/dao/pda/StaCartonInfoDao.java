package com.jumbo.dao.pda;

import java.util.List;

import com.jumbo.wms.model.mongodb.StaCartonInfo;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.support.BeanPropertyRowMapperExt;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author hsh10697
 */
@Transactional
public interface StaCartonInfoDao extends GenericEntityDao<StaCartonInfo, Long> {

    @NativeQuery
    List<StaCartonInfo> findByStatus(@QueryParam("status") Integer status, BeanPropertyRowMapperExt<StaCartonInfo> beanPropertyRowMapper);

}
