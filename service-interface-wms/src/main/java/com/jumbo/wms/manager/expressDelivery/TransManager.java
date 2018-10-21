package com.jumbo.wms.manager.expressDelivery;

import java.io.File;
import java.util.List;

import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.support.excel.ReadStatus;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.warehouse.WhTransAreaNoCommand;
import com.jumbo.wms.model.warehouse.WhTransProvideNoCommand;

public interface TransManager extends BaseManager {
    Pagination<WhTransProvideNoCommand> findProvide(int start, int pageSize, WhTransProvideNoCommand provide, Long id, Sort[] sorts);

    Pagination<WhTransAreaNoCommand> findProvince(int start, int pageSize, WhTransAreaNoCommand reaeNo, Sort[] sorts);

    ReadStatus transProvideImport(File coachFile, String lpcode, String jcustid, Boolean checkboxSf);

    ReadStatus transProvinceImport(File coachFile, String lpcode, Long updateMode, Long userId);

    void deleteProvince(List<Long> ids);

    /**
     * 用户绑定仓库导入
     * 
     * @param file
     * @return
     */
    ReadStatus userWarehouseRefImport(File file);


}
