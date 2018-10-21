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
package com.jumbo.wms.manager.warehouse.card;

/**
 * @author lichuan
 * 
 */
public interface CardClientFactory {
    final Integer DEFAULT_CLIENT = null;
    final Integer STARBUCKS_ZHX_CLIENT = 3;//星巴克ZHX接口
    final Integer STARBUKCS_HP_CLIENT = 5;//星巴克HP接口
    final Integer NIKE_CLIENT = 6;// NIKE接口
    final Integer STARBUKCS_BEN_CLIENT = 7;// 星巴克HP接口



    CardClientManager getClient(Integer interfaceType);

}
