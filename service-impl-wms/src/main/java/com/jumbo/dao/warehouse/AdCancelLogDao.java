
package com.jumbo.dao.warehouse;

import loxia.dao.GenericEntityDao;
import org.springframework.transaction.annotation.Transactional;
import com.jumbo.wms.model.warehouse.AdCancelLog;

@Transactional
public interface AdCancelLogDao extends GenericEntityDao<AdCancelLog, Long> {

}
