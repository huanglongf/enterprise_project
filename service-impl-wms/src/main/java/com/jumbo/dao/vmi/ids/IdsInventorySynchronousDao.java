package com.jumbo.dao.vmi.ids;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.ids.IdsInventorySynchronous;
@Transactional
public interface IdsInventorySynchronousDao extends GenericEntityDao<IdsInventorySynchronous, Long> {
    
    @NativeQuery
    List<IdsInventorySynchronous> findIdsInvSynByStatus(@QueryParam(value = "source") String source,@QueryParam(value = "itRtype") String itRtype,RowMapper<IdsInventorySynchronous> rowMapper);
    @NativeUpdate
    void updateMsgStatusById(@QueryParam(value = "status") int status,@QueryParam(value = "stacode") String stacode, @QueryParam(value = "msgid") Long msgid,@QueryParam(value = "remark") String remark);
    
    @NativeUpdate
    void updateMsgStaById(@QueryParam(value = "status") int status, @QueryParam(value = "msgid") Long msgid,@QueryParam(value = "staCode") String staCode);
    
    @NativeQuery
    List<IdsInventorySynchronous> findIdsInvSyn(@QueryParam(value = "source") String source,RowMapper<IdsInventorySynchronous> rowMapper);
    
    @NativeUpdate
    void updateMsgStatusBywmsCode(@QueryParam(value = "wmsCode") String wmsCode,@QueryParam(value = "staCode") String staCode);
    
    @NativeUpdate
    void updateMsgStaById2(@QueryParam(value = "msgid") Long msgid);
}
