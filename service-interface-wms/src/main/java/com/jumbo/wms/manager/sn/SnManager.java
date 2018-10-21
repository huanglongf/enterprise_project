package com.jumbo.wms.manager.sn;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import loxia.dao.Pagination;
import loxia.dao.Sort;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.baseinfo.SkuSnCardStatus;
import com.jumbo.wms.model.jasperReport.ImportEntryListObj;
import com.jumbo.wms.model.jasperReport.SnCardErrorObj;
import com.jumbo.wms.model.warehouse.PackageInfoCommand;
import com.jumbo.wms.model.warehouse.PickingListCommand;
import com.jumbo.wms.model.warehouse.SkuSn;
import com.jumbo.wms.model.warehouse.SkuSnCommand;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;


public interface SnManager extends BaseManager {

    void checkSnStatus(Long ouid, String sn);

    StockTransApplication getStaIdBySnStv(Long ouid, String sn);

    void cardBindingStv(Long ouid, String sn, Long staid);

    SkuSnCommand checkStvBinding(Long staid);

    String activateCardStatus(Long staid, Long uid, String sn);

    Long getSnCountForStvSku(Long staid, String barCode, Integer cardStatus);

    void createSn(String sn, Long skuId, Long ouId, Long staId, SkuSnCardStatus cStatus);

    void saveSnOperateLog(SkuSn skuSn, Long operatorId, String memo);

    void saveSnOperateLog(String sn, Long skuId, SkuSnCardStatus cStatus, Long operatorId, String memo);

    Map<String, String> formatCardToSn(List<String> snList, Long ouid, String sn, Long uid);

    Pagination<PickingListCommand> searchSnCardCheckList(int start, int pageSize, PickingListCommand cmd, Sort[] sorts, Long ouid);

    List<StockTransApplicationCommand> findSnCardErrorList(String plCode, String staCode);

    StockTransApplicationCommand findSnCardErrorByStaCode(String code, Long ouid);

    PickingListCommand getSnCardErrorPl(Long plid);

    void shiftCardBindingStv(Long snid, Long staid, String sn);

    List<StaLineCommand> findSnCardCheckSta(Long staid);

    List<PackageInfoCommand> findPackAgeInfoForSta(Long id);

    BigDecimal getBiChannelLimitAmount(Long staid);

    List<StockTransApplicationCommand> printSnCardStaList(String staCode, String plCode);

    SnCardErrorObj printSnCardError(StockTransApplicationCommand sta, Long cmpOuid);

    Map<Long, ImportEntryListObj> printImportEntryList(Long staid);

    Map<Long, ImportEntryListObj> printImportMacaoEntryList(Long staid);

    Boolean findIsWrapStuff(Long id, String wrapStuff, Long ouId);

    public StockTransApplicationCommand findPackageCount(Long id);

    void createChildSn(String sn, String seedSn, SkuSnCardStatus cStatus, Long skuId);

}
