package com.jumbo.dao.warehouse;


import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.NikeTurnCreate;

@Transactional
public interface NikeTurnCreateDao extends GenericEntityDao<NikeTurnCreate, Long> {
    /**
     * 查出尚未操作数据
     * 
     * @param r
     * @return
     */
    @NamedQuery
    public List<NikeTurnCreate> findNikeTurnStatus();


}
