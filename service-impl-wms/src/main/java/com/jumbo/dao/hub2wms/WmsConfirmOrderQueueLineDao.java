package com.jumbo.dao.hub2wms;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.hub2wms.WmsConfirmOrderQueueLine;

/**
 * 
 * @author lzb
 * 
 */
@Transactional
public interface WmsConfirmOrderQueueLineDao extends GenericEntityDao<WmsConfirmOrderQueueLine, Long> {
    @NativeQuery
    List<WmsConfirmOrderQueueLine> getListBycId(@QueryParam("cId") Long cId);

}
