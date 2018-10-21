/**
 * Copyright (c) 2015 Jumbomart All Rights Reserved.
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
package com.jumbo.wms.model.warehouse.O2oEsprit;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.baozun.scm.baseservice.sac.command.JoinCodeCommand;
import com.baozun.scm.baseservice.sac.manager.CodeManager;

// @Service("codeGeneratorService")
public class CodeGeneratorService {

    @Autowired(required=false)
    private CodeManager codeManager;
    
    

    public String generateCode(String entity) {
        return this.codeManager.generateCode(NotifyConstants.CUSTOM_CODE, entity, null, null, null);
    }

    public List<String> generateCodes(String entity, int count) {
        return this.generateCodes(entity, null, count);
    }

    public List<String> generateCodes(String entity, String group, int count) {
        JoinCodeCommand command = this.codeManager.generateCodeList(NotifyConstants.CUSTOM_CODE, entity, group, null, null, count);

        return command.toArray();
    }
    
}
