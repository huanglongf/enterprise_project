package com.jumbo.wms.manager.vmi.ckData;

import java.io.File;

import com.jumbo.wms.manager.BaseManager;

public interface CKParseDataManager extends BaseManager {
    
    String SHOP_CODE_CK = "1charleskeith旗舰店";

    void executeIBFeedBackForData();

    boolean parseProductData(File productFile, String bakDir);

    boolean parseInventoryData(File inventoryFile, String bakDir);

    boolean parseInBoundData(File inBoundFile, String bakDir);

    boolean parseOutBoundData(File outBoundFile, String bakDir);

    void writeOutbound(String filePath);

    void writeInbound(String filePath);

    void generateInventoryCheck(String batchNum);

    boolean parseInventoryDataForInvStart(File inventoryFile, String bakDir);

}
