/**
 * EventObserver * Copyright (c) 2010 Jumbomart All Rights Reserved.
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
package com.jumbo.wms.daemon;

import java.util.List;

import com.jumbo.pac.manager.extsys.wms.rmi.model.OperationBillLine;
import com.jumbo.wms.manager.vmi.ext.ExtParam;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.vmi.Default.VmiRsnLineDefault;
import com.jumbo.wms.model.warehouse.BiChannelCommand;
import com.jumbo.wms.model.warehouse.InventoryCommand;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StvLine;
import com.jumbo.wms.model.warehouse.StvLineCommand;



public interface VmiDefaultInterface {

    void generateInboundStaSetHeadDefault(String slipCode, StockTransApplication head);

    void generateInboundStaSetSlipCodeOwner(BiChannelCommand shop, StockTransApplication sta);

    void generateReceivingWhenShelv(ExtParam ext, VmiRsnLineDefault v);

    void generateReceivingWhenClose(Long staid);

    void generateSaveSkuBatch(String batchCode, StockTransApplication sta, List<StvLine> stvlineList);

    void transferOmsOutBound(OperationBillLine line, StvLineCommand sc);

    void generateVmiInboundStaLine(StockTransApplication sta, StaLine line, String upc) throws Exception;

    void generateInsertVmiInventory(ExtParam ext);

    boolean importForBatchReceiving();

    void importForBatchReceivingSaveStvLine(StvLine stvLine, StockTransApplication sta, Sku sku);

    void generateVmiOutBound(ExtParam ext);

    void generateVmiInBound(ExtParam ext);

    List<InventoryCommand> findVmiInventoryByOwner(List<String> ownerList);
}
