package com.jumbo.wms.manager.warehouse;

import java.util.Date;
import java.util.List;

import loxia.dao.Pagination;
import loxia.dao.Sort;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancel;
import com.jumbo.wms.model.warehouse.PackageInfo;
import com.jumbo.wms.model.warehouse.PickingList;
import com.jumbo.wms.model.warehouse.PickingListCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;

public interface WareHouseManagerCancel extends BaseManager {

    /**
     * 取消当前按箱收货
     * 
     * @param id
     */
    void cancelCartonStaOfNew(Long id);

    /**
     * 取消交接清单
     * 
     * @param hoId
     * @param userId
     */
    void cancelHandOverList(Long hoId, Long userId, Long wId);

    /**
     * 取消交货清单明细
     * 
     * @param hoListLineId
     * @param userId
     */
    Boolean cancelHandoverListLine(Long hoListLineId, Long userId, Long wId);

    /**
     * 转物流
     * 
     * @param staId
     * @param userId
     * @param lpcode
     * @param pgList
     */
    void modifyTransport(Long staId, Long userId, String lpcode, List<PackageInfo> pgList, Long wId);

    /**
     * 取消调整单
     * 
     * @param invCkId
     * @param userId
     */
    void cancelInvCheck(Long invCkId, Long userId);

    /**
     * 取消盘点批
     * 
     * @param invCkId
     */
    void cancelInventoryCheck(Long invCkId, Long userId);

    /**
     * 修改状态为差异未处理
     * 
     * @param invCkId
     */
    void cancelInventoryCheckManager(Long invCkId, User user);

    /**
     * 取消库存修改
     * 
     * @param staId
     * @param userId
     */
    void cancelInvStatusChangeSta(Long staId, Long userId);

    /**
     * 取消自定义出库
     * 
     * @param staId
     */
    void cancelOthersStaInOutbound(Long staId, Long userId) throws Exception;

    /**
     * 通过sta释放库存
     * 
     * @param staId
     */
    void releaseInventoryByStaId(Long staId, Long userId, String slipCode);

    /**
     * 取消本次采购上架入库
     * 
     * @param stvId
     */
    void cancelStv(Long stvId, Long userId);

    /**
     * 取消拣货单,只有等待配货状态才能取消
     * 
     * @param pickingListId
     */
    void cancelPickingList(Long pickingListIdd);

    /**
     * 取消装箱
     * 
     * @param staId void
     * @throws
     */
    void cancelReturnPacking(Long staId);

    /**
     * 取消配货清单
     * 
     * @param staId
     */
    void cancelSalesSta(Long staId, Long userId);

    /**
     * 通过订单取消作销售业但单
     * 
     * @param soCode
     */
    void cancelSalesStaBySoCode(String soCode);

    void vmiCancelSta(Long staId);

    List<MsgOutboundOrderCancel> ifCancel(String staCode, int status);

    /**
     * 获取pickingList是否包含取消作业单 KJL
     * 
     * @param id
     * @param statusList
     * @param singleColumnRowMapper
     * @return
     */
    public Boolean findIfExistsCancelSta(Long id, List<Integer> statusList);

    /**
     * 取消当前收货
     * 
     * @param id
     */
    void cancelSta(Long id);

    /**
     * 取消当前收货
     * 
     * @param id
     */
    void cancelStaEasy(Long id, String remark);

    /**
     * 取消库内移动
     */
    void cancelTranistInner(Long staId, Long userId);

    /**
     * 库间移动申请单取消 sta状态： 新建： 直接取消 库存占用： 释放占用库存取消 其他状态： 不能取消
     * 
     * @param uersId
     * @param staId
     */
    void cancelTransitCrossSta(Long uersId, Long staId);

    void cancelVmiReturnOutBound(Long staID, Long userid) throws Exception;

    void cancelVmiTransferOutBound(Long staID, Long userid) throws Exception;

    /**
     * 删除picking list，释放库存，删除相关stv、stvline、更新sta状态
     * 
     * @param pickingListId
     */
    String deletePickingList(Long pickingListId, Long userId, Long ouId);

    /**
     * 查询取消未处理sta
     * 
     * @param shopId
     * @param sta
     * @param wh_ou_id
     * @param sorts
     * @return
     */
    List<StockTransApplicationCommand> findSalesCancelUndoStaList(String shopId, StockTransApplication sta, Long wh_ou_id, List<Long> plists, Date createTime, Date endCreateTime, Sort[] sorts);

    /**
     * 查询取消未处理sta 分页
     * 
     * @param shopId
     * @param sta
     * @param wh_ou_id
     * @param sorts
     * @return
     */
    Pagination<StockTransApplicationCommand> findSalesCancelUndoStaListByPage(int start, int pagesize, String shopId, StockTransApplication sta, Long wh_ou_id, List<Long> plists, Date createTime, Date endCreateTime, Sort[] sorts);



    /**
     * 移除pickinglist中占用库存失败的所有sta
     * 
     * @param pickingListId
     * @return
     */
    PickingListCommand removeFialedSalesSta(Long pickingListId);

    /**
     * 计算pickinglist中计划单据数与计划执行产品数
     * 
     * @param pl
     */
    void setPickingListPlanQty(PickingList pl);

    void deletePdaPostLog(Long logid, Long userid);

    void predefinedOutCanceled(Long staId, String memo, User creator);

    /**
     * 删除外包仓关联
     */
    void deleteSkuWarehouseRef(Long brandId, String source, String sourcewh, Long channelId);

}
