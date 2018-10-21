package com.jumbo.wms.manager.vmi.coachData;

import java.io.File;

import loxia.support.excel.ReadStatus;

import com.jumbo.wms.manager.BaseManager;

public interface CoachParseDataManagerProxy extends BaseManager {

    int SHEET_0 = 1;
    int SHEET_1 = 2;

    ReadStatus generateCoachSkuImport(File coachFile, Long shopId);

    ReadStatus generateCoachInventoryImport(File coachFile, Long shopId, String slipCode, Long ouId, Long userId);
}
