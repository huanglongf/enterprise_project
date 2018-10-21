package com.jumbo.wms.manager.task.archive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.archive.ArchiveManager;

@Service("archiveTask")
public class ArchiveTaskImpl extends BaseManagerImpl implements ArchiveTask {
    /**
     * 
     */
    private static final long serialVersionUID = -6812923122332666758L;
    @Autowired
    ArchiveManager archiveManager;

    @Override
    public void archiveCreateSta() {
        log.info("t_wh_confirm_order==============begin");
        archiveManager.createConfirmOrder(5);

    }

    @Override
    public void archiveOutSta() {
        archiveManager.createOutOrder(5);

    }

}
