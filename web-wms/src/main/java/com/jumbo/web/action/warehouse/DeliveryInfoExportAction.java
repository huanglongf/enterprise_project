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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;

import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.wms.manager.DropDownListManager;
import com.jumbo.manager.ExcelExportManager;
import com.jumbo.wms.manager.warehouse.excel.ExcelWriterManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.web.action.BaseJQGridProfileAction;

/**
 * 
 * @author jumbo
 * 
 */
public class DeliveryInfoExportAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = -5701503637076291286L;
    @Autowired
    private DropDownListManager dropDownListManager;
    @Autowired
    private ExcelWriterManager excelWriterManager;
    @Autowired
    private ExcelExportManager excelExportManager;
    private Date starttime;
    private Date endtime;
    private Long ouid;
    private Long deliveryid;
    private List<OperationUnit> whList;

    public String deliveryInfoExportEntry() {
        whList = dropDownListManager.findWarehouseOuListByComOuId(userDetails.getCurrentOu().getId(), null);
        return SUCCESS;
    }

    /**
     * 物流对账信息导出
     * 
     * @throws Exception
     */
    public void deliveryInfoExportForAccount() throws Exception {
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        List<Long> oulist = null;
        if (ouid == null) {
            whList = dropDownListManager.findWarehouseOuListByComOuId(userDetails.getCurrentOu().getId(), null);
            oulist = new ArrayList<Long>();
            if (whList.size() > 0) for (OperationUnit ou : whList) {
                oulist.add(ou.getId());
            }
        }
        String fileName = "物流对账信息";
        fileName = new String(fileName.getBytes("GBK"), "ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + Constants.EXCEL_XLS);
        try {
            excelExportManager.deliveryInfoExport(response.getOutputStream(), oulist, deliveryid, ouid, starttime, endtime);
        } catch (Exception e) {
            log.error("", e);
        }
        ServletOutputStream outs = response.getOutputStream();
        outs.flush();
        outs.close();
    }

    /**
     * 统计 物流对账信息导出的总数量
     */
    public String findDeliveryInfoCount() throws JSONException {
        JSONObject json = new JSONObject();
        List<Long> oulist = null;
        if (ouid == null) {
            whList = dropDownListManager.findWarehouseOuListByComOuId(userDetails.getCurrentOu().getId(), null);
            oulist = new ArrayList<Long>();
            if (whList.size() > 0) for (OperationUnit ou : whList) {
                oulist.add(ou.getId());
            }
        }
        // 获取总数量 判断是否超过30W
        Long count = excelWriterManager.findDeliveryInfoCount(oulist, deliveryid, ouid, starttime, endtime);
        if (count <= Constants.WH_PACKAGE_INFO_COUNT) {
            json.put("msg", "success");
        } else {
            json.put("msg", "error");
        }
        request.put(JSON, json);
        return JSON;
    }

    public List<OperationUnit> getWhList() {
        return whList;
    }


    public void setWhList(List<OperationUnit> whList) {
        this.whList = whList;
    }

    public Long getOuid() {
        return ouid;
    }


    public void setOuid(Long ouid) {
        this.ouid = ouid;
    }


    public Long getDeliveryid() {
        return deliveryid;
    }


    public void setDeliveryid(Long deliveryid) {
        this.deliveryid = deliveryid;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }
}
