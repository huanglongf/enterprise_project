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
package loxia.util;

import java.io.Serializable;
import java.util.List;

public abstract class CollectionsUtil implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5760703551032175754L;

	public static String joinList(List<String> list, String delim) {
        if (delim == null) {
            throw new IllegalArgumentException("The second param[delim] must not be null.");
        }
        StringBuffer sb = new StringBuffer();
        if (list == null || list.isEmpty()) return sb.toString();
        for (String each : list) {
            sb.append(each).append(delim);
        }
        sb.delete(sb.length() - delim.length(), sb.length());
        return sb.toString();
    }
}
