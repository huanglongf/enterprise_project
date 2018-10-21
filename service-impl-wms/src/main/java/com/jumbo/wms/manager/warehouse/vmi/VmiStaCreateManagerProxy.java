package com.jumbo.wms.manager.warehouse.vmi;

import com.jumbo.wms.manager.BaseManager;

/**
 * VMI作业单创建
 * 
 * @author sjk
 * 
 */
public interface VmiStaCreateManagerProxy extends BaseManager {
    /**
     * VMI 品牌 创建入库作业单
     * 
     * @param vmiCode
     */
    void generateVmiInboundStaByVmiCode(String vmiCode);

    /**
     * VMI 品牌创建入库作业单(通用)
     */
    void generateVmiInboundStaByVmiCodeDefault(String vmiCode, String vmiSource);
}
