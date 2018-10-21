package com.jumbo.dao.vmi.converseYxData;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.converseData.ConverseYxStockin;

/**
 * Converse永兴数据访问逻辑层
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
public interface ConverseYxStockinDao extends GenericEntityDao<ConverseYxStockin, Long> {
    /**
     * 查询所有需要创单的Converse永兴单据
     * 
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<String> findAllDataNeedToCreate(SingleColumnRowMapper<String> singleColumnRowMapper);

    /**
     * 根据前置单据号（箱号）查询一条数据，用于创建sta 头信息
     * 
     * @param slipCode
     * @return
     */
    @NamedQuery
    ConverseYxStockin getOneRecordBySlipCode(@QueryParam("slipCode") String slipCode);

    /**
     * 根据前置单据号（箱号）查询入库明细
     * 
     * @param slipCode
     * @return
     */
    @NamedQuery
    List<ConverseYxStockin> getByReferenceNo(@QueryParam("slipCode") String slipCode);

    @NativeUpdate
    void saveConverseYxStockinData(@QueryParam("cartonNumber") String cartonNumber, @QueryParam("receiveDate") String receiveDate, @QueryParam("fromLocation") String fromLocation, @QueryParam("toLocation") String toLocation,
            @QueryParam("upc") String upc, @QueryParam("quantity") Long quantity, @QueryParam("lineSequenceNo") String lineSequenceNo, @QueryParam("transferNo") String transferNo, @QueryParam("staId") Long staId);

    @NativeUpdate
    void deleteNewData();

    @NativeUpdate
    void updateNewDataCanUse();

}
