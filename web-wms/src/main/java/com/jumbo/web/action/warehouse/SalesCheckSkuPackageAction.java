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
package com.jumbo.web.action.warehouse;

import java.util.List;

import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.manager.warehouse.PackageSkuManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.warehouse.PickingListCheckMode;
import com.jumbo.wms.model.warehouse.PickingListCommand;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.util.FormatUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;

/**
 * 套装核对
 * 
 * @author
 * 
 */
public class SalesCheckSkuPackageAction extends BaseJQGridProfileAction {
    private static final long serialVersionUID = 7629719173462523246L;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private PackageSkuManager packageSkuManager;
    private PickingListCommand plCmd;
    private Long pid;
    private Long pgSkuId;

    /**
     * 套装核对
     * 
     * @return
     */
    public String entPlCheck() {
        return SUCCESS;
    }

    /**
     * 配货清单Json,配货中+部分出库
     * 
     * @return
     * @throws JSONException
     */
    public String findSkuPackagePickingList() throws JSONException {
        setTableConfig();
        if (plCmd == null) {
            plCmd = new PickingListCommand();
        }
        if (plCmd != null) {
            plCmd.setPickingTime(FormatUtil.getDate(plCmd.getPickingTime1()));
            plCmd.setExecutedTime(FormatUtil.getDate(plCmd.getExecutedTime1()));
        }
        plCmd.setWarehouse(userDetails.getCurrentOu());
        plCmd.setCheckMode(PickingListCheckMode.PICKING_PACKAGE);
        request.put(JSON, toJson(wareHouseManager.findSkuPackingPickingList(tableConfig.getStart(), tableConfig.getPageSize(), plCmd, tableConfig.getSorts())));
        return JSON;
    }

    public String getPackageMainSku() throws JSONException {
        JSONObject result = new JSONObject();
        String skus1 = packageSkuManager.findSkus1ByPackingListId(pid);
        if (skus1 == null || "".equals(skus1)) {
            result.put("result", ERROR);
        } else {
            result.put("result", SUCCESS);
            String tmp[] = skus1.split(";");
            Long skuId = Long.valueOf(tmp[0]);
            String qty = tmp[1];
            Sku sku = wareHouseManager.findSkuBySkuId(skuId);
            result.put("barcode", sku.getBarCode());
            result.put("skucode", sku.getCode());
            result.put("skuid", sku.getId());
            result.put("suppliercode", sku.getSupplierCode());
            result.put("keyproperties", sku.getKeyProperties());
            result.put("skuname", sku.getName());
            result.put("qty", qty);
        }
        request.put(JSON, result);
        return JSON;
    }

    public String getOcpStalForPgSkuByPl() {
        setTableConfig();
        List<StaLineCommand> list = wareHouseManager.findOccpiedStaLineForSkuPackageByPlId(plCmd.getId(), pgSkuId, tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    public PickingListCommand getPlCmd() {
        return plCmd;
    }

    public void setPlCmd(PickingListCommand plCmd) {
        this.plCmd = plCmd;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Long getPgSkuId() {
        return pgSkuId;
    }

    public void setPgSkuId(Long pgSkuId) {
        this.pgSkuId = pgSkuId;
    }

}
