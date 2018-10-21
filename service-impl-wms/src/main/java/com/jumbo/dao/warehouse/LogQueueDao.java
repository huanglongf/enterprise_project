package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.LogQueue;
import com.jumbo.wms.model.warehouse.LogQueueCode;

@Transactional
public interface LogQueueDao extends GenericEntityDao<LogQueue, Long> {
    // @NamedQuery
    // public List<LogQueue> queryLogQueuebatchId(@QueryParam("batchId")String batchId);
    /**
     * 查询当前批次所有数据
     * 
     * @param batchId
     * @param beanPropertyRowMapperExt
     * @return
     */
    @NativeQuery
    public List<LogQueueCode> queryLogQueue(@QueryParam("batchId") String batchId, BeanPropertyRowMapperExt<LogQueueCode> beanPropertyRowMapperExt);

    /**
     * 更新批次
     * 
     * @param batchId
     * @return
     */
    @NativeUpdate
    public int updateLogQueueBatchId(@QueryParam("batchId") String batchId);

    /**
     * 记录日志表
     * 
     * @param batchId
     */
    @NativeUpdate
    public void addLogQueue(@QueryParam("batchId") String batchId);

    /**
     * 查询出错批次号
     * 
     * @param r
     * @return
     */
    @NativeQuery
    public List<String> queryLogQueueBatchId(SingleColumnRowMapper<String> r);

    /**
     * 跟新错误次数
     * 
     * @param batchId
     */
    @NativeUpdate
    public void updateLogQueue(@QueryParam("batchId") String batchId, @QueryParam("count") int count);

    /**
     * 删除完成信息
     * 
     * @param batchId
     */
    @NativeUpdate
    public void deleteLogQueue();

    /**
     * 更新批次
     * 
     * @param batchId
     * @return
     */
    @NativeUpdate
    public int updateLogQueueStatus(@QueryParam("batchId") String batchId);

    /**
     * 查询当前同步失败的增量库存条数
     * 
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    public Integer findErrorCount(SingleColumnRowMapper<Integer> singleColumnRowMapper);
    /**
     * 查询当前错误批次
     * 
     * @param batchId
     * @param beanPropertyRowMapperExt
     * @return
     */
    @NativeQuery
    public List<LogQueue> queryLogQueueEmail(BeanPropertyRowMapperExt<LogQueue> beanPropertyRowMapperExt);
    /**
     * 修改发送通知
     * 
     * @param batchId
     * @return
     */
    @NativeUpdate
    public int updateLogQueueEmail(@QueryParam("batchId") String batchId);
    



}
