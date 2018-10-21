package com.jumbo.dao.vmi.espData;

import java.math.BigDecimal;
import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.espData.ESPDelivery;
import com.jumbo.wms.model.vmi.espData.ESPDeliveryCommand;

@Transactional
public interface ESPDeliveryDao extends GenericEntityDao<ESPDelivery, Long> {
    @NativeQuery
    Long getDeliverySequence(RowMapper<Long> rowMapper);

    @NativeQuery
    BigDecimal getDeliveryNo(RowMapper<BigDecimal> rowMapper);

    @NativeQuery(model = ESPDelivery.class)
    List<ESPDelivery> findDeliveryList();

    @NativeUpdate
    void updateStatusToDoing();

    @NativeUpdate
    void updateStatusToFinished(@QueryParam("seqno") String seq);
    
    /**
     * 查已存在反馈信息数量
     * 
     * @param rowMapper
     * @return Long
     * @throws
     */
    @NativeQuery
    Long findDeliveryCountByStaCode(@QueryParam("staCode") String staCode, SingleColumnRowMapper<Long> r);
    
    /**
     * 根据staCode更新反馈信息状态
     * 
     * @param status
     * @param staCode void
     * @throws
     */
    @NativeUpdate
    void updateStatusByStaCode(@QueryParam("status") Integer status, @QueryParam("staCode") String staCode, @QueryParam("batchCode") String batchCode);


    /**
     * 根据staCode更新反馈信息状态2
     * 
     * @param status
     * @param staCode void
     * @throws
     */
    @NativeUpdate
    void updateStatusByStaCode2(@QueryParam("sequenceNumber") String sequenceNumber, @QueryParam("staCode") String staCode, @QueryParam("batchCode") String batchCode, @QueryParam("batchCode2") String batchCode2);

    /**
     * 根据staCode更新反馈信息状态3
     * 
     * @param status
     * @param staCode void
     * @throws
     */
    @NativeUpdate
    void updateStatusByStaCode3(@QueryParam("status") Integer status, @QueryParam("staCode") String staCode, @QueryParam("batchCode") String batchCode, @QueryParam("remark") String remark, @QueryParam("remark2") String remark2);


    /**
     * 关单反馈成功
     * 
     * @param status
     * @param staCode void
     * @throws
     */
    @NativeUpdate
    void updateStatusByClosedStaCode(@QueryParam("status") Integer status, @QueryParam("staCode") String staCode, @QueryParam("batchCode") String batchCode);

    /**
     * 获取一批待反馈数据
     * 
     * @param staCode
     * @param rowMapper
     * @return List<ESPDeliveryCommand>
     * @throws
     */
    @NativeQuery
    List<ESPDeliveryCommand> findDeliveryDatasGroupByStaCode(RowMapper<ESPDeliveryCommand> rowMapper);
    
    /**
     * 获取一批待反馈数据2
     * 
     * @param staCode
     * @param rowMapper
     * @return List<ESPDeliveryCommand>
     * @throws
     */
    @NativeQuery
    List<ESPDeliveryCommand> findDeliveryDatasGroupByStaCode2(RowMapper<ESPDeliveryCommand> rowMapper);

    /**
     * 获取一批待反馈数据
     * 
     * @param rowMapper
     * @return List<ESPDeliveryCommand>
     * @throws
     */
    @NativeQuery
    List<ESPDeliveryCommand> findAllDeliveryDatasGroupByStaCode(@QueryParam("staType") Integer staType, RowMapper<ESPDeliveryCommand> rowMapper);
    
    /**
     * 获取一批待反馈数据2
     * 
     * @param rowMapper
     * @return List<ESPDeliveryCommand>
     * @throws
     */
    @NativeQuery
    List<ESPDeliveryCommand> findAllDeliveryDatasGroupByStaCode2(@QueryParam("staType") Integer staType, RowMapper<ESPDeliveryCommand> rowMapper);


    /**
     * 获取一批待反馈数据3
     * 
     * @param rowMapper
     * @return List<ESPDeliveryCommand>
     * @throws
     */
    @NativeQuery
    List<ESPDeliveryCommand> findAllDeliveryDatasGroupByStaCode3(@QueryParam("staType") Integer staType, RowMapper<ESPDeliveryCommand> rowMapper);



    /**
     * 获取一批关单反馈数据
     * 
     * @param rowMapper
     * @return List<ESPDeliveryCommand>
     * @throws
     */
    @NativeQuery
    List<ESPDeliveryCommand> findCloseDeliveryDatasGroupByStaCode(RowMapper<ESPDeliveryCommand> rowMapper);
    
    /**
     * 获取一批关单反馈数据2
     * 
     * @param rowMapper
     * @return List<ESPDeliveryCommand>
     * @throws
     */
    @NativeQuery
    List<ESPDeliveryCommand> findCloseDeliveryDatasGroupByStaCode2(RowMapper<ESPDeliveryCommand> rowMapper);

    /**
     * 根据staCode获取待反馈数据
     * 
     * @param staCode
     * @param rowMapper
     * @return List<ESPDeliveryCommand>
     * @throws
     */
    @NativeQuery
    List<ESPDeliveryCommand> findDeliveryDatasByStaCode(@QueryParam("staCode") String staCode, @QueryParam("batchCode") String batchCode, RowMapper<ESPDeliveryCommand> rowMapper);
    
    @NativeQuery
    List<ESPDeliveryCommand> findDeliveryDatasByStaCode2(@QueryParam("staCode") String staCode, @QueryParam("batchCode") String batchCode, RowMapper<ESPDeliveryCommand> rowMapper);

    /**
     * 根据staCode获取待反馈数据
     * 
     * @param staCode
     * @return List<ESPDelivery>
     * @throws
     */
    @NamedQuery
    List<ESPDelivery> getDeliveryDatasByStaCode(@QueryParam("staCode") String staCode);
    
    /**
     * 收货关单数据反馈
     * 
     * @param staCode
     * @param rowMapper
     * @return List<ESPDeliveryCommand>
     * @throws
     */
    @NativeQuery
    List<ESPDeliveryCommand> findCloseDeliveryDatasByStaCode(@QueryParam("staCode") String staCode, @QueryParam("batchCode") String batchCode, RowMapper<ESPDeliveryCommand> rowMapper);
    
    /**
     * 收货关单数据反馈
     * 
     * @param staCode
     * @return List<ESPDelivery>
     * @throws
     */
    @NamedQuery
    List<ESPDelivery> getCloseDeliveryDatasByStaCode(@QueryParam("staCode") String staCode);
    
    /**
     * 获取反馈批次号
     * 
     * @param rowMapper
     * @return BigDecimal
     * @throws
     */
    @NativeQuery
    BigDecimal findBatchId(RowMapper<BigDecimal> rowMapper);
    
    /**
     * 查询Dispatch反馈头序列
     * 
     * @param r
     * @return
     */
    @NativeQuery
    String findDispatchHeaderSeq(SingleColumnRowMapper<String> r);
}
