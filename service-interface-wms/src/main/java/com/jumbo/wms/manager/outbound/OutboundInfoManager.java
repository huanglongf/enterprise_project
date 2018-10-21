package com.jumbo.wms.manager.outbound;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.web.commond.OrderCheckCommand;
import com.jumbo.wms.web.commond.PickingListCommand;
import com.jumbo.wms.web.commond.TransWeightOrderCommand;

import loxia.dao.Pagination;
import loxia.dao.Sort;

public interface OutboundInfoManager extends BaseManager {

    /**
     * 通过单据查询待核对清单
     * 
     * @param code 可能为配货清单号、小批次号、作业单号
     * @param typeFlag
     * @return
     */
    public PickingListCommand getOutboundCheckInfo(String code, Integer typeFlag);

    /**
     * 获取称重数据
     * 
     * @param transNo
     * @param isSpPacking
     * @return
     */
    public TransWeightOrderCommand getOrderInfo(String transNo, Boolean isSpPacking);

    /**
     * 校验扫描的单号是否正确
     * 
     * @param orderCode
     * @param ouId
     * @return
     */
    public Integer checkOrderCode(String orderCode, Long ouId);

    /**
     * 根据前台传过来的sn号校验是否可用
     * 
     * @param uniqueId
     * @param sn
     */
    public void checkSnStatus(Long uniqueId, String sn);

    /**
     * 根据封装的数据来进行单据核对
     * 
     * @param checkOrder
     * @param userId
     * @param ouId
     */
    public void doCheckBySta(OrderCheckCommand checkOrder, Long userId, Long ouId,String isSkipWeight,String staffCode2);

    /**
     * 根据当前操作账户查询未交接单据数
     * 
     * @param ouId 当前组织ID
     * @param userId 当前用户ID
     * @return
     */
    public List<StaDeliveryInfo> getNeedHandOverOrderSummary(Long id, Long id2);

    /**
     * 根据信息记录当前耗材的作业单信息
     * 
     * @param transNo
     * @param weight
     * @param staffCode
     */
    public void recordWeightForTrackingNo(String transNo, BigDecimal weight, String staffCode);

    /**
     * 根据当前用户查询用户操作权限
     * 
     * @param id
     */
    public User findUserPrivilegeInfo(Long id);

    /**
     * 行取消
     * 
     * @param lines
     * @param id
     */
    public void cancelStaLine(List<String> lines, Long id);

    /**
     * @param idList
     * @param id
     * @param ouId
     */
    public void partlySalesOutbound(List<Long> idList, Long id, Long ouId);

    /**
     * @param idList
     * @param id
     * @param id2
     */
    public void reportMissingWhenCheck(List<Long> idList, Long id, Long id2);

    /**
     * 查询AD配货失败的单据
     * 
     * @param start
     * @param pageSize
     * @param date
     * @param date2
     * @param sta
     * @param sorts
     * @return
     */
    public Pagination<StockTransApplicationCommand> getAllOccupiedFailureOrder(int start, int pageSize, Long ouId, Date date, Date date2, StockTransApplicationCommand sta, Sort[] sorts);

    /**
     * @param id
     */
    public void occupiedByIdForAD(Long id);

    /**
     * @param id
     */
    public void reOccupiedForAd(Long id);

    /**
     * @param id
     */
    public void cancelAdOrderById(Long id);

    /**
     * @param id
     */
    public void partySendAdOrderById(Long id);

    /**
     * @param id
     * @param sorts
     * @return
     */
    public List<StaLineCommand> showOccDetail(Long id, Sort[] sorts);

    /**
     * 获取商品原产地
     * 
     * @return
     */
    public Map<String, Set<String>> findSkuOriginByPlId(Long plId);

    /**
     * 保税仓新增产地记录
     */
    public void addStaSkuOrigin(String skuOrigin, Long staId);
}
