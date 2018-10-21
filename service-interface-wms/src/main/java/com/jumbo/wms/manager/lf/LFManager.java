package com.jumbo.wms.manager.lf;

public interface LFManager {
    /**
     * 接收MQ AF INVENTORY 数据并插入数据库 param : message
     */
    void receiveWhLFInventoryByMq(String message);

}
