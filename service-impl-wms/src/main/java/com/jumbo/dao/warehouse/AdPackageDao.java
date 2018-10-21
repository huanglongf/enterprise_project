package com.jumbo.dao.warehouse;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import com.jumbo.wms.model.AdPackage;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;

public interface AdPackageDao extends GenericEntityDao<AdPackage, Long> {
    @NativeUpdate
    void deleteAdPackage(@QueryParam("ouId") Long ouId);

    @NativeQuery
    public List<AdPackage> findAdPackageByOuId(@QueryParam(value = "ouId") Long ouId, BeanPropertyRowMapper<AdPackage> beanPropertyRowMapper);

    @NativeQuery(pagable = true)
    Pagination<AdPackage> findAdPackageByOuIdPage(int start, int pageSize, @QueryParam("ouId") Long ouId, RowMapper<AdPackage> rowMapper);

    @NativeQuery
    public List<AdPackage> findAdPackageByOuIdByAdName( @QueryParam("ouId") Long ouId,BeanPropertyRowMapper<AdPackage> beanPropertyRowMapper);

}
