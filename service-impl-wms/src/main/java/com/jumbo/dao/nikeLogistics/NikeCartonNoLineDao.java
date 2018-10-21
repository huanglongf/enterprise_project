package com.jumbo.dao.nikeLogistics;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.nikeLogistics.NikeCartonNoLine;

@Transactional
public interface NikeCartonNoLineDao extends GenericEntityDao<NikeCartonNoLine, Long> {
    /**
     * 查询明细信息
     * 
     * @return
     */
    @NamedQuery
    public List<NikeCartonNoLine> findByCartonNoId(@QueryParam("cartonNoId") Long cartonNoId);

}
