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
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.webservice.nike.manager.NikeOrderManager;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.model.vmi.nikeData.NikeCheckReceiveCommand;



/**
 * nike 调整单查询
 * 
 * @author dailingyun
 * 
 */
public class NikeCheckQueryAction extends BaseJQGridProfileAction {
    /**
	 * 
	 */
    private static final long serialVersionUID = 433887006269649461L;

    @Autowired
    private NikeOrderManager nikeOrderManager;

    private NikeCheckReceiveCommand check;

    private File file;


    public String nikeCheckFailure() {
        return SUCCESS;
    }


    public String starbucksjsp() {
        return SUCCESS;
    }

    /**
     * 查询NIKE调整同步信息
     * 
     * @return
     */
    public String nikeCheckQuery() {
        setTableConfig();
        request.put(JSON, toJson(nikeOrderManager.findNikeCheckReceive(tableConfig.getStart(), tableConfig.getPageSize(), check, tableConfig.getSorts(), userDetails.getCurrentOu().getId())));
        return JSON;
    }

    /**
     * 入库确认数量导入
     * 
     * @return
     * @throws Exception
     */
    public String importNikecheckReceive() throws Exception {
        String msg = SUCCESS;
        request.put("msg", msg);
        if (file == null && check == null) {
            msg = " The upload file must not be null";
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                ReadStatus rs = nikeOrderManager.importNikecheckReceive(file, check.getManualType(), check.getRemark(), userDetails.getCurrentOu().getId(), userDetails.getUser());
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
            } catch (BusinessException e) {
                request.put("msg", "error");
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
                request.put("msg", "error");
                log.error("", e);
                log.error(e.getMessage());
                request.put("msg", e.getMessage());
            }
        }
        return SUCCESS;
    }

    public NikeCheckReceiveCommand getCheck() {
        return check;
    }

    public void setCheck(NikeCheckReceiveCommand check) {
        this.check = check;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

}
