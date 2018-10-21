package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.DeliveryChangeConfigure;

@Transactional
public interface DeliveryChangeConfigureDao extends GenericEntityDao<DeliveryChangeConfigure, Long> {

    /**
     * 根据初始物流商和目标物流商查找物流变更配置信息
     * 
     * @param lpcode
     * @param newLpcode
     * @return
     */
    @NativeQuery
    List<DeliveryChangeConfigure> getDCCByLpcode(@QueryParam("lpcode") String lpcode, @QueryParam("newLpcode") String newLpcode, RowMapper<DeliveryChangeConfigure> beanPropertyRowMapper);
}
