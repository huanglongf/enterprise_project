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

import java.math.BigDecimal;
import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.InventoryCheckDifferenceLine;
import com.jumbo.wms.model.warehouse.InventoryCheckDifferenceLineCommand;

@Transactional
public interface InventoryCheckDifferenceLineDao extends GenericEntityDao<InventoryCheckDifferenceLine, Long> {

    @NamedQuery
    List<InventoryCheckDifferenceLine> findByInventoryCheck(@QueryParam("invCkId") Long invCkId);

    @NamedQuery
    List<InventoryCheckDifferenceLine> findCheckOverageListByIcid(@QueryParam("invCkId") Long invCkId);
    
    @NativeUpdate
    void deleteById(@QueryParam("id") Long id);

    /**
     * 查寻盘盈未处理的商品
     * 
     * @param invCkId
     * @return
     */
    @NamedQuery
    List<InventoryCheckDifferenceLine> findCheckOverageIsNullByIcid(@QueryParam("invCkId") Long invCkId);

    @NativeQuery(pagable = true)
    Pagination<InventoryCheckDifferenceLineCommand> findInvCkDifLineByInvCkId(int start, int pageSize, @QueryParam("invCkId") Long invCkId, Sort[] sort, RowMapper<InventoryCheckDifferenceLineCommand> rowMapper);

    @NativeQuery(pagable = true)
    Pagination<InventoryCheckDifferenceLineCommand> findInvCheckCountNoLocationByInvCheckId(int start, int pageSize, @QueryParam("invCkId") Long invCkId, Sort[] sort, RowMapper<InventoryCheckDifferenceLineCommand> rowMapper);

    @NativeQuery
    List<InventoryCheckDifferenceLineCommand> findCheckOverageByInvCk(@QueryParam("invCkId") Long invCkId, @QueryParam("ouId") Long ouId, @QueryParam("compId") Long compId, RowMapper<InventoryCheckDifferenceLineCommand> rowMapper);


    @NativeUpdate
    void createByInnentory(@QueryParam("icCode") String icCode);

    /**
     * 
     * @param invCheckId 盘点头ID
     * @param statusId 库存状态ID
     * @param owner 所属店铺
     * @param ouId 仓库OU
     */
    @NativeUpdate
    void saveCKinvCheckDifLine(@QueryParam("invCheckId") Long invCheckId, @QueryParam("statusId") Long statusId, @QueryParam("owner") String owner, @QueryParam("ouId") Long ouId, @QueryParam("status") Integer status,
            @QueryParam("batchNum") String batchNum);

    @NativeQuery
    BigDecimal findInvStatusIdByInvCheckId(@QueryParam("invCkId") Long invCkId, SingleColumnRowMapper<BigDecimal> rp);

    /*
     * @NativeQuery List<InventoryCheckDifferenceLineCommand> findByStaIdAndSkuIdInvStatusId(
     * 
     * @QueryParam("staId") Long staId, @QueryParam("skuId") Long skuId, @QueryParam("invStatusId")
     * Long invStatusId, RowMapper<InventoryCheckDifferenceLineCommand> r);
     */


    @NativeUpdate
    void generateInventoryCheckDifferenceLine(@QueryParam("skuId") Long skuId, @QueryParam("qty") long qty, @QueryParam("inventoryCheckId") Long inventoryCheckId, @QueryParam("districtId") Long districtId, @QueryParam("locationId") Long locationId,
            @QueryParam("invStatusId") Long invStatusId, @QueryParam("owner") String owner, @QueryParam("skuCost") BigDecimal skuCost);

    /**
     * 根据盘点批查询盘点明细
     * 
     * @param id
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<InventoryCheckDifferenceLineCommand> getDifferentDetailByInventoryCheckId(@QueryParam("invId") Long id, BeanPropertyRowMapper<InventoryCheckDifferenceLineCommand> beanPropertyRowMapper);

    /**
     * 根据库存占用重新生成盘亏差异明细
     * 
     * @param code
     * @param id
     */
    @NativeUpdate
    void reCreateOutDifferentLineById(@QueryParam("code") String code, @QueryParam("invId") Long id);

    /**
     * 删除盘亏差异明细
     * 
     * @param id
     */
    @NativeUpdate
    void deleteOutDifferentLineById(@QueryParam("invId") Long id);
    
    /**
     * 查询  差异数据
     * @param icId
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<InventoryCheckDifferenceLineCommand> getSortingDifferentByInvCheck(@QueryParam("icId") Long icId, BeanPropertyRowMapper<InventoryCheckDifferenceLineCommand> beanPropertyRowMapper);
    
    /**
     * 更新 差异数量
     * @param lineId
     * @param qty
     */
    @NativeUpdate
    void updateLineQty(@QueryParam("lineId") Long lineId,@QueryParam("qty") Long qty);
}
