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

package com.jumbo.dao.oms;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;
import com.jumbo.wms.model.oms.EtamOmsInvRule;

@Transactional
public interface EtamOmsInvRuleDao extends GenericEntityDao<EtamOmsInvRule, Long> {

    /**
     * 查询当前库存rule=1
     * 
     * @param start
     * @param pageSize
     * @param brand
     * @param size
     * @param rowMapper
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<EtamOmsInvRule> findEtamOmsInvRule1ByPage(int start, int pageSize, @QueryParam("brand") String brand, @QueryParam("sizes") String sizes, RowMapper<EtamOmsInvRule> rowMapper, Sort[] sorts);

    /**
     * 查询当前库存rule=2
     * 
     * @param start
     * @param pageSize
     * @param brand
     * @param size
     * @param rowMapper
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<EtamOmsInvRule> findEtamOmsInvRule2ByPage(int start, int pageSize, @QueryParam("bar9") String brand, @QueryParam("color") String sizes, RowMapper<EtamOmsInvRule> rowMapper, Sort[] sorts);

    /**
     * 取消作业
     * 
     * @param start
     * @return
     */
    @NativeUpdate
    void cancelEtam(@QueryParam("id") long id);

    /**
     * 根据品牌尺码查询
     * 
     * @param sizes
     * @param brand
     * @return
     */
    @NamedQuery
    EtamOmsInvRule findByBrandSize(@QueryParam("sizes") String sizes, @QueryParam("brand") String brand);

    /**
     * 根据款号颜色查询
     * 
     * @param sizes
     * @param brand
     * @return
     */
    @NamedQuery
    EtamOmsInvRule findByBar9Color(@QueryParam("bar9") String bar9, @QueryParam("color") String color);

}
