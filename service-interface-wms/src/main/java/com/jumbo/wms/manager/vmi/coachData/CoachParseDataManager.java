package com.jumbo.wms.manager.vmi.coachData;

import java.io.File;

import loxia.support.excel.ReadStatus;

import com.jumbo.wms.manager.BaseManager;

public interface CoachParseDataManager extends BaseManager {

    int SHEET_0 = 1;
    int SHEET_1 = 2;

    boolean coachParseProductImport(File coachfile, String bakDir);

    boolean coachParsePriceImport(File priceFile, String bakDir);

    boolean parseCoachProductData(File outBoundFile, String bakDir);

    ReadStatus generateCoachInventoryImport(File coachFile, Long shopId, String slipCode);

    void receiveCoachProductByMq(String message);

    void receiveCoachPriceByMq(String message);

    void coachPriceToPriceLog();

}
