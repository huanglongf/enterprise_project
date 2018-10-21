/**
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

package com.jumbo.dao.baseinfo;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.baseinfo.SkuBarcode;
import com.jumbo.wms.model.command.SkuBarcodeCommand;

@Transactional
public interface SkuBarcodeDao extends GenericEntityDao<SkuBarcode, Long> {


    @NamedQuery
    List<SkuBarcode> findByMainBarcode(@QueryParam("mainBarcode") String mainBarcode, @QueryParam("customerId") Long customerId);

    @NativeQuery
    List<SkuBarcodeCommand> findByBarcode1(@QueryParam("mainBarcode") String mainBarcode, @QueryParam("customerId") Long customerId, RowMapper<SkuBarcodeCommand> rowMapper);

    /**
     * 通过主条码列表查询副条码
     * 
     * @param barcodes
     * @param customerId
     * @return
     */
    @NativeQuery(model = SkuBarcodeCommand.class)
    List<SkuBarcodeCommand> findByMainBarcode(@QueryParam("barcodes") List<String> barcodes, @QueryParam("customerId") Long customerId);

    @NamedQuery
    List<SkuBarcode> findBySkuId(@QueryParam("skuId") Long skuId);

    @NativeUpdate
    void deleteBySkuId(@QueryParam("skuId") Long skuId);

    @NamedQuery
    SkuBarcode findByBarCode(@QueryParam("barcode") String barcode, @QueryParam("customerId") Long customerId);

    @NativeUpdate
    void createConverseBarCodes();

    @NativeQuery
    List<SkuBarcodeCommand> findSkuBarcodeForPda(@QueryParam("plcode") String plcode, RowMapper<SkuBarcodeCommand> r);

    @NativeQuery
    List<SkuBarcodeCommand> findEdwTbiInvSkuBarcode(RowMapper<SkuBarcodeCommand> rowMapper);
}
