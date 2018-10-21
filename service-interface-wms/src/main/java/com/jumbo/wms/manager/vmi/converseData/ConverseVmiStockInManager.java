package com.jumbo.wms.manager.vmi.converseData;

import java.io.File;
import java.util.List;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.vmi.converseData.ConverseVmiStockIn;


public interface ConverseVmiStockInManager extends BaseManager {

    List<String> findCartonNoList();

    List<ConverseVmiStockIn> findConverseInstructListByCartonNo(String cartonNo);

    void generateInboundSta(String referenceNo);

    boolean parseEverGreenData(File everGreenFile, String bakDir);

    boolean parseProductInfoData(File productInfoFile, String bakDir);

    boolean parsePriceChangeData(File priceChangeFile, String bakDir);

    boolean parseStockInData(File stockInFile, String bakDir);

    boolean parseStyleData(File styleFile, String bakDir);

    boolean parseListpriceData(File listPriceFile, String bakDir);

    void changeStyleListPrice();

}
