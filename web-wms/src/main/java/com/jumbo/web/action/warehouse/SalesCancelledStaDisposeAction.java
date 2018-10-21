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

import loxia.dao.Pagination;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import cn.baozun.bh.util.StringUtils;

import com.jumbo.Constants;
import com.jumbo.manager.ExcelExportManager;
import com.jumbo.util.FormatUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.WareHouseLocationManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerCancel;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;

/**
 * @author yitaofen
 */
public class SalesCancelledStaDisposeAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = 3819378717209197919L;

    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WareHouseManagerCancel wareHouseManagerCancel;
    @Autowired
    private ExcelExportManager excelExportManager;

    @Autowired
    private WareHouseLocationManager wareHouseLocationManager;

    private List<Long> staIds;
    private String shopId;
    private Date createTime;
    private Date endCreateTime;
    private String createTime2;
    private String endCreateTime2;
    private StockTransApplication sta;

    private String staList;



    private List<Long> plList;
    private Long wid;

    /**
     * 指定库位
     */
    private String appointStorage;

    public Long getWid() {
        return wid;
    }

    public void setWid(Long wid) {
        this.wid = wid;
    }

    public List<Long> getPlList() {
        return plList;
    }

    public void setPlList(List<Long> plList) {
        this.plList = plList;
    }

    /**
     * 销售出库作业 -已取消销售出库单处理
     * 
     * @return
     */
    public String outboundDisposeEntry() {
        return SUCCESS;
    }

    /**
     * 配货失败订单批量导出缺货的SKU信息
     * 
     * @return
     */
    public String skuFailureExport() {
        return SUCCESS;
    }

    /**
     * 导出缺货的SKU信息
     */
    public void exportskuDistributionOfFailureInfo() throws Exception {

        java.util.Calendar c = java.util.Calendar.getInstance();
        java.text.SimpleDateFormat f = new java.text.SimpleDateFormat("yyyy-MM-dd-hh_mm_ss");
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        log.info("==============fileName : {}=================", new Date());
        response.setHeader("Content-Disposition", "attachment;filename=" + f.format(c.getTime()) + Constants.EXCEL_XLS);
        try {
            excelExportManager.exportStaDistributionOfFailureInfo(response.getOutputStream(), this.getCurrentOu().getId());
        } catch (Exception e) {
            log.error("", e);
        }
        ServletOutputStream outs = response.getOutputStream();
        outs.flush();
        outs.close();
    }

    /**
     * 查询已取消sta
     * 
     * @return
     */
    public String staNoCancelledList() {
        setTableConfig();
        if (plList == null) wid = userDetails.getCurrentOu().getId();
        if (wid != null) {
            plList = new ArrayList<Long>();
            plList.add(wid);
        }
        Pagination<StockTransApplicationCommand> pageList = null;
        try {
            pageList = wareHouseManagerCancel.findSalesCancelUndoStaListByPage(tableConfig.getStart(), tableConfig.getPageSize(), shopId, sta, wid, plList, FormatUtil.getDate(createTime2), FormatUtil.getDate(endCreateTime2), tableConfig.getSorts());

        } catch (Exception e) {
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("staNoCancelledList error:", e);
            };
        }
        // System.out.println(pageList.getSize());
        request.put(JSON, toJson(pageList));
        // System.err.println(toJson(wareHouseManagerCancel.findSalesCancelUndoStaList(shopId, sta,
        // wid, plList, createTime, endCreateTime, tableConfig.getSorts())));
        // request.put(JSON, toJson(wareHouseManagerCancel.findSalesCancelUndoStaList(shopId, sta,
        // wid, plList, createTime, endCreateTime, tableConfig.getSorts())));
        return JSON;
    }

    public String suggestion() {
        setTableConfig();
        List<Integer> statusList = new ArrayList<Integer>();
        statusList.add(StockTransApplicationStatus.CANCEL_UNDO.getValue());
        request.put(JSON, toJson(wareHouseManager.findStvLineByStaIds(staIds, tableConfig.getSorts())));
        return JSON;
    }

    public String releaseInventory() throws JSONException {
        JSONObject result = new JSONObject();
        Long userId = userDetails.getUser().getId();
        try {
            if (StringUtils.isEmpty(appointStorage)) {
                if (staIds != null) {
                    for (Long staId : staIds) {
                        wareHouseManagerCancel.releaseInventoryByStaId(staId, userId, null);
                    }
                }
                if (!StringUtils.isEmpty(staList)) {
                    String array[] = staList.split(",");
                    for (int i = 0; i < array.length; i++) {
                        wareHouseManagerCancel.releaseInventoryByStaId(null, userId, array[i]);
                    }
                }
            } else {
                if (staIds != null) {
                    for (Long staId : staIds) {
                        wareHouseManager.releaseInventoryByStaIdAndLocationCode(staId, userId, appointStorage, userDetails.getCurrentOu().getId(), null);
                    }
                }
                if (!StringUtils.isEmpty(staList)) {
                    String array[] = staList.split(",");
                    for (int i = 0; i < array.length; i++) {
                        wareHouseManager.releaseInventoryByStaIdAndLocationCode(null, userId, appointStorage, userDetails.getCurrentOu().getId(), array[i]);
                    }
                }
            }
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
            BusinessException el = e.getLinkedException();
            if (el != null) {
                result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + el.getErrorCode(), el.getArgs()));
            }
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 方法说明：校验库位编码
     * 
     * @author LuYingMing
     * @date 2016年8月31日 下午8:27:56
     * @return
     * @throws JSONException
     */
    public String verifyLocationCode() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            result.put("result", wareHouseLocationManager.verifyLocationByCondition(appointStorage, userDetails.getCurrentOu().getId()));
        } catch (BusinessException e) {
            log.error("", e);
            result.put("result", ERROR);
        }
        request.put(JSON, result);
        return JSON;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public StockTransApplication getSta() {
        return sta;
    }

    public void setSta(StockTransApplication sta) {
        this.sta = sta;
    }

    public List<Long> getStaIds() {
        return staIds;
    }

    public void setStaIds(List<Long> staIds) {
        this.staIds = staIds;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setEndCreateTime(Date endCreateTime) {
        this.endCreateTime = endCreateTime;
    }

    public String getAppointStorage() {
        return appointStorage;
    }

    public void setAppointStorage(String appointStorage) {
        this.appointStorage = appointStorage;
    }

    public String getCreateTime2() {
        return createTime2;
    }

    public void setCreateTime2(String createTime2) {
        this.createTime2 = createTime2;
    }

    public String getEndCreateTime2() {
        return endCreateTime2;
    }

    public void setEndCreateTime2(String endCreateTime2) {
        this.endCreateTime2 = endCreateTime2;
    }

    public String getStaList() {
        return staList;
    }

    public void setStaList(String staList) {
        this.staList = staList;
    }

}
