package com.jumbo.wms.manager.warehouse;

import java.io.File;
import java.util.List;

import loxia.support.excel.ReadStatus;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.warehouse.SfCloudWarehouseConfigCommand;
import com.jumbo.wms.model.warehouse.SfNextMorningConfig;

/**
 * 
 * @author jinlong.ke
 * @date 2016年4月7日下午2:54:32
 */
public interface SfNextMorningConfigManager extends BaseManager {

    List<SfNextMorningConfig> findConfigByOuId(Long id);

    List<SfCloudWarehouseConfigCommand> findSfConfigByOuId(Long ouId, Long cId);


    ReadStatus importSfNextMorningConfig(File fileSFC, Long id, Long id2);

    ReadStatus importSfConfig(File fileSFC, Long id, Long id2, Long cId);


    OperationUnit getById(Long id);

}
