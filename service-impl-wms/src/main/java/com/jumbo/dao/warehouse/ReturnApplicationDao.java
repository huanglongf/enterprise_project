package com.jumbo.dao.warehouse;

import java.util.Date;
import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.ReturnApplication;
import com.jumbo.wms.model.warehouse.ReturnApplicationCommand;

/**
 * 退货申请
 * 
 * @author xiaolong.fei
 * 
 */
@Transactional
public interface ReturnApplicationDao extends GenericEntityDao<ReturnApplication, Long> {

    @NativeQuery(pagable = true)
    Pagination<ReturnApplicationCommand> findReturnAppList(int start, int pageSize, RowMapper<ReturnApplicationCommand> rowMapper, Sort[] sorts, @QueryParam(value = "ouId") Long ouId, @QueryParam(value = "owner") String owner,
            @QueryParam(value = "omsStatus") String omsStatus, @QueryParam(value = "staCode") String staCode, @QueryParam(value = "code") String code, @QueryParam(value = "slipCode") String slipCode, @QueryParam(value = "slipCode2") String slipCode2,
            @QueryParam(value = "statusName") String statusName, @QueryParam(value = "trankNo") String trankNo, @QueryParam(value = "lpCode") String lpCode, @QueryParam(value = "returnUser") String returnUser,
            @QueryParam(value = "telephone") String telephone, @QueryParam(value = "source") String source, @QueryParam(value = "startTime") Date startTime, @QueryParam(value = "endTime") Date endTime,
            @QueryParam(value = "feekBackstartTime") Date feekBackstartTime, @QueryParam(value = "feekBackendTime") Date feekBackendTime,@QueryParam("status")String status,@QueryParam("brand")String brand);

    @NativeQuery(pagable = true)
    Pagination<ReturnApplicationCommand> findReturnAppList2(int start, int pageSize, RowMapper<ReturnApplicationCommand> rowMapper, Sort[] sorts, @QueryParam(value = "ouId") Long ouId, @QueryParam(value = "owner") String owner,
            @QueryParam(value = "omsStatus") String omsStatus, @QueryParam(value = "staCode") String staCode, @QueryParam(value = "code") String code, @QueryParam(value = "slipCode") String slipCode, @QueryParam(value = "slipCode2") String slipCode2,
            @QueryParam(value = "statusName") String statusName, @QueryParam(value = "trankNo") String trankNo, @QueryParam(value = "lpCode") String lpCode, @QueryParam(value = "returnUser") String returnUser,
            @QueryParam(value = "telephone") String telephone, @QueryParam(value = "source") String source, @QueryParam(value = "startTime") Date startTime, @QueryParam(value = "endTime") Date endTime,
            @QueryParam(value = "feekBackstartTime") Date feekBackstartTime, @QueryParam(value = "feekBackendTime") Date feekBackendTime,@QueryParam("status")String status,@QueryParam("brand")String brand);

    
    @NativeQuery
    ReturnApplicationCommand findReturnAppByTrackingNo(@QueryParam(value = "ouId") Long ouId, @QueryParam(value = "trackingNo") String trackingNo, RowMapper<ReturnApplicationCommand> r);

    @NativeQuery(model = ReturnApplicationCommand.class)
    List<ReturnApplicationCommand> fingReturnSkuByRaId(@QueryParam(value = "raId") Long raId);


    @NamedQuery
    ReturnApplication getReturnByCode(@QueryParam(value = "code") String code);
    
    @NamedQuery
    ReturnApplication getReturnByCode2(@QueryParam(value = "code") String code);


    @NamedQuery
    ReturnApplication getReturnByLpCode(@QueryParam(value = "lpCode") String lpCode);

    @NamedQuery
    List<ReturnApplication> getReturnByLpCode1(@QueryParam(value = "lpCode") String lpCode);

    @NativeUpdate
    void updateReturnApplication(@QueryParam(value = "OmsStatus") Integer omsStatus, @QueryParam(value = "status") Integer status, @QueryParam(value = "slipCode") String slipCode, @QueryParam(value = "trackingNo") String transferNum);

}
