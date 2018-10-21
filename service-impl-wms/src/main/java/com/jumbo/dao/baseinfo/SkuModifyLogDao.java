package com.jumbo.dao.baseinfo;

import java.util.Map;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.baseinfo.SkuModifyLog;
import com.jumbo.wms.model.command.SkuModifyLogCommand;

@Transactional
public interface SkuModifyLogDao extends GenericEntityDao<SkuModifyLog, Long> {
    
    /**
     * 查询变更日志
     * @param start
     * @param pageSize
     * @param sorts
     * @return
     */
    @NamedQuery(pagable=true)
    Pagination<SkuModifyLog> getSkuModifyLogAll(int start, int pageSize,  Sort[] sorts);
    
    /**
     * 按条件查询变更日志
     * @param start
     * @param pageSize
     * @param m
     * @param rowMapper
     * @param sorts
     * @return
     */
    @NativeQuery(pagable=true)
    Pagination<SkuModifyLogCommand> findSkuModifyLogAll(int start, int pageSize, @QueryParam Map<String, Object> m, RowMapper<SkuModifyLogCommand> rowMapper, Sort[] sorts);
}
