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
import java.util.Date;
import java.util.List;
import java.util.Map;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.commandMapper.MapRowMapper;
import com.jumbo.wms.model.command.pickZone.WhPickZoneInfoCommand;
import com.jumbo.wms.model.warehouse.WarehouseLocation;
import com.jumbo.wms.model.warehouse.WarehouseLocationCommand;

@Transactional
public interface WarehouseLocationDao extends GenericEntityDao<WarehouseLocation, Long> {


    /**
     * 查寻同商品、同批次（有批次的前提下） 的建议库位
     * 
     * @param ouid
     * @param stvId
     * @param r
     * @return
     */
    @NativeQuery
    List<WarehouseLocationCommand> findSuggestLocationByStvInfo(@QueryParam("ouId") Long ouId, @QueryParam("stvId") Long stvId, @QueryParam("transTypeId") Long transTypeId, RowMapper<WarehouseLocationCommand> r);

    @NamedQuery
    List<WarehouseLocation> findLocationByDiId(@QueryParam("id") Long id, @QueryParam("ouId") Long ouId, RowMapper<WarehouseLocation> r);

    /**
     * 查寻stv里面同商品 ，库区信息
     * 
     * @param ouid
     * @param stvId
     * @param r
     * @return
     */
    @NativeQuery
    List<WarehouseLocationCommand> findSuggestDistrictByStvInfo(@QueryParam("ouId") Long ouId, @QueryParam("stvId") Long stvId, @QueryParam("transTypeId") Long transTypeId, RowMapper<WarehouseLocationCommand> r);

    /**
     * 根据 库区 查寻商品和空库位
     * 
     * @param ouId
     * @param stvId
     * @param transTypeId
     * @param r
     * @return
     */
    @NativeQuery
    List<WarehouseLocationCommand> findSkuAndLocationByDistrict(@QueryParam("ouId") Long ouId, @QueryParam("stvId") Long stvId, @QueryParam("districtId") Long districtId, @QueryParam("transTypeId") Long transTypeId, RowMapper<WarehouseLocationCommand> r);


    /**
     * 根据库存店铺查询库位
     * 
     * @param ouid
     * @param owner
     * @return
     */
    @NamedQuery
    List<WarehouseLocation> findByInvOwner(@QueryParam(value = "ouid") Long ouid, @QueryParam(value = "owner") String owner);

    /**
     * 查看库位是否支持作业类型
     * 
     * @param locid
     * @param typeid
     * @param r
     * @return
     */
    @NativeQuery
    BigDecimal findIsSupportTranstype(@QueryParam(value = "locid") Long locid, @QueryParam(value = "typeid") Long typeid, SingleColumnRowMapper<BigDecimal> r);

    /**
     * 查询仓库未锁定库位数量
     * 
     * @param ouid
     * @param r
     * @return
     */
    @NativeQuery
    BigDecimal findUnLockCountByOu(@QueryParam(value = "ouid") Long ouid, SingleColumnRowMapper<BigDecimal> r);

    /**
     * 根据库存品牌查询库位
     * 
     * @param ouid
     * @param brandId
     * @return
     */
    @NamedQuery
    List<WarehouseLocation> findByInvBrand(@QueryParam(value = "ouid") Long ouid, @QueryParam(value = "brandId") Long brandId);

    /**
     * 当前库区的所有库位
     * 
     * @param disId
     * @return
     */
    @NamedQuery(pagable = true)
    Pagination<WarehouseLocation> findLocationsListByDistrictId(int start, int pageSize, @QueryParam(value = "disId") Long disId, Sort[] sorts);

    /**
     * 库区的所有库位,包含绑定的作业类型的数量
     * 
     * @param id
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<WarehouseLocation> findLocationsTransCountListByDistrictId(int start, int pageSize, @QueryParam(value = "disId") Long disId, Sort[] sorts, RowMapper<WarehouseLocation> rowMapper);

    @NativeQuery(pagable = true)
    Pagination<WarehouseLocation> findLocationsTransCountListByDistrictAndLocId(int start, int pageSize, @QueryParam(value = "disId") Long disId, @QueryParam(value = "locId") Long locId, Sort[] sorts, RowMapper<WarehouseLocation> rowMapper);

    /**
     * 库位绑定作业类型
     * 
     * @param locationIds
     * @param transIds
     */
    @NativeUpdate
    void createTransactionTypeForLocations(@QueryParam(value = "locationIds") List<Long> locationIds, @QueryParam(value = "transIds") List<Long> transIds);


    /**
     * 库位绑定作业类型
     * 
     * @param locationIds
     * @param transIds
     */
    @NativeUpdate
    void createLocTransForLocAndTran(@QueryParam(value = "locCode") String locCode, @QueryParam(value = "tranCode") String tranCode);


    /**
     * 库位撤销绑定作业类型
     * 
     * @param locationIds
     * @param transIds
     */
    @NativeUpdate
    void deleteTransactionTypeForLocations(@QueryParam(value = "locationIds") List<Long> locationIds, @QueryParam(value = "transIds") List<Long> transIds);

    /**
     * 根据库区绑定库位的作业作业类型
     * 
     * @param districtId 库位id
     * @param transIds 作业类型id
     */
    @NativeUpdate
    void createTransactionTypeByDistrict(@QueryParam(value = "districtId") Long districtId, @QueryParam(value = "transIds") List<Long> transIds);

    /**
     * 根据库区撤销库位的作业作业类型
     * 
     * @param districtId 库位id
     * @param transIds 作业类型id
     */
    @NativeUpdate
    void deleteTransactionTypeByDistrict(@QueryParam(value = "districtId") Long districtId, @QueryParam(value = "transIds") List<Long> transIds);

    @NamedQuery
    List<WarehouseLocation> findWarehouseLocationByOuid(@QueryParam(value = "ouId") Long ouId);

    @NamedQuery
    WarehouseLocation findOneWarehouseLocationByOuid(@QueryParam(value = "ouId") Long ouId);

    /**
     * 包括校验库位是否锁定
     * 
     * @param code
     * @param ouId
     * @return
     */
    @NamedQuery
    WarehouseLocation findLocationByCode(@QueryParam(value = "code") String code, @QueryParam(value = "ouId") Long ouId);

    /**
     * 获取库位根据 code 与 ouId
     * 
     * @param code
     * @param ouId
     * @return
     */
    @NamedQuery
    WarehouseLocation findLocationByCode2(@QueryParam(value = "code") String code, @QueryParam(value = "ouId") Long ouId);


    /**
     * 未校验库位是否锁定
     * 
     * @param code
     * @param ouId
     * @return
     */
    @NamedQuery
    WarehouseLocation findByLocationCode(@QueryParam(value = "locationCode") String locationCode, @QueryParam(value = "ouId") Long ouId);

    @NativeUpdate
    void updateLocationPopByCode(@QueryParam(value = "locationCode") String locationCode, @QueryParam(value = "ouId") Long ouId, @QueryParam(value = "popCode") String popCode);

    @NativeQuery(pagable = true)
    List<WarehouseLocation> findWarehouseLocationByCodeNull(int start, int pageSize, @QueryParam(value = "ouId") Long ouId, @QueryParam(value = "code") String code, RowMapper<WarehouseLocation> rowMapper);

    @NativeQuery(pagable = true)
    List<WarehouseLocation> findWarehouseLocationNull(int start, int pageSize, @QueryParam(value = "ouId") Long ouId, @QueryParam(value = "code") String code, RowMapper<WarehouseLocation> rowMapper);

    @NamedQuery
    List<WarehouseLocation> findDisputableWl(@QueryParam(value = "ouId") Long ouId);

    /**
     * 有效的，不包含虚拟库位，
     * 
     * @param code
     * @param ouId
     * @return
     */
    @NativeQuery
    WarehouseLocation findWarehouseLocationByCode(@QueryParam(value = "code") String code, @QueryParam(value = "ouId") Long ouId, @QueryParam(value = "tranCode") String tranCode, BeanPropertyRowMapper<WarehouseLocation> s);

    /**
     * 返回当前仓库下的所有库位的<库位编码,Id>的Map
     * 
     * @param warehouseId
     * @param rowMapper
     * @return
     */
    @NativeQuery
    Map<String, Map<String, Long>> findLocationCodeMapByWarehouseSql(@QueryParam(value = "ouId") Long warehouseId, RowMapper<Map<String, Map<String, Long>>> rowMapper);

    @NamedQuery
    WarehouseLocation findByTransactionType(@QueryParam(value = "locCode") String locCode, @QueryParam(value = "ouId") Long ouId, @QueryParam(value = "ttId") Long ttId);

    /**
     * 该仓库库位是否有此作业类型
     * 
     * @param locationId
     * @param transtypeId
     * @param rowMapper
     * @return
     */
    @NativeQuery
    Integer findLocationTranstype(@QueryParam(value = "locationId") Long locationId, @QueryParam(value = "transtypeId") Long transtypeId, RowMapper<Integer> rowMapper);


    @NativeQuery
    WarehouseLocationCommand findLocationTranstypeByCode(@QueryParam(value = "locCode") String locCode, @QueryParam(value = "ouid") Long ouid, @QueryParam(value = "stvid") Long stvid, RowMapper<WarehouseLocationCommand> rowMapper);



    @NativeQuery(withGroupby = true)
    BigDecimal findLockedAndNoOccupaidLocation(@QueryParam(value = "locId") Long locId, SingleColumnRowMapper<BigDecimal> rowMap);

    /**
     * 分页查找库位列表信息
     * 
     * @param start
     * @param pageSize
     * @param districtCode
     * @param districtName
     * @param code
     * @param ouid
     * @param sorts
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<WarehouseLocationCommand> findLocationListByPage(int start, int pageSize, @QueryParam(value = "brand") String brand, @QueryParam(value = "owner") String owner, @QueryParam(value = "districtCode") String districtCode,
            @QueryParam(value = "districtName") String districtName, @QueryParam(value = "code") String code, @QueryParam(value = "ouid") Long ouid, Sort[] sorts, BeanPropertyRowMapper<WarehouseLocationCommand> beanPropertyRowMapper);

    /**
     * 查看当前库位是否存在 存在就获取结果集
     * 
     * @param locCode
     * @param ouId
     * @return
     */
    @NativeQuery
    WarehouseLocationCommand findLocationByLocationCode(@QueryParam(value = "locCode") String locCode, @QueryParam(value = "ouId") Long ouId, RowMapper<WarehouseLocationCommand> rowMapper);

    /**
     * 
     * 模糊查找配对的仓库code
     * 
     * @param ouId 当前仓库
     * @param type 绑定其它入库作业类型的库位
     * @param code 主要用来做校验时的code参数
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<WarehouseLocation> findMateWLListbyCode(@QueryParam(value = "ouId") Long ouId, @QueryParam(value = "typeId") Long typeId, @QueryParam(value = "code") String code, RowMapper<WarehouseLocation> rowMapper);

    /**
     * 
     * 模糊查找配对的仓库code
     * 
     * @param ouId 当前仓库
     * @param type 绑定其它入库作业类型的库位
     * @param code 主要用来做校验时的code参数
     * @param owner 店铺
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<WarehouseLocation> findMateWLListbyOwnerCode(@QueryParam(value = "ouId") Long ouId, @QueryParam(value = "typeId") Long typeId, @QueryParam(value = "code") String code, @QueryParam(value = "owner") String owner,
            RowMapper<WarehouseLocation> rowMapper);

    /**
     * 联想查询
     * 
     * @param code
     * @param ouId
     * @return
     */
    @NativeQuery
    List<WarehouseLocation> findLocationByLikeCode(@QueryParam(value = "code") String code, @QueryParam(value = "ouId") Long ouId, RowMapper<WarehouseLocation> rowMapper);

    /**
     * 根据仓库查询库位
     * 
     * @param ouId
     * @param r
     * @return Map<String,Long> key:库位code;value 库位id
     */
    @NativeQuery
    Map<String, Long> findAllByOu(@QueryParam(value = "ouId") Long ouId, MapRowMapper r);

    /**
     * 根据库区查询锁定或被占用库位
     * 
     * @param dId
     * @param r
     * @param isLocdAndOccupaid 是否占用或被锁定，true:查询被占用或锁定库位;false : 所有
     * @param limit 记录限制
     * @return Map<String,Long> key:库位code;value 库位id
     */
    @NativeQuery
    Map<String, Long> findAllByDistrict(@QueryParam(value = "dId") Long dId, @QueryParam(value = "isLocdAndOccupaid") Boolean isLocdAndOccupaid, @QueryParam(value = "limit") Long limit, MapRowMapper r);

    /**
     * 根据品牌查询锁定或被占用库位
     * 
     * @param dId
     * @param r
     * @param isLocdAndOccupaid 是否占用或被锁定，true:查询被占用或锁定库位;false : 所有
     * @param limit 记录限制
     * @return Map<String,Long> key:库位code;value 库位id
     */
    @NativeQuery
    Map<String, Long> findAllByBrand(@QueryParam(value = "bId") Long bId, @QueryParam(value = "isLocdAndOccupaid") Boolean isLocdAndOccupaid, @QueryParam(value = "ouId") Long ouId, @QueryParam(value = "limit") Long limit, MapRowMapper r);

    /**
     * 根据店铺查询锁定或被占用库位
     * 
     * @param owner
     * @param ouId
     * @param isLocdAndOccupaid 是否占用或被锁定，true:查询被占用或锁定库位;false : 所有
     * @param limit 记录限制
     * @param r
     * @return Map<String,Long> key:库位code;value 库位id
     */
    @NativeQuery
    Map<String, Long> findAllByOwner(@QueryParam(value = "owner") String owner, @QueryParam(value = "isLocdAndOccupaid") Boolean isLocdAndOccupaid, @QueryParam(value = "ouId") Long ouId, @QueryParam(value = "limit") Long limit, MapRowMapper r);

    /**
     * 查询库位，计算库容后
     * 
     * @param locCode
     * @param ouid
     * @param r
     * @return
     */
    @NativeQuery
    WarehouseLocation findLocationWithcapacityByCode(@QueryParam(value = "locCode") String locCode, @QueryParam(value = "ouid") Long ouid, RowMapper<WarehouseLocation> r);

    @NativeUpdate
    void unLockByInvCheck(@QueryParam(value = "icId") Long icId);

    @NativeUpdate
    void lockByInvCheck(@QueryParam(value = "icId") Long icId);

    /**
     * 级联库位-有效的、未锁定的库位
     * 
     * @param ouId
     * @param code
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<WarehouseLocation> findAllAvailLocations(@QueryParam(value = "ouid") Long ouid, @QueryParam(value = "code") String code, BeanPropertyRowMapper<WarehouseLocation> beanPropertyRowMapper);


    /**
     * 分页查询当前仓库下所有库位
     * 
     * @param start
     * @param pageSize
     * @param ouid
     * @param locationCode
     * @param districtCode
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<WarehouseLocation> findLocationsListByOuId(int start, int pageSize, @QueryParam(value = "ouid") Long ouid, @QueryParam(value = "locationCode") String locationCode, @QueryParam(value = "districtCode") String districtCode, Sort[] sorts,
            BeanPropertyRowMapper<WarehouseLocation> beanPropertyRowMapper);

    @NativeQuery
    List<WarehouseLocation> findAllLocationsListByOuId(@QueryParam(value = "ouid") Long ouid, BeanPropertyRowMapper<WarehouseLocation> beanPropertyRowMapper);


    @NamedQuery
    WarehouseLocation findwhLocationByCode(@QueryParam(value = "locationCode") String locCode, @QueryParam(value = "ouid") Long ouid);

    /**
     * 查询需要补货的库位
     * 
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<WarehouseLocationCommand> findReplenishmentWhLocation(@QueryParam(value = "ouid") Long ouid, BeanPropertyRowMapper<WarehouseLocationCommand> beanPropertyRowMapper);

    // 查询补货条件的库位商品
    @NativeQuery
    List<WarehouseLocationCommand> findMoveWhLocation(@QueryParam(value = "skuid") Long skuid, @QueryParam(value = "ouid") Long ouid, @QueryParam(value = "owner") String owner, BeanPropertyRowMapper<WarehouseLocationCommand> beanPropertyRowMapper);


    /**
     * 查寻当前仓库的暂存区
     * 
     * @param start
     * @param pageSize
     * @param map
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<WarehouseLocationCommand> findGILocation(int start, int pageSize, @QueryParam(value = "ouid") Long ouid, @QueryParam Map<String, Object> map, Sort[] sorts, BeanPropertyRowMapperExt<WarehouseLocationCommand> rowMap);


    @NativeQuery
    List<Long> findwhLocationOuid(RowMapper<Long> ouid);


    @NativeQuery(pagable = true)
    Pagination<WarehouseLocation> findValidLocationsByouid(int start, int pageSize, @QueryParam(value = "code") String code, @QueryParam(value = "ouid") Long ouid, BeanPropertyRowMapperExt<WarehouseLocation> beanPropertyRowMapperExt, Sort[] sorts);

    @NativeQuery
    List<String> findAllAvailLocationsByDistrictId(@QueryParam(value = "id") Long id, SingleColumnRowMapper<String> singleColumnRowMapper);

    /*
     * List<ProductBarcodeObj> findAllAvailLocationsByDistrictId(@QueryParam(value = "id") Long id,
     * BeanPropertyRowMapper<ProductBarcodeObj> rp);
     */

    @NativeQuery
    String findLocationCodeByid(@QueryParam(value = "id") Long id, SingleColumnRowMapper<String> singleColumnRowMapper);

    // 根据库存盘点条码解锁
    @NativeUpdate
    void unlockbyinvcheckcode(@QueryParam(value = "code") String code);

    @NativeQuery
    List<WarehouseLocationCommand> findEdwTwhLocation(RowMapper<WarehouseLocationCommand> rowMapper);

    @NativeQuery
    WarehouseLocationCommand findOneWarehouseLocationByOuidAndSku(@QueryParam("skuId") Long skuId, @QueryParam("statusId") Long statusId, @QueryParam("ouId") Long ouId, RowMapper<WarehouseLocationCommand> r);

    /**
     * 查找是否存在location&district
     * 
     * @param ouId
     * @param location
     * @param district
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    WhPickZoneInfoCommand findLocationByLocationAndDistrict(@QueryParam(value = "ouId") Long ouId, @QueryParam(value = "location") String location, @QueryParam(value = "district") String district,
            BeanPropertyRowMapper<WhPickZoneInfoCommand> beanPropertyRowMapper);

    /**
     * 创建拣货区域和顺序
     * 
     * @param ouId
     * @param location
     * @param district
     * @param pickZoneCode
     * @param userId
     * @param createDate
     */
    @NativeUpdate
    void updateByLocationAndDistrict(@QueryParam(value = "ouId") Long ouId, @QueryParam(value = "location") String location, @QueryParam(value = "district") String district, @QueryParam(value = "pickZoneCode") String pickZoneCode,
            @QueryParam(value = "sort") Integer sort, @QueryParam(value = "userId") Long userId, @QueryParam(value = "createDate") Date createDate, @QueryParam(value = "lastModifyTime") Date lastModifyTime);

    @NativeUpdate
    void createLog(@QueryParam(value = "ouId") Long ouId, @QueryParam(value = "location") String location, @QueryParam(value = "pickZoneCode") String pickZoneCode, @QueryParam(value = "sort") Integer sort, @QueryParam(value = "userId") Long userId,
            @QueryParam(value = "createDate") Date createDate);

    /**
     * 根据弹出口区域ID获取库位
     * 
     * @param popUpAreaId
     * @return
     */
    @NamedQuery
    List<WarehouseLocation> getWarehouseLocationByPopUpAreaId(@QueryParam(value = "popUpAreaId") Long popUpAreaId);

    /**
     * 查找库存上的库位编码和优先级
     * 
     * @param locCode
     * @param ouid
     * @param r
     * @return
     */
    @NativeQuery
    WarehouseLocation findLocaSortAndCodeByInv(@QueryParam(value = "staCode") String locCode, @QueryParam(value = "ouId") Long ouid, RowMapper<WarehouseLocation> r);

    /**
     * 根据T_WH_STA_OCP_LINE对应STA的ocp_code查找对应库位ID
     * 
     * @param id
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<String> findLocationIdByStaOcpLineId(@QueryParam(value = "id") Long id, SingleColumnRowMapper<String> singleColumnRowMapper);

}
