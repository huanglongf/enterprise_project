package com.jumbo.wms.manager.vmi.ckData;

import com.jumbo.wms.manager.BaseManager;

public interface CKParseDataManagerProxy extends BaseManager {
    void executeIBFeedBack();

    void executeOBFeedBack();
}
