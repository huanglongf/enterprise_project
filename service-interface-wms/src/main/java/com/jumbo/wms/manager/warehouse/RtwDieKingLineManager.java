package com.jumbo.wms.manager.warehouse;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.command.RtwDiekingCommend;
import com.jumbo.wms.model.warehouse.RtwDieking;
import com.jumbo.wms.model.warehouse.RtwDiekingLine;
import com.jumbo.wms.model.warehouse.RtwDiekingLineLineLogCommand;

import loxia.dao.Pagination;
import loxia.dao.Sort;

public interface RtwDieKingLineManager extends BaseManager {


    Pagination<RtwDiekingCommend> getRtwDiekingList(int start, int pageSize, RtwDiekingCommend rtwDieking, String shopLikeQuery, Long ouid, Sort[] sorts);

    String saveRtwDickingTask(Long staid, String rule, Long maxQty, Long mainWhID, Long userId, String userName);

    String getRtwDiekingBatchCode();

    Pagination<RtwDiekingLine> getOutboundDetailListCollection(int start, int pageSize, Long ouid, Long staid);

    Pagination<RtwDiekingCommend> getOutboundDickingTaskDetailList(int start, int pageSize, Sort[] sorts, Long ouid, Long staid);

    Pagination<RtwDiekingLineLineLogCommand> getOutboundDickingZzxDetailList(int start, int pageSize, Long ouid, String staCode);

    RtwDieking getRtwDiekingById(Long id);

    // void mergeRtwDickingLine(String staCode, Long mainWhID);

}
