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

import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.warehouse.BiChannelCommand;
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

@Transactional
public interface BiChannelDao extends GenericEntityDao<BiChannel, Long> {

    /**
     * 根据仓库组织ID查询所有仓库关联店铺
     * 
     * @param whouid
     * @return
     */
    @NamedQuery
    List<BiChannel> getAllRefShopByWhOuId(@QueryParam(value = "whouid") Long whouid);

    /**
     * 根据绑定的默认收货仓库ID查询所有关联店铺
     * 
     * @param whouid
     * @return List<BiChannel>
     * @throws
     */
    @NamedQuery
    List<BiChannel> getAllRefShopByDefaultWhOuId(@QueryParam(value = "whouid") Long whouid);

    /**
     * 
     * 通过客户ID查询所有客户下的店铺
     * 
     * @param whouid
     * @return
     */
    @NativeQuery(model = BiChannel.class)
    List<BiChannel> findAllByCustomer(@QueryParam(value = "customerid") Long customerid);

    /**
     * 
     * 通过客户ID查询所有客户下的店铺
     * 
     * @param whouid
     * @return
     */
    @NativeQuery(model = BiChannel.class)
    List<BiChannel> findAllByCustomers(@QueryParam(value = "startWarehouseId") Long startWarehouseId, @QueryParam(value = "endWarehouseId") Long endWarehouseId);

    /**
     * 根据仓库查询对应渠道信息
     * 
     * @param start
     * @param pagesize
     * @param whouid
     * @param name
     * @param r
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<BiChannel> findRefShopByWhOuId(int start, int pagesize, @QueryParam(value = "whouid") Long whouid, @QueryParam(value = "name") String name, RowMapper<BiChannel> r, Sort[] sorts);

    /**
     * 根据仓库及店铺是否支持按箱收货标识查询对应渠道信息
     * 
     * @param start
     * @param pagesize
     * @param whouid
     * @param name
     * @param r
     * @param sorts
     * @return
     */
    @NativeQuery(model = BiChannel.class)
    List<BiChannel> findRefShopByWhOuIdAndCartonShop(@QueryParam(value = "whouid") Long whouid, @QueryParam(value = "isCartonStaShop") Boolean isCartonStaShop, RowMapper<BiChannel> r);

    /**
     * 根据运营中心查询渠道信息
     * 
     * @param start
     * @param pagesize
     * @param ocouid
     * @param name
     * @param r
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<BiChannel> findRefShopByOcOuId(int start, int pagesize, @QueryParam(value = "ocouid") Long ocouid, @QueryParam(value = "name") String name, RowMapper<BiChannel> r, Sort[] sorts);

    /**
     * 根据公司查询渠道信息
     * 
     * @param start
     * @param pagesize
     * @param cmpouid
     * @param name
     * @param r
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<BiChannel> findRefShopByCmpOuId(int start, int pagesize, @QueryParam(value = "cmpouid") Long cmpouid, @QueryParam(value = "name") String name, RowMapper<BiChannel> r, Sort[] sorts);

    /**
     * 通过编码获取店铺
     * 
     * @param code
     * @return
     */
    @NamedQuery
    BiChannel getByCode(@QueryParam(value = "code") String code);

    /**
     * 通过编码和客户id获取店铺
     * 
     * @param code
     * @param customerId
     * @return
     */
    @NamedQuery
    BiChannel getByCodeAndCustomerId(@QueryParam(value = "code") String code, @QueryParam(value = "customerId") Long customerId);

    /**
     * 通过品牌对接编码获取店铺
     * 
     * @param vmicode
     * @return
     */
    @NamedQuery
    BiChannel getByVmiCode(@QueryParam(value = "vmicode") String vmicode);

    /**
     * 查询装箱清单定制店铺
     * 
     * @param ouid
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<String> customiztationTemplShop(@QueryParam(value = "ouid") Long ouid, SingleColumnRowMapper<String> singleColumnRowMapper);

    /**
     * 查询装箱清单定制店铺模板
     * 
     * @param ouid
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<String> findwarehosueIsRelateShopForPrint(@QueryParam(value = "ouid") Long ouid, SingleColumnRowMapper<String> singleColumnRowMapper);

    /**
     * 查询加入配货批的装箱清单模板
     * 
     * @param ouid
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<String> findwarehosueShopForPrintIsSame(@QueryParam(value = "ouid") Long ouid, @QueryParam(value = "shopList") List<String> shopList, SingleColumnRowMapper<String> singleColumnRowMapper);

    /**
     * 通过渠道ID查询装箱清单模板
     * 
     * @param id
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    String findTrunkTemplateName(@QueryParam(value = "id") Long id, SingleColumnRowMapper<String> singleColumnRowMapper);

    /**
     * 查询是否存在装箱清单定制店铺
     * 
     * @param innerCoders
     * @param ouid
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    Long warehosueIsRelateMulitShop(@QueryParam(value = "innerCoders") String[] innerCoders, @QueryParam(value = "ouid") Long ouid, SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * 查询渠道列表
     * 
     * @param start
     * @param pagesize
     * @param cmpouid
     * @param name
     * @param r
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<BiChannelCommand> getBiChannelList(int start, int pagesize, @QueryParam(value = "code") String code, @QueryParam(value = "name") String name, @QueryParam(value = "cId") Long cId, @QueryParam(value = "companyName") String companyName,
            RowMapper<BiChannelCommand> r, Sort[] sorts);



    /**
     * 查询渠道列表
     * 
     * @param start
     * @param pagesize
     * @param cmpouid
     * @param name
     * @param r
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<BiChannelCommand> getBiChannelListByType(int start, int pagesize, @QueryParam(value = "name") String name, @QueryParam(value = "whId") Long whId, RowMapper<BiChannelCommand> r, Sort[] sorts);

    /**
     * 查询渠道列表
     * 
     * @param start
     * @param pagesize
     * @param cmpouid
     * @param name
     * @param r
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<BiChannelCommand> getBiChannelListByTypeAndExpT(int start, int pagesize, @QueryParam(value = "code") String code, @QueryParam(value = "name") String name, @QueryParam(value = "cId") Long cId, RowMapper<BiChannelCommand> r, Sort[] sorts);

    /**
     * 查询渠道列表
     * 
     * @param start
     * @param pagesize
     * @param name
     * @param sorts
     * @return
     */
    @NamedQuery(pagable = true)
    Pagination<BiChannel> getBiChannelListByPage(int start, int pagesize, Sort[] sorts);

    @NamedQuery(pagable = true)
    Pagination<BiChannel> getBiChannelListByPageAndShopName(int start, int pagesize, @QueryParam(value = "shopName") String shopName, Sort[] sorts);

    /**
     * 通过ID获取渠道信息
     * 
     * @param id
     * @return
     */
    @NativeQuery
    BiChannelCommand findBiChannelById(@QueryParam(value = "id") Long id, RowMapper<BiChannelCommand> r);

    /**
     * 通过编码获取渠道信息
     * 
     * @param code
     * @param r
     * @return BiChannelCommand
     * @throws
     */
    @NativeQuery
    BiChannelCommand findBiChannelByCode(@QueryParam(value = "code") String code, RowMapper<BiChannelCommand> r);

    /**
     * 加载所有店铺信息下拉列表
     * 
     * @param rowMap
     * @return
     */
    @NativeQuery
    List<BiChannel> findChannelNameAll(RowMapper<BiChannel> rowMap);

    @NativeQuery
    BiChannel checkBiChannelByCodeOrName(@QueryParam(value = "code") String code, @QueryParam(value = "name") String name, RowMapper<BiChannel> r);

    @NamedQuery
    BiChannel getByName(@QueryParam("name") String owner);

    @NamedQuery
    BiChannel getByvmiWHSource1(@QueryParam("vmiWHSource1") String vmiWHSource1);

    @NamedQuery
    BiChannel getByvmiWHSource(@QueryParam("vmiWHSource") String vmiWHSource);

    @NativeQuery
    List<String> getAllJDbIcHannel(SingleColumnRowMapper<String> singleColumnRowMapper);

    @NativeQuery
    List<BiChannel> getBiChannelListJd(RowMapper<BiChannel> r);

    @NativeQuery
    String findBiChannelListByOuId(@QueryParam("ouId") Long ouId, @QueryParam("statusId") Long statusId, SingleColumnRowMapper<String> singleColumnRowMapper);

    /**
     * 获取所有渠道信息
     */
    @NamedQuery
    List<BiChannel> getAllBiChannel();

    @NativeQuery
    List<String> getChannelCode(@QueryParam(value = "channelId") List<Long> channelId, SingleColumnRowMapper<String> singleColumnRowMapper);

    /**
     * 
     * @param channelId
     */
    @NativeUpdate
    void updateChannelStatusById(@QueryParam(value = "chanId") Long channelId);

    /**
     * SF货主查询对应渠道信息
     */
    @NamedQuery
    BiChannel getByVmiWHSource(@QueryParam(value = "vmiWHSource") String vmiWHSource);

    @NativeQuery
    List<BiChannelCommand> findEdwTbiChannel(RowMapper<BiChannelCommand> rowMapper);

    @NativeQuery
    BiChannelCommand findVmiDefaultTbiChannel(@QueryParam(value = "vmicode") String vmicode, @QueryParam(value = "vmisource") String vmisource, @QueryParam(value = "isdef") Long isdel, RowMapper<BiChannelCommand> rowMapper);

    @NativeQuery
    BiChannelCommand findVmiDefaultTbiChannelByOwen(@QueryParam(value = "owen") String owen, RowMapper<BiChannelCommand> rowMapper);

    /**
     * 查询是否要特殊定制收货逻辑
     * 
     * @param staCode
     * @param barCode
     * @param priceLimit
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    Boolean getIsSpecialByStaAndSku(@QueryParam("staCode") String staCode, @QueryParam("barCode") String barCode, @QueryParam("priceLimit") String priceLimit, SingleColumnRowMapper<Boolean> singleColumnRowMapper);

    @NativeQuery
    Boolean getIsSpecialByStaAndSku1(@QueryParam("staCode") String staCode, @QueryParam("barCode") String barCode, @QueryParam("priceLimit") String priceLimit, @QueryParam("ouId") Long ouId, SingleColumnRowMapper<Boolean> singleColumnRowMapper);
    /**
     * 通过编码获取店铺
     * 
     * @param whouid
     * @return
     */
    @NamedQuery
    List<BiChannel> getBySpecial(@QueryParam(value = "specialExpressPadar") Boolean specialExpressPadar);

    /**
     * 获取公司信息
     * 
     * @return
     */
    @NativeQuery
    List<BiChannelCommand> findInvoiceCompany(RowMapper<BiChannelCommand> rowMapper);

    @NativeQuery
    List<String> getBiChannelByVmiCode(@QueryParam("vmicode") String vmicode, SingleColumnRowMapper<String> singleColumnRowMapper);

    @NativeQuery
    BiChannelCommand getBiChannelByDefaultCode(@QueryParam("code") String code, RowMapper<BiChannelCommand> rowMapper);

    @NativeQuery
    List<String> findBiChannelByDefaultCode(@QueryParam("code") String code, SingleColumnRowMapper<String> singleColumnRowMapper);

    @NativeQuery
    List<BiChannel> findAllBiChannel(BeanPropertyRowMapper<BiChannel> beanPropertyRowMapper);


    @NativeQuery
    String getVmiCodeByInv(@QueryParam("invId") Long id, SingleColumnRowMapper<String> singleColumnRowMapper);

    @NativeQuery
    BigDecimal queryChannlIdByOwner(@QueryParam("owner") String owner, SingleColumnRowMapper<BigDecimal> singleColumnRowMapper);

    /**
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<String> findInitInventoryOwnerList(SingleColumnRowMapper<String> singleColumnRowMapper);

    @NativeQuery
    List<String> findChannelByOuId(@QueryParam("ouId") Long ouId, SingleColumnRowMapper<String> s);

}
