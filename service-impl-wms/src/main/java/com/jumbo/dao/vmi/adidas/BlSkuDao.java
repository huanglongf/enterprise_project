package com.jumbo.dao.vmi.adidas;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.BlSku;
import com.jumbo.wms.model.warehouse.BlSkuCommand;

@Transactional
public interface BlSkuDao extends GenericEntityDao<BlSku, Long> {


    @NativeQuery
    BlSku findSingelSkuCdBarcodeOrBarcode(@QueryParam("code") String code, @QueryParam("barcode") String barcode, RowMapper<BlSku> r);

    /**
     * 查询未创建BlSku
     * 
     * @param r
     * @return
     */
    @NativeQuery(model = BlSku.class)
    List<BlSkuCommand> findNoBlSku(@QueryParam("barCode") String barCode, RowMapper<BlSkuCommand> r);

    /**
     * 更新状态 status 0
     */
    @NativeUpdate
    int updateBlSkuStatus(@QueryParam("id") Long id);

}
