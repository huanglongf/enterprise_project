package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.trans.TransportService;
import com.jumbo.wms.model.trans.TransportServiceCommand;

/**
 * 物流服务
 * 
 * @author xiaolong.fei
 * 
 */
@Transactional
public interface TransportServiceDao extends GenericEntityDao<TransportService, Long> {

    /**
     * 查询物流服务
     * 
     * @param start
     * @param pagesize
     * @param r
     * @param sorts
     * @return
     */
    @NativeQuery
    List<TransportServiceCommand> getTransportService(RowMapper<TransportServiceCommand> r, Sort[] sorts);

    @NativeQuery
    List<TransportServiceCommand> getTransportServiceById(@QueryParam("transId") String transId, RowMapper<TransportServiceCommand> r, Sort[] sorts);

    Pagination<TransportServiceCommand> findTransport(RowMapper<TransportServiceCommand> r, Sort[] sorts);

    /**
     * 通过物流商 类型 时效类型查询物流服务类型
     * 
     * @return
     */
    @NativeQuery
    Long getTransportServiceByTTE(@QueryParam(value = "expcode") String expcode, @QueryParam(value = "type") Integer type, @QueryParam(value = "timetype") Integer timetype, SingleColumnRowMapper<Long> r);

    /**
     * 通过物流商code来查询时效类型
     */
    @NativeQuery
    List<TransportServiceCommand> getTransportServiceByCode(@QueryParam(value = "expcode") String expcode, RowMapper<TransportServiceCommand> r);

}
