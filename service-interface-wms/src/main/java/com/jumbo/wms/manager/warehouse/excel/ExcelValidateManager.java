package com.jumbo.wms.manager.warehouse.excel;

import java.io.File;
import java.io.FileNotFoundException;

import loxia.support.excel.ReadStatus;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.authorization.User;

public interface ExcelValidateManager extends BaseManager {
    static int SHEET_0 = 1;
    static int SHEET_1 = 2;
    static final String REPEATE = "REPEATE";
    static final String RIGHT = "RIGHT";
    static final String WRONG = "WRONG";

    ReadStatus importRefreshPickingListSN(File file, Long ouId, Long userId) throws FileNotFoundException;

    /**
     * 退货入库导入
     * 
     * @param file
     * @param ouId
     * @param userId
     * @return
     * @throws FileNotFoundException
     */
    ReadStatus importReturnRequestInbound(File file, Long ouId, User user) throws FileNotFoundException;
}
