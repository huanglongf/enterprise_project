package com.jumbo.dao.vmi.espData;

import java.util.Date;
import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.espData.ESPMarkDown;

@Transactional
public interface ESPMarkDownDao extends GenericEntityDao<ESPMarkDown, Long> {

    @NativeUpdate
    void updateMarkDownForStatu(@QueryParam("newStatus") Integer newStatus, @QueryParam("oldStatus") Integer oldStatus, @QueryParam("startDate") Date startDate, @QueryParam("endDate") Date endDate);

    @NativeUpdate
    void updateMarkDownForStatuSeq(@QueryParam("newStatus") Integer newStatus, @QueryParam("oldStatus") Integer oldStatus, @QueryParam("seqNum") String seqNum);

    @NativeQuery(model = ESPMarkDown.class)
    List<ESPMarkDown> findMarkDownList(@QueryParam("status") Integer newStatus);

}
