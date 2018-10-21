package com.jumbo.wms.manager.pingan;

import com.jumbo.wms.manager.BaseManager;

public interface WhPingAnCoverManager extends BaseManager {

    /**
     * 创建平安投保数据
     * 
     * @param staid 作业单ID
     * @param ouid 仓库ID
     * @param trackingNumber 运单号
     */
    void createPingAnCover(Long staid, Long ouid, String trackingNumber, String expressCode);

}
