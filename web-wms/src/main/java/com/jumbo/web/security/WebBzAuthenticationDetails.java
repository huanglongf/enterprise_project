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

package com.jumbo.web.security;

import javax.servlet.http.HttpServletRequest;

public class WebBzAuthenticationDetails extends org.springframework.security.web.authentication.WebAuthenticationDetails {

    /**
	 * 
	 */
    private static final long serialVersionUID = 4416822596467335637L;

    private String userAgent;

    public WebBzAuthenticationDetails(HttpServletRequest request) {
        super(request);
        populateAdditionalInformation(request);
    }

    private void populateAdditionalInformation(HttpServletRequest request) {
        this.userAgent = request.getHeader("User-Agent");
    }

    public String getUserAgent() {
        return userAgent;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString() + ": ");
        sb.append("UserAgent:" + this.getUserAgent());
        return sb.toString();
    }
}
