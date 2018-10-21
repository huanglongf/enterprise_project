package com.jumbo.dao.vmi.espData;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.espData.ESPReceiving;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;


@Transactional
public interface ESPReceivingDao extends GenericEntityDao<ESPReceiving, Long> {
    /**
     * 更新收货反馈数据至待处理
     * 
     * @return
     */
    @NativeUpdate
    int updateReceivingDataToWriting();
    
    @NativeUpdate
    int updateReceivingDataForWriting();

    /**
     * 更新收货反馈数据至处理完成
     * 
     * @return
     */
    @NativeUpdate
    int updateReceivingDataToFinish(@QueryParam("seq") String seq);

    @NativeQuery
    List<ESPReceiving> receivingList(RowMapper<ESPReceiving> r);

    /**
     * 更新待处理数据发票信息
     * 
     * @return
     */
    @NativeUpdate
    int updateReceivingDataWritingInvoiceNumber();



    /**
     * 查询所有待处理数据
     * 
     * @return
     */
    @NativeQuery(model = ESPReceiving.class)
    List<ESPReceiving> findAllWrintingData();

    /**
     * 查询反馈头序列
     * 
     * @param r
     * @return
     */
    @NativeQuery
    String findHeaderSeq(SingleColumnRowMapper<String> r);
    
    /**
     * 查找所有无发票系数未反馈的数据
     *@return List<ESPReceiving> 
     *@throws
     */
    @NativeQuery(model = ESPReceiving.class)
    List<ESPReceiving> findAllNoneInvoiceAndNotFeedbackReceivingData();
    
    @NativeQuery(model = ESPReceiving.class)
    List<ESPReceiving> findAllReceivingDataByOrderNo(@QueryParam("orderNo") String orderNo);
    
    /**
     * 查找所有未维护发票反馈失败的指令
     *@return List<ESPReceiving> 
     *@throws
     */
    @NativeQuery(model = ESPReceiving.class)
    List<ESPReceiving> findAllNotFeedbackReceivingData();
}
