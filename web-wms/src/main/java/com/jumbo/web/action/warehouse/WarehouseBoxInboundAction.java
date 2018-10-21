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

import loxia.support.excel.ExcelKit;
import loxia.support.excel.ReadStatus;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.util.FormatUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerCancel;
import com.jumbo.wms.manager.warehouse.WhBoxInboundManager;
import com.jumbo.wms.model.warehouse.StockTransApplication;

/**
 * 按箱收货
 * 
 * @author PUCK SHEN
 * 
 */
public class WarehouseBoxInboundAction extends BaseJQGridProfileAction {

    /**
     * 
     */
    private static final long serialVersionUID = 987895313882544548L;

    private File file;

    private String innerShopName;

    private Long staId;

    private String startTime;
    private String endTime;
    private String arriveStartTime;
    private String arriveEndTime;
    private Integer types;
    private Integer status;
    private StockTransApplication sta;
    private StockTransApplication cartonSta;


    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WareHouseManagerCancel wareHouseManagerCancel;
    @Autowired
    private WhBoxInboundManager whBoxInboundManager;



    /*
     * 显示按箱入库相关作业单
     */
    public String whBoxInbound() {
        return SUCCESS;
    }

    /**
     * 导入按箱收货
     * 
     * @return
     */
    public String importBoxReceive() {
        String msg = SUCCESS;
        request.put("msg", msg);
        request.put("result", SUCCESS);
        if (file == null) {
            msg = "The upload file can not be null...";
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                ReadStatus rs = whBoxInboundManager.whBoxReceiveImport(file, userDetails.getUser().getId(), staId);
                request.put("staId", staId);
                if (rs != null) {
                    if (rs.getExceptions().size() > 0) request.put("msg", rs.getExceptions().get(0).getMessage());
                }
                if (rs == null) {
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                } else if (rs.getStatus() > ReadStatus.STATUS_SUCCESS) {
                    request.put("result", ERROR);
                    request.put("msg", "");
                    List<String> list = ExcelKit.getInstance().getReadStatusMessages(rs, this.getLocale());
                    request.put("msg", list);
                } else if (rs.getStatus() < ReadStatus.STATUS_SUCCESS) {
                    request.put("result", ERROR);
                    request.put("msg", "");
                    List<String> list = new ArrayList<String>();
                    for (Exception ex : rs.getExceptions()) {
                        if (ex instanceof BusinessException) {
                            BusinessException be = (BusinessException) ex;
                            list.add(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()) + Constants.HTML_LINE_BREAK);
                        }
                    }
                    request.put("msg", list);
                }
            } catch (BusinessException e) {
                request.put("result", ERROR);
                request.put("msg", ERROR);
                log.error("", e);
                StringBuffer sb = new StringBuffer();
                if (e.getErrorCode() > 0) {
                    sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
                }
                BusinessException be = e;
                while ((be = be.getLinkedException()) != null) {
                    sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()));
                }
                log.error(e.getMessage());
                request.put("msg", sb.toString());
            } catch (Exception e) {
                request.put("result", ERROR);
                request.put("msg", ERROR);
                log.error("", e);
                log.error(e.getMessage());
                request.put("msg", e.getMessage());
            }
        }
        return SUCCESS;
    }


    /**
     * 导入按箱收货分页操作
     * 
     * @return
     */
    public String findCartonStaByPagination() {
        setTableConfig();
        request.put(JSON, toJson(wareHouseManager.findCartonStaByPagination(sta, userDetails.getCurrentOu(), FormatUtil.getDate(startTime), FormatUtil.getDate(endTime), FormatUtil.getDate(arriveStartTime), FormatUtil.getDate(arriveEndTime), types,
                status, tableConfig.getStart(), tableConfig.getPageSize(), tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 按箱收货作业单查询
     * 
     * @return
     */
    public String findCartonStaByGroupStaId() {
        if (cartonSta != null) {
            setTableConfig();
            request.put(JSON, toJson(wareHouseManager.findCartonStaByGroupStaId(cartonSta.getId())));
        }
        return JSON;
    }

    /*
     * 新建的按箱收货作业单关闭,不需要通知pac
     */
    public String cancelCartonSta() {
        JSONObject result = new JSONObject();
        try {
            if (cartonSta != null) {
            	wareHouseManagerCancel.cancelCartonStaOfNew(cartonSta.getId());
            }
            result.put("result", "success");
        } catch (JSONException e) {
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    /*
     * 调用后台，校验是否计划与收货执行量一致，一致，则跳转到第一个页面
     */
    public String checkingRootStaCompleteQty() {
        JSONObject result = new JSONObject();
        boolean flag = false;
        try {
            if (sta != null) {
                flag = wareHouseManager.checkingRootStaCompleteQty(sta.getId());
            }
            result.put("result", "success");
            result.put("flag", flag);
        } catch (JSONException e) {
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    public String boxReceiveStaLine() {
        if (sta != null) {
            setTableConfig();
            // request.put(JSON, toJson(wareHouseManager.findStaLineListByStaId(sta.getId(),
            // tableConfig.getSorts())));
            request.put(JSON, toJson(whBoxInboundManager.findBoxReceiveStaLine(sta.getId(), tableConfig.getSorts())));
        }
        return JSON;
    }

    public String getInnerShopName() {
        return innerShopName;
    }

    public void setInnerShopName(String innerShopName) {
        this.innerShopName = innerShopName;
    }

    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getArriveStartTime() {
        return arriveStartTime;
    }

    public void setArriveStartTime(String arriveStartTime) {
        this.arriveStartTime = arriveStartTime;
    }

    public String getArriveEndTime() {
        return arriveEndTime;
    }

    public void setArriveEndTime(String arriveEndTime) {
        this.arriveEndTime = arriveEndTime;
    }

    public Integer getTypes() {
        return types;
    }

    public void setTypes(Integer types) {
        this.types = types;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public StockTransApplication getSta() {
        return sta;
    }

    public void setSta(StockTransApplication sta) {
        this.sta = sta;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public StockTransApplication getCartonSta() {
        return cartonSta;
    }

    public void setCartonSta(StockTransApplication cartonSta) {
        this.cartonSta = cartonSta;
    }

}
