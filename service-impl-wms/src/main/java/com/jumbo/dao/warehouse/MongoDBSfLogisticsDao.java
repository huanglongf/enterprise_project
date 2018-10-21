package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.MongoDBSfLogistics;

@Transactional
public interface MongoDBSfLogisticsDao extends GenericEntityDao<MongoDBSfLogistics, Long> {
    /**
     * 查询库存列表封装MongoDB对象
     * 
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    public List<MongoDBSfLogistics> findAllLogistics(BeanPropertyRowMapper<MongoDBSfLogistics> beanPropertyRowMapper);

}
