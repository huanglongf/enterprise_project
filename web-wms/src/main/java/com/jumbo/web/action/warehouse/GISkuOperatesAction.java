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


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExecute;
import com.jumbo.wms.manager.warehouse.WareHouseManagerQuery;
import com.jumbo.wms.model.warehouse.WarehouseLocationCommand;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;

/**
 * 仓库其他出入库操作
 * 
 * @author dly
 * 
 */
public class GISkuOperatesAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = -580564636602616897L;

    @Autowired
    WareHouseManagerQuery whQuery;
    @Autowired
    WareHouseManagerExecute whExecute;

    private WarehouseLocationCommand loc;
    private File file;

    public String operatesGISku() {
        return SUCCESS;
    }

    /**
     * 查询暂存区的库位商品
     * 
     * @return
     */
    public String queryGILocation() {
        setTableConfig();
        if (loc != null) {
            loc.setCreateDate(FormatUtil.getDate(loc.getCreateDate1()));
            loc.setEndCreateDate(FormatUtil.getDate(loc.getEndCreateDate1()));
        }
        request.put(JSON, toJson(whQuery.findGILocation(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), loc, tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 查寻暂存区上的商品
     * 
     * @return
     */
    public String queryGILocSku() {
        setTableConfig();
        request.put(JSON, toJson(whQuery.queryGILocSku(loc.getId())));
        return JSON;
    }


    /**
     * 创建库间移动申请作业单
     * 
     * @return
     * @throws Exception
     */
    public String createGIInboundByLoc() throws Exception {
        List<String> errorMsg = new ArrayList<String>();
        JSONObject result = new JSONObject();
        try {
            // wareHouseManager.createVMIFlittingSta(stacode, comd, userDetails.getUser(),
            // userDetails.getCurrentOu(), staLineCmd);
            whExecute.createGIInboundByLoc(loc.getId(), userDetails.getUser(), userDetails.getCurrentOu());
            result.put("msg", SUCCESS);
        } catch (BusinessException e) {
            BusinessException bex = e;
            do {
                String msg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + bex.getErrorCode(), bex.getArgs());
                log.error(msg);
                errorMsg.add(msg);
                bex = bex.getLinkedException();
            } while (bex != null);
            result.put("msg", ERROR);
            result.put("errMsg", JsonUtil.collection2json(errorMsg));
        } catch (Exception e) {
            log.error("",e);
            result.put("msg", e.getCause() + " " + e.getMessage());
        }
        request.put(JSON, result);
        return JSON;
    }

    public WarehouseLocationCommand getLoc() {
        return loc;
    }

    public void setLoc(WarehouseLocationCommand loc) {
        this.loc = loc;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

}
