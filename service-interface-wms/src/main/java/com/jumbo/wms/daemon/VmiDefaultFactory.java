/**
 * EventObserver * Copyright (c) 2010 Jumbomart All Rights Reserved.
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
package com.jumbo.wms.daemon;



public interface VmiDefaultFactory {

    String DEFAULT_JOHNSON = "johnson";

    String DEFAULT_STARBUCK = "starbucks";

    String DEFAULT_MARKWINS = "markwins";

    String DEFAULT_SPEEDO = "speedo";

    String DEFAULT_PAULFRANK = "paulFrank";

    String DEFAULT_MK = "MichaelKors";

    String DEFAULT_CK = "CK";

    String DEFAULT_GUCCI = "gucci";

    String DEFAULT_RALPH = "07300";

    VmiDefaultInterface getVmiDefaultInterface(String code);
}
