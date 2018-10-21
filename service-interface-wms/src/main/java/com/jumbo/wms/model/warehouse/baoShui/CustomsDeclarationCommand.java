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

package com.jumbo.wms.model.warehouse.baoShui;


import java.util.List;

/**
 * 报关头表
 */

public class CustomsDeclarationCommand extends CustomsDeclaration {

    /**
     * 
     */
    private static final long serialVersionUID = -5582847862592609452L;
   

    private List<CustomsDeclarationLineCommand> cdlcList = null;


    public List<CustomsDeclarationLineCommand> getCdlcList() {
        return cdlcList;
    }


    public void setCdlcList(List<CustomsDeclarationLineCommand> cdlcList) {
        this.cdlcList = cdlcList;
    }


}