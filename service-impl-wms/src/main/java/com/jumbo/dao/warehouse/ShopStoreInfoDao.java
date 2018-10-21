package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.ShopStoreInfo;

/**
 * 门店处理DAO
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
public interface ShopStoreInfoDao extends GenericEntityDao<ShopStoreInfo, Long> {
    /**
     * 查询门店信息
     * 
     * @param code
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<ShopStoreInfo> findAllShopStore(@QueryParam("code") String code, BeanPropertyRowMapper<ShopStoreInfo> beanPropertyRowMapper);

    /**
     * 
     * @param code
     * @param name
     * @param receiver
     * @param address
     * @param telephone
     * @param country
     * @param province
     * @param city
     * @param district
     */
    @NativeUpdate
    void editShopStoreByCode(@QueryParam("code") String code, @QueryParam("name") String name, @QueryParam("receiver") String receiver, @QueryParam("address") String address, @QueryParam("telephone") String telephone,
            @QueryParam("country") String country, @QueryParam("province") String province, @QueryParam("city") String city, @QueryParam("district") String district);

    @NamedQuery
    ShopStoreInfo getShopStoreInfoByCode(@QueryParam("code") String code);


}
