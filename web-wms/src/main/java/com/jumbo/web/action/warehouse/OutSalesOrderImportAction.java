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
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.WareHouseManagerProxy;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;

/**
 * 
 * @author sjk
 * 
 */
public class OutSalesOrderImportAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = 2003884635438006161L;

    @Autowired
    private WareHouseManagerProxy wareHouseManagerProxy;

    private File file;

    public String outSalesOrderImportEntry() {
        return SUCCESS;
    }

    public String importOutSalsOrder() {
        List<String> errorMsg = new ArrayList<String>();
        if (file == null) {
            return ERROR;
        } else {
            try {
                List<StockTransApplication> resultSta = new ArrayList<StockTransApplication>();
                ReadStatus rs = wareHouseManagerProxy.outSalesOrderImport(file, userDetails.getCurrentOu().getId(), userDetails.getUser().getId(), resultSta);
                request.put("result", ERROR);
                if (rs.getStatus() == ReadStatus.STATUS_SUCCESS) {
                    request.put("result", SUCCESS);
                    request.put("stas", JsonUtil.collection2json(resultSta));
                    for (Exception ex : rs.getExceptions()) {
                        if (ex instanceof BusinessException) {
                            BusinessException bex = (BusinessException) ex;
                            String msg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + bex.getErrorCode(), bex.getArgs());
                            errorMsg.add(msg);
                            BusinessException current = bex.getLinkedException();
                            while (current != null) {
                                // 库存不足信息
                                String m = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs());
                                errorMsg.add(m);
                                current = current.getLinkedException();
                            }
                        }
                    }
                } else if (rs.getStatus() > 0) {
                    List<String> list = ExcelKit.getInstance().getReadStatusMessages(rs, this.getLocale());
                    for (String s : list) {
                        errorMsg.add(s);
                    }
                } else if (rs.getStatus() < 0) {
                    for (Exception ex : rs.getExceptions()) {
                        if (ex instanceof BusinessException) {
                            BusinessException bex = (BusinessException) ex;
                            String msg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + bex.getErrorCode(), bex.getArgs());
                            errorMsg.add(msg);
                        }
                    }
                }
            } catch (BusinessException e) {
                request.put("result", ERROR);
                String msg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs());
                errorMsg.add(msg);
            }
            request.put("message", JsonUtil.collection2json(errorMsg));
        }
        return SUCCESS;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

}
