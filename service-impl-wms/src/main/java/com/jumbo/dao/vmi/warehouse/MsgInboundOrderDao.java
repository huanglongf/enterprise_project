package com.jumbo.dao.vmi.warehouse;



import java.util.Date;
import java.util.List;

import loxia.annotation.DynamicQuery;
import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrderCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;

@Transactional
public interface MsgInboundOrderDao extends GenericEntityDao<MsgInboundOrder, Long> {

    @NativeQuery
    MsgInboundOrder findMsgInboundOrder(@QueryParam(value = "stacCod") String stacCod, RowMapper<MsgInboundOrder> rowMapper);

    @NativeUpdate
    void updateMsgInboundStatus(@QueryParam(value = "inOrderId") Long oId, @QueryParam(value = "status") int status);

    @DynamicQuery
    List<MsgInboundOrder> findMsgInboundByStatus(@QueryParam(value = "source") String source, @QueryParam(value = "sourceWH") String sourceWH, @QueryParam("status") DefaultStatus status, @QueryParam("isAll") boolean isAll);

    @DynamicQuery
    List<MsgInboundOrder> findMsgReturnInboundByStatus(@QueryParam(value = "source") String source, @QueryParam(value = "sourceWH") String sourceWH, @QueryParam(value = "type") StockTransApplicationType type);

    @NamedQuery
    List<MsgInboundOrder> findMsgReturnInboundByStatus(@QueryParam(value = "source") String source, @QueryParam(value = "type") StockTransApplicationType type);

    // @NativeQuery
    // List<MsgInboundOrderCommand> findInboundBySourceANDStatus(@QueryParam("vmiSource") String
    // vmiSource, @QueryParam("status") int status, RowMapper<MsgInboundOrderCommand> rowMapper);

    @DynamicQuery
    List<MsgInboundOrder> findMsgInboundOrderList(@QueryParam(value = "source") String source, @QueryParam(value = "sourceWH") String sourceWH);

    @DynamicQuery
    List<MsgInboundOrder> findByOwner(@QueryParam(value = "source") String source, @QueryParam(value = "ownerSource") String ownerSource, @QueryParam(value = "type") StockTransApplicationType type, @QueryParam("status") DefaultStatus status);


    // @NativeQuery
    // String findTBCodeByStaSoId(@QueryParam("staId") Long staId, RowMapper<String> rowmap);

    // @NativeQuery
    // String findTBCodeByStaRaId(@QueryParam("staId") Long staId, RowMapper<String> rowmap);

    /**
     * 跟新须要执行的数据状态
     * 
     * @param status
     * @param updateStatus
     */
    @NativeUpdate
    void updateIDSASNStatus(@QueryParam(value = "status") Integer status, @QueryParam(value = "updateStatus") Integer updateStatus);


    @NativeUpdate
    void updateInboundOrder(@QueryParam(value = "batchId") Long batchId, @QueryParam(value = "status") int status, @QueryParam(value = "oId") Long id);

    @NativeUpdate
    void updateStatusBySourceANDSta(@QueryParam(value = "fromStatus") int fromStatus, @QueryParam(value = "toStatus") int toStatus, @QueryParam(value = "vmiSource") String vmiSource);


    @NativeUpdate
    void updateInboundOrderStatusByStaCode(@QueryParam("staCode") String staCode, @QueryParam("status") int status);

    @NativeUpdate
    void updateInboundOrderStatusByBatchId(@QueryParam("batchId") Long batchId, @QueryParam("status") int status);

    @NamedQuery
    List<MsgInboundOrder> findMsgInbounderListByBybatchId(@QueryParam(value = "batchId") Long batchId, @QueryParam(value = "status") DefaultStatus status);

    @NativeQuery
    Long findInOrderBatchNo(RowMapper<Long> batchNo);



    @NativeUpdate
    void updateInboundOrderByStaCode(@QueryParam(value = "status") int status, @QueryParam(value = "staCode") String staCode);

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
    Pagination<MsgInboundOrderCommand> findMsgInboundOrderByPage(int start, int pageSize, @QueryParam("staCode") String staCode, @QueryParam("slipCode") String slipCode, @QueryParam("status") int status, @QueryParam("startDate") Date startDate,
            @QueryParam("endDate") Date endDate, @QueryParam("whId") Long whId, RowMapper<MsgInboundOrderCommand> rowMapper, Sort[] sorts);

    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<MsgInboundOrderCommand> findMsgInboundOrderByPageRoot(int start, int pageSize, @QueryParam("staCode") String staCode, @QueryParam("slipCode") String slipCode, @QueryParam("status") int status, @QueryParam("startDate") Date startDate,
            @QueryParam("endDate") Date endDate, @QueryParam("ouId") Long ouId, RowMapper<MsgInboundOrderCommand> rowMapper, Sort[] sorts);

    @NativeUpdate
    void saveRtnInBoundFromTemplate(@QueryParam(value = "source") String source, @QueryParam(value = "rtnStatus") Integer rtnStatus, @QueryParam(value = "fbStatus") Integer fbStatus);


    @NativeQuery
    Long findRaStatusByCode(@QueryParam("slipCode") String slipCode, RowMapper<Long> rowmap);


    /**
     * 查询需要创建到物流宝的入库sta
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
    Pagination<MsgInboundOrderCommand> findWLBMsgInboundOrderByPage(int start, int pageSize, @QueryParam("staCode") String staCode, @QueryParam("status") int status, @QueryParam("startDate") Date startDate, @QueryParam("endDate") Date endDate,
            @QueryParam("shopId") Long shopId, @QueryParam("source") String source, @QueryParam("sourcewh") String sourcewh, RowMapper<MsgInboundOrderCommand> rowMapper, Sort[] sorts);

    @NamedQuery
    MsgInboundOrder findByStaCode(@QueryParam(value = "staCode") String staCode);

    @NativeQuery
    Long findCountByStaCode(@QueryParam(value = "staCode") String staCode, RowMapper<Long> r);

    @NativeUpdate
    void createLineForBatchCode(@QueryParam(value = "staCode") String staCode, @QueryParam(value = "msgId") Long msgId);

    @NativeUpdate
    void updateWlbCode(@QueryParam(value = "inOrderId") Long oId, @QueryParam(value = "wlbCode") String wlbCode, @QueryParam(value = "status") int status);


    @NativeQuery(pagable = true, withGroupby = true)
    List<MsgInboundOrder> findWlbMsgInboundByStatus(int start, int pageSize, @QueryParam(value = "source") String source, @QueryParam(value = "sourceWh") String sourceWh, @QueryParam("startDate") Date startDate, @QueryParam("endDate") Date endDate,
            @QueryParam(value = "type") String type, RowMapper<MsgInboundOrder> rowMapper, Sort[] sorts);

    @NativeQuery
    String findWlbItemId(@QueryParam(value = "extCode") String extCode, @QueryParam(value = "shopId") Long shopId, RowMapper<String> wlbItemId);

    // @NativeQuery
    // MsgInboundOrderCommand findMsgInboundOrderbyRefSlipCode(@QueryParam(value = "refSlipCode")
    // String refSlipCode, RowMapper<MsgInboundOrderCommand> rowMapper);

    @NamedQuery
    List<MsgInboundOrder> findInboundOrderByStatus(@QueryParam(value = "status") DefaultStatus status, @QueryParam(value = "source") String source, @QueryParam(value = "type") StockTransApplicationType type);

    /**
     * 香港站新加
     * 
     * @param vmiWhSource
     * @return
     */
    @NamedQuery
    List<MsgInboundOrder> findVmiInboundNeedSend(@QueryParam("vmiSource") String vmiWhSource);

    /**
     * 金宝贝退货指示查询
     * 
     * @param vmiWhSourceGymboree
     * @return
     */
    @NamedQuery
    List<MsgInboundOrder> findVmiInboundNeedSendHaveType(@QueryParam("vmiSource") String vmiWhSourceGymboree, @QueryParam("wh") String owner);

    @DynamicQuery
    List<MsgInboundOrder> findMsgReturnInboundByStatusToLF(@QueryParam(value = "source") String source, @QueryParam(value = "sourceWH") String sourceWH, @QueryParam(value = "type") StockTransApplicationType type);

    /**
     * 查询状态为新建或取消的推送第三方
     * 
     * @return
     */
    @NativeQuery
    List<MsgInboundOrder> getInboundOrderByStatus(@QueryParam(value = "code") String code, RowMapper<MsgInboundOrder> rowMap);

    @NamedQuery
    List<MsgInboundOrder> getMsgInboundByErrorCount(@QueryParam(value = "errorCount") Long errorCount);

    @NativeUpdate
    void updateInboundById(@QueryParam(value = "id") Long id);

    @NativeUpdate
    void insertWlbInQueryTime(@QueryParam(value = "type") int type, @QueryParam(value = "endTime") Date endTime);

    @NamedQuery
    MsgInboundOrder getByWlbCode(@QueryParam(value = "wlbCode") String wlbCode);

    @NamedQuery
    MsgInboundOrder getByCodeAndSource(@QueryParam(value = "staCode") String staCode, @QueryParam(value = "source") String source);

    @NativeQuery
    List<MsgInboundOrder> findMsgInListByDate(@QueryParam(value = "startTime") Date startTime, @QueryParam(value = "endTime") Date endTime, @QueryParam(value = "source") String source, BeanPropertyRowMapperExt<MsgInboundOrder> beanPropertyRowMapper);

    @NativeQuery
    List<MsgInboundOrder> findMsgInListByDate2(@QueryParam(value = "startTime") Date startTime, @QueryParam(value = "endTime") Date endTime, @QueryParam(value = "source") String source, BeanPropertyRowMapperExt<MsgInboundOrder> beanPropertyRowMapper);

}
