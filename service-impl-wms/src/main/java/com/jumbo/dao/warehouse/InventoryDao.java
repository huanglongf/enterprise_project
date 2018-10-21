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

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.commandMapper.MapInvMapper;
import com.jumbo.dao.commandMapper.MapInvOwnerMapper;
import com.jumbo.dao.commandMapper.MapQtyRowMapper;
import com.jumbo.dao.commandMapper.MapRowMapper;
import com.jumbo.wms.model.pda.InboundOnShelvesDetailCommand;
import com.jumbo.wms.model.pda.InboundOnShelvesSkuCommand;
import com.jumbo.wms.model.vmi.adidasData.AdidasSalesInventory;
import com.jumbo.wms.model.vmi.adidasData.AdidasTotalInventory;
import com.jumbo.wms.model.vmi.af.AFinventoryCommand;
import com.jumbo.wms.model.vmi.reebokData.ReebokSalesInventory;
import com.jumbo.wms.model.vmi.reebokData.ReebokTotalInventory;
import com.jumbo.wms.model.warehouse.Inventory;
import com.jumbo.wms.model.warehouse.InventoryCommand;
import com.jumbo.wms.model.warehouse.InventoryOccupyCommand;
import com.jumbo.wms.model.warehouse.InventoryReport;
import com.jumbo.wms.model.warehouse.LogQueueHub;
import com.jumbo.wms.model.warehouse.OccupationInfoCommand;
import com.jumbo.wms.model.warehouse.WarehouseDataCommand;

import loxia.annotation.DynamicQuery;
import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;

@Transactional
public interface InventoryDao extends GenericEntityDao<Inventory, Long> {

    /**
     * 增量数据插入（通知hub）
     */
    @NativeUpdate
    public void salesInventoryGncAdd(@QueryParam(value = "datetime") Date datetime);

    @NativeUpdate
    public void backUpInv(@QueryParam("code") String code, @QueryParam("owners") String owners, @QueryParam("ouNames") List<String> ouNames);

    @NativeUpdate
    public void backUpSta(@QueryParam("code") String code, @QueryParam("owners") String owners, @QueryParam("ouNames") List<String> ouNames);

    /**
     * 增量数据插入（通知hub-Adidas）
     */
    @NativeUpdate
    public void salesInventoryAdidasAdd(@QueryParam(value = "datetime") Date datetime);

    /**
     * 增量数据删除（通知hub）
     */
    @NativeUpdate
    public void delSalesInventoryGncAdd(@QueryParam(value = "datetime") Date datetime);

    /**
     * 增量数据删除（通知hub-Adidas）
     */
    @NativeUpdate
    public void delSalesInventoryAdidasAdd(@QueryParam(value = "datetime") Date datetime);

    @NativeQuery
    InventoryCommand checkLocIsInv(@QueryParam("ouId") Long ouId, @QueryParam("locCode") String locCode, RowMapper<InventoryCommand> r);

    /**
     * 查询仓库店铺
     */
    @NativeQuery
    List<InventoryCommand> findOwnerByInv(@QueryParam("ouid") Long ouid, RowMapper<InventoryCommand> r);

    // @NativeQuery
    // Map<String, Long> findSalesAvialQtyNoShareBh(@QueryParam("soCodes") List<String> soCodes,
    // @QueryParam("whId") Long whId, MapRowMapper r);

    // @NativeQuery
    // Map<String, Long> findSalesAvialQtyWithShareBh(@QueryParam("soCodes") List<String> soCodes,
    // @QueryParam("whId") Long whId, MapRowMapper r);

    @NativeQuery
    List<InventoryCommand> findInvByOwnerAndOuId(@QueryParam("owners") String owners, @QueryParam("ouNames") List<String> ouNames, RowMapper<InventoryCommand> r);


    @NativeQuery
    Boolean findInvByOwnerAndOuIdIsOccupied(@QueryParam("owners") String owners, @QueryParam("ouNames") List<String> ouNames, SingleColumnRowMapper<Boolean> r);

    /**
     * 查询仓库品牌
     */
    @NativeQuery
    List<InventoryCommand> findBrandByInv(@QueryParam("ouid") Long ouid, RowMapper<InventoryCommand> r);

    @NativeUpdate
    void updateDistrict(@QueryParam("ouId") Long ouId);

    @NativeUpdate
    void updateByOuId(@QueryParam("ouId") Long ouId);

    /**
     * 根据库位、商品、库存状态查询库存
     * 
     * @param skuId
     * @param locId
     * @return
     */
    @NamedQuery
    List<Inventory> findBySkuLocationAndStatus(@QueryParam("skuId") Long skuId, @QueryParam("locId") Long locId, @QueryParam("statusId") Long statusId);

    /**
     * 查寻未被库位占用的库存数据
     * 
     * @param skuId
     * @param locId
     * @param statusId
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<Inventory> findBySkuLocAndStatus(@QueryParam("skuId") Long skuId, @QueryParam("locId") Long locId, @QueryParam("statusId") Long statusId, @QueryParam("ouId") Long ouId, RowMapper<Inventory> beanPropertyRowMapper);

    /**
     * 通过占用编码查询被占用的库存
     * 
     * @param occupiedCode
     * @return
     */
    @NamedQuery
    List<Inventory> findByOccupiedCode(@QueryParam("occupiedCode") String occupiedCode);

    /**
     * 指定库区是否有库存量>0的库存
     * 
     * @param districtId
     * @return
     */
    @DynamicQuery(value = "Inventory.findInventoryByDistrictOrLocation")
    Inventory findInventoryByDistrict(@QueryParam("districtId") Long districtId);

    /**
     * 指定库位是否有库存量>0的库存
     * 
     * @param districtId
     * @return
     */
    @DynamicQuery(value = "Inventory.findInventoryByDistrictOrLocation")
    Inventory findInventoryByLocation(@QueryParam("locationId") Long locationId);

    /**
     * 查询当前仓库下的店铺信息
     * 
     * @return
     */
    @NativeQuery
    List<InventoryCommand> findInventoryCommandList(RowMapper<InventoryCommand> rowMapper);

    /**
     * 已占用库存量列表查询
     * 
     * @param queryParams
     * @return
     */
    @NativeQuery
    List<OccupationInfoCommand> findOccupationInfo(@QueryParam Map<String, Object> m, RowMapper<OccupationInfoCommand> rowMapper, Sort[] sorts);

    @NamedQuery
    Long findInventoryBySkuId(@QueryParam("skuId") Long skuId, SingleColumnRowMapper<Long> s);

    /**
     * skuId不是必填条件
     * 
     * @param skuId
     * @param code
     * @param ouId
     * @param s
     * @return
     */
    @NativeQuery
    BigDecimal findInventoryBySkuIdandLocationId(@QueryParam("skuId") Long skuId, @QueryParam("code") String code, @QueryParam("ouId") Long ouId, SingleColumnRowMapper<Long> s);

    @NativeQuery
    BigDecimal findInventoryBySkuIdandLocationIdandInvStatus(@QueryParam("skuId") Long skuId, @QueryParam("code") String code, @QueryParam("ouId") Long ouId, @QueryParam("invStatus") Long invStatus, SingleColumnRowMapper<Long> s);

    @NativeQuery
    List<Inventory> findInventoryByCode(@QueryParam("ouId") Long ouId, @QueryParam("code") String code, RowMapper<Inventory> rowMapper);

    @NativeQuery
    List<Long> findInventorySkuByCode(@QueryParam("ouId") Long ouId, @QueryParam("code") String code, RowMapper<Long> rowMapper);

    @NativeQuery
    BigDecimal findInventoryNumByLocationId(@QueryParam(value = "locationId") Long locationId, SingleColumnRowMapper<BigDecimal> s);

    @NativeQuery
    List<Long> findInventoryNoSkuByCode(@QueryParam("locationId") Long locationId, @QueryParam("skuId") Long skuId, RowMapper<Long> rowMapper);

    /**
     * 
     * @param staId
     * @param isNotFinish
     * @param isSnSku
     * @param sorts
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<InventoryCommand> findFlittingOutInfoByStaId(@QueryParam(value = "staId") Long staId, RowMapper<InventoryCommand> rowMapper);



    /**
     * 查询禁止入库库位
     * 
     * @param stvId
     * @param ouid
     * @param r
     * @return
     */
    @NativeQuery
    List<String> findErrorSkuLocation(@QueryParam(value = "stvid") Long stvId, @QueryParam(value = "ouid") Long ouid, SingleColumnRowMapper<String> r);

    /**
     * 根据时间查询时间段库存快照库存记录
     * 
     * @param start
     * @param pageSize
     * @param startDate
     * @param endDate
     * @param barCode
     * @param jmCode
     * @param skuName
     * @param supplierSkuCode
     * @param invOwner
     * @param rowMapper
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<InventoryCommand> findSnapShotPageByDate(int start, int pageSize, @QueryParam("startDate") Date startDate, @QueryParam("endDate") Date endDate, @QueryParam("barCode") String barCode, @QueryParam("skuCode") String skuCode,
            @QueryParam("brandName") String brandName, @QueryParam("jmCode") String jmCode, @QueryParam("skuName") String skuName, @QueryParam("supplierSkuCode") String supplierSkuCode, @QueryParam("invOwner") String invOwner,
            @QueryParam("whOuId") String whOuId, @QueryParam("ouIds") List<String> ouIds, @QueryParam("companyid") Long companyid, RowMapper<InventoryCommand> rowMapper, Sort[] sorts);

    /**
     * 查询某一时刻库存
     * 
     * @param start
     * @param pageSize
     * @param date
     * @param barCode
     * @param skuCode
     * @param skuName
     * @param supplierSkuCode
     * @param invOwner
     * @param whOuId
     * @param rowMapper
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<InventoryCommand> findSnapShotPageByPreciseTime(int start, int pageSize, @QueryParam("date") Date date, @QueryParam("barCode") String barCode, @QueryParam("skuCode") String skuCode, @QueryParam("brandName") String brandName,
            @QueryParam("jmCode") String jmCode, @QueryParam("skuName") String skuName, @QueryParam("supplierSkuCode") String supplierSkuCode, @QueryParam("invOwner") String invOwner, @QueryParam("whOuId") String whOuId,
            @QueryParam("ouIds") List<String> ouIds, @QueryParam("companyid") Long companyid, @QueryParam("isShowZero") Boolean isShowZero, RowMapper<InventoryCommand> rowMapper, Sort[] sorts);

    /**
     * 查询当前库存
     * 
     * @param start
     * @param pageSize
     * @param barCode
     * @param jmCode
     * @param skuName
     * @param supplierSkuCode
     * @param owner
     * @param whOuId
     * @param rowMapper
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<InventoryCommand> findCurrentInventoryByPage(int start, int pageSize, @QueryParam("barCode") String barCode, @QueryParam("skuCode") String skuCode, @QueryParam("extCode2") String extCode2, @QueryParam("jmCode") String jmCode,
            @QueryParam("skuName") String skuName, @QueryParam("supplierSkuCode") String supplierSkuCode, @QueryParam("brandName") String brandName, @QueryParam("invOwner") String invOwner, @QueryParam("whOuId") Long whOuId,
            @QueryParam("companyid") Long companyid, @QueryParam("isShowZero") Boolean isShowZero, @QueryParam("extCode1") String extCode1, @QueryParam("numberUp") Long numberUp, @QueryParam("amountTo") Long amountTo,
            RowMapper<InventoryCommand> rowMapper, Sort[] sorts);

    /**
     * 数量占用库存查询
     * 
     * @param ouId
     * @param skuId
     * @param owner
     * @param rowMapper
     * @return
     */
    @NativeQuery
    InventoryCommand findQtyOccupiedInv(@QueryParam("ouId") Long ouId, @QueryParam("skuId") Long skuId, @QueryParam("owner") String owner, RowMapper<InventoryCommand> rowMapper);

    /**
     * 数量占用库存查询
     * 
     * @param ouId
     * @param skuId
     * @param owner
     * @param rowMapper
     * @return
     */
    @NativeQuery
    InventoryCommand findSalesQtyInv(@QueryParam("ouId") Long ouId, @QueryParam("skuId") Long skuId, @QueryParam("owner") String owner, RowMapper<InventoryCommand> rowMapper);


    /**
     * 查询当前库存 new
     * 
     * @param start
     * @param pageSize
     * @param barCode
     * @param jmCode
     * @param skuName
     * @param supplierSkuCode
     * @param owner
     * @param whOuId
     * @param rowMapper
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<InventoryCommand> findCurrentInventoryByPageNew(int start, int pageSize, @QueryParam("barCode") String barCode, @QueryParam("skuCode") String skuCode, @QueryParam("extCode2") String extCode2, @QueryParam("jmCode") String jmCode,
            @QueryParam("skuName") String skuName, @QueryParam("supplierSkuCode") String supplierSkuCode, @QueryParam("brandName") String brandName, @QueryParam("invOwner") String invOwner, @QueryParam("whOuId") Long whOuId,
            @QueryParam("companyid") Long companyid, @QueryParam("isShowZero") Boolean isShowZero, @QueryParam("extCode1") String extCode1, @QueryParam("numberUp") Long numberUp, @QueryParam("amountTo") Long amountTo,
            RowMapper<InventoryCommand> rowMapper, Sort[] sorts);

    @NativeQuery
    List<InventoryCommand> findCurrentInventory(@QueryParam("barCode") String barCode, @QueryParam("skuCode") String skuCode, @QueryParam("jmCode") String jmCode, @QueryParam("skuName") String skuName,
            @QueryParam("supplierSkuCode") String supplierSkuCode, @QueryParam("brandName") String brandName, @QueryParam("invOwner") String invOwner, @QueryParam("whOuId") Long whOuId, @QueryParam("companyid") Long companyid,
            @QueryParam("isShowZero") Boolean isShowZero, RowMapper<InventoryCommand> rowMapper);


    @NativeQuery
    List<InventoryCommand> findCurrentConverseInventory(@QueryParam("vmicode") String vmicode, RowMapper<InventoryCommand> rowMapper);

    @NativeQuery
    List<InventoryCommand> findCurrentPhilipsInventory(@QueryParam("shopId") Long shopId, RowMapper<InventoryCommand> rowMapper);

    /**
     * 根据Sku id 查询库区库位库存
     * 
     * @param skuId
     * @param owner
     * @param whOuId
     * @param rowMapper
     * @param sorts
     * @return
     */
    @NativeQuery
    List<InventoryCommand> findInventoryBySku(@QueryParam("skuId") Long skuId, @QueryParam("owner") String owner, @QueryParam("whOuId") Long whOuId, RowMapper<InventoryCommand> rowMapper, Sort[] sorts);

    /**
     * 根据公司角色获取仓库信息
     * 
     * @param skuId
     * @param rowMap
     * @return
     */
    @NativeQuery
    List<WarehouseDataCommand> getWarehouseDataByCompany(@QueryParam("ouId") Long ouId, RowMapper<WarehouseDataCommand> rowMap);

    /**
     * 根据仓库角色获取仓库信息
     * 
     * @param skuId
     * @param rowMap
     * @return
     */
    @NativeQuery
    List<WarehouseDataCommand> getWarehouseDataByWarehouse(@QueryParam("ouId") Long ouId, RowMapper<WarehouseDataCommand> rowMap);

    /**
     * @deprecated
     * @param stvId
     */
    @NativeUpdate
    void createZeroInventoryForSpecial(@QueryParam("stvId") Long stvId);

    /**
     * 根据sta释放库存
     * 
     * @param staId
     */
    @NativeUpdate
    int releaseInventoryByOpcOcde(@QueryParam("code") String code);


    /**
     * 释放库存
     * 
     * @param code
     */
    @NativeUpdate
    int releaseInventoryByStaId(@QueryParam("staId") Long staId);

    /**
     * 查寻库存明细
     * 
     * @param start
     * @param pageSize
     * @param barCode
     * @param jmCode
     * @param skuName
     * @param supplierSkuCode
     * @param owner
     * @param whOuId
     * @param rowMapper
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<InventoryCommand> findDetailsInventoryByPage(int start, int pageSize, @QueryParam("jmCode") String jmCode, @QueryParam("skuCode") String skuCode, @QueryParam("extCode2") String extCode2, @QueryParam("barCode") String barCode,
            @QueryParam("skuName") String skuName, @QueryParam("supplierSkuCode") String supplierSkuCode, @QueryParam("locationCode") String locationCode, @QueryParam("invOwner") String invOwner, @QueryParam("whOuId") Long whOuId,
            @QueryParam("companyid") Long companyid, @QueryParam("statusId") Long statusId, RowMapper<InventoryCommand> rowMapper, Sort[] sorts);

    /**
     * 根据仓库查询所有库存
     * 
     * @param start
     * @param pageSize
     * @param whOuId
     * @param cmpOuId
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<InventoryCommand> findAllByOuid(int start, int pageSize, @QueryParam("whOuId") Long whOuId, RowMapper<InventoryCommand> rowmap, Sort[] sotrs);

    @NativeQuery(withGroupby = true)
    List<InventoryCommand> findByInventoryCheckId(@QueryParam("invCkId") Long invCkId, @QueryParam("isSnSku") Boolean isSnSku, RowMapper<InventoryCommand> rowmap, Sort[] sort);

    @NativeQuery(withGroupby = true)
    Map<String, Long> findQtyByInventoryCheck(@QueryParam("invCkId") Long invCkId, MapRowMapper r);

    /**
     * 
     * @param locationCode
     * @param shopId
     * @param ouid
     * @param rowMap
     * @return
     */
    @NativeQuery
    List<InventoryCommand> findByLocAndSku(@QueryParam(value = "locationCode") String locationCode, @QueryParam(value = "skuCode") String skuCode, @QueryParam(value = "shopId") Long shopId, @QueryParam(value = "skuId") Long skuId,
            @QueryParam(value = "ouid") Long ouid, @QueryParam(value = "inventoryStatusId") Long inventoryStatusId, @QueryParam(value = "productionDate") String productionDate, @QueryParam(value = "expireDate") String expireDate,
            RowMapper<InventoryCommand> rowMap);

    /**
     * 根据库位 商品编码 获取商品列表 +数量
     * 
     * @param locationCode
     * @param skuId
     * @param shopId
     * @param statusId
     * @param ouid
     * @param rowMap
     * @return
     */
    @NativeQuery
    List<InventoryCommand> findByLocationAdnSku(@QueryParam(value = "locationCode") String locationCode, @QueryParam(value = "skuCode") String skuCode, @QueryParam(value = "skuId") Long skuId, @QueryParam(value = "shopId") Long shopId,
            @QueryParam(value = "statusId") Long statusId, @QueryParam(value = "ouid") Long ouid, RowMapper<InventoryCommand> rowMap);

    /**
     * 根据库位 商品编码 获取商品列表 +数量,不带店铺分组
     * 
     * @param locationCode
     * @param skuId
     * @param shopId
     * @param statusId
     * @param ouid
     * @param rowMap
     * @return
     */
    @NativeQuery
    List<InventoryCommand> findByLocationAdnSkuWithoutOwner(@QueryParam(value = "locationCode") String locationCode, @QueryParam(value = "skuCode") String skuCode, @QueryParam(value = "skuId") Long skuId, @QueryParam(value = "statusId") Long statusId,
            @QueryParam(value = "ouid") Long ouid, RowMapper<InventoryCommand> rowMap);

    /**
     * 根据库位获取库位上的商品
     * 
     * @param barCode
     * @param jmCode
     * @param keyProperties
     * @return
     */
    @NativeQuery
    List<InventoryCommand> findByLocationCode(@QueryParam("locationCode") String locationCode, @QueryParam("shopId") Long shopId, @QueryParam("ouid") Long ouid, RowMapper<InventoryCommand> rowMapper);


    /**
     * 根据skuCode获取库位 以及商品数量
     * 
     * @param barCode
     * @param jmCode
     * @param keyProperties
     * @return
     */
    @NativeQuery
    List<InventoryCommand> findBySkuCode(@QueryParam("skuCode") String skuCode, @QueryParam("shopId") Long shopId, @QueryParam("ouid") Long ouid, RowMapper<InventoryCommand> rowMapper);


    /**
     * 根据库存查询店铺
     * 
     * @param skuId
     * @return
     */
    @NativeQuery
    String findOwnerBySku(@QueryParam("skuId") Long skuId, @QueryParam("ouid") Long ouid, SingleColumnRowMapper<String> r);

    @NativeQuery(withGroupby = true)
    List<InventoryCommand> findSkuQtyByLocationWithStatus(@QueryParam("skuId") Long skuId, @QueryParam("locId") Long locationId, RowMapper<InventoryCommand> r);

    @NativeQuery
    Integer findInventoryCountByStatusIdSql(@QueryParam("invstatusid") Long invstatusId, RowMapper<Integer> rp);

    /**
     * 根据skuid locationid 查询库存sku信息
     * 
     * @param skuId
     * @param locId
     * @param locationCode
     * @param r
     * @return
     */
    @NativeQuery
    Long findInventoryBySkuIdLocationId(@QueryParam("skuId") Long skuId, @QueryParam("locId") Long locId, @QueryParam("locationCode") String locationCode, RowMapper<Long> r);

    @NativeQuery
    List<InventoryCommand> findInventoryBySkuIdLocationIdOwner(@QueryParam("skuId") Long skuId, @QueryParam("locId") Long locId, @QueryParam("owner") String owner, @QueryParam("ouId") Long ouId, RowMapper<InventoryCommand> r);


    @NativeQuery
    Long find1InventoryBySkuIdLocationIdOwner(@QueryParam("skuId") Long skuId, @QueryParam("locId") Long locId, @QueryParam("owner") String owner, @QueryParam("ouId") Long ouId, @QueryParam("statusId") Long statusId, SingleColumnRowMapper<Long> r);

    @NativeQuery
    List<InventoryCommand> findInventoryBySkuIdLocationIdOwner2(@QueryParam("skuId") Long skuId, @QueryParam("locId") Long locId, @QueryParam("owner") String owner, @QueryParam("ouId") Long ouId, @QueryParam("statusId") Long statusId,
            RowMapper<InventoryCommand> r);

    /**
     * 销售库存时点查询
     * 
     * @param ouid
     * @param date
     * @param jmcode
     * @param productCategory
     * @param productLine
     * @param consumerGroup
     * @param r
     * @return
     */
    @NativeQuery
    List<InventoryCommand> findSalesReportInvnentory(@QueryParam("ouid") Long ouid, @QueryParam("date") Date date, @QueryParam("supplierSkuCode") String supplierSkuCode, @QueryParam("productCategory") String productCategory,
            @QueryParam("productLine") String productLine, @QueryParam("consumerGroup") String consumerGroup, RowMapper<InventoryCommand> r);

    @NativeQuery
    List<InventoryCommand> findLeivsCurrentInv(@QueryParam("shopid") Long shopId, @QueryParam("supplierSkuCode") String supplierSkuCode, @QueryParam("productCategory") String productCategory, @QueryParam("productLine") String productLine,
            @QueryParam("consumerGroup") String consumerGroup, RowMapper<InventoryCommand> r);

    /**
     * 
     * @param skuid
     * @param locationid
     * @return
     */
    @NativeQuery
    List<String> findBatchCodeBySkuIdLocationId(@QueryParam("skuid") Long skuid, @QueryParam("locationid") Long locationid, RowMapper<String> r);

    /**
     * @param staId
     * @param addiowner
     */
    @NativeUpdate
    void releaseInvAndUpdateOwnerByStaId(@QueryParam("staId") Long staId, @QueryParam("addiowner") String addiowner);

    /**
     * 根据商品查询库存
     * 
     * @param skuId
     * @param locId
     * @return
     */
    @NativeQuery
    List<Long> findBySkuId(@QueryParam("skuId") Long skuId, SingleColumnRowMapper<Long> r);

    /**
     * 根据库存ID占用库存份
     * 
     * @param id 库存ID
     * @param occupyCode 占用码
     * @param qty 占用数量
     */
    @NativeUpdate
    void occupyInvById(@QueryParam("id") Long id, @QueryParam("occupyCode") String occupyCode, @QueryParam("qty") Long qty);

    /**
     * 根据sta校验
     * 
     * @param staId
     * @param r
     * @return
     */
    @Deprecated
    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<InventoryCommand> validateOccupyByStaId(int start, int pageSize, @QueryParam("staId") Long staId, RowMapper<InventoryCommand> r);

    /**
     * 根据sta校验 fanht
     * 
     * @param staId
     * @param r
     * @return
     */
    @NativeQuery
    List<InventoryCommand> validateOccupyByStaId(@QueryParam("staId") Long staId, RowMapper<InventoryCommand> r);

    /**
     * 查寻库位上商品数
     * 
     * @param locId
     * @param r
     * @return
     */
    @NativeQuery
    List<InventoryCommand> queryGILocSku(@QueryParam("locId") Long locId, RowMapper<InventoryCommand> r);


    /**
     * 查询销售出库需占用的库存信息
     * 
     * @param staId
     * @param r
     * @return
     */
    @NativeQuery
    List<InventoryOccupyCommand> findSalesOutboundToOccupyInventory(@QueryParam("saleOcpType") Integer saleOcpType, @QueryParam("staid") Long staId, @QueryParam("wooCode") String wooCode, RowMapper<InventoryOccupyCommand> r);

    /**
     * 新查询销售出库需占用的库存信息
     * 
     * @param staId
     * @param r
     * @return
     */
    @NativeQuery
    List<InventoryOccupyCommand> findSalesOutboundToOccupyInventoryNew(@QueryParam("saleOcpType") Integer saleOcpType, @QueryParam("staid") Long staId, @QueryParam("wooCode") String wooCode, @QueryParam("lcodes") List<String> lcodes,
            RowMapper<InventoryOccupyCommand> r);

    /**
     * 取消库存占用
     * 
     * @param code
     */
    @NativeUpdate
    void updateOccupyIsNull(@QueryParam("occupyCode") String occupyCode);

    /**
     * 库间移动 - 库存占用
     * 
     * @param id
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<InventoryOccupyCommand> findForTransitCrossToOccupyInventory(@QueryParam("staid") Long staid, RowMapper<InventoryOccupyCommand> r);

    @NativeQuery
    List<InventoryOccupyCommand> findForPartOutBoundInventory(@QueryParam("staCode") String staCode, @QueryParam("skuId") Long skuId, RowMapper<InventoryOccupyCommand> r);

    /**
     * 释放库存
     * 
     * @param occupationCode
     * @param qty
     */
    @NativeUpdate
    void releaseInventoryById(@QueryParam("id") Long id, @QueryParam("qty") Long qty);

    /**
     * 根据占用批编码释放库存
     * 
     * @param ocpCode
     */
    @NativeUpdate
    void releaseInventoryByOcpCode(@QueryParam("ocpCode") String ocpCode);

    @NativeUpdate
    void releaseStaByOcpCode();

    @NativeUpdate
    void releaseStaByOcpCode2();

    /**
     * 
     * @param batchCode
     * @param skuId
     * @param qty
     * @param districtId
     * @param locationId
     * @param ouId
     * @param statusId
     * @param owner
     * @param inboundTime
     * @param skuCost
     * @param occupationCode
     * @param isOccupied
     */
    @NativeUpdate
    void generateNewInventory(@QueryParam("batchCode") String batchCode, @QueryParam("skuId") Long skuId, @QueryParam("qty") Long qty, @QueryParam("districtId") Long districtId, @QueryParam("locationId") Long locationId, @QueryParam("ouId") Long ouId,
            @QueryParam("statusId") Long statusId, @QueryParam("owner") String owner, @QueryParam("inboundTime") Date inboundTime, @QueryParam("skuCost") BigDecimal skuCost, @QueryParam("occupationCode") String occupationCode);

    @NativeUpdate
    void generateNewInventoryForTransCrossThreePl(@QueryParam("stvId") Long stvId);

    /**
     * 查寻当前库位上面为占用的商品库存
     * 
     * @param locId
     * @param beanPropertyRowMapperExt
     * @return
     */
    @NativeQuery
    List<InventoryCommand> findLocationSkuByLocId(@QueryParam("locId") Long locId, BeanPropertyRowMapperExt<InventoryCommand> beanPropertyRowMapperExt);

    /**
     * 查寻当前库位上面为占用的SN商品库存
     * 
     * @param locId
     * @param beanPropertyRowMapperExt
     * @return
     */
    @NativeQuery
    List<InventoryCommand> findLocationSNSkuByLocId(@QueryParam("locId") Long locId, BeanPropertyRowMapperExt<InventoryCommand> beanPropertyRowMapperExt);

    @NativeQuery
    List<InventoryReport> findStatisticsInv(BeanPropertyRowMapper<InventoryReport> beanPropertyRowMapper);

    @NativeQuery
    List<InventoryReport> findStatisticsInv1(BeanPropertyRowMapper<InventoryReport> beanPropertyRowMapper);

    @NativeQuery
    List<InventoryReport> findStatisticsInv2(BeanPropertyRowMapper<InventoryReport> beanPropertyRowMapper);


    @NamedQuery
    List<Inventory> finInventoriesByinvOwner(@QueryParam("invOwner") String invOwner, @QueryParam("ouId") Long ouId);

    @NativeQuery
    Map<Long, Long> findSalesTotalInvForChangeOwner(@QueryParam("groupCode") String groupCode, @QueryParam("whid") Long whid, MapQtyRowMapper r);

    @NativeQuery
    Map<Long, Long> findSalesCurrentInvForChangeOwner(@QueryParam("groupCode") String groupCode, @QueryParam("owner") String owner, @QueryParam("whid") Long whid, MapQtyRowMapper r);

    @NativeQuery
    List<InventoryCommand> getInventoryForPda(@QueryParam("location") String location, @QueryParam("skuBarCode") String skuBarCode, @QueryParam("uniqCode") String uniqCode, BeanPropertyRowMapper<InventoryCommand> beanPropertyRowMapper);

    /**
     * Pda 获取pda退货单数据 detail
     * 
     * @param code
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<InboundOnShelvesDetailCommand> findReturnOrderDetail(@QueryParam("code") String code, @QueryParam("isNeedInvStatus") Boolean isNeedInvStatus, BeanPropertyRowMapper<InboundOnShelvesDetailCommand> beanPropertyRowMapper);

    /***
     * Pda获取退货单数据sku
     * 
     * @param code
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<InboundOnShelvesSkuCommand> findReturnOrderSkus(@QueryParam("code") String code, BeanPropertyRowMapper<InboundOnShelvesSkuCommand> beanPropertyRowMapper);


    @NativeQuery
    List<InventoryCommand> findCoachSnapShotPageByPreciseTime(@QueryParam("date") Date date, @QueryParam("brandName") String brandName, @QueryParam("invOwner") String invOwner, @QueryParam("ouIds") List<String> ouIds, RowMapper<InventoryCommand> rowMapper);

    /**
     * 推荐拣货区库位存在良品库位 KJL
     * 
     * @param whId
     * @param skuId
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    Long getRecommendLocationId(@QueryParam("whId") Long whId, @QueryParam("skuId") Long skuId, SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * 获取部分出库差异明细 KJL
     * 
     * @param plId
     * @param staId
     * @param ouId
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<InventoryCommand> getPartlyOutboundInventory(Long plId, Long staId, Long ouId, BeanPropertyRowMapper<InventoryCommand> beanPropertyRowMapper);

    /**
     * 释放库存 更新库存 KJL
     * 
     * @param id
     * @param object
     */
    @NativeUpdate
    void updateInventoryById(@QueryParam("invId") Long id, @QueryParam("qty") Long qty);

    /**
     * 拆分后插入新的库存份 KJL
     * 
     * @param id
     * @param l
     */
    @NativeUpdate
    void insertNewInventory(@QueryParam("invId") Long invId, @QueryParam("qty") Long qty);


    /**
     * 预售订单取消插入新的库存
     * 
     * @param id
     * @param l
     */
    @NativeUpdate
    void insertNewInventoryPre(@QueryParam("staCode") String staCode);

    /**
     * 查找错误信息 KJL
     * 
     * @param stvId
     * @param staId
     * 
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    String selectErrorSku(@QueryParam("staId") Long staId, @QueryParam("stvId") Long stvId, SingleColumnRowMapper<String> singleColumnRowMapper);

    /**
     * 根据仓库，商品查看可以补货情况
     * 
     * @param ouId
     * @param skuId
     * @param innerShopCode
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<InventoryCommand> findCanReplenishInventory(@QueryParam("ouId") Long ouId, @QueryParam("skuId") Long skuId, @QueryParam("shop") String innerShopCode, BeanPropertyRowMapper<InventoryCommand> beanPropertyRowMapper);

    /**
     * 根据商品和仓库获取拣货区库存
     * 
     * @param ouId
     * @param skuId
     * @param disId
     * @param innerShopCode
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    Long getPickingInventoryBySkuAndOu(@QueryParam("ouId") Long ouId, @QueryParam("skuId") Long skuId, @QueryParam("disId") Long disId, @QueryParam("shop") String innerShopCode, SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * 根据商品和仓库获取存货区库存
     * 
     * @param ouId
     * @param skuId
     * @param innerShopCode
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    Long getStockInventoryBySkuAndOu(@QueryParam("ouId") Long ouId, @QueryParam("skuId") Long skuId, @QueryParam("shop") String innerShopCode, SingleColumnRowMapper<Long> singleColumnRowMapper);


    /**
     * 查询库存占用
     * 
     * @param ouId
     * @param skuId
     * @param statusId
     * @param owner
     */
    @NativeQuery
    List<Inventory> findInventory(@QueryParam("staid") Long staid, @QueryParam("locationid") String locationid, @QueryParam("skuid") Long skuid, RowMapper<Inventory> mapper);

    /**
     * 查询库存数量
     * 
     * @param ouId
     * @param skuId
     * @param statusId
     * @param owner
     */
    @NativeQuery
    Integer findInventorysum(@QueryParam("staid") Long staid, @QueryParam("locationid") String locationid, @QueryParam("skuid") Long skuid, RowMapper<Integer> mapper);

    /**
     * 查询库存数量
     * 
     * @param ouId
     * @param skuId
     * @param statusId
     * @param owner
     */
    @NativeQuery
    Integer findInventorycontrast(@QueryParam("code") String code, RowMapper<Integer> mapper);


    /**
     * 根据作业类型和staId 查询可供占用的库存 KJL
     * 
     * @param code
     * @param id
     * @param type
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<InventoryOccupyCommand> findForOccupyInventory(@QueryParam("code") String code, @QueryParam("id") Long id, @QueryParam("type") int type, BeanPropertyRowMapper<InventoryOccupyCommand> beanPropertyRowMapper);

    /**
     * 根据作业类型和staId 查询可供占用的库存,关联库位
     * 
     * @param code
     * @param id
     * @param type
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<InventoryOccupyCommand> findForOccupyInventoryCommon(@QueryParam("code") String code, @QueryParam("id") Long id, @QueryParam("type") int type, BeanPropertyRowMapper<InventoryOccupyCommand> beanPropertyRowMapper);


    /**
     * 根据staId
     * 
     * @param staId
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<InventoryOccupyCommand> findForOccupyInventoryCheck(@QueryParam("staId") Long staId, BeanPropertyRowMapper<InventoryOccupyCommand> beanPropertyRowMapper);


    /**
     * 查询非共享仓库销售库用库存 for 过仓
     * 
     * @return
     */
    @NativeQuery
    Map<String, Map<String, Long>> findSalesAvailQtyByBatchNoShare(@QueryParam("whouid") Long whouid, @QueryParam("batchcode") String batchcode, MapInvOwnerMapper rowMapper);

    /**
     * 查询非共享仓库销售库用库存 for 过仓
     * 
     * @return
     */
    @NativeQuery
    Map<String, Map<String, Long>> findSalesAvailQtyByBatchNoShareDetial(@QueryParam("whouid") Long whouid, @QueryParam("batchcode") String batchcode, MapInvOwnerMapper rowMapper);

    /**
     * 查询非共享仓库销售库用库存 for 过仓
     * 
     * @return
     */
    @NativeQuery
    Map<String, Map<String, Long>> findSalesAvailQtyByBatchShare(@QueryParam("whouid") Long whouid, @QueryParam("batchcode") String batchcode, @QueryParam("channel") String channel, MapInvMapper rowMapper);

    /**
     * 查询非共享仓库销售库用库存 for 过仓
     * 
     * @return
     */
    @NativeQuery
    Map<String, Map<String, Long>> findSalesAvailQtyByBatchShareDetial(@QueryParam("whouid") Long whouid, @QueryParam("batchcode") String batchcode, @QueryParam("channel") String channel, MapInvMapper rowMapper);

    /**
     * 查询明细OWNER销售库用库存 for 过仓
     * 
     * @return
     */
    @NativeQuery
    Map<String, Map<String, Long>> findSalesAvailQtyByBatchShare2(@QueryParam("whouid") Long whouid, @QueryParam("batchcode") String batchcode, MapInvMapper rowMapper);

    /**
     * 查询非共享仓库销售库用库存 for 过仓
     * 
     * @return
     */
    @NativeQuery
    Map<String, Map<String, Long>> findSalesAvailQtyByBatchShare1(@QueryParam("whouid") Long whouid, @QueryParam("owner") String owner, MapInvMapper rowMapper);

    /**
     * 删除VMI库存调整上次占用的库存
     * 
     * @param code
     */
    @NativeUpdate
    void updateOccupyByCode(@QueryParam("invCode") String code);

    /**
     * 仓库库存报表导出
     * 
     * @param ouId
     * @param skuId
     * @param innerShopCode
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<InventoryCommand> findInventoryReportk(@QueryParam("ouId") Long ouId, BeanPropertyRowMapperExt<InventoryCommand> beanPropertyRowMapper);

    /**
     * 库存明细查寻(新增保质期条件) bin.hu
     * 
     * @param start
     * @param pageSize
     * @param barCode
     * @param jmCode
     * @param skuName
     * @param supplierSkuCode
     * @param owner
     * @param whOuId
     * @param rowMapper
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<InventoryCommand> findDetailsInventoryByPageShelfLife(int start, int pageSize, @QueryParam("jmCode") String jmCode, @QueryParam("skuCode") String skuCode, @QueryParam("extCode2") String extCode2, @QueryParam("barCode") String barCode,
            @QueryParam("skuName") String skuName, @QueryParam("supplierSkuCode") String supplierSkuCode, @QueryParam("locationCode") String locationCode, @QueryParam("invOwner") String invOwner, @QueryParam("whOuId") Long whOuId,
            @QueryParam("companyid") Long companyid, @QueryParam("statusId") Long statusId, @QueryParam("warningDate") Integer warningDate, @QueryParam("shelfLife") Integer shelfLife, @QueryParam("startDate") Date startDate,
            @QueryParam("endDate") Date endDate, @QueryParam("isZeroInventory") Integer isZeroInventory, RowMapper<InventoryCommand> rowMapper, Sort[] sorts);

    /**
     * 库存明细查寻(SKU总数) cheng.su
     * 
     * @param barCode
     * @param jmCode
     * @param skuName
     * @param supplierSkuCode
     * @param owner
     * @param whOuId
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<InventoryCommand> findDetailsInventorySkuSum(@QueryParam("jmCode") String jmCode, @QueryParam("skuCode") String skuCode, @QueryParam("extCode2") String extCode2, @QueryParam("barCode") String barCode, @QueryParam("skuName") String skuName,
            @QueryParam("supplierSkuCode") String supplierSkuCode, @QueryParam("locationCode") String locationCode, @QueryParam("invOwner") String invOwner, @QueryParam("whOuId") Long whOuId, @QueryParam("companyid") Long companyid,
            @QueryParam("statusId") Long statusId, @QueryParam("warningDate") Integer warningDate, @QueryParam("shelfLife") Integer shelfLife, @QueryParam("startDate") Date startDate, @QueryParam("endDate") Date endDate,
            RowMapper<InventoryCommand> rowMapper);

    /**
     * 库存明细查寻(库位总数) cheng.su
     * 
     * @param barCode
     * @param jmCode
     * @param skuName
     * @param supplierSkuCode
     * @param owner
     * @param whOuId
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<InventoryCommand> findDetailsInventoryLocationSum(@QueryParam("jmCode") String jmCode, @QueryParam("skuCode") String skuCode, @QueryParam("extCode2") String extCode2, @QueryParam("barCode") String barCode, @QueryParam("skuName") String skuName,
            @QueryParam("supplierSkuCode") String supplierSkuCode, @QueryParam("locationCode") String locationCode, @QueryParam("invOwner") String invOwner, @QueryParam("whOuId") Long whOuId, @QueryParam("companyid") Long companyid,
            @QueryParam("statusId") Long statusId, @QueryParam("warningDate") Integer warningDate, @QueryParam("shelfLife") Integer shelfLife, @QueryParam("startDate") Date startDate, @QueryParam("endDate") Date endDate,
            RowMapper<InventoryCommand> rowMapper);

    /**
     * 库存明细查寻(库存总数) cheng.su
     * 
     * @param barCode
     * @param jmCode
     * @param skuName
     * @param supplierSkuCode
     * @param owner
     * @param whOuId
     * @param rowMapper
     * @return
     */
    @NativeQuery
    InventoryCommand findDetailsInventorySkuQty(@QueryParam("jmCode") String jmCode, @QueryParam("skuCode") String skuCode, @QueryParam("extCode2") String extCode2, @QueryParam("barCode") String barCode, @QueryParam("skuName") String skuName,
            @QueryParam("supplierSkuCode") String supplierSkuCode, @QueryParam("locationCode") String locationCode, @QueryParam("invOwner") String invOwner, @QueryParam("whOuId") Long whOuId, @QueryParam("companyid") Long companyid,
            @QueryParam("statusId") Long statusId, @QueryParam("warningDate") Integer warningDate, @QueryParam("shelfLife") Integer shelfLife, @QueryParam("startDate") Date startDate, @QueryParam("endDate") Date endDate,
            RowMapper<InventoryCommand> rowMapper);

    /**
     * 查询保质期商品（定时）
     */
    @NativeQuery
    List<Inventory> findInventoryByShelfLife(@QueryParam("ouId") Long ouId, @QueryParam("storeMode") Integer storeMode, @QueryParam("owner") String owner, RowMapper<Inventory> mapper);

    /**
     * 获取同一商店、库位、仓库、SKU商品的过期时间
     */
    @NativeQuery
    List<Inventory> findSkuLocationOuIdOwner(@QueryParam(value = "ouid") Long ouid, @QueryParam(value = "locationId") Long locationId, @QueryParam(value = "skuId") Long skuId, RowMapper<Inventory> mapper);

    @NativeQuery(withGroupby = true)
    Map<String, Long> findQtyByInventoryCheckPC(@QueryParam("invCkId") Long invCkId, MapRowMapper r);

    /**
     * AF仓库库存报表导出
     * 
     * @param ouId
     * @param skuId
     * @param innerShopCode
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<AFinventoryCommand> findAFInventoryReport(@QueryParam("ouId") Long ouId, BeanPropertyRowMapperExt<AFinventoryCommand> beanPropertyRowMapper);


    /**
     * AF仓库库存报表导出2
     * 
     * @param ouId
     * @param skuId
     * @param innerShopCode
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<AFinventoryCommand> findAFInventoryReport2(@QueryParam("ouId") Long ouId, @QueryParam("date") Date date, BeanPropertyRowMapperExt<AFinventoryCommand> beanPropertyRowMapper);

    /**
     * 全量库存
     * 
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<Inventory> getNeedSendDataUnit(BeanPropertyRowMapper<Inventory> beanPropertyRowMapper);

    /**
     * 根据条件查询可用库存效期范围
     * 
     * @param extCode1
     * @param owner
     * @return
     */
    @NativeQuery
    InventoryCommand findInventoryByCode1(@QueryParam("extCode1") String extCode1, @QueryParam("owner") String owner, RowMapper<InventoryCommand> rowMapper);

    @NativeQuery
    Long findInventoryByLocationOuIdOwnerSku(@QueryParam("owner") String owner, @QueryParam("skuid") Long skuid, @QueryParam("ouid") Long ouid, SingleColumnRowMapper<Long> singleColumnRowMapper);

    @NativeQuery
    List<InventoryCommand> findEdwTwhInventory(@QueryParam("owner") String owner, @QueryParam("skuid") Long skuid, RowMapper<InventoryCommand> rowMapper);

    /**
     * 查询满足条件的库位信息
     * 
     * @return
     */
    @NativeQuery
    Long getVmiReturnLoaction(@QueryParam("owner") String owner, @QueryParam("skuid") Long skuid, @QueryParam("ouid") Long ouid, @QueryParam("qty") Long qty, @QueryParam("status") Long status, @QueryParam("transactionTypeId") Long transactionTypeId,
            SingleColumnRowMapper<Long> singleColumnRowMapper);

    @NativeQuery
    Long checkVmiReturnSkuQty(@QueryParam("owner") String owner, @QueryParam("skuid") Long skuid, @QueryParam("ouid") Long ouid, @QueryParam("status") Long status, SingleColumnRowMapper<Long> singleColumnRowMapper);

    @NativeQuery
    List<InventoryCommand> findVmiReturnSkuQtyLocation(@QueryParam("owner") String owner, @QueryParam("skuid") Long skuid, @QueryParam("ouid") Long ouid, @QueryParam("status") Long status, @QueryParam("transactionTypeId") Long transactionTypeId,
            RowMapper<InventoryCommand> RowMapper);

    @NativeQuery
    List<InventoryCommand> findVmiReturnSkuQtyLocation2(@QueryParam("owner") String owner, @QueryParam("skuid") Long skuid, @QueryParam("ouid") Long ouid, @QueryParam("status") Long status, @QueryParam("transactionTypeId") Long transactionTypeId,
            RowMapper<InventoryCommand> RowMapper);


    @NativeQuery
    List<InventoryCommand> findSkuInventory(@QueryParam("barcode") String barcode, @QueryParam("skucode") String skucode, @QueryParam("upc") String upc, @QueryParam("ouid") Long ouid, @QueryParam("owner") String owner,
            RowMapper<InventoryCommand> RowMapper);

    @NativeQuery
    InventoryCommand findSnCardSkuQtyLocation(@QueryParam("owner") String owner, @QueryParam("skuid") Long skuid, @QueryParam("ouid") Long ouid, @QueryParam("status") Long status, @QueryParam("locationid") Long locationid,
            RowMapper<InventoryCommand> RowMapper);

    @NativeQuery
    List<InventoryCommand> findSnCardSkuQtyLocation1(@QueryParam("owner") String owner, @QueryParam("skuid") Long skuid, @QueryParam("ouid") Long ouid, @QueryParam("status") Long status, @QueryParam("locationid") Long locationid,
            RowMapper<InventoryCommand> RowMapper);

    /**
     * 根据商品 获取可占用的库存
     * 
     * @param m
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<InventoryCommand> findInventoryOccupyBySku(@QueryParam Map<String, Object> m, RowMapper<InventoryCommand> rowMapper);

    /**
     * 按区域等条件查找商品可用库存
     * 
     * @param m
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<InventoryCommand> findInventoryOccupyByOcpArea(@QueryParam Map<String, Object> m, RowMapper<InventoryCommand> rowMapper);

    /**
     * 根据参数 获取被占用的库存
     * 
     * @param owner
     * @param skuId
     * @param sourceOcpCode
     * @param whId
     * @return
     */
    @NativeQuery
    List<InventoryCommand> findInventoryOccupyBySourceOcpCode(@QueryParam("owner") String owner, @QueryParam("skuId") Long skuId, @QueryParam("sourceOcpCode") String sourceOcpCode, @QueryParam("whId") Long whId, RowMapper<InventoryCommand> rowMapper);

    /**
     * 根据库存ID占用库存份
     * 
     * @param id 库存ID
     * @param occupyCode 占用码
     * @param qty 占用数量
     * @param ocpCode 旧的占用批编码
     */
    @NativeUpdate
    void modifyInventory(@QueryParam("invId") Long invId, @QueryParam("occupyCode") String occupyCode, @QueryParam("qty") Long qty, @QueryParam("ocpCode") String ocpCode);

    @NativeUpdate
    void modifyInventoryByOwner(@QueryParam("invId") Long invId, @QueryParam("locId") Long locId, @QueryParam("disId") Long disId);


    @NativeUpdate
    void modifyInventoryByOwnerQty(@QueryParam("invId") Long invId, @QueryParam("locId") Long locId, @QueryParam("disId") Long disId, @QueryParam("qty") Long qty);

    @NativeUpdate
    void updateInventoryByOwner(@QueryParam("invId") Long invId, @QueryParam("locId") Long locId, @QueryParam("disId") Long disId, @QueryParam("qty") Long qty);

    /**
     * 添加新的库存份（新的库存份切割方法）
     * 
     * @param invId
     * @param qty
     */
    @NativeUpdate
    void addNewInventory(@QueryParam("invId") Long invId, @QueryParam("qty") Long qty, @QueryParam("occupationCode") String occupationCode);

    /**
     * 平台对接编码下拉列表
     * 
     * @param ouId
     * @param RowMapper
     * @return
     */
    @NativeQuery
    List<InventoryCommand> findExtCode1(@QueryParam("ouId") Long ouId, RowMapper<InventoryCommand> RowMapper);

    /**
     * 根据占用码删除库存
     * 
     * @param occupyCode
     * @return
     */
    @NativeUpdate
    Integer deleteInvByOccupationCode(@QueryParam("occupyCode") String occupyCode);

    /**
     * 品牌库存快照通过SKU+OWNER(通用)
     * 
     * @param owner
     * @param RowMapper
     * @return
     */
    @NativeQuery
    List<InventoryCommand> findVmiInventoryByOwner(@QueryParam("owner") List<String> owner, RowMapper<InventoryCommand> RowMapper);

    @NativeQuery
    String findVmiInventoryQtyAndBlockQty(@QueryParam("batchCode") String batchCode, @QueryParam("skuid") Long skuid, @QueryParam("owner") String owner, @QueryParam("status") Long status, SingleColumnRowMapper<String> singleColumnRowMapper);

    /**
     * 获取所有的仓库
     * 
     * @author yimin.lu
     * @param rowMap
     * @return
     */
    @NativeQuery
    List<WarehouseDataCommand> getAllWarehouseData(RowMapper<WarehouseDataCommand> rowMap);

    /**
     * 强生库存快照定制数据
     * 
     * @param owner
     * @param RowMapper
     * @return
     */
    @NativeQuery
    List<InventoryCommand> findVmiInventoryByOwnerToJNJ(@QueryParam("owner") List<String> owner, RowMapper<InventoryCommand> RowMapper);


    /**
     * SPEEDO库存快照定制数据
     * 
     * @param owner
     * @param RowMapper
     * @return
     */
    @NativeQuery
    List<InventoryCommand> findVmiInventoryByOwnerToSPEEDO(@QueryParam("owner") List<String> owner, RowMapper<InventoryCommand> RowMapper);


    /**
     * PaulFrank库存快照定制数据
     * 
     * @param owner
     * @param RowMapper
     * @return
     */
    @NativeQuery
    List<InventoryCommand> findVmiInventoryByOwnerToPaulFrank(@QueryParam("owner") List<String> owner, RowMapper<InventoryCommand> RowMapper);


    @NativeQuery
    String findVmiInventoryQtyAndBlockQtyAndOnHoldQty(@QueryParam("skuid") Long skuid, @QueryParam("owner") String owner, SingleColumnRowMapper<String> singleColumnRowMapper);

    /**
     * speedo
     */
    @NativeQuery
    String findVmiInventoryQtyAndBlockQtyAndOnHoldQtySPEEDO(@QueryParam("skuid") Long skuid, @QueryParam("owner") String owner, SingleColumnRowMapper<String> singleColumnRowMapper);

    @NativeQuery
    String findVmiInventoryQtyAndBlockQtyAndOnHoldQtyCK(@QueryParam("skuid") Long skuid, @QueryParam("owner") String owner, SingleColumnRowMapper<String> singleColumnRowMapper);

    @NativeQuery
    String findVmiInventoryQtyAndBlockQtyAndOnHoldQtyPaulFrank(@QueryParam("skuid") Long skuid, @QueryParam("owner") String owner, SingleColumnRowMapper<String> singleColumnRowMapper);

    /**
     * 查询残次库存
     */
    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<InventoryCommand> findImperfectInv(int start, int pageSize, @QueryParam("ouId") Long ouid, @QueryParam("owner") String owner, @QueryParam("barCode") String barCode, @QueryParam("supplierCode") String supplierCode,
            @QueryParam("jmCode") String jmCode, RowMapper<InventoryCommand> r);

    /**
     * 根据中间表ID查询关联的作业单需要占用和取消的库存
     * 
     * @param id
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<InventoryCommand> getOccupationDataByStaCode(@QueryParam("id") Long id, BeanPropertyRowMapper<InventoryCommand> beanPropertyRowMapper);

    @NativeQuery
    Long findQuantityBySkuAndOuid(@QueryParam("skuId") Long skuId, @QueryParam("ouId") Long ouId, SingleColumnRowMapper<Long> r);

    /**
     * 根据店铺code List查询出ad全量库存
     */
    @NativeQuery
    List<AdidasTotalInventory> findAdidasTotalInventoryByCodes(@QueryParam("ouId") Long ouId, @QueryParam("codes") List<String> codes, BeanPropertyRowMapper<AdidasTotalInventory> beanPropertyRowMapper);

    /**
     * 根据wId List查询出ad销售库存 findAdidasSalesInventoryByCodes
     */
    @NativeQuery
    List<AdidasSalesInventory> findAdidasSalesInventoryByCodes(@QueryParam("ouId") Long ouId, @QueryParam("codes") List<String> codes, BeanPropertyRowMapper<AdidasSalesInventory> beanPropertyRowMapper);


    /**
     * 根据店铺code List查询出reebok全量库存
     */
    @NativeQuery
    List<ReebokTotalInventory> findReebokTotalInventoryByCodes(@QueryParam("ouIds") List<String> ouIds, @QueryParam("codes") List<String> codes, BeanPropertyRowMapper<ReebokTotalInventory> beanPropertyRowMapper);


    /**
     * 根据店铺code List查询出全量可销售库存 通用
     */
    @NativeQuery
    List<AdidasSalesInventory> findAdidasSalesInventoryByCodes2(@QueryParam("ouIds") List<String> ouIds, @QueryParam("codes") List<String> codes, BeanPropertyRowMapper<AdidasSalesInventory> beanPropertyRowMapper);


    /**
     * 根据wId List查询出reebok销售库存
     */
    @NativeQuery
    List<ReebokSalesInventory> findReebokSalesInventoryByCodes(@QueryParam("ouIds") List<String> ouIds, @QueryParam("codes") List<String> codes, BeanPropertyRowMapper<ReebokSalesInventory> beanPropertyRowMapper);


    /**
     * 方法说明：根据库位[库区]移动,更新释放库存
     * 
     * @author LuYingMing
     * @date 2016年9月1日 上午10:13:45
     * @param id
     * @param locationId
     * @param districtId
     */
    @NativeUpdate
    int updateInventoryByLocationMove(@QueryParam("staId") Long staId, @QueryParam("locationId") Long locationId, @QueryParam("districtId") Long districtId);

    /**
     * AD按行取消未打印拣货单之前释放库存
     * 
     * @param lineNo
     * @param id
     */
    @NativeUpdate
    void releaseInvForAdLineCancel(@QueryParam("lineNo") String lineNo, @QueryParam("staId") Long id);

    /**
     * 按行查询库存forAD
     * 
     * @param saleOcpType
     * @param lineId
     * @param wooCode
     * @param r
     * @return
     */
    @NativeQuery
    List<InventoryOccupyCommand> findSalesOutboundToOccupyInventoryForAd(@QueryParam("saleOcpType") Integer saleOcpType, @QueryParam("lineId") Long lineId, @QueryParam("wooCode") String wooCode, RowMapper<InventoryOccupyCommand> r);

    /**
     * 
     * 方法说明：在库商品效期调整查询(条件)
     * 
     * @author LuYingMing
     * @param start
     * @param pageSize
     * @param map
     * @param sorts
     * @param rowMapper
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<InventoryCommand> findValidityAdjustByPage(int start, int pageSize, @QueryParam Map<String, Object> m, Sort[] sorts, RowMapper<InventoryCommand> rowMapper);


    /**
     * 
     * 方法说明：(根据条件)查询效期
     * 
     * @author LuYingMing
     * @param m
     * @param RowMapper
     * @return
     */
    @NativeQuery
    List<InventoryCommand> findvalidDateByParameter(@QueryParam Map<String, Object> m, RowMapper<InventoryCommand> RowMapper);

    /**
     * 
     * 方法说明：(在库商品效期调整) 更新效期
     * 
     * @author LuYingMing
     * @param id
     * @param pDate
     * @param eDate
     * @return
     */
    @NativeUpdate
    int updateInventoryByValidDate(@QueryParam("id") Long id, @QueryParam("pDate") Date pDate, @QueryParam("eDate") Date eDate);

    @NativeUpdate
    void updateInventoryByInvId(@QueryParam("id") Long id, @QueryParam("qty") Long qty);

    /**
     * 根据商品id查询库存数据不为0的库存数据
     * 
     * @param id
     * @return
     */
    @NativeQuery
    List<Inventory> findNotZeroInvBySkuId(Long skuId, RowMapper<Inventory> r);

    @NativeQuery
    Long findInvQtyBySkuId(@QueryParam("skuId") Long skuId, SingleColumnRowMapper<Long> s);

    @NativeQuery
    List<InventoryCommand> findInvQtyBySkuIdAndLocationId(@QueryParam("skuId") Long skuId, @QueryParam("locationId") Long locationId, @QueryParam("ouId") Long ouId, RowMapper<InventoryCommand> r);


    @NativeQuery
    Long findInvQtyBySkuIdOwnerAndLocationId(@QueryParam("skuId") Long skuId, @QueryParam("locationId") Long locationId, @QueryParam("ouId") Long ouId, @QueryParam("owner") String owner, @QueryParam("statusId") Long statusId, SingleColumnRowMapper<Long> s);

    @NativeQuery
    Long findInvQtyBySkuIdOwnerAndLocationIdAll(@QueryParam("skuId") Long skuId, @QueryParam("locationId") Long locationId, @QueryParam("ouId") Long ouId, @QueryParam("owner") String owner, @QueryParam("statusId") Long statusId,
            SingleColumnRowMapper<Long> s);

    @NativeQuery
    Date findInvQtyBySkuIdOwnerAndLocationId2(@QueryParam("skuId") Long skuId, @QueryParam("locationId") Long locationId, @QueryParam("ouId") Long ouId, @QueryParam("owner") String owner, @QueryParam("statusId") Long statusId,
            SingleColumnRowMapper<Date> s);

    @NativeQuery
    List<InventoryCommand> findInvExpireDateQtyBySkuIdAndLocationId(@QueryParam("skuId") Long skuId, @QueryParam("locationId") Long locationId, @QueryParam("ouId") Long ouId, RowMapper<InventoryCommand> r);

    @NativeQuery
    List<InventoryCommand> findLocBySingOrderAndOuId(@QueryParam("ouId") Long ouId, @QueryParam("districtId") Long districtId,@QueryParam("wh_districtId") Long wh_districtId, @QueryParam("num") Integer num, RowMapper<InventoryCommand> r);

    @NativeQuery
    Long findSkuCountByLocId(@QueryParam("ouId") Long ouId, @QueryParam("locId") Long locId, SingleColumnRowMapper<Long> s);

    @NativeQuery
    Long findOwnerCountByLocId(@QueryParam("ouId") Long ouId, @QueryParam("locId") Long locId, SingleColumnRowMapper<Long> s);

    @NativeQuery
    List<InventoryCommand> findStaIdByLocId(@QueryParam("ouId") Long ouId, @QueryParam("locId") Long locId, RowMapper<InventoryCommand> r);

    /**
     * 方法说明：获取库存数量
     * 
     * @param skuId
     * @param ouId
     * @return
     */
    @NativeQuery
    InventoryCommand getInventoryQuantityBySkuId(@QueryParam("skuId") Long skuId, RowMapper<InventoryCommand> RowMapper);

    /**
     * 获取库存同步UniqueKey
     * 
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    Long getSequenceIdForInvUniqueKey(SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * 查询库存信息插入同步库存表内
     * 
     * @param owner
     * @param skuCode
     * @param ouCode
     * @param batch
     */
    @NativeUpdate
    void insertInventorySnapshot(@QueryParam("owner") String owner, @QueryParam("skuCode") List<String> skuCode, @QueryParam("ouCode") List<String> ouCode, @QueryParam("batch") String batch);

    /**
     * 方法说明：获取Ad增量库存
     * 
     * @param beanPropertyRowMapper
     * 
     * @param rownum
     * @return
     */
    @NativeQuery
    public List<LogQueueHub> findAdIncrementalInv(@QueryParam("limitNum") int limitNum, BeanPropertyRowMapper<LogQueueHub> beanPropertyRowMapper);

    /**
     * 方法说明：更新Ad增量库存推送状态
     * 
     * @param rownum
     * @return
     */
    @NativeUpdate
    public void updateAdAmiIncInvSucessStatus(@QueryParam("status") int status, @QueryParam("ids") List<Long> ids);

    /**
     * 方法说明：更新Ad增量库存推送错误次数和下次推送时间
     * 
     * @param rownum
     * @return
     */
    @NativeUpdate
    public void updateAdAmiIncInvFailedStatus(@QueryParam("ids") List<Long> ids);

    /**
     * 方法说明：删除Ad增量库存历史数据
     * 
     * @param rownum
     * @return
     */
    @NativeUpdate
    public void deleteLastAdAmiIncInvDate();

    /**
     * 方法说明：转移Ad增量库存到历史表
     * 
     * @param rownum
     * @return
     */
    @NativeUpdate
    public void transferInventoryAdidasToLog(@QueryParam(value = "datetime") Date datetime);

    /**
     * 
     * @param locationCode
     * @param shopId
     * @param ouid
     * @param rowMap
     * @return
     */
    @NativeQuery
    List<InventoryCommand> findInventoryBatchLockList(@QueryParam(value = "locationCode") String locationCode, @QueryParam(value = "skuCode") String skuCode, @QueryParam(value = "shopId") Long shopId, @QueryParam(value = "ouid") Long ouid,
            @QueryParam(value = "inventoryStatusId") Long inventoryStatusId, @QueryParam(value = "productionDate") String productionDate, @QueryParam(value = "expireDate") String expireDate, RowMapper<InventoryCommand> rowMap);

    @NativeQuery
    List<InventoryCommand> findInventoryBatchLockListByInvIds(@QueryParam(value = "ids") List<Long> ids, @QueryParam(value = "ouid") Long ouid, RowMapper<InventoryCommand> rowMap);

    /**
     * 自动化库存数据恢复
     */
    @NativeUpdate
    public int reStoreSkuInventory(@QueryParam(value = "plCode") String plCode);
   
    /**
     * 获取对应STA单据对应SKU库存信息 并for update
     */
    @NativeQuery
    List<Long> findOccupyInventoryByStaIdPartial(@QueryParam(value = "staid") Long staid, @QueryParam(value = "skuid") Long skuid, SingleColumnRowMapper<Long> mapper);

    /**
     * 根据条码，仓库id查询库存
     * 
     * @param barCode
     * @param ouId
     * @param rowMap
     * @return
     */
    @NativeQuery
    public List<InventoryCommand> getInventoryByBarCodeAndOuId(@QueryParam("barCode") String barCode, @QueryParam("ouId") Long ouId, RowMapper<InventoryCommand> rowMap);

    
    /**
     * 根据库位编码，仓库id查找库存
     * @param barCode
     * @param ouId
     * @param rowMap
     * @return
     */
    @NativeQuery
    public List<InventoryCommand> getInventoryLocationCodeAndOuId(@QueryParam("locationCode") String locationCode, @QueryParam("ouId") Long ouId, RowMapper<InventoryCommand> rowMap);
    
    /**
     * pda库存查询支持多条码
     * @param location
     * @param skuBarCode
     * @param uniqCode
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<InventoryCommand> getInventoryForPdaMorebarCode(@QueryParam("location") String location, @QueryParam("skuId") Long skuId, @QueryParam("uniqCode") String uniqCode, BeanPropertyRowMapper<InventoryCommand> beanPropertyRowMapper);

    @NativeQuery
    Long findQtyOccpInv(@QueryParam("owner") String owner, @QueryParam("skuid") Long skuid, @QueryParam("ouid") Long ouid, SingleColumnRowMapper<Long> singleColumnRowMapper);
    
    @NativeQuery
    List<InventoryCommand> findInventoryByCodeAndShop(@QueryParam("skuCode")String code,@QueryParam("shop")String shop,RowMapper<InventoryCommand> r);
}
