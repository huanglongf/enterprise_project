package com.jumbo.dao.vmi.warehouse;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCommand;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundReturn;

@Transactional
public interface MsgOutboundReturnDao extends GenericEntityDao<MsgOutboundReturn, Long> {

    @NativeQuery
    List<Long> findMsgOutboundReturnIds(@QueryParam(value = "source") String source, RowMapper<Long> ids);

    @NativeQuery
    MsgOutboundOrderCommand findMsgOutboundReturnByMsgId(@QueryParam("msgId") Long msgId, RowMapper<MsgOutboundOrderCommand> rowMapper);
}
