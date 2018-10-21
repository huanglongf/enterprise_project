package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.YamatoConfirmOrderQueue;

@Transactional
public interface YamatoConfirmOrderQueueDao extends GenericEntityDao<YamatoConfirmOrderQueue, Long> {

    @NamedQuery
    List<YamatoConfirmOrderQueue> getYamatoInfoSendHub();

}
