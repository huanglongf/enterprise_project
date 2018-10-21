package com.jumbo.dao.pickZone;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.command.pickZone.WhPickZoneInfoCommand;
import com.jumbo.wms.model.command.pickZone.WhPickZoonCommand;
import com.jumbo.wms.model.pickZone.WhPickZoon;
import com.jumbo.wms.model.warehouse.StockTransApplication;


/**
 * @author gianni.zhang
 * 
 *         2015年7月8日 下午4:02:57
 */

@Transactional
public interface WhPickZoneDao extends GenericEntityDao<WhPickZoon, Long> {

    @NativeQuery(pagable = true)
    Pagination<WhPickZoonCommand> findPickZoneList(int start, int pageSize, Sort[] sorts, @QueryParam("code") String code, @QueryParam("name") String name, @QueryParam("ouId") Long ouId, RowMapper<WhPickZoonCommand> rowMapper);

    @NativeQuery
    WhPickZoon findPickZoneByCode(@QueryParam("code") String code, @QueryParam("ouId") Long ouId, RowMapper<WhPickZoon> rowMapper);
    
    @NativeQuery
    List<WhPickZoon> findPickZoneInfo(@QueryParam("ouId") Long ouId, RowMapper<WhPickZoon> rowMapper);
    
    @NativeQuery(pagable = true)
    Pagination<WhPickZoneInfoCommand> findPickZoneInfoList(int start, int pageSize, Sort[] sorts, @QueryParam("district") String district, @QueryParam("location") String location, @QueryParam("pickZoneName") String pickZoneName, @QueryParam("pickZoneCode") String pickZoneCode, @QueryParam("ouId") Long ouId, RowMapper<WhPickZoneInfoCommand> rowMapper);
    
    @NativeQuery
    List<WhPickZoneInfoCommand> findPickZoneInfoList2Excel(@QueryParam("district") String district, @QueryParam("location") String location, @QueryParam("pickZoneName") String pickZoneName, @QueryParam("pickZoneCode") String pickZoneCode, @QueryParam("ouId") Long ouId, RowMapper<WhPickZoneInfoCommand> rowMapper);

    @NativeQuery
    BigDecimal countLocation(@QueryParam("district") String district, @QueryParam("location") String location, @QueryParam("pickZoneName") String pickZoneName, @QueryParam("pickZoneCode") String pickZoneCode, @QueryParam("ouId") Long ouId, SingleColumnRowMapper<BigDecimal> rowMap);

    @NativeQuery
    List<WhPickZoon> findAllPickZone(@QueryParam("ouId") Long ouId, RowMapper<WhPickZoon> rowmap);

    /**
     * 根据库位ID获取拣货区ID
     * 
     * @param locationIds
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<Long> findZoonIdsByLocationIds(@QueryParam("locationIds") Set<Long> locationIds, SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * 根据订单ID更新拣货区列表
     * 
     * @param zoonIds
     * @param staId
     */
    @NativeUpdate
    void updateStaZoonIdsByStaId(@QueryParam("zoonIds") String zoonIds, @QueryParam("staId") Long staId);
    
    /**
     * 根据拣货区域ID查询正在使用此区域的作业单
     * 
     * @param zoonId
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<StockTransApplication> findStasByZoonId(@QueryParam("zoonId") Long zoonId, RowMapper<StockTransApplication> rowMapper);
    
    /**
     * 根据拣货区域ID，清除库位上的拣货区域
     * 
     * @param zoonId
     */
    @NativeUpdate
    void clearLocationZoonByZoonId(@QueryParam("zoonId") Long zoonId);

    @NamedQuery
    List<WhPickZoon> getWhPickZoonByZoonId(@QueryParam("zoonId") Long zoonId);
}
