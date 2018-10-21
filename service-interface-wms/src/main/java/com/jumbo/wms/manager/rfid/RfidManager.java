package com.jumbo.wms.manager.rfid;

import java.util.List;
import java.util.Map;

import com.jumbo.wms.manager.BaseManager;

public interface RfidManager extends BaseManager {

    /**
     * 保存rfid并记录日志
     * @param barCode
     * @param rfids
     */
    Map<String,Object> saveRfidAndLog(String barCode, List<String> rfids);
    String createRfid(String barcode);
}
