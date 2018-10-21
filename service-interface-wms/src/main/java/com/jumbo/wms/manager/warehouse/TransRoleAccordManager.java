package com.jumbo.wms.manager.warehouse;

import java.io.File;
import java.util.Date;
import java.util.List;

import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.support.excel.ReadStatus;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.trans.TransRoleAccordCommand;

/**
 * 配送范围
 * 
 * @author xiaolong.fei
 * 
 */
public interface TransRoleAccordManager extends BaseManager {
	
	Pagination<TransRoleAccordCommand> findTransRoleAccord(int start, int pageSize, TransRoleAccordCommand tra, Long id, Sort[] sorts);
	
	ReadStatus transRoleAccordImport(File file,  Long userId);
	
	void updateTransRoleAccord(Long id, Date changeTime, Long roleIsAvailable, Long priority, Date lastOptionTime, Long userId);
	
	List<TransRoleAccordCommand> findAvailableTransRoleAccord(String changeTime);
	
	void updateTransRole(Long id, Long roleId, Integer roleIsAvailable, Integer priority);
}
