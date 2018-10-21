package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.QueueGiftLine;

@Transactional
public interface QueueGiftLineDao extends GenericEntityDao<QueueGiftLine, Long> {
    @NativeQuery
    public List<QueueGiftLine> getByfindQstaline(@QueryParam(value = "lineid") Long lineid, BeanPropertyRowMapper<QueueGiftLine> beanPropertyRowMapper);

    @NativeUpdate
    public void lineDelete(@QueryParam(value = "lineid") Long lineid);


}
