package com.jumbo.wms.manager.warehouse;


import loxia.dao.Pagination;
import loxia.dao.Sort;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.command.OperationUnitCommand;
import com.jumbo.wms.model.trans.TransRoleCommand;
import com.jumbo.wms.model.trans.TransRoleDetial;
import com.jumbo.wms.model.trans.TransRoleDetialCommand;

/**
 * 物流规则
 * 
 * @author xiaolong.fei
 *
 */
public interface TransRoleManager extends BaseManager {
    void saveTransRole(TransRoleCommand transRole, TransRoleDetial roleDetail, String skuList, String skuCateList, String sktTagList, String whList, Long isAdd, Long roleId, Long roleDtalId);

    void saveDefaultTransRole(TransRoleCommand transRole, Long isAdd);

    void saveKeyWord(String includeKey, Long channelId);

    String queryKeyWord();

    Pagination<TransRoleCommand> findTransRole(int start, int pageSize, Sort[] sorts, Long chanId);

    Pagination<TransRoleDetialCommand> findSkuRef(int start, int pageSize, Long roleDtalId, Sort[] sorts);

    Pagination<TransRoleDetialCommand> findSkuCateRef(int start, int pageSize, Long roleDtalId, Sort[] sorts);

    Pagination<TransRoleDetialCommand> findSkuTagRef(int start, int pageSize, Long roleDtalId, Sort[] sorts);

    Pagination<OperationUnitCommand> findTransRoleWH(int start, int pageSize, Long channelId, Long roleDtalId, Sort[] sorts);

    public String queryKeyWordForChannlId(Long channlId);

}
