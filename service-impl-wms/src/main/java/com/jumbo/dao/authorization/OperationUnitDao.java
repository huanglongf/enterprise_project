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
package com.jumbo.dao.authorization;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.command.OperationUnitCommand;

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
public interface OperationUnitDao extends GenericEntityDao<OperationUnit, Long> {
    /**
     * 通过编码获取组织信息
     * 
     * @return
     */
    @NamedQuery
    OperationUnit getByCode(@QueryParam("code") String code);

    /**
     * 通过店铺获取关联的默认收货仓库
     * 
     * @param shopId
     * @return
     */
    @NamedQuery
    OperationUnit getDefaultInboundWhByShopId(@QueryParam("shopid") Long shopId);


    @NativeQuery
    public List<OperationUnit> odoOuIdList(@QueryParam("id") String id, RowMapper<OperationUnit> r);

    /**
     * 通过公司组织ID获取公司下仓库组织
     * 
     * @param cmpouid
     * @return
     */
    @NamedQuery
    List<OperationUnit> getAllWhByCmpOuId(@QueryParam("cmpouid") Long cmpouid);

    /**
     * 通过公司组织ID获取运营中心下仓库组织
     * 
     * @param cmpouid
     * @return
     */
    @NamedQuery
    List<OperationUnit> getAllWhByOcOuId(@QueryParam("ocouid") Long ocouid);

    /**
     * 通过店铺获取关联的仓库
     * 
     * @param shopId
     * @return
     */
    @NamedQuery
    List<OperationUnit> getAllWhByShopId(@QueryParam("shopid") Long shopId);

    /**
     * 根据物流商 查询绑定的仓库
     * 
     * @param transId
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<OperationUnit> findWHOUByTrans(@QueryParam("transId") Long transId, RowMapper<OperationUnit> rowMapper);

    @NativeQuery
    List<OperationUnit> findOperationUnitListSql(RowMapper<OperationUnit> rowMapper);

    @NativeQuery
    List<OperationUnit> findOperationUnitByOuTypeIDSql(@QueryParam("ouTypeId") Long ouTypeId, @QueryParam("isAvailable") Boolean isAvailable, RowMapper<OperationUnit> rowMapper);


    @NativeQuery
    Long getCompanyIdByWarehouseOuId2(@QueryParam("whId") Long whId, SingleColumnRowMapper<Long> singleColumnRowMapper);


    /**
     * 判断是否已经存在指定名字的组织
     * 
     * @param name
     * @param id
     * @return
     */
    @DynamicQuery
    Long findOperationUnitCountByCodeName(@QueryParam("code") String code, @QueryParam("name") String name, @QueryParam("id") Long id);

    @NamedQuery
    List<OperationUnit> findAllByType(@QueryParam("type") String type);

    /**
     * 根据公司查询所有的仓库
     * 
     * @param ouid
     * @return
     */
    @NativeQuery
    List<OperationUnitCommand> findWarehouseByCompany(@QueryParam("companyId") Long companyId, @QueryParam("shopId") Long shopId, RowMapper<OperationUnitCommand> rowMapper);


    /**
     * 根据公司查询所有的仓库
     * 
     * @param ouid
     * @return
     */
    @NativeQuery
    List<OperationUnitCommand> findWarehouseByCompanyList(@QueryParam("companyId") Long companyId, @QueryParam("innerShopCode") String innerShopCode, RowMapper<OperationUnitCommand> rowMapper);


    /**
     * 根据店铺查询所有的仓库
     * 
     * @param ouid
     * @return
     */
    @NativeQuery
    List<OperationUnitCommand> findWarehouseByShop(@QueryParam("shopId") Long shopId, RowMapper<OperationUnitCommand> rowMapper);

    /**
     * 查找仓库列表
     * 
     * @param id
     * @return
     */
    @NamedQuery
    List<OperationUnit> findOperationUnitList(@QueryParam("id") Long id);

    /**
     * 查找仓库对象
     * 
     * @param id
     * @return
     */
    @DynamicQuery(pagable = true)
    OperationUnit findOperationUnitWarehouseopc(@QueryParam("ouid") Long ouid);


    @NamedQuery
    List<OperationUnit> findOperationUnitListByCode(@QueryParam("code") String code);

    /**
     * 仓库附加信息未填写的组织列表
     * 
     * @param start
     * @param pageSize
     * @param list
     * @param sorts
     * @return
     */
    @DynamicQuery(pagable = true)
    Pagination<OperationUnit> findOperationUnitWarehouseList(int start, int pageSize, @QueryParam("ouids") List<Long> ouids, Sort[] sorts);

    @NativeQuery
    List<OperationUnit> findWarehouseOuListByComOuId(@QueryParam("compId") Long comOuId, RowMapper<OperationUnit> rowMapper, Sort[] sorts);

    @NamedQuery
    OperationUnit findByName(@QueryParam("name") String name);

    @NamedQuery
    OperationUnit findByNameIsExist(@QueryParam("name") String name, @QueryParam("ouId") Long ouId);

    @NativeQuery
    List<OperationUnit> findBusinessShopToWhList(@QueryParam(value = "shopId") Long shopId, @QueryParam(value = "whOuIds") List<Long> whOuId, RowMapper<OperationUnit> rowMapper);

    /**
     * 根据公司id 查寻出所有的销售事业部
     * 
     * @param id
     * @return
     */
    @NamedQuery
    List<OperationUnit> findDivisionList(@QueryParam("id") Long id);

    /**
     * 根据销售事业部id 查寻出所有的店铺
     * 
     * @param id
     * @return
     */
    @NamedQuery
    List<OperationUnit> findShopListByDivision(@QueryParam("id") Long divisionId);

    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<OperationUnit> findDivisionInfo(int start, int pageSize, @QueryParam("ouid") Long ouid, RowMapper<OperationUnit> rowMapper, Sort[] sorts);

    /**
     * 查询所有的company基本信息列表
     * 
     * @return
     */
    @NativeQuery
    List<OperationUnit> selectAllCompany(RowMapper<OperationUnit> rowMapper);

    /**
     * 根据company id查询所有运营中心
     * 
     * @param comid
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<OperationUnit> selectCenterByCompanyId(@QueryParam("comid") Long comid, BeanPropertyRowMapper<OperationUnit> beanPropertyRowMapper);

    /**
     * 根据运营中心查询所有仓库
     * 
     * @param cenid
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<OperationUnit> selectWarehouseByCenid(@QueryParam("cenid") Long cenid, BeanPropertyRowMapper<OperationUnit> beanPropertyRowMapper);

    /**
     * 查看已关联虚拟仓
     * 
     * @param phid
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<OperationUnit> selectOperationUnitByPhyId(BeanPropertyRowMapper<OperationUnit> beanPropertyRowMapper);

    /**
     * 根据物理仓查找所有关联虚拟仓列表
     * 
     * @param phyid
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<OperationUnit> selectAllWarehouseByPhyId(@QueryParam("phyid") Long phyid, BeanPropertyRowMapper<OperationUnit> beanPropertyRowMapper);

    /**
     * 保存关联关系
     * 
     * @param phid
     * @param list
     */
    @NativeUpdate
    void saveRelationShip(@QueryParam("phid") Long phid, @QueryParam("list") List<Long> list);

    /**
     * 根据物理仓删除关联信息
     * 
     * @param phid
     */
    @NativeUpdate
    void deleteRelationShip(@QueryParam("phid") Long phid);

    @NativeQuery
    Long getCompanyIdByWarehouseOu(@QueryParam("whId") Long whId, SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * 查询所有在用的组织
     * 
     * @param isAvailable
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<Long> findAlvailableWarehouseOuId(SingleColumnRowMapper<Long> r);

    /**
     * 查询所有在用的组织
     * 
     * @param isAvailable
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<Long> findAlvailableWarehouseOuIdDetial(SingleColumnRowMapper<Long> r);


    @NativeQuery
    List<Long> findWarehouseByIsVMIWh(@QueryParam("isAutoWh") Boolean isVMIWh, SingleColumnRowMapper<Long> r);


    @NativeQuery
    List<OperationUnitCommand> findBiChannelWhRefList(@QueryParam("customerId") Long customerId, BeanPropertyRowMapperExt<OperationUnitCommand> r);

    /**
     * Edw
     * 
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<OperationUnitCommand> findEdwTauOperationUnit(RowMapper<OperationUnitCommand> rowMapper);

    @NativeQuery
    String selectWhCodeByStaId(@QueryParam("id") Long id, SingleColumnRowMapper<String> r);

    /**
     * 
     * @param start
     * @param pageSize
     * @param roleDtalId
     * @param e
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<OperationUnitCommand> findChannelTransWh(int start, int pageSize, @QueryParam(value = "channelId") Long channelId, @QueryParam(value = "roleDtalId") Long roleDtalId, BeanPropertyRowMapper<OperationUnitCommand> e, Sort[] sorts);

    /**
     * 新作分仓查询需要多少仓库，启动多少线程
     * 
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<Long> getNeedExecuteWarehouse(SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * 新作分仓查询需要多少仓库，启动多少线程
     * 
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<Long> getNeedExecuteWarehousePac(SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * 新作分仓查询需要多少仓库，启动多少线程
     * 
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<Long> getBeginFlagForOrderPac(SingleColumnRowMapper<Long> singleColumnRowMapper);

    @NativeQuery
    Long getCompanyIdByWarehouseOuId(@QueryParam("whId") Long whId, SingleColumnRowMapper<Long> singleColumnRowMapper);

    @NativeQuery
    List<Long> getOuIdByLpCode(@QueryParam("lpCode") String lpCode, SingleColumnRowMapper<Long> singleColumnRowMapper);

    @NativeQuery
    List<OperationUnit> getSendWarehouse(RowMapper<OperationUnit> rowMapper);

    /**
     * 
     * @param start
     * @param pageSize
     * @param roleDtalId
     * @param e
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<OperationUnit> getSendWarehouseforPage(int start, int pageSize, @QueryParam(value = "name") String name, BeanPropertyRowMapper<OperationUnit> e);

    @NativeQuery
    List<String> findInitInventoryWarehouseCodeList(SingleColumnRowMapper<String> singleColumnRowMapper);

    @NativeQuery
    List<String> findInitInventoryWarehouseCodeListDelete(SingleColumnRowMapper<String> singleColumnRowMapper);

    @NativeQuery
    OperationUnit getById(@QueryParam(value = "id") Long id, BeanPropertyRowMapper<OperationUnit> rowMapper);

}
