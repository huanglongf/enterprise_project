package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.ImportPrintData;
/**
 * 
 * @author jinlong.ke
 *
 */
@Transactional
public interface ImportPrintDataDao extends GenericEntityDao<ImportPrintData, Long> {
    @NamedQuery
    List<ImportPrintData> selectAllData();

}
