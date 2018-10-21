package com.jumbo.wms.manager.vmi.vmiInventory;

import java.util.List;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.warehouse.BiChannelCommand;

public interface VmiInventoryManager extends BaseManager {

    List<String> getBiChannelByVmiCode(String vmicode);

    void insertVmiInventory(BiChannelCommand bi, List<String> ownerList);

    /**
     * ck 根据品牌定制获取相关数据插入vmi库存快照表
     */
    void insertCKVmiInventory(String defaultCode, List<String> ownerList);

    void uploadVmiInventoryToHub(String vmiCode);
}
