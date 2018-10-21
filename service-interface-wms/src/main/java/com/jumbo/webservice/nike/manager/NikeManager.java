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
package com.jumbo.webservice.nike.manager;

import java.util.List;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.vmi.nikeData.NikeVmiStockInHub;

public interface NikeManager extends BaseManager {
	

    void uploadInboundNikeDataToHub();

    void uploadInboundNikeDataToHub2();


	void generateRsnData();

    void generateStaByRefNo(String refNo, String brand2);
    
    void inBoundTransferInBoundWriteToFile(String localDir);

    void transferOutReceiveWriteToFile(String localDir);

    void vmiReturnReceiveWriteToFile(String localDir);
    
    /**
     * 库存调整 反馈
     * @param localDir
     */
    void invCheckReceiveWriteToFile(String localDir);

    boolean inBoundreadFileIntoDB(String localFileDir, String bakFileDir, String fileStart);

    /**
     * nike收货hub
     */
    boolean nikeInboundHub(List<NikeVmiStockInHub> nikeVmiStockInHub);


    /**
     * 获取调整行数据的文件编码
     * 
     * @return
     */
    String getFileCode();
}
