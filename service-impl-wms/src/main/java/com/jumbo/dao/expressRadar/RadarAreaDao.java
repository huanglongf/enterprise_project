package com.jumbo.dao.expressRadar;

import java.util.List;
import java.util.Map;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.expressRadar.SysRadarArea;

@Transactional
public interface RadarAreaDao extends GenericEntityDao<SysRadarArea, Long> {

    @NativeQuery(pagable = true)
    Pagination<SysRadarArea> findRadarAreaByParams(int start, int pageSize, Sort[] sorts, @QueryParam("province") String province, @QueryParam("city") String city, RowMapper<SysRadarArea> rowMapper);

    @NativeQuery
    List<SysRadarArea> findRadarAreaByID(@QueryParam("ids") List<Long> ids, RowMapper<SysRadarArea> rowMapper);

    @NativeUpdate
    void updateRadarAreaStatus(@QueryParam("ids") List<Long> ids, @QueryParam("status") Long status);

    @NativeQuery
    SysRadarArea findStatusByArea(@QueryParam Map<String, String> m, RowMapper<SysRadarArea> rowMapper);

    @NativeQuery
    List<SysRadarArea> findRadarAreaProvince(RowMapper<SysRadarArea> rowMapper);

    @NativeQuery
    List<SysRadarArea> findRadarAreaCity(@QueryParam("province") String province, RowMapper<SysRadarArea> rowMapper);

    @NativeQuery
    SysRadarArea findRadarAreaByProvinceCity(@QueryParam("province") String province, @QueryParam("city") String city, RowMapper<SysRadarArea> rowMapper);

    /**
     * 根据省市模糊查询
     * 
     * @param province
     * @param city
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<SysRadarArea> findRadarAreaByVagueProvinceCity(@QueryParam("province") String province, @QueryParam("city") String city, RowMapper<SysRadarArea> rowMapper);

}
