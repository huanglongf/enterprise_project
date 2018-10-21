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
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;
import com.jumbo.wms.model.warehouse.CartonLine;
import com.jumbo.wms.model.warehouse.CartonLineCommand;

@Transactional
public interface CartonLineDao extends GenericEntityDao<CartonLine, Long> {

    /**
     * 查询执行量超出计划量 的商品
     * 
     * @return
     */
    @NativeQuery
    List<CartonLineCommand> findErrorSku(@QueryParam("staId") Long staId, @QueryParam("cartonId") Long cartonId, RowMapper<CartonLineCommand> r);

    /**
     * 查询所有装箱明细与计划差异
     * 
     * @param staId
     * @param r
     * @return
     */
    @NativeQuery
    List<CartonLineCommand> findDiffSku(@QueryParam("staId") Long staId, RowMapper<CartonLineCommand> r);
    
    /**
     * 根据staId获取完成装箱的明细总和
     *@param staId
     *@param singleColumnRowMapper
     *@return Long 
     *@throws
     */
    @NativeQuery
    Long findCompleteCartonLineCountByStaId(@QueryParam("staId") Long staId, SingleColumnRowMapper<Long> singleColumnRowMapper);
   
    @NativeQuery
    List<CartonLineCommand> findCartonLineGroupByCarId(@QueryParam(value = "id") Long Id,RowMapper<CartonLineCommand>rowMap);
    /**
     * 根据cartonId删除所有箱明细
     *@param cartonId void 
     *@throws
     */
    @NativeUpdate
    void deleteByCartonId(@QueryParam(value = "cartonId") Long cartonId);

}
