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
 */
package com.jumbo.webservice.ttk;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lichuan
 * 
 */
public class TransBigWordRequest {
    private String stie;
    private String cus;
    private String password;
    private List<TransBigWordSender> data = new ArrayList<TransBigWordSender>();

    public String getStie() {
        return stie;
    }

    public void setStie(String stie) {
        this.stie = stie;
    }

    public String getCus() {
        return cus;
    }

    public void setCus(String cus) {
        this.cus = cus;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<TransBigWordSender> getData() {
        return data;
    }

    public void setData(List<TransBigWordSender> data) {
        this.data = data;
    }


}
