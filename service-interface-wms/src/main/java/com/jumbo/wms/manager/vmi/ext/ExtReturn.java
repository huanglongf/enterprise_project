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
package com.jumbo.wms.manager.vmi.ext;

import java.io.Serializable;
import java.util.List;

import com.jumbo.wms.model.vmi.Default.VmiAsnLineCommand;
import com.jumbo.wms.model.warehouse.BiChannelCommand;

/**
 * @author lichuan
 * 
 */
public class ExtReturn implements Serializable {

    private static final long serialVersionUID = -4287743586309169814L;

    private List<VmiAsnLineCommand> valList;
    private BiChannelCommand shopCmd;

    public List<VmiAsnLineCommand> getValList() {
        return valList;
    }

    public void setValList(List<VmiAsnLineCommand> valList) {
        this.valList = valList;
    }

    public BiChannelCommand getShopCmd() {
        return shopCmd;
    }

    public void setShopCmd(BiChannelCommand shopCmd) {
        this.shopCmd = shopCmd;
    }

}
