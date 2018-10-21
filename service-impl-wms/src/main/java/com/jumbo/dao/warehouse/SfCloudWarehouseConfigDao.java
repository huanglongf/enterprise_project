package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.SfCloudWarehouseConfig;
import com.jumbo.wms.model.warehouse.SfCloudWarehouseConfigCommand;

/**
 * 
 * @author jinlong.ke
 * @date 2016年4月6日下午8:10:30
 */
@Transactional
public interface SfCloudWarehouseConfigDao extends GenericEntityDao<SfCloudWarehouseConfig, Long> {

    /**
     * @param ouId
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    SfCloudWarehouseConfig findCloudConfigByOu(@QueryParam("ouId") Long ouId, @QueryParam("cId") Long cId, @QueryParam("province") String province, @QueryParam("city") String city, BeanPropertyRowMapper<SfCloudWarehouseConfig> beanPropertyRowMapper);


    @NativeQuery(pagable = true)
    Pagination<SfCloudWarehouseConfigCommand> findCloudConfigByOuByPage(int start, int pagesize, @QueryParam("ouId") Long ouId, @QueryParam("cId") Long cId, RowMapper<SfCloudWarehouseConfigCommand> r, Sort[] sorts);


    @NativeQuery
    List<SfCloudWarehouseConfigCommand> findCloudConfigByOuCid(@QueryParam("ouId") Long ouId, @QueryParam("cId") Long cId, BeanPropertyRowMapper<SfCloudWarehouseConfigCommand> beanPropertyRowMapper);

    @NativeUpdate
    void deleteSfConfigByOuIdCid(@QueryParam("ouId") Long ouId, @QueryParam("cId") Long cId);

}
