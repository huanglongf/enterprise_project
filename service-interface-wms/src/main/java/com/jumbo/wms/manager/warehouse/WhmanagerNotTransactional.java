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
package com.jumbo.wms.manager.warehouse;

import java.io.File;
import java.util.List;
import java.util.Map;


import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.command.NikeVMIInboundCommand;


public interface WhmanagerNotTransactional extends BaseManager {

    void importCreateNikeVMIInbound(File staFile, Long ouid, User creator,Long shopId,Map<String, List<NikeVMIInboundCommand>> errorList,Map<String, List<NikeVMIInboundCommand>> successList);
    
    
}
