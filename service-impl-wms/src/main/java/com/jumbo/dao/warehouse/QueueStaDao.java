package com.jumbo.dao.warehouse;

import java.util.List;
import java.util.Map;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.QueueSta;
import com.jumbo.wms.model.warehouse.QueueStaCommand;

@Transactional
public interface QueueStaDao extends GenericEntityDao<QueueSta, Long> {
    /**
     * 修改批次号
     * 
     * @param batchcode
     */
    @NativeUpdate
    public Integer updateQueuesta(@QueryParam("batchcode") String batchcode, @QueryParam("ouid") Long ouid, @QueryParam("num") Integer num, SingleColumnRowMapper<Integer> s);

    /**
     * 修改批次号
     * 
     * @param batchcode
     */
    @NativeUpdate
    public Integer updateQueuesta(@QueryParam("batchcode") String batchcode, SingleColumnRowMapper<Integer> s);

    /**
     * 通过批次号查询单据
     * 
     * @param batchcode
     * @return
     */
    @NamedQuery
    public List<QueueSta> queryBatchcode(@QueryParam("batchcode") String batchcode);


    @NamedQuery
    public List<QueueSta> queryByErrorCount();

    /**
     * 获取单据批次号
     * 
     * @param s
     * @return
     */
    @NativeQuery
    public String queryBatchcode(SingleColumnRowMapper<String> s);

    /**
     * 查询失败过的待过仓数据
     * 
     * @return
     */
    @NamedQuery
    public List<QueueSta> queryQueuesta();

    /**
     * 根据前置单据查询非取消单据
     * 
     * @param code
     * @return
     */
    @NamedQuery
    public QueueSta findStaBySlipCodeNotCancel(@QueryParam("code") String code);

    // /**
    // * 根据仓库ID查询过仓队列
    // * @param ouid
    // * @return
    // */
    // @NativeQuery
    // public List<QueueSta> finbByStaOuid(@QueryParam("ouid") Long
    // ouid,BeanPropertyRowMapper<QueueSta> beanPropertyRowMapper);

    /**
     * 更新同一仓库的销售单据批次（不含addowner）
     * 
     * @return
     */
    @NativeUpdate
    int updateQstaBatchCodeByOuid(@QueryParam("ouid") Long ouid, @QueryParam("channelList") String channelList, @QueryParam("barchCode") String barchCode, @QueryParam("num") Integer num);

    /**
     * 更新同一仓库的销售单据批次（不含addowner）
     * 
     * @return
     */
    @NativeUpdate
    int updateQstaBatchCodeByOuid1(@QueryParam("ouid") Long ouid, @QueryParam("barchCode") String barchCode, @QueryParam("num") Integer num);

    /**
     * 更新同一仓库的销售单据批次（含addowner）
     * 
     * @return
     */
    @NativeUpdate
    int updateQstaBatchCodeByOuidOwner(@QueryParam("ouid") Long ouid, @QueryParam("channelList") String channelList, @QueryParam("barchCode") String barchCode, @QueryParam("num") Integer num);

    /**
     * 更新同一仓库的退换货单据批次
     * 
     * @return
     */
    @NativeUpdate
    int updateQstaBatchCodeByOuidOutOwner(@QueryParam("ouid") Long ouid, @QueryParam("channelList") String channelList, @QueryParam("barchCode") String barchCode, @QueryParam("num") Integer num);

    /**
     * 根据仓库ID批次号查询列队
     * 
     * @param ouid
     * @param batchcode
     * @return
     */
    @NamedQuery
    public List<QueueSta> getByStaBatchcode(@QueryParam("ouid") Long ouid, @QueryParam("batchcode") String batchcode);

    /**
     * 根据仓库ID批次号查询列队单据ID
     * 
     * @param ouid
     * @param batchcode
     * @return
     */
    @NativeQuery
    public List<QueueSta> findIdsByStaBatchcode(@QueryParam("ouid") Long ouid, @QueryParam("batchcode") String batchcode, RowMapper<QueueSta> r);

    /**
     * 根据仓库ID批次号查询列队单据ID
     * 
     * @param ouid
     * @param batchcode
     * @return
     */
    @NativeQuery
    public List<QueueSta> findIdsByStaBatchcode1(@QueryParam("ouid") Long ouid, @QueryParam("batchcode") String batchcode, RowMapper<QueueSta> r);

    /**
     * 根据仓库ID批次号查询列队
     * 
     * @param ouid
     * @param batchcode
     * @return
     */
    @NamedQuery
    public List<QueueSta> getByBatchcode(@QueryParam("batchcode") String batchcode, BeanPropertyRowMapper<QueueSta> beanPropertyRowMapper);

    /**
     * 根据仓库ID批次号查询退换货列队
     * 
     * @param ouid
     * @param batchcode
     * @return
     */
    @NativeQuery
    public List<String> findBatchCodeByOuid(@QueryParam("ouid") Long ouid, SingleColumnRowMapper<String> singleColumnRowMapper);

    /**
     * 根据仓库ID批次号查询退换货列队
     * 
     * @param ouid
     * @param batchcode
     * @return
     */
    @NativeQuery
    public List<QueueSta> findSetFlagByOuId(@QueryParam("ouId") Long ouid, BeanPropertyRowMapper<QueueSta> beanPropertyRowMapper);

    /**
     * 根据仓库ID批次号查询特殊列队
     * 
     * @param ouid
     * @param batchcode
     * @return
     */
    @NativeQuery
    public List<String> findBatchCodeByOuidDetial1(@QueryParam("ouid") Long ouid, SingleColumnRowMapper<String> singleColumnRowMapper);

    /**
     * 根据仓库ID批次号查询特殊列队
     * 
     * @param ouid
     * @param batchcode
     * @return
     */
    @NativeQuery
    public List<String> findBatchCodeByOuidDetial2(@QueryParam("ouid") Long ouid, SingleColumnRowMapper<String> singleColumnRowMapper);

    /**
     * 根据仓库ID批次号查询正常销售列队
     * 
     * @param ouid
     * @param batchcode
     * @return
     */
    @NativeQuery
    public List<String> findBatchCodeByOuidDetial(@QueryParam("ouid") Long ouid, SingleColumnRowMapper<String> singleColumnRowMapper);



    /**
     * 根据仓库ID批次号查询列队
     * 
     * @param ouid
     * @param batchcode
     * @return
     */
    @NativeQuery
    public List<String> findChannelListByOuid(@QueryParam("ouid") Long ouid, SingleColumnRowMapper<String> singleColumnRowMapper);

    /**
     * 查询当前时间过仓失败单据总数
     * 
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    public Integer findErrorCount(SingleColumnRowMapper<Integer> singleColumnRowMapper);

    /**
     * 根据ID删除队列信息
     * 
     * @param id
     * @return
     */
    @NativeUpdate
    public int deleteQueuesta(@QueryParam("id") Long id);

    @NamedQuery
    public List<QueueSta> findQueueStaSendMq();

    /**
     * 根据超时时间获取创建作业单超时的单据数
     * 
     * @param timeOut
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    public Integer findCreateStaTimeOutCount(@QueryParam("timeOut") Integer timeOut, SingleColumnRowMapper<Integer> singleColumnRowMapper);

    /**
     * 根据超时时间获取创建作业单超时的单据列表
     * 
     * @param timeOut
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    public List<String> findOrdercodeList(@QueryParam("timeOut") Integer timeOut, SingleColumnRowMapper<String> singleColumnRowMapper);

    /**
     * 方法说明：(根据参数)查询 QueueStaCommand
     * 
     * @author LuYingMing
     * @date 2016年7月25日 下午2:12:07
     * @param start
     * @param pageSize
     * @param m
     * @param rowMapper
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<QueueStaCommand> findQueueStaByParams(int start, int pageSize, @QueryParam Map<String, Object> m, RowMapper<QueueStaCommand> rowMapper);

    /**
     * 方法说明：重置为0
     * 
     * @author LuYingMing
     * @date 2016年7月25日 下午5:22:03
     * @param queueStaId
     */
    @NativeUpdate
    void updateZeroByErrorCode(@QueryParam("queueStaId") Long queueStaId);

    /**
     * 方法说明：重置为99
     * 
     * @author LuYingMing
     * @date 2016年7月25日 下午5:22:03
     * @param queueStaId
     */
    @NativeUpdate
    void update99ByErrorCode(@QueryParam("queueStaId") Long queueStaId, @QueryParam("batchNo") String batchNo);

    @NativeUpdate
    void setBeginFlagForOrderPac(@QueryParam("rowlimit") int rowlimit, @QueryParam("ouId") Long ouId);

    /**
     * 查询所有可创的单子
     * 
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<Long> getAllOrderToCreatePac(SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * 查询是否所有的单子都打上了能否创单标识
     * 
     * @param singleColumnRowMapper
     */
    @NativeQuery
    Boolean getIsAllHaveFlagPac(SingleColumnRowMapper<Boolean> singleColumnRowMapper);


    /**
     * 查询所有状态为5可创的单子
     * 
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<Long> getAllOrderToCreatePac2(SingleColumnRowMapper<Long> singleColumnRowMapper);
}
