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

package com.jumbo.dao.baseinfo;

import java.util.List;

import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.baseinfo.ChannelWhRef;
import com.jumbo.wms.model.warehouse.ChannelWhRefCommand;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.support.BeanPropertyRowMapperExt;

/**
 * WarehouseShopRef
 * 
 * @author SJK
 * 
 */
@Transactional
public interface ChannelWhRefRefDao extends GenericEntityDao<ChannelWhRef, Long> {
    /**
     * 查询仓库渠道绑定关系
     * 
     * @param ouid
     * @param channelid
     * @return
     */
    @NamedQuery
    ChannelWhRef getChannelRef(@QueryParam("ouid") Long ouid, @QueryParam("channelid") Long channelid);

    @NamedQuery
    List<ChannelWhRef> getChannelRefByOuid(@QueryParam("ouid") Long ouid);

    /**
     * 根据仓库和提供的店铺innerShopCode 查询是否关联 KJL
     * 
     * @param id
     * @param owner
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    String getShopInfoByWarehouseAndShopCode(@QueryParam("ouId") Long id, @QueryParam("shopCode") String owner, SingleColumnRowMapper<String> singleColumnRowMapper);

    /**
     * 根据CHANNELID获取绑定关系
     * 
     * @param channelId
     * @param beanPropertyRowMapperExt
     * @return
     */
    @NativeQuery
    List<ChannelWhRefCommand> findChannelWhRefListByChannelId(@QueryParam(value = "channelId") Long channelId, BeanPropertyRowMapperExt<ChannelWhRefCommand> beanPropertyRowMapperExt);

    @NativeUpdate
    int deleteChannelWhRefByChannelId(@QueryParam(value = "channelId") Long channelId);
    
    @NativeUpdate
    void updateChannelWhRefByChannelId(@QueryParam(value = "whId") Long whId,@QueryParam(value = "channelId") Long channelId);

    @NativeQuery
    List<ChannelWhRef> findChannelWhRefListByChannelIdR(@QueryParam(value = "channelId") Long channelId, BeanPropertyRowMapperExt<ChannelWhRef> beanPropertyRowMapperExt);

    /**
     * 根据月结账号查询数据
     * 
     * @param channelId
     * @param beanPropertyRowMapperExt
     * @return
     */

    @NativeQuery
    List<ChannelWhRef> findChannelWhRefListBySfJcustid(@QueryParam(value = "sfJcustid") String sfJcustid, BeanPropertyRowMapperExt<ChannelWhRef> beanPropertyRowMapperExt);


    /**
     * 更新后置打印配置
     * 
     * @param channelId
     * @param ouId
     * @param isPostPackingPage
     * @param isPostExpressBill void
     * @throws
     */
    @NativeUpdate
    void updatePostPrintConf(@QueryParam(value = "channelId") Long channelId, @QueryParam(value = "ouId") Long ouId, @QueryParam(value = "isPostPackingPage") Integer isPostPackingPage, @QueryParam(value = "isPostExpressBill") Integer isPostExpressBill);
    
    /**
     * 根据仓库id查询所有后置打印配置
     * 
     * @param ouId
     * @param channelRef
     * @return List<ChannelWhRefCommand>
     * @throws
     */
    @NativeQuery
    List<ChannelWhRefCommand> findAllPostPrintConfByOuId(@QueryParam(value = "ouId") Long ouId, BeanPropertyRowMapperExt<ChannelWhRefCommand> channelRef);
}
