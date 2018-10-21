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

import javax.servlet.ServletOutputStream;

import loxia.dao.Pagination;
import loxia.support.jasperreport.JasperPrintFailureException;
import loxia.support.jasperreport.JasperReportNotFoundException;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.manager.ExcelExportManager;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.web.manager.print.PrintManager;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerCancel;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExecute;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.warehouse.HandOverList;
import com.jumbo.wms.model.warehouse.HandOverListCommand;

public class HandOverListQueryAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = 2427543061432972568L;
    @Autowired
    private ExcelExportManager excelExportManager;
    @Autowired
    private WareHouseManager manager;
    @Autowired
    private WareHouseManagerCancel managerCancel;
    @Autowired
    private WareHouseManagerExecute wareHouseManagerExecute;
    @Autowired
    private PrintManager printManager;

    private OperationUnit ou;
    private Long hoListId;
    private Long hoListLineId;
    private String hoListCode;
    private Long wid;
    private String hoIds;
    private String transNo;
    private String lpCode;

    public String getLpCode() {
        return lpCode;
    }

    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }

    public String getHoIds() {
        return hoIds;
    }

    public void setHoIds(String hoIds) {
        this.hoIds = hoIds;
    }

    public Long getWid() {
        return wid;
    }

    public void setWid(Long wid) {
        this.wid = wid;
    }

    private HandOverListCommand handOverList;

    public OperationUnit getOu() {
        return ou;
    }

    public void setOu(OperationUnit ou) {
        this.ou = ou;
    }

    public String queryEntry() {
        return SUCCESS;
    }

    public String queryEntryIsOpc() throws JSONException {
        JSONObject result = new JSONObject();
        String typeName = userDetails.getCurrentOu().getOuType().getName();
        Boolean b = false;
        if (typeName.equals("OperationCenter")) {
            b = true;
        } else {
            b = false;
        }
        result.put("flag", b);
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 取消交货清单行
     * 
     * @return
     * @throws JSONException
     */
    public String cancelhoListLine() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            Boolean b = managerCancel.cancelHandoverListLine(hoListLineId, userDetails.getUser().getId(), userDetails.getCurrentOu().getId());
            result.put("result", SUCCESS);
            result.put("flag", b);
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 校验单号是否正确
     * 
     * @return
     * @throws JSONException
     */
    public String checkTrackingNoForHoList() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            wareHouseManagerExecute.checkTrackingNoForHoList(transNo);
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 
     * @return
     * @throws JSONException
     */
    /**
     * 根据运单号来获取包裹信息判断物流商
     * 
     * @return
     * @throws JSONException
     */
    public String checkTrackingNo() throws JSONException {
        JSONObject result = new JSONObject();
        String str = null;
        try {
            str = wareHouseManagerExecute.checkTrackingNo(transNo, lpCode);
            result.put("result", str);
        } catch (BusinessException e) {
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("checkTrackingNo error:" + transNo, e);
            };
        }
        request.put(JSON, result);
        return JSON;
    }

    public String cancelHandOverList() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            managerCancel.cancelHandOverList(hoListId, userDetails.getUser().getId(), userDetails.getCurrentOu().getId());
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 交接
     * 
     * @return
     * @throws JSONException
     */
    public String handOver() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            HandOverList ho = manager.handoverListhandOver(handOverList, userDetails.getUser().getId());
            result.put("result", SUCCESS);
            result.put("ho", JsonUtil.obj2json(ho));
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 根据交货订单id查询明细
     * 
     * @return
     */
    public String findLineByHoListId() {
        setTableConfig();
        request.put(JSON, toJson(manager.findHoLineByHoListId(hoListId, tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 根据id获取交货清单信息
     * 
     * @return
     */
    public String getHandOverListById() {
        HandOverList hoList = manager.getHandOverListById(hoListId);
        request.put("hoList", JsonUtil.obj2json(hoList));
        return JSON;
    }

    /**
     * 物流交接清单查询
     * 
     * @return
     */
    public String QueryHandOverList() {
        setTableConfig();
        if (handOverList != null) {
            handOverList.setCreateStratTime(FormatUtil.getDate(handOverList.getCreateStratTime1()));
            handOverList.setCreateEndTime(FormatUtil.getDate(handOverList.getCreateEndTime1()));
            handOverList.setHandOverStartTime(FormatUtil.getDate(handOverList.getHandOverStartTime1()));
            handOverList.setHandOverEndTime(FormatUtil.getDate(handOverList.getHandOverEndTime1()));
        }
        Pagination<HandOverListCommand> hollist = manager.findHandOverListByPage(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), handOverList, tableConfig.getSorts());
        request.put(JSON, toJson(hollist));
        return JSON;
    }


    /**
     * 根据id获取交货清单信息
     * 
     * @return
     */
    public String findHandOverListById() {
        JSONObject json = new JSONObject();
        try {
            json.put("handoverlist", JsonUtil.obj2json(manager.getHandOverListById(hoListId)));
        } catch (Exception e) {
            log.error("", e);
        }
        request.put(JSON, json);
        return JSON;
    }

    /**
     * 物流交接清单 保存
     * 
     * @return
     */
    public String saveHandOverListInfo() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            HandOverListCommand ho = wareHouseManagerExecute.saveHandOverListInfo(handOverList, userDetails.getUser().getId());
            result.put("result", SUCCESS);
            result.put("ho", JsonUtil.obj2json(ho));
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 物流交接清单打印
     * 
     * @return
     */
    public String printHandOverList() {
        try {
            // if(ou==null||ou.getId()==null){
            // wid=userDetails.getCurrentOu().getId();
            // }else{
            // wid=ou.getId();
            // }
            // JasperPrint jasperPrintList = printManager.printHandOverList(hoListId,
            // userDetails.getCurrentOu().getId());
            // 新增物流打印需求
            JasperPrint jasperPrintList = printManager.newPrintHandOverList(hoListId, userDetails.getCurrentOu().getId());
            if (jasperPrintList == null) jasperPrintList = new JasperPrint();
            return printObject(jasperPrintList);
        } catch (Exception e1) {
            // e1.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("printHandOverList error:" + transNo, e1);
            };
            return null;
        }
    }

    /**
     * 批量打印交接清单打印
     */
    public String printHandOverList2() {
        List<JasperPrint> jp;
        try {
            jp = printManager.newPrintHandOverList2(hoIds, userDetails.getCurrentOu().getId());
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

    /**
     * 批量打印交接清单打印
     */
    public String printAutoWhHandOverList() {
        List<JasperPrint> jp;
        try {
            jp = printManager.printAutoWhHandOverList(hoIds, userDetails.getCurrentOu().getId());
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

    /**
     * 
     * @return
     */
    public String currentHandOverListTotal() {
        setTableConfig();
        // 新建 1 和完成 10
        List<HandOverListCommand> list = manager.findHandOverListTotal(userDetails.getCurrentOu().getId(), tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }


    /**
     * 导出物流送达明细
     * 
     * @throws Exception
     */
    public void exportTransInfo() throws Exception {
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        hoListCode = new String(hoListCode.getBytes("GBK"), "ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + hoListCode + Constants.EXCEL_XLS);
        try {
            excelExportManager.exportInfoByHoListId(response.getOutputStream(), hoListId, hoListCode);
        } catch (Exception e) {
            log.error("", e);
        }
        ServletOutputStream outs = response.getOutputStream();
        outs.flush();
        outs.close();
    }


    public Long getHoListId() {
        return hoListId;
    }

    public void setHoListId(Long hoListId) {
        this.hoListId = hoListId;
    }

    public HandOverListCommand getHandOverList() {
        return handOverList;
    }

    public void setHandOverList(HandOverListCommand handOverList) {
        this.handOverList = handOverList;
    }

    public Long getHoListLineId() {
        return hoListLineId;
    }

    public void setHoListLineId(Long hoListLineId) {
        this.hoListLineId = hoListLineId;
    }

    public String getHoListCode() {
        return hoListCode;
    }

    public void setHoListCode(String hoListCode) {
        this.hoListCode = hoListCode;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

}
