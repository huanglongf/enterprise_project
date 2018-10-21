package com.jumbo.dao.vmi.espData;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.jumbo.wms.model.vmi.espData.ESPOrderCommand;
import com.jumbo.wms.model.vmi.espData.ESPTransferOrder;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;


public interface ESPTransferOrderDao extends GenericEntityDao<ESPTransferOrder, Long> {
    @NativeUpdate
    void createTransferOrder(@QueryParam("stvId") Long stvId);

    @NativeQuery
    List<ESPOrderCommand> findTransferOrdsGroupBySeqNumAndPO(RowMapper<ESPOrderCommand> rowMapper);

    @NativeQuery
    List<Long> findStaIdNotGenerateTransfer(RowMapper<Long> rowMapper);

    @NativeQuery
    List<Long> findInvIdNotGenerateTransfer(RowMapper<Long> rowMapper);

    @NativeQuery
    String getTONodeByStaID(@QueryParam("staId") String staId, RowMapper<String> rowMapper);

    @NativeQuery
    String getTONodeByInvID(@QueryParam("invId") String staId, RowMapper<String> rowMapper);

    @NamedQuery
    List<ESPTransferOrder> findTransferOrdersByStaId(@QueryParam("staId") String staId);

    @NamedQuery
    List<ESPTransferOrder> findTransferOrdersByInvId(@QueryParam("invId") String invId);

    @NamedQuery
    List<ESPTransferOrder> findOrdersByStaIdEndWithO(@QueryParam("staId") Long staId);

    @NamedQuery
    List<ESPTransferOrder> findOrdersByStaIdNotEndWithO(@QueryParam("staId") Long staId);

    @NamedQuery
    List<ESPTransferOrder> findOrdersByInvIdEndWithO(@QueryParam("invId") Long invId);

    @NamedQuery
    List<ESPTransferOrder> findOrdersByInvIdNotEndWithO(@QueryParam("invId") Long invId);

    @NativeQuery
    List<String> getPoByStaId(@QueryParam("staId") Long staId, RowMapper<String> rowMapper);

}
