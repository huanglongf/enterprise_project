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
 */
package com.jumbo.wms.manager.warehouse;

import java.util.List;

import loxia.dao.Pagination;
import loxia.dao.Sort;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.baseinfo.ChannelWhRef;
import com.jumbo.wms.model.warehouse.BiChannelCommand;
import com.jumbo.wms.model.warehouse.ChannelWhRefCommand;

/**
 * @author lichuan
 *
 */
public interface ChannelPostPrintConfManager extends BaseManager {
    /**
     * 查询仓库关联的所有渠道列表(包括跨渠道合并的虚拟主渠道)
     *
     * @param start
     * @param pageSize
     * @param ouId
     * @param sorts
     * @return Pagination<BiChannelCommand>
     * @throws
     */
    Pagination<BiChannelCommand> findAllChannelRefByOuId(int start, int pageSize, Long ouId, Sort[] sorts);

    /**
     * 更新后置打印配置
     *
     * @param channelWhRefList
     * @param ouId void
     * @throws
     */
    void updatePostPrintConf(List<ChannelWhRef> channelWhRefList, Long ouId, Long userId, List<ChannelWhRefCommand> channelList);

    /**
     * 根据仓库id查询所有后置打印配置
     *
     * @param ouId
     * @return List<ChannelWhRefCommand>
     * @throws
     */
    List<ChannelWhRefCommand> findAllPostPrintConfByOuId(Long ouId);

}
