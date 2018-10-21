package com.jumbo.wms.manager.inventorySnapshot;

import java.util.List;

import com.jumbo.wms.manager.BaseManager;

public interface InventorySnapshotManagerProxy extends BaseManager {

    void InventorySnapshotToIm(String owner, List<String> skuCode, List<String> ouCode);

}
