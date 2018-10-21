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
package com.jumbo.wms.manager.vmi.philipsData;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.vmi.philipsData.PhilipsCustomerReturn;
import com.jumbo.wms.model.vmi.philipsData.PhilipsInboundDelivery;
import com.jumbo.wms.model.vmi.philipsData.PhilipsOutboundDelivery;
import com.jumbo.wms.model.warehouse.StockTransApplication;

public interface PhilipsParseDataManager extends BaseManager {

    void receivePhilipsMasterByMq(String message);

    void receivePhilipsInboundByMq(String message);

    void receivePhilipsOutboundByMq(String message);

    void receivePhilipsCusReturnByMq(String message);

    StockTransApplication createPhilipsSalesSta(StockTransApplication sta, PhilipsOutboundDelivery outbound, String vmiCode);

    StockTransApplication createPhilipsReturnSta(StockTransApplication sta, PhilipsCustomerReturn cr, String vmiCode);

    String getLocationByInvID(Long invId);

    void generateInboundSta(PhilipsInboundDelivery inboundDel, String vmiCode);
}
