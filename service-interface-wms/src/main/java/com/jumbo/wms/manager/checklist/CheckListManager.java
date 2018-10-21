package com.jumbo.wms.manager.checklist;

import java.util.Date;
import java.util.List;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.warehouse.AutoOutBoundEntity;

public interface CheckListManager extends BaseManager {

    boolean checkDubboService(Date checkDate);

    boolean checkDd(Date checkDate);

    boolean checkTaskIsOk(Date checkDate);

    void outBoundByPistListToMq(List<AutoOutBoundEntity> autoOutBoundEntityList);

    String checkServicePing() throws Exception;
}
