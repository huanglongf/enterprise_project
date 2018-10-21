/**
 * Copyright (c) 2013 Baozun All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Baozun. You shall not disclose
 * such Confidential Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with Baozun.
 *
 * BAOZUN MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. BAOZUN SHALL NOT BE LIABLE FOR ANY DAMAGES
 * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 *
 */
package com.jumbo.dao.msg;

import java.util.Date;
import java.util.List;

import lark.common.dao.Pagination;
import lark.common.dao.Sort;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.baozun.scm.baseservice.message.common.MessageConsumerCommond;
import com.jumbo.wms.model.msg.MessageConsumer;
import com.jumbo.wms.model.msg.MessageConsumerDto;

@Transactional
public interface MessageConsumerDao extends GenericEntityDao<MessageConsumer, Long> {

    @NativeQuery(pagable = true)
    Pagination<MessageConsumerCommond> findListByQueryMapWithPage(int start, int pageSize, @QueryParam("id") Long id, @QueryParam("msgId") String msgId, @QueryParam("msgType") String msgType, @QueryParam("topics") List<String> topics,
            @QueryParam("status") Integer status, @QueryParam("receiveTime") Date receiveTime, @QueryParam("dealTime") Date dealTime, @QueryParam("tags") String tags, Sort[] sorts, BeanPropertyRowMapper<MessageConsumerCommond> r);

    @NativeQuery(model = MessageConsumerCommond.class)
    MessageConsumerCommond findMsgByMsgId(@QueryParam(value = "msgId") String msgId, @QueryParam(value = "msgType") String msgType, RowMapper<MessageConsumerCommond> r);

    @NativeQuery
    List<MessageConsumerCommond> findByMsgList(@QueryParam("id") Long id, @QueryParam("msgId") String msgId, @QueryParam("msgType") String msgType, @QueryParam("topics") List<String> topics,
 @QueryParam("status") Integer status,
            @QueryParam("receiveTime") Date receiveTime, @QueryParam("dealTime") Date dealTime, @QueryParam("tags") String tags, BeanPropertyRowMapper<MessageConsumerCommond> r);


    @NativeQuery(model = MessageConsumerDto.class)
    MessageConsumerDto findMsgByMsgIdByDto(@QueryParam(value = "msgId") String msgId, @QueryParam(value = "msgType") String msgType, RowMapper<MessageConsumerDto> r);

    @NativeUpdate
    int delMsgByMsgId(@QueryParam(value = "msgId") String msgId, @QueryParam(value = "msgType") String msgType);


    @NativeUpdate
    int updateMsgByMsgId(@QueryParam(value = "msgId") String msgId, @QueryParam(value = "msgType") String msgType);


}
