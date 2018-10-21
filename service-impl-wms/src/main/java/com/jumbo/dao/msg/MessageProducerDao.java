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
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.baozun.scm.baseservice.message.common.MessageProducerCommond;
import com.jumbo.wms.model.msg.MessageProducer;

@Transactional
public interface MessageProducerDao extends GenericEntityDao<MessageProducer, Long> {

    @NativeQuery(pagable = true)
    Pagination<MessageProducerCommond> findListByQueryMapWithPage(int start, int pageSize, @QueryParam("id") Long id, @QueryParam("msgId") String msgId, @QueryParam("msgType") String msgType, @QueryParam("topics") List<String> topics,
            @QueryParam("status") Integer status, @QueryParam("sendTime") Date sendTime, @QueryParam("feedbackTime") Date feedbackTime, @QueryParam("tags") String tags, lark.common.dao.Sort[] sorts, RowMapper<MessageProducerCommond> r);

    @NativeQuery
    MessageProducerCommond findMsgByMsgId(@QueryParam(value = "msgId") String msgId, @QueryParam(value = "msgType") String msgType, BeanPropertyRowMapper<MessageProducerCommond> r);


    @NativeQuery
    List<MessageProducerCommond> findMsgList(@QueryParam("id") Long id, @QueryParam("msgId") String msgId, @QueryParam("msgType") String msgType, @QueryParam("topics") List<String> topics,
 @QueryParam("status") Integer status,
            @QueryParam("sendTime") Date sendTime, @QueryParam("feedbackTime") Date feedbackTime, @QueryParam("tags") String tagss, RowMapper<MessageProducerCommond> r);

}
