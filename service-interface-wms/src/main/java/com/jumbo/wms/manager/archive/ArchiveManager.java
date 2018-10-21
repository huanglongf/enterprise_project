package com.jumbo.wms.manager.archive;

import com.jumbo.wms.manager.BaseManager;

public interface ArchiveManager extends BaseManager {
    void createConfirmOrder(int sum);
    
    
    void createOutOrder(int sum);

    
}
