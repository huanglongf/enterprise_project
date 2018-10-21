package com.jumbo.dao.automaticEquipment;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.automaticEquipment.MsgToWcsLog;

/**
 * @author jinlong.ke
 * @date 2016年6月7日下午5:45:47
 * 
 */
@Transactional
public interface MsgToWcsLogDao extends GenericEntityDao<MsgToWcsLog, Long> {
    @NativeUpdate
    Integer insertMsgToWcsLog(@QueryParam(value = "msgId") Long msgId);

    @NativeQuery
    List<String> findMsgToWcsByType(@QueryParam(value = "plCode") String plCode, @QueryParam(value = "type") Integer type, RowMapper<String> rowMap);

}
