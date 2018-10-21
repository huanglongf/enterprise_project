/**
 * 
 * Copyright (c) 2013 Jumbomart All Rights Reserved.
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
package com.jumbo.wms.manager.warehouse;

import java.util.List;

import loxia.dao.Pagination;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.warehouse.HightProvinceConfig;
import com.jumbo.wms.model.warehouse.PriorityShippedCityConfig;
import com.jumbo.wms.model.warehouse.PriorityShippedCityConfigCommand;

/**
 * 接口/类说明：优先发货城市配置
 * 
 * @ClassName: PriorityShippedCityConfigManager
 * @author LuYingMing
 * @date 2016年8月25日 上午11:31:30
 */
public interface PriorityShippedCityConfigManager extends BaseManager {

    /**
     * 方法说明：分页查询
     * 
     * @author LuYingMing
     * @date 2016年8月25日 下午7:27:57
     * @param start
     * @param pageSize
     * @param ouId
     * @param ouTypeId
     * @param sorts
     * @return
     */
    public Pagination<PriorityShippedCityConfigCommand> queryPriorityCityConfig(int start, int pageSize, Long ouId, Long ouTypeId);

    public Pagination<HightProvinceConfig> queryHightProvinceConfig(int start, int pageSize, Long ouId, Long ouTypeId, HightProvinceConfig hightProvinceConfig);


    void deleteHightProvinceConfig(String ids);

    List<HightProvinceConfig> findPriorityList(Long ouId, Long ouTypeId);

    /**
     * 方法说明：保存
     * 
     * @author LuYingMing
     * @date 2016年8月25日 下午1:01:17
     * @param entity
     * @throws Exception
     */
    void saveEntity(PriorityShippedCityConfig entity);

    /**
     * 方法说明：删除
     * 
     * @author LuYingMing
     * @date 2016年8月25日 下午1:01:42
     * @param id
     */
    void deleteEntity(String ids);

    /**
     * 方法说明：控件查询优先发货城市(先仓库,后集团)
     * 
     * @author LuYingMing
     * @date 2016年8月26日 下午6:07:46
     * @param ouId
     * @param ouTypeId
     * @return
     */
    List<PriorityShippedCityConfigCommand> findPriorityCityList(Long ouId, Long ouTypeId);


    public void saveHightProvinceConfig(HightProvinceConfig hightProvinceConfig);
}
