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
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.ReturnPackage;
import com.jumbo.wms.model.warehouse.ReturnPackageCommand;

@Transactional
public interface ReturnPackageDao extends GenericEntityDao<ReturnPackage, Long> {

    /**
     * 获取序列号
     * 
     * @param s
     * @return
     */
    @NativeQuery
    public Long getReturnPackageSequence(SingleColumnRowMapper<Long> s);


    @NativeQuery(pagable = true)
    Pagination<ReturnPackageCommand> findReturnPackage(int start, int pageSize, @QueryParam(value = "createTime") Date createTime, @QueryParam(value = "entCreateTime") Date entCreateTime, @QueryParam(value = "staCode") String staCode,
            @QueryParam(value = "lpcode") String lpcode, @QueryParam(value = "trackingNo") String trackingNo, @QueryParam(value = "batchCode") String batchCode, @QueryParam(value = "intStatus") Integer intStatus,
            @QueryParam(value = "rejectionReasons") String rejectionReasons, @QueryParam(value = "whName") String whName,@QueryParam(value = "registerWHId") String registerWHId ,@QueryParam(value = "userName") String userName, Sort[] sorts, RowMapper<ReturnPackageCommand> rowMapper);

    @NativeQuery
    List<ReturnPackageCommand> findReturnPackageList(@QueryParam(value = "batchCode") String batchCode, @QueryParam(value = "dataFormat") String dataFormat, RowMapper<ReturnPackageCommand> rowMapper);

    @NamedQuery
    List<ReturnPackage> getPackageByTrackingNo(@QueryParam("trackingNo") String trackingNo);

    @NativeUpdate
    void updatePackageStatus(@QueryParam("trackingNo") String trackingNo, @QueryParam("status") int status, @QueryParam("staId") Long staId, @QueryParam("ouId") Long ouId);
}
