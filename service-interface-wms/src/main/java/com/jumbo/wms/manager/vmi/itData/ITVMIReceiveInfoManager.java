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
import com.jumbo.wms.model.vmi.itData.ITVMIReceiveInfo;
import com.jumbo.wms.model.vmi.itData.VMIITReceiveCommand;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.StockTransApplication;

public interface ITVMIReceiveInfoManager extends BaseManager {

    public List<Long> findNotFinishITDNStaIDs();

    public List<ITVMIReceiveInfo> findReceiveInfosByStaId(Long staId);

    public List<VMIITReceiveCommand> findITReceiveInfos(String vender, String toLocation);

    public void updateReceiveInfoStatusByVender(int status, String vender, String toLocation, int toStatus);

    public List<String> findInnerShopCodeFromReceiving();

    public void generateReceiveConfirmationFile(String fileDir, String date);

    void generateVMIReceiveInfoBySta(StockTransApplication sta);

    void generateVMIReceiveInfoByInvCk(InventoryCheck inv);

    void generateRtnWh(StockTransApplication sta, String fileDir, String date, String locNo);
}
