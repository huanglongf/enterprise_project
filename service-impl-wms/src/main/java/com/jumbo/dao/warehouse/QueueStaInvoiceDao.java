package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.QueueStaInvoice;

@Transactional
public interface QueueStaInvoiceDao extends GenericEntityDao<QueueStaInvoice, Long> {
    /**
     * 根据qStaId获取发票信息
     * 
     * @return
     */
    @NativeQuery
    public List<QueueStaInvoice> findByQstaId(@QueryParam(value = "qstaId") Long qstaId, BeanPropertyRowMapperExt<QueueStaInvoice> beanPropertyRowMapperExt);

    /**
     * 删除数据
     * 
     * @param id
     */
    @NativeUpdate
    void cleanDataByInvoiceId(@QueryParam("id") Long id);
}
