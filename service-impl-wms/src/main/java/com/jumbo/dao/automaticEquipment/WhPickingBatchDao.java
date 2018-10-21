package com.jumbo.dao.automaticEquipment;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.automaticEquipment.WhPickingBatch;
import com.jumbo.wms.model.command.automaticEquipment.WhPickingBatchCommand;
import com.jumbo.wms.model.warehouse.PickingList;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

/**
 * 配货二级批次DAO
 * 
 * @author jinlong.ke
 * @date 2016年2月26日下午5:18:36
 */
@Transactional
public interface WhPickingBatchDao extends GenericEntityDao<WhPickingBatch, Long> {

    @NativeUpdate
    void savePlzByPickingList(@QueryParam(value = "pId") Long pId);

    @NamedQuery
    List<WhPickingBatch> getPlzListByPickingListId(@QueryParam(value = "pId") Long pId);

    @NativeQuery
    List<String> findPlzListByPickingListIdS(@QueryParam(value = "pIdS") String pIdS, SingleColumnRowMapper<String> scrm);

    @NamedQuery
    WhPickingBatch getPlzListByCode(@QueryParam(value = "code") String code);

    @NamedQuery
    List<WhPickingBatch> getPlzListByBarCode(@QueryParam(value = "barCode") String barCode);

    @NamedQuery
    PickingList getPkListByBarCode(@QueryParam(value = "barCode") String barCode);

    @NamedQuery
    WhPickingBatch getPlzListByZoonCode(@QueryParam(value = "pId") Long pId, @QueryParam(value = "zoonCode") String zoonCode);

    @NativeQuery(pagable = true)
    Pagination<WhPickingBatchCommand> findPickingListZoneByParams(int start, int pageSize, @QueryParam Map<String, Object> m, RowMapper<WhPickingBatchCommand> rowMapper);

    @NativeUpdate
    void deleteBatchByPickId(@QueryParam(value = "pId") Long pId);

    @NativeUpdate
    void deleteContainByPkId(@QueryParam(value = "pId") Long pId);

    @NativeUpdate
    void updateBatchByPickId(@QueryParam(value = "pId") Long pId);

    @NamedQuery
    List<WhPickingBatch> getPlzListByPickingListIdAndStatus(@QueryParam(value = "pId") Long pId, @QueryParam(value = "status") DefaultStatus status);

    /*
     * @NativeQuery(pagable = true) Pagination<com.jumbo.wms.model.command.WhPickingBatchCommand>
     * getPickingListLogQueryDo(int start, int pagesize, @QueryParam("ouId") Long ouId,
     * 
     * @QueryParam("startTime") Date startTime, @QueryParam("startTime1") Date startTime1,
     * 
     * @QueryParam("createTime") Date createTime, @QueryParam("createTime1") Date createTime1,
     * 
     * @QueryParam("patchCode") String patchCode, @QueryParam("userName") String userName,
     * RowMapper<com.jumbo.wms.model.command.WhPickingBatchCommand> r, Sort[] sorts);
     */

    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<com.jumbo.wms.model.command.WhPickingBatchCommand2> getPickingListLogQueryPage(int start, int pagesize, @QueryParam("ouId") Long ouId, @QueryParam("startTime") Date startTime, @QueryParam("startTime2") Date startTime1,
            @QueryParam("createTime") Date createTime, @QueryParam("createTime2") Date createTime1, @QueryParam("patchCode") String patchCode, @QueryParam("userName") String userName, RowMapper<com.jumbo.wms.model.command.WhPickingBatchCommand2> r,
            Sort[] sorts);


    /**
     * 设置开始时间与操作人
     */
    @NativeUpdate
    void updateWhPickingBatchStartTime(@QueryParam(value = "barCode") String barCode, @QueryParam(value = "userId") Long userId);

    /**
     * 设置结束时间
     */
    @NativeUpdate
    int updateWhPickingBatchEndTime(@QueryParam(value = "barCode") String barCode, @QueryParam(value = "endTime") Date endTime);

    /**
     * 清除没有明细的小批次
     * 
     * @param pId
     */
    @NativeQuery
    List<Long> findWhPickingBatchByNotLine(@QueryParam(value = "pbBarCode") String pbBarCode, SingleColumnRowMapper<Long> scr);

    /**
     * 清除没有明细的小批次
     * 
     * @param pId
     */
    @NativeQuery
    List<Long> findWhPickingBatchByNotLine2(@QueryParam(value = "pIdS") String pIdS, SingleColumnRowMapper<Long> scr);

    @NativeUpdate
    void modifyStaPbId(@QueryParam(value = "pbIds") List<Long> pbIds);

}
