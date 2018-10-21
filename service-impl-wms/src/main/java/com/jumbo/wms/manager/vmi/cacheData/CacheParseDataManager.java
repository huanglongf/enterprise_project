package com.jumbo.wms.manager.vmi.cacheData;

import java.util.Date;
import java.util.List;

import cn.baozun.bh.connector.cch.model.Sales;

import com.jumbo.wms.manager.BaseManager;

public interface CacheParseDataManager extends BaseManager {

    void receiveCacheProductByMq(String message);

    void receiveCacheMarkdownDetailByMq(String message);

    void receiveCacheStockTransferInByMq(String message);

    List<String> getParcelCodeWithNoSta(String vmiCode);

    void generateInboundSta(String parcelCode);
    
    void generateInboundStaSql(String parcelCode);

    Sales getSSSalesFile(Long shopId, String shopCode, Date date);
}
