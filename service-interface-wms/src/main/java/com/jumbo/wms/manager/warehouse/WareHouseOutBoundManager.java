package com.jumbo.wms.manager.warehouse;

import java.util.Date;
import java.util.List;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.command.WhPickingBatchCommand2;
import com.jumbo.wms.model.warehouse.BiWarehouseAddStatusCommand;
import com.jumbo.wms.model.warehouse.CreatePickingListSql;
import com.jumbo.wms.model.warehouse.InventoryCommand;
import com.jumbo.wms.model.warehouse.PickingList;
import com.jumbo.wms.model.warehouse.PickingListCheckMode;
import com.jumbo.wms.model.warehouse.PickingListPackage;
import com.jumbo.wms.model.warehouse.SkuSizeConfig;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;

import loxia.dao.Pagination;
import loxia.dao.Sort;

/**
 * 新配货逻辑接口
 * 
 * @author jinlong.ke
 * 
 */
public interface WareHouseOutBoundManager extends BaseManager {
    String PICKINGLIST_EXPORT_MODE1 = "excel/tplt_pickinglist_export_mode1.xls";
    String DELIVERY_INFO_EXCEL = "excel/tplt_export_delivery_info.xls";
    String DISPATCH_LIST_INV = "excel/tplt_dispatch_list_inv.xls";
    String COACH_INVOICE_EXPORT = "excel/tplt_export_coach_invoice.xls";
    String SO_INVOICE_TAX = "excel/tplt_so_invoice_tax.xls";


    String createPickinglistBySingOrder(Long ouId, String district, String wh_district, Long userId);

    List<InventoryCommand> findStaIdByLoc(Long ouId, Long locId);

    /**
     * 分页查询待配货作业单
     * 
     * @param model
     * @param shoplist
     * @param cityList
     * @param start
     * @param pageSize
     * @param date
     * @param date2
     * @param sta
     * @param isNeedInvoice
     * @param id
     * @param sorts
     * @return
     */
    Pagination<StockTransApplicationCommand> findStaForPickingByModel(Boolean isMargeWhZoon, Integer mergePickZoon, Integer mergeWhZoon, String areaList, String whAreaList, List<SkuSizeConfig> ssList, String shoplist, String shoplist1,
            List<String> cityList, int start, int pageSize, Date date, Date date2, Date date3, Date date4, StockTransApplication sta, Long categoryId, Integer isSn, Integer transTimeType, Integer isNeedInvoice, Integer isCod, Long id, Sort[] sorts,
            List<String> priorityList, String otoAll, Boolean countAreasCp);

    Pagination<StockTransApplicationCommand> findPickFailedByWhId(String shoplist, Integer transTimeType, String lpCode, List<String> cityList, Integer isCod, Integer pickStatus, Long pickSort, Long categoryId, Integer isNeedInvoice, int start,
            int pageSize, Date date, Date date2, Date date3, Date date4, StockTransApplication sta, Long ouId, String isPreSale, Sort[] sorts);

    /**
     * 失败结果再分配
     * 
     * @param shoplist
     * @param transTimeType
     * @param lpCode
     * @param cityList
     * @param isCod
     * @param pickStatus
     * @param pickSort
     * @param categoryId
     * @param isNeedInvoice
     * @param date
     * @param date2
     * @param date3
     * @param date4
     * @param sta
     * @param ouId
     */
    void failedReusltAgainPick(String againSort, String againType, String shoplist, Integer transTimeType, String lpCode, List<String> cityList, Integer isCod, Integer pickStatus, Long pickSort, Long categoryId, Integer isNeedInvoice, Date date,
            Date date2, Date date3, Date date4, StockTransApplication sta, String isPreSale, Long ouId);


    public CreatePickingListSql findRulaNameById(String id);

    /**
     * 查询作业单明细
     * 
     * @param start
     * @param pageSize
     * @param id
     * @param sorts
     * @return
     */
    Pagination<StaLineCommand> findPickingStaLine(int start, int pageSize, Long id, Sort[] sorts);


    void deleteRultName(String id);

    /**
     * 创建配货清单
     * 
     * @param object
     * @param staIdList
     * @param cityList
     * @param isNeedInvoice
     * @param isSn
     * @param secondary
     * @param id
     * @param id2
     */
    PickingList createPickingList(String skusIdAndQty, List<Long> staIdList, Long warehouseId, Long userID, PickingListCheckMode checkModel, Boolean isSp, Integer isSn, Long categoryId, List<String> cityList, Boolean isTransAfter,
            List<SkuSizeConfig> ssList, Integer isCod, String isQs, Boolean isOtwoo, Boolean isZd, Boolean isClickBatch, Long locId, Boolean isOtoPicking);

    /**
     * 查询店铺对应的团购商品
     * 
     * @param start
     * @param pageSize
     * @param id
     * @param sorts
     * @return
     */
    Pagination<Sku> queryGroupBuyingSku(int start, int pageSize, Long id, Sort[] sorts);

    /**
     * 建批次箱
     * 
     * @param pl
     * @param toLocation
     * @return PickingListPackage
     * @throws
     */
    public PickingListPackage createPickingListPackage(Long plId, String toLocation);

    /**
     * 查询供应商名称
     * 
     * @param pickingListId
     * @return
     */
    String findexpNameByPlid(Long pickingListId);

    /**
     * 配货清单占用库存成功更新数据
     * 
     * @param pickingListId
     * @param operatorId
     */
    void occupiedPickingListSuccess(Long pickingListId, Long operatorId);

    /**
     * 配货清单占用库存失败更新数据
     * 
     * @param pickingListId
     * @param operatorId
     */
    void occupiedPickingListFailed(Long pickingListId, Long operatorId);

    /**
     * 确认配货清单，如果存在占用库存失败则pickinglist为失败状态，从新计算待处理sta去除取消数量
     * 
     * @param pickingListId
     * @param operatorId
     */
    Long confirmPickingList(Long pickingListId, Long operatorId, Long ouid);

    /**
     * 秒杀商品计数器计算
     * 
     * @param sta
     */
    void sedKillSkuCounterByPickingList(StockTransApplication sta);

    /**
     * 查询配货清单 更具所有的可创建的作业单
     * 
     * @param warehouseId
     */
    List<Long> findPickingListByAllSta(Long warehouseId);

    List<Long> findStaForPickingByModelListNew(String whZoonList, Integer mergeWhZoon, Integer isMargeWhZoon, String zoonList, String zoonId, Integer mergePickZoon, Integer isMergeInt, List<SkuSizeConfig> ssList, String shoplist, String shoplist1,
            List<String> cityList, StockTransApplication sta, Integer transTimeType, Date date, Date date2, Date orderCreateTime, Date toOrderCreateTime, Long categoryId, Integer isCod, Integer isSn, Integer isNeedInvoice, Long ouId,
            PickingListCheckMode checkMode, Boolean isSp, String isQs, int i, Integer sumCount, String otoAll, Boolean countAreasCp);

    BiWarehouseAddStatusCommand getBiWhST(Long ouId, PickingListCheckMode checkModel, int type, int status, Integer staIdList, Long plId);

    /**
     * 通过plId获得PickingList
     * 
     * @param plId
     * @return
     */
    void matchingTransNoByPlId(Long plId);

    List<StockTransApplicationCommand> findStaForPickingCount(List<SkuSizeConfig> ssList, String shoplist, String shoplist1, List<String> cityList, Date date, Date date2, Date date3, Date date4, StockTransApplication sta, Long categoryId, Integer isSn,
            Integer transTimeType, Integer isNeedInvoice, Integer isCod, Long id);

    // 根据配货规则进行配合
    List<Long> findStaForPickingByModelListNew1(String zoonId, Integer isMergeInt, List<SkuSizeConfig> ssList, String shoplist, String shoplist1, List<String> cityList, StockTransApplication sta, Integer transTimeType, Date date, Date date2,
            Date orderCreateTime, Date toOrderCreateTime, Long categoryId, Integer isCod, Integer isSn, Integer isNeedInvoice, Long ouId, PickingListCheckMode checkMode, Boolean isSp, String isQs, int i, Integer sumCount);

    /**
     * 分页查询待升单 cheng.su
     * 
     * @param shoplist
     * @param start
     * @param pageSize
     * @param date
     * @param date2
     * @param sta
     * @param transTimeType
     * @param id
     * @param sorts
     * @return
     */
    Pagination<StockTransApplicationCommand> findStaLsingle(int start, int pageSize, StockTransApplicationCommand sta, Integer transTimeType, Date fromDate, Date toDate, Long id, Sort[] sorts);

    /**
     * 自动化-作业单集货口匹配 xiaolong.fei
     * 
     * @param staIdList
     * @param ouId
     */
    Long marryStaShiipingCode(PickingListCheckMode checkModle, String plCode, List<Long> staIdList, Long ouId);

    /**
     * LEVIS销售接口卡单重置批次号 0：查无此波次号；1：重置成功 ；2：非配货状态的波次不能重置！
     * 
     * @param batchCode
     * @return
     */
    int resetMsgOutboundOrderStatus(String batchCode);


    public String addRultNameByOuId(String postshopInputName1, String postshopInputName, Boolean isMargeWhZoon, Integer mergePickZoon, Integer mergeWhZoon, String areaList, String AeraList1, String whAreaList, String whZoneList1, String ssList,
            String shoplist, String shoplist1, String cityList, String date, String date2, String date3, String date4, StockTransApplication sta, Long categoryId, Integer transTimeType, Integer isSn, Integer isNeedInvoice, Integer isCod,
            String priorityList, Long ouId, Long userId, String modeName, String ruleCode, String ruleName);

    /**
     * pda拣货日志查询
     * 
     * @param start
     * @param pageSize
     * @param sta
     * @param transTimeType
     * @param fromDate
     * @param toDate
     * @param id
     * @param sorts
     * @return
     */
    Pagination<WhPickingBatchCommand2> pickingListLogQueryDo(int start, int pageSize, WhPickingBatchCommand2 sta, Date startTime, Date startTime2, Date createTime, Date createTime2, Long userId, Long ouId, String pCode, String userName, Sort[] sorts);

}
