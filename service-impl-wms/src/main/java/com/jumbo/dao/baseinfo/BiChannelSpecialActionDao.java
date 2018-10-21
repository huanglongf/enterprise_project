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

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.baseinfo.BiChannelSpecialAction;
import com.jumbo.wms.model.baseinfo.BiChannelSpecialActionType;
import com.jumbo.wms.model.warehouse.BiChannelSpecialActionCommand;

/**
 * 
 * @author SJK
 * 
 */
@Transactional
public interface BiChannelSpecialActionDao extends GenericEntityDao<BiChannelSpecialAction, Long> {

    /**
     * 获取渠道的指定类型
     * 
     * @param channelId
     * @param type
     * @return
     */
    @NamedQuery
    List<BiChannelSpecialAction> getChannelByAndType(@QueryParam("channelId") Long channelId, @QueryParam("type") BiChannelSpecialActionType type);

    /**
     * 获取装箱订单模板类型
     * 
     * @param plid
     * @param staid
     * @return
     */
    @NativeQuery
    String findTemplateType(@QueryParam("plid") Long plid, @QueryParam("staid") Long staid, SingleColumnRowMapper<String> r);

    /**
     * 通过渠道ID获取对应的渠道行为
     * 
     * @param plid
     * @param staid
     * @param r
     * @return
     */
    @NativeQuery
    BiChannelSpecialActionCommand getBiChannelSpecialActionByCidType(@QueryParam("id") Long id, @QueryParam("type") int type, RowMapper<BiChannelSpecialActionCommand> r);

    /**
     * 通过渠道ID获取对应的渠道行为 type为20、30
     * 
     * @param plid
     * @param staid
     * @param r
     * @return
     */
    @NativeQuery
    List<BiChannelSpecialAction> getBiChannelSpecialActionByChId(@QueryParam("id") Long id, RowMapper<BiChannelSpecialAction> r);

    /**
     * 通过渠道ID获取对应的渠道行为 type为1
     * 
     * @param id
     * @param r
     * @return
     */
    @NativeQuery
    BiChannelSpecialAction getBiChannelSpecialActionByType(@QueryParam("id") Long id, RowMapper<BiChannelSpecialAction> r);


    /**
     * 根据配货清单查询发票模板类型
     * 
     * @param plid
     * @return
     */
    @NativeQuery
    String findInvoiceType(@QueryParam("plid") Long plid, @QueryParam("staid") Long staid, SingleColumnRowMapper<String> r);

    /**
     * 
     * @Description: 通过渠道编码与渠道行为类型获取对应的渠道行为
     * @param channelCode
     * @param type 30获取保价金额信息
     * @param r
     * @return BiChannelSpecialActionCommand
     * @throws
     */
    @NativeQuery
    BiChannelSpecialAction getChannelActionByCodeAndType(@QueryParam("channelCode") String channelCode, @QueryParam("type") int type, RowMapper<BiChannelSpecialAction> r);

    /**
     * 根据batchNo查询发票模板类型
     * 
     * @param plid
     * @return
     */
    @NativeQuery
    String findInvoiceTypeByBatchNo(@QueryParam("batchNo") String batchNo, @QueryParam("wioIdList") List<Long> wioIdList, SingleColumnRowMapper<String> r);
    
    /**
     * 获取自定义打印模板
     * @param plid
     * @param staid
     * @param r
     * @return
     */
    @NativeQuery
    String findCustomPtintCode(@QueryParam("plid") Long plid, @QueryParam("staid") Long staid, SingleColumnRowMapper<String> r);

}
