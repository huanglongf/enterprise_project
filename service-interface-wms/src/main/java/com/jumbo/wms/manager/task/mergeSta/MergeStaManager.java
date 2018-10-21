package com.jumbo.wms.manager.task.mergeSta;

import java.util.List;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.warehouse.PickingList;
import com.jumbo.wms.model.warehouse.StockTransApplication;

public interface MergeStaManager extends BaseManager {

    void mergeStaTask(Long whOuId);

    StockTransApplication creatNewSta(List<Long> staOldIdList, String newOwner);

    void creatNewStaStaLine(List<Long> staId, StockTransApplication sta);

    void newStaNEoldSta(List<Long> staS, List<Long> staE, StockTransApplication sta, PickingList p);

    void newStaEqualOldSta(List<Long> staS, StockTransApplication sta);
}
