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
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.warehouse.BiChannelCommand;
import com.jumbo.wms.model.warehouse.BiChannelGroup;
import com.jumbo.wms.model.warehouse.BiChannelGroupCommand;

/**
 * @author lichuan
 * 
 */
public interface ChannelGroupManager extends BaseManager {

    /**
     * 根据仓库id查询所有关联渠道组
     * 
     * @param start
     * @param pageSize
     * @param ouId
     * @param sorts
     * @return Pagination<BiChannelGroupCommand>
     * @throws
     */
    Pagination<BiChannelGroupCommand> findAllChannelGroupByOuId(int start, int pageSize, Long ouId, Sort[] sorts);

    /**
     * 根据仓库id查询所有关联渠道组 不分页
     * 
     * @param ouId
     * @param sorts
     * @return
     */
    List<BiChannelGroupCommand> findChannelGroupByOuId(Long ouId, Sort[] sorts);
    
    /**
     * 根据编码查询渠道组是否存在
     * 
     * @param code
     * @return boolean
     * @throws
     */
    boolean findChannelGroupExistByCode(String code);

    /**
     * 根据编码查询渠道组是否存在
     * 
     * @param code
     * @return boolean
     * @throws
     */
    boolean findChannelGroupExistByName(String name);

    /**
     * 保存渠道组
     * 
     * @param role
     * @param status void
     * @throws
     */
    void saveChannelGroup(BiChannelGroup group, Integer status, OperationUnit ou);

    /**
     * 删除渠道组
     * 
     * @param group void
     * @throws
     */
    void deleteChannelGroup(BiChannelGroup group);

    /**
     * 根据编码查询渠道组
     * 
     * @param code
     * @return AutoPickingListRole
     * @throws
     */
    BiChannelGroup findChannelGroupByCode(String code);

    /**
     * 保存关联渠道
     * 
     * @param role
     * @param status void
     * @throws
     */
    void saveChannelRef(BiChannelGroup group, Integer status, OperationUnit ou, List<BiChannel> channelRef);

    /**
     * 查询渠道组关联的所有渠道
     * 
     * @param groupId
     * @return List<BiChannelCommand>
     * @throws
     */
    List<BiChannelCommand> findAllChannelRefByGroupId(Long groupId);
    /**
     * 查询渠道组关联的所有渠道
     * 
     * @param code
     * @return List<BiChannelCommand>
     * @throws
     */
    List<BiChannelCommand> findAllChannelRefByGroupCode(String code);

    /**
     * 根据渠道id查询关联渠道组
     * 
     * @param channelId
     * @return List<BiChannelGroupCommand>
     * @throws
     */
    List<BiChannelGroupCommand> findAllChannelGroupByCIdAndOuId(Long channelId, OperationUnit ou);
    
    /**
     * 根据渠道id,渠道组id查询关联渠道组
     * 
     * @param channelId
     * @return List<BiChannelGroupCommand>
     * @throws
     */
    List<BiChannelGroupCommand> findAllChannelGroupByCIdAndOuId(Long channelId,Long groupId, OperationUnit ou);
}
