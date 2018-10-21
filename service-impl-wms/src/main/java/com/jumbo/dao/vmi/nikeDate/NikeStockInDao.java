/**
 * DimensionImpl.java com.erry.model.it.impl
 * 
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */

package com.jumbo.dao.vmi.nikeDate;

import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.nikeData.NikeVmiStockInCommand;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

@Transactional
public interface NikeStockInDao extends GenericEntityDao<NikeVmiStockInCommand, Long> {


    @NativeQuery
    List<NikeVmiStockInCommand> findNikeVmiStockInByRefNo(@QueryParam("refNo") String refNo, @QueryParam("brand") String brand, RowMapper<NikeVmiStockInCommand> rowMapper);

    @NativeUpdate
    void updateStaIdByRefNo(@QueryParam("staId") long staId, @QueryParam("staLineId") long staLineId, @QueryParam("itemEanUpcCode") String itemEanUpcCode, @QueryParam("referenceNo") String referenceNo, @QueryParam("brand") String brand);

    /**
     * 插入T_NIKE_VMI_STOCKIN_RECODE
     */
    @NativeUpdate
    void insertNikeVmiStockInRecode(@QueryParam("staId") long staId, @QueryParam("referenceNo") String referenceNo);


    @NativeUpdate
    void saveNikeVMIStockInBoundSql(@QueryParam("transferPrefix") String transferPrefix, @QueryParam("referenceNo") String referenceNo, @QueryParam("receiveDate") String receiveDate, @QueryParam("fromLocation") String fromLocation,
            @QueryParam("toLocation") String toLocation, @QueryParam("cs2000ItemCode") String cs2000ItemCode, @QueryParam("colorCode") String colorCode, @QueryParam("sizeCode") String sizeCode, @QueryParam("itemEanUpcCode") String itemEanUpcCode,
            @QueryParam("quantity") Long quantity, @QueryParam("lineSequenceNo") String lineSequenceNo, @QueryParam("totalLineSequenceNo") String totalLineSequenceNo, @QueryParam("sapCarton") String sapCarton, @QueryParam("sapDnNo") String sapDnNo,
            @QueryParam("status") Integer status);

    /**
     * saveNikeVMIStockInBoundSqlBrand
     */

    @NativeUpdate
    void saveNikeVMIStockInBoundSqlBrand(@QueryParam("transferPrefix") String transferPrefix, @QueryParam("referenceNo") String referenceNo, @QueryParam("receiveDate") String receiveDate, @QueryParam("fromLocation") String fromLocation,
            @QueryParam("toLocation") String toLocation, @QueryParam("cs2000ItemCode") String cs2000ItemCode, @QueryParam("colorCode") String colorCode, @QueryParam("sizeCode") String sizeCode, @QueryParam("itemEanUpcCode") String itemEanUpcCode,
            @QueryParam("quantity") Long quantity, @QueryParam("lineSequenceNo") String lineSequenceNo, @QueryParam("totalLineSequenceNo") String totalLineSequenceNo, @QueryParam("sapCarton") String sapCarton, @QueryParam("sapDnNo") String sapDnNo,
            @QueryParam("status") Integer status, @QueryParam("brand") String brand, @QueryParam("qualifier") String qualifier, @QueryParam("lotnumber") String lotnumber, @QueryParam("createTime") Date createTime);


    /**
     * 根据唯一单号查询明细
     * 
     * @param referenceNo
     * @return
     */
    @NamedQuery
    List<NikeVmiStockInCommand> getByReferenceNo(@QueryParam("referenceNo") String referenceNo);

    /**
     * 根据唯一单号查询明细(只获取1行)
     * 
     * @param referenceNo
     * @return
     */
    @NamedQuery
    NikeVmiStockInCommand getOneByReferenceNo(@QueryParam("referenceNo") String referenceNo);

    /***
     * 
     * @param sequene
     * @param beanPropertyRowMapperExt
     * @return
     */
    @NativeQuery
    List<String> findSequencesIsExist(@QueryParam("sequene") String sequene, SingleColumnRowMapper<String> beanPropertyRowMapperExt);


    @NativeQuery
    List<String> findNikeVmiStockIn(SingleColumnRowMapper<String> singleColumnRowMapper);

    @NativeQuery
    List<String> findNikeVmiStockInBrand(SingleColumnRowMapper<String> singleColumnRowMapper);

    @NativeUpdate
    void deleteTodoStatus(@QueryParam("status") Integer status);

    /**
     * nike hub 收货 deleteTodoStatusBrand
     * 
     * @param status
     */
    @NativeUpdate
    void deleteTodoStatusBrand(@QueryParam("status") Integer status);

    /**
     * nike hub 收货 updateNikeVmiStockInCommandStatusBrand
     * 
     * @param status
     * @param rawStatus
     */

    @NativeUpdate
    void updateNikeVmiStockInCommandStatusBrand(@QueryParam("status") Integer status, @QueryParam("rawStatus") Integer rawStatus);

    @NativeUpdate
    void updateNikeVmiStockInCommandStatus(@QueryParam("status") Integer status, @QueryParam("rawStatus") Integer rawStatus);

    @NativeUpdate
    void updateStatusToFinishByRefNo(@QueryParam("refNo") String refNo);

    @NativeQuery
    List<NikeVmiStockInCommand> findNikeVmiStockInByDeclaration(@QueryParam("nikeVmiCode") String nikeVmiCode, RowMapper<NikeVmiStockInCommand> rowMapper);

}
