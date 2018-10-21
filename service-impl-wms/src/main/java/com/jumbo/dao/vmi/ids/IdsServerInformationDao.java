package com.jumbo.dao.vmi.ids;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.ids.IdsServerInformation;

@Transactional
public interface IdsServerInformationDao extends GenericEntityDao<IdsServerInformation, Long> {
    @NamedQuery
    IdsServerInformation findServerInformationBySource(@QueryParam("source") String source);

    @NamedQuery
    IdsServerInformation findServerInformationByFacility(@QueryParam("facility") String facility);

    /**
     * 查询所有配置信息
     * 
     * @return
     */
    @NamedQuery
    List<IdsServerInformation> findAll();
}
