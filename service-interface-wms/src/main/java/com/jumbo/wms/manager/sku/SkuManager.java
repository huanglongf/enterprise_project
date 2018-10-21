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
package com.jumbo.wms.manager.sku;


import java.io.UnsupportedEncodingException;
import java.util.List;

import loxia.dao.Pagination;

import com.jumbo.rmi.warehouse.RmiSku;
import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuBarcode;
import com.jumbo.wms.model.baseinfo.SkuModifyLog;
import com.jumbo.wms.model.command.SkuCommand;


public interface SkuManager extends BaseManager {
    void rmiUpdateSku(RmiSku rmiSku);

    SkuCommand getByBarcode1(String barCode);

    /**
     * 记录sku的变更
     */
    public SkuModifyLog refreshSkuModifyLog(Sku sku);

    public SkuCommand getByBarcode2(String barCode, String bi);

    List<SkuCommand> findSkuProvideInfoPickingDistrict(Long ouid, boolean b);

    List<SkuCommand> findSkuProvideInfoUnMaintain(Long ouid, boolean b);

    List<SkuCommand> unfinishedStaUnMaintainProductExport(Long ouid, Boolean boolean1, List<Integer> staTypes, List<Integer> staStatuses);

    List<Sku> findSkuByStvId(Long stvId);

    /**
     * 特殊商品作废
     * 
     * @param barcode
     * @return
     */
    List<String> specialCommodityDelete(List<String> barcode, Long ouId, Long userId);

    /**
     * 方法说明：退换货入库指令商品明细
     * 
     * @author LuYingMing
     * @param staId
     * @return
     */
    Pagination<SkuCommand> returnInboundDirectiveDetail(int start, int pageSize, Long staId, Long ouId);

    void insertMsgSKUSync(Sku sku, boolean isUpdate) throws UnsupportedEncodingException;

    void insertMsgSkuSyncByBarCode(SkuBarcode skuBarcode);
    
    /**
     * 查找耗材
     * @param barCode
     * @return
     */
    SkuCommand findSkuMaterial(String barCode,Long customerId);


}
