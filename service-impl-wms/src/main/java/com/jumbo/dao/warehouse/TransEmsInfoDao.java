/**
 * \ * Copyright (c) 2010 Jumbomart All Rights Reserved.
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

import java.util.Date;
import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.baseinfo.TransEmsInfo;

@Transactional
public interface TransEmsInfoDao extends GenericEntityDao<TransEmsInfo, Long> {

    @NamedQuery
    TransEmsInfo findByCmp(@QueryParam("isCod") Boolean isCod, @QueryParam("type") Integer type);

    @NamedQuery
    List<TransEmsInfo> findByAllCmp(@QueryParam("isCod") Boolean isCod, @QueryParam("type") Integer type);

    @NamedQuery
    TransEmsInfo findAccountByCmp(@QueryParam("isCod") Boolean isCod, @QueryParam("isDefaultAccount") Boolean isDefaultAccount, @QueryParam("type") Integer type);

    @NamedQuery
    TransEmsInfo findByAccount(@QueryParam("isCod") Boolean isCod, @QueryParam("account") String account, @QueryParam("type") Integer type);

    @NativeQuery(model = TransEmsInfo.class)
    List<TransEmsInfo> queryTransEmsInfo(@QueryParam("type") Integer type, RowMapper<TransEmsInfo> r);

    @NativeQuery(model = TransEmsInfo.class)
    List<TransEmsInfo> queryTransEmsInfo2(@QueryParam("type") Integer type, RowMapper<TransEmsInfo> r);


    @NativeUpdate
    int updateTransEmsInfo(@QueryParam("customerCode") String customerCode, @QueryParam("flashToken") String flashToken, @QueryParam("flashTokenExpireTime") Date flashTokenExpireTime);

    @NativeUpdate
    int updateTransEmsInfo2(@QueryParam("customerCode") String customerCode, @QueryParam("authorization") String authorization, @QueryParam("expireTime") Date expireTime);

}
