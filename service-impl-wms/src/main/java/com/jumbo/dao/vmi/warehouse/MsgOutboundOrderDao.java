package com.jumbo.dao.vmi.warehouse;

import java.util.Date;
import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;

@Transactional
public interface MsgOutboundOrderDao extends GenericEntityDao<MsgOutboundOrder, Long> {
    @NativeQuery
    List<Long> findMsgOutboundOrderId(@QueryParam(value = "source") String source, RowMapper<Long> ids);

    @NativeQuery
    List<MsgOutboundOrder> findSOOutboundOrder(@QueryParam(value = "source") String source, @QueryParam(value = "islocked") boolean islocked, @QueryParam(value = "sourceWH") String sourceWH, @QueryParam(value = "struts") int struts,
            @QueryParam(value = "num") Integer num, RowMapper<MsgOutboundOrder> orders);

    @NativeQuery
    List<MsgOutboundOrder> findSOByOwnerSource(@QueryParam(value = "whSource") String whSource, @QueryParam(value = "ownerSource") String ownerSource, @QueryParam(value = "struts") int struts, @QueryParam(value = "isAll") boolean isAll,
            RowMapper<MsgOutboundOrder> orders);

    @NativeQuery
    List<MsgOutboundOrder> findOutboundOrderBySource(@QueryParam(value = "source") String source, RowMapper<MsgOutboundOrder> orders);

    @NativeUpdate
    void updateStatusById(@QueryParam(value = "sta") int sta, @QueryParam(value = "msgId") Long msgId);


    @NativeUpdate
    void updateStatusBySourceANDStatus(@QueryParam(value = "fromStatus") int fromStatus, @QueryParam(value = "toStatus") int toStatus, @QueryParam(value = "vmiSource") String vmiSource);



    @NativeQuery
    BiChannel findShopByOwner(@QueryParam(value = "owner") String owner, RowMapper<BiChannel> shop);

    @NativeQuery
    Long findBatchNo(RowMapper<Long> batchNo);

    @NativeQuery
    Long findBatchNoPre(RowMapper<Long> batchNo);


    @NativeUpdate
    void updateBatchNoByID(@QueryParam(value = "batchNo") Long batchNo, @QueryParam(value = "sta") int sta, @QueryParam(value = "msgId") Long msgId);

    @NativeQuery
    List<MsgOutboundOrder> findMsgOutboundOrder(@QueryParam(value = "source") String source, @QueryParam(value = "sourceWH") String sourceWH, @QueryParam(value = "status") int status, @QueryParam(value = "isAll") boolean isAll,
            RowMapper<MsgOutboundOrder> orders);


    @NativeUpdate
    void updateStatusByStaCode(@QueryParam(value = "sta") int sta, @QueryParam(value = "staCode") String staCode);


    @NativeUpdate
    void updateStatusByBatchId(@QueryParam(value = "status") int status, @QueryParam(value = "batchNo") Long batchNo);


    @NativeUpdate
    void updateStatusByBatchId2(@QueryParam(value = "status") int status, @QueryParam(value = "batchNo") Long batchNo);

    @NativeUpdate
    void clearStatusAndBatchByBatchId(@QueryParam(value = "status") int status, @QueryParam(value = "batchNo") Long batchNo);

    @NamedQuery
    List<MsgOutboundOrder> findeMsgOutboundOrderBybatchId(@QueryParam(value = "batchId") Long batchId, @QueryParam(value = "status") DefaultStatus status);

    @NamedQuery
    MsgOutboundOrder findOutBoundByStaCode(@QueryParam(value = "staCode") String staCode);

    @NativeUpdate
    void updateOutBoundStatubatchNo(@QueryParam(value = "sta") int sta, @QueryParam(value = "staCode") String staCode);

    @NamedQuery
    List<MsgOutboundOrder> findOutBoundList(@QueryParam(value = "source") String source);

    @NamedQuery
    List<MsgOutboundOrder> getOutBoundListByBatchNo(@QueryParam(value = "batchId") Long batchId);

    @NativeQuery
    List<Long> findMsgOutboundOrderBatchNoBySource(@QueryParam(value = "source") String source, RowMapper<Long> batchNo);

    @NativeUpdate
    void saveIdslog(@QueryParam("workTime") Date workTime, @QueryParam("opcode") String opcode, @QueryParam("payload") String payload, @QueryParam(value = "status") Long status);

    /**
     * 查询当前库存
     * 
     * @param start
     * @param pageSize
     * @param brand
     * @param size
     * @param rowMapper
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<MsgOutboundOrderCommand> findMsgOutboundOrderByPage(int start, int pageSize, @QueryParam("staCode") String staCode, @QueryParam("slipCode") String slipCode, @QueryParam("status") int status, @QueryParam("province") String province,
            @QueryParam("city") String city, @QueryParam("startDate") Date startDate, @QueryParam("endDate") Date endDate, @QueryParam("whId") Long whId, RowMapper<MsgOutboundOrderCommand> rowMapper, Sort[] sorts);

    /**
     * 查询当前库存
     * 
     * @param start
     * @param pageSize
     * @param brand
     * @param size
     * @param rowMapper
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<MsgOutboundOrderCommand> findMsgOutboundOrderByPageRoot(int start, int pageSize, @QueryParam("staCode") String staCode, @QueryParam("slipCode") String slipCode, @QueryParam("status") int status, @QueryParam("province") String province,
            @QueryParam("city") String city, @QueryParam("startDate") Date startDate, @QueryParam("endDate") Date endDate, @QueryParam("ouId") Long ouId, RowMapper<MsgOutboundOrderCommand> rowMapper, Sort[] sorts);



    /**
     * 按仓库Source查询出库指令
     * 
     * @param vmiSource
     * @param status
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<MsgOutboundOrderCommand> findMsgOutboundOrderBySource(@QueryParam("vmiSource") String vmiSource, @QueryParam("status") int status, @QueryParam("ouId") Long ouId, RowMapper<MsgOutboundOrderCommand> rowMapper);

    @NativeQuery
    List<MsgOutboundOrderCommand> findCKWHCodeBySKUCode(@QueryParam("skuCode") String skuCode, @QueryParam("statusId") Long statusId, @QueryParam("ouId") Long ouId, RowMapper<MsgOutboundOrderCommand> rowMapper);

    @NativeQuery
    MsgOutboundOrderCommand findMsgOutboundOrderByMsgId(@QueryParam("msgId") Long msgId, RowMapper<MsgOutboundOrderCommand> rowMapper);


    /**
     * 查询需要创建到物流宝的销售sta
     * 
     * @param start
     * @param pageSize
     * @param brand
     * @param size
     * @param rowMapper
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<MsgOutboundOrderCommand> findWLBMsgOutboundOrderByPage(int start, int pageSize, @QueryParam("staCode") String staCode, @QueryParam("slipCode") String slipCode, @QueryParam("status") int status, @QueryParam("startDate") Date startDate,
            @QueryParam("endDate") Date endDate, @QueryParam("shopId") Long shopId, @QueryParam("source") String source, @QueryParam("sourcewh") String sourcewh, RowMapper<MsgOutboundOrderCommand> rowMapper, Sort[] sorts);


    @NativeQuery
    MsgOutboundOrderCommand getWlbItemIdByCode(@QueryParam("shopId") Long ouId, @QueryParam("code") String code, RowMapper<MsgOutboundOrderCommand> rowMapper);


    @NamedQuery
    MsgOutboundOrder findOutBoundByWlbCode(@QueryParam(value = "wlbCode") String staCode);

    @NamedQuery
    MsgOutboundOrder findOutBoundBySlipCode(@QueryParam(value = "slipCode") String slipCode);

    @NativeUpdate
    void saveWlbLog(@QueryParam(value = "type") int type, @QueryParam(value = "code") String code, @QueryParam(value = "shopId") Long shopId, @QueryParam(value = "shopName") String shopName, @QueryParam(value = "exEntityId") String exEntityId,
            @QueryParam(value = "itemId") String itemId, @QueryParam(value = "errorMsg") String errorMsg);

    @NativeUpdate
    void updateWlbCode(@QueryParam(value = "outOrderId") Long oId, @QueryParam(value = "wlbCode") String wlbCode, @QueryParam(value = "status") int status);

    @NativeUpdate
    void updateMsgOutboundOrderById(@QueryParam(value = "batchId") Long batchId, @QueryParam(value = "idList") List<Long> id);

    @NativeQuery
    MsgOutboundOrder findOutOrderCodeBySlipCode(@QueryParam("code") String code, RowMapper<MsgOutboundOrder> rowMapper);

    @NativeQuery
    List<MsgOutboundOrderCommand> findGDVSalesOrder(RowMapper<MsgOutboundOrderCommand> rowMapper);

    @NativeQuery
    MsgOutboundOrderCommand findGDVSalesOrderStacode(@QueryParam("code") String code, RowMapper<MsgOutboundOrderCommand> rowMapper);

    @NativeUpdate
    void updateMsgoutboundOrder(@QueryParam(value = "mId") Long mId, @QueryParam(value = "status") int status);

    @NativeUpdate
    void updateMsgOutBoundOrderUnLocked();

    @NamedQuery
    List<StockTransApplication> findLockedMsgOrder();

    @NamedQuery
    MsgOutboundOrder getByStaCode(@QueryParam("staCode") String code);

    @NativeQuery
    List<MsgOutboundOrder> sendSOOutboundOrder(@QueryParam(value = "source") String source, @QueryParam(value = "sourceWH") String sourceWH, @QueryParam(value = "struts") int struts, RowMapper<MsgOutboundOrder> orders);

    @NativeUpdate
    Integer updateMsgOutboundOrderBatchNo(@QueryParam(value = "batchNo") Long batchNo, @QueryParam(value = "source") String source, @QueryParam(value = "sourceWH") String sourceWH, @QueryParam(value = "struts") int struts,
            RowMapper<MsgOutboundOrder> orders);

    @NativeUpdate
    Integer updateMsgOutboundOrderBatchNo2(@QueryParam(value = "batchNo") Long batchNo, @QueryParam(value = "source") String source, @QueryParam(value = "sourceWH") String sourceWH, @QueryParam(value = "struts") int struts,
            @QueryParam(value = "num") Integer num, RowMapper<MsgOutboundOrder> orders);

    @NativeUpdate
    void sendSOOutboundOrderAfreshs(@QueryParam(value = "batchId") Long batchId, @QueryParam(value = "sourceWH") String sourceWH);

    /**
     * 大陆站新加
     * 
     * @param vimWhSourceSf
     * @return
     */
    @NamedQuery
    List<MsgOutboundOrder> findVmiOutboundNeedSend(@QueryParam("vmiSource") String vimWhSourceSf);

    /**
     * SF外包仓取消订单通知
     * 
     * @param code
     * @return
     */
    @NamedQuery
    List<String> findCancelOutboundOrderToSf(@QueryParam("vmiSource") String vimWhSourceSf);

    /**
     * 金宝贝零售单指示查询
     * 
     * @param vmiWhSourceGymboree
     * @return
     */
    @NamedQuery
    List<MsgOutboundOrder> findVmiOutboundNeedSendHaveType(@QueryParam("vmiSource") String vmiWhSourceGymboree, @QueryParam("wh") String whFlag);

    /**
     * 查询状态为新建或取消的推送第三方
     * 
     * @return
     */
    @NativeQuery
    List<MsgOutboundOrder> getOutboundOrderByStatus(@QueryParam(value = "code") String code, RowMapper<MsgOutboundOrder> rowMap);

    @NamedQuery
    List<MsgOutboundOrder> getMsgOutboundByErrorCount(@QueryParam(value = "errorCount") Long errorCount);

    @NativeUpdate
    void updateOutboundById(@QueryParam(value = "id") Long id);

    @NamedQuery
    MsgOutboundOrder getByWlbCode(@QueryParam(value = "wlbCode") String wlbCode);

    /*
     * UA出库通知查询CODE和TYPE
     * 
     * @NativeQuery MsgOutboundOrder findOutOrderCodeAndTypeBySlipCode(@QueryParam("slipCode")
     * String slipCode, RowMapper<MsgOutboundOrder> rowMapper);
     */

    @NativeQuery
    List<MsgOutboundOrder> findMsgOutListByDate(@QueryParam(value = "startTime") Date startTime, @QueryParam(value = "endTime") Date endTime, @QueryParam(value = "source") String source, BeanPropertyRowMapperExt<MsgOutboundOrder> beanPropertyRowMapper);

    @NativeQuery
    List<MsgOutboundOrderCommand> findMsgOutListByDate2(@QueryParam(value = "startTime") Date startTime, @QueryParam(value = "endTime") Date endTime, @QueryParam(value = "source") String source, @QueryParam(value = "startNum") int startNum,
            @QueryParam(value = "endNum") int endNum, BeanPropertyRowMapperExt<MsgOutboundOrderCommand> beanPropertyRowMapper);

    @NamedQuery
    MsgOutboundOrder getByCodeAndSource(@QueryParam(value = "staCode") String staCode, @QueryParam(value = "source") String source);

    @NamedQuery
    MsgOutboundOrder findSlipCode(@QueryParam(value = "slipCode") String slipCode);

    /**
     * @param vimWhSourceIds
     * @param b
     * @param object
     * @param value
     * @param batchNo
     * @param value2
     * @return
     */
    @NativeUpdate
    int updateLevisBatchData(@QueryParam(value = "source") String source, @QueryParam(value = "islocked") boolean islocked, @QueryParam(value = "sourceWH") String sourceWH, @QueryParam(value = "struts") int struts,
            @QueryParam(value = "batchId") long batchId, @QueryParam(value = "toStruts") int toStruts);

    /**
     * @param source
     * @param time
     */
    @NativeQuery
    String findSalesOrderSendToLFFailedMailIds(@QueryParam(value = "source") String source, @QueryParam(value = "time") long time, SingleColumnRowMapper<String> singleColumnRowMapper);

}
