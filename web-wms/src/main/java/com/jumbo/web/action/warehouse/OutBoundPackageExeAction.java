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

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import loxia.dao.Pagination;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.jumbo.Constants;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WarehouseReturnManager;
import com.jumbo.wms.model.warehouse.Carton;
import com.jumbo.wms.model.warehouse.CartonCommand;
import com.jumbo.wms.model.warehouse.CartonLineCommand;
import com.jumbo.wms.model.warehouse.SkuImperfectCommand;
import com.jumbo.wms.model.warehouse.StaAdditionalLine;
import com.jumbo.wms.model.warehouse.StaLineCommand;

/**
 * 退仓出库包装执行
 * 
 * @author sjk
 * 
 */
public class OutBoundPackageExeAction extends BaseJQGridProfileAction {

    private static final long serialVersionUID = -7992937981455040097L;

    private String startCreateTime;
    private String endCreateTime;
    private CartonCommand cmd;
    private List<CartonLineCommand> lines;
    private List<StaAdditionalLine> addls;
    private Boolean isImperfect;
    private Long cartonId;
    /**
     * 作业单编码
     */
    private String staCode;
    /**
     * 条形码
     */
    private String barCode;
    /**
     * 数量
     */
    private Long qty;
    /**
     * 重量
     */
    private BigDecimal weight;
    /**
     * 残次编码
     */
    private String defectCode;

    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WarehouseReturnManager warehouseReturnManager;

    public String entExe() {
        return SUCCESS;
    }

    public String crwExportExeclIndex() {
        return SUCCESS;
    }

    /**
     * 装箱
     * 
     * @return
     * @throws JSONException
     */
    public String exePackage() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            Long whouid = userDetails.getCurrentOu().getId();
            Carton carton = null;
            if (isImperfect) {
                if (StringUtils.hasText(defectCode)) {
                    carton = wareHouseManager.packageCartonLine(cartonId, defectCode, lines, addls, weight, whouid);
                    result.put("result", SUCCESS);
                    result.put("carton", JsonUtil.obj2json(carton));
                } else {
                    result.put("result", ERROR);
                    result.put("message", "残次编码获取错误");
                }
            } else {
                carton = wareHouseManager.packageCartonLine(cartonId, lines, addls, weight, whouid);
                result.put("result", SUCCESS);
                result.put("carton", JsonUtil.obj2json(carton));
            }

        } catch (BusinessException e) {
            result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
            if (e.getLinkedException() != null) {
                StringBuffer ss = new StringBuffer();
                BusinessException current = e;
                while (current.getLinkedException() != null) {
                    current = current.getLinkedException();
                    ss.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs()));
                }
                result.put("message", result.get("message").toString() + Constants.HTML_LINE_BREAK + ss);
            }
        } catch (Exception e) {
            // e.printStackTrace();
            // log.error("", e);
            if (log.isErrorEnabled()) {
                log.error("exePackage error:", e);
            };
            result.put("result", ERROR);
            result.put("message", this.getMessage(ErrorCode.SYSTEM_ERROR + " : " + e.getMessage()));
        }
        request.put(JSON, result);
        return JSON;
    }

    public String updateCartonPacking() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            wareHouseManager.updateCartonPacking(cartonId);
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        } catch (Exception e) {
            log.error("", e);
            result.put("result", ERROR);
            result.put("message", this.getMessage(ErrorCode.SYSTEM_ERROR + " : " + e.getMessage()));
        }
        request.put(JSON, result);
        return JSON;
    }

    public String updateCartonCreate() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            wareHouseManager.updateCartonCreate(cartonId);
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        } catch (Exception e) {
            log.error("", e);
            result.put("result", ERROR);
            result.put("message", this.getMessage(ErrorCode.SYSTEM_ERROR + " : " + e.getMessage()));
        }
        request.put(JSON, result);
        return JSON;
    }

    public String findCartonList() {
        setTableConfig();
        if (cmd == null) {
            cmd = new CartonCommand();
        }
        if (StringUtils.hasText(startCreateTime)) {
            try {
                Date st = FormatUtil.stringToDate(startCreateTime);
                cmd.setStartCreateTime(st);
            } catch (ParseException e) {}
        }
        if (StringUtils.hasText(endCreateTime)) {
            try {
                Date et = FormatUtil.stringToDate(endCreateTime);
                cmd.setEndCreateTime(et);
            } catch (ParseException e) {}
        }
        Pagination<CartonCommand> cartons = wareHouseManager.findPackingStaCartonList(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), cmd, tableConfig.getSorts());
        request.put(JSON, toJson(cartons));
        return JSON;
    }

    /**
     * 装箱校验扫描的条码是否是当前计划商品以及是否大于执行量
     * 
     * @return
     * @throws JSONException
     */
    public String validateIsPlanOrNot() throws JSONException {
        JSONObject json = new JSONObject();
        try {
            Long whouid = userDetails.getCurrentOu().getId();
            warehouseReturnManager.validateSkuIsPlanOrNot(staCode, barCode, qty, whouid);
            json.put("msg", "success");
            // json.put("status", line.getInvStatus().getName());
        } catch (BusinessException e) {
            String errorMsg = getMessage((ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode()), e.getArgs());
            json.put("msg", errorMsg);
        } catch (Exception e) {
            json.put("msg", "error");
        }
        request.put(JSON, json);
        return JSON;
    }

    public String stalineStauts() throws JSONException {
        JSONObject json = new JSONObject();
        try {
            StaLineCommand staLineCommand = warehouseReturnManager.staLineStatus(staCode);
            json.put("msg", "success");
            json.put("statusName", staLineCommand.getIntInvstatusName());
            json.put("owner", staLineCommand.getOwner());
        } catch (BusinessException e) {
            String errorMsg = getMessage((ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode()), e.getArgs());
            json.put("msg", errorMsg);
        } catch (Exception e) {
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("stalineStauts error:" + staCode, e);
            };
            json.put("msg", "error");
        }
        request.put(JSON, json);
        return JSON;

    }

    public String validatedefectCode() throws JSONException {
        JSONObject json = new JSONObject();
        try {
            SkuImperfectCommand command = warehouseReturnManager.validateSkuImperfect(defectCode, barCode);
            json.put("msg", "success");
            json.put("defectCode", command.getDefectCode());
        } catch (BusinessException e) {
            String errorMsg = getMessage((ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode()), e.getArgs());
            json.put("msg", errorMsg);
        } catch (Exception e) {
            json.put("msg", "error");
        }
        request.put(JSON, json);
        return JSON;
    }

    public String validatedeImperfectCarton() throws JSONException {
        JSONObject json = new JSONObject();
        try {
            SkuImperfectCommand command = warehouseReturnManager.validateSkuImperfect(defectCode, barCode);
            json.put("msg", "success");
            json.put("defectCode", command.getDefectCode());
        } catch (BusinessException e) {
            String errorMsg = getMessage((ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode()), e.getArgs());
            json.put("msg", errorMsg);
        } catch (Exception e) {
            json.put("msg", "error");
        }
        request.put(JSON, json);
        return JSON;
    }



    public String getStartCreateTime() {
        return startCreateTime;
    }

    public void setStartCreateTime(String startCreateTime) {
        this.startCreateTime = startCreateTime;
    }

    public String getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(String endCreateTime) {
        this.endCreateTime = endCreateTime;
    }

    public CartonCommand getCmd() {
        return cmd;
    }

    public void setCmd(CartonCommand cmd) {
        this.cmd = cmd;
    }

    public List<CartonLineCommand> getLines() {
        return lines;
    }

    public void setLines(List<CartonLineCommand> lines) {
        this.lines = lines;
    }

    public List<StaAdditionalLine> getAddls() {
        return addls;
    }

    public void setAddls(List<StaAdditionalLine> addls) {
        this.addls = addls;
    }

    public Long getCartonId() {
        return cartonId;
    }

    public void setCartonId(Long cartonId) {
        this.cartonId = cartonId;
    }

    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getDefectCode() {
        return defectCode;
    }

    public void setDefectCode(String defectCode) {
        this.defectCode = defectCode;
    }

    public Boolean getIsImperfect() {
        return isImperfect;
    }

    public void setIsImperfect(Boolean isImperfect) {
        this.isImperfect = isImperfect;
    }



}
