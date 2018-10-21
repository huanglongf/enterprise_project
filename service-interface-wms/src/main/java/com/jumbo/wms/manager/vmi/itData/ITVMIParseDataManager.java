package com.jumbo.wms.manager.vmi.itData;

import java.io.File;

public interface ITVMIParseDataManager {
    int importBrand(File file, String batch, String bakDir);

    int importDimension(File file, String batch, String bakDir);

    int importSeason(File file, String batch, String bakDir);

    int importPlu(File file, String batch, String bakDir);

    int importSKU(File file, String batch, String bakDir);

    int importPLUPrice(File file, String batch, String bakDir);

    int importSkuPrice(File file, String batch, String bakDir);

    void importProductInfo(String downDir, String bakDir, String batch);

    boolean isBom(File file);

    String getFileCharacterEnding(File file);

    boolean removeFile(File file);

    void callProcedure(String batch);
}
