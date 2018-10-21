package com.jumbo.dao.warehouse;


import java.util.Date;
import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;
import com.jumbo.wms.model.trans.TransRoleAccord;
import com.jumbo.wms.model.trans.TransRoleAccordCommand;


/**
 * 渠道快递规则状态变更
 * @author NCGZ-DZ-SJ
 */
@Transactional
public interface TransRoleAccordDao extends GenericEntityDao<TransRoleAccord, Long> {

	@NativeQuery(pagable = true)
	Pagination<TransRoleAccordCommand> findTransRoleAccord(int start, int pageSize, @QueryParam(value = "channelCode") String channelCode, @QueryParam(value = "channelName") String channelName, @QueryParam(value = "intStatus") Integer intStatus,
			@QueryParam(value = "roleCode") String roleCode, @QueryParam(value = "roleName") String roleName, RowMapper<TransRoleAccordCommand> r, Sort[] sorts);

	@NativeUpdate
    void updateTransRoleAccord(@QueryParam("id") Long id, @QueryParam("changeTime") Date changeTime, @QueryParam("roleIsAvailable") Long roleIsAvailable, @QueryParam("priority") Long priority, @QueryParam("lastOptionTime") Date lastOptionTime,
            @QueryParam("userId") Long userId);
	
	@NativeQuery
	List<TransRoleAccordCommand> findAvailableTransRoleAccord(@QueryParam(value = "changeTime") Date changeTime,BeanPropertyRowMapper<TransRoleAccordCommand> beanPropertyRowMapper);
}
