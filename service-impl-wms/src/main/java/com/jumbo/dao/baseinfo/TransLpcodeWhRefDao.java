package com.jumbo.dao.baseinfo;

import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.baseinfo.TransLpcodeWhRef;

/**
 * 新增外包仓品牌对接物流商
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
public interface TransLpcodeWhRefDao extends GenericEntityDao<TransLpcodeWhRef, Long> {
    @NamedQuery
    TransLpcodeWhRef getWhCarrierByLpCodeAndSource(@QueryParam("lpCode") String lpCode, @QueryParam("source") String source);
    @NamedQuery
    TransLpcodeWhRef getLpCodeByWhAndCarrier(@QueryParam("source")String vmiWhSourceGymboree, @QueryParam("carrier")String trackName);

}
