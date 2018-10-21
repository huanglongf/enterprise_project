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
package com.jumbo.wms.manager.mongodb;

import java.util.List;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.mongodb.MongoDBYtoBigWord;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;

/**
 * @author lichuan
 * 
 */
public interface MongoDBManager extends BaseManager {

    /**
     * 初始化圆通大头笔信息到MongoDB void
     * 
     * @throws
     */
    void initYtoBigWordIntoMongoDB();

    /**
     * 根据目的省和目的地查询模糊查找打包编码
     * 
     * @param province
     * @param district
     * @return MongoDBYtoBigWord
     * @throws
     */
    MongoDBYtoBigWord findPackNo(String province, String district);
    
    /**
     * 根据目的省和目的地查询模糊匹配所有打包编码
     *@param province
     *@param district
     *@return List<MongoDBYtoBigWord> 
     *@throws
     */
    List<MongoDBYtoBigWord> matchingAllPackNo(StaDeliveryInfo sd);
    
    /**
     * 根据目的省和目的地查询模糊匹配打包编码
     *@param sd
     *@return MongoDBYtoBigWord 
     *@throws
     */
    MongoDBYtoBigWord matchingPackNo(StaDeliveryInfo sd);

}
