package com.jumbo.wms.manager.warehouse;

import java.util.List;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.warehouse.QueueSta;

public interface QueueStaManagerProxy extends BaseManager {
    String LOCATION_CODE = "CODE";
    String LOCATION_OWNER = "OWNER";
    int Q_STA_ORDER_TYPE_SALES = 0;// 销售
    int Q_STA_ORDER_TYPE_SP = 1;// 特殊
    int Q_STA_ORDER_TYPE_RETURN = 2;// 退换货

    /**
     * 待过仓订单生成批次好
     * 
     * @param ouid 仓库组织ID
     */
    void generateQstaBatchCode(Long ouid);

    /**
     * 查询队列批次信息
     * 
     * @param whouid 仓库组织ID
     * @param type 0:销售，1：特殊订单，2：退换货，其他：销售
     * @return 批次列表
     */
    List<String> findBatchCodeByWhouid(Long whouid, int type);

    public void screeningQsta();

    public void errorQsta(QueueSta queueSta);

    public void createStabyBatchCode(Long ouid, String batchCode);

    void createStaByQueue(Long ouid);

    /**
     * 实时增量库存同步
     */
    public void salesInventoryOms();
}
