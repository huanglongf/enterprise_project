package com.jumbo.wms.manager.warehouse;

import java.io.File;
import java.util.List;

import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.support.excel.ReadStatus;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.command.TransportatorCommand;
import com.jumbo.wms.model.trans.TransAreaGroupCommand;
import com.jumbo.wms.model.trans.TransAreaGroupDetial;

/**
 * 配送范围
 * 
 * @author xiaolong.fei
 * 
 */
public interface TransAreaGroupManager extends BaseManager {
    ReadStatus transAreaImport(File areaFile, String code, String name, Long areaId, Long status, Long cstmId);

    ReadStatus transAreaImport2(File areaFile, String maId, String tranId, String serviceName, String serviceType, String timeType, String statuss, User user);

    Pagination<TransAreaGroupCommand> findTransArea(int start, int pageSize, Sort[] sorts, Long cstmId);

    Pagination<TransAreaGroupCommand> updateFindTransArea(int start, int pageSize, Sort[] sorts, Long cstmId);

    void updateTransArea(Long id, String name, Long status);

    List<TransAreaGroupDetial> findTransAreaGDetiaByGId(Long groupId);

    Long saveMaTransport(TransportatorCommand ma, User user);

}
