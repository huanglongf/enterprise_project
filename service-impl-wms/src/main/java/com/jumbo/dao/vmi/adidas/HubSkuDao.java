package com.jumbo.dao.vmi.adidas;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.BlSku;
import com.jumbo.wms.model.warehouse.HubSku;

@Transactional
public interface HubSkuDao extends GenericEntityDao<HubSku, Long> {


    @NativeQuery
    BlSku findSingelSkuCdBarcodeOrBarcode(@QueryParam("code") String code, @QueryParam("barcode") String barcode, RowMapper<BlSku> r);

    /**
     * 查询未创建hubSku
     * 
     * @param r
     * @return
     */
    @NativeQuery(model = HubSku.class)
    List<HubSku> findNoHubSku(@QueryParam("brand") String brand, RowMapper<HubSku> r);

    /**
     * 更新状态 status 0
     */
    @NativeUpdate
    int updateHubSkuStatus(@QueryParam("id") Long id);

    @NativeQuery(model = HubSku.class)
    HubSku findOneHubSku(@QueryParam("brand") String brand, @QueryParam("skuCode") String skuCode, RowMapper<HubSku> r);


}
