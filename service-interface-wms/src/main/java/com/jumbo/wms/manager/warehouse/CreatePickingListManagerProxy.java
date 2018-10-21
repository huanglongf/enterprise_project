package com.jumbo.wms.manager.warehouse;

import java.util.Date;
import java.util.List;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.warehouse.PickingListCheckMode;
import com.jumbo.wms.model.warehouse.SkuSizeConfig;
import com.jumbo.wms.model.warehouse.StockTransApplication;

/**
 * 
 * @author jinlong.ke
 * 
 */
public interface CreatePickingListManagerProxy extends BaseManager {

    Long createPickingListBySta(String skusIdAndQty, List<Long> staIdList, Long ouId, Long id, PickingListCheckMode checkMode, Boolean isSp, Integer isSn, Long categoryId, List<String> cityList, Boolean isTransAfter, List<SkuSizeConfig> ssList,
            Integer isCod, String isSq, Boolean isOtwoo, Boolean isSd, Boolean isClickBatch, Long locId, Boolean isOtoPicking);

    List<Long> autoCreatePickingListBySta(Integer mergePickZoon, Integer mergeWhZoon, Boolean isMargeWhZoon, String whAreaList, String areaList, String skusIdAndQty, List<SkuSizeConfig> ssList, String shoplist, String shoplist1, List<String> cityList,
            StockTransApplication sta, Integer transTimeType, Integer minAutoSize, Integer limit, Integer plCount, Date date, Date date2, Date orderCreateTime, Date toOrderCreateTime, Long categoryId, Integer isCod, Integer isSn, Integer isNeedInvoice,
            Long ouId, Long id, PickingListCheckMode checkMode, Boolean isSp, String isQs, Integer sumCount, String otoAll, Boolean isOtoPicking, Boolean countAreasCp);

    void autoCreatePickingListByStaByNoPT(String areaList, String skusIdAndQty, List<SkuSizeConfig> ssList, String shoplist, String shoplist1, List<String> cityList, StockTransApplication sta, Integer transTimeType, Integer minAutoSize, Integer limit,
            Integer plCount, Date date, Date date2, Date orderCreateTime, Date toOrderCreateTime, Long categoryId, Integer isCod, Integer isSn, Integer isNeedInvoice, Long ouId, Long id, PickingListCheckMode checkMode, Boolean isSp, String isQs,
            Integer sumCount);

    void savePickNode(String jubNumber, Long pickType, String batchCode, Long ouId);

    /**
     * 手动升单 cheng.su
     * 
     * @param staIdList
     * @param ouId
     * @param id
     */
    void generStaLsingle(List<Long> staIdList, Long ouId, Long id);

    /**
     * 不升单 cheng.su
     * 
     * @param staIdList
     * @param ouId
     * @param id
     */
    void outGenerStaLsingle(List<Long> staIdList, Long ouId, Long id);
}
