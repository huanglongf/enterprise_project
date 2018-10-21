package com.jumbo.wms.manager.baseinfo;


import java.util.Map;

import loxia.dao.Pagination;
import loxia.dao.Sort;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.command.SkuModifyLogCommand;

/**
 * 商品信息变更日志管理
 * @author hui.li
 *
 */
public interface SkuModifyLogManager extends BaseManager {

    /**
     * 按条件查询变更日志
     * @param start
     * @param pageSize
     * @param m
     * @param sorts
     * @return
     */
    Pagination<SkuModifyLogCommand> findSkuModifyLogAll(int start, int pageSize,Map<String, Object> m, Sort[] sorts);
}
