package com.jumbo.dao.compensation;

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

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.compensation.ClaimProcess;
import com.jumbo.wms.model.compensation.ClaimResult;
import com.jumbo.wms.model.compensation.WhCompensation;
import com.jumbo.wms.model.compensation.WhCompensationCommand;

@Transactional
public interface WhCompensationDao extends GenericEntityDao<WhCompensation, Long> {

    @NamedQuery
    WhCompensation getWhCompensationBySysCode(@QueryParam("sysCode") String sysCode);

    @NamedQuery
    List<WhCompensation> getWhCompensationByClaimCode(@QueryParam("claimCode") String claimCode);

    @NativeQuery(pagable = true)
    Pagination<WhCompensationCommand> findCompensationByParams(int start, int pageSize, Sort[] sorts, @QueryParam Map<String, Object> com, RowMapper<WhCompensationCommand> rowMapper);

    @NativeQuery(pagable = true)
    Pagination<ClaimProcess> findClaimProcessByParams(int start, int pageSize, @QueryParam("startTime") Date startTime, @QueryParam("endTime") Date endTime, RowMapper<ClaimProcess> rowMapper);


    @NativeQuery(pagable = true)
    Pagination<ClaimResult> findClaimResultByParams(int start, int pageSize, @QueryParam("startTime") Date startTime, @QueryParam("endTime") Date endTime, RowMapper<ClaimResult> rowMapper);

    @NativeQuery
    List<WhCompensationCommand> findStatesIsCheck(RowMapper<WhCompensationCommand> rowMapper);

    @NativeUpdate
    void updateStatesIsSucceed(@QueryParam("whCompensationId") Long whCompensationId);

    @NamedQuery
    WhCompensation getWhCompensationByerpOrderCode(@QueryParam("erpOrderCode") String erpOrderCode);

    @NamedQuery
    WhCompensation getWhCompensationBytransCode(@QueryParam("transCode") String transCode, @QueryParam("transNumber") String transNumber);

    @NativeQuery
    List<WhCompensationCommand> findCompensationByParamsNoPage(@QueryParam Map<String, Object> com, RowMapper<WhCompensationCommand> rowMapper);

}
