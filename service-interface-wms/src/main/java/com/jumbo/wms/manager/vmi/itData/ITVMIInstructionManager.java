/**
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
package com.jumbo.wms.manager.vmi.itData;

import java.util.List;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.vmi.itData.ITVMIInstruction;
import com.jumbo.wms.model.vmi.itData.VMIInstructionType;

public interface ITVMIInstructionManager extends BaseManager {

    void importITStockFile(String fileDir, String bakDir);

    List<String> getNotOperaterITDNFileName(VMIInstructionType type);

    List<ITVMIInstruction> getInstructionsByFileName(String fileName);

    void generateSTAFromDNInstruction(String fileName);

    void generateInventoryCheckFromITSA(String fileName);

    void generateITSkuInstruction(String fileName);
}
