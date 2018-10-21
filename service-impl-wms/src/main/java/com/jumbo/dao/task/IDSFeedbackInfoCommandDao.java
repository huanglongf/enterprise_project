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
package com.jumbo.dao.task;

import java.util.Date;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.task.IDSFeedbackInfoCommand;

import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

@Transactional
public interface IDSFeedbackInfoCommandDao extends GenericEntityDao<IDSFeedbackInfoCommand, Long> {

    @NativeUpdate
    void saveASNByMIBO(@QueryParam(value = "status") Integer status);


    @NativeUpdate
    void saveOutBoundRECHD(@QueryParam(value = "interfaceActionFlag") String interfaceActionFlag, @QueryParam(value = "receiptKey") String receiptKey, @QueryParam(value = "externReceiptKey") String externReceiptKey,
            @QueryParam(value = "receiptDate") Date receiptDate, @QueryParam(value = "recType") String recType, @QueryParam(value = "facility") String facility);

    @NativeUpdate
    void saveOutBoundRECDT(@QueryParam(value = "interfaceActionFlag") String interfaceActionFlag, @QueryParam(value = "externReceiptKey") String externReceiptKey, @QueryParam(value = "externLineNo") String externLineNo,
            @QueryParam(value = "sku") String sku, @QueryParam(value = "qtyExpected") String qtyExpected, @QueryParam(value = "qtyReceived") String qtyReceived);

    @NativeUpdate
    void saveOutBoundSHPHD(@QueryParam(value = "interfaceActionFlag") String interfaceActionFlag, @QueryParam(value = "externReceiptKey") String externReceiptKey, @QueryParam(value = "consigneeKey") String consigneeKey,
            @QueryParam(value = "type") String type, @QueryParam(value = "facility") String facility);

    @NativeUpdate
    void saveOutBoundSHPDT(@QueryParam(value = "interfaceActionFlag") String interfaceActionFlag, @QueryParam(value = "externReceiptKey") String externReceiptKey, @QueryParam(value = "externLineNo") String externLineNo,
            @QueryParam(value = "sku") String sku, @QueryParam(value = "shippedQty") String shippedQty);

    @NativeUpdate
    void saveOutBoundADJHD(@QueryParam(value = "interfaceActionFlag") String interfaceActionFlag, @QueryParam(value = "adjustmentKey") String adjustmentKey, @QueryParam(value = "effectiveDate") Date effectiveDate,
            @QueryParam(value = "facility") String facility, @QueryParam(value = "adjustmentType") String adjustmentType);

    @NativeUpdate
    void saveOutBoundADJDT(@QueryParam(value = "interfaceActionFlag") String interfaceActionFlag, @QueryParam(value = "adjustmentKey") String adjustmentKey, @QueryParam(value = "adjustmentLineNumber") String adjustmentLineNumber,
            @QueryParam(value = "sku") String sku, @QueryParam(value = "reasonCode") String reasonCode, @QueryParam(value = "qty") String qty);

    @NativeUpdate
    void updateStatus(@QueryParam(value = "origStatus") Integer origStatus, @QueryParam(value = "newStatus") Integer newStatus, @QueryParam(value = "tbName") String tbName);


    @NativeUpdate
    void updateStatusBySHPHD(@QueryParam(value = "status") Integer newStatus);

    @NativeUpdate
    void updateStatusByADJHD(@QueryParam(value = "status") Integer newStatus);

    @NativeUpdate
    void updateStatusByRECHD(@QueryParam(value = "status") Integer newStatus);

    @NativeUpdate
    void saveInBoundORDInfo(@QueryParam(value = "staId") Long staId);

    @NativeUpdate
    void saveInBoundORDInfoNike(@QueryParam(value = "staId") Long staId, @QueryParam(value = "source") String source);

    @NativeUpdate
    void saveInBoundORDInfoConverse(@QueryParam(value = "staId") Long staId, @QueryParam(value = "source") String source);

    @NativeUpdate
    void saveInBoundORDInfoPf(@QueryParam(value = "staId") Long staId);

    @NativeUpdate
    void saveInBoundORDInfoNike(@QueryParam(value = "staId") Long staId);



}
