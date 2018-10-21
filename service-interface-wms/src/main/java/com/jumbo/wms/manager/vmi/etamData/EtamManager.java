package com.jumbo.wms.manager.vmi.etamData;

import java.util.List;

import com.jumbo.wms.manager.BaseManager;

public interface EtamManager extends BaseManager {

    boolean skuInsertIntoDB(List<String> results);
    // void readSkuToDB();
    // boolean readSKUFileIntoDB(String localFileDir, String bakFileDir);
}
