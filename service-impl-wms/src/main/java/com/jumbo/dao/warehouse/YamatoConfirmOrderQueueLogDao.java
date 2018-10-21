package com.jumbo.dao.warehouse;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.YamatoConfirmOrderQueueLog;

@Transactional
public interface YamatoConfirmOrderQueueLogDao extends GenericEntityDao<YamatoConfirmOrderQueueLog, Long> {


}
