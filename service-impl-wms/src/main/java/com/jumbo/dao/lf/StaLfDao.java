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
package com.jumbo.dao.lf;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.lf.StaLf;
import com.jumbo.wms.model.lf.StaLfCommand;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.support.BeanPropertyRowMapperExt;

@Transactional
public interface StaLfDao extends GenericEntityDao<StaLf, Long> {

    @NativeQuery
    StaLfCommand findNikeOutBoundLabel(@QueryParam("cartonId") Long cartonId, BeanPropertyRowMapperExt<StaLfCommand> r);

    @NativeQuery
    StaLfCommand findNikeCrwPod(@QueryParam("staId") Long staId, BeanPropertyRowMapperExt<StaLfCommand> r);
    
    @NativeQuery
    StaLfCommand findOutBound(@QueryParam("staId") Long staId, BeanPropertyRowMapperExt<StaLfCommand> r);
    
    @NamedQuery
    StaLf getStaLfByStaId(@QueryParam("staId") Long staId);
    
}
