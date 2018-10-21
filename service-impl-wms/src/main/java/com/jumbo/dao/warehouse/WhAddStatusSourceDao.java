package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.WhAddStatusSource;
import com.jumbo.wms.model.warehouse.WhAddStatusSourceCommand;

@Transactional
public interface WhAddStatusSourceDao extends GenericEntityDao<WhAddStatusSource, Long> {


    @NativeQuery
    List<WhAddStatusSourceCommand> findWhStatusSourceListSql(@QueryParam(value = "type") int type, RowMapper<WhAddStatusSourceCommand> rowMapper);
   
    /**
     * 根据TYPE&STATUS找寻仓库状态
     * @return
     */
    @NativeQuery
    WhAddStatusSourceCommand getWhStatusSourceByTS(@QueryParam(value = "type") int type,@QueryParam(value = "status") int status,RowMapper<WhAddStatusSourceCommand> rowMapper);

}
