package com.jumbo.wms.manager.vmi.levisData;

import com.jumbo.wms.manager.BaseManager;

public interface LevisChildrenManager extends BaseManager {
    
    public void inBoundTransferInBoundWriteToFile(String localDir);
    
    //levis永兴退仓反馈
    void vmiReturnReceiveWriteToFile(String localDir);

}
