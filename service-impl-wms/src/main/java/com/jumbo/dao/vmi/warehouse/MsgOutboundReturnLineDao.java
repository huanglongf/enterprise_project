package com.jumbo.dao.vmi.warehouse;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderLineCommand;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundReturnLine;

@Transactional
public interface MsgOutboundReturnLineDao extends GenericEntityDao<MsgOutboundReturnLine, Long> {

    @NativeQuery
    List<MsgOutboundOrderLineCommand> findeMsgOutLintBymsgReturnId(@QueryParam("msgReturnId") Long msgOrderId, RowMapper<MsgOutboundOrderLineCommand> rowMapper);

}
