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
import java.util.Map;

import loxia.annotation.DynamicQuery;
import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.commandMapper.MapRowMapper;
import com.jumbo.wms.model.warehouse.InventoryStatus;

@Transactional
public interface InventoryStatusDao extends GenericEntityDao<InventoryStatus, Long> {
    /**
     * 查询库存状态
     * 
     * @param name
     * @param cmpouid
     * @return
     */
    @NamedQuery
    InventoryStatus getByName(@QueryParam(value = "name") String name, @QueryParam(value = "cmpouid") Long cmpouid);

    @DynamicQuery
    List<InventoryStatus> findInventoryListByIsSystem(@QueryParam("isSystem") Boolean isSystem, @QueryParam("ouId") Long ouId, Sort[] sorts);

    @NamedQuery
    List<InventoryStatus> findInvStatusListByCompany(@QueryParam(value = "companyId") Long companyId, Sort[] sorts);

    /**
     * 作业单明细库存状态查询
     * 
     * @param staId
     * @param staCode
     * @return
     */
    @NativeQuery
    List<InventoryStatus> findInvStatusListBySta(@QueryParam(value = "staId") Long staId, @QueryParam(value = "staCode") String staCode, BeanPropertyRowMapperExt<InventoryStatus> beanPropertyRowMapper);

    /**
     * 根据公司查寻 可用的销售
     * 
     * @param companyId
     * @return
     */
    @NamedQuery
    InventoryStatus findXSInvStatusByCompany(@QueryParam(value = "companyId") Long companyId);

    @NamedQuery
    InventoryStatus findInvStatusByCompanyANDName(@QueryParam(value = "companyId") Long companyId, @QueryParam(value = "name") String name);

    /**
     * 可销售的库存状态
     * 
     * @param ouId
     * @return
     */
    @NamedQuery
    InventoryStatus findInvStatusForSale(@QueryParam("ouId") Long ouId);


    /**
     * 根据组织查询库存状态
     * 
     * @param ouId
     * @param isForSale
     * @return
     */
    @NamedQuery
    InventoryStatus findInvStatusisForSale(@QueryParam("ouId") Long ouId, @QueryParam("isForSale") Boolean isForSale);

    @NamedQuery
    InventoryStatus findInvStatusisForSales(@QueryParam("ouId") Long ouId, @QueryParam("isForSale") Boolean isForSale);


    @NamedQuery
    InventoryStatus findInvStatusisByOuId(@QueryParam("ouId") Long ouId, @QueryParam("statusName") String statusName);

    /**
     * 根据组织查询库存状态
     * 
     * @param ouId
     * @param isForSale
     * @return
     */
    @NamedQuery
    InventoryStatus findGoodInvStatusisForSale(@QueryParam("ouId") Long ouId, @QueryParam("isForSale") Boolean isForSale, @QueryParam("description") String description);

    /**
     * 根据公司获得库存状态列表
     * 
     * @param ouid
     * @return
     */
    @NamedQuery
    List<InventoryStatus> findInvStatusByOuid(@QueryParam(value = "ouid") Long ouid);

    /**
     * 
     * @param ttId TransactionType id
     * @param ouid
     * @param skuId sku id
     * @param whLocId WarehouseLocation id
     * @param rowMap
     * @return
     */
    @NativeQuery
    List<InventoryStatus> findListByInventory(@QueryParam(value = "ttId") Long ttId, @QueryParam(value = "ouid") Long ouid, @QueryParam(value = "skuId") Long skuId, @QueryParam(value = "whLocId") Long whLocId, @QueryParam(value = "owner") String owner,
            RowMapper<InventoryStatus> rowMap);

    @NamedQuery
    InventoryStatus findByName(@QueryParam(value = "name") String name, @QueryParam(value = "ouid") Long ouid);

    @NamedQuery
    InventoryStatus findByNameUnionSystem(@QueryParam(value = "name") String name, @QueryParam(value = "ouid") Long ouid);

    /**
     * 查询库位上的商品的所有库存状态
     * 
     * @return
     */
    @NamedQuery
    List<InventoryStatus> findBySkuOnLocation(@QueryParam("skuId") Long skuId, @QueryParam("locId") Long locId);

    @NativeQuery
    Map<String, Long> findAllByCmpOu(@QueryParam(value = "ouid") Long ouid, MapRowMapper r);

    @NativeQuery
    List<InventoryStatus> findIsForSaleByCompanyId(@QueryParam(value = "cmpId") Long cmpId, BeanPropertyRowMapperExt<InventoryStatus> beanPropertyRowMapper);

    /**
     * 仓库所属公司下可销售的库存状态
     * 
     * @param warehouseOuId
     * @param beanPropertyRowMapperExt
     * @return
     */
    @NativeQuery
    List<InventoryStatus> findInvStatusForSaleByWarehouseOuId(@QueryParam(value = "warehouseOuId") Long warehouseOuId, BeanPropertyRowMapperExt<InventoryStatus> beanPropertyRowMapperExt);

    @NativeQuery
    InventoryStatus findByVmiSourceAndStatus(@QueryParam(value = "vmiSource") String vmiSource, @QueryParam(value = "invStatus") String invStatus, BeanPropertyRowMapperExt<InventoryStatus> beanPropertyRowMapperExt);

    /**
     * 根据库存状态ID和公司查询库存状态
     * 
     * @param invStatusId
     * @param ouId
     * @return
     */
    @NamedQuery
    InventoryStatus getByPrimaryKeyAndOuId(@QueryParam("invId") Long invStatusId, @QueryParam("ouId") Long ouId);

    /**
     * 根据公司id和库存状态idlist 查询存在的库存状态
     * 
     * @param list
     * @param id
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<Long> findInvStatusByIdAndOu(@QueryParam("idList") List<Long> list, @QueryParam("id") Long id, SingleColumnRowMapper<Long> singleColumnRowMapper);

    @NamedQuery
    InventoryStatus findByNameAndOu(@QueryParam("invName") String string, @QueryParam("ouId") Long id);

    @NamedQuery
    InventoryStatus findByStatusIdAndOu(@QueryParam("statusId") Long statusId, @QueryParam("ouId") Long id);

    @NamedQuery
    InventoryStatus getByNameAndWarehouse(@QueryParam("invName") String string, @QueryParam("vmiSource") String vmiSource, @QueryParam("vmiSourceWh") String vmiSourceWh);

    /**
     * 查询良品可销售库存状态
     * 
     * @param id
     * @return
     */
    @NamedQuery
    InventoryStatus findSalesAviliableStatus(@QueryParam("ouId") Long id);

    /**
     * 根据外包仓和反馈编码查找对应的库存状态
     * 
     * @param vmiSource
     * @param vmiSourceWh
     * @param statusName
     * @return
     */
    @NamedQuery
    InventoryStatus findInventoryByWhAndName(@QueryParam("vmiSource") String vmiSource, @QueryParam("vmiSourceWh") String vmiSourceWh, @QueryParam("statusName") String statusName);

    /**
     * 根据仓库库存及库存状态名称，商品id获取状态ID
     * 
     * @param skuId
     * @param ouId
     * @param statusName
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    Long findInvStatusBySkuIdAndOu(@QueryParam("skuId") Long skuId, @QueryParam("ouId") Long ouId, @QueryParam("statusName") String statusName, SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * 根据输出单据号查询获取库存状态
     * 
     * @param orderCode
     * @return
     */
    @NativeQuery
    InventoryStatus findInvStatusByorderCode(@QueryParam("orderCode") String orderCode, RowMapper<InventoryStatus> rowMapper);

    /**
     * 根据id查询状态名称
     */
    @NativeQuery
    InventoryStatus findStatusNameByid(@QueryParam("statusId") String statusId, RowMapper<InventoryStatus> rowMapper);
}
