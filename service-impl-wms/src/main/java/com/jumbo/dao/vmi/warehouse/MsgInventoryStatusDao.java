package com.jumbo.dao.vmi.warehouse;

import java.util.List;
import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.warehouse.MsgInventoryStatus;

@Transactional
public interface MsgInventoryStatusDao extends GenericEntityDao<MsgInventoryStatus, Long> {

    @NativeQuery
    List<MsgInventoryStatus> findMsgInvStatusByStaCode(@QueryParam(value = "staCode") String staCode, RowMapper<MsgInventoryStatus> RowMapper);

    @NativeQuery
    String findInventoryStatusByBzStatus(@QueryParam(value = "whstatus") Long bzstatus, @QueryParam(value = "source") String Source, SingleColumnRowMapper<String> s);
       
    @NamedQuery
    MsgInventoryStatus findInventoryStatusByVmiStatus(@QueryParam(value = "source") String source,@QueryParam(value = "vmiStatus") String vmiStatus,@QueryParam(value = "sourceWh") String sourceWh);
}
