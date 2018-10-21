package com.jumbo.dao.pda;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.pda.StaOpLog;
import com.jumbo.wms.model.pda.StaOpLogCommand2;
import com.jumbo.wms.model.warehouse.StaOpLogCommand;


/**
 * 
 * @author lizaibiao
 * 
 */
@Transactional
public interface StaOpLogDao extends GenericEntityDao<StaOpLog, Long> {

    @NativeUpdate
    public void insertStaOpDetailLog(@QueryParam("staId") Long staId);

    @NativeQuery(pagable = true)
    Pagination<StaOpLogCommand> showShelvesDetail(int start, int size, @QueryParam("staId") Long staId, RowMapper<StaOpLogCommand> rowMapper, Sort[] sorts);


    @NativeQuery(pagable = true)
    Pagination<StaOpLogCommand> showShelvesSNDetail1(int start, int size, @QueryParam("staId") Long staId, RowMapper<StaOpLogCommand> beanPropertyRowMapper, Sort[] sorts);

    @NativeQuery(pagable = true)
    Pagination<StaOpLogCommand> queryStaCartonSNList(int start, int size, @QueryParam("staId") Long staId, RowMapper<StaOpLogCommand> rowMapper, Sort[] sorts);

    /**
     * 查询
     */
    @NativeQuery
    List<StaOpLogCommand2> queryStaOpLog(@QueryParam("staId") Long staId, @QueryParam("carId") Long carId, @QueryParam("ouId") Long ouId, RowMapper<StaOpLogCommand2> rowMapper);


}
