package com.jumbo.web.action.warehouse;

import loxia.dao.Pagination;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.warehouse.DeliveryChanngeLogCommand;

/**
 * 
 * @author jinggang.chen
 * 
 */
public class StaDeliveryChangeLogAction extends BaseJQGridProfileAction {

    /**
     * 
     */
    private static final long serialVersionUID = 4649230079923117178L;

    @Autowired
    private WareHouseManager wareHouseManager;

    private DeliveryChanngeLogCommand deliveryLog;

    public String findDeliveryChangeLog() {
        return SUCCESS;
    }

    /**
     * 
     * @return
     */
    public String findDeliveryChangeLogList() {
        setTableConfig();
        Pagination<DeliveryChanngeLogCommand> list = wareHouseManager.findDeliveryChanngeLogCommandList(tableConfig.getStart(), tableConfig.getPageSize(), tableConfig.getSorts(), userDetails.getCurrentOu().getId(), deliveryLog);
        request.put(JSON, toJson(list));
        return JSON;
    }

    public DeliveryChanngeLogCommand getDeliveryLog() {
        return deliveryLog;
    }

    public void setDeliveryLog(DeliveryChanngeLogCommand deliveryLog) {
        this.deliveryLog = deliveryLog;
    }

}
