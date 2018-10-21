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

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.baseinfo.cond.WarehouseQueryCond;
import com.jumbo.wms.model.command.WarehouseCommand;

import loxia.annotation.DynamicQuery;
import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;

/**
 * Warehouse
 * 
 * @author jumbo
 * 
 */
@Transactional
public interface WarehouseDao extends GenericEntityDao<Warehouse, Long> {
    /**
     * 根据组织id查找仓库的附加信息
     * 
     * @param ouid
     * @return
     */
    @NamedQuery
    Warehouse getByOuId(@QueryParam("ouid") Long ouid);
    
    @NamedQuery
    List<Warehouse> getIsAreaOcpInvAll();

    @DynamicQuery
    Warehouse getBySource(@QueryParam("source") String source, @QueryParam("sourceWh") String sourceWh);

    @NamedQuery
    List<Warehouse> getAllBySource(@QueryParam("source") String source);

    /**
     * 查找组织为仓库的仓库附加信息
     * 
     * @param start
     * @param pageSize
     * @param ouids
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<Warehouse> findWarehouseDetailList(int start, int pageSize, @QueryParam("ouids") List<Long> ouids, Sort[] sorts, WarehouseCommandRowMapper rowMapper);

    /**
     * 批量修改仓库共享性
     * 
     * @param ids
     * @param flag
     */
    @NativeUpdate
    void updateWarehouseShare(@QueryParam("ids") List<Long> ids, @QueryParam("flag") int flag);

    /**
     * 
     * @param cmpid
     * @param sorts
     * @param beanPropertyRowMapperExt
     * @return
     */
    @NativeQuery
    List<WarehouseCommand> findwhInfosForsoCountModel(@QueryParam("cmpid") Long cmpid, Sort[] sorts, BeanPropertyRowMapperExt<WarehouseCommand> beanPropertyRowMapperExt);

    @NamedQuery
    Warehouse findWarehouseByOUCode(@QueryParam("code") String code);


    /**
     * 
     * @param cmpid
     */
    @NativeUpdate
    void updateSoModelByCompanyid(@QueryParam("cmpid") Long cmpid);

    // @NativeQuery
    // List<Long> findouidByShopid(@QueryParam("ownerid") Long ownerid, SingleColumnRowMapper<Long>
    // beanPropertyRowMapperExt);

    // @NamedQuery
    // Warehouse getDefaultWarehouseForVmi(@QueryParam("shopId") Long shopId);

    // @NamedQuery
    // List<Warehouse> getVMIWHByShop(@QueryParam("shopId") Long shopId);

    // @NamedQuery
    // Warehouse getConverseVMIWHByShop(@QueryParam("shopId") Long shopId);

    @DynamicQuery
    Warehouse getWHByVmiSource(@QueryParam("source") String source, @QueryParam("sourceWH") String sourceWH);

    // @NativeQuery
    // List<Warehouse> findByCompanysShopId(@QueryParam("shopid") Long shopid, RowMapper<Warehouse>
    // beanPropertyRowMapper);

    @NamedQuery
    Warehouse findWarehouseByVmiSource(@QueryParam("vmiSource") String vmiSource);

    @NamedQuery
    Warehouse getWareHouseByVmiSource(@QueryParam("source") String source);

    @NamedQuery
    List<Warehouse> findAllWarehouse();

    // run
    @NativeQuery
    List<WarehouseCommand> findAllWarehouse2(BeanPropertyRowMapper<WarehouseCommand> beanPropertyRowMapper);

    @NativeQuery
    Long findCmpOuIdByOuId(@QueryParam(value = "ouId") Long ouId, SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * 查询所有非外包仓并且使用顺丰电子面单的仓库IDList KJL
     * 
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<Long> getAllSfWarehouse(SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * 查询所有非外包仓并且使用YTO电子面单的仓库
     * 
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<Long> getAllYtoWarehouse(SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * 查询所有非外包仓并且使用STO电子面单的仓库
     * 
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<Long> getAllSTOWarehouse(SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * 查询所有非外包仓并且使用ZTO电子面单的仓库
     * 
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<Long> getAllZTOWarehouse(SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * 查询所有非外包仓并且使用TTK电子面单的仓库
     * 
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<Long> getAllTTKWarehouse(SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * 查询所有非外包仓并且使用WX电子面单的仓库
     * 
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<Long> getAllWXWarehouse(SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * 方法说明：查询所有非外包仓的仓库
     * 
     * @author LuYingMing
     * @date 2016年5月30日 下午12:35:02
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<Long> getAllWarehouseByExcludeVmi(SingleColumnRowMapper<Long> singleColumnRowMapper);

    @NativeQuery
    List<Long> findAllWarehouseByExcludeVmiNotTask(SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * 查询所有非外包仓仓库,且非占用库存中的仓库
     * 
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<Long> findAllWarehouseByStatus(SingleColumnRowMapper<Long> singleColumnRowMapper);

    @NativeUpdate
    void insertWarehouseOcpRef(@QueryParam("ouId") Long ouId);

    @NativeUpdate
    Integer updateWarehouseOpcStatus(@QueryParam("status") Long status, SingleColumnRowMapper<Integer> singleColumnRowMapper);

    /**
     * 查询所有和物流有绑定的非外包仓
     * 
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<Long> getTransRefWarehouse(SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * 查询所有非外包仓并且使用EMS电子面单的仓库
     * 
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<Long> getAllEMSWarehouse(SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * 查询所有非外包仓并且使用RFD电子面单的仓库
     * 
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<Long> getAllRFDWarehouse(SingleColumnRowMapper<Long> singleColumnRowMapper);

    @NativeQuery
    Warehouse getWarehouseByOuId(@QueryParam("ouId") Long whId, BeanPropertyRowMapper<Warehouse> beanPropertyRowMapper);

    @NativeQuery
    Warehouse getZtoWarehouseByOuId(@QueryParam("ouId") Long whId, BeanPropertyRowMapper<Warehouse> beanPropertyRowMapper);

    @NativeQuery
    Warehouse getYtoWarehouseByOuId(@QueryParam("ouId") Long whId, BeanPropertyRowMapper<Warehouse> beanPropertyRowMapper);

    /**
     * 查询所有非外包仓并且使用TTK电子面单的仓库
     * 
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    Warehouse getTtkWarehouseByOuId(@QueryParam("ouId") Long whId, BeanPropertyRowMapper<Warehouse> beanPropertyRowMapper);

    /**
     * 查询所有非外包仓并且使用RFD电子面单的仓库
     * 
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    Warehouse getRFDWarehouseByOuId(@QueryParam("ouId") Long whId, BeanPropertyRowMapper<Warehouse> beanPropertyRowMapper);

    /**
     * 根据仓库的vmiSourceWh查找对应的仓库 KJL
     * 
     * @param vmiWhSourceGymboree
     * @return
     */
    @NamedQuery
    Warehouse getWarehouseByVmiSourceWh(@QueryParam("vmiSourceWh") String vmiWhSourceGymboree);

    /**
     * 关联快递
     * 
     * @param skuTagId
     * @param skuId void
     * @throws
     */
    @NativeUpdate
    void insertTransRef(@QueryParam(value = "ouId") Long ouId, @QueryParam(value = "transId") Long transId);

    /**
     * 删除关联的快递
     * 
     * @param skuTagId void
     * @throws
     */
    @NativeUpdate
    void deleteTransRef(@QueryParam(value = "ouId") Long ouId);

    @NativeQuery
    List<Transportator> findAllTransRef(@QueryParam("ouId") Long ouId, BeanPropertyRowMapper<Transportator> trans);

    @NativeUpdate
    void updateLpCodeByStaCode(@QueryParam(value = "id") Long id);

    @NativeUpdate
    void updateTrackByStaCode(@QueryParam(value = "code") String code);

    /**
     * Edw t_bi_warehouse
     * 
     * @return
     */
    @NativeQuery
    List<WarehouseCommand> findEdwTbiWarehouse(RowMapper<WarehouseCommand> rowMapper);

    @NativeQuery
    List<Long> getAllWarehouse(SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * 删除用户仓库绑定
     * 
     * @param ouId
     */
    @NativeUpdate
    void deleteUserWhRef(@QueryParam(value = "userId") Long userId);

    /**
     * 保存用户仓库绑定
     * 
     * @param ouId
     * @param whOuId
     */
    @NativeUpdate
    void saveUserWhRef(@QueryParam(value = "userId") Long userId, @QueryParam(value = "whOuId") Long whOuId);

    /**
     * 修改快递登录的状态
     * 
     * @param raId
     * @param typeId
     */
    @NativeUpdate
    void updateReTrackNoStatus(@QueryParam(value = "raId") Long raId, @QueryParam(value = "typeId") Long typeId);

    /**
     * 根据客户编码查询出所有店铺code
     */
    @NativeQuery
    List<String> getAllChannelByCusCode(@QueryParam(value = "code") String code, SingleColumnRowMapper<String> singleColumnRowMapper);

    @NativeQuery
    Integer findLimitBySku(@QueryParam(value = "staIdList") List<Long> staIdList, RowMapper<Integer> rowMapper);

    @NativeQuery(model = WarehouseQueryCond.class)
    WarehouseQueryCond findWarehouseByOuId(@QueryParam("whOuId") Long whOuId);

    /**
     * 判断仓库是否执行过线程占用
     * 
     * @param mine
     * @param whOuId
     * @return
     */
    @NativeQuery
    Boolean ocpIsWaitByOcpStatus(@QueryParam(value = "mine") Integer mine, @QueryParam(value = "whOuId") Long whOuId, SingleColumnRowMapper<Boolean> singleColumnRowMapper);

    /**
     * 修改仓库占用信息
     * 
     * @param status
     * @param ouId
     */
    @NativeUpdate
    int updateIsOcpByOuId(@QueryParam(value = "status") Integer status, @QueryParam(value = "ouId") Long ouId);


    @NativeUpdate
    void insertLfRtnOutbound(@QueryParam(value = "staCode") String staCode, @QueryParam(value = "wls") String wls, @QueryParam(value = "ydh") String ydh, @QueryParam(value = "weight") Double weight, @QueryParam(value = "field") String field,
            @QueryParam(value = "ctnType") String ctnType);

    @NativeUpdate
    void insertLfRtnLine(@QueryParam(value = "field") String field, @QueryParam(value = "ctnType") String ctnType);

    @NativeQuery
    Integer skuWeightCount(@QueryParam(value = "id") Long id, RowMapper<Integer> rowMapper);
}
