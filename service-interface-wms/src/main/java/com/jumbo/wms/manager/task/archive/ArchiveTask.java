package com.jumbo.wms.manager.task.archive;

import com.jumbo.wms.manager.BaseManager;

public interface ArchiveTask extends BaseManager {

    /**
     * 创单数据归档
     */
    public void archiveCreateSta();
    /**
     * 反馈数据归档
     */
    public void archiveOutSta();



}
