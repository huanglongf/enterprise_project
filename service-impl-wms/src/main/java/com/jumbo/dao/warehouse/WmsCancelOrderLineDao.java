package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.WmsCancelOrderLine;

@Transactional
public interface WmsCancelOrderLineDao extends GenericEntityDao<WmsCancelOrderLine, Long> {
    
    /**
     * 获取报缺明细
     * @param staId
     * @param r
     * @return
     */
    @NativeQuery
    List<WmsCancelOrderLine> findCancelLine(@QueryParam("staId")Long staId,RowMapper<WmsCancelOrderLine>r);
    
    /**
     * 获取报缺明细(已保存)
     * @param staId
     * @param r
     * @return
     */
    @NativeQuery
    List<WmsCancelOrderLine> findCancelLine1(@QueryParam("staId")Long staId,RowMapper<WmsCancelOrderLine>r);
    
    /**
     * 
     * @param id
     * @return
     */
    @NamedQuery
    List<WmsCancelOrderLine> findCancelLineBycaId(@QueryParam("id")Long id);
}
