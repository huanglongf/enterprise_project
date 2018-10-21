package com.jumbo.wms.manager.warehouse;

import java.util.List;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.warehouse.CreatePickingListSql;
import com.jumbo.wms.model.warehouse.ShopStoreInfo;

/**
 * 
 * @author jinlong.ke
 * 
 */
public interface ShopStoreInfoManager extends BaseManager {

    List<ShopStoreInfo> getAllShopStore(String code);

    void deleteShopStoreById(Long id);

    void editShopStoreByCode(ShopStoreInfo ssi);

    void addNewShopStore(ShopStoreInfo ssi);

    List<CreatePickingListSql> getAllRuleName(Long ouId, String modeName);

}
