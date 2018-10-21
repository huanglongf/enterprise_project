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
package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.BiChannelCommand;
import com.jumbo.wms.model.warehouse.BiChannelGroup;
import com.jumbo.wms.model.warehouse.BiChannelGroupCommand;

/**
 * @author lichuan
 * 
 */
@Transactional
public interface ChannelGroupDao extends GenericEntityDao<BiChannelGroup, Long> {

    /**
     * 根据仓库id查询所有关联渠道组
     * 
     * @param starts
     * @param pageSize
     * @param ouId
     * @param sorts
     * @param channelGroup
     * @return Pagination<BiChannelGroupCommand>
     * @throws
     */
    @NativeQuery(pagable = true)
    Pagination<BiChannelGroupCommand> findAllChannelGroupByOuId(int starts, int pageSize, @QueryParam(value = "ouId") Long ouId, Sort[] sorts, BeanPropertyRowMapper<BiChannelGroupCommand> channelGroup);

    /**
     * 根据仓库id查询所有关联渠道组 不分页
     * @param ouId
     * @param sorts
     * @param channelGroup
     * @return
     */
    @NativeQuery
    List<BiChannelGroupCommand> findChannelGroupByOuId(@QueryParam(value = "ouId") Long ouId, Sort[] sorts, BeanPropertyRowMapper<BiChannelGroupCommand> channelGroup);
    
    /**
     * 根据编码查询渠道组数量
     * 
     * @param code
     * @param r
     * @return Long
     * @throws
     */
    @NativeQuery
    Long findChannelGroupCountByCode(@QueryParam("code") String code, SingleColumnRowMapper<Long> r);

    /**
     * 根据名称查询渠道组数量
     * 
     * @param code
     * @param r
     * @return Long
     * @throws
     */
    @NativeQuery
    Long findChannelGroupCountByName(@QueryParam("name") String name, SingleColumnRowMapper<Long> r);

    /**
     * 根据编码查询渠道组
     * 
     * @param code
     * @return AutoPickingListRole
     * @throws
     */
    @NamedQuery
    BiChannelGroup findChannelGroupByCode(@QueryParam("code") String code);

    /**
     * 删除渠道组关联的所有渠道
     * 
     * @param groupId void
     * @throws
     */
    @NativeUpdate
    void deleteChannelRefByGroupId(@QueryParam(value = "groupId") Long groupId);

    /**
     * 渠道组关联渠道
     * 
     * @param groupId
     * @param channelId void
     * @throws
     */
    @NativeUpdate
    void insertChannelRef(@QueryParam(value = "groupId") Long groupId, @QueryParam(value = "channelId") Long channelId);

    /**
     * 查询渠道组关联的所有渠道
     * 
     * @param groupId
     * @param channelRef
     * @return List<BiChannelCommand>
     * @throws
     */
    @NativeQuery
    List<BiChannelCommand> findAllChannelRefByGroupId(@QueryParam(value = "groupId") Long groupId, BeanPropertyRowMapperExt<BiChannelCommand> channelRef);
    /**
     * 查询渠道组关联的所有渠道
     * 
     * @param code
     * @param channelRef
     * @return List<BiChannelCommand>
     * @throws
     */
    @NativeQuery
    List<BiChannelCommand> findAllChannelRefByGroupCode(@QueryParam(value = "code") String code, BeanPropertyRowMapperExt<BiChannelCommand> channelRef);

    /**
     * 根据渠道id查询关联渠道组
     * 
     * @param channelId
     * @param channelGroup
     * @return List<BiChannelGroupCommand>
     * @throws
     */
    @NativeQuery
    List<BiChannelGroupCommand> findAllChannelGroupByCIdAndOuId(@QueryParam(value = "channelId") Long channelId, @QueryParam(value = "ouId") Long ouId, BeanPropertyRowMapperExt<BiChannelGroupCommand> channelGroup);
    
    /**
     * 根据渠道id查询关联渠道组
     * 
     * @param channelId
     * @param channelGroup
     * @return List<BiChannelGroupCommand>
     * @throws
     */
    @NativeQuery
    List<BiChannelGroupCommand> findAllChannelGroupByCIdAndOuId(@QueryParam(value = "channelId") Long channelId,@QueryParam(value = "groupId") Long groupId, @QueryParam(value = "ouId") Long ouId, BeanPropertyRowMapperExt<BiChannelGroupCommand> channelGroup);
}
