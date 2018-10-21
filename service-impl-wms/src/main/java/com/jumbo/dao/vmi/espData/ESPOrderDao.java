package com.jumbo.dao.vmi.espData;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.espData.ESPOrder;
import com.jumbo.wms.model.vmi.espData.ESPOrderCommand;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

@Transactional
public interface ESPOrderDao extends GenericEntityDao<ESPOrder, Long> {

    @NativeQuery
    List<String> findNotOperateOrderSeqNum(RowMapper<String> rowMapper);

    @NativeQuery
    BigDecimal findBatchId(RowMapper<BigDecimal> rowMapper);

    @NativeQuery
    List<ESPOrder> findOrderAsnByPo(@QueryParam("po") String po, RowMapper<ESPOrder> rowMapper);

    @NativeQuery
    List<ESPOrderCommand> findOrdersGroupBySeqNumAndPO(@QueryParam("status") String status, RowMapper<ESPOrderCommand> rowMapper);

    @NamedQuery
    List<ESPOrder> findOrdersBySeqNumAndPO(@QueryParam("seqNum") String seqNum, @QueryParam("status") String status, @QueryParam("po") String po);

    @NativeQuery
    List<ESPOrderCommand> findESPOrderCommandByStvId(@QueryParam("stvId") Long stvId, RowMapper<ESPOrderCommand> rowMapper);

    @NativeQuery
    List<ESPOrderCommand> findESPOrderCommandByStvId1(@QueryParam("stvId") Long stvId, RowMapper<ESPOrderCommand> rowMapper);

    @NativeQuery
    List<ESPOrderCommand> findESPOrderCommandByStvId3(@QueryParam("staId") Long staId, RowMapper<ESPOrderCommand> rowMapper);

    @NativeQuery
    List<ESPOrderCommand> findESPOrderCommandByStvId2(@QueryParam("staId") Long staId, RowMapper<ESPOrderCommand> rowMapper);

    @NativeQuery
    BigDecimal getStaIdBySeqNum(@QueryParam("seqNum") String seqNum, RowMapper<BigDecimal> rowMapper);

    @NativeUpdate
    void updateOrderStatus(@QueryParam("staStatus") Integer staStatus, @QueryParam("seqNum") String seqNum, @QueryParam("poNum") String poNum, @QueryParam("status") String status);

    @NativeQuery(model = ESPOrder.class)
    ESPOrder getLastedOrderByPO(@QueryParam("seqNum") String seqNum);

}
