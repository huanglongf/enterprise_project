package com.jumbo.dao.odo;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.odo.OdoLine;
import com.jumbo.wms.model.odo.OdoLineCommand;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;


@Transactional
public interface OdoLineDao extends GenericEntityDao<OdoLine, Long> {

    @NativeQuery
    public List<OdoLine> findOdOLineByOdOId(@QueryParam("odoId") Long odoId, @QueryParam("type") int type, RowMapper<OdoLine> r);

    @NativeQuery
    public List<OdoLine> findOdOLineByCodeOdOId(@QueryParam("odoId") Long odoId, @QueryParam("type") int type, RowMapper<OdoLine> r);

    @NativeQuery
    public List<OdoLine> findOdoLineByOdoId2(@QueryParam("odoId") Long odoId, RowMapper<OdoLine> r);

    @NativeQuery
    public List<OdoLineCommand> findOdoLineCommandByOdOId(@QueryParam("odoId") Long odoId, @QueryParam("type") int type, RowMapper<OdoLineCommand> r);

    @NativeQuery(pagable = true)
    Pagination<OdoLineCommand> odoOutBoundDetail(int start, int pageSize, Sort[] sorts, @QueryParam("status") int status, @QueryParam("id") String id, RowMapper<OdoLineCommand> rowMapper);

    @NativeQuery
    Boolean odoOutBoundDetailList(@QueryParam("status") int status, @QueryParam("id") String id, SingleColumnRowMapper<Boolean> r);


}
