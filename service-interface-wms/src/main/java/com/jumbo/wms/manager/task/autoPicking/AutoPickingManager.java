package com.jumbo.wms.manager.task.autoPicking;

import java.util.List;

import com.jumbo.wms.manager.BaseManager;

public interface AutoPickingManager extends BaseManager {

    /**
     * 更新下次执行时间
     */
    void updateAutoPickingNextExecuteTime(Long whouid);

    /**
     * 创建配货清单
     */
    void generatePicking(List<Long> staId);
}
