package com.jumbo.dao.warehouse;

import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.AdPackageLineDeal;
import com.jumbo.wms.model.warehouse.AdPackageLineDealDto;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

@Transactional
public interface AdPackageLineDealDao extends GenericEntityDao<AdPackageLineDeal, Long> {
    /**
     * 查询未推送给ad的数据
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<AdPackageLineDealDto> adPackageUpdate(RowMapper<AdPackageLineDealDto> beanPropertyRowMapper);

    /**
     * 根据ad工单编码查实体
     */
    @NativeQuery
    AdPackageLineDealDto  getAdPackageLineDealDtoByAdOrderId( @QueryParam("adOrderId") String  adOrderId,RowMapper<AdPackageLineDealDto> beanPropertyRowMapper);

    @NativeQuery(pagable = true)
    Pagination<AdPackageLineDealDto> adPackageList(int start, int pageSize, RowMapper<AdPackageLineDealDto> rowMapper, Sort[] sorts, @QueryParam(value = "adOrderId") String adOrderId, @QueryParam(value = "wmsOrderId") String wmsOrderId,
            @QueryParam(value = "extended") String extended, @QueryParam(value = "adOrderType") String adOrderType, @QueryParam(value = "status") Integer status, @QueryParam(value = "trankNo") String trankNo, @QueryParam(value = "lpCode") String lpCode,
            @QueryParam(value = "fromTime") Date fromTime, @QueryParam(value = "endTime") Date endTime, @QueryParam(value = "opStatus") Integer opStatus, @QueryParam(value = "skuId") String skuId);


    @NativeQuery
    AdPackageLineDeal adPackageDetail(@QueryParam("id") Long id, BeanPropertyRowMapper<AdPackageLineDeal> beanPropertyRowMapper);


    @NativeUpdate
    void adPackageCommit(@QueryParam("id") Long id, @QueryParam("wmsStatus") String wmsStatus, @QueryParam("remark") String remark, @QueryParam("userName") String userName);
}
