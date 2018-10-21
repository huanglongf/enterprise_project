package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.ReturnApplicationLine;

/**
 * 退货申请明细
 * 
 * @author xiaolong.fei
 * 
 */
@Transactional
public interface ReturnApplicationLineDao extends GenericEntityDao<ReturnApplicationLine, Long> {

    @NativeUpdate
    void deleteLineByRaId(@QueryParam(value = "raId") Long raId);

    @NamedQuery
    List<ReturnApplicationLine> getRtnLineByRtnId(@QueryParam(value = "raId") Long raId);

}
