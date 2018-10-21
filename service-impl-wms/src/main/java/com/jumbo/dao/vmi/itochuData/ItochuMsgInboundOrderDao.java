package com.jumbo.dao.vmi.itochuData;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import com.jumbo.wms.model.vmi.warehouse.ItochuMsgInboundOrder;

@Transactional
public interface ItochuMsgInboundOrderDao extends GenericEntityDao<ItochuMsgInboundOrder, Long> {

    @NamedQuery
    ItochuMsgInboundOrder findItochuMsgByBoxAndSku(@QueryParam("billNo") String billNo, @QueryParam("boxNo") String boxNo, @QueryParam("sku") String sku);

    @NativeQuery
    List<ItochuMsgInboundOrder> findAllInboundOrderByStatus(@QueryParam("boxno") String boxno, RowMapper<ItochuMsgInboundOrder> rowMapper);

    @NativeQuery
    List<ItochuMsgInboundOrder> findAllInboundOrderBoxNoByStatus(RowMapper<ItochuMsgInboundOrder> rowMapper);

    @NativeUpdate
    void nativeUpdate(@QueryParam(value = "boxNo") String boxNo, @QueryParam(value = "sku") String sku, @QueryParam(value = "staId") Long staId, @QueryParam(value = "staLineId") Long staLineId, @QueryParam(value = "status") int status);

}
