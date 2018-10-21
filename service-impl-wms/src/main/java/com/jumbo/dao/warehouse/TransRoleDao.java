package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.trans.TransRole;
import com.jumbo.wms.model.trans.TransRoleCommand;
import com.jumbo.wms.model.trans.TransRoleDetialCommand;

/**
 * 物流规则
 * 
 * @author xiaolong.fei
 * 
 */
@Transactional
public interface TransRoleDao extends GenericEntityDao<TransRole, Long> {
    @NamedQuery
    TransRole findTransRoleByCode(@QueryParam(value = "code") String code);

    @NativeQuery(pagable = true)
    Pagination<TransRoleCommand> findTransRole(int start, int pageSize, Sort[] sorts, BeanPropertyRowMapper<TransRoleCommand> e, @QueryParam(value = "chanId") Long chanId);

    /**
     * 根据店铺获取规则
     * 
     * @param owner
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<TransRoleCommand> findTransRoleByOwner(@QueryParam(value = "owner") String owner, RowMapper<TransRoleCommand> rowMapper);

    @NativeQuery(pagable = true)
    Pagination<TransRoleDetialCommand> findSkuRef(int start, int pageSize, @QueryParam(value = "roleDtalId") Long roleDtalId, BeanPropertyRowMapper<TransRoleDetialCommand> e, Sort[] sorts);
	
	@NativeQuery(pagable = true)
	Pagination<TransRoleDetialCommand> findSkuCateRef(int start,int pageSize,@QueryParam(value = "roleDtalId") Long roleDtalId,BeanPropertyRowMapper<TransRoleDetialCommand> e,Sort [] sorts);
	
	@NativeQuery(pagable = true)
	Pagination<TransRoleDetialCommand> findSkuTagRef(int start,int pageSize,@QueryParam(value = "roleDtalId") Long roleDtalId,BeanPropertyRowMapper<TransRoleDetialCommand> e,Sort [] sorts);
	
	@NamedQuery
    TransRole getTransRoleIdByCode(@QueryParam(value = "code") String code);
	
	@NativeUpdate
	int updateTransRoleById(@QueryParam(value = "roleId") Long roleId, @QueryParam(value = "roleIsAvailable") Integer roleIsAvailable, @QueryParam(value = "priority") Integer priority);
}
