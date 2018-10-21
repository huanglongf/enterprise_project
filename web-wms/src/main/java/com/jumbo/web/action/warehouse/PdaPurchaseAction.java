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

import loxia.support.excel.ReadStatus;
import loxia.support.jasperreport.JasperPrintFailureException;
import loxia.support.jasperreport.JasperReportNotFoundException;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerCancel;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.manager.warehouse.excel.ExcelReadManager;
import com.jumbo.wms.model.warehouse.PdaPostLogCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.web.manager.print.PrintManager;

/**
 * pda收货上架
 * 
 * @author jumbo
 * 
 */
public class PdaPurchaseAction extends BaseJQGridProfileAction {


    /**
	 * 
	 */
    private static final long serialVersionUID = 1023583269004345262L;

    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WareHouseManagerExe wareHouseManagerExe;
    @Autowired
    private WareHouseManagerCancel wareHouseManagerCancel;
    @Autowired
    private ExcelReadManager excelReadManager;
    @Autowired
    private PrintManager printManager;

    private PdaPostLogCommand cmd;
    private StockTransApplicationCommand staCommand;
    private String createDate;
    private String endCreateDate;
    private boolean isFinish = false;
    private Long staid;
    private File file;
    private StockTransVoucher stv;
    private String pdaCode;
    private String code;

    public String pdaPurchaseEntry() {
        return SUCCESS;
    }

    public String findPdaPurchaseStas() {
        setTableConfig();
        request.put(JSON, toJson(wareHouseManager.findPdaPurchaseStas(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), FormatUtil.getDate(createDate), FormatUtil.getDate(endCreateDate), staCommand, tableConfig
                .getSorts())));
        return JSON;
    }

    /***
     * pda 收货明细
     * 
     * @return
     */
    public String findPdaPurchaseDetail() {
        setTableConfig();
        request.put(JSON, toJson(wareHouseManager.findPdaPurchaseDetailByStaId(tableConfig.getStart(), tableConfig.getPageSize(), staid, tableConfig.getSorts())));
        return JSON;
    }

    /***
     * 作业单号打印
     * 
     * @return
     */
    public String printStaCode() {
        JasperPrint jp = null;
        if (staid == null) {
            return ERROR;
        }
        try {
            jp = printManager.printStaCode(staid);
            return printObject(jp);
        } catch (JasperPrintFailureException e) {
            log.error("", e);
        } catch (JRException e) {
            log.error("", e);
        } catch (JasperReportNotFoundException e) {
            log.error("", e);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    /***
     * pda 确认收货
     * 
     * @return
     * @throws Exception
     */
    public String executePdaPurchase() throws Exception {
        JSONObject result = new JSONObject();
        try {
            StockTransVoucher stv = wareHouseManagerExe.executePdaPurchase(staid, userDetails.getCurrentOu().getId(), this.userDetails.getUser().getId());
            Integer sncount = wareHouseManager.findIsNeedSnByStaId(staid, this.userDetails.getCurrentOu().getId());
            try {
                // 商品仓库定位
            	wareHouseManagerExe.executeLocationByStaId(staid, code);
            } catch (Exception e) {
                // System.out.println("insert t_wh_pda_sku_location error!");
            }
            result.put("stvId", stv.getId());
            result.put("sncount", sncount);
            // result.put("stvLine", toJson(stvLineDao.findStvLinesListByStvId(stv.getId(),new
            // BeanPropertyRowMapperExt<StvLineCommand>(StvLineCommand.class))));
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("error", ERROR);
            result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    /***
     * PDA sn号导入
     * 
     * @return
     */
    public String pdaPurchaseSnImport() {
        List<String> errorMsg = new ArrayList<String>();
        if (file == null) {
            return ERROR;
        } else {
            try {
                ReadStatus rs = excelReadManager.pdaPurchaseSnImport(file, staid, userDetails.getCurrentOu().getId());
                request.put("result", ERROR);
                if (rs.getStatus() == ReadStatus.STATUS_SUCCESS) {
                    request.put("result", SUCCESS);
                }
                errorMsg.addAll(getExcelImportErrorMsg(rs));
            } catch (BusinessException e) {
                request.put("result", ERROR);
                String msg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs());
                errorMsg.add(msg);
            }
            request.put("message", JsonUtil.collection2json(errorMsg));
        }
        return SUCCESS;
    }

    /**
     * 
     * @return
     * @throws JSONException
     */
    public String findIsNeedSnByStaId() throws JSONException {
        JSONObject obj = new JSONObject();
        try {
            Integer count = wareHouseManager.findIsNeedSnByStaId(staid, this.userDetails.getCurrentOu().getId());
            if (count.intValue() > 0) {
                obj.put("result", SUCCESS);
            } else {
                obj.put("result", ERROR);
            }
        } catch (BusinessException e) {
            obj.put("result", ERROR);
            obj.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, obj);
        return JSON;
    }


    // PDA上架
    public String pdaInboundPurchase() throws JSONException {
        JSONObject obj = new JSONObject();
        try {
            Long stvId =
                    wareHouseManager.pdaInboundPurchase(staCommand.getId(), staCommand.getInvoiceNumber(), staCommand.getDutyPercentage(), staCommand.getMiscFeePercentage(), pdaCode, userDetails.getUser().getId(), userDetails.getCurrentOu()
                            .getParentUnit().getParentUnit().getId(), isFinish);
            if (stvId != null) {
                obj.put("stvid", stvId);
            }
            obj.put(RESULT, SUCCESS);
        } catch (Exception e) {
            String msg = getErrorMessage(e, true);
            obj.put(RESULT, ERROR);
            obj.put(MESSAGE, msg);
            log.error("", e);
            log.error(e.getMessage());
        }
        request.put(JSON, obj);
        return JSON;
    }


    // 关闭不上架
    public String pdaCancelStv() throws JSONException {
        JSONObject obj = new JSONObject();
        try {
        	wareHouseManagerCancel.cancelStv(stv.getId(), userDetails.getUser().getId());
            obj.put(RESULT, SUCCESS);
        } catch (Exception e) {
            String msg = getErrorMessage(e, true);
            obj.put(RESULT, ERROR);
            obj.put(MESSAGE, msg);
        }
        request.put(JSON, obj);
        return JSON;
    }

    public String pdaCodeQuery() {
        request.put(JSON, JsonUtil.collection2json(wareHouseManager.queryPdaCodeByStaId(staid)));
        return JSON;
    }


    public PdaPostLogCommand getCmd() {
        return cmd;
    }

    public void setCmd(PdaPostLogCommand cmd) {
        this.cmd = cmd;
    }

    public Long getStaid() {
        return staid;
    }

    public void setStaid(Long staid) {
        this.staid = staid;
    }

    public StockTransApplicationCommand getStaCommand() {
        return staCommand;
    }

    public void setStaCommand(StockTransApplicationCommand staCommand) {
        this.staCommand = staCommand;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getEndCreateDate() {
        return endCreateDate;
    }

    public void setEndCreateDate(String endCreateDate) {
        this.endCreateDate = endCreateDate;
    }


    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean isFinish) {
        this.isFinish = isFinish;
    }


    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public StockTransVoucher getStv() {
        return stv;
    }

    public void setStv(StockTransVoucher stv) {
        this.stv = stv;
    }

    public String getPdaCode() {
        return pdaCode;
    }

    public void setPdaCode(String pdaCode) {
        this.pdaCode = pdaCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
