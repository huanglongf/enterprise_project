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

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.msg.MessageConfig;

@Transactional
public interface MessageConfigDao extends GenericEntityDao<MessageConfig, Long> {

    @NativeQuery
    List<MessageConfig> findMessageConfigByParameter(@QueryParam(value = "msgType") String msgType, @QueryParam(value = "topic") String topic, @QueryParam(value = "tags") String tags, BeanPropertyRowMapper<MessageConfig> r);
    
    @NativeQuery(pagable=true)
    Pagination<MessageConfig> findMessageConfigPage(int start, int pageSize,Sort[] sorts,@QueryParam("msgType") String msgType,@QueryParam("topic") String topic,RowMapper<MessageConfig> rowMapper);
    
}
