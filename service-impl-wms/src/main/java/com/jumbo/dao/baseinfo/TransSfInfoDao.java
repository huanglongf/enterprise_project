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

import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.baseinfo.TransSfInfo;

@Transactional
public interface TransSfInfoDao extends GenericEntityDao<TransSfInfo, Long> {
    /**
     * 根据月结账号查询
     * 
     * @return
     */
    @NamedQuery
    TransSfInfo findTransSfInfoJCustid(@QueryParam("jCustid") String jCustid);

    /**
     * 查询默认配置
     * 
     * @return
     */
    @NamedQuery
    TransSfInfo findTransSfInfoDefault(@QueryParam("default") Boolean isDefault);

    /**
     * @param specialCode
     * @return
     */
    @NamedQuery
    TransSfInfo findTransSfInfoSpecialUse(@QueryParam("specialCode") String specialCode);



}
