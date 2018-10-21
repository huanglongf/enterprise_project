package com.jumbo.dao.expressRadar;


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

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.command.expressRadar.ExpressOrderQueryCommand;
import com.jumbo.wms.model.command.expressRadar.RadarTransNoCommand;
import com.jumbo.wms.model.expressRadar.RadarTransNo;


@Transactional
public interface RadarTransNoDao extends GenericEntityDao<RadarTransNo, Long> {


    @NativeQuery(pagable = true)
    Pagination<RadarTransNoCommand> findRadarTransNoByParams(int start, int pageSize, @QueryParam Map<String, Object> m, RowMapper<RadarTransNoCommand> rowMapper);

    @NativeUpdate
    void updateRadarTransNoStatus(@QueryParam(value = "expressCode") String expressCode, @QueryParam(value = "status") Integer status);

    @NativeUpdate
    void updateRadarTransNoTakingTime(@QueryParam(value = "expressCode") String expressCode, @QueryParam(value = "takingTime") Date takingTime);

    /*
     * @NativeUpdate List<String> findStatus();
     */

    @NativeQuery(pagable = true)
    Pagination<ExpressOrderQueryCommand> findExpressInfoByParams(int start, int pageSize, @QueryParam("lpCode") String lpCode, @QueryParam("expressCode") String expressCode, @QueryParam("pwhName") String pwhName,
            @QueryParam("warningLv") String warningLv, @QueryParam("orderCode") String orderCode, @QueryParam("takingTime") Date takingTime, @QueryParam("startDate") Date startDate, @QueryParam("endDate") Date endDate, @QueryParam("pcode") String pcode,
            @QueryParam("owner") String owner, @QueryParam("province") String province, @QueryParam("city") String city, @QueryParam("status") String status, @QueryParam("warningType") String warningType, Sort[] sorts,
            RowMapper<ExpressOrderQueryCommand> rowMapper);

    @NamedQuery
    RadarTransNo getRadarTransNoByExpressCode(@QueryParam(value = "expressCode") String expressCode);



    /**
     * 快递汇总信息查询
     * 
     * @param start
     * @param pageSize
     * @param sorts
     * @param rowMapper
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<RadarTransNoCommand> findExpressInfoCount(int start, int pageSize, Sort[] sorts, @QueryParam("lpcode") String lpcode, @QueryParam(value = "owner") String owner, @QueryParam("destinationProvince") String destinationProvince,
            @QueryParam(value = "physicalWarehouseId") Long physicalWarehouseId, RowMapper<RadarTransNoCommand> rowMapper);

    /**
     * 快递汇总信息查询 （省市）
     * 
     * @param start
     * @param pageSize
     * @param province
     * @param lpcode
     * @param owner
     * @param physicalWarehouseId
     * @param rowMapper
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<RadarTransNoCommand> findExpressDetailList(int start, int pageSize, @QueryParam(value = "province") String province, @QueryParam(value = "lpcode") String lpcode, @QueryParam(value = "owner") String owner,
            @QueryParam(value = "physicalWarehouseId") Long physicalWarehouseId, RowMapper<RadarTransNoCommand> rowMapper);

    /**
     * 统计 快递汇总信息查询数据
     * 
     * @param rowMapper
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<RadarTransNoCommand> getExpreessTotal(int start, int pageSize, Sort[] sorts, @QueryParam("lpcode") String lpcode, @QueryParam(value = "owner") String owner, @QueryParam("destinationProvince") String destinationProvince,
            @QueryParam(value = "physicalWarehouseId") Long physicalWarehouseId, RowMapper<RadarTransNoCommand> rowMapper);

    /**
     * 快递雷达状态详情
     * 
     * @param rowMapper
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<RadarTransNoCommand> findExpressStatusInfo(int start, int pageSize, Sort[] sorts, @QueryParam("province") String province, @QueryParam("lpcode") String lpcode, @QueryParam("exceprtionStatus") String exceprtionStatus,
            @QueryParam(value = "owner") String owner, RowMapper<RadarTransNoCommand> rowMapper);



    /**
     * 异常 详情
     * 
     * @param start
     * @param pageSize
     * @param sorts
     * @param rowMapper
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<RadarTransNoCommand> findAllExpressExceptInfo(int start, int pageSize, @QueryParam(value = "province") String province, @QueryParam(value = "lpcode") String lpcode, @QueryParam(value = "warehouseName") String warehouseName,
            @QueryParam(value = "owner") String owner, RowMapper<RadarTransNoCommand> rowMapper);

    /**
     * 加载省
     * 
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<RadarTransNo> getExpressProvince(RowMapper<RadarTransNo> rowMapper);

    /**
     * 初始化 仓库
     * 
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<RadarTransNoCommand> getexpressWarehouse(BeanPropertyRowMapper<RadarTransNoCommand> beanPropertyRowMapper);

    /**
     * 加载店铺
     * 
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<RadarTransNo> getExpressOwner(RowMapper<RadarTransNo> rowMapper);

    /**
     * 获取有可能揽收超时的数据
     * 
     * @param timeRule
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<RadarTransNoCommand> findTakesTimeoutExpress(int start, int pageSize, RowMapper<RadarTransNoCommand> rowMapper);

    /**
     * 获取有可能配送超时的数据
     * 
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<RadarTransNoCommand> findDeliveryTimeoutExpress(int start, int pageSize, RowMapper<RadarTransNoCommand> rowMapper);

    @NativeUpdate
    void updateRdTransNoWarning(@QueryParam(value = "expressCode") String expressCode, @QueryParam(value = "lvId") Long lvId, @QueryParam(value = "warningReason") Long warningReason);

    /**
     * 获取有可能有其它预警的数据
     * 
     * @return
     */
    @NativeQuery
    List<RadarTransNoCommand> findOtherWarningExpress(@QueryParam(value = "statusCode") String statusCode, RowMapper<RadarTransNoCommand> rowMapper);

    /**
     * 根据快递单号更新最新的快递状态
     * 
     * @param expressCode
     */
    @NativeUpdate
    void updateRdTransNoRouteStatus(@QueryParam(value = "expressCode") String expressCode, @QueryParam(value = "routeStatusId") Long routeStatusId);

    /**
     * 根据快递id查找快递信息
     * 
     * @param id
     */
    @NativeQuery
    RadarTransNo findRadarTransNoById(@QueryParam(value = "id") Long id, RowMapper<RadarTransNo> rowMapper);

    /**
     * 根据快递单号查找其最新状态
     * 
     * @param expressCode
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    Long findRouteStatusByTimeAndExpressCode(@QueryParam(value = "expressCode") String expressCode, SingleColumnRowMapper<Long> singleColumnRowMapper);
}
