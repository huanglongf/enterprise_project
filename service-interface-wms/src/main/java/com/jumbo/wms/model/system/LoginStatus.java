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

package com.jumbo.wms.model.system;


public enum LoginStatus {
    LOGIN_SUCCESS("SUCCESS"), LOGIN_FAILURE_BAD_CREDENTIAL("FAILURE_BAD_CREDENTIAL"), LOGIN_FAILURE_CREDENTIAL_EXPIRED("FAILURE_CREDENTIAL_EXPIRED"), LOGIN_FAILURE_USER_DISABLED("FAILURE_USER_DISABLED"), LOGIN_FAILURE_USER_EXPIRED("FAILURE_USER_EXPIRED"), LOGIN_FAILURE_USER_LOCKED(
            "FAILURE_USER_LOCKED"), LOGIN_FAILURE_OTHERS("FAILURE_OTHERS"), LOGOUT_SUCCESS("LOOUT_SUCCESS"), SESSION_TIMEOUT("SESSION_TIMEOUT");

    private String value;

    LoginStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
