/**
 * 
 */
package com.jumbo.dao.warehouse;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.DwhDistriButionAreaLoc;
import com.jumbo.wms.model.warehouse.DwhDistriButionAreaLocCommand;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

/**
 * @author lijinggong+2018年8月6日
 *
 * 
 */
@Transactional
public interface DwhDistriButionAreaLocDao extends GenericEntityDao<DwhDistriButionAreaLoc, Long>{
    //根据分配区域编码查询相关的DISTRIBUTION_ID;
    @NativeQuery
    Integer getDistriButionId(@QueryParam("mainWhid") Long mainWhid,@QueryParam("distriButionCode") String distriButionCode,@QueryParam("distriButionName") String distriButionName,RowMapper<Integer> rowMapper);

    //根据库位和库区查询LOCATION_ID
    @NativeQuery
    Integer getLocationId(@QueryParam("code") String code,@QueryParam("codeName") String codeName,RowMapper<Integer> rowMapper);
   
    //根据distriButionId和locationId判断数据是否已经存在
    @NativeQuery 
    int getNum(@QueryParam("distriButionId") Integer distriButionId,@QueryParam("locationId") Integer locationId,RowMapper<Integer> rowMapper);
    
    @NativeQuery 
    List<DwhDistriButionAreaLocCommand> exportDistriButionArea(@QueryParam("mainWhid") Long mainWhid,
                                                                      @QueryParam("locCodeName") String locCodeName,
                                                                      @QueryParam("locCode") String locCode,
                                                                      @QueryParam("locDistriButionAreaCode") String locDistriButionAreaCode,
                                                                      @QueryParam("locDistriButionAreaName") String locDistriButionAreaName,
                                                                      RowMapper<DwhDistriButionAreaLocCommand> rowMapper);
    
}
