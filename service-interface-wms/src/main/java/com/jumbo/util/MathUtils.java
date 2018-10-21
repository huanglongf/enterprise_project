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

package com.jumbo.util;

import java.io.Serializable;
import java.math.BigDecimal;

public class MathUtils implements Serializable{
    
    private static final long serialVersionUID = -5270599743045857717L;

    public static int div(BigDecimal number, BigDecimal unit) {
        if (number == null || unit == null || unit.equals(new BigDecimal(0))) {throw new IllegalArgumentException();}
        BigDecimal value = number.divide(unit, 2, BigDecimal.ROUND_HALF_UP);
        int intValue = value.intValue();
        return value.compareTo(new BigDecimal(intValue)) > 0 ? (intValue + 1) : intValue;
    }
}
