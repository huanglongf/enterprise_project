package com.jumbo.dao.warehouse;



import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.SfExpressTypeConfig;

/**
 * SF快件产品类型 DAO
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
public interface SfExpressTypeConfigDao extends GenericEntityDao<SfExpressTypeConfig, Long> {
    /**
     * 根据条件在配置中查询对应的SF快件产品类型
     * 
     * @param transTimeType
     * @param lpCode
     * @param deliveryType
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    Integer findSfExpressTypeByCondition(@QueryParam("transTimeType") int transTimeType, @QueryParam("lpCode") String lpCode, @QueryParam("deliveryType") int deliveryType, SingleColumnRowMapper<Integer> singleColumnRowMapper);

}
