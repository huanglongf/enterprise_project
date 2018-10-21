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

package com.jumbo.web.action;

import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.Role;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.authorization.UserRole;
import com.jumbo.web.security.UserDetails;

public class BaseProfileAction extends BaseAction implements UserDetailsAware {

    /**
	 * 
	 */
    private static final long serialVersionUID = -4615660231926948385L;

    protected UserDetails userDetails;

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public User getUser() {
        return userDetails == null ? null : userDetails.getUser();
    }

    public UserRole getCurrentUserRole() {
        return userDetails == null ? null : userDetails.getCurrentUserRole();
    }

    public OperationUnit getCurrentOu() {
        return userDetails == null ? null : userDetails.getCurrentOu();
    }

    public Role getCurrentRole() {
        return userDetails == null ? null : userDetails.getCurrentRole();
    }

}
