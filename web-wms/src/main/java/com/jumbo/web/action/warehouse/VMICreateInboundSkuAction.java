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
import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.vmi.coachData.CoachParseDataManagerProxy;
import com.jumbo.web.action.BaseJQGridProfileAction;

public class VMICreateInboundSkuAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = -5676799612636133489L;

    private File file;

    private String ownerid;

    private String slipCode;

    @Autowired
    private CoachParseDataManagerProxy coachParseDataManagerProxy;

    public String vmiCreateInboundSku() {
        return SUCCESS;
    }

    public String vmiInboundSkuImport() {
        String msg = SUCCESS;
        request.put("msg", msg);
        request.put("result", SUCCESS);
        if (file == null) {
            msg = "The upload file can not be null...";
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                ReadStatus rs = coachParseDataManagerProxy.generateCoachSkuImport(file, Long.parseLong(request.get("ownerid").toString()));
                if (rs != null) {
                    if (rs.getExceptions().size() > 0) {
                        request.put("msg", rs.getExceptions().get(0).getMessage());
                    }
                    if (rs.getStatus() > ReadStatus.STATUS_SUCCESS) {
                        request.put("msg", "");
                        List<String> list = ExcelKit.getInstance().getReadStatusMessages(rs, this.getLocale());
                        request.put("msg", list);
                    } else if (rs.getStatus() < ReadStatus.STATUS_SUCCESS) {
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
                }
            } catch (BusinessException e) {
                request.put("msg", ERROR);
                log.error("",e);
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
                request.put("msg", ERROR);
                log.error("",e);
                log.error(e.getMessage());
                request.put("msg", e.getMessage());
            }
        }
        return SUCCESS;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(String ownerid) {
        this.ownerid = ownerid;
    }

    public String getSlipCode() {
        return slipCode;
    }

    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode;
    }
}
