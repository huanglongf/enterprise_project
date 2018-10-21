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
 */
package com.jumbo.web.action.operationcenter;

import java.util.List;

import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.util.FormatUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.operationcenter.ScanSkuRecordManager;
import com.jumbo.wms.model.command.ScanSkuRecordCommand;

/**
 * @author lichuan
 * 
 */
public class ScanSkuRecordAction extends BaseJQGridProfileAction {

    private static final long serialVersionUID = 286064528043500103L;
    @Autowired
    private ScanSkuRecordManager scanSkuRecordManager;
    private List<ScanSkuRecordCommand> ssrList;
    private ScanSkuRecordCommand ssrCmd;

    public String skuScanRecord() {
        return SUCCESS;
    }

    public String saveScanRecords() throws JSONException {
        JSONObject result = new JSONObject();
        String errorMsg = "";
        try {
            scanSkuRecordManager.saveScanRecords(ssrList, userDetails.getUser().getId());
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("result", ERROR);
            errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs());
            BusinessException current = e;
            while (current.getLinkedException() != null) {
                current = current.getLinkedException();
                errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs());
            }
            result.put("msg", errorMsg);
        }
        request.put(JSON, result);
        return JSON;
    }

    public String findScanRecords() {
        setTableConfig();
        if (ssrCmd != null) {
            ssrCmd.setCreateTime(FormatUtil.getDate(ssrCmd.getFromTime()));
            ssrCmd.setFinishTime(FormatUtil.getDate(ssrCmd.getToTime()));
        }
        request.put(JSON, toJson(scanSkuRecordManager.findScanRecordsByPage(tableConfig.getStart(), tableConfig.getPageSize(), ssrCmd, tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 获取批次号
     */
    public String getScanRecordBatchCode() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            String batchCode = scanSkuRecordManager.getScanRecordBatchCode();
            if (StringUtil.isEmpty(batchCode)) {
                result.put("result", ERROR);
            } else {
                result.put("result", SUCCESS);
                result.put("batchCode", batchCode);
            }
        } catch (Exception e) {
            result.put("result", ERROR);
        }
        request.put(JSON, result);
        return JSON;
    }

    public List<ScanSkuRecordCommand> getSsrList() {
        return ssrList;
    }

    public void setSsrList(List<ScanSkuRecordCommand> ssrList) {
        this.ssrList = ssrList;
    }

    public ScanSkuRecordCommand getSsrCmd() {
        return ssrCmd;
    }

    public void setSsrCmd(ScanSkuRecordCommand ssrCmd) {
        this.ssrCmd = ssrCmd;
    }


}
