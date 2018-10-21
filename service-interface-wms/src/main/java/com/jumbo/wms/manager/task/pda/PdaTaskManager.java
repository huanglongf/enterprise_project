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

package com.jumbo.wms.manager.task.pda;


import java.util.List;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.pda.PdaOrder;
import com.jumbo.wms.model.pda.PdaOrderLine;
import com.jumbo.wms.model.pda.PdaOrderLineSn;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransVoucher;

/**
 * @author wanghua
 * 
 */
public interface PdaTaskManager extends BaseManager {

    void inboundSheleves(StockTransApplication sta, StockTransVoucher stv, PdaOrder pdaOrder, List<PdaOrderLine> pdaLineList, User creator);

    StockTransVoucher inboundConfirm(StockTransApplication sta, StockTransVoucher stv, PdaOrder pdaOrder);

    String checkSkuSn(String id);

    List<PdaOrderLineSn> findPdaLineSnList(String id);

    void editPdaOrderSkuSn(String id, String sn);

    void deletePdaOrderSkuSn(String id);

    void addPdaOrderSkuSn(String id, String sn);

    List<StockTransApplication> findStaBySlipCode(String silpCode);
}
