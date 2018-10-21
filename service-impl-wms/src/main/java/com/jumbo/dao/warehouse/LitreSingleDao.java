package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.LitreSingle;

@Transactional
public interface LitreSingleDao extends GenericEntityDao<LitreSingle, Long> {
    @NativeQuery
    public List<LitreSingle> findmainWarehouseOwner(@QueryParam(value = "ouId") Long ouId, @QueryParam(value = "owner") String owner, @QueryParam(value = "province") String province,
                @QueryParam(value = "city") String city,@QueryParam(value = "district") String district, BeanPropertyRowMapper<LitreSingle> beanPropertyRowMapper);
    
    @NativeQuery(pagable = true)
    public Pagination<LitreSingle> findNIKETodaySendConfigByOuId(int start, int pageSize, @QueryParam("ouId") Long id, Sort[] sorts, BeanPropertyRowMapper<LitreSingle> beanPropertyRowMapper);
    
    @NativeUpdate
    public void deleteNikeTodaySendConfigByOuId(@QueryParam("ouId") Long id);

}
