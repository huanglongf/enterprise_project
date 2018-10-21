package com.jumbo.wms.manager.warehouse;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.command.StaDeliverCommand;
import com.jumbo.wms.model.warehouse.PickingList;
import com.jumbo.wms.model.warehouse.PickingListCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.WhPlDiffLine;
import com.jumbo.wms.model.warehouse.WhPlDiffLineCommand;

import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.support.excel.ReadStatus;

/**
 * 配货清单打印业务逻辑
 * 
 * @author jinlong.ke
 * 
 */
public interface PickingListPrintManager extends BaseManager {

    /**
     * 查询所有的配货清单
     * 
     * @param start
     * @param pageSize
     * @param pickingList
     * @param isMacaoOrder
     * @param isPrintMaCaoHGD
     * @param id
     * @param sorts
     * @return
     */
    Pagination<PickingList> getAllPickingList(int start, int pageSize, PickingList pickingList, Boolean isMacaoOrder, Boolean isPrintMaCaoHGD, Long id, Sort[] sorts);

    /**
     * 查询所有的配货清单
     * 
     * @param start
     * @param pageSize
     * @param pickingList
     * @param id
     * @param sorts
     * @return
     */
    Pagination<PickingList> getAllPickingQuery(int start, int pageSize, PickingList pickingList, Long id, Sort[] sorts);


    public void updatPickListById(Long plid);

    /**
     * 查询所有的配货清单(用于取消操作查询)
     * 
     * @param start
     * @param pageSize
     * @param id
     * @param sorts
     * @return
     */
    Pagination<PickingList> findAllPickingList(int start, int pageSize, PickingList pickingList, Long id, Sort[] sorts);

    /**
     * 查询所有的配货清单
     * 
     * @param start
     * @param pageSize
     * @param pickingList
     * @param id
     * @param sorts
     * @return
     */
    Pagination<PickingList> getAllPickingListStatus(int start, int pageSize, PickingList pickingList, Long id, Sort[] sorts, List<String> list);

    /**
     * 查询所有的配货清单
     * 
     * @param start
     * @param pageSize
     * @param pickingList
     * @param id
     * @param sorts
     * @return
     */
    Pagination<PickingList> getAllPickingListStatusBulk(int start, int pageSize, PickingList pickingList, Long id, Sort[] sorts, List<String> list);

    /**
     * 查询所有的配货清单(带仓库)
     * 
     * @param start
     * @param pageSize
     * @param pickingList
     * @param id
     * @param lists
     * @param sorts
     * @return
     */
    Pagination<PickingListCommand> getAllPickingListsigleopc(int start, int pageSize, PickingList pickingList, Long id, List<Long> lists, Sort[] sorts);

    /**
     * 根据配货清单编码查询配货清单信息
     * 
     * @param code
     * @return
     */
    PickingListCommand getPickingListByCode(String code, Integer checkMode, int whStatus, Long ouId);

    /**
     * 根据配货清单Id查询所有的作业单 需求分页
     * 
     * @param start
     * @param pageSize
     * @param pickingListId
     * @param id
     * @param sorts
     * @return
     */
    Pagination<StockTransApplicationCommand> getAllStaByPickingListId(int start, int pageSize, Long pickingListId, Long id, Sort[] sorts);


    /**
     * 根据相关单据号，更新对应的物流单号
     * 
     * @param std
     */
    void updateTransNoBySlipCode(List<StaDeliverCommand> std);

    /**
     * 导入物流面单维护信息
     * 
     * @param file
     * @param pickingListId
     * @return
     */
    ReadStatus importStaDeliveryInfo(File file, Long pickingListId);


    /**
     * 修改配货清单状态
     * 
     * @param plid
     * @param ouId
     */
    void updatePickListWhStatus(Long plid, Long ouId, Long userID);

    /**
     * 拣货有差异，插入t_wh_pl_diff_line表
     * 
     * @param plid
     * @param pickingListValues
     */
    void addWhPlDiffLine(Long plid, List<WhPlDiffLine> whPlDiffLineList);


    List<WhPlDiffLineCommand> findWhPlDillLineByPid(Long plid, int status);


    List<WhPlDiffLineCommand> findWhPlDillLineByPidS(Long plid);


    List<WhPlDiffLineCommand> findWhPlDillLineByPidSD(Long plid);


    void deleteWhPlDillLineByPid(Long plid);

    /**
     * 差异明细确认完成
     * 
     * @param plid
     * @param ouId
     * @param userID
     * @param whPlDiffLineList
     */
    void donePickListWhDieking(Long plid, Long ouId, Long userID, List<WhPlDiffLine> whPlDiffLineList);

    List<PickingListCommand> findPickListDetail(Long plId);

    /**
     * 配货批次分配 分页查询
     * 
     * @param start
     * @param pageSize
     * @return
     */
    Pagination<PickingListCommand> findBatchAllocation(int start, int pageSize, Date createTime, Date endTime, Date exeTime, Date endExeTime, PickingListCommand pickingListCommand, Sort[] sorts, Long ouId);

    /**
     * 根据产品id查找仓库id
     */
    public Long getWhidBypickid(String pcode);

    /**
     * 作业单id，查询其作业单实体
     * 
     * @param staId
     * @param beanPropertyRowMapper
     * @return
     */
    public Long findPickingListByPickId(Long pickId);

    public PickingList getPickingListById(Long id);

    /**
     * 根据配货批次号查询秒杀配货批基本信息
     * 
     * @param code
     * @param beanPropertyRowMapper
     * @return
     */
    public PickingListCommand findCheckoutPickingByCode(String code, Long whId, List<Long> idList);

    /**
     * 根据配货清单查询物流交接单
     * 
     * @param id
     * @param singleColumnRowMapper
     * @return
     */
    public Long getHandOverIdByPickingListId(Long id);

    /**
     * 作业单id，是否特定
     * 
     * @param staId
     * @param beanPropertyRowMapper
     * @return
     */
    public Long findPickingListByPickIdS(Long pickId);

    public BigDecimal findOutputCount(Long plId);

    /**
     * 修改配货清单状态-团购
     * 
     * @param plid
     * @param ouId
     */
    void updatePickListWhStatusBulk(List<Long> plids, Long ouId, Long userID);
}
