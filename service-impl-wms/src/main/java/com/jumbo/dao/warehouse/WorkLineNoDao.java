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

import org.springframework.transaction.annotation.Transactional;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import com.jumbo.wms.model.warehouse.WorkLineNo;

@Transactional
public interface WorkLineNoDao extends GenericEntityDao<WorkLineNo, Long> {
    @NamedQuery
    List<WorkLineNo> queryWorkLineNoByOuid(@QueryParam(value = "ouid") Long ouid);

    @NativeUpdate
    void deleteByOuId(@QueryParam("ouid") Long ouid);

    @NativeUpdate
    void addLineNoByOuId(@QueryParam("code") String code, @QueryParam("lineNo") String lineNo, @QueryParam("ouid") Long ouid);

    @NamedQuery
    WorkLineNo getWorkLineNoByCode(@QueryParam(value = "ouid") Long ouid, @QueryParam("code") String code);
}
