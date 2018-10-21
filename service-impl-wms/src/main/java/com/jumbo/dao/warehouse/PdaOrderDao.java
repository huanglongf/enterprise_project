package com.jumbo.dao.warehouse;

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

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.pda.PdaOrder;
import com.jumbo.wms.model.pda.PdaOrderCommand;
import com.jumbo.wms.model.pda.PdaOrderType;

@Transactional
public interface PdaOrderDao extends GenericEntityDao<PdaOrder, Long> {

    /**
     * 查询须要执行的单据（状态为 0 or 1 的）
     * 
     * @param type 根据类型查找
     * @return
     */
    @NamedQuery
    List<PdaOrder> findNeedToPerformByType(@QueryParam(value = "type") PdaOrderType type);


    /**
     * 查询须要执行的单据（状态为 0 or 1 的）
     * 
     * @param type 根据类型查找
     * @return
     */
    @DynamicQuery
    List<PdaOrder> findPdaOrderByOrderCode(@QueryParam(value = "orderCode") String orderCode, @QueryParam(value = "typeList") List<Integer> typeList, @QueryParam(value = "statusList") List<Integer> statusList);



    /**
     * 查询须要执行的单据（状态为 0 or 1 的）
     * 
     * @param type 根据类型查找
     * @return
     */
    @NativeUpdate
    void updatePdaOrderStatus(@QueryParam(value = "pdaOrderId") Long pdaOrderId, @QueryParam(value = "status") Integer status, @QueryParam(value = "memo") String memo);
    
    /**
     * 更新状态 根据明细数据是否都已经完成
     * @param pdaOrderId
     * @param status
     * @param memo
     */
    @NativeUpdate
    void updatePdaStatusByLine(@QueryParam(value = "code") String code, @QueryParam(value = "type") Integer type);


    /**
     * 查看Pda操作日志
     * 
     * @param start
     * @param pageSize
     * @param code
     * @param stat
     * @param endTime1
     * @param endTime
     * @param bt1
     * @param bt
     * @param id
     * @param sorts
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<PdaOrderCommand> findPdaOrderList(int start, int pageSize, @QueryParam("beginTime") Date bt, @QueryParam("beginTime1") Date bt1, @QueryParam("endTime") Date endTime, @QueryParam("endTime1") Date endTime1, @QueryParam("stat") Integer stat,
            @QueryParam("pType") Integer pType,@QueryParam("code") String code, @QueryParam("id") String id, Sort[] sorts, BeanPropertyRowMapper<PdaOrderCommand> beanPropertyRowMapper);

    /**
     * 查询操作人
     * 
     * @param staCode
     * @param RowMapper
     * @return
     */
    @NativeQuery
    List<PdaOrder> findPdaUserName(@QueryParam(value = "staCode") String staCode, RowMapper<PdaOrder> RowMapper);


    /**
     * 根据作业单号查询PDA中间表中是否存在未执行单据
     * 
     * @param code
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    String getPdaOrderByCode(@QueryParam("code") String code, SingleColumnRowMapper<String> singleColumnRowMapper);

    /**
     * 根据作业单号 查询PDA中间表是否存在未操作的单据 KJL
     * 
     * @param code
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    Long findPdaOrderByCodeAndTypeStatus(@QueryParam("code") String code, SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * PDA失败作业单列表
     * 
     * @param start
     * @param pageSize
     * @param date
     * @param date2
     * @param code
     * @param typeList
     * @param statusList
     * @param ouId 
     * @param sorts
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<PdaOrder> getErrorPdaLogList(int start, int pageSize, @QueryParam("beginTime") Date date, @QueryParam("endTime") Date date2, @QueryParam("code") String code, @QueryParam("statusList") List<Integer> statusList,
            @QueryParam("typeList") List<Integer> typeList, @QueryParam("ouId")String ouId, Sort[] sorts, BeanPropertyRowMapper<PdaOrder> beanPropertyRowMapper);
    /**
     * 根据单据id更新PDA单据状态让其重新执行
     * @param id
     */
    @NativeUpdate
    void updateStatusById(@QueryParam("id")Long id);
}
