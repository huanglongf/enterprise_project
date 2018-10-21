
package com.jumbo.wms.daemon;

import com.jumbo.wms.manager.BaseManager;

/**
 * 星巴克定时任务
 * 
 * @author lzb
 * 
 */

public interface StarbucksTask extends BaseManager {
    /**
     * 发送星巴克卡订制信息到金邦达
     */
    void sendCardInfoToJBD();
}
