package com.jumbo.dao.vmi.itData;

import java.util.List;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.itData.ITVMIReceiveInfo;
import com.jumbo.wms.model.vmi.itData.VMIITReceiveCommand;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

@Transactional
public interface ITVMIReceiveInfoDao extends GenericEntityDao<ITVMIReceiveInfo, Long> {
    @NamedQuery
    List<ITVMIReceiveInfo> findReceiveInfosByStaId(@QueryParam("staId") Long staId, @QueryParam("status") int status);

    @NativeQuery
    List<Long> findStaIdListByStatus(@QueryParam("status") int status, RowMapper<Long> rowMapper);

    @NativeQuery
    List<VMIITReceiveCommand> findReceiveInfoGroupByTranId(@QueryParam("status") int status, @QueryParam("vender") String vender, @QueryParam("toLocation") String toLocation, RowMapper<VMIITReceiveCommand> rowMapper);

    @NativeQuery
    List<String> findNewTolocationByVender(@QueryParam("vender") String vender, RowMapper<String> rowMapper);


    @NativeUpdate
    void updateReceiveInfoStatusByVender(@QueryParam("status") int status, @QueryParam("vender") String vender, @QueryParam("toLocation") String toLocation, @QueryParam("toStatus") int toStatus);

    @NativeQuery
    List<String> findInnerShopCodeFromReceiving(RowMapper<String> rowMapper);

}
