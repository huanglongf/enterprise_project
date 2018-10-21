package com.jumbo.dao.vmi.levis;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.converseData.ConverseYxStockin;
import com.jumbo.wms.model.vmi.levisData.LevisShoesStockin;
@Transactional
public interface LevisShoesStockinDao extends GenericEntityDao<LevisShoesStockin, Long>{
    
    /**
     * 查询所有需要创单的levis童装单据
     * 
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<String> findAllDataNeedToCreate(SingleColumnRowMapper<String> singleColumnRowMapper);
    
    @NativeUpdate
    void saveLevisShoesStockinData(@QueryParam("cartonNumber") String cartonNumber, @QueryParam("receiveDate") String receiveDate, @QueryParam("fromLocation") String fromLocation, @QueryParam("toLocation") String toLocation,
            @QueryParam("upc") String upc, @QueryParam("quantity") Long quantity, @QueryParam("lineSequenceNo") String lineSequenceNo, @QueryParam("transferNo") String transferNo, @QueryParam("staId") Long staId);
    
    @NativeUpdate
    void deleteNewData();

    @NativeUpdate
    void updateNewDataCanUse();
    
    /**
     * 根据前置单据号（箱号）查询入库明细
     * 
     * @param slipCode
     * @return
     */
    @NamedQuery
    List<LevisShoesStockin> getByReferenceNo(@QueryParam("slipCode") String slipCode);
    
    /**
     * 根据前置单据号（箱号）查询一条数据，用于创建sta 头信息
     * 
     * @param slipCode
     * @return
     */
    @NamedQuery
    LevisShoesStockin getOneRecordBySlipCode(@QueryParam("slipCode") String slipCode);
    

}
