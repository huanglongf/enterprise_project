package com.jumbo.wms.manager.warehouse;

import java.util.List;

import com.jumbo.wms.manager.BaseManager;

public interface QstaManager extends BaseManager {
    // 销售出库
    public void screeningQsta();

    public void createStabyBatchCode(Long whId, String batchId);

    void createPickingListToWCS(Long msgId);

    void createPickingListToWCS(List<Long> msgId);

}
