package com.jumbo.wms.manager.warehouse;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.automaticEquipment.WhContainer;
import com.jumbo.wms.model.automaticEquipment.WhContainerCommand;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.command.automaticEquipment.WhPickingBatchCommand;
import com.jumbo.wms.model.mongodb.MongDbNoThreeDimensional;
import com.jumbo.wms.model.warehouse.PickingList;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.TransactionDirection;

import loxia.dao.Pagination;
import loxia.dao.Sort;

/**
 * 自动化出库周转箱相关控制逻辑
 * 
 * @author jinlong.ke
 * @date 2016年2月25日下午2:58:01
 */
public interface AutoOutboundTurnboxManager extends BaseManager {
    /**
     * 根据扫描的前置单据号和周装箱条码绑定周转箱和小批次
     * 
     * @param slipCode
     * @param barCode
     * @param ouId
     * @param userId
     */
    String bindBatchAndTurnbox(String slipCode, String code, Long pId, Long ouId, Long userId);

    /**
     * 根据输入的名称自动生成周转箱
     * 
     * @param code
     * @param id
     */
    void createNewTurnboxByName(String code, Long id);

    /**
     * 根据条件分页查询
     * 
     * @param start
     * @param pageSize
     * @param code
     * @param sorts
     * @return
     */
    Pagination<WhContainerCommand> getAllTurnoverBox(int start, int pageSize, String code, String plCode, Integer status, Long ouId, Sort[] sorts);

    /**
     * 根据ID,修改周装周转箱状态为可用
     * 
     * @param tId
     * @param userId
     */
    void resetTurnoverBoxStatus(Long tId, Long userId);

    /**
     * 根据ID,修改周装周转箱状态为可用(PDA上架)
     * 
     * @param tId
     * @param userId
     */
    void resetTurnoverBoxStatusPdaShelves(Long tId, Long userId);

    /**
     * 强制重置周转箱状态
     * 
     * @param pId
     */
    public void resetBoxStatus(Long pId);

    /**
     * 记录周转箱变更日志
     * 
     * @param code 周转箱编码
     * @param status 周转箱状态
     * @param orderCode 相关单号
     * @param type 周转箱出入类型
     * @param pickListId 配货清单ID
     * @param pbId 二级批次ID
     * @param staId 作业单ID
     * @param userId 操作人
     */
    void createWhContainerLog(String code, DefaultStatus status, String orderCode, TransactionDirection type, Long pickListId, Long pbId, Long staId, Long userId);

    /**
     * 多件批次绑定
     * 
     * @param pickingListCode
     * @param barCode
     * @param code
     * @param ouId
     * @return
     */
    public WhContainer bindManyBatchAndTurnbox(String pickingListCode, String zoonCode, String barCode, Long ouId);

    /**
     * 根据参数获取配货配与仓储区域的关联关系
     * 
     * @param start
     * @param pageSize
     * @param m
     * @return
     */
    Pagination<WhPickingBatchCommand> findPickingListZoneByParams(int start, int pageSize, Map<String, Object> m);

    /**
     * 配货批下的周转箱设为完成 WEB版
     * 
     * @param pickingListCode
     * @param barCode
     */
    public List<Long> pickingListAndZoneOverForWeb(Long pickingListId, String zoonCode, List<String> boxCode, Long ouId, Long userId);

    /**
     * 配货批下的仓储区域设为完成
     * 
     * @param userId
     * 
     * @param pickingListCode
     * @param barCode
     * @return
     */
    public List<Long> pickingListAndZoneOver(Long pickingListId, String zoonCode, List<String> boxCode, Long ouId, Long userId);

    /**
     * 非自动化仓配货批下的周转箱设为完成
     * 
     * @param pickingListCode
     * @param barCode
     */
    public List<Long> notAutoPickingListAndZoneOver(Long pickingListId, String zoonCode, List<String> boxCode, Long ouId, Long userId);

    /**
     * 查找已经维护好的复核工作台编码
     * 
     * @param pl
     * @return
     */
    public String findCheckingCode(PickingList pl);

    /**
     * 获取自动化仓人工集货推荐信息
     * 
     * @param boxCode
     * @param pl
     * @param ouId
     * @return
     */
    public List<Long> generateToWcsMsg(List<String> boxCode, PickingList pl, Long ouId);

    /**
     * 在页面操作区域完成时，将明细设置为拣货完成
     * 
     * @param pickingListId
     * @param zoonCode
     */
    public void pickingLineOver(Long pickingListId, String zoonCode);

    public String pickingBatchBarCode(Long pickingListId, String zoonCode);

    /**
     * 根据ID获取周转箱
     * 
     * @param tId
     * @return
     */
    WhContainer getWhContainerById(Long tId);

    /**
     * 根据波次号查询当前波次是否可以操作周转箱绑定
     * 
     * @param pCode
     * @param ouId
     * @return
     */
    PickingList checkBindPickingList(String pCode, Long ouId);

    /**
     * 按批次重置周转箱状态
     * 
     * @param pickId
     * @param userId
     */
    public void resetTurnoverBoxStatusByPickingList(Long pickId, Long userId);

    /**
     * 配货批次下订单有任意一条匹配到可用的优先级最高的播种墙推荐规则，则配货批次下所有周转箱应用该播种墙推荐规则制定的播种墙
     * 
     * @param staList
     * @param ouId
     * @return
     */
    String checkBozRole(List<StockTransApplication> staList, Long ouId, Long pkId);

    /**
     * 获取已经占用周转箱的组织
     * 
     * @return
     */
    public List<OperationUnit> findOccupyOu();

    /**
     * @param plCode
     */
    void releaseTurnBoxByPc(String plCode, Long userId);

    /**
     * 重置周转箱状态
     * 
     * @param boxCode
     * @param userId
     */
    public String resetTurnoverBoxStatusByBoxCode(String boxCode, Long userId);

    /**
     * 缺失体积或重量的商品是否已经维护好了
     */
    public void verifyThreeDimensionalOver();

    /**
     * 查询商品存在三维信息缺失的单子
     * 
     * @param start
     * @param pageSize
     * @param params
     * @return
     * @throws Exception
     */
    public Pagination<StockTransApplicationCommand> findThreeDimensional(int start, int pageSize, StockTransApplicationCommand sta) throws Exception;

    /**
     * 根据作业单查找缺失三维的商品
     * 
     * @param start
     * @param pageSize
     * @param staId
     * @return
     */
    public Pagination<SkuCommand> findThreeDimensionalSkuInfo(int start, int pageSize, Long staId);

    public List<SkuCommand> findThreeDimensionalSkuInfoList(StockTransApplicationCommand sta) throws Exception;

    public Pagination<MongDbNoThreeDimensional> findNoThreeDimensional(int start, int pageSize, String pinkingCode, Date time1, Date time2, String store, Long ouId) throws Exception;

    public Pagination<SkuCommand> findNoThreeDimensionalSkuInfo(int start, int pageSize, Long plId);

    public List<SkuCommand> findNoThreeDimensionalSkuInfoList(Long ouId) throws Exception;

}
