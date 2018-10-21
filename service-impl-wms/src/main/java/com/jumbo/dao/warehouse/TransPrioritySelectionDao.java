package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.trans.TransPrioritySelection;

/**
 * EMS关键字维护
 * 
 * @author xiaolong.fei
 * 
 */
@Transactional
public interface TransPrioritySelectionDao extends GenericEntityDao<TransPrioritySelection, Long> {
    @NativeQuery
    List<TransPrioritySelection> findTrasPriSel(@QueryParam("channelId") Long channelId, RowMapper<TransPrioritySelection> r);

    @NativeUpdate
    void deleteAllTrasPriSel(@QueryParam("channelId") Long channelId);
}
