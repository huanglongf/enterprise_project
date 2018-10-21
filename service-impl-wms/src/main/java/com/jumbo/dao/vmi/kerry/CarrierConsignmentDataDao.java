package com.jumbo.dao.vmi.kerry;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.kerry.CarrierConsignmentData;

@Transactional
public interface CarrierConsignmentDataDao extends GenericEntityDao<CarrierConsignmentData, Long> {
	
    @NamedQuery
    List<CarrierConsignmentData> findDataByStatus();

}
