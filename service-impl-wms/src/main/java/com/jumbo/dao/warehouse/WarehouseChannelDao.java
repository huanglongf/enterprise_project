/**
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

package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.DynamicQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.WarehouseChannel;
import com.jumbo.wms.model.warehouse.WarehouseChannelType;

@Transactional
public interface WarehouseChannelDao extends GenericEntityDao<WarehouseChannel, Long> {

    /**
     * 根据通道类型查找仓库收发货通道列表
     * 
     * @param type
     * @return
     */
    @DynamicQuery
    List<WarehouseChannel> findWarehouseChannelList(@QueryParam("type") WarehouseChannelType type, @QueryParam("ouid") Long ouid);

    /**
     * 根据通道编码,组织id,通道类型 查询通道信息
     * 
     * @param code
     * @param ouid
     * @param type
     * @return
     */
    @DynamicQuery
    WarehouseChannel findWarehouseChannelByCodeAndOu(@QueryParam("code") String code, @QueryParam("ouid") Long ouid, @QueryParam("type") WarehouseChannelType type);
}
