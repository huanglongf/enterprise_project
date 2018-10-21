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
package com.jumbo.dao.pda;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.pda.PickingBatchBox;

import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;


/**
 * @author lihui
 *
 * @createDate 2016年1月19日 下午9:00:16
 */
@Transactional
public interface PickingBatchBoxDao extends GenericEntityDao<PickingBatchBox, Long> {

    @NativeUpdate
    int insertPickingBatchBoxByLineId(@QueryParam(value = "pickingLineId") Long pickingLineId, @QueryParam(value = "cCode") String cCode, @QueryParam(value = "qty") Integer qty);

    @NativeUpdate
    int updatePickingBatchBoxByLineId(@QueryParam(value = "pickingLineId") Long pickingLineId, @QueryParam(value = "cCode") String cCode);

}
