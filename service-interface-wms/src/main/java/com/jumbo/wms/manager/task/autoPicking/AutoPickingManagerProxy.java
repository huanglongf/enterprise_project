package com.jumbo.wms.manager.task.autoPicking;

import com.jumbo.wms.manager.BaseManager;

public interface AutoPickingManagerProxy extends BaseManager {

    /**
     * 根据仓库创建数据
     */
    void createCreateByWh(Long whOuId, boolean isSingleTask);

    /**
     * 单仓执行先处理合并订单，再创建配货清单
     */
    void autoGeneratePickingLilst(Long hwouid);

    /**
     * 基于渠道组创建配货清单,根据规则顺序查询单据依次创建配货清单
     */
    void autoGeneratePickingLilst(Long hwouid, Long channelGroupId);


}
