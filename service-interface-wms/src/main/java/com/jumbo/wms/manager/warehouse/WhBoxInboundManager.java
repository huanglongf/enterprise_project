package com.jumbo.wms.manager.warehouse;

import java.io.File;
import java.util.List;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.warehouse.StaLineCommand;

import loxia.dao.Sort;
import loxia.support.excel.ReadStatus;

/**
 * 按箱收货
 * 
 * @author PUCK SHEN
 * 
 */
public interface WhBoxInboundManager extends BaseManager {


    // ReadStatus whBoxInboundImport(File file, Long userId, Long ouId, String innerShopName, Long
    // staId);

    /**
     * 根据作业单id查询明细，定制化作业单
     * 
     * @param id
     * @param sorts
     * @return
     */
    List<StaLineCommand> findBoxReceiveStaLine(Long id, Sort[] sorts);

    ReadStatus whBoxReceiveImport(File file, Long id, Long staId);

    void createSonStaByGroupData(List<List<StaLineCommand>> list, Long userId, Long staId);

}
