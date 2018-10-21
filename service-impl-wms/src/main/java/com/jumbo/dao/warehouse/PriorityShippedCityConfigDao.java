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

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.PriorityShippedCityConfig;
import com.jumbo.wms.model.warehouse.PriorityShippedCityConfigCommand;

/**
 * 接口/类说明：优先发货城市配置 DAO
 * 
 * @ClassName: PriorityShippedCityConfigDao
 * @author LuYingMing
 * @date 2016年8月25日 下午1:20:31
 */
@Transactional
public interface PriorityShippedCityConfigDao extends GenericEntityDao<PriorityShippedCityConfig, Long> {

    /**
     * 方法说明：分页查询
     * 
     * @author LuYingMing
     * @date 2016年8月25日 下午7:35:28
     * @param start
     * @param pagesize
     * @param beanPropertyRowMapper
     * @param sorts
     * @param ouId
     * @param ouTypeId
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<PriorityShippedCityConfigCommand> queryPriorityShippedCityConfig(int start, int pagesize, @QueryParam(value = "ouId") Long ouId, @QueryParam(value = "ouTypeId") Long ouTypeId, RowMapper<PriorityShippedCityConfigCommand> r);


    /**
     * 方法说明: 页面
     * 
     * @author LuYingMing
     * @date 2016年8月26日 下午6:14:33
     * @param ouId
     * @param ouTypeId
     * @param rowMapper
     * @return
     */
    // @NativeQuery
    // List<PriorityShippedCityConfig> findSkuCategoryBySta(@QueryParam(value = "ouId") Long
    // ouId,@QueryParam(value = "ouTypeId") Long ouTypeId,RowMapper<PriorityShippedCityConfig>
    // rowMapper);
}
