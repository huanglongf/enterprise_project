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

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.baseinfo.ChannelWhRef;
import com.jumbo.wms.model.warehouse.BiChannelCommand;

/**
 * @author lichuan
 *
 */
@Transactional
public interface ChannelPostPrintConfDao extends GenericEntityDao<ChannelWhRef, Long> {
    /**
     * 查询仓库关联的所有渠道列表(包括跨渠道合并的虚拟主渠道)
     *@param starts
     *@param pageSize
     *@param ouId
     *@param storts
     *@param channel
     *@return Pagination<BiChannelCommand> 
     *@throws
     */
    @NativeQuery(pagable = true)
    Pagination<BiChannelCommand> findAllChannelRefByOuId(int starts, int pageSize, @QueryParam(value = "ouId") Long ouId, Sort[] sorts, BeanPropertyRowMapper<BiChannelCommand> channel);
    
}
