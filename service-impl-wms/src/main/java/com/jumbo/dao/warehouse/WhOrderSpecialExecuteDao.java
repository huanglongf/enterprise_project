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

package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.WhOrderSpecialExecute;

@Transactional
public interface WhOrderSpecialExecuteDao extends GenericEntityDao<WhOrderSpecialExecute, Long> {
    @NamedQuery
    List<WhOrderSpecialExecute> getByQueueId(@QueryParam(value = "qId") Long id);

    @NativeUpdate
    void inserSpecialExecuteLog(@QueryParam(value = "qId") Long qId, @QueryParam(value = "type") int type, @QueryParam(value = "memo") String memo);

    @NativeUpdate
    void delSpecialExecute(@QueryParam(value = "qId") Long qId);


}
