package com.jumbo.wms.manager.ymt2hub;

import java.util.List;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.warehouse.YamatoConfirmOrderQueue;

public interface YamatoTransToHubManager extends BaseManager{

    /**
     * yamato信息反馈hub接口
     * @param yamatoConfirmOrderQueue
     */
    public void yamatoTransInfoToHub(List<YamatoConfirmOrderQueue> yamatoOrderList);

}
