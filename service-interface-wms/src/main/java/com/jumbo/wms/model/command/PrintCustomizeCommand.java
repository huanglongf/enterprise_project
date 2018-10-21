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
package com.jumbo.wms.model.command;

import com.jumbo.wms.model.print.PrintCustomize;

public class PrintCustomizeCommand extends PrintCustomize {

    /**
     * 
     */
    private static final long serialVersionUID = -4491752078567203472L;

    private Integer printTypeInteger;

    public Integer getPrintTypeInteger() {
        return printTypeInteger;
    }

    public void setPrintTypeInteger(Integer printTypeInteger) {
        this.printTypeInteger = printTypeInteger;
    }



}
