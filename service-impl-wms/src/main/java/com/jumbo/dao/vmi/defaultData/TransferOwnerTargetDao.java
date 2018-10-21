package com.jumbo.dao.vmi.defaultData;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.Default.TransferOwnerTarget;
import com.jumbo.wms.model.vmi.Default.TransferOwnerTargetCommand;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;

@Transactional
public interface TransferOwnerTargetDao extends GenericEntityDao<TransferOwnerTarget, Long> {

    @NativeQuery
    List<String> findTargetOwnerByStaId(@QueryParam("staId") Long staId, SingleColumnRowMapper<String> scr);
    
    @NativeQuery
    List<TransferOwnerTargetCommand> findTargetRatioByStaId(@QueryParam("staId") Long staId, @QueryParam("targetOwner") String targetOwner, @QueryParam("invStatusId") Long invStatusId, BeanPropertyRowMapper<TransferOwnerTargetCommand> scr);

    @NativeQuery
    List<TransferOwnerTarget> findTargetRatioBySourceAndSku(@QueryParam("skuId") Long skuId, @QueryParam("sourceOwner") String sourceOwner, @QueryParam("ouId") Long ouId, BeanPropertyRowMapper<TransferOwnerTarget> scr);

    @NamedQuery
    TransferOwnerTarget getTransferOwnerTarget(@QueryParam("skuId") Long skuId, @QueryParam("sourceOwner") String sourceOwner, @QueryParam("targetOwner") String targetOwner, @QueryParam("ouId") Long ouId);

    @NativeQuery
    List<TransferOwnerTargetCommand> findAllSourceOwner(@QueryParam("ouId") Long ouId, BeanPropertyRowMapper<TransferOwnerTargetCommand> scr);

    @NativeQuery
    List<TransferOwnerTargetCommand> findAllTargetOwner(@QueryParam("ouId") Long ouId, @QueryParam("sourceOwner") String sourceOwner, BeanPropertyRowMapper<TransferOwnerTargetCommand> scr);

    @NativeQuery(pagable = true)
    Pagination<TransferOwnerTargetCommand> findTransferOwnerTarget(int start, int pageSize, @QueryParam Map<String, Object> m, RowMapper<TransferOwnerTargetCommand> rowMapper);

    @NativeQuery
    List<Long> findTransferInvStatusIdByStaId(@QueryParam("staId") Long staId, SingleColumnRowMapper<Long> scr);
}
