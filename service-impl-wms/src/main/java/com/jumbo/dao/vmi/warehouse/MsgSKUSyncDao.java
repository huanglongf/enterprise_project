package com.jumbo.dao.vmi.warehouse;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.vmi.warehouse.MsgSKUSync;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

@Transactional
public interface MsgSKUSyncDao extends GenericEntityDao<MsgSKUSync, Long> {

    @NativeQuery
    List<MsgSKUSync> findVmiMsgSKUSync(@QueryParam(value = "source") String source, RowMapper<MsgSKUSync> rowMapper);

    @NativeUpdate
    void updateStatusById(@QueryParam(value = "sta") int sta, @QueryParam(value = "msgId") Long msgId);

    @NativeQuery
    Long findSKUSyncBatchNo(RowMapper<Long> batchNo);


    @NativeUpdate
    void updateBatchNoByID(@QueryParam(value = "batchNo") Long batchNo, @QueryParam(value = "SKUSyncId") Long SKUSyncId);

    @NativeUpdate
    void updateStatusByBatchNo(@QueryParam(value = "status") int status, @QueryParam(value = "batchNo") Long batchNo);

    @NamedQuery
    List<MsgSKUSync> findeMsgSKUSyncBybatchId(@QueryParam(value = "batchId") Long batchId, @QueryParam(value = "status") DefaultStatus status);

    /**
     * 获取所有可以同步给SF商品货主信息bin,hu
     */
    @NativeQuery
    List<String> findSfFlagSKUSync(@QueryParam(value = "source") String source, SingleColumnRowMapper<String> s);

    /**
     * 通过来源（SF）和货主获取所有能同步给SF商品信息
     */
    @NativeQuery
    List<MsgSKUSync> findVmiMsgSKUSyncForSfFlag(@QueryParam(value = "source") String source, @QueryParam(value = "sfFlag") String sfFlag, RowMapper<MsgSKUSync> rowMapper);

}
