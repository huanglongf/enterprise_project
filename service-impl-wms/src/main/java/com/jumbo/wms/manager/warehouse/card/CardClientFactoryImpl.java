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

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

/**
 * @author lichuan
 * 
 */
@Service("cardClientFactory")
public class CardClientFactoryImpl implements CardClientFactory {
    @Resource(name = "defaultClient")
    private CardClientManager defaultClient;
    @Resource(name = "starbucksZhxClient")
    private CardClientManager starbucksZhxClient;
    @Resource(name = "starbucksHpClient")
    private CardClientManager starbucksHpClient;
    @Resource(name = "nikeClient")
    private CardClientManager nikeClient;
    @Resource(name = "starbucksBenClient")
    private CardClientManager starbucksBenClient;// 星巴克本


    /**
     * 获取接口客户端
     */
    @Override
    public CardClientManager getClient(Integer interfaceType) {
        if (DEFAULT_CLIENT == interfaceType) {
            return defaultClient;
        } else if (STARBUCKS_ZHX_CLIENT == interfaceType) {
            return starbucksZhxClient;
        } else if (STARBUKCS_HP_CLIENT == interfaceType) {
            return starbucksHpClient;
        } else if (NIKE_CLIENT == interfaceType) {
            return nikeClient;
        } else if (STARBUKCS_BEN_CLIENT == interfaceType) {
            return starbucksBenClient;
        } else {
            return defaultClient;
        }
    }

}
