package com.jumbo.dao.nikeLogistics;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.nikeLogistics.NikeCartonNo;

@Transactional
public interface NikeCartonNoDao extends GenericEntityDao<NikeCartonNo, Long> {
    /**
     * 查询未推送的数据
     * 
     * @return
     */
    @NamedQuery
    public List<NikeCartonNo> queryNikeCartonNo();

    @NamedQuery
    public NikeCartonNo findByTrackingNo(@QueryParam(value = "trackingNo") String trackingNo);

}
