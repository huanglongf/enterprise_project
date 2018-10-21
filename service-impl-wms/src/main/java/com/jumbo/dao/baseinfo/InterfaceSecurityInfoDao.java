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

import java.util.Date;

import org.springframework.transaction.annotation.Transactional;
import com.jumbo.wms.model.baseinfo.InterfaceSecurityInfo;
import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

@Transactional
public interface InterfaceSecurityInfoDao extends GenericEntityDao<InterfaceSecurityInfo, Long> {

    @NamedQuery
    InterfaceSecurityInfo findUseringUserBySource(@QueryParam("source") String source, @QueryParam("date") Date expDate);

    @NamedQuery
    InterfaceSecurityInfo findUseringByName(@QueryParam("username") String username, @QueryParam("source") String source, @QueryParam("date") Date date);

    @NamedQuery
    InterfaceSecurityInfo findByName(@QueryParam("username") String username, @QueryParam("date") Date date);

}
