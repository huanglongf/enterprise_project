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

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.SfMailNoRemainRelation;

/**
 * 
 * 接口/类说明：顺丰订单确认队列 (超长)运单分存 关联表 DAO
 * 
 * @ClassName: SfMailNoRemainRelationDao
 * @author LuYingMing
 */
@Transactional
public interface SfMailNoRemainRelationDao extends GenericEntityDao<SfMailNoRemainRelation, Long> {

    /**
     * 方法说明：根据顺丰订单确认队列的pId查询关联的分存运单号码
     * 
     * @author LuYingMing
     * @param pId
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<String> findMailNoByRefPid(@QueryParam("pId") Long pId, SingleColumnRowMapper<String> singleColumnRowMapper);
}
