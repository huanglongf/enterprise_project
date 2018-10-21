package com.jumbo.dao.vmi.warehouse;


import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutboundCommand;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutboundCommand2;
import com.jumbo.wms.model.warehouse.StaLineCommand;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

@Transactional
public interface MsgRtnOutboundDao extends GenericEntityDao<MsgRtnOutbound, Long> {

    @NativeQuery
    MsgRtnOutboundCommand findVmiMsgOutbound(@QueryParam("msgId") long msgId, RowMapper<MsgRtnOutboundCommand> rowMapper);

    @NativeQuery
    List<MsgRtnOutbound> findVmiMsgOutboundByStaCode(@QueryParam("staCode") String staCode, RowMapper<MsgRtnOutbound> rowMapper);

    @NamedQuery
    MsgRtnOutbound findByStaCode(@QueryParam("staCode") String staCode);

    @NamedQuery
    MsgRtnOutbound findBySlipCode(@QueryParam("slipCode") String slipCode);

    @NativeQuery
    List<MsgRtnOutbound> findAllVmiMsgOutbound(@QueryParam(value = "source") String source, RowMapper<MsgRtnOutbound> rowMapper);

    @NativeQuery
    List<MsgRtnOutbound> findAllVmiMsgOutboundByRowNum(@QueryParam(value = "source") String source, @QueryParam(value = "rowNum") int rowNum, RowMapper<MsgRtnOutbound> rowMapper);

    @NativeUpdate
    void updateStatusByID(@QueryParam("msgId") long msgId, @QueryParam("sta") int sta);

    @NativeUpdate
    void updateStatusByIDMq(@QueryParam("msgId") long msgId, @QueryParam("sta") int sta);


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
    Pagination<MsgRtnOutboundCommand2> findMsgRtnOutboundByPage(int start, int pageSize, @QueryParam("staCode") String staCode, @QueryParam("slipCode") String slipCode, @QueryParam("status") int status, @QueryParam("startDate") Date startDate,
            @QueryParam("endDate") Date endDate, @QueryParam("whId") Long whId, RowMapper<MsgRtnOutboundCommand2> rowMapper, Sort[] sorts);


    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<MsgRtnOutboundCommand> findStaByStaCode(int start, int pageSize, @QueryParam("staCodeList") List staCodeList, Sort[] sorts, RowMapper<MsgRtnOutboundCommand> rowMapper);


    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<MsgRtnOutboundCommand2> findMsgRtnOutboundByPageRoot(int start, int pageSize, @QueryParam("staCode") String staCode, @QueryParam("slipCode") String slipCode, @QueryParam("status") int status, @QueryParam("startDate") Date startDate,
            @QueryParam("endDate") Date endDate, @QueryParam("ouId") Long ouId, RowMapper<MsgRtnOutboundCommand2> rowMapper, Sort[] sorts);


    @NativeUpdate
    void saveRtnOutBoundFromTemplate(@QueryParam(value = "source") String source, @QueryParam(value = "rtnStatus") Integer rtnStatus, @QueryParam(value = "fbStatus") Integer fbStatus);

    @NativeUpdate
    void updateWmsOutBound(@QueryParam(value = "idsList") List<Long> idsList);

    @NativeQuery
    Date findWlbMsgNextTime(@QueryParam(value = "type") int type, @QueryParam(value = "shopId") Long shopId, RowMapper<Date> rm);

    @NativeUpdate
    void updateWlbMsgNextTime(@QueryParam(value = "type") int type, @QueryParam(value = "shopId") Long shopId, @QueryParam(value = "date") Date date);

    @NativeUpdate
    void insertWLbTime(@QueryParam(value = "type") int type, @QueryParam(value = "shopId") Long shopId, @QueryParam(value = "date") Date date);


    @NativeQuery
    List<MsgRtnOutbound> findGdvFailInfo(@QueryParam(value = "source") String source, @QueryParam(value = "startDate") Date startDate, @QueryParam(value = "endDate") Date endDate, RowMapper<MsgRtnOutbound> rowMapper);

    @NativeUpdate
    void updateMsgRtnOutBoundStaCode(@QueryParam(value = "msgId") Long msgId, @QueryParam(value = "staCode") String staCode);

    @NamedQuery
    List<MsgRtnOutbound> getNeedExeReplenishOrder(@QueryParam("vmiSource") String vimWhSourceSf);

    @NativeQuery
    List<MsgRtnOutbound> findRtnOutboundByStatus(@QueryParam(value = "sourceList") List<String> sourceList, RowMapper<MsgRtnOutbound> rowMap);

    @NativeUpdate
    void updateRtnOutboundById(@QueryParam(value = "id") Long id);

    @NamedQuery
    List<MsgRtnOutbound> getRtnOutboundByErrorCount(@QueryParam(value = "errorCount") Long errorCount);

    @NativeQuery
    List<MsgRtnOutbound> wmsRtnOutBountMq(BeanPropertyRowMapper<MsgRtnOutbound> beanPropertyRowMapper);


    @NativeUpdate
    void updateGymboreeErrorOrder();

    @NativeQuery
    List<MsgRtnOutbound> findNeedEmailGymboreeOrder(BeanPropertyRowMapper<MsgRtnOutbound> beanPropertyRowMapper);

    @NativeUpdate
    void updateGymboreeErrorOrderSend();

    /**
     * 方法说明：菜鸟仓SN发货查询 根据状态已经完成(10)和是否SN商品查询
     * 
     * @author LuYingMing
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<MsgRtnOutbound> findRtnOutboundByStatusAndSn(BeanPropertyRowMapper<MsgRtnOutbound> beanPropertyRowMapper);

    /**
     * 方法说明：更新MsgRtnOutbound 错误次数
     * 
     * @author LuYingMing
     * @param msgId
     * @param errorCode
     */
    @NativeUpdate
    void updateErrorCodeByID(@QueryParam("msgId") long msgId, @QueryParam("errorCount") Long eCount);

    /**
     * 方法说明：查询错误次数大于3次且没有发送邮件
     * 
     * @author LuYingMing
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<MsgRtnOutbound> findRtnOutboundByErrorCountAndNoSend(BeanPropertyRowMapper<MsgRtnOutbound> beanPropertyRowMapper);

    /**
     * 方法说明：更新邮件发送状态(菜鸟仓sn发货查询)
     * 
     * @author LuYingMing
     * @param msgId
     */
    @NativeUpdate
    void updateIsSendById(@QueryParam(value = "msgId") Long msgId);

    /**
     * 通过source定制-外包仓出库执行失败数据
     * 
     * @param source
     * @param errorCount
     * @return
     */
    @NamedQuery
    List<MsgRtnOutbound> getRtnOutboundByErrorCountAndSource(@QueryParam(value = "source") String source, @QueryParam(value = "errorCount") Long errorCount);

    /**
     * 获取反馈的明细行数量
     * 
     * @param msgId
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<StaLineCommand> findRtnOutBoudLineQty(@QueryParam(value = "msgId") Long msgId, BeanPropertyRowMapper<StaLineCommand> beanPropertyRowMapper);
}
