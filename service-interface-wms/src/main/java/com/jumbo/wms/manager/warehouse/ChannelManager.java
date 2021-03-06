/**
 * 
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */
package com.jumbo.wms.manager.warehouse;

import java.util.List;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.warehouse.WarehouseChannel;

public interface ChannelManager extends BaseManager {
    /**
     * 根据通道类型查找仓库收发货通道列表
     * 
     * @param type
     * @return
     */
    public List<WarehouseChannel> findWarehouseChannelList(int intChannelType, Long ouid);

    /**
     * 新增保存收发货通道信息
     * 
     * @param ou
     * @param channelType
     * @param warehouseChannelList
     * @param addList
     * @return
     */
    List<WarehouseChannel> createUpdateChannelList(OperationUnit ou, int channelType, List<WarehouseChannel> warehouseChannelList, List<WarehouseChannel> addList);
}
