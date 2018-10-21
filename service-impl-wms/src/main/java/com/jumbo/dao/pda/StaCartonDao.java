package com.jumbo.dao.pda;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.mongodb.StaCarton;
import com.jumbo.wms.model.warehouse.StaCartonCommand;


/**
 * 
 * @author lizaibiao
 * 
 */

@Transactional
public interface StaCartonDao extends GenericEntityDao<StaCarton, Long> {
    /**
     * 根据code获取 StaCarton实体
     */
    @NativeQuery(model = StaCarton.class)
    StaCarton getStaCartonByCode(@QueryParam("code") String code, @QueryParam("ouId") Long ouId, BeanPropertyRowMapperExt<StaCarton> r);

    @NativeQuery(model = StaCarton.class)
    StaCarton getStaCartonByCode1(@QueryParam("code") String code, @QueryParam("ouId") Long ouId, BeanPropertyRowMapperExt<StaCarton> r);

    @NativeQuery
    List<StaCarton> findStaCartonByStaId(@QueryParam("staId") Long staId, @QueryParam("ouId") Long ouId, BeanPropertyRowMapperExt<StaCarton> beanPropertyRowMapper);

    @NativeQuery
    List<StaCarton> findStaCartonByStaId1(@QueryParam("staId") Long staId, @QueryParam("ouId") Long ouId, BeanPropertyRowMapperExt<StaCarton> beanPropertyRowMapper);

    @NativeUpdate
    void updateStatusById(@QueryParam("idList") List<Long> list);

    @NativeUpdate
    int updateTagByCode(@QueryParam("tag") String tag, @QueryParam("code") String code, @QueryParam("carId") Long carId);


    @NativeQuery(pagable = true)
    Pagination<StaCartonCommand> queryStaCartonList(int start, int size, @QueryParam("staId") Long staId, RowMapper<StaCartonCommand> rowMapper, Sort[] sorts);



    @NativeQuery
    StaCarton findStaCartonByStaIdAndOuId(@QueryParam("staId") Long staId, @QueryParam("ouId") Long ouId, BeanPropertyRowMapperExt<StaCarton> beanPropertyRowMapper);

}
