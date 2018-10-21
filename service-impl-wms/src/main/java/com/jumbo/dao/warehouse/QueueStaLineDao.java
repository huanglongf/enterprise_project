package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.QueueStaLine;
import com.jumbo.wms.model.warehouse.QueueStaLineCommand;

@Transactional
public interface QueueStaLineDao extends GenericEntityDao<QueueStaLine, Long> {
    /**
     * 获取sta line
     * 
     * @param staId
     * @return
     */
    @NamedQuery
    List<QueueStaLine> findByStaId(@QueryParam(value = "qstaId") Long qstaId);

    @NamedQuery
    List<QueueStaLine> findByStaIddir(@QueryParam(value = "qstaId") Long qstaId);

    @NativeQuery
    List<QueueStaLine> findQueueLineDetial(@QueryParam(value = "qstaId") Long qstaId, RowMapper<QueueStaLine> rowMapper);

    @NativeQuery
    List<QueueStaLine> findSkuQtyByStaId(@QueryParam(value = "qstaId") Long qstaId, RowMapper<QueueStaLine> rowMapper);

    @NativeQuery
    List<QueueStaLineCommand> findSkuQtyByStaId1(@QueryParam(value = "qstaId") Long qstaId, RowMapper<QueueStaLineCommand> rowMapper);

    @NativeQuery
    List<QueueStaLine> queryStaId(@QueryParam(value = "qstaId") Long qstaId, RowMapper<QueueStaLine> rowMapper);

    @NativeQuery
    Long querycountQty(@QueryParam(value = "qstaId") Long qstaId, SingleColumnRowMapper<Long> singleColumnRowMapper);

    @NamedQuery
    List<QueueStaLine> findByStaIdIn(@QueryParam(value = "qstaId") Long qstaId);

    /**
     * 删除数据
     * 
     * @param id
     */
    @NativeUpdate
    void cleanDataByLineId(@QueryParam("id") Long id);



}
