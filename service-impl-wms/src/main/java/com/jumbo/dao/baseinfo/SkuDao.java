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

package com.jumbo.dao.baseinfo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.commandMapper.MapRowMapper;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.command.SkuCountryOfOriginCommand;
import com.jumbo.wms.model.pda.InboundOnShelvesSkuCommand;
import com.jumbo.wms.model.warehouse.ProductThreeDimensionalCommand;

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
public interface SkuDao extends GenericEntityDao<Sku, Long> {

    /**
     * 查询星巴克 MSR定制订单
     * 
     * @param skuId
     * @param locId
     * @param r
     * @return
     */
    @NativeQuery
    Integer findMarSkuCardType(@QueryParam("staId") Long staId, @QueryParam("type") Integer type, SingleColumnRowMapper<Integer> r);


    /**
     * 根据条码查找sku
     * 
     * @param barCode
     * @return
     */
    @NamedQuery
    Sku getByBarcode(@QueryParam(value = "barCode") String barCode, @QueryParam(value = "customerId") Long customerId);

    /**
     * 根据条码查找(排除id)
     * 
     * @param
     * @return
     */
    @NamedQuery
    Sku getByBarcodeNoId(@QueryParam(value = "barCode") String barCode, @QueryParam(value = "customerId") Long customerId, @QueryParam(value = "id") Long id);

    /**
     * 根据code查找(排除id)
     * 
     * @param
     * @return
     */
    @NamedQuery
    Sku judgeSkuCodeNoId(@QueryParam(value = "code") String code, @QueryParam(value = "id") Long id);

    /**
     * 根据客户商品编码查找(排除id)
     * 
     * @param
     * @return
     */
    @NamedQuery
    Sku judgeSkuCustomerSkuCodeNoId(@QueryParam(value = "customerSkuCode") String customerSkuCode, @QueryParam(value = "id") Long id);

    /**
     * 根据条码查找sku
     * 
     * @param barCode
     * @return
     */
    @Deprecated
    @NamedQuery
    Sku getSkuByBarcode(@QueryParam(value = "barCode") String barCode);

    /**
     * 外部对接编码查找SKU
     * 
     * @param barCode
     * @return
     */
    @NamedQuery
    Sku getByCustomerSkuCode(@QueryParam(value = "customerSkuCode") String customerSkuCode, @QueryParam(value = "customerId") Long customerId);

    /**
     * 外部对接编码查找SKU
     * 
     * @param extCode1
     * @return
     */
    @NamedQuery
    Sku getByExtCode1AndCustomerId(@QueryParam(value = "extCode1") String extCode1, @QueryParam(value = "customerId") Long customerId);

    /**
     * 外部对接编码查找SKU
     * 
     * @param extCode2
     * @return
     */
    @NamedQuery
    Sku getByExtCode2AndCustomerId(@QueryParam(value = "extCode2") String extCode2, @QueryParam(value = "customerId") Long customerId);

    /**
     * 根据编码查询sku
     * 
     * @param code
     * @return
     */
    @NamedQuery
    Sku getByCode(@QueryParam(value = "code") String code);

    @NamedQuery
    List<Sku> getByJmCode(@QueryParam("jmcode") String jmcode);


    @NamedQuery
    List<Sku> getBysupplierCode(@QueryParam("supplierCode") String supplierCode);

    @NamedQuery
    Sku getSkuByExtensionCode1(@QueryParam(value = "extensionCode1") String extensionCode1);

    @NamedQuery
    Sku getByAddBarcode(@QueryParam(value = "barcode") String barcode, @QueryParam(value = "customerId") Long customerId);

    /**
     * 获取SKU
     * 
     * @param barCode
     * @param jmCode
     * @param keyProperties
     * @return
     */
    @DynamicQuery
    Sku findSkuByParameter(@QueryParam("barCode") String barCode, @QueryParam("jmCode") String jmCode, @QueryParam("keyProperties") String keyProperties, @QueryParam("customerId") Long customerId);

    /**
     * 获取SKU数量
     * 
     * @param barCode
     * @param jmCode
     * @param keyProperties
     * @return
     */
    @DynamicQuery
    List<Sku> findCountSkuByParameter(@QueryParam("barCode") String barCode, @QueryParam("jmCode") String jmCode, @QueryParam("keyProperties") String keyProperties, @QueryParam("customerId") Long customerId);

    /**
     * 查询商品是否在库位上
     * 
     * @param skuId
     * @param locId
     * @param r
     * @return
     */
    @NativeQuery
    Long findSkuOnLoaction(@QueryParam("skuId") Long skuId, @QueryParam("locId") Long locId, SingleColumnRowMapper<Long> r);


    /**
     * 查询商品保修卡信息
     * 
     * @param skuId
     * @param locId
     * @param r
     * @return
     */
    @NativeQuery
    Integer findSkuWarrantyCardType(@QueryParam("skuCode") String skuCode, SingleColumnRowMapper<Integer> r);


    /**
     * 查询仓库有库存SKU列表 返回当前所有SKU商品<code,sku>的Map
     * 
     * @param rowMapper
     * @return
     */
    @NativeQuery
    Map<String, Sku> findAllSkuMapByWhouId(@QueryParam("WhouId") Long WhouId, @QueryParam("owner") String owner, RowMapper<Map<String, Sku>> rowMapper);

    @NativeQuery
    Map<String, Long> findAllKeyCode(@QueryParam(value = "customerId") Long customerId, MapRowMapper r);

    @NativeQuery
    Map<String, Long> findAllKeyExtCode2(@QueryParam(value = "customerId") Long customerId, MapRowMapper r);

    @NativeQuery
    Map<String, Long> findAllKeyBarcode(@QueryParam(value = "customerId") Long customerId, MapRowMapper r);

    @NativeQuery
    Map<String, Long> findAllKeyCodeNeedSn(@QueryParam("customerId") Long customerId, MapRowMapper r);

    @NativeQuery
    Map<String, Long> findAllKeyCodeNotNeedSn(@QueryParam("customerId") Long customerId, MapRowMapper r);

    @NativeQuery
    Map<String, Long> findAllKeyBarcodeNeedSn(@QueryParam("customerId") Long customerId, MapRowMapper r);

    @NativeQuery
    Map<String, Long> findAllKeyBarcodeNotNeedSn(@QueryParam("customerId") Long customerId, MapRowMapper r);

    @NativeQuery
    Map<String, Long> findAllKeyCodeByOu(@QueryParam(value = "ouid") Long ouId, MapRowMapper r);

    @NativeQuery
    Map<String, Long> findAllKeyBarcodeByOu(@QueryParam(value = "ouid") Long ouId, MapRowMapper r);

    @NativeQuery
    Map<String, Long> findAllKeyCodeByOuWithSn(@QueryParam(value = "ouid") Long ouId, @QueryParam(value = "icid") Long icid, @QueryParam(value = "isSnSku") Boolean isSnSku, MapRowMapper r);

    @NativeQuery
    Map<String, Long> findVmiAdjSku(@QueryParam(value = "invCKID") Long invCKID, MapRowMapper r);

    @NativeQuery
    List<Sku> findSkuByLikeCode(@QueryParam(value = "skuCode") String skuCode, @QueryParam(value = "ouId") Long ouId, RowMapper<Sku> rowMapper);


    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<SkuCommand> findSkuAll(int start, int pageSize, @QueryParam Map<String, Object> m, RowMapper<SkuCommand> rowMapper, Sort[] sorts);

    @NativeQuery(pagable = true)
    Pagination<SkuCommand> findSkubySkuCode(int start, int pageSize, Sort[] sorts, String code, RowMapper<SkuCommand> rowMapper);

    @NativeQuery
    List<String> findLvsSkuInfoByProductCategory(RowMapper<String> rowMapper);

    @NativeQuery
    List<String> findLvsSkuInfoByProductLine(RowMapper<String> rowMapper);

    @NativeQuery
    List<String> findLvsSkuInfoByConsumerGroup(RowMapper<String> rowMapper);

    @NativeQuery
    SkuCommand findSKUCommandFromITData(@QueryParam(value = "skuCode") String skuCode, RowMapper<SkuCommand> rowMapper);

    @NativeQuery
    SkuCommand findSKUCommandFromCoachData(@QueryParam(value = "skuCode") String skuCode, RowMapper<SkuCommand> rowMapper);

    @NativeQuery
    SkuCommand findSKUCommandFromMasData(@QueryParam(value = "skuCode") String skuCode, RowMapper<SkuCommand> rowMapper);

    @NativeQuery
    List<SkuCommand> findSKUCommandFromESPData(@QueryParam(value = "skuCode") String skuCode, @QueryParam(value = "maohao") String maohao, RowMapper<SkuCommand> rowMapper);

    @NativeQuery
    List<SkuCommand> findSKUCommandFromCKData(RowMapper<SkuCommand> rowMapper);

    @NativeQuery
    SkuCommand findSKUCommandFromMASStockData(@QueryParam(value = "skuCode") String skuCode, RowMapper<SkuCommand> rowMapper);

    @NativeQuery
    SkuCommand findSKUCommandFromNikeData(@QueryParam(value = "extcode2") String extcode2, RowMapper<SkuCommand> rowMapper);

    @NativeQuery
    SkuCommand findSKUCommandFromConverseData(@QueryParam(value = "skuCode") String skuCode, RowMapper<SkuCommand> rowMapper);

    @NativeQuery
    SkuCommand findSKUCommandFromCCHData(@QueryParam(value = "skuCode") String skuCode, RowMapper<SkuCommand> rowMapper);

    @NativeQuery
    SkuCommand findSKUCommandFromPhilipsData(@QueryParam(value = "skuCode") String skuCode, RowMapper<SkuCommand> rowMapper);

    @NativeQuery
    SkuCommand findSKUCommandFromConverseEANData(@QueryParam(value = "skuCode") String skuCode, @QueryParam(value = "styleId") String styleId, @QueryParam(value = "styleSize") String styleSize, @QueryParam(value = "color") String color,
            RowMapper<SkuCommand> rowMapper);

    @NativeQuery
    SkuCommand findSKUCommandFromLevis(@QueryParam(value = "extcode2") String skuCode, RowMapper<SkuCommand> rowMapper);

    @NativeQuery
    List<String> findSKUCodefromEspOrder(@QueryParam(value = "po") String po, RowMapper<String> rowMapper);

    @NativeQuery
    SkuCommand findSKUCommandFromEtamData(@QueryParam(value = "maohao") String maohao, @QueryParam(value = "skuCode") String skuCode, RowMapper<SkuCommand> rowMapper);


    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<SkuCommand> findPlanExecuteDetailInfoPage(int start, int pageSize, @QueryParam(value = "staid") Long staid, RowMapper<SkuCommand> beanPropertyRowMapper, Sort[] sorts);

    /**
     * 根据商品Code查询库存成本
     * 
     * @param code
     * @param cmpId
     * @param r
     * @return
     */
    @NativeQuery
    SkuCommand findSkuCostByCode(@QueryParam("code") String code, @QueryParam("cmpid") Long cmpId, RowMapper<SkuCommand> r);

    @NativeQuery(pagable = true)
    Pagination<SkuCommand> findCompleteDetailInfoPage(int start, int pageSize, @QueryParam(value = "staid") Long staid, @QueryParam(value = "cartonCode") String cartonCode, @QueryParam(value = "code") String code,
            @QueryParam(value = "barCode") String barCode, @QueryParam(value = "supplyCode") String supplyCode, Sort[] sorts, BeanPropertyRowMapperExt<SkuCommand> beanPropertyRowMapperExt);

    @NativeQuery
    List<SkuCommand> findSkuProvideInfoPickingDistrict(@QueryParam(value = "ouid") Long ouid, @QueryParam("isShare") boolean b, Sort[] sorts, RowMapper<SkuCommand> r);

    @NativeQuery
    List<SkuCommand> findSkuProvideInfoUnMaintain(@QueryParam(value = "ouid") Long ouid, @QueryParam("isShare") Boolean boolean1, Sort[] sorts, RowMapper<SkuCommand> r);


    /**
     * 获取物流推荐规则明细对应的
     * 
     * @param trdId
     * @param r
     * @return
     */
    @NativeQuery
    List<Long> findTrdRefSku(@QueryParam(value = "trdId") Long trdId, RowMapper<Long> r);


    @NativeQuery
    List<SkuCommand> unfinishedStaUnMaintainProductExport(@QueryParam(value = "ouid") Long ouid, @QueryParam("isShare") Boolean boolean1, @QueryParam(value = "staTypes") List<Integer> staTypes,
            @QueryParam(value = "staStatuses") List<Integer> staStatuses, Sort[] sorts, RowMapper<SkuCommand> r);


    /**
     * 根据条件查询product
     * 
     * @param start
     * @param pageSize
     * @param skuInfoMap
     * @param beanPropertyRowMapperExt
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<SkuCommand> findAllProducts(int start, int pageSize, @QueryParam Map<String, Object> m, RowMapper<SkuCommand> rowMapper, Sort[] sorts);

    /**
     * 根据条件查询product2
     * 
     * @param start
     * @param pageSize
     * @param skuInfoMap
     * @param beanPropertyRowMapperExt
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<SkuCommand> findAllProducts2(int start, int pageSize, @QueryParam Map<String, Object> m, RowMapper<SkuCommand> rowMapper, Sort[] sorts);


    /**
     * Pda获取拣货单数据SKU部分
     * 
     * @param code
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<InboundOnShelvesSkuCommand> findPickSku(@QueryParam("code") String code, BeanPropertyRowMapper<InboundOnShelvesSkuCommand> beanPropertyRowMapper);

    @NativeQuery
    public BigDecimal findInvSkuBySkuCode(@QueryParam("skuCode") String skuCode, RowMapper<Sku> rowMapper);

    /**
     * KJL 查找相关仓库绑定的团购商品
     * 
     * @param start
     * @param pageSize
     * @param id
     * @param sorts
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<Sku> queryGroupBuyingSku(int start, int pageSize, @QueryParam("id") Long id, Sort[] sorts, BeanPropertyRowMapper<Sku> beanPropertyRowMapper);

    /**
     * 根据Sku idList查询商品名称和条形码
     * 
     * @param idList
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<Sku> getSkuByIdList(@QueryParam("idList") List<Long> idList, BeanPropertyRowMapper<Sku> beanPropertyRowMapper);

    /**
     * 根据商品编码查询销售可用量
     * 
     * @param owner
     * @param mainWhOuId
     * @param skuCode
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    SkuCommand findSalesQtyByOuAndOwner(@QueryParam("owner") String owner, @QueryParam("ouId") Long mainWhOuId, @QueryParam("skuCode") String skuCode, BeanPropertyRowMapper<SkuCommand> beanPropertyRowMapper);

    @NativeQuery
    List<SkuCommand> summaryInventoryToEmail(RowMapper<SkuCommand> beanPropertyRowMapper);

    /**
     * 查询外包仓反馈的是否存在差异（数量及匹配问题)
     * 
     * @param staId
     * @param msgId
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<Sku> findErrorSkuVmiReturn(@QueryParam("staId") Long staId, @QueryParam("msgId") Long msgId, BeanPropertyRowMapper<Sku> beanPropertyRowMapper);

    @NativeQuery
    List<Sku> findSkuByStvId(@QueryParam("stvId") Long stvId, RowMapper<Sku> rowMapper);

    /**
     * 根据传入的分类Id查看该分类下的所有商品 KJL
     * 
     * @param id
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<Sku> getProductByCategoryId(@QueryParam("id") Long id, BeanPropertyRowMapper<Sku> beanPropertyRowMapper);


    @NativeQuery(pagable = true)
    Pagination<Sku> findProductForBoxByPage(int start, int pageSize, @QueryParam("code") String code, @QueryParam("supplier") String supplier, @QueryParam("name") String name, RowMapper<Sku> rowMapper, Sort[] sorts);



    /**
     * 香港站新增
     * 
     * @param id
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<Sku> getErrorSkuSfRtn(@QueryParam("staId") Long id, BeanPropertyRowMapper<Sku> beanPropertyRowMapper);

    /**
     * 香港站新增
     * 
     * @param id
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<Sku> findErrorInboundSn(@QueryParam("inId") Long id, BeanPropertyRowMapper<Sku> beanPropertyRowMapper);

    /**
     * 根据条件查询所有的特殊处理商品
     * 
     * @param id
     * @param supplierCode
     * @param sorts
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<SkuCommand> getAllSepecialSku(int start, int pageSize,@QueryParam("ouId") Long id, @QueryParam("sCode") String supplierCode, Sort[] sorts, BeanPropertyRowMapper<SkuCommand> beanPropertyRowMapper);

    /**
     * 根据供应商编码查询商品
     * 
     * @param supplierCode
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<SkuCommand> getAllSkuBySupplierCode(@QueryParam("ssCode") String supplierCode, BeanPropertyRowMapper<SkuCommand> beanPropertyRowMapper);

    /**
     * 根据商品编码和客户id查询商品
     * 
     * @param skuCode
     * @param customerId
     * @return
     */
    @NamedQuery
    Sku getByCodeAndCustomer(@QueryParam("code") String skuCode, @QueryParam("customerId") Long customerId);

    @NamedQuery
    Sku getByBarCodeAndCustomer(@QueryParam("code") String skuCode, @QueryParam("customerId") Long customerId);

    @NamedQuery
    Sku getByExtCode1AndCustomer(@QueryParam("code") String skuCode, @QueryParam("customerId") Long customerId);

    @Deprecated
    @NamedQuery
    Sku getByBarcode1(@QueryParam(value = "barCode") String barCode);

    @Deprecated
    @NamedQuery
    Sku getByExtCode2(@QueryParam(value = "extcode2") String extcode2);


    /**
     * 根据ExtCode2 和客户ID 查询sku KJL
     * 
     * @param skuCode
     * @param customerId
     * @return
     */
    @NamedQuery
    Sku getByExtCode2AndCustomer(@QueryParam("extCode2") String skuCode, @QueryParam("cusId") Long customerId);

    @NamedQuery
    List<Sku> getSkuListByExtCode2AndCustomer(@QueryParam("extCode2") String skuCode, @QueryParam("cusId") Long customerId);


    @NativeQuery
    Long findSkuCountByStaCode(@QueryParam("staCode") String staCode, @QueryParam("sn") Long sn, @QueryParam("storeM") Long storeM, SingleColumnRowMapper<Long> r);

    @NativeQuery
    Long findShelfLifeSkuCountByStaCode(@QueryParam("staCode") String staCode, @QueryParam("storeM") Long snstoreM, SingleColumnRowMapper<Long> r);

    /**
     * 根据Code
     * 
     * @param ouid
     * @param batchcode
     * @return
     */
    @NativeQuery
    public List<String> findExtCode3(@QueryParam("staId") Long staId, SingleColumnRowMapper<String> singleColumnRowMapper);

    /**
     * 根据商品标签id查询关联商品
     * 
     * @param start
     * @param pageSize
     * @param m
     * @param rowMapper
     * @param sorts
     * @return Pagination<SkuCommand>
     * @throws
     */
    @NativeQuery(pagable = true)
    Pagination<SkuCommand> findAllSkuByTagId(int start, int pageSize, @QueryParam("tagId") Long tagId, Sort[] sorts, RowMapper<SkuCommand> rowMapper);

    /**
     * 根据条件查询商品
     * 
     * @param m
     * @param start
     * @param pageSize
     * @param sorts
     * @param rowMapper
     * @return Pagination<SkuCommand>
     * @throws
     */
    @NativeQuery(pagable = true)
    Pagination<SkuCommand> findAllSkuRef(int start, int pageSize, @QueryParam Map<String, Object> m, Sort[] sorts, RowMapper<SkuCommand> rowMapper);

    /**
     * Edw
     * 
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<SkuCommand> findEdwSku(RowMapper<SkuCommand> rowMapper);


    /**
     * 根据时间段查询品牌的商品信息
     * 
     * @param startTime
     * @param endTime
     * @param code
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<SkuCommand> findSKuByTime(@QueryParam(value = "startTime") Date startTime, @QueryParam(value = "endTime") Date endTime, @QueryParam(value = "brandList") List<Long> brandList, BeanPropertyRowMapperExt<SkuCommand> beanPropertyRowMapper);

    @NativeQuery
    SkuCommand getSkuByBarCodeAndCostomer(@QueryParam("barcode") String barcode, @QueryParam("cid") Long cid, RowMapper<SkuCommand> r);

    /**
     * 根据仓库的操作模式查询商品的可销售状态
     * 
     * @param barcode
     * @param ouId
     * @param owner
     * @param stvId
     * @param r
     * @return
     */
    @NativeQuery
    SkuCommand getSkuIsForsale(@QueryParam("barcode") String barcode, @QueryParam("ouId") Long ouId, @QueryParam("stvId") Long stvId, RowMapper<SkuCommand> r);


    @NamedQuery
    Sku findGuessMaterial(@QueryParam("supplierCode") String supplierCode, @QueryParam("length") BigDecimal length, @QueryParam("width") BigDecimal width, @QueryParam("height") BigDecimal height);

    @NativeQuery
    Sku findBarCodeByStaCode(@QueryParam(value = "staCode") String staCode, RowMapper<Sku> r);

    @Deprecated
    @NamedQuery
    List<Sku> getSkuListByBarcode(@QueryParam("barcode") String barcode);

    @NativeQuery
    SkuCommand findStaLineByStaIdCard(@QueryParam(value = "id") String id, @QueryParam(value = "staId") Long staId, BeanPropertyRowMapperExt<SkuCommand> beanPropertyRowMapperExt);


    @NativeQuery
    Long findQtyByStLog(@QueryParam(value = "skuId") Long skuId, @QueryParam(value = "ouId") Long ouId, SingleColumnRowMapper<Long> r);

    @NativeQuery
    List<SkuCommand> findSkuByStaId(@QueryParam(value = "staId") Long staId, BeanPropertyRowMapperExt<SkuCommand> beanPropertyRowMapperExt);

    /**
     * @param stvId
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<Sku> findSkuListByStvId(@QueryParam("stvId") Long stvId, BeanPropertyRowMapper<Sku> beanPropertyRowMapper);

    /**
     * 方法说明：根据条码查询(不更新到货号)
     * 
     * @author LuYingMing
     * @date 2016年6月17日 下午2:22:10
     * @param staId
     * @param beanPropertyRowMapperExt
     * @return
     */
    @NativeQuery
    List<ProductThreeDimensionalCommand> findProductByBarCode(@QueryParam("barCode") String barCode, RowMapper<ProductThreeDimensionalCommand> rowMapper);

    /**
     * 方法说明：根据条码查询(更新到货号)
     * 
     * @author LuYingMing
     * @date 2016年6月23日 上午11:14:51
     * @param barCode
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<ProductThreeDimensionalCommand> findProductByBarCodeWithSupplierCode(@QueryParam("barCode") String barCode, RowMapper<ProductThreeDimensionalCommand> rowMapper);


    /**
     * 方法说明：根据(更新到)货号查询商品三维 分页
     * 
     * @author LuYingMing
     * @date 2016年6月14日 下午6:58:20
     * @param start
     * @param pageSize
     * @param barCode
     * @param rowMapper
     * @param sorts
     * @return ProductThreeDimensionalCommand
     */
    @NativeQuery(pagable = true)
    Pagination<ProductThreeDimensionalCommand> findProductBySupplierCodeForPagination(int start, int pageSize, @QueryParam("barCode") String barCode, RowMapper<ProductThreeDimensionalCommand> rowMapper, Sort[] sorts);

    /**
     * 方法说明：根据条码查询商品三维 分页
     * 
     * @author LuYingMing
     * @date 2016年6月14日 下午6:59:54
     * @param start
     * @param pageSize
     * @param barCode
     * @param rowMapper
     * @param sorts
     * @return ProductThreeDimensionalCommand
     */
    @NativeQuery(pagable = true)
    Pagination<ProductThreeDimensionalCommand> findProductByBarCodeForPagination(int start, int pageSize, @QueryParam("barCode") String barCode, RowMapper<ProductThreeDimensionalCommand> rowMapper, Sort[] sorts);

    /**
     * 根据的id查找sku
     * 
     * @param id
     * @return
     */
    @NativeQuery
    SkuCommand findSkuBySkuId(@QueryParam("id") Long id, RowMapper<SkuCommand> rowMapper);

    /**
     * 根据extCode2,客户Id,店铺下面的品牌获取商品
     */
    // @NativeQuery
    // List<Sku> getByExtCode2AndCustomerAndShopId(@QueryParam("extCode2") String extCode2,
    // @QueryParam("customerId") Long customerId, @QueryParam("channelId") Long shopId,
    // BeanPropertyRowMapper<Sku> b);

    /**
     * 根据extCode2,客户Id,店铺下面的品牌获取商品
     */
    @NamedQuery
    List<Sku> getByExtCode2AndCustomerAndShopId(@QueryParam("extCode2") String extCode2, @QueryParam("customerId") Long customerId, @QueryParam("channelId") Long shopId);

    /**
     * 根据快递单号查询商品好耗材
     * 
     * @return
     */
    @NativeQuery
    Sku findSkuByTrackingNo(@QueryParam("trackingNo") String trackingNo, BeanPropertyRowMapper<Sku> rowMapper);

    /**
     * 
     * 方法说明：退换货入库指令商品明细查询
     * 
     * @author LuYingMing
     * @param staId
     * @param beanPropertyRowMapperExt
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<SkuCommand> returnInboundDirectiveDetail(int start, int pageSize, @QueryParam("staId") Long staId, @QueryParam(value = "ouId") Long ouId, RowMapper<SkuCommand> rowMapper);

    /**
     * 根据输出单据号获取库存状态信息
     * 
     * @param orderCode
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<SkuCommand> findSkuStatusByorderCode(@QueryParam("orderCode") String orderCode, RowMapper<SkuCommand> rowMapper);

    /**
     * 根据staId获取商品耗材信息(barCode字段)
     * 
     * @param staId
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<SkuCommand> findSkuMaterialByStaId(@QueryParam("staId") Long staId, RowMapper<SkuCommand> rowMapper);

    /**
     * 根据staCode获取星巴克MSR卡入库商品id
     * 
     * @param staId
     * @param rowMapper
     * @return
     */
    @NativeQuery
    Long findSkuIdByStaCode(@QueryParam("staCode") String staCode, RowMapper<Long> l);

    /**
     * 查找耗材
     * 
     * @param barCode
     * @return
     */
    @NativeQuery
    SkuCommand findSkuMaterialByBarcode(@QueryParam("barCode") String barCode, @QueryParam("customerId") Long customerId, RowMapper<SkuCommand> rowMapper);

    /**
     * 分页查商品产地
     * 
     */
    @NativeQuery(pagable = true)
    Pagination<SkuCountryOfOriginCommand> getSkuCountryOfOriginPage(int start, int pagesize, @QueryParam("ouId") long ouId, @QueryParam("skuCode") String skuCode, RowMapper<SkuCountryOfOriginCommand> r, Sort[] sorts);

    /**
     * 批量删除商品产地
     * 
     */
    @NativeUpdate
    void delSkuCountryOfOrigin(@QueryParam("ids") List<Long> ids);

    @NativeQuery
    List<Sku> findSkuByBarcodeList(@QueryParam("barcodeList") List<String> barcodeList, @QueryParam("customerId") Long customerId, BeanPropertyRowMapper<Sku> beanPropertyRowMapper);

    /**
     * 根据作业单查找体积或重量缺少的商品
     * 
     * @param staId
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<Sku> findSkuThreeDimensionalIsNullByStaId(@QueryParam("staId") Long staId, RowMapper<Sku> rowMapper);

    @NativeQuery(pagable = true)
    Pagination<SkuCommand> findThreeDimensionalSkuInfo(int start, int pageSize, @QueryParam("staId") Long staId, @QueryParam("skuId") Set<Long> skuId, RowMapper<SkuCommand> rowMapper);

    @NativeQuery
    List<SkuCommand> findThreeDimensionalSkuInfoList(@QueryParam("staId") Set<Long> staId, RowMapper<SkuCommand> rowMapper);

    @NativeQuery
    List<SkuCommand> findNoThreeDimensionalSku(@QueryParam("plId") Long plId, RowMapper<SkuCommand> rowMapper);

    @NativeQuery(pagable = true)
    Sku findSkuByBarCodeAndCustomerId(@QueryParam(value = "barCode") String barCode, @QueryParam(value = "customerId") Long customerId, RowMapper<Sku> rowMapper);


    @NativeQuery
    List<SkuCommand> findNoThreeDimensionalSkuInfoList(@QueryParam("pinkingListIdList") List<Long> pinkingListIdList, RowMapper<SkuCommand> rowMapper);
	
	@NativeQuery(pagable = true)
    Pagination<SkuCommand> findNoThreeDimensionalSkuInfo(int start, int pageSize, @QueryParam("plId") Long plId, RowMapper<SkuCommand> rowMapper);

}
