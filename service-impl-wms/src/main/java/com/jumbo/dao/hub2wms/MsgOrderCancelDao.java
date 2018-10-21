package com.jumbo.dao.hub2wms;

import java.util.Date;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.hub2wms.MsgOrderCancel;

/**
 * 
 * 
 * @author cheng.su
 * 
 */
@Transactional
public interface MsgOrderCancelDao extends GenericEntityDao<MsgOrderCancel, Long> {

    @NativeQuery(pagable = true)
    public Pagination<MsgOrderCancel> wmsCancelOrderConfirm(int start, int size, @QueryParam(value = "startTime") Date startTime, @QueryParam(value = "endTime") Date endTime, @QueryParam(value = "systemKey") String systemKey, Sort[] sorts,
            RowMapper<MsgOrderCancel> rowMapper);

    @NativeQuery
    public MsgOrderCancel listStaCode(@QueryParam(value = "staCode") String staCode, RowMapper<MsgOrderCancel> rowMapper);

}
