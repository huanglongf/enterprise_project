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

import com.jumbo.wms.model.warehouse.SfNextMorningConfig;

/**
 * 
 * @author jinlong.ke
 * @date 2016年4月6日下午8:10:30
 */
@Transactional
public interface SfNextMorningConfigDao extends GenericEntityDao<SfNextMorningConfig, Long> {
    /**
     * 
     * @param start
     * @param pageSize
     * @param id
     * @param sorts
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<SfNextMorningConfig> findSfNextMorningConfigByOuId(int start, int pageSize, @QueryParam("ouId") Long id, Sort[] sorts, BeanPropertyRowMapper<SfNextMorningConfig> beanPropertyRowMapper);

    /**
     * 
     * @param id
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<SfNextMorningConfig> findSfNextMorningConfigListByOuId(@QueryParam("ouId") Long id, BeanPropertyRowMapper<SfNextMorningConfig> beanPropertyRowMapper);

    /**
     * 删除制定仓库SF次晨达区域配置
     * 
     * @param ouId
     */
    @NativeUpdate
    void deleteSfNextMorningConfigByOuId(@QueryParam("ouId") Long ouId);

}
