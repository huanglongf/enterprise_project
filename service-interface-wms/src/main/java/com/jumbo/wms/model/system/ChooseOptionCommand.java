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


public class ChooseOptionCommand extends ChooseOption {
    /**
	 * 
	 */
    private static final long serialVersionUID = -2032867288095312286L;
    private String optionStatusText;
    private int optionStatus;
    private String isAvailables;
    public int getOptionStatus() {
        return optionStatus;
    }

    
    public void setOptionStatus(int optionStatus) {
        this.optionStatus = optionStatus;
    }

    public String getOptionStatusText() {
        return optionStatusText;
    }

    public void setOptionStatusText(String optionStatusText) {
        this.optionStatusText = optionStatusText;
    }
    public String getIsAvailables() {
        return isAvailables;
    }


    public void setIsAvailables(String isAvailables) {
        this.isAvailables = isAvailables;
    }
    
}
