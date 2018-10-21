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
package com.jumbo.dao.pickZone;

import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.pickZone.WhOcpOrderExeLine;



/**
 * @author lihui
 *
 * @createDate 2015年7月27日 下午4:15:47
 */
@Transactional
public interface WhOcpOrderExeLineDao extends GenericEntityDao<WhOcpOrderExeLine, Long> {

    /**
     * 根据库存新增执行明细
     * 
     * @param invId
     * @param ocpId
     * @param qty
     */
    @NativeUpdate
    void insertOoelByInv(@QueryParam(value = "invId") Long invId, @QueryParam(value = "ocpId") Long ocpId, @QueryParam(value = "qty") Long qty);
}
