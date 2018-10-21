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

import com.jumbo.wms.model.BaseResponse;

/**
 * 
 * @author lzb
 * 
 */
public class EspDeliveryResponse extends BaseResponse {

    private static final long serialVersionUID = 2985681726021532339L;

    private List<EspDeliveryCommand> data;

    public List<EspDeliveryCommand> getData() {
        return data;
    }

    public void setData(List<EspDeliveryCommand> data) {
        this.data = data;
    }
    
}
