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
package com.jumbo.dao.automaticEquipment;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.automaticEquipment.ZoonOcpSort;


/**
 * 配货区域优先级
 * 
 * @author xiaolong.fei
 * 
 */
@Transactional
public interface ZoonOcpSortDao extends GenericEntityDao<ZoonOcpSort, Long> {

    /**
     * 获取区域下拉框数据
     * 
     * @param pickingId
     * @param rowMap
     * @return
     */
    @NativeQuery
    List<ZoonOcpSort> findZoonOcpSortList(@QueryParam("ouId") Long ouId, @QueryParam("saleModle") String saleModle, RowMapper<ZoonOcpSort> rowMap);

    /**
     * 获取分页数据
     */
    @NativeQuery(pagable = true)
    Pagination<ZoonOcpSort> findZoonOcpSortByzoonCode(int start, int pageSize, Sort[] sorts, @QueryParam("ouId") Long ouId, @QueryParam("zoonCode") String zoonCode, RowMapper<ZoonOcpSort> rowMapper);

    /**
     * 配货 模式，优先级唯一校验
     * 
     * @param ouId
     * @param sort
     * @param saleModel
     * @return
     */
    @NativeQuery
    int findZoonOcpSortBySortAndsaleModel(@QueryParam("ouId") Long ouId, @QueryParam("sort") int sort, @QueryParam("saleModel") String saleModel, SingleColumnRowMapper<Integer> single);

    /**
     * 配货模式，仓库区域唯一校验
     * 
     * @param ouId
     * @param saleModel
     * @param zoonCode
     * @return
     */
    @NativeQuery
    int findZoonOcpSortByZoonCodeAndsaleModel(@QueryParam("ouId") Long ouId, @QueryParam("saleModel") String saleModel, @QueryParam("zoonCode") String zoonCode, SingleColumnRowMapper<Integer> single);


}
