package com.jumbo.dao.vmi.gymboreeData;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.gymboreeData.GymboreeWarehouse;

/**
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
public interface GymboreeWarehouseDao extends GenericEntityDao<GymboreeWarehouse, Long> {
    @NativeUpdate
    void deleteAllWh();

    /**
     * 店存出库指令根据仓库code查询需要反馈的仓库id
     * 
     * @param fchrWarehouseID
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    String getIdByCode(@QueryParam("code") String code, SingleColumnRowMapper<String> singleColumnRowMapper);

    /**
     * 店存出库指令根据仓库code查询需要反馈的仓库id
     * 
     * @param fchrWarehouseID
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<GymboreeWarehouse> getIdByFchrWarehouseID(@QueryParam("code") String code, @QueryParam("fchrWarehouseID") String fchrWarehouseID, RowMapper<GymboreeWarehouse> rowMapper);

    @NamedQuery
    List<GymboreeWarehouse> getIdByFchrWarehouse(@QueryParam("code") String code, @QueryParam("fchrWarehouseID") String fchrWarehouseID);

}
