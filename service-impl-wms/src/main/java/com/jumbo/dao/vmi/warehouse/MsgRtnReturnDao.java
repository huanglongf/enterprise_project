package com.jumbo.dao.vmi.warehouse;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnReturn;

@Transactional
public interface MsgRtnReturnDao extends GenericEntityDao<MsgRtnReturn, Long> {

    @NativeUpdate
    void saveByIDSSHP(@QueryParam("recStatus") Integer recStatus, @QueryParam("status") Integer status, @QueryParam("source") String source);

    @NamedQuery
    List<MsgRtnReturn> queryByCanceledAndCreated(@QueryParam("source") String source);

    @NativeUpdate
    void saveForEtam(@QueryParam("vmiSource") String vmiSource, @QueryParam("staCode") String staCode, @QueryParam("type") int type);

    @NativeUpdate
    void updateFinishByStacode(@QueryParam(value = "staCode") String staCode);

    @NativeUpdate
    void updateFinishBySlipCode(@QueryParam(value = "staCode") String staCode, @QueryParam(value = "returnId") Long returnId);

    @NamedQuery
    List<MsgRtnReturn> queryRtnBySourceAndByStatus(@QueryParam("source") String source);

    @NamedQuery
    MsgRtnReturn findReturnBySlipCode(@QueryParam("slipCode") String slipCode);

    @NativeQuery
    List<MsgRtnReturn> findReturnByVmiSourceAndStatus(@QueryParam("vmiSource") String vmiSource, RowMapper<MsgRtnReturn> rowMapper);

}
